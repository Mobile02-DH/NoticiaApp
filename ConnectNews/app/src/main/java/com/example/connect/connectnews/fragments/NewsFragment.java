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
import com.example.connect.connectnews.model.News;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    RecyclerView recyclerViewNews;
    ArrayList<News> listaNews = new ArrayList<>();

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
     View view =  inflater.inflate(R.layout.fragment_news, container, false);

        AdapterNews adapterNews = new AdapterNews(criarNews());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //linearLayoutManager.setOrientation(LinearLayout.HORIZONTAL);
        recyclerViewNews = view.findViewById(R.id.recycler_news);
        recyclerViewNews.setLayoutManager(linearLayoutManager);
        recyclerViewNews.setHasFixedSize(true);
        recyclerViewNews.setAdapter(adapterNews);

        return view;
    }

    public ArrayList<News> criarNews(){

        ArrayList<News> listaNews = new ArrayList<>();

        for (int i=0; i<6 ; i++){
            listaNews.add(new News("Cabo Daciolo" + i,"Benevenuto Daciolo Fonseca dos Santos," +
                    " mais  conhecido como Cabo Daciolo, é um bombeiro militar e político brasileiro" +
                    " filiado ao partido Patriota. Em 2014, foi eleito deputado federal pelo Rio de  Janeiro. " +
                    "Expulso do PSOL em 2015, foi filiado ao  Avante e, atualmente, está filiado ao Patriota.\\n " +
                    " Glóriaaaa a Deuxxx!"));


        }
        return listaNews;
    }
}
