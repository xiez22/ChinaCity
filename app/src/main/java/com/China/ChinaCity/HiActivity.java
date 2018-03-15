package com.China.ChinaCity;

import android.app.*;
import android.os.*;
import android.view.*;
import android.content.*;
import android.widget.*;
import com.China.ChinaCity.Tool.*;
import java.util.*;
import android.net.*;
import android.graphics.*;
import android.view.animation.*;

public class HiActivity extends Activity 
{
	int downloadsta=0;
	int downloadsta1=0;
	int downloaded=0;
	int add_downloaded=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hi);

		SharedPreferences sp = getSharedPreferences("temp", Activity.MODE_PRIVATE);
		int result = sp.getInt("bigtitle", 0);
		add_downloaded = sp.getInt("add_downloaded", 1);
		if (result == 0)
		{
			Button button=(Button)findViewById(R.id.hiButton1);
			button.setVisibility(View.INVISIBLE);
			TextView textView=(TextView)findViewById(R.id.hiTextView1);
			textView.setText("要获取下载地址，需要开启在线资源服务。请在设置中开启在线资源服务。");
			ProgressBar progressBar=(ProgressBar)findViewById(R.id.hiProgressBar1);
			LinearLayout linear=(LinearLayout)findViewById(R.id.hiLinearLayout1);
			linear.removeView(progressBar);
		}
		else if (result == 1 && downloadsta == 0)
		{
			Button button=(Button)findViewById(R.id.hiButton1);
			button.setVisibility(View.INVISIBLE);
			TextView textView=(TextView)findViewById(R.id.hiTextView1);
			textView.setText("获取下载地址中，请稍候……");
			//Download Files
			new Thread(new Runnable() {
					@Override
					public void run()
					{
						int dr=0;
						if (add_downloaded == 0)
						{
							dr = internetutil.download("https://coding.net/u/ligongzzz/p/ChinaResources/git/raw/master/src/DownloadURL.txt", "/ChinaCity/", "DownloadURL.txt");
							SharedPreferences.Editor editor= getSharedPreferences("temp", MODE_WORLD_WRITEABLE).edit();
							editor.putInt("add_downloaded", 1);
							editor.commit();
						}
						if (dr == 0)
						{
							downloadsta = 1;
							Message message = new Message(); 
							message.what = 1; 
							handler.sendMessage(message);

						}
						else
						{
							downloadsta = 2;
							Message message = new Message(); 
							message.what = 1; 
							handler.sendMessage(message);

						}
					}
				}).start();
		}
		if(result==1){
			new Thread(new Runnable() {
					@Override
					public void run()
					{
						int dr=0;
						int filesta=fileutil.write(null, "/Android/data/com.China.ChinaCity/cache/image_galaxy.jpg", 2);
						if (filesta == 0)
						{
							downloaded=1;
							dr = internetutil.download("https://coding.net/u/ligongzzz/p/ChinaResources/git/raw/master/src/image_galaxy.jpg", "/Android/data/com.China.ChinaCity/cache/", "image_galaxy.jpg");
						}
						if (dr == 0)
						{
							downloadsta1 = 1;
							Message message = new Message(); 
							message.what = 1; 
							handler1.sendMessage(message);
						}
					}
				}).start();
		}
    }

	public void onClickSubHiButtonBack(View view)
	{
		finish();
	}

	public void onClickSubHiButton1(View view)
	{
		List list=new ArrayList();
		if (fileutil.write(null, "ChinaCity/DownloadURL.txt", 2) == 0)
		{
			Toast.makeText(this, "抱歉，出现了一些问题。", Toast.LENGTH_SHORT).show();
		}
		list = fileutil.read("ChinaCity/DownloadURL.txt");
		if (list == null)
		{
			Toast.makeText(this, "抱歉，出现了一些问题。", Toast.LENGTH_SHORT).show();
		}
		String DownloadURL=list.get(0).toString();
		Uri uri = Uri.parse(DownloadURL);
    	Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

	public void onClickSubHiButton2(View view)
	{
		Uri uri = Uri.parse("http://www.minecraft.net");
    	Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

	Handler handler= new Handler() { 
		public void handleMessage(Message msg)
		{ 
			if (downloadsta == 1)
			{
				Button button1=(Button)findViewById(R.id.hiButton1);
				button1.setText("下载中国城地图");
				button1.setVisibility(View.VISIBLE);
				TextView textView=(TextView)findViewById(R.id.hiTextView1);
				textView.setText("点击下面的按钮进入中国城地图下载页面:");
				ProgressBar progressBar=(ProgressBar)findViewById(R.id.hiProgressBar1);
				LinearLayout linear=(LinearLayout)findViewById(R.id.hiLinearLayout1);
				linear.removeView(progressBar);
			}
			else if (downloadsta == 2)
			{
				TextView textView=(TextView)findViewById(R.id.hiTextView1);
				textView.setText("下载地址获取失败，请检查您的网络连接。");
				ProgressBar progressBar=(ProgressBar)findViewById(R.id.hiProgressBar1);
				LinearLayout linear=(LinearLayout)findViewById(R.id.hiLinearLayout1);
				linear.removeView(progressBar);
			}
			super.handleMessage(msg); 
		}
	};
	
	Handler handler1= new Handler() { 
		public void handleMessage(Message msg)
		{ 
			if (downloadsta1 == 1)
			{
				String path = Environment.getExternalStorageDirectory().toString();//获得SDCard目录 
				Bitmap bmpDefaultPic=null;
				ImageView  iv = (ImageView)findViewById(R.id.subHiImageView1);
				if (bmpDefaultPic == null)
					bmpDefaultPic = BitmapFactory.decodeFile(path + "/Android/data/com.China.ChinaCity/cache/image_galaxy.jpg", null);
				iv.setImageBitmap(bmpDefaultPic);
				if(downloaded==1){
					Animation animation=AnimationUtils.loadAnimation(HiActivity.this, R.anim.alpha_anim_appear);
					iv.startAnimation(animation);
				}
			}
			super.handleMessage(msg); 
		}
	};
}
