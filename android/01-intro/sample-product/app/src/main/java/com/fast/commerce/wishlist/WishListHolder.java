package com.fast.commerce.wishlist;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fast.commerce.R;

import static com.paypal.android.sdk.onetouch.core.metadata.ah.w;

public class WishListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView mTextView;
    private Context mContext;
    private RecyclerView.Adapter mAdapter;

    public WishListHolder(View view, Context context, RecyclerView.Adapter adapter) {
        super(view);
        mContext = context;
        mAdapter = adapter;
        view.setOnClickListener(this);
        mTextView = (TextView) view.findViewById(R.id.wishlist_item_text);
        itemView.findViewById(R.id.wishlist_item_delete_action).setOnClickListener(this);
        itemView.findViewById(R.id.wishlist_item_edit_action).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int itemPosition = getAdapterPosition();
        WishListItem item = WishlistManager.getWishList(mContext).get(itemPosition);
        String string = item.getText();
        switch (v.getId()) {
            case R.id.wishlist_item_delete_action:
                string += " delete";
                WishlistManager.deleteItem(item, mContext);
                mAdapter.notifyDataSetChanged();
                Snackbar.make(v, "Wishlist item deleted: " + string, Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                WishlistManager.undoDeletion(mContext);
                                mAdapter.notifyDataSetChanged();
                            }
                        })
                        .show();
                break;
            case R.id.wishlist_item_edit_action:
                string += " edit";
                break;
        }
        Toast.makeText(mContext, string, Toast.LENGTH_SHORT).show();
    }
}
