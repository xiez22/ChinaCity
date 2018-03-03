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

public class AboutActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subabout);
		

		//设置开启屏控件
		Switch mSwitch=(Switch)findViewById(R.id.subaboutSwitch1);
		Switch mSwitch1=(Switch)findViewById(R.id.subaboutSwitch2);
		SharedPreferences sp = getSharedPreferences("temp", Activity.MODE_PRIVATE);
		int p2=0;
		int result = sp.getInt("splashscreen",p2);
		if(result==0){
         mSwitch.setChecked(true);
		}
		else if(result==1){
	     mSwitch.setChecked(false);
		}
		p2=1;
		SharedPreferences sp1 = getSharedPreferences("temp", Activity.MODE_PRIVATE);
		int result1 = sp1.getInt("bigtitle",p2);
		if(result1==0){
			mSwitch1.setChecked(false);
		}
		else if(result1==1){
			mSwitch1.setChecked(true);
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
						editor.putInt("splashscreen",0);
						editor.commit();
					} 
					else
					{
						//没有选中的处理
						//Toast.makeText(AboutActivity.this, "Off", Toast.LENGTH_SHORT).show();
						SharedPreferences.Editor editor= getSharedPreferences("temp", MODE_WORLD_WRITEABLE).edit();
						editor.putInt("splashscreen",1);
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
			//List list=fileutil.read("ChinaCity/bug.txt");
			//for(int i=0;list.get(i)!=null;i++){
			//String temp=list.get(0).toString();
			//Toast.makeText(AboutActivity.this,temp,Toast.LENGTH_SHORT).show();
			SharedPreferences.Editor editor= getSharedPreferences("temp", MODE_WORLD_WRITEABLE).edit();
			editor.putInt("bigtitle",1);
			editor.commit();
			
			if(fileutil.write(null,"ChinaCity/BigTitleOL.txt",2)==0){
				fileutil.write("请重启应用来获取在线内容","ChinaCity/BigTitleOL.txt",0);
			}
			AboutDialogFragment f = new AboutDialogFragment();
			f.show(getFragmentManager(), "mydialog");
			//Toast.makeText(AboutActivity.this, "This activity has been killed due to some unknown errors.(Thank you for touching off this bug written by the UP on purpose.You can have a look at the code on GitHub.com for more bugs and pull it to the UP.)", Toast.LENGTH_LONG).show();
		} 
		else
		{
			//没有选中的处理
			Toast.makeText(AboutActivity.this, "已关闭在线资源服务。", Toast.LENGTH_SHORT).show();
			SharedPreferences.Editor editor= getSharedPreferences("temp", MODE_WORLD_WRITEABLE).edit();
			editor.putInt("bigtitle",0);
			editor.commit();
		}
		
	}
	});
    }

	public void onClickSubAboutButton1(View view)
	{
		Uri uri = Uri.parse("https://github.com/ligongzzz/ChinaCity/blob/master/app/build/bin/app.apk?raw=true");
    	Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
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
	public void onClickSubAboutButtonShare(View view)
	{
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT,"强烈推荐中国城官方导览应用!下载地址:https://github.com/ligongzzz/ChinaCity/blob/master/app/build/bin/app.apk?raw=true。");
		startActivity(intent);
	}
	public void onClickSubAboutButtonBack(View view)
	{
		finish();
	}
	public void onClickSubAboutButtonClean(View view)
	{
		fileutil.deleteDir(this.getExternalCacheDir().toString());
		Toast.makeText(this,"已经清除缓存!",Toast.LENGTH_SHORT).show();
	}
}

class AboutDialogFragment extends DialogFragment
{
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("已启用在线资源服务");
		builder.setMessage("在线资源服务已启用。请注意，这个功能需要网络连接，如果您正在使用移动数据上网，可能会消耗极少的流量，由此产生的费用请至运营商咨询。");
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
