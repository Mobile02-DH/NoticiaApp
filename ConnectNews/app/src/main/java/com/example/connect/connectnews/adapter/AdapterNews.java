package com.example.connect.connectnews.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.connect.connectnews.FavoritoActivity;
import com.example.connect.connectnews.R;
import com.example.connect.connectnews.model.Article;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.connect.connectnews.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        Article article = articleList.get(i);
        viewHolder.bind(article);
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
        ImageView favorito;
        private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("usuario");
        private FirebaseAuth autenticacao = FirebaseAuth.getInstance();
        String emailUsuario;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        imagem = itemView.findViewById(R.id.image_item);
        titulo = itemView.findViewById(R.id.txtTitulo);
        noticia = itemView.findViewById(R.id.txtNoticias);
        favorito = itemView.findViewById(R.id.btn_user_like);
    }

        public void bind(final Article article){

            titulo.setText(article.getTitle());
            noticia.setText( article.getDescription());

            if (article.getUrlToImage()!= null && !article.getUrlToImage().equals("")) {
                Picasso.get().load(article.getUrlToImage())
                        .error(R.drawable.ic_logotop)
                        .placeholder(R.drawable.ic_logotop)
                        .into(imagem);
            }else {
                imagem.setVisibility(View.GONE);
            }

            favorito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    emailUsuario = autenticacao.getCurrentUser().getUid();

                    databaseReference.child(emailUsuario).child("favoritos").push().setValue(article);

                    Toast.makeText(v.getContext(), "Noticia Salva", Toast.LENGTH_SHORT).show();

                   // Intent intent = new Intent(v.getContext(),FavoritoActivity.class);
                    //v.getContext().startActivity(intent);
                }
            });
        }
}
}
