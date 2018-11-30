package com.example.connect.connectnews.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.connect.connectnews.R;
import com.example.connect.connectnews.model.Article;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.ViewHolder> {

    private List<Article> articleList;

    public AdapterNews() {
    }

    public AdapterNews(List<Article> lista) {
        this.articleList = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemNews = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_item_view, viewGroup, false);
        return new ViewHolder(itemNews);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Article article = articleList.get(i);
        viewHolder.titulo.setText(article.getTitle());
        viewHolder.noticia.setText(article.getDescription());


        if (article.getUrlToImage() != null && !article.getUrlToImage().equals("")) {

            Picasso.get().load(article.getUrlToImage())
                    .error(R.drawable.ic_logotop)
                    .placeholder(R.drawable.ic_logotop)
                    .into(viewHolder.imagem);
        } else {
            viewHolder.imagem.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public void update(List<Article> articleList) {
        this.articleList = articleList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imagem;
        TextView titulo;
        TextView noticia;
        ImageView favoritos;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagem = itemView.findViewById(R.id.image_item);
            titulo = itemView.findViewById(R.id.txtTitulo);
            noticia = itemView.findViewById(R.id.txtNoticias);
            favoritos = itemView.findViewById(R.id.btn_user_like);


        }

        public void bind(final Article article) {
            favoritos.setOnClickListener(new View.OnClickListener() {

                private DatabaseReference firebase;

                @Override
                public void onClick(View v) {
                    firebase = FirebaseDatabase.getInstance().getReference();
                    firebase.child("usuario").setValue(article);


                }

            });
        }
    }
}
