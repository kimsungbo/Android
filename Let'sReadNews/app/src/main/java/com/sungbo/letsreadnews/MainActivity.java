package com.sungbo.letsreadnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.icu.text.SearchIterator;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toolbar;

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
    private RequestQueue queue;
    private SearchView searchView;
    private List<NewsData> news_list;
    private MenuItem searchItem;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        newsUtil = new NewsUtil();


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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchItem = menu.findItem(R.id.action_search);

        searchView.setQueryHint("검색어를 입력하여 주세요");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getNewsWithKeyWord(s);
                searchView.onActionViewCollapsed();



                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                menu.findItem(R.id.action_search).collapseActionView();
                return true;
            }
        });




        return true;
    }


    public void getNewsWithKeyWord(String keyword){
        newsUtil.getApi().getNewsWithKeyword(keyword, "publishedAt", BuildConfig.NEWS_API_KEY).enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                News news = new News();
                news = response.body();
                Log.d("MainActivity", news.getArticles().get(0).getTitle());

                news_list.clear();

                for(int i = 0 ; i < news.getArticles().size(); i++){

                    NewsData newsData = new NewsData();

                    newsData.setTitle(news.getArticles().get(i).getTitle());

                    String url = "";
                    if ( news.getArticles().get(i).getUrlToImage() != null && news.getArticles().get(i).getUrlToImage().substring(0, 5).equals("http:")){
                        url = "https:" + news.getArticles().get(i).getUrlToImage().substring(5);
                    }
                    else{
                        url = news.getArticles().get(i).getUrlToImage();
                    }


                    newsData.setUrlToImage(url);
                    newsData.setContent((String) news.getArticles().get(i).getDescription());
                    newsData.setInternetUrl(news.getArticles().get(i).getUrl());

                    news_list.add(newsData);
                }


                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.d("MainActivity", "failed");


            }
        });
    }

    public void getNews(){
        newsUtil.getApi().getNews("kr", BuildConfig.NEWS_API_KEY).enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                News news = new News();
                news = response.body();
                Log.d("MainActivity", news.getArticles().get(0).getTitle());

                news_list = new ArrayList<NewsData>();

                for(int i = 0 ; i < news.getArticles().size(); i++){

                        NewsData newsData = new NewsData();

                        newsData.setTitle(news.getArticles().get(i).getTitle());

                        String url;
                        if ( news.getArticles().get(i).getUrlToImage() != null && news.getArticles().get(i).getUrlToImage().substring(0, 5).equals("http:")){
                            url = "https:" + news.getArticles().get(i).getUrlToImage().substring(5);
                        }
                        else{
                            url = news.getArticles().get(i).getUrlToImage();
                        }

                        newsData.setUrlToImage(url);
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