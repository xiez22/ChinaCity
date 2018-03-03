package com.China.ChinaCity;

import android.app.*;
import android.os.*;
import android.view.*;
import android.content.*;
import android.widget.*;
import android.graphics.*;
import com.China.ChinaCity.Tool.*;
import android.view.animation.*;

public class Sub1Activity extends Activity 
{

	int downloadsta=0;
	int downloaded=0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub1);

		//Start Internet Connection
		SharedPreferences sp = getSharedPreferences("temp", Activity.MODE_PRIVATE);
		int p2=1;
		int result = sp.getInt("bigtitle", p2);
		if (result == 1)
		{
			new Thread(new Runnable() {
					@Override
					public void run()
					{
						int dr=0;int dr1=0;
						int filesta=fileutil.write(null, "/Android/data/com.China.ChinaCity/cache/image_map.jpg", 2);
						int filesta1=fileutil.write(null, "/Android/data/com.China.ChinaCity/cache/image_area.jpg", 2);
						if (filesta == 0)
						{
							downloaded=1;
							dr = internetutil.download("https://coding.net/u/ligongzzz/p/ChinaResources/git/raw/master/src/image_map.jpg", "/Android/data/com.China.ChinaCity/cache/", "image_map.jpg");
						}
						if (filesta1 == 0)
						{
							downloaded=1;
							dr1 = internetutil.download("https://coding.net/u/ligongzzz/p/ChinaResources/git/raw/master/src/image_area.jpg", "/Android/data/com.China.ChinaCity/cache/", "image_area.jpg");
						}
						if (dr == 0 && dr1 == 0)
						{
							downloadsta = 1;
							Message message = new Message(); 
							message.what = 1; 
							handler.sendMessage(message);
						}
					}
				}).start();
		}
		else{
			TextView textView1=(TextView)findViewById(R.id.sub1TextView1);
			textView1.setText("需要开启在线资源服务以显示图片");
			TextView textView2=(TextView)findViewById(R.id.sub1TextView2);
			textView2.setText("需要开启在线资源服务以显示图片");
		}

    }

	public void onClickSub1ButtonGet(View view)
	{
		Intent intent = new Intent(this, HiActivity.class);
    	startActivity(intent);
	}

	public void onClickSub1ButtonArea(View view)
	{
		Intent intent = new Intent(this, Sub1Sub1Activity.class);
    	startActivity(intent);
	}

	public void onClickSub1ButtonBack(View view)
	{
		finish();
	}

	Handler handler= new Handler() { 
		public void handleMessage(Message msg)
		{ 
			if (downloadsta == 1)
			{
				String path = Environment.getExternalStorageDirectory().toString();//获得SDCard目录 
				Bitmap bmpDefaultPic=null;
				ImageView  iv = (ImageView)findViewById(R.id.sub1ImageView1);
				if (bmpDefaultPic == null)
					bmpDefaultPic = BitmapFactory.decodeFile(path + "/Android/data/com.China.ChinaCity/cache/image_map.jpg", null);
				iv.setImageBitmap(bmpDefaultPic);
				Bitmap bmpDefaultPic1=null;
				ImageView  iv1 = (ImageView)findViewById(R.id.sub1ImageView2);
				if (bmpDefaultPic1 == null)
					bmpDefaultPic1 = BitmapFactory.decodeFile(path + "/Android/data/com.China.ChinaCity/cache/image_area.jpg", null);
				iv1.setImageBitmap(bmpDefaultPic1);
				
				if(downloaded==1){
				Animation animation=AnimationUtils.loadAnimation(Sub1Activity.this, R.anim.alpha_anim_appear);
				iv.startAnimation(animation);
				iv1.startAnimation(animation);
				}
			}
			super.handleMessage(msg); 
		}
	};
}
