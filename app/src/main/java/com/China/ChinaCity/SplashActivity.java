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
	//Constant
	final int splashTime=1500;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		
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
			TextView textView=(TextView)findViewById(R.id.splashscreenTextView1);
			textView.setText("V"+getResources().getString(R.string.version)+"   ©2018 ligongzzz");
			
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
			timer.schedule(task,splashTime);
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
