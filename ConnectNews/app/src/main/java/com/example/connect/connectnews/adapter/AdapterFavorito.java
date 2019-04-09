package com.example.connect.connectnews.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import com.example.connect.connectnews.DetalheNewsActivity;
import com.example.connect.connectnews.R;
import com.example.connect.connectnews.model.Article;
import com.example.connect.connectnews.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterFavorito extends RecyclerView.Adapter<AdapterFavorito.ViewHolder> {

    private List<Article> articleList;


    public AdapterFavorito(List<Article> articleList) {
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemFavotito = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_item_favorito, viewGroup, false);
        return new ViewHolder(itemFavotito);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Article article = articleList.get(i);
        viewHolder.bind(article);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imagem;
        TextView titulo;
        TextView noticia;
        ImageView delete;
        ImageView share1;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagem = itemView.findViewById(R.id.image_item);
            titulo = itemView.findViewById(R.id.txtTitulo);
            noticia = itemView.findViewById(R.id.txtNoticias);
            delete = itemView.findViewById(R.id.image_delete);
            share1 = itemView.findViewById(R.id.image_compartilhar1);
            constraintLayout = itemView.findViewById(R.id.constraint_item2);
        }

        public void bind(final Article article) {

            titulo.setText(article.getTitle());
            noticia.setText(article.getDescription());

            if (article.getUrlToImage() != null && !article.getUrlToImage().equals("")) {
                Picasso.get().load(article.getUrlToImage())
                        .error(R.drawable.ic_logotop)
                        .placeholder(R.drawable.ic_logotop)
                        .into(imagem);
            } else {
                imagem.setVisibility(View.GONE);
            }

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("usuario");
                    FirebaseAuth autenticacao = FirebaseAuth.getInstance();

                    String idUsuario;
                    DatabaseReference idFavorito;

                    idUsuario = autenticacao.getCurrentUser().getUid();
                    idFavorito = databaseReference.child(idUsuario).child("favoritos").child(article.getChave());

                    idFavorito.removeValue();

                    Toast.makeText(v.getContext(),"Noticia Removida", Toast.LENGTH_SHORT).show();

                   notifyItemRemoved(getAdapterPosition());
                   articleList.remove(article);
                    //excluirNews(article);
                }
            });

            share1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent myIntent = new Intent(Intent.ACTION_SEND);
                    myIntent.setType("text/plain");
                    String shareBody = article.getUrl();
                    String shareSub = article.getTitle();
                    myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                    myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                    v.getContext().startActivity(Intent.createChooser(myIntent,"Compartilhar"));

                    Toast.makeText(v.getContext(),"Compartilhar", Toast.LENGTH_SHORT).show();
                }
            });

            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(itemView.getContext(), DetalheNewsActivity.class);
                    intent.putExtra("url",article.getUrl());
                    v.getContext().startActivity(intent);
                }
            });

        }
    }

    public void update(List<Article> articleList) {
        this.articleList = articleList;
        notifyDataSetChanged();
    }

  /* public void excluirNews(final Article article){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Excluir Noticias");
        alertDialog.setMessage("Você tem certeza que deseja excluir a noticia?");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String idUsuario;
                DatabaseReference idFavorito;

               DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("usuario");
               FirebaseAuth autenticacao = FirebaseAuth.getInstance();

                idUsuario = autenticacao.getCurrentUser().getUid();
                idFavorito = databaseReference.child(idUsuario).child("favoritos").child(article.getChave());

                idFavorito.removeValue();

                Toast.makeText(context,"noticia removida", Toast.LENGTH_SHORT).show();


            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(context,"Cancelado", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }*/


}
