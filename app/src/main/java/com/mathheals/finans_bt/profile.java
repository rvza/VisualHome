package com.mathheals.finans_bt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profile extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private Button kullaniciSil, cikisYap;
    private TextView textView;
    private Toolbar toolbar;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        auth = FirebaseAuth.getInstance();

        //Bir AuthStateListener dinleyicisi oluşturuyoruz ve bu dinleyici onAuthStateChanged bölümünü çalıştırır.
        // Buradaki getCurrentUser metodu ile kullanıcı verilerine ulaşabilmekteyiz.
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                // Eğer geçerli bir kullanıcı oturumu yoksa LoginActivity e geçilir.
                // Oturum kapatma işlemi yapıldığında bu sayede LoginActivity e geçilir.
                if (user == null) {

                    startActivity(new Intent(profile.this, MainActivity.class));
                    finish();
                }
            }
        };
        //getCurrentUser metodu üzerinden ulaştığımız kullanıcı verilerinde getEmail ile de kullanıcının mailini kullanarak,
        // kullanıcıya bir text gösteriyoruz.
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        setUpToolbar();
        setUpDrawer();
        recyclerView= (RecyclerView) findViewById(R.id.recyclerview);

        CardViewAdapter cardViewAdapter=new CardViewAdapter(this,Esya.getData(), new CustomItemClickListener()
        {
            @Override
            public void onItemClick(View v, int position) {
                //  sendCardViewItem_sbInfo(subInfo,  subInfoValue,  userId,   subImagesUrl);
                Log.e("position", "Tıklanan Pozisyon:" + position);
                gercekle(position);
          }

        });
        recyclerView.setAdapter(cardViewAdapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
    private void setUpToolbar(){
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Visual Home");
      //  toolbar.setSubtitle("Anasayfa");
    }
    private void setUpDrawer(){

        NavigationDrawerFragment navFragment= (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment);
        DrawerLayout drawerLayout= (DrawerLayout) findViewById(R.id.drawerLayout);
        navFragment.setUpNavigationDrawer(drawerLayout, toolbar);

       }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.layout_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
      public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.cikis:
                //FirebaseAuth.getInstance().signOut ile oturumu kapatabilmekteyiz.
                auth.signOut();
                startActivity(new Intent(profile.this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }
    public  void  gercekle(int position)
    {
        Intent intent = new Intent(this, com.unity3d.player.UnityPlayerNativeActivity.class);
        startActivity(intent);
    }
}