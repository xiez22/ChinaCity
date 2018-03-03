package com.China.ChinaCity;

import android.app.*;
import android.os.*;
import android.view.*;
import android.content.*;
import android.widget.*;

public class Sub1Sub3Activity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		//获取信息
		Intent intent =getIntent();
        Bundle bundle= intent.getExtras();
        int zmt=bundle.getInt("zmt");
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub1sub3);
		
		switch (zmt){
			case 1:{
					setTitle("机场区介绍");
					TextView textView1=(TextView) findViewById(R.id.sub1sub3TextView1);
					textView1.setText("中国城机场区");
					TextView textView2=(TextView) findViewById(R.id.sub1sub3TextView2);
					textView2.setText("        机场区是中国城面积最大的行政区。区域大部分为树林、海洋，建筑物占少部分，良好地保持了原有的自然风貌。\n        近年来，随着城市化的不断推进，机场区的风景也日新月异。随着G1与G2城市高速建设的推进，全区各片均有高速公路连接，形成了由主城区到达机场的快速路网。\n        中国城机场区还是中国城开发最早的片区，在地铁岛附近，您能够找到历史的痕迹。中国城最大的单体建筑机场也坐落于此，机场在中国城的交通中起着重要的枢纽作用。");
					break;
				}
			case 2:{
					setTitle("新区介绍");
					TextView textView1=(TextView) findViewById(R.id.sub1sub3TextView1);
					textView1.setText("中国城新区");
					TextView textView2=(TextView) findViewById(R.id.sub1sub3TextView2);
					textView2.setText("        新区坐落于中国城市中心的西北侧的高原上，是中国城的创新产业基地。中国城市区的最高峰——大石山坐落于此，您可以乘坐地铁9号线前往大石山顶，一览中国城万家灯火。\n        新区北部为中国城首个展览中心，其中地铁展览馆现已建成启用，外形模仿上海世博会中国馆，是中国城中一幢极具特色的建筑物。");
					break;
				}
			case 3:{
					setTitle("沙城区介绍");
					TextView textView1=(TextView) findViewById(R.id.sub1sub3TextView1);
					textView1.setText("中国城沙城区");
					TextView textView2=(TextView) findViewById(R.id.sub1sub3TextView2);
					textView2.setText("        沙城区原为沙城市，后随中国城行政区划调整并入中国城，设沙城区。沙城区是一个建立在沙漠之中的市区，生态环境良好，是中国城重要的旅游目的地。\n        从中国城市中心前往沙城，可以在新区乘坐13号线，全程仅需4分钟。");
					break;
				}
			case 4:{
					setTitle("天安门区介绍");
					TextView textView1=(TextView) findViewById(R.id.sub1sub3TextView1);
					textView1.setText("中国城天安门区");
					TextView textView2=(TextView) findViewById(R.id.sub1sub3TextView2);
					textView2.setText("        中国城天安门区是中国城人民政府所在的行政区。全区共分为西口、天安门、国贸、人民广场、镜海和芳园六个片区。著名景点有：天安门、中央电视台、镜海自然风景保护区。\n        天安门区的国贸是全市最大的CBD，在这里，鳞次栉比的高楼将带您体验到一个不一样的城市。G2城市高速还将天安门与其它片区相连，使得天安门拥有无与伦比的交通地位。");
					break;
				}
			case 5:{
					setTitle("半岛区介绍");
					TextView textView1=(TextView) findViewById(R.id.sub1sub3TextView1);
					textView1.setText("中国城半岛区");
					TextView textView2=(TextView) findViewById(R.id.sub1sub3TextView2);
					textView2.setText("        中国城半岛区是中国城面积最小的行政区，但也是城市化水平最高的行政区。已开发地区达到70%，其路网覆盖率达到100%。全区设有环形的沿江步道，其中一、二、三期工程均已完工，四期工程(大学城部分)正在规划中。建成后，将成为半岛区独有的特色风景线。\n        商贸中心是全市最为繁华的商贸场所，在商贸街道上，您将能够欣赏到宏伟的现代高楼群。半岛还是信息化发展基地，中国城首个信息控制中心便座落在半岛。");
					break;
				}
		}
    }
	
	public void onClickSub1Sub3ButtonBack(View view){
		finish();
	}
	
}
