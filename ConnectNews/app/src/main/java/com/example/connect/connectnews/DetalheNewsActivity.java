package com.example.connect.connectnews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.connect.connectnews.R;
import com.example.connect.connectnews.model.Article;

public class DetalheNewsActivity extends AppCompatActivity {

    WebView webView;
    TextView url;
    String http;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_news);

        Bundle extras = getIntent().getExtras();


        webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(extras.getString("url"));
    }
}
