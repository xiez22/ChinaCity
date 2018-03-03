package com.China.ChinaCity.Line;
import java.math.*;

public class LineSearch
{
	//整形初始化
	public void init(int[] shuzu, int num,int value) {
		for (int i = 0; i < num; i++)
			shuzu[i] = value;
	}
	//浮点型初始化
	public void initd(double[] shuzu, int num,double value) {
		for (int i = 0; i < num; i++)
			shuzu[i] = value;
	}

	public String[][] destination=new String[100][2];//线路，方向，站名
	
	//有向图车站列表
	public newstation[] nslist=new newstation[200];

    // 车站列表
	public station[] slist=new station[100];
	
	// 路线大集合
	public passenger[] ans=new passenger[10];
	
	public int create_sta(station[] slist, double sx, double sy,String name) {
		int n = slist[0].num+1;
		slist[n].num = n;
		slist[n].sx = sx;
		slist[n].sy = sy;
		slist[n].name=name;
		slist[0].num = n;
		return n;
	}
	
	// 线路大集合
	public int[][] line=new int[100][100];

    //有向图：S集与U集
	public int[] sji=new int[200];
	public int[] uji=new int[200];
	
	//------------------------------
	
	//算法寻找路线
	public int dsearch(passenger[] ans,int start,int end){
		//是否抵达
		if (start==end){

			//写入数据
			//倒序数组
			int[] temp=new int[200];
			temp[0]=1;
			temp[1]=end;
			for (;;){
				//加入数组
				int ci=++temp[0];
				temp[ci]=nslist[temp[ci-1]].shortcur;
				if (nslist[temp[ci]].shortcur==0)
					break;
			}
			//写入ans集合
			ans[1].st[0]=nslist[temp[temp[0]]].origin;
			ans[1].wl[0]=0;
			ans[1].time=nslist[end].time+20.0;
			for (int i=temp[0]-1;i>0;i--){
				//判断是否改变了车站
				if (nslist[temp[i]].origin!=nslist[temp[i+1]].origin){
					//将该车站写入
					ans[1].st[++ans[1].wl[0]]=nslist[temp[i]].origin;
					ans[1].wl[ans[1].wl[0]]=nslist[temp[i]].line;
					//判断隧道信息
					for (int m=1;m<=nslist[temp[i+1]].tunnelsta[0];m++){
						if (nslist[temp[i+1]].tunnelsta[m]==temp[i]){
							ans[1].ds[ans[1].wl[0]]=nslist[temp[i+1]].tunnelline[m];
							break;
						}
					}
				}
			}
			return 0;
		}
		sji[++sji[0]]=start;
		uji[start]=0;
		//修改里程
		for (int i=1;i<=nslist[start].tunnelsta[0];i++){
			double deltat=nslist[start].tunneltime[i];
			if (deltat+nslist[start].time<nslist[nslist[start].tunnelsta[i]].time){
				//修改里程
				nslist[nslist[start].tunnelsta[i]].time=deltat+nslist[start].time;//修改父节点
				nslist[nslist[start].tunnelsta[i]].shortcur=start;
			}
		}
		int smallpoint=0;
//寻找最小U值
		for (int i=1;i<=uji[0];i++){
			if ((smallpoint==0||nslist[i].time<nslist[smallpoint].time)&&uji[i]!=0){
				int q=uji[0];
				smallpoint=i;
			}
		}
//递归
		dsearch(ans,smallpoint,end);
		return 0;
	}

