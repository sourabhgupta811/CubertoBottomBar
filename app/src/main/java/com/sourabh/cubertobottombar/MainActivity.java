package com.sourabh.cubertobottombar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;

import com.google.android.material.navigation.NavigationView;
import com.sourabh.cuberto_design.CubertoBottomBar;
import com.sourabh.cuberto_design.CubertoBottomBarItem;

public class MainActivity extends AppCompatActivity {
    CubertoBottomBar bottomBar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"));
        bottomBar = findViewById(R.id.bottom_bar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottomBar.setOnBottomBarTabSelectedListener(new CubertoBottomBar.OnBottomBarTabSelectedListener() {
            @Override
            public void onTabSelected(int tabPosition) {
                if(tabPosition==3){
                    if(drawerLayout.isDrawerOpen(GravityCompat.END)){
                        drawerLayout.closeDrawers();
                    }else{
                        drawerLayout.openDrawer(GravityCompat.END);
                    }
                }else{
                    drawerLayout.closeDrawers();
                }
            }
        });
        setUpBottomBar();
    }

    private void setUpBottomBar() {
        bottomBar.addItem(new CubertoBottomBarItem(0, "Home", R.drawable.ic_home));
        bottomBar.addItem(new CubertoBottomBarItem(1, "Events", R.drawable.ic_event));
        bottomBar.addItem(new CubertoBottomBarItem(2, "Messages", R.drawable.ic_message));
        bottomBar.addItem(new CubertoBottomBarItem(3, "menu", R.drawable.ic_menu));
    }
}
