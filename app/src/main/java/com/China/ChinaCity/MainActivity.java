package com.China.ChinaCity;

import android.app.*;
import android.os.*;
import android.view.*;
import android.content.*;
import java.util.Random;
import android.widget.*;
import android.view.animation.*;
import java.util.*;
import android.*;
import com.China.ChinaCity.Tool.*;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.*;
import android.content.pm.*;

public class MainActivity extends Activity 
{
	public int titlep=0;
	int text2sta=0;

	//Download Status
	int downloadsta=0;
	int startAtBegin=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		//Request
		requestPower();
		
		SharedPreferences sp1= getSharedPreferences("temp", Activity.MODE_PRIVATE);
		int result1 = sp1.getInt("request",0);//Internet Request
		if(result1==0)
		{
			RequestDialogFragment f = new RequestDialogFragment();
			f.show(getFragmentManager(), "mydialog");
			f.setCancelable(false);
		}
		
		//修改标题
		String[] bigtitle={"欢迎来到中国城"};
		Random random=new Random();
		int titlen=random.nextInt(bigtitle.length);
		TextView textView=(TextView) findViewById(R.id.mainTextView1);
		textView.setText(bigtitle[titlen]);
		Animation animation=AnimationUtils.loadAnimation(this, R.anim.alpha_anim_appear);
		textView.startAnimation(animation);
        titlep = titlen;

		TimerTask task=new TimerTask(){
			public void run()
			{
				Message message = new Message(); 
				message.what = 1; 
                handler.sendMessage(message);
			}
		};
		Timer timer=new Timer();
		timer.schedule(task, 6000, 6000);
		TimerTask task1=new TimerTask(){
			public void run()
			{
				Message message = new Message(); 
				message.what = 1; 
                handler1.sendMessage(message);
			}
		};
		Timer timer1=new Timer();
		timer1.schedule(task1, 6600, 6000);

