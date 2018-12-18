package com.example.connect.connectnews;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.connect.connectnews.adapter.CategoryPageAdapter;
import com.example.connect.connectnews.fragments.NewsFragment;
import com.example.connect.connectnews.model.Article;
import com.example.connect.connectnews.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    TabLayout tabLayout;
    private ViewPager viewPager;
    CategoryPageAdapter categoryPageAdapter = new CategoryPageAdapter(getSupportFragmentManager(),criarCategorias());
    TextView txtEmail;
    TextView txtName;
    private ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.container);

        viewPager.setAdapter(categoryPageAdapter);

      viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
      tabLayout.addOnTabSelectedListener( new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FirebaseAuth autenticacao = FirebaseAuth.getInstance();
        Usuario usuario = new Usuario();

        txtName = navigationView.getHeaderView(0).findViewById(R.id.tv_name);
        txtEmail = navigationView.getHeaderView(0).findViewById(R.id.txtEmail);
       // avatar = navigationView.getHeaderView(0).findViewById(R.id.avatar);

        Intent intent = getIntent();
        //String nome = autenticacao.getLanguageCode();
       // String image = intent.getStringExtra("image");

       // txtName.setText(nome);
        //Picasso.get().load(image).error(R.drawable.cabo_dacilolo).into(avatar);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.login) {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_connectnews) {
            // Handle the camera action
        } else if (id == R.id.nav_myfavorites) {
            FirebaseAuth autenticacao = FirebaseAuth.getInstance();

            if (autenticacao.getCurrentUser() != null){

                startActivity(new Intent(getApplicationContext(),FavoritoActivity.class));
            }else {

                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }

        } else if (id == R.id.nav_preferences) {

        } else if (id == R.id.nav_profile) {

            startActivity(new Intent(this,RegisterActivity.class));

        }  else if (id == R.id.nav_exit) {

            FirebaseAuth autenticacao = FirebaseAuth.getInstance();
            autenticacao.signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private List<Fragment>criarCategorias(){

        ArrayList<Fragment> fragments = new ArrayList<>();

         fragments.add(NewsFragment.newInstance("business"));
         fragments.add(NewsFragment.newInstance("entertainment"));
         fragments.add(NewsFragment.newInstance("general"));
         fragments.add(NewsFragment.newInstance("health"));
         fragments.add(NewsFragment.newInstance("science"));
         fragments.add(NewsFragment.newInstance("sports"));
         fragments.add(NewsFragment.newInstance("technology"));

        return fragments;
    }


}
