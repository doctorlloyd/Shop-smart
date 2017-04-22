package com.example.admin.shopsmart.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.admin.shopsmart.R;
import com.example.admin.shopsmart.pojos.Image;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class GalleryRecyclerView extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.global_gallery_content);
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Gallery");
        FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerViewGallery);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerAdapter<Image, GalleryViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Image, GalleryViewHolder>(
                Image.class,
                R.layout.gallery_raw_view,
                GalleryViewHolder.class,
                mDatabaseReference

        ) {
            @Override
            protected void populateViewHolder(GalleryViewHolder viewHolder, Image model, int position) {
                final String _key = getRef(position).getKey();
                viewHolder.setImageUrl(model.getImageUrl());
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }
private static class GalleryViewHolder extends RecyclerView.ViewHolder{

    View mView;

    public GalleryViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setImageUrl(String imageUrl){

        TextView tvName = (TextView) mView.findViewById(R.id.itemName);
        tvName.setText(imageUrl);
    }
}
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),ShopsRecyclerView.class));
        finish();
    }
}