	public void dsearchnew(passenger[] ans,int start,int end){
		for (int i=0;i<=slist[0].num;i++){
			init(slist[i].newsta,20,0);
		}
		for (int i=0;i<200;i++){
			nslist[i].num=0;
			nslist[i].time=99999.0;
			nslist[i].line=0;
			nslist[i].shortcur=0;
			nslist[i].origin=0;
			init(nslist[i].tunnelsta,20,0);
			init(nslist[i].tunnelline,20,0);
			initd(nslist[i].tunneltime,20,0.0);
		}
		for (int i=0;i<10;i++){
			ans[i].num=0;
			ans[i].time=0.0;
			init(ans[i].wl,20,0);
			init(ans[i].st,20,0);
			init(ans[i].ds,20,0);
		}
		//先列举
		for (int i=1;i<=line[0][0];i++){
			for (int j=1;j<=line[i][0];j++){
				int num=++nslist[0].num;
				nslist[num].num=num;
				nslist[num].origin=line[i][j];
				nslist[num].line=i;
				slist[line[i][j]].newsta[++slist[line[i][j]].newsta[0]]=num;
				if (j>1){
					nslist[num].tunnelsta[++nslist[num].tunnelsta[0]]=num-1;
					nslist[num].tunnelline[nslist[num].tunnelsta[0]]=0;
					int m=line[i][j];
					double d= Math.sqrt(Math.pow(slist[line[i][j-1]].sx -slist[line[i][j]].sx,2.0)+Math.pow(slist[line[i][j-1]].sy -slist[line[i][j]].sy, 2.0));
					nslist[num].tunneltime[nslist[num].tunnelsta[0]]=d/8.0+5.0;
				}
				if (j<line[i][0]){
					nslist[num].tunnelsta[++nslist[num].tunnelsta[0]]=num+1;
					nslist[num].tunnelline[nslist[num].tunnelsta[0]]=1;
					int m=line[i][j];
					double d= Math.sqrt(Math.pow(slist[line[i][j+1]].sx -slist[line[i][j]].sx,2.0)+Math.pow(slist[line[i][j+1]].sy -slist[line[i][j]].sy, 2.0));
					nslist[num].tunneltime[nslist[num].tunnelsta[0]]=d/8.0+5.0;
				}
			}
		}
		//建立换乘通道
		for (int i=1;i<=nslist[0].num;i++){
			for (int j=1;j<=slist[nslist[i].origin].newsta[0];j++){
				if (slist[nslist[i].origin].newsta[j]!=i){
					nslist[i].tunnelsta[++nslist[i].tunnelsta[0]]=slist[nslist[i].origin].newsta[j];
					nslist[i].tunneltime[nslist[i].tunnelsta[0]]=15.0;
					nslist[i].tunnelline[nslist[i].tunnelsta[0]]=-1;
//特殊情况
					if (start==nslist[i].origin||end==nslist[i].origin){
						nslist[i].tunneltime[nslist[i].tunnelsta[0]]=0.0;
					}
				}
			}
		}
//初始化集合
		init(sji,200,0);
		for (int i=0;i<200;i++){
			uji[i]=i;
		}
		uji[0]=199;
//初始化起点站时间
		nslist[slist[start].newsta[1]].time=0.0;
//调用函数
		dsearch(ans,slist[start].newsta[1],slist[end].newsta[1]);
	}
	
//Find Station
	public int FindStation(String sta){
		for (int i=1;i<=slist[0].num;i++){
			if (slist[i].name.equals(sta))
				return i;
		}
		return 0;
	}
	
//-------------------------------
	
	public void Search(int startn,int endn){
		dsearchnew(ans,startn,endn);
		dsearchnew(ans,startn,endn);
	}
	
	public LineSearch(){
		//init
		for(int i=0;i<200;i++)
			nslist[i]=new newstation();
		for(int i=0;i<10;i++)
			ans[i]=new passenger();
		for(int i=0;i<100;i++)
			slist[i]=new station();
		}
		// 初始化车站
		/*slist[0].num = 0;
		ans[0].wl[0]=0;
		ans[0].st[0]=0;*/


