package com.China.ChinaCity;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;
import com.China.ChinaCity.Tool.*;
import net.sf.json.*;

public class Sub1Activity extends Activity 
{

	int downloadsta=0;
	int downloaded=0;
	int online=0;
	int noticed=0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub1);
		
		//Start Internet Connection
		SharedPreferences sp = getSharedPreferences("temp", Activity.MODE_PRIVATE);
		int result = sp.getInt("bigtitle",0);
		online=result;
		noticed=sp.getInt("noticed",0);
		if (result == 1)
		{
			new Thread(new Runnable() {
					@Override
					public void run()
					{
						int dr=0;int dr1=0;int dr2=0;int dr3=0;
						int filesta=fileutil.write(null, "/Android/data/com.China.ChinaCity/cache/image_map.jpg", 2);
						int filesta1=fileutil.write(null, "/Android/data/com.China.ChinaCity/cache/image_area.jpg", 2);
						int filesta2=fileutil.write(null, "/Android/data/com.China.ChinaCity/cache/image_gallery.jpg", 2);
						int filesta3=fileutil.write(null, "/Android/data/com.China.ChinaCity/cache/image_news.jpg", 2);
						
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
						if (filesta2== 0)
						{
							downloaded=1;
							dr2 = internetutil.download("https://coding.net/u/ligongzzz/p/ChinaResources/git/raw/master/src/image_gallery.jpg", "/Android/data/com.China.ChinaCity/cache/", "image_gallery.jpg");
						}
						if (filesta3== 0)
						{
							downloaded=1;
							dr3 = internetutil.download("https://coding.net/u/ligongzzz/p/ChinaResources/git/raw/master/src/image_news.jpg", "/Android/data/com.China.ChinaCity/cache/", "image_news.jpg");
						}
						if (dr == 0 && dr1 == 0&&dr2==0&&dr3==0)
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
			TextView textView3=(TextView)findViewById(R.id.sub1TextView3);
			textView3.setText("需要开启在线资源服务以显示图片");
			TextView textView4=(TextView)findViewById(R.id.sub1TextView4);
			textView4.setText("需要开启在线资源服务以显示图片");
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
	
	public void onClickSub1ButtonGallery(View view)
	{
		if(noticed==0){
			NoticeDialogFragment f = new NoticeDialogFragment();
			f.show(getFragmentManager(), "mydialog");
		}
		else{
		Intent intent = new Intent(this, GalleryActivity.class);
    	startActivity(intent);
		}
	}
	
	public void onClickSub1ButtonNews(View view)
	{
		//Toast.makeText(this,"该功能尚未开发完成，敬请期待！",Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this, NewsActivity.class);
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
				Bitmap bmpDefaultPic2=null;
				ImageView  iv2 = (ImageView)findViewById(R.id.sub1ImageView3);
				if (bmpDefaultPic2 == null)
					bmpDefaultPic2 = BitmapFactory.decodeFile(path + "/Android/data/com.China.ChinaCity/cache/image_gallery.jpg", null);
				iv2.setImageBitmap(bmpDefaultPic2);
				Bitmap bmpDefaultPic3=null;
				ImageView  iv3 = (ImageView)findViewById(R.id.sub1ImageView4);
				if (bmpDefaultPic3 == null)
					bmpDefaultPic3 = BitmapFactory.decodeFile(path + "/Android/data/com.China.ChinaCity/cache/image_news.jpg", null);
				iv3.setImageBitmap(bmpDefaultPic3);
				
				if(downloaded==1){
				Animation animation=AnimationUtils.loadAnimation(Sub1Activity.this, R.anim.alpha_anim_appear);
				iv.startAnimation(animation);
				iv1.startAnimation(animation);
				iv2.startAnimation(animation);
				iv3.startAnimation(animation);
				}
			}
			super.handleMessage(msg); 
		}
	};
	
	class NoticeDialogFragment extends DialogFragment
	{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("流量消耗红色预警");
			builder.setMessage("即将加载大量的高清图片。请注意，这个功能需要网络连接，如果您正在使用移动数据上网，可能会消耗大量的流量，由此产生的费用请至运营商咨询。");
			builder.setPositiveButton("进入", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						// TODO: Implement this method
						SharedPreferences.Editor editor= getSharedPreferences("temp", MODE_WORLD_WRITEABLE).edit();
						editor.putInt("noticed",1);
						editor.commit();
						noticed=1;
						Intent intent = new Intent(Sub1Activity.this, GalleryActivity.class);
						startActivity(intent);
					}
				});
			builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						// TODO: Implement this method
					}
				});

			return builder.create();
		}
   }
}


