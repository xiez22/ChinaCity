package com.China.ChinaCity;

import android.app.*;
import android.os.*;
import android.view.*;
import android.content.*;
import android.net.*;
import android.widget.*;
import com.China.ChinaCity.Tool.*;
import java.util.*;
import java.io.*;
import android.graphics.*;
import android.view.animation.*;
import net.sf.json.*;

public class AboutActivity extends Activity 
{

	int downloaded=0;
	int downloadsta=0;
	int updatesta=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subabout);


		//设置开启屏控件
		Switch mSwitch=(Switch)findViewById(R.id.subaboutSwitch1);
		Switch mSwitch1=(Switch)findViewById(R.id.subaboutSwitch2);
		Switch mSwitch3=(Switch)findViewById(R.id.subaboutSwitch3);
		SharedPreferences sp = getSharedPreferences("temp", Activity.MODE_PRIVATE);
		int p2=0;
		int result = sp.getInt("splashscreen", p2);
		if (result == 0)
		{
			mSwitch.setChecked(true);
		}
		else if (result == 1)
		{
			mSwitch.setChecked(false);
		}
		p2 = 0;
		SharedPreferences sp1 = getSharedPreferences("temp", Activity.MODE_PRIVATE);
		int result1 = sp1.getInt("bigtitle", p2);
		if (result1 == 0)
		{
			mSwitch1.setChecked(false);
			LinearLayout linear=(LinearLayout)findViewById(R.id.subaboutLinearLayout1);
			ImageView iv=(ImageView)findViewById(R.id.subaboutImageView1);
			linear.removeView(iv);
		}
		else if (result1 == 1)
		{
			mSwitch1.setChecked(true);
		}
		sp1 = getSharedPreferences("temp", Activity.MODE_PRIVATE);
		int result2 = sp1.getInt("autoChange",1);
		if (result2== 0)
		{
			mSwitch3.setChecked(false);
		}
		else if (result2 == 1)
		{
			mSwitch3.setChecked(true);
		}
		//Download
		if (result1 == 1)
		{
			new Thread(new Runnable() {
					@Override
					public void run()
					{
						int dr=0;
						int filesta=fileutil.write(null, "/Android/data/com.China.ChinaCity/cache/image_bilibili.jpg", 2);
						if (filesta == 0)
						{
							downloaded = 1;
							dr = internetutil.download("https://coding.net/u/ligongzzz/p/ChinaResources/git/raw/master/src/image_bilibili.jpg", "/Android/data/com.China.ChinaCity/cache/", "image_bilibili.jpg");
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

		mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
				{
                    if (isChecked)
					{
						//选中的处理
						//Toast.makeText(AboutActivity.this, "On", Toast.LENGTH_SHORT).show();
						SharedPreferences.Editor editor= getSharedPreferences("temp", MODE_WORLD_WRITEABLE).edit();
						editor.putInt("splashscreen", 0);
						editor.commit();
					} 
					else
					{
						//没有选中的处理
						//Toast.makeText(AboutActivity.this, "Off", Toast.LENGTH_SHORT).show();
						SharedPreferences.Editor editor= getSharedPreferences("temp", MODE_WORLD_WRITEABLE).edit();
						editor.putInt("splashscreen", 1);
						editor.commit();
					}

				}
			});

		mSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
				{
					if (isChecked)
					{
						//选中的处理
						SharedPreferences.Editor editor= getSharedPreferences("temp", MODE_WORLD_WRITEABLE).edit();
						editor.putInt("bigtitle", 1);
						editor.commit();

						if (fileutil.write(null, "ChinaCity/BigTitleOL.txt", 2) == 0)
						{
							fileutil.write("请重启应用来获取在线内容", "ChinaCity/BigTitleOL.txt", 0);
						}
						AboutDialogFragment f = new AboutDialogFragment();
						f.show(getFragmentManager(), "mydialog");
						//Toast.makeText(AboutActivity.this, "This activity has been killed due to some unknown errors.(Thank you for touching off this bug written by the UP on purpose.You can have a look at the code on GitHub.com for more bugs and pull it to the UP.)", Toast.LENGTH_LONG).show();
					} 
					else
					{
						//没有选中的处理
						//Toast.makeText(AboutActivity.this, "已关闭在线资源服务。", Toast.LENGTH_SHORT).show();
						SharedPreferences.Editor editor= getSharedPreferences("temp", MODE_WORLD_WRITEABLE).edit();
						editor.putInt("bigtitle", 0);
						editor.commit();
					}

				}
			});
		mSwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
				{
					if (isChecked)
					{
						//选中的处理
						SharedPreferences.Editor editor= getSharedPreferences("temp", MODE_WORLD_WRITEABLE).edit();
						editor.putInt("autoChange", 1);
						editor.commit();
						//Toast.makeText(AboutActivity.this, "This activity has been killed due to some unknown errors.(Thank you for touching off this bug written by the UP on purpose.You can have a look at the code on GitHub.com for more bugs and pull it to the UP.)", Toast.LENGTH_LONG).show();
					} 
					else
					{
						//没有选中的处理
						//Toast.makeText(AboutActivity.this, "已关闭在线资源服务。", Toast.LENGTH_SHORT).show();
						SharedPreferences.Editor editor= getSharedPreferences("temp", MODE_WORLD_WRITEABLE).edit();
						editor.putInt("autoChange", 0);
						editor.commit();
					}

				}
			});
    }

	public void onClickSubAboutButton1(View view)
	{
		//Update Check
		new Thread(new Runnable() {
				@Override
				public void run()
				{
					int dr=0;
					dr = internetutil.download("https://coding.net/u/ligongzzz/p/ChinaResources/git/raw/master/src/information.json", "/Android/data/com.China.ChinaCity/cache/", "information.json");
					if (dr == 0)
					{
						try
						{
							JSONObject myjson=JSONObject.fromObject(fileutil.read("/Android/data/com.China.ChinaCity/cache/information.json").get(0).toString());
							String result=getResources().getString(R.string.version);
							if (result.equals(myjson.getString("version")))
							{
								updatesta = 2;
								Looper.prepare();
								Toast.makeText(AboutActivity.this,"已经是最新版本!",Toast.LENGTH_SHORT).show();
								Looper.loop();
							}
							else
							{
								updatesta = 1;
				                UpdateDialogFragment f = new UpdateDialogFragment();
								f.show(getFragmentManager(), "mydialog");
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
							updatesta = 0;
							Looper.prepare();
							Toast.makeText(AboutActivity.this,"未知的错误:"+e.toString(),Toast.LENGTH_SHORT).show();
							Looper.loop();
						}
					}
					else
					{
						Looper.prepare();
						Toast.makeText(AboutActivity.this,"检查更新失败，请检查你的网络连接",Toast.LENGTH_SHORT).show();
						Looper.loop();
					}
				}
			}).start();
	}
	public void onClickSubAboutButton2(View view)
	{
		Uri uri = Uri.parse("https://github.com/ligongzzz/ChinaCity");
    	Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}
	public void onClickSubAboutButtonWatch(View view)
	{
		Uri uri = Uri.parse("https://github.com/ligongzzz/ChinaCity/blob/master/README.md");
    	Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}
	public void onClickSubAboutButtonBilibili(View view)
	{
		Uri uri = Uri.parse("https://space.bilibili.com/14116912/#/");
    	Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}
	public void onClickSubAboutButtonShare(View view)
	{
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, "强烈推荐中国城官方导览应用!下载地址:https://github.com/ligongzzz/ChinaCity/blob/master/app/build/bin/app.apk?raw=true。");
		startActivity(intent);
	}
	public void onClickSubAboutButtonBack(View view)
	{
		finish();
	}
	public void onClickSubAboutButtonClean(View view)
	{
		fileutil.deleteDir(this.getExternalCacheDir().toString());
		Toast.makeText(this, "已经清除缓存!", Toast.LENGTH_SHORT).show();
	}

	Handler handler= new Handler() { 
		public void handleMessage(Message msg)
		{ 
			if (downloadsta == 1)
			{
				String path = Environment.getExternalStorageDirectory().toString();//获得SDCard目录 
				Bitmap bmpDefaultPic=null;
				ImageView  iv = (ImageView)findViewById(R.id.subaboutImageView1);
				if (bmpDefaultPic == null)
					bmpDefaultPic = BitmapFactory.decodeFile(path + "/Android/data/com.China.ChinaCity/cache/image_bilibili.jpg", null);
				iv.setImageBitmap(bmpDefaultPic);
				if (downloaded == 1)
				{
					Animation animation=AnimationUtils.loadAnimation(AboutActivity.this, R.anim.alpha_anim_appear);
					iv.startAnimation(animation);
				}
			}
			else
			{
				LinearLayout linear=(LinearLayout)findViewById(R.id.subaboutLinearLayout1);
				ImageView iv=(ImageView)findViewById(R.id.subaboutImageView1);
				linear.removeView(iv);
			}
			super.handleMessage(msg); 
		}
	};

	class UpdateDialogFragment extends DialogFragment
	{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			try{
			final JSONObject myjson=JSONObject.fromObject(fileutil.read("/Android/data/com.China.ChinaCity/cache/information.json").get(0).toString());
				builder.setTitle("中国城"+myjson.getString("version"));
				builder.setMessage(myjson.getString("detail"));
				builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface p1, int p2)
						{
							// TODO: Implement this method
							Uri uri = Uri.parse(myjson.getString("URL"));
							Intent intent = new Intent(Intent.ACTION_VIEW, uri);
							startActivity(intent);
						}
					});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface p1, int p2)
						{
							// TODO: Implement this method
						}
					});
			}
			catch(Exception e){
				e.printStackTrace();
				updatesta = 0;
				Toast.makeText(AboutActivity.this,"未知的错误:"+e.toString(),Toast.LENGTH_SHORT).show();
			}
			

			return builder.create();
		}
	}
}

class AboutDialogFragment extends DialogFragment
{
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("已启用在线资源服务");
		builder.setMessage("在线资源服务已启用。请注意，这个功能需要网络连接，如果您正在使用移动数据上网，可能会消耗一定的流量，由此产生的费用请至运营商咨询。");
		builder.setPositiveButton("好", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					// TODO: Implement this method
				}
			});

		return builder.create();
	}
}
