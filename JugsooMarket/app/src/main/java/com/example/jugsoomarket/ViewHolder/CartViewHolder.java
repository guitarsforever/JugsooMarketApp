package com.example.jugsoomarket.ViewHolder;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jugsoomarket.Adapter.ItemClickListener;
import com.example.jugsoomarket.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView itemName, itemPrice, itemQuantity;
    public ImageView itemImage;
    ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        itemName = itemView.findViewById(R.id.itemName);
        itemPrice = itemView.findViewById(R.id.itemPrice);
        itemQuantity = itemView.findViewById(R.id.itemQuantity);
        itemImage = itemView.findViewById(R.id.itemImage);

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onItemClickListener(v, getAdapterPosition());
    }
}