		// 线路与车站
		//车站
	public void initlog(){
		create_sta(slist, -480.0, 899.0, "天安门");
		create_sta(slist, -571.0, 1020.0, "半岛");
		create_sta(slist, -639.0, 1117.0, "人民广场");
		create_sta(slist, -496.0, 1245.0, "大石山脚");
		create_sta(slist, -330.0, 1245.0, "新区");
		create_sta(slist, -187.0, 1097.0, "森林公园");
		create_sta(slist, -16.0, 567.0, "迷雾山");
		create_sta(slist, 130.0, 433.0, "购物中心");
		create_sta(slist, 17.0, 136.0, "沙湾");
		create_sta(slist, 17.0, 31.0, "地铁岛");//10
		create_sta(slist, -158.0, 17.0, "机场");
		create_sta(slist, -264.0, 63.0, "T2航站楼");
		create_sta(slist, -62.0, 6.0, "好望角");
		create_sta(slist, -650.0, 937.0, "商贸中心");
		create_sta(slist, -668.0, 787.0, "芳园");
		create_sta(slist, -464.0, 583.0, "海湾");
		create_sta(slist, -378.0, 923.0, "西口");
		create_sta(slist, -539.0, 891.0, "天安门东");
		create_sta(slist, -750.0, 931.0, "艺林");
		create_sta(slist, -736.0, 1023.0, "和平路");//20
		create_sta(slist, -476.0, 1072.0, "国贸");
		create_sta(slist, -375.0, 1312.0, "大石山顶");
		create_sta(slist, -526.0, 785.0, "银桥");
		create_sta(slist, -860.0, 1187.0, "镜湖");
		create_sta(slist, -1094.0, 1245.0, "镜海");
		create_sta(slist, -384.0, 1429.0, "展览城");
		create_sta(slist, -169.0, 1468.0, "平庄");
		create_sta(slist, -25.0, 1407.0, "平庄西");
		create_sta(slist, 349.0, 2144.0, "沙堡自然村");
		create_sta(slist, 555.0, 1755.0, "沙堡");//30
		create_sta(slist, 946.0, 1787.0, "三角洲");
		create_sta(slist, 1261.0, 1815.0, "旅游中心");
		create_sta(slist, 1292.0, 1999.0, "沙城");
		create_sta(slist, 1426.0, 1999.0, "沙城区政府");
		create_sta(slist, 1482.0, 1935.0, "沙城半岛");
		create_sta(slist, -105.0, 118.0, "沼泽地");
		create_sta(slist, -183.0, 559.0, "微软");
		create_sta(slist, -203.0, 1330.0, "蝶湖");
		create_sta(slist, 1123.0, 1787.0, "海关");//39


//线路
		destination[1][0]="好望角";
		destination[1][1]="新区";
		destination[2][0]="地铁岛";
		destination[2][1]="星海";
		destination[3][0]="西口";
		destination[3][1]="大学城东";
		destination[4][0]="地铁岛";
		destination[4][1]="海湾";
		destination[5][0]="机场";
		destination[5][1]="沙湾";
		destination[6][0]="芳园";
		destination[6][1]="迷雾山";
		destination[7][0]="平庄西";
		destination[7][1]="T2航站楼";
		destination[8][0]="西口";
		destination[8][1]="大学城东";
		destination[9][0]="海湾";
		destination[9][1]="大石山顶";
		destination[12][0]="海湾";
		destination[12][1]="芳园";
		destination[13][0]="西口";
		destination[13][1]="沙城";
		destination[16][0]="机场";
		destination[16][1]="镜海";
		
		
		line[0][0]=16;
		line[1][0]=5;
		line[1][1]=13;
		line[1][2]=11;
		line[1][3]=37;
		line[1][4]=17;
		line[1][5]=5;
		line[2][0]=18;
		line[2][1]=10;
		line[2][2]=13;
		line[2][3]=11;
		line[2][4]=12;
		line[2][5]=15;
		line[2][6]=14;
		line[2][7]=3;
		line[2][8]=5;
		line[2][9]=38;
		line[2][10]=27;
		line[2][11]=29;
		line[2][12]=30;
		line[2][13]=31;
		line[2][14]=39;
		line[2][15]=32;
		line[2][16]=33;
		line[2][17]=34;
		line[2][18]=35;
		line[3][0]=4;
		line[3][1]=17;
		line[3][2]=1;
		line[3][3]=14;
		line[3][4]=19;
		line[4][0]=6;
		line[4][1]=10;
		line[4][2]=9;
		line[4][3]=8;
		line[4][4]=7;
		line[4][5]=37;
		line[4][6]=16;
		line[5][0]=3;
		line[5][1]=11;
		line[5][2]=36;
		line[5][3]=9;
		line[6][0]=9;
		line[6][1]=15;
		line[6][2]=23;
		line[6][3]=1;
		line[6][4]=2;
		line[6][5]=3;
		line[6][6]=4;
		line[6][7]=5;
		line[6][8]=6;
		line[6][9]=7;
		line[7][0]=8;
		line[7][1]=28;
		line[7][2]=27;
		line[7][3]=26;
		line[7][4]=5;
		line[7][5]=21;
		line[7][6]=1;
		line[7][7]=16;
		line[7][8]=12;
		line[8][0]=5;
		line[8][1]=17;
		line[8][2]=6;
		line[8][3]=21;
		line[8][4]=3;
		line[8][5]=20;
		line[9][0]=5;
		line[9][1]=16;
		line[9][2]=18;
		line[9][3]=2;
		line[9][4]=4;
		line[9][5]=22;
		line[12][0]=8;
		line[12][1]=16;
		line[12][2]=23;
		line[12][3]=17;
		line[12][4]=21;
		line[12][5]=2;
		line[12][6]=20;
		line[12][7]=19;
		line[12][8]=15;
		line[13][0]=5;
		line[13][1]=17;
		line[13][2]=5;
		line[13][3]=28;
		line[13][4]=30;
		line[13][5]=33;
		line[16][0]=4;
		line[16][1]=1;
		line[16][2]=3;
		line[16][3]=24;
		line[16][4]=25;

        //生成车站表格
		for (int s=1;s<=slist[0].num;s++){
			for (int i=1;i<=line[0][0];i++){
				for (int j=1;j<=line[i][0];j++){
					if (line[i][j]==s){
						int n=slist[s].sl[0]+1;
						slist[s].sl[n]=i;
						slist[s].ss[n]=j;
						slist[s].sl[0]=n;
					}	
				}
			}
		}
	}
}

