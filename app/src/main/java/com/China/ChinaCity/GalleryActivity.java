package com.China.ChinaCity;

import android.app.*;
import android.os.*;
import android.view.*;
import android.content.*;
import android.widget.*;
import com.China.ChinaCity.Tool.*;
import java.util.*;
import android.view.animation.*;
import android.graphics.*;

public class GalleryActivity extends Activity 
{
	//Constant
	static int MAX_SIZE=50;
	static int FREQUENCY=100;
	static int MAX_TIME=60;

	int downloadsta=0;
	int downloadedn=0;
	int status=0;
	int datan=0;
	int currentlist=0;
	int progressRemoved=0;
	int downloadingData=0;
	Timer timer=null;

	galleryData data[]=new galleryData[MAX_SIZE];

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subgallery);

		//init
		for (int i=0;i < MAX_SIZE;i++)
			data[i] = new galleryData();

		//Download Files
		SharedPreferences sp = getSharedPreferences("temp", Activity.MODE_PRIVATE);
		int result = sp.getInt("bigtitle",0);
		if (result == 1)
		{
			downloadAll();

			//Begin Timer
			TimerTask task=new TimerTask(){
				public void run()
				{
					if (status > MAX_TIME)
					{
						status = 0;
						if (currentlist < downloadedn - 1)
							currentlist++;
						else
							currentlist = 0;
					}
					if (downloadedn >= 1)
					{
						Message message = new Message(); 
						message.what = 1; 
						handler.sendMessage(message);
					}
				}
			};
			timer = new Timer();
			timer.schedule(task, FREQUENCY, FREQUENCY);
		}
        else
		{
			downloadsta = 3;
			Message message = new Message(); 
			handlerException.sendMessage(message);
		}

    }

	//点击
	public void onClickSubGalleryGoto(View view)
	{
		if (downloadsta == 1 && downloadedn >= 1 && status < MAX_TIME - 9)
		{
			status = MAX_TIME - 9;
		}
	}

	public void onClickSubGalleryButtonBack(View view)
	{
		finish();
	}

	//Handlers
	Handler handler= new Handler() { 
		public void handleMessage(Message msg)
		{ 
			if (status == 0)
			{
				TextView textView=(TextView) findViewById(R.id.subgalleryTextView1);
				textView.setText("");
				TextView textView1=(TextView) findViewById(R.id.subgalleryTextView2);
				textView1.setText("");
				String path = Environment.getExternalStorageDirectory().toString();//获得SDCard目录 
				Bitmap bmpDefaultPic=null;
				ImageView  iv = (ImageView)findViewById(R.id.subgalleryImageView1);
				if (bmpDefaultPic == null)
					bmpDefaultPic = BitmapFactory.decodeFile(path + "/Android/data/com.China.ChinaCity/cache/gallery/" + data[currentlist].address, null);

				//异常处理
				if (bmpDefaultPic == null)
				{
					fileutil.write(null, "Android/data/com.China.ChinaCity/cache/gallery/" + data[currentlist].address, 3);
					//返回
					downloadsta = 0;
					downloadedn = 0;
					downloadAll();
					status = 0;
					currentlist = 0;
				}
				else
				{
					iv.setImageBitmap(bmpDefaultPic);
					Animation animation=AnimationUtils.loadAnimation(GalleryActivity.this, R.anim.alpha_anim_appear);
					iv.startAnimation(animation);

					if (progressRemoved == 0)
					{
						progressRemoved = 1;
						RelativeLayout linear=(RelativeLayout) findViewById(R.id.subgalleryRelativeLayout1);
						ProgressBar progress=(ProgressBar) findViewById(R.id.subgalleryProgressBar1);
						TextView textView2=(TextView) findViewById(R.id.subgalleryTextView3);
						linear.removeView(progress);
						linear.removeView(textView2);
					}
				}
			}
			else if (status == 3)
			{
				TextView textView=(TextView) findViewById(R.id.subgalleryTextView1);
				textView.setText(data[currentlist].bigTitle);
				Animation animation=AnimationUtils.loadAnimation(GalleryActivity.this, R.anim.alpha_anim_appear);
				textView.startAnimation(animation);
			}
			else if (status == 5)
			{
				TextView textView=(TextView) findViewById(R.id.subgalleryTextView2);
				textView.setText(data[currentlist].smallTitle);
				Animation animation=AnimationUtils.loadAnimation(GalleryActivity.this, R.anim.alpha_anim_appear);
				textView.startAnimation(animation);
			}
			else if (status == MAX_TIME - 9)
			{
				TextView textView=(TextView) findViewById(R.id.subgalleryTextView2);
				Animation animation=AnimationUtils.loadAnimation(GalleryActivity.this, R.anim.alpha_anim_disappear);
				textView.startAnimation(animation);
			}
			else if (status == MAX_TIME - 7)
			{
				TextView textView=(TextView) findViewById(R.id.subgalleryTextView1);
				Animation animation=AnimationUtils.loadAnimation(GalleryActivity.this, R.anim.alpha_anim_disappear);
				textView.startAnimation(animation);
			}
			else if (status == MAX_TIME - 5)
			{
				ImageView textView=(ImageView) findViewById(R.id.subgalleryImageView1);
				Animation animation=AnimationUtils.loadAnimation(GalleryActivity.this, R.anim.alpha_anim_disappear);
				textView.startAnimation(animation);
			}
			else if (status == MAX_TIME - 4)
			{
				TextView textView=(TextView) findViewById(R.id.subgalleryTextView2);
				textView.setText("");
			}
			else if (status == MAX_TIME - 2)
			{
				TextView textView=(TextView) findViewById(R.id.subgalleryTextView1);
				textView.setText("");
			}
            status++;
			super.handleMessage(msg); 
		}
	};

	Handler handlerException= new Handler() { 
		public void handleMessage(Message msg)
		{ 
		    if (progressRemoved == 0)
			{
				progressRemoved = 1;
				RelativeLayout linear=(RelativeLayout) findViewById(R.id.subgalleryRelativeLayout1);
				ProgressBar progress=(ProgressBar) findViewById(R.id.subgalleryProgressBar1);
				TextView textView=(TextView) findViewById(R.id.subgalleryTextView3);
				linear.removeView(progress);
				linear.removeView(textView);
			}
			if (downloadsta == 2)
			{
				TextView textView=(TextView) findViewById(R.id.subgalleryTextView1);
				textView.setText("需要网络连接");
				TextView textView1=(TextView) findViewById(R.id.subgalleryTextView2);
				textView1.setText("无法连接至互联网，请连接网络以欣赏图片。");
			}
			else if (downloadsta == 3)
			{
				TextView textView=(TextView) findViewById(R.id.subgalleryTextView1);
				textView.setText("需要开启在线资源服务");
				TextView textView1=(TextView) findViewById(R.id.subgalleryTextView2);
				textView1.setText("请在设置中开启在线资源服务以欣赏在线图片。");
			}
			if (timer != null)
			{
				timer.cancel();
				timer = null;
			}
			super.handleMessage(msg); 
		}
	};

	@Override
	protected void onDestroy()
	{
		// TODO: Implement this method
		if (timer != null)
		{
			timer.cancel();
			timer = null;
		}
		super.onDestroy();
	}

	//Data Class
	public class galleryData
	{
		public String imageURL;
		public String address;
		public String bigTitle;
		public String smallTitle;

		galleryData()
		{
			imageURL = null;
			address = null;
			bigTitle = null;
			smallTitle = null;
		}
	}

	public void downloadAll()
	{
		new Thread(new Runnable() {
				public void run()
				{
					try{
					int dr=0;
					dr = internetutil.download("https://coding.net/u/ligongzzz/p/ChinaResources/git/raw/master/src/galleryInfo.txt", "/Android/data/com.China.ChinaCity/cache/", "galleryInfo.txt");
					//Save It
					if (dr == 1)
					{
						if (fileutil.write(null, "Android/data/com.China.ChinaCity/cache/galleryInfo.txt", 2) == 1)
							dr = 0;
					}
					if (dr == 0)
					{
						downloadsta = 1;
						//Analysis
						List list=fileutil.read("Android/data/com.China.ChinaCity/cache/galleryInfo.txt");
						for (int a=0,b=0;a < list.size();b++,a++)
						{
							if (b == 4)
							{
								b = 0;
							}
							if (b == 0)
							{
								datan += 1;
								data[datan - 1].imageURL = list.get(a).toString();

							}
							else if (b == 1)
							{
								data[datan - 1].address = list.get(a).toString();
							}
							else if (b == 2)
							{
								data[datan - 1].bigTitle = list.get(a).toString();
							}
							else if (b == 3)
							{
								data[datan - 1].smallTitle = list.get(a).toString();
							}
						}

						//Create New Downloads
						for (int i=0;i < datan;i++)
						{
							String addr=data[i].imageURL;
							if (fileutil.write(null, "Android/data/com.China.ChinaCity/cache/gallery/" + data[i].address, 2) == 0)
							{
								dr = internetutil.download(addr, "/Android/data/com.China.ChinaCity/cache/gallery/", data[i].address);
								if (dr != 0)
									break;
							}
							//Error Files
						    else
							{
								String path = Environment.getExternalStorageDirectory().toString();//获得SDCard目录 
								Bitmap bmpDefaultPic=null;
								if (bmpDefaultPic == null)
									bmpDefaultPic = BitmapFactory.decodeFile(path + "/Android/data/com.China.ChinaCity/cache/gallery/" + data[i].address, null);
								if (bmpDefaultPic == null)
								{
									fileutil.write(null, "Android/data/com.China.ChinaCity/cache/gallery/" + data[i].address, 3);
									i--;
									downloadedn--;
								}
							}

							downloadedn++;
						}
						if (dr != 0)
						{
							downloadsta = 2;
							Message message = new Message(); 
							handlerException.sendMessage(message);
						}
					}
					else
					{
						downloadsta = 2;
						Message message = new Message(); 
						handlerException.sendMessage(message);
					}
					}
					catch(Exception e){
						e.printStackTrace();
						downloadsta = 2;
						Message message = new Message(); 
						handlerException.sendMessage(message);
					}
				}
			}).start();
	}
}
