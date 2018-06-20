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
import java.util.*;


public class NewsSub1Activity extends Activity 
{
	protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subnews_sub1);
		
		//获取信息
		Intent intent =getIntent();
        Bundle bundle= intent.getExtras();
		
		TextView textView1=(TextView)findViewById(R.id.subnewssub1TextView1);
		TextView textView2=(TextView)findViewById(R.id.subnewssub1TextView2);
		TextView textView3=(TextView)findViewById(R.id.subnewssub1TextView3);
		
		textView1.setText(bundle.getString("t1"));
		textView2.setText(bundle.getString("t2"));
		textView3.setText(bundle.getString("t3"));
		
	}
	
	public void onClickSubNewsSub1ButtonBack(View view)
	{
		finish();
	}
}
