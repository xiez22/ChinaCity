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

public class NewsActivity extends Activity 
{
	//Data
	int downloadsta=0;
	ProgressDialog dialog;
	ArrayList<News> newsList;

	protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subnews);

		//News
		newsList = new ArrayList<News>();

		//Get News
		getNews(newsList);

	}

	public void onClickSubNewsButtonBack(View view)
	{
		finish();
	}

	//Adapter
	class NewsAdapter extends ArrayAdapter<News>
	{
		public NewsAdapter(Context context, ArrayList<News> newsList)
		{
			super(context, R.layout.news_entry, newsList);
		}
		@Override
        public View getView(int position, View convertView, ViewGroup parent)
		{
            News news= getItem(position);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.news_entry, null);
            TextView textView1 = (TextView) view.findViewById(R.id.newsentryTextView1);
            TextView textView2 = (TextView) view.findViewById(R.id.newsentryTextView2);
            TextView textView3 = (TextView) view.findViewById(R.id.newsentryTextView3);
            TextView textView4 = (TextView) view.findViewById(R.id.newsentryTextView4);

			textView1.setText(news.title);
			textView2.setText(news.date);
			textView3.setText(news.kind);
			textView4.setText(news.content);

            return view;
        }
	}

	//Class News
	class News
	{
		public String title;
		public String date;
		public String kind;
		public String content;

		public News(String title, String date, String kind, String content)
		{
			this.title = title;
			this.date = date;
			this.kind = kind;
			this.content = content;
		}
	}

	public void getNews(ArrayList<News> news)
	{
		//for(int i=0;i<10;i++)
		//news.add(new News("Title"+Integer.toString(i),"2018.6.20","News","Welcome to use news!"));

		dialog = new ProgressDialog(this);
		dialog.setMessage("加载中...");
		dialog.show();
		dialog.setCanceledOnTouchOutside(false);
		new Thread(new Runnable() {
				@Override
				public void run()
				{
					int dr=internetutil.download("https://coding.net/u/ligongzzz/p/ChinaResources/git/raw/master/src/news.json", "/Android/data/com.China.ChinaCity/cache/", "news.json");
					if (dr == 0)
					{
						downloadsta = 1;
					}
					else
					{
						downloadsta = 2;
					}
					Message message = new Message(); 
					handler.sendMessage(message);
				}
			}).start();
    }

	//Handlers
	Handler handler= new Handler() { 
		public void handleMessage(Message msg)
		{ 
			dialog.dismiss();
			if (downloadsta == 1)
			{
				//Analysis
				JSONArray myjson=JSONArray.fromObject(fileutil.read("Android/data/com.China.ChinaCity/cache/news.json").get(0).toString());
				for (int i=myjson.size()-1;i>=0;i--)
				{
					JSONObject myobject=myjson.getJSONObject(i);
					newsList.add(new News(myobject.getString("title"), myobject.getString("date"), myobject.getString("kind"), myobject.getString("content")));
				}
			}
			else{
				newsList.add(new News("加载失败","很久很久以前...","错误","网络已经中断，请检查您的网络连接。"));
			}
			//Create List
			NewsAdapter adapter=new NewsAdapter(NewsActivity.this, newsList);
			ListView listView=(ListView)findViewById(R.id.subnewsListView1);
			listView.setAdapter(adapter);
		}
	};
}
