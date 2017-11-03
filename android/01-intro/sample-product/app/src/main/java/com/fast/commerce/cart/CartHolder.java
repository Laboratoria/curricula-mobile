package com.fast.commerce.cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fast.commerce.R;

public class CartHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView mItemTitleText;
    TextView mItemDescriptionText;
    TextView mItemPriceText;
    ImageView mItemThumnailImage;
    TextView mItemQuantityText;
    private Context mContext;
    private RecyclerView.Adapter mAdapter;
    Cart cart;

    public CartHolder(View view, Context context, RecyclerView.Adapter adapter) {
        super(view);
        Cart cart = Cart.getInstance(context);
        mContext = context;
        mAdapter = adapter;
        mItemTitleText = (TextView) view.findViewById(R.id.cart_item_title_text);
        mItemDescriptionText = (TextView) view.findViewById(R.id.cart_item_description_text);
        mItemPriceText = (TextView) view.findViewById(R.id.cart_item_price);
        mItemThumnailImage = (ImageView) view.findViewById(R.id.cart_item_thumbnail);
        mItemQuantityText = (TextView) itemView.findViewById(R.id.cart_item_quantity);

        view.setOnClickListener(this);
        itemView.findViewById(R.id.cart_item_delete).setOnClickListener(this);
        mItemQuantityText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int itemPosition = getAdapterPosition();
        String itemId = cart.getItemIdAtPosition(itemPosition);
//        switch (v.getId()) {
//            case R.id.cart_item_delete:
//                string += " delete";
//                WishlistManager.deleteItem(item, mContext);
//                mAdapter.notifyDataSetChanged();
//                Snackbar.make(v, " item deleted: " + string, Snackbar.LENGTH_LONG)
//                        .setAction("UNDO", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                WishlistManager.undoDeletion(mContext);
//                                mAdapter.notifyDataSetChanged();
//                            }
//                        })
//                        .show();
//                break;
//            case R.id.wishlist_item_edit_action:
//                string += " edit";
//                break;
//        }
//        Toast.makeText(mContext, string, Toast.LENGTH_SHORT).show();
    }
}
