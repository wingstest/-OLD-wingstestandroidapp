package com.example.blinktasker.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blinktasker.ApiService;
import com.example.blinktasker.ApiServiceBuilder;
import com.example.blinktasker.Fragments.DeliveryFragment;
import com.example.blinktasker.Fragments.OrderListFragment;
import com.example.blinktasker.Fragments.StatisticFragment;
import com.example.blinktasker.R;
import com.example.blinktasker.Utils.CircleTransform;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverMainActivity extends AppCompatActivity {


    private DrawerLayout mDrawerLayout;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerLayout = findViewById(R.id.drawer_layout_driver);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        int id = menuItem.getItemId();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        if (id == R.id.nav_orders) {

                            transaction.replace(R.id.content_frame, new OrderListFragment()).commit();
                        } else if (id == R.id.nav_delivery) {

                            transaction.replace(R.id.content_frame, new DeliveryFragment()).commit();


                        } else if (id == R.id.nav_statistic) {
                            transaction.replace(R.id.content_frame, new StatisticFragment()).commit();


                        } else if (id == R.id.nav_logout) {

                            logoutToServer(sharedPref.getString("token", ""));
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.remove("token");
                            editor.apply();


                            finishAffinity();
                            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                            startActivity(intent);


                        }

                        return true;
                    }
                });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, new OrderListFragment()).commit();

        sharedPref = getSharedPreferences("MY_KEY", Context.MODE_PRIVATE);

        View header = navigationView.getHeaderView(0);
        ImageView customer_avatar = (ImageView) header.findViewById(R.id.customer_avatar);
        TextView customer_name = header.findViewById(R.id.customer_name);

        customer_name.setText(sharedPref.getString("name", ""));
        Picasso.with(this).load(sharedPref.getString("avatar", "")).transform(new CircleTransform()).into(customer_avatar);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
    }


    private void logoutToServer(final String token) {
        ApiService service = ApiServiceBuilder.getService();

        Call<Void> call = service.getToken(token, "TjaTk1dt14vDi016DrvxIMBi5cchdd6lf3EKY98u", "NA86n5QFbiKdI27JrdMWebrltxbVX9ApNe29600vBQyUh7CdwZoOBEy5VKlUzOLhAj6VzuS3PppQCI7lZln34dWMx3AcVHwpYCgtigbQPHNdUrtwX8asAgRMSA4y9mxU");


        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(DriverMainActivity.this, "DRIVER SUCCESS RESPONSE RETROFIT " + response.code(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(DriverMainActivity.this, "DRIVER FAILURE RETROFIT " + t.getMessage().toString() + t.getCause() + t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
 

    }

}
