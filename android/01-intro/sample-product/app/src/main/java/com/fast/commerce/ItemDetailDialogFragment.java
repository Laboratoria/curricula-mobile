package com.fast.commerce;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fast.commerce.cart.Cart;
import com.fast.commerce.cart.CartActivity;

public class ItemDetailDialogFragment extends DialogFragment {
    private ViewPager viewPager;
    private InventoryItem item;
    private TextView imageIndex;
    private Button cartButton;

    static ItemDetailDialogFragment newInstance() {
        return new ItemDetailDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_images_slider, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        imageIndex = (TextView) v.findViewById(R.id.image_index);
        TextView itemDescription = (TextView) v.findViewById(R.id.description);
        TextView itemTitle = (TextView) v.findViewById(R.id.title);

        v.findViewById(R.id.add_to_cart_button).setOnClickListener(new ClickListener());

        cartButton = (Button) v.findViewById(R.id.checkout_action_button);
        cartButton.setOnClickListener(new ClickListener());

        updateCartButton();

        item = (InventoryItem) getArguments().getSerializable("item");

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(pageChangeListener);

        itemDescription.setText(item.description);
        itemTitle.setText(item.title + ", $" + item.price);

        viewPager.setCurrentItem(0, true);
        displayPictureIndex(0);

        return v;
    }

    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int viewId = view.getId();
            switch (viewId) {
                case R.id.add_to_cart_button:
                    onAddToCart();
                    break;
                case R.id.checkout_action_button:
                    Cart cart = Cart.getInstance(getActivity());
                    if (cart.isEmpty()) {
                        Toast.makeText(getContext(), "Empty Cart", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(CartActivity.getIntent(getActivity()));
                    break;
            }
        }
    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            displayPictureIndex(position);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private void displayPictureIndex(int position) {
        imageIndex.setText((position + 1) + " of " + item.largePictureUrls.size());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    public void onAddToCart() {
        Cart.getInstance(getContext()).add(item.itemId);
        updateCartButton();
    }

    private void updateCartButton() {
        Cart cart = Cart.getInstance(getContext());
        if (cart.isEmpty()) {
            cartButton.setText("Empty");
            cartButton.setBackgroundColor(Color.LTGRAY);
            cartButton.setEnabled(false);
        } else {
            cartButton.setText("" + cart.getTotalCount() + " items $" + cart.getTotalPrice());
            cartButton.setBackgroundColor(0xff99cc00);  // holo_green_light
            cartButton.setEnabled(true);
        }
    }

    public class MyViewPagerAdapter extends PagerAdapter {

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            LayoutInflater layoutInflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.item_details, container, false);

            ImageView imageViewPreview = (ImageView) view.findViewById(R.id.detailed_image);

            Glide.with(ItemDetailDialogFragment.this)
                    .load(item.largePictureUrls.get(position))
                    .thumbnail(0.5f)
                    .crossFade()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageViewPreview);

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return item.largePictureUrls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == ((View) obj);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
