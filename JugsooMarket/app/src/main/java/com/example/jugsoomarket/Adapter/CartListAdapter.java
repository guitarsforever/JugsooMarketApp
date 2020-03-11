package com.example.jugsoomarket.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jugsoomarket.R;
import com.example.jugsoomarket.ViewHolder.CartViewHolder;
import com.example.jugsoomarket.ViewModel.item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartViewHolder> {
    private static final String PRODUCT_TOTAL_PRICE = "Product Total Price = $";
    private static final String QUANTITY = "Quantity = " ;
    Context C;
    private List <item> itemList;
    private int totalPrice = 0;

    public CartListAdapter () {
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_inflater, null);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        String itemNameString = (position+1) + ". "+itemList.get(position).getName();
        String itemPriceString =  PRODUCT_TOTAL_PRICE + (itemList.get(position).getTotalPrice());
        String itemQuantityString = QUANTITY + (itemList.get(position).getQuantity());
        holder.itemName.setText(itemNameString);
        holder.itemPrice.setText(itemPriceString);
        holder.itemQuantity.setText(itemQuantityString);
        Picasso.get().load(itemList.get(position).getThumbnail()).into(holder.itemImage);
        totalPrice = totalPrice + itemList.get(position).getTotalPrice();
    }

    @Override
    public int getItemCount() {
        if(itemList != null) {
            return itemList.size();
        } else {
            return 0;
        }
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public item getItemAt(int position) {
        return itemList.get(position);
    }

    public void setItemList (List<item> items) {
        this.itemList = items;
        notifyDataSetChanged();

    }
}
