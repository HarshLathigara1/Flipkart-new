package lathigara.harsh.flipkart.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.PriorityQueue;

import lathigara.harsh.flipkart.Fragments.HomeFragment;
import lathigara.harsh.flipkart.Fragments.MyAccountFragment;
import lathigara.harsh.flipkart.Fragments.MyCartFragment;
import lathigara.harsh.flipkart.Fragments.MyOrdersFragment;
import lathigara.harsh.flipkart.Fragments.MyRewardsFragment;
import lathigara.harsh.flipkart.Fragments.OrderDetailsFragement;
import lathigara.harsh.flipkart.Fragments.WishlistFragment;
import lathigara.harsh.flipkart.R;

import static lathigara.harsh.flipkart.Activities.RegisterActivity.setSignUpFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FrameLayout frameLayout;
    private static final int HOME_FRAGMENT = 0;
    private static final int CART_FRAGMENT = 1;
    private static final int ORDER_FRAGMENT = 2;
    private static final int WISH_FRAGMENT = 3;
    private static final int REWARD_FRAGMENT = 4;
    private static final int ACCOUNT_FRAGMENT = 5;
    private static  int currentFragment = -1;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
         navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        frameLayout = findViewById(R.id.framLayout);
        setFragment(new HomeFragment(),HOME_FRAGMENT);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (currentFragment == HOME_FRAGMENT) {
            getSupportActionBar().setTitle("Home");
            getMenuInflater().inflate(R.menu.main, menu);

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.cart) {
            getSupportActionBar().setTitle("My Cart");
            myCart();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void myCart() {
        invalidateOptionsMenu();
        setFragment(new MyCartFragment(),CART_FRAGMENT);
        navigationView.getMenu().getItem(4).setChecked(true);


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            invalidateOptionsMenu();
            getSupportActionBar().setTitle("My Orders");
            setFragment(new MyOrdersFragment(),ORDER_FRAGMENT);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {


        } else if (id == R.id.nav_cart) {
          final   Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.sign_in_dialog);
            dialog.setCancelable(true);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            Button signIndialogBtn = dialog.findViewById(R.id.btnSignIn);
            Button signUpDialog  =dialog.findViewById(R.id.btnSiugnUp);
            final Intent intentRegister = new Intent(MainActivity.this,RegisterActivity.class);
            signIndialogBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    setSignUpFragment = false;
                        startActivity(intentRegister);
                }
            });

            signUpDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    setSignUpFragment = true;
                    startActivity(intentRegister);

                }
            });
            dialog.show();
            //getSupportActionBar().setTitle("My Cart");
           // myCart();

        } else if (id == R.id.nav_account) {
            getSupportActionBar().setTitle("My Account");
            setFragment(new MyAccountFragment(),ACCOUNT_FRAGMENT);

        } else if (id == R.id.nav_rewards) {
            getSupportActionBar().setTitle("My Rewards");
            setFragment(new MyRewardsFragment(),REWARD_FRAGMENT);


        } else if (id == R.id.nav_wishlist) {
            invalidateOptionsMenu();
            setFragment(new WishlistFragment(),WISH_FRAGMENT);

        }else if (id == R.id.nav_mall) {
            invalidateOptionsMenu();
            setFragment(new HomeFragment(),HOME_FRAGMENT);

        }else if (id == R.id.signout) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setFragment(Fragment fragment , int fragmentNo) {
        if (fragmentNo != currentFragment) {
            currentFragment = fragmentNo;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.commit();
        }
    }
}
