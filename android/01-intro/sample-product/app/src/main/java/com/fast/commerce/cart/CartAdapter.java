package com.fast.commerce.cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fast.commerce.InventoryItem;
import com.fast.commerce.MainActivity;
import com.fast.commerce.R;

public class CartAdapter extends RecyclerView.Adapter<CartHolder> {

    private Context context;
    private Cart cart;

    public CartAdapter(Context context) {
        this.context = context;
        cart = Cart.getInstance(context);
    }

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.cart_item, parent, false);
        return new CartHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(CartHolder holder, int position) {
        String itemId = cart.getItemIdAtPosition(position);
        InventoryItem item = MainActivity.itemIdToInventoryMap.get(itemId);
        holder.mItemTitleText.setText(item.title);
        holder.mItemDescriptionText.setText(item.description);
        holder.mItemPriceText.setText("$" + item.price);
        holder.mItemQuantityText.setText("Quantity: " + cart.getQuantityForItem(itemId));
        Glide.with(context)
                .load(item.thumbnailUrl)
                .thumbnail(0.5f)
                .crossFade()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mItemThumnailImage);
    }

    @Override
    public int getItemCount() {
        return cart.getDistinctItemCount();
    }
}
