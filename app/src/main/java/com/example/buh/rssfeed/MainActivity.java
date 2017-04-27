package com.example.buh.rssfeed;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

import java.util.List;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity  {
    private static final String TAG_TITLE = "title";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_AUTHOR = "author";
    private static final String TAG_PUBLISHEDAT = "publishedAt";
    private static final String TAG_URL = "url";

    Context ctx;
    private List<NewsItem> newsFeed = new ArrayList<NewsItem>();
    CustomAdapter adapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.list);
        jsonParser();
        addClickListener();
        adapter = new CustomAdapter();
        lv.setAdapter(adapter);
    }
    public void jsonParser() {

        RequestQueue queue = Volley.newRequestQueue(this);
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=264e1f7afdff497aaaa6458de9bab1de",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray articlesItem = response.getJSONArray("articles");
                    for (int i = 0; i < articlesItem.length(); i++) {
                        JSONObject item = articlesItem.getJSONObject(i);

                        String title = item.getString(TAG_TITLE);
                        String description = item.getString(TAG_DESCRIPTION);
                        String author = item.getString(TAG_AUTHOR);
                        String dateTime = item.getString(TAG_PUBLISHEDAT);
                        String url = item.getString(TAG_URL);
Log.e("my", title+ " " + description + " " + author + " " + dateTime+ " "+url);



                        newsFeed.add(new NewsItem(title, description, author, dateTime, url));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                Log.e("mylog", error.toString());
            }

        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        queue.add(request);
    }
    private void addClickListener() {
        lv = (ListView) findViewById(R.id.list);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()

                                  {
                                      @Override
                                      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                          NewsItem currentItem = newsFeed.get(i);
                                          Intent intent = new Intent(Intent.ACTION_VIEW);
                                          intent.setData(Uri.parse(currentItem.getUrl()));
                                          startActivity(intent);
                                      }
                                  }
        );

    }

    public class CustomAdapter extends ArrayAdapter<NewsItem> {
       public CustomAdapter() {

           super(MainActivity.this, R.layout.item_list, newsFeed);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)  {
            MyViewHolder mViewHolder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_list, parent, false);
                mViewHolder = new MyViewHolder(convertView);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (MyViewHolder) convertView.getTag();
            }

            NewsItem currentItem = newsFeed.get(position);

            mViewHolder.heading.setText(currentItem.getNewsHeading());
            mViewHolder.desc.setText(currentItem.getNewsDescSmall());
            mViewHolder.author.setText(currentItem.getAuthor());
            mViewHolder.date.setText(currentItem.getDateTime());
           mViewHolder.url.setText(currentItem.getUrl());
            Log.e("my", currentItem.getNewsHeading() + " " + currentItem.getNewsDesc() + " " + currentItem.getAuthor() + " "
                    + currentItem.getDateTime() + " " + currentItem.getUrl());
            return convertView;
        }
            private class MyViewHolder {
                TextView heading, desc, author, date, url ;

                public MyViewHolder(View item) {
                    heading = (TextView) item.findViewById(R.id.heading);
                    desc = (TextView) item.findViewById(R.id.desc);
                    author = (TextView) item.findViewById(R.id.author);
                    date = (TextView) item.findViewById(R.id.datetime);
                    url = (TextView) item.findViewById(R.id.url);
                }
            }
        }


    @Override
    protected void onResume() {
           super.onResume();
      jsonParser();
        addClickListener();
    }
}