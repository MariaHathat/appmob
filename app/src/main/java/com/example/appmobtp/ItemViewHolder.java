package com.example.appmobtp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView itemNameTextView;
    public TextView itemDescriptionTextView;
    public TextView itemPriceTextView;
    public ImageView itemImageView;

    public ItemViewHolder(View itemView) {
        super(itemView);

        itemNameTextView = itemView.findViewById(R.id.item_name_text_view);
        itemDescriptionTextView = itemView.findViewById(R.id.item_description_text_view);
        itemImageView = itemView.findViewById(R.id.item_image_view);
        itemPriceTextView = itemView.findViewById(R.id.item_price_text_view);
    }



}