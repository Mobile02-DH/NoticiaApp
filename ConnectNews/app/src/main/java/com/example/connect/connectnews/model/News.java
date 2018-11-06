package com.example.connect.connectnews.model;

public class News {

    private String imagem;
    private String titulo;
    private String noticias;

    public News() {
    }

    public News( String titulo, String noticias) {
        this.imagem = imagem;
        this.titulo = titulo;
        this.noticias = noticias;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNoticias() {
        return noticias;
    }

    public void setNoticias(String noticias) {
        this.noticias = noticias;
    }
}
