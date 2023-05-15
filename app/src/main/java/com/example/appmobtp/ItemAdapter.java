package com.example.appmobtp;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.bumptech.glide.annotation.GlideModule;

import kotlinx.coroutines.internal.SystemPropsKt;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    private List<Item> items;

    public ItemAdapter(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = items.get(position);

        holder.itemNameTextView.setText(item.getName());
        holder.itemDescriptionTextView.setText(item.getDescription());
        holder.itemPriceTextView.setText(item.getPrice());
        Glide.with(holder.itemView.getContext())
                .load(item.getImage())
                .into(holder.itemImageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
