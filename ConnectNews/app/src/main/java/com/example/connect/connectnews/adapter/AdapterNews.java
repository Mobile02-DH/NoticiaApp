package com.example.connect.connectnews.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.connect.connectnews.R;
import com.example.connect.connectnews.model.News;

import java.util.List;

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.ViewHolder>{

    private List<News> listaNoticias;

    public AdapterNews(List<News> lista) {
        this.listaNoticias = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemNews = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_item_view,viewGroup,false);
        return new ViewHolder(itemNews);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        News news = listaNoticias.get(i);
        viewHolder.titulo.setText(news.getTitulo());
        viewHolder.noticia.setText(news.getNoticias());

    }

    @Override
    public int getItemCount() {
        return listaNoticias.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imagem;
        TextView titulo;
        TextView noticia;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        imagem = itemView.findViewById(R.id.image_item);
        titulo = itemView.findViewById(R.id.txtTitulo);
        noticia = itemView.findViewById(R.id.txtNoticias);
    }
}
}
