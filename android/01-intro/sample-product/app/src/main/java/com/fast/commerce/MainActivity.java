package com.fast.commerce;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.fast.commerce.wishlist.WishListActivity;
import com.fast.commerce.ScrollingActivity;
import com.fast.commerce.LoginActivity;
import com.fast.commerce.register.DigitsHelper;
import com.fast.commerce.register.RegistrationFragment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity
        extends AppCompatActivity
        implements RegistrationFragment.OnRegistrationListener {

    // Only for debug purposes, if true registration step will be skipped.
    private static final boolean DEBUG_SKIP_REGISTRATION = true;

    public static final String TAG = "FastActivity";

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;

    public final static HashMap<String, InventoryItem> itemIdToInventoryMap = new HashMap<>();
    private static final ArrayList<InventoryItem> inventory = createMockInventory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            boolean isRegistered = isRegisteredUser();
            Log.d(TAG, "isRegistered = [" + isRegistered + "]"
                    + " skip = " + DEBUG_SKIP_REGISTRATION);
            if (!isRegistered && !DEBUG_SKIP_REGISTRATION) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_main, new RegistrationFragment())
                        .commitAllowingStateLoss();
                return;
            }
        }

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.showroom_recycler_view);
        findViewById(R.id.go_to_wishlist).setOnClickListener(new ClickListener());
        findViewById(R.id.login_activity).setOnClickListener(new ClickListener());

        ShowroomAdapter mAdapter = new ShowroomAdapter(getApplicationContext(), inventory);

        RecyclerView.LayoutManager mLayoutManager
                = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(
                new ShowroomAdapter.RecyclerTouchListener(getApplicationContext(),
                        recyclerView, new ShowroomAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", inventory.get(position));
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ItemDetailDialogFragment newFragment = ItemDetailDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onRegistration() {
        startAppHome();
    }

    private void startAppHome() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int viewId = view.getId();
            switch (viewId) {
                case R.id.go_to_wishlist:
                    startActivity(WishListActivity.getIntent(MainActivity.this));
                    break;
                case R.id.login_activity:
                    startActivity(LoginActivity.getIntent(MainActivity.this));
                    break;
            }
        }
    }

    private boolean isRegisteredUser() {
        final SharedPreferences sharedPreferences
                = getApplicationContext().getSharedPreferences(DigitsHelper.FAST_SP, MODE_PRIVATE);
        final String phoneNumber
                = sharedPreferences.getString(DigitsHelper.PHONE_NUMBER_KEY, null);
        Log.d(TAG, "phone number = " + phoneNumber);
        return phoneNumber != null;
    }

    private static ArrayList<InventoryItem> createMockInventory() {

        final String[] imageUrls = new String[]{
                "https://img0.etsystatic.com/154/0/14016314/il_570xN.1100179706_9njv.jpg",
                "https://img1.etsystatic.com/144/0/14016314/il_570xN.1152725447_77no.jpg",
                "https://img1.etsystatic.com/171/1/14016314/il_570xN.1124995581_pply.jpg",
                "https://img0.etsystatic.com/162/0/14016314/il_570xN.1100178362_opy0.jpg",
                "https://img1.etsystatic.com/160/1/14016314/il_570xN.1135194439_rilx.jpg",
                "https://img1.etsystatic.com/168/0/14016314/il_570xN.1125024375_n4p2.jpg",
                "https://img0.etsystatic.com/144/0/14016314/il_570xN.1078420166_fj4i.jpg",
                "https://img1.etsystatic.com/165/0/14016314/il_570xN.1135190689_aurv.jpg",
                "https://img1.etsystatic.com/152/0/14016314/il_570xN.1125017383_fxyz.jpg",
                "https://img0.etsystatic.com/163/0/14016314/il_570xN.1079049884_4mhu.jpg"};

        ArrayList<InventoryItem> inventory = new ArrayList<>();
        for (int i = 0; i < imageUrls.length; i++) {
            String id = "" + i;
            InventoryItem item = new InventoryItem(
                    id, "Cushion #" + (i + 1), imageUrls[i], new BigDecimal(i + 1));
            inventory.add(item);
            itemIdToInventoryMap.put(id, item);
        }
        return inventory;
    }

    // TODO(tgadh): Change this.
    private void addDrawerItems() {
        String[] osArray = { "Android", "iOS", "Windows", "OS X", "Linux" };
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(
                        MainActivity.this,
                        "Ship fast!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if (mDrawerToggle != null) {
            mDrawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mDrawerToggle != null) {
            mDrawerToggle.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Activate the navigation drawer toggle
        if (mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Fast");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("Fast");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
}
