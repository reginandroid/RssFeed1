package com.example.buh.rssfeed;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;


public class MainActivity extends AppCompatActivity  {
    private static final String TAG_TITLE = "title";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_AUTHOR = "author";
    private static final String TAG_PUBLISHEDAT = "publishedAt";
    private static final String TAG_URL = "url";
    private static final String TAG_IMAGE_URL = "urlToImage";
    CustomAdapter adapter;
    private ArrayList<NewsItem> newsFeed;
Context context;
    FeedDBHelper feedDBHelper;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        (new NukeSSLCerts()).nuke();
        feedDBHelper = new FeedDBHelper(this);

        jsonParser();

        ArrayList<NewsItem> listforadapter = feedDBHelper.getArticleList();
        adapter = new CustomAdapter(this, listforadapter) ;

                lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();


            addClickListener();
    }
    public void onResume(){
        super.onResume();


    }

    public void jsonParser() {

   final ArrayList<NewsItem> list =  new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=4e5bfbc64a4f4064991cd38068c8228b",
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
                        String imageurl = item.getString(TAG_IMAGE_URL);
                        Log.d("my", title+ " " + description + " " + author + " " + dateTime+ " "+url + " "+ imageurl);

                        list.add(new NewsItem(title, description, author, dateTime, url, imageurl ));
                   }
                   if (feedDBHelper.getArticleList().size()>0){feedDBHelper.deleteAllrows();}
                    feedDBHelper.saveArticleItem(list);

Log.d("my",feedDBHelper.getArticleList().size() +  feedDBHelper.getArticleList().get(0).getNewsHeading().toString() );

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                Log.d("mylog", error.toString());
            }

        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        queue.add(request);

    }
    private void addClickListener() {
        lv = (ListView) findViewById(R.id.list);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()

                                  {
                                      @Override
                                      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                          NewsItem currentItem = feedDBHelper.getArticleList().get(i);
                                          Intent intent = new Intent(Intent.ACTION_VIEW);
                                          intent.setData(Uri.parse(currentItem.getUrl()));
                                          startActivity(intent);
                                      }
                                  }
        );

    }




    public class CustomAdapter extends ArrayAdapter<NewsItem> {
ArrayList<NewsItem> list;
        private CustomAdapter(Context context, ArrayList<NewsItem> arrayList) {
            super(context, R.layout.item_list, arrayList);
this.list = arrayList;
        }

        @Override
        @NonNull
        @SuppressWarnings("NullableProblems")
        public View getView(int position, View convertView, ViewGroup parent)  {
            MyViewHolder mViewHolder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_list, parent, false);
                mViewHolder = new MyViewHolder(convertView);
                convertView.setTag(mViewHolder);
            } else {

                mViewHolder = (MyViewHolder) convertView.getTag();
            }

            NewsItem currentItem = list.get(position);

            mViewHolder.heading.setText(currentItem.getNewsHeading());
            mViewHolder.desc.setText(currentItem.getNewsDescSmall());
            mViewHolder.author.setText(currentItem.getAuthor());
            mViewHolder.date.setText(currentItem.getDateTime());
            mViewHolder.url.setText(currentItem.getUrl());
            Picasso.with(MainActivity.this).load(currentItem.getImageurl()).into(mViewHolder.image);
            return convertView;
        }
        private class MyViewHolder {
            TextView heading, desc, author, date, url ;
            ImageView image;
            private MyViewHolder(View item) {
                heading = (TextView) item.findViewById(R.id.heading);
                desc = (TextView) item.findViewById(R.id.desc);
                author = (TextView) item.findViewById(R.id.author);
                date = (TextView) item.findViewById(R.id.datetime);
                url = (TextView) item.findViewById(R.id.url);
                image = (ImageView)item.findViewById(R.id.image);
            }
        }
    }

protected void onDestroy(){

    super.onDestroy();




}
    public static class NukeSSLCerts {
        protected static final String TAG = "NukeSSLCerts";

        public static void nuke() {
            try {
                TrustManager[] trustAllCerts = new TrustManager[] {
                        new X509TrustManager() {
                            public X509Certificate[] getAcceptedIssuers() {
                                X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                                return myTrustedAnchors;
                            }

                            @Override
                            public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                            @Override
                            public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                        }
                };

                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String arg0, SSLSession arg1) {
                        return true;
                    }
                });
            } catch (Exception e) {
            }
        }
    }
}