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

public class MainActivity extends Activity 
{
	int titlep=0;
	int text2sta=0;

	//Download Status
	int downloadsta=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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

		//Download Files
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
	Handler handler1 = new Handler() { 
		public void handleMessage(Message msg)
		{ 
			//获取状态
			SharedPreferences sp = getSharedPreferences("temp", Activity.MODE_PRIVATE);
			int p2=1;
			int result = sp.getInt("bigtitle", p2);
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
}

/*class MyDialogFragment extends DialogFragment
{
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("关于");
		builder.setMessage("中国城 V2.1.2\n2018年1月28日\n\n更新日志：\n1、错误修复与性能改进。\n2、细节优化。\n\n©2018中国城人民政府 版权所有");
		builder.setPositiveButton("好", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					// TODO: Implement this method
				}
			});

		return builder.create();
	}

}*/
