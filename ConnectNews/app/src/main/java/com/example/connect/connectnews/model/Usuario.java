package com.example.connect.connectnews.model;

import com.example.connect.connectnews.configuracaoFirebase.FirebaseConfiguracao;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Usuario {

   private String email;
   private String senha;
   private String id;
   private String favoritos;


    public String getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(String favoritos) {
        this.favoritos = favoritos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    private String nome;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario() {
    }

    public void salvar(){
        DatabaseReference referenceFirebase = FirebaseConfiguracao.getFirebase();
        referenceFirebase.child("usuario").child(String.valueOf(getId())).setValue(this);
    }

    public void favoritar(){
        DatabaseReference referenceFirebase = FirebaseConfiguracao.getFirebase();
        referenceFirebase.child("favorito").child("usuario").setValue(this);
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String,Object> hashMapUsuario = new HashMap<>();

        hashMapUsuario.put("id", getId());
        hashMapUsuario.put("email", getEmail());
        hashMapUsuario.put("senha", getSenha());
        hashMapUsuario.put("nome", getNome());
        hashMapUsuario.put("favorito", getFavoritos());

        return hashMapUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
