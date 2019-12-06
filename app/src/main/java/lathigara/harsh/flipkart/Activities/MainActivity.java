package lathigara.harsh.flipkart.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.PriorityQueue;

import lathigara.harsh.flipkart.DBQueries;
import lathigara.harsh.flipkart.Fragments.HomeFragment;
import lathigara.harsh.flipkart.Fragments.MyAccountFragment;
import lathigara.harsh.flipkart.Fragments.MyCartFragment;
import lathigara.harsh.flipkart.Fragments.MyOrdersFragment;
import lathigara.harsh.flipkart.Fragments.MyRewardsFragment;
import lathigara.harsh.flipkart.Fragments.OrderDetailsFragement;
import lathigara.harsh.flipkart.Fragments.SigninFragment;
import lathigara.harsh.flipkart.Fragments.SignupFragment;
import lathigara.harsh.flipkart.Fragments.WishlistFragment;
import lathigara.harsh.flipkart.R;

import static lathigara.harsh.flipkart.Activities.RegisterActivity.setSignUpFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FrameLayout frameLayout;
    public static final int HOME_FRAGMENT = 0;
    private static final int CART_FRAGMENT = 1;
    private static final int ORDER_FRAGMENT = 2;
    private static final int WISH_FRAGMENT = 3;
    private static final int REWARD_FRAGMENT = 4;
    private static final int ACCOUNT_FRAGMENT = 5;
    private static int currentFragment = -1;
    private NavigationView navigationView;
    private Dialog dialog;
    private FirebaseUser currentUser;
    //  private ImageView noInternetConnection;
    public static Activity mainActivity;
    private TextView badgeCount;

    // for badge .//
    // public static  MenuItem cartItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // noInternetConnection =findViewById(R.id.no_internetConnection);


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

        setFragment(new HomeFragment(), HOME_FRAGMENT);


        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.sign_in_dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button signIndialogBtn = dialog.findViewById(R.id.btnSignIn);
        Button signUpDialog = dialog.findViewById(R.id.btnSiugnUp);
        final Intent intentRegister = new Intent(MainActivity.this, RegisterActivity.class);
        signIndialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SigninFragment.disableCloseBtn = true;
                dialog.dismiss();
                setSignUpFragment = false;
                startActivity(intentRegister);
            }
        });

        signUpDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignupFragment.disableCloseBtn = true;
                dialog.dismiss();
                setSignUpFragment = true;
                startActivity(intentRegister);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null){
           // invalidateOptionsMenu();
            setFragment(new HomeFragment(), HOME_FRAGMENT);
        }
        if (currentUser == null) {
            navigationView.getMenu().getItem(navigationView.getMenu().size() - 1).setEnabled(false);
        } else {
            navigationView.getMenu().getItem(navigationView.getMenu().size() - 1).setEnabled(true);

        }



        // invalidateOptionsMenu();

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (currentFragment == HOME_FRAGMENT) {
                currentFragment = -1;
                super.onBackPressed();

            } else {
                setFragment(new HomeFragment(), HOME_FRAGMENT);
                navigationView.getMenu().getItem(0).setChecked(true);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (currentFragment == HOME_FRAGMENT) {
            getMenuInflater().inflate(R.menu.main, menu);
            getSupportActionBar().setTitle("Home");
            MenuItem cartItem = menu.findItem(R.id.cart);



            cartItem.setActionView(R.layout.badge_layouit);
            ImageView badgeIcon = cartItem.getActionView().findViewById(R.id.badge_icon);
            badgeIcon.setImageResource(R.drawable.ic_cart);
            badgeCount = cartItem.getActionView().findViewById(R.id.badgeCount);
            if (currentUser != null) {
                if (DBQueries.cartList.size() == 0) {

                    DBQueries.loadCartList(MainActivity.this, new Dialog(MainActivity.this), false, badgeCount,new TextView(MainActivity.this));
                   // badgeCount.setVisibility(View.INVISIBLE);

                }else {

                        badgeCount.setVisibility(View.VISIBLE);


                    if (DBQueries.cartList.size() < 99){
                        badgeCount.setText(String.valueOf(DBQueries.cartList.size()));


                    }else {
                        badgeCount.setText("99");
                    }
                }

            }
            cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentUser == null) {
                        dialog.show();
                    } else {
                        myCart();
                    }
                }
            });


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
            if (currentUser == null) {
                dialog.show();
            } else {
                getSupportActionBar().setTitle("My Cart");
                myCart();

            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void myCart() {
        invalidateOptionsMenu();
        setFragment(new MyCartFragment(), CART_FRAGMENT);
        navigationView.getMenu().getItem(4).setChecked(true);


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (currentUser != null) {


            // Handle navigation view item clicks here.
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                invalidateOptionsMenu();
                getSupportActionBar().setTitle("My Orders");
                setFragment(new MyOrdersFragment(), ORDER_FRAGMENT);
                // Handle the camera action
            } else if (id == R.id.nav_gallery) {


            } else if (id == R.id.nav_cart) {
                if (currentUser == null) {
                    dialog.show();
                } else {
                    myCart();
                }


                //getSupportActionBar().setTitle("My Cart");
                // myCart();

            } else if (id == R.id.nav_account) {
                getSupportActionBar().setTitle("My Account");
                setFragment(new MyAccountFragment(), ACCOUNT_FRAGMENT);

            } else if (id == R.id.nav_rewards) {
                getSupportActionBar().setTitle("My Rewards");
                setFragment(new MyRewardsFragment(), REWARD_FRAGMENT);


            } else if (id == R.id.nav_wishlist) {
                invalidateOptionsMenu();
                setFragment(new WishlistFragment(), WISH_FRAGMENT);

            } else if (id == R.id.nav_mall) {
                invalidateOptionsMenu();
                setFragment(new HomeFragment(), HOME_FRAGMENT);

            } else if (id == R.id.signout) {
                FirebaseAuth.getInstance().signOut();
                DBQueries.clearData();
                Intent rIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(rIntent);
                finish();

            }
            drawer.closeDrawer(GravityCompat.START);
            return true;

        } else {
            drawer.closeDrawer(GravityCompat.START);
            dialog.show();
            return false;
        }


    }

    private void setFragment(Fragment fragment, int fragmentNo) {
        if (fragmentNo != currentFragment) {
            currentFragment = fragmentNo;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.commit();
        }
    }
}
