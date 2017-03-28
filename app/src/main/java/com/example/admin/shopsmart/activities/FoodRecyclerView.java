package com.example.admin.shopsmart.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import com.example.admin.shopsmart.pojos.Food;
import com.example.admin.shopsmart.pojos.Shop;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodRecyclerView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Shop shop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.global_list);
        shop = (Shop)getIntent().getSerializableExtra("model");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(shop.getShop_Name());
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String _key = getIntent().getStringExtra("_key");
        String _category = shop.getShop_Category();
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Shop").child(_key).child(_category);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerAdapter<Food, FoodViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.item_raw_view,
                FoodViewHolder.class,
                mDatabaseReference

        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, final Food item, int position) {
                final String _key = getRef(position).getKey();
                viewHolder.setName(item.getFood_Brand_Name());
                viewHolder.setDescription(item.getFood_Specification());
                viewHolder.setImg(getApplicationContext(), item.getImage());
                viewHolder.setPrice(String.valueOf(item.getFood_Reduced_Price()));
                System.out.println("===========================:");
                System.out.println("===========================:" + String.valueOf(item.getFood_Reduced_Price()));
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Key: " + _key, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), ItemDisplay.class);
                        intent.putExtra("item", item);
                        intent.putExtra("shop", shop);
                        startActivity(intent);
                    }
                });
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
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
            startActivity(new Intent("android.intent.action.ABOUT_US"));
        } else if (id == R.id.nav_manage_settings) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private static class FoodViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public FoodViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setName(String name) {

            TextView tvName = (TextView) mView.findViewById(R.id.itemName);
            tvName.setText(name);

        }

        void setDescription(String description) {

            TextView tvLocation = (TextView) mView.findViewById(R.id.itemSpecification);
            tvLocation.setText(description);

        }

        void setPrice(String description) {

            TextView tvPrice = (TextView) mView.findViewById(R.id.itemPrice);
            tvPrice.setText("R " + description);

        }

        void setImg(Context c, String img) {

            ImageView imageView = (ImageView) mView.findViewById(R.id.itemIcon);
            Picasso.with(c).load(img).into(imageView);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), ShopsRecyclerView.class));
        finish();
    }
}