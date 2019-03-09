package com.example.connect.connectnews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.connect.connectnews.adapter.AdapterFavorito;
import com.example.connect.connectnews.helper.RecyclerItemClickListener;
import com.example.connect.connectnews.model.Article;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoritoActivity extends AppCompatActivity {

    RecyclerView recyclerFavorito;
    List<Article> articles = new ArrayList<>();
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorito);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance()
                .getReference().child("usuario").child(firebaseAuth.getCurrentUser().getUid());

        recyclerFavorito = findViewById(R.id.recycler_favorito);

        final AdapterFavorito adapterFavorito = new AdapterFavorito(new ArrayList<Article>());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerFavorito.setLayoutManager(linearLayoutManager);
        recyclerFavorito.setHasFixedSize(true);
        recyclerFavorito.setAdapter(adapterFavorito);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnapshot : dataSnapshot.child("favoritos").getChildren()) {
                    Article article = itemSnapshot.getValue(Article.class);
                    article.setChave(itemSnapshot.getKey());
                    articles.add(article);
                }
                adapterFavorito.update(articles);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("DATABASE", "loadPost:onCancelled", databaseError.toException());
            }
        });

        recyclerFavorito.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext()
                , recyclerFavorito, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Article article = articles.get(position);
                Intent intent = new Intent(getApplicationContext(),DetalheNewsActivity.class);
                intent.putExtra("url",article.getUrl());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

    }
}