		SharedPreferences sp = getSharedPreferences("temp", Activity.MODE_PRIVATE);
		int result = sp.getInt("bigtitle",0);
		//Download Files
		if (result == 1)
		{
			startAtBegin = 1;
			new Thread(new Runnable() {
					@Override
					public void run()
					{
						int dr=internetutil.download("https://coding.net/u/ligongzzz/p/ChinaResources/git/raw/master/src/bigtitle.txt", "/ChinaCity/", "BigTitleOL.txt");
						if (dr == 0)
						{
							downloadsta = 1;
						}
						else
						{
							downloadsta = 2;
						}
					}
				}).start();
		}
    }

	public void onButton1Click(View view)
	{
		Intent intent = new Intent(this, Sub1Activity.class);
    	startActivity(intent);
	}

	public void onButton2Click(View view)
	{
		Intent intent = new Intent(this, Sub2Activity.class);
    	startActivity(intent);
	}

	public void onClickButton3(View view)
	{
		Intent intent = new Intent(this, AboutActivity.class);
    	startActivity(intent);
    }


	public void onMainTextView2Click(View view)
	{
		TextView textView=(TextView) findViewById(R.id.mainTextView2);
		if (text2sta == 4)
		{
			textView.setText("中国城  Powered by Minecraft");
			text2sta = 0;
		}
        else
		{
			text2sta++;
			textView.setText("中国城");
		}
	}


	//Handlers
	public Handler handler1 = new Handler() { 
		public void handleMessage(Message msg)
		{
			//获取状态
			SharedPreferences sp = getSharedPreferences("temp", Activity.MODE_PRIVATE);
			int result = sp.getInt("bigtitle",0);
			if (result == 0)
			{
				int titlenn;
				String[] bigtitle={"富强 民主 文明 和谐","自由 平等 公正 法治","爱国 敬业 诚信 友善","中国城，始于2014","国贸片区改造竣工","人民广场购物中心试营业","人民广场焰火试运行","沿江森林公路通车","G2机场高速现已竣工","新的地铁线路网已经启用"};
				while (true)
				{
					Random random=new Random();
					titlenn = random.nextInt(bigtitle.length);
					if (titlenn != titlep)
					{
						titlep = titlenn;
						break;
					}
				}
				TextView textView=(TextView) findViewById(R.id.mainTextView1);
				textView.setText(bigtitle[titlenn]);
				Animation animation=AnimationUtils.loadAnimation(MainActivity.this, R.anim.alpha_anim_appear);
				textView.startAnimation(animation);
			}
			else if (result == 1)
			{
				List list=new ArrayList();
				if (downloadsta == 0)
				{
					list.add("更新文件中......");
					list.add("Updating the Data...");

					//Download Files
					if (startAtBegin == 0)
					{
						downloadsta = 0;
						startAtBegin=1;
						new Thread(new Runnable() {
								@Override
								public void run()
								{
									int dr=internetutil.download("https://coding.net/u/ligongzzz/p/ChinaResources/git/raw/master/src/bigtitle.txt", "/ChinaCity/", "BigTitleOL.txt");
									if (dr == 0)
									{
										downloadsta = 1;
									}
									else
									{
										downloadsta = 2;
									}
								}
							}).start();
					}
				}
				else if (downloadsta == 2)
				{
					list.add("网络连接异常");
					list.add("Connection Failed");
					//Download Files
					downloadsta = 0;
					new Thread(new Runnable() {
							@Override
							public void run()
							{
								int dr=internetutil.download("https://coding.net/u/ligongzzz/p/ChinaResources/git/raw/master/src/bigtitle.txt", "/ChinaCity/", "BigTitleOL.txt");
								if (dr == 0)
								{
									downloadsta = 1;
								}
								else
								{
									downloadsta = 2;
								}
							}
						}).start();
				}
				else
				{
					if (fileutil.write(null, "ChinaCity/BigTitleOL.txt", 2) == 0)
					{
						fileutil.write("外部大字标题已启用\n您可以手动添加外部大字标语", "ChinaCity/BigTitleOL.txt", 0);
					}
					list = fileutil.read("ChinaCity/BigTitleOL.txt");
					if (list == null)
					{
						fileutil.write("外部大字标题已启用\n您可以手动添加外部大字标语", "ChinaCity/BigTitleOL.txt", 0);
					}
					list = fileutil.read("ChinaCity/BigTitleOL.txt");
				}
				int titlenn=0;
				while (true)
				{
					Random random=new Random();
					titlenn = random.nextInt(list.size());
					if (titlenn != titlep || list.size() == 1)
					{
						titlep = titlenn;
						break;
					}
				}
				TextView textView=(TextView) findViewById(R.id.mainTextView1);
				textView.setText(list.get(titlep).toString());
				Animation animation=AnimationUtils.loadAnimation(MainActivity.this, R.anim.alpha_anim_appear);
				textView.startAnimation(animation);
			}


			super.handleMessage(msg); 
		}
	};
	
	//Save me PLZ!!!!
	public void requestPower() {
		//判断是否已经赋予权限
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
			//如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
																	Manifest.permission.WRITE_EXTERNAL_STORAGE)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
            } else {
				//申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1);
            }
        }
    }

    Handler handler= new Handler() { 
		public void handleMessage(Message msg)
		{ 
			TextView textView=(TextView) findViewById(R.id.mainTextView1);
			Animation animation=AnimationUtils.loadAnimation(MainActivity.this, R.anim.alpha_anim_disappear);
			textView.startAnimation(animation);
			super.handleMessage(msg); 
		}
	};

	Handler handler_download= new Handler() { 
		public void handleMessage(Message msg)
		{ 
		    downloadsta = 1;
			super.handleMessage(msg); 
		}
	};
	
	class RequestDialogFragment extends DialogFragment
	{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("中国城在线资源服务");
			builder.setMessage("        欢迎您来到中国城游览者导览应用!\n        本程序提供在线资源服务，该服务将会为您联网提供有关图片与信息,且该服务本身免费提供。请注意，这个功能需要网络连接，如果您正在使用移动数据上网，可能会消耗一定的流量，由此产生的费用请至运营商咨询。\n        您可以自主选择是否启用该服务。");
			builder.setPositiveButton("启用", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface p1, int p2)
					{
						// TODO: Implement this method
						SharedPreferences.Editor editor= getSharedPreferences("temp", MODE_WORLD_WRITEABLE).edit();
						editor.putInt("bigtitle",1);
						editor.putInt("request",1);
						editor.commit();
					}
				});
			builder.setNegativeButton("不启用", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface p1, int p2)
					{
						// TODO: Implement this method
						SharedPreferences.Editor editor= getSharedPreferences("temp", MODE_WORLD_WRITEABLE).edit();
						editor.putInt("bigtitle",0);
						editor.putInt("request",1);
						editor.commit();
					}
				});

			return builder.create();
		}
	}
}


