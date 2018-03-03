package com.China.ChinaCity;

import android.app.*;
import android.os.*;
import android.view.*;
import android.content.*;
import android.widget.*;
import android.view.animation.*;
import java.util.*;
import android.*;
import android.net.*;
import com.China.ChinaCity.Tool.*;

public class SplashActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		
		//Download Files
		/*Context mContext=getApplicationContext();
		String downloadUrl ="https://github.com/ligongzzz/ChinaCity/blob/master/README.md";
		DownloadManager.Request request=new DownloadManager.Request(Uri.parse(downloadUrl));
		String fileName ="file.txt";
		request.setDestinationInExternalPublicDir("/ChinaCity/", fileName);
		DownloadManager downloadManager= (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
		downloadManager.enqueue(request);
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        request.setVisibleInDownloadsUi(false);*/
		
		SharedPreferences.Editor editor= getSharedPreferences("temp", MODE_WORLD_WRITEABLE).edit();
		editor.putInt("add_downloaded",0);
		editor.commit();

		SharedPreferences sp = getSharedPreferences("temp", Activity.MODE_PRIVATE);
		int p2=0;
		int result = sp.getInt("splashscreen", p2);
		if (result == 1)
		{
			Intent intent = new Intent(SplashActivity.this, MainActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.alpha_anim_appear, R.anim.alpha_anim_disappear);
			finish();
		}
		else
		{
			setContentView(R.layout.splashscreen);
			TimerTask task=new TimerTask(){
				public void run()
				{
					Message message = new Message(); 
					message.what = 1; 
					handler.sendMessage(message);
					finish();
				}
			};
			Timer timer=new Timer();
			timer.schedule(task, 2000);
		}
    }
	Handler handler= new Handler() { 
		public void handleMessage(Message msg)
		{ 
			Intent intent = new Intent(SplashActivity.this, MainActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.alpha_anim_appear, R.anim.alpha_anim_disappear);
			super.handleMessage(msg); 
		}
	};
}
