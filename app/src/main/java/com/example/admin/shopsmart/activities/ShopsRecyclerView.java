package com.example.admin.shopsmart.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.shopsmart.R;
import com.example.admin.shopsmart.pojos.Shop;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import static java.security.AccessController.getContext;

public class ShopsRecyclerView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference mDatabaseReference;
    private RecyclerView recyclerView;
    private String _key;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int mThemeId = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.global_list);
        if(savedInstanceState != null && savedInstanceState.getInt("theme", -1) != -1) {
            mThemeId = savedInstanceState.getInt("theme");
            this.setTheme(mThemeId);
        }

//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.scrollToRefresh);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                onStart();
//            }
//        });
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Shop");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Shop, ShopViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Shop, ShopViewHolder>(
                Shop.class,
                R.layout.custom_shop_view,
                ShopViewHolder.class,
                mDatabaseReference
        ) {
            @Override
            protected void populateViewHolder(ShopViewHolder viewHolder, final Shop model, final int position) {
                viewHolder.setName(model.getShop_Name());
                viewHolder.setLocation(model.getShop_location());
                viewHolder.setImg(getApplicationContext(), model.getImage());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(model.getShop_Category().equalsIgnoreCase("Clothing")){
                            _key = getRef(position).getKey();
                            Toast.makeText(getApplicationContext(), "Key: " + _key +" :"+model.getShop_Category(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(),ClothingRecyclerView.class);
                            intent.putExtra("_key",_key);
                            intent.putExtra("model",model);
                            startActivity(intent);
                            finish();
                        }else if(model.getShop_Category().equalsIgnoreCase("Food")){
                            _key = getRef(position).getKey();
                            Toast.makeText(getApplicationContext(), "Key: " + _key+" :"+model.getShop_Category(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(),FoodRecyclerView.class);
                            intent.putExtra("_key",_key);
                            intent.putExtra("model",model);
                            startActivity(intent);
                            finish();
                        }else {
                            _key = getRef(position).getKey();
                            Toast.makeText(getApplicationContext(), "Key: " + _key+" :"+model.getShop_Category(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(),FurnitureRecyclerView.class);
                            intent.putExtra("_key",_key);
                            intent.putExtra("model",model);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private static class ShopViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public ShopViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        void setName(String name){

            TextView tvName = (TextView) mView.findViewById(R.id.tv_ShopName);
            tvName.setText(name);

        }

        void setLocation(String location){

            TextView tvLocation = (TextView) mView.findViewById(R.id.tv_Location);
            tvLocation.setText(location);

        }

        void setImg(Context c, String img){
            ImageView imageView = (ImageView) mView.findViewById(R.id.imageview_Shop);
//            Bitmap bitmap = BitmapFactory.decodeFile(img);
//            bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
//            imageView.setImageBitmap(bitmap);
            Picasso.with(c).load(img).into(imageView);

        }

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //TODO are you sure you want to exit
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_refresh) {
            startActivity(new Intent(getApplicationContext(),ShopsRecyclerView.class));
            finish();
        } else if (id == R.id.nav_shop_centre) {
            startActivity(new Intent(getApplicationContext(),ActivityMap.class));
            finish();
        } else if (id == R.id.nav_about_us) {
            Intent intent = new Intent("android.intent.action.ABOUT_US");
            startActivity(intent);
        } else if (id == R.id.nav_manage_settings) {
            startDialog();
        }else if(id == R.id.nav_share)
        {
            startActivity(new Intent(getBaseContext(),Gallery.class));
            finish();
        }else if(id==R.id.nav_gallery){
            startActivity(new Intent(getBaseContext(),GalleryRecyclerView.class));
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //--------------------
    private void startDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Change app theme");
        myAlertDialog.setMessage("Which theme do you want to set?");

        myAlertDialog.setPositiveButton("Light theme",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        mThemeId = R.style.AppTheme;
                    }
                });

        myAlertDialog.setNegativeButton("Dark theme",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        mThemeId = R.style.DarkTheme;
                    }
                });
        myAlertDialog.show();
    }
    // ===========

}
