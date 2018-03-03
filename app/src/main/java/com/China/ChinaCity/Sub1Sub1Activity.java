package com.China.ChinaCity;

import android.app.*;
import android.os.*;
import android.view.*;
import android.content.*;
import android.widget.*;
import android.graphics.*;

public class Sub1Sub1Activity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub1sub1);
    }
	public void onSub1Button1Click(View view)
	{
		Intent intent = new Intent(this, Sub1Sub3Activity.class);
		intent.putExtra("zmt", 4);
    	startActivity(intent);
	}
	public void onSub1Button2Click(View view)
	{
		Intent intent = new Intent(this, Sub1Sub3Activity.class);
		intent.putExtra("zmt", 5);
    	startActivity(intent);
	}
	public void onSub1Button3Click(View view)
	{
		Intent intent = new Intent(this, Sub1Sub3Activity.class);
		intent.putExtra("zmt", 1);
    	startActivity(intent);
	}
	public void onSub1Button4Click(View view)
	{
		Intent intent = new Intent(this, Sub1Sub3Activity.class);
		intent.putExtra("zmt", 2);
    	startActivity(intent);
	}
	public void onSub1Button5Click(View view)
	{
		Intent intent = new Intent(this, Sub1Sub3Activity.class);
		intent.putExtra("zmt", 3);
    	startActivity(intent);
	}

	public void onClickSub1ButtonGet(View view)
	{
		Intent intent = new Intent(this, HiActivity.class);
    	startActivity(intent);
	}

	public void onClickSub1Sub1ButtonBack(View view)
	{
		finish();
	}
}
