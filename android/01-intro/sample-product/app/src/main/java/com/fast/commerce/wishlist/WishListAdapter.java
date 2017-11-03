package com.fast.commerce.wishlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fast.commerce.R;
import com.fast.commerce.db.DatabaseManager;

import java.util.ArrayList;

import static com.fast.commerce.wishlist.WishlistManager.getWishList;

public class WishListAdapter extends RecyclerView.Adapter<WishListHolder> {

    private Context context;

    public WishListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public WishListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.wishlist_item, parent, false);
        return new WishListHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(WishListHolder holder, int position) {
        String text = WishlistManager.getWishList(context).get(position).getText();
        holder.mTextView.setText(text);
    }

    @Override
    public int getItemCount() {
        ArrayList<WishListItem> list = WishlistManager.getWishList(context);
        return list.size();
    }
}
