package com.sungbo.letsreadnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sungbo.letsreadnews.model.news.News;
import com.sungbo.letsreadnews.util.NewsUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private NewsUtil newsUtil;

    private String[] myDataset = {"1", "2"};

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        getNews();

    }

//    public void getNews(){
//
//        // Instantiate the RequestQueue.
//        queue = Volley.newRequestQueue(this);
//
//        //https://newsapi.org/v2/top-headlines?country=kr&apiKey=fc8e423475bd4d088c816679ea63f5c3
//        String url ="https://newsapi.org/v2/everything?q=tesla&from=2021-03-01&sortBy=publishedAt&apiKey=fc8e423475bd4d088c816679ea63f5c3";
//
//        // Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                // Display the first 500 characters of the response string.
//                //textView.setText("Response is: "+ response.substring(0,500));
//                Log.d("MainActivity", response);
//
//
//                try {
//                    JSONObject jsonObj = new JSONObject(response);
//
//                    JSONArray arrayArticles = jsonObj.getJSONArray("articles");
//
//                    List<NewsData> news = new ArrayList<NewsData>();
//
//                    for(int i = 0 ; i < arrayArticles.length(); i++){
//                        JSONObject obj = arrayArticles.getJSONObject(i);
//
//                        Log.d("NEWS", obj.toString());
//
//                        NewsData newsData = new NewsData();
//
//                        newsData.setTitle(obj.getString("title"));
//                        newsData.setUrlToImage(obj.getString("urlToImage"));
//                        newsData.setContent(obj.getString("content"));
//
//                        news.add(newsData);
//                    }
//
//                    mAdapter = new NewsAdapter(news);
//                    mRecyclerView.setAdapter(mAdapter);
//
//
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //textView.setText("That didn't work!");
//                Log.d("MainActivity", "failed");
//
//            }
//        }) {
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                //headers.put("Content-Type", "application/json");
//                headers.put("key", "fc8e423475bd4d088c816679ea63f5c3");
//                return headers;
//            }
//        };
//
//        // Add the request to the RequestQueue.
//        queue.add(stringRequest);
//    }

    public void getNews(){
        newsUtil = new NewsUtil();
        newsUtil.getApi().getNews("kr", BuildConfig.NEWS_API_KEY).enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                News news = new News();
                news = response.body();
                Log.d("MainActivity", news.getArticles().get(0).getTitle());

                List<NewsData> news_list = new ArrayList<NewsData>();

                for(int i = 0 ; i < news.getArticles().size(); i++){

                        NewsData newsData = new NewsData();

                        newsData.setTitle(news.getArticles().get(i).getTitle());
                        newsData.setUrlToImage(news.getArticles().get(i).getUrlToImage());
                        newsData.setContent((String) news.getArticles().get(i).getDescription());
                        newsData.setInternetUrl(news.getArticles().get(i).getUrl());

                        news_list.add(newsData);
                    }

                    mAdapter = new NewsAdapter(news_list, MainActivity.this);
                    mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.d("MainActivity", "failed");


            }
        });
    }
}