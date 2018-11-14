package com.example.connect.connectnews.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.connect.connectnews.R;
import com.example.connect.connectnews.adapter.AdapterNews;
import com.example.connect.connectnews.helper.NewService;
import com.example.connect.connectnews.helper.RetrofitConfig;
import com.example.connect.connectnews.model.Article;
import com.example.connect.connectnews.model.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    RecyclerView recyclerViewNews;

    public static final String APIKEY = "da01bd4c89e244f79999751870ae2f8e";
    public static final String COUNTRY = "br";
    public static final String ARTICLES = "top-headlines";
    public List<Article> articleList;
    private String category;

    public NewsFragment() {
        // Required empty public constructor
    }

    public static NewsFragment newInstance (String categoria){

        Bundle args = new Bundle();
        args.putString("categoria" , categoria);

        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     final View view =  inflater.inflate(R.layout.fragment_news, container, false);

       NewService service = RetrofitConfig.buildRetrofit().create(NewService.class);

        final AdapterNews adapterNews = new AdapterNews(new ArrayList<Article>());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //linearLayoutManager.setOrientation(LinearLayout.HORIZONTAL);
        recyclerViewNews = view.findViewById(R.id.recycler_news);
        recyclerViewNews.setLayoutManager(linearLayoutManager);
        recyclerViewNews.setHasFixedSize(true);
        recyclerViewNews.setAdapter(adapterNews);

        category = getArguments().getString("categoria");


        Call<News> call = service.getNewsByCategory(ARTICLES,COUNTRY,APIKEY,category);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {

                articleList = response.body().getArticles();
                adapterNews.update(articleList);
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });



        return view;
    }



}
