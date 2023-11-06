package com.example.smarthome;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarthome.databinding.ActivityHomeBinding;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);
//        binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
//                .setOpenableLayout(drawer)
//                .build();
        navigationView.setItemIconTintList(null);
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.nav_profile)
        {
            startActivity(new Intent(getApplicationContext(),view_profile.class));
        }if(id==R.id.nav_plan)
        {
            startActivity(new Intent(getApplicationContext(),view_plan.class));
        }if(id==R.id.nav_planhistory)
        {
            startActivity(new Intent(getApplicationContext(),view_plan_booking_history.class));
        }if(id==R.id.nav_designs)
        {
            startActivity(new Intent(getApplicationContext(),view_design_template_and_booking.class));
        }if(id==R.id.nav_designsBookings)
        {
            startActivity(new Intent(getApplicationContext(),view_design_booking_history.class));
        }if(id==R.id.nav_custplan)
        {
            startActivity(new Intent(getApplicationContext(),view_custom_plan_budget_and_accept.class));
        }if(id==R.id.nav_shops)
        {
            startActivity(new Intent(getApplicationContext(),View_shop.class));
        }if(id==R.id.nav_cart)
        {
            startActivity(new Intent(getApplicationContext(),View_cart_store.class));
        }if(id==R.id.nav_order)
        {
            startActivity(new Intent(getApplicationContext(),view_purchase_history.class));
        }if(id==R.id.nav_complaiint)
        {
            startActivity(new Intent(getApplicationContext(),viewreply.class));
        }if(id==R.id.nav_changep)
        {
            startActivity(new Intent(getApplicationContext(),change_password.class));
        }if(id==R.id.nav_logout)
        {
            startActivity(new Intent(getApplicationContext(),login.class));
        }
        if(id==R.id.navar)
        {
//            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.drakosha.augmentedr_1");
//            if (launchIntent != null) {
//                startActivity(launchIntent);
//            } else {
//                Toast.makeText(Home.this, "There is no package available in android", Toast.LENGTH_LONG).show();
//            }


            ComponentName cName = new ComponentName("com.drakosha.augmentedr_1","com.drakosha.augmentedr_1.ARPreview");
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setComponent(cName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);


        }
        return true;
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}