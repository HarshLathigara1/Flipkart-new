package lathigara.harsh.flipkart.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lathigara.harsh.flipkart.Adapters.ProductDetailsAdapter;
import lathigara.harsh.flipkart.Adapters.ProductImagesAdapter;
import lathigara.harsh.flipkart.Adapters.RewardAdapter;
import lathigara.harsh.flipkart.DBQueries;
import lathigara.harsh.flipkart.Fragments.SigninFragment;
import lathigara.harsh.flipkart.Fragments.SignupFragment;
import lathigara.harsh.flipkart.Models.CartItemModel;
import lathigara.harsh.flipkart.Models.ProductSpecificationModel;
import lathigara.harsh.flipkart.Models.RewardModel;
import lathigara.harsh.flipkart.Models.WishlistModel;
import lathigara.harsh.flipkart.R;

import static lathigara.harsh.flipkart.Activities.RegisterActivity.setSignUpFragment;


public class ProdutDetailsActivity extends AppCompatActivity {

    public static boolean running_wishList_query = false;
    public static boolean running_cart_query = false;


    private ViewPager productImagesViewPager;
    private TextView productTitle;
    private TextView averageRatingMiniView;
    private TextView totalRatingMiniView;
    private TextView productPrice;
    private TextView cuttedPrice;
    // cod //
    private ImageView codIndicatorImage;
    // cod //
    private TextView txtCod_indicator;

    private TextView rewardTitle, rewardContent;
    private TabLayout viewPagerIndicator;
    private Button coupenRedeemBtn;
    //2//
    private FirebaseFirestore firebaseFirestore;


    /// coupenDialog //

    public static TextView coupenTitle;
    public static TextView coupenExpiryDate;
    public static TextView coupenBody;
    private static RecyclerView coupenRecycleView;
    private static LinearLayout selectedCoupen;


    //    /// coupenDialog //
    // second view pager //
    //  private ConstraintLayout productDetailsOnlyContainer;
    private ConstraintLayout productDetailsTabContainer;
    // private TextView productOnlyDescriptionBody;
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTablayout;
    private String productDescription;
    private String productOtherDetails;
    private List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();
    // public  static int tabPosition = -1;
    // second view pager //
    public static FloatingActionButton addToWishlisBtn;
    // rating layout //
    private Dialog loadingDialog, signIndialog;

    private LinearLayout rateNowLayout;
    private TextView totalRatings;
    private LinearLayout ratingsNumberContainer;
    private TextView txtTotalFigure;
    private LinearLayout ratingsProgressBarContainer;
    private Dialog dialog;
    private Button buyNowbtn;
    private Button addToCart;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    // for wishlist //
    private FloatingActionButton floatingActionButton;

    public static String productid;
    private DocumentSnapshot documentSnapshot;
    // for wishlist //

    // rating layout //
    public static Boolean ADDED_TO_WISHLIST = false;
    public static Boolean ADDED_TO_CART = false;
    public static Activity ProdutDetailsActivityy;

    public static  MenuItem cartItem;
     private  TextView badgeCount;

    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser != null) {
            //// for wishList ////
            if (DBQueries.wishList.size() == 0) {
                DBQueries.loadWishList(ProdutDetailsActivity.this, loadingDialog, false);
                // loadingDialog.hide();
            } else {
                loadingDialog.dismiss();
            }

            //// for cart ////
           /* if (DBQueries.cartList.size() == 0) {
                DBQueries.loadCartList(ProdutDetailsActivity.this, loadingDialog, false,badgeCount);
                // loadingDialog.hide();
            }*/

        } else {
            loadingDialog.dismiss();

        }

        // for Added To in On Start //
        if (DBQueries.wishList.contains(productid)) {
            ADDED_TO_WISHLIST = true;
            addToWishlisBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#2874f0")));
        } else {
            ProdutDetailsActivity.addToWishlisBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
            ADDED_TO_WISHLIST = false;
        }


        if (DBQueries.cartList.contains(productid)) {
            ADDED_TO_CART = true;
            //addToWishlisBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#2874f0")));
        } else {
            // ProdutDetailsActivity.addToWishlisBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
            ADDED_TO_CART = false;
        }

        invalidateOptionsMenu();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_produt_details);
        productImagesViewPager = findViewById(R.id.products_images_view_pager);
        productTitle = findViewById(R.id.producttitile);
        averageRatingMiniView = findViewById(R.id.txtProductRating);
        totalRatingMiniView = findViewById(R.id.totalRatings);
        productPrice = findViewById(R.id.productPricee);
        cuttedPrice = findViewById(R.id.cuttedPricee);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        txtCod_indicator = findViewById(R.id.txt_cod_indicator);
        codIndicatorImage = findViewById(R.id.codIndicatorImage);
        rewardTitle = findViewById(R.id.rewardTitile);
        rewardContent = findViewById(R.id.rewardContent);
       // floatingActionButton = findViewById(R.id.BuyNowFloating);

        // addToWishlisBtn = findViewById(R.id.btnAddToWish);

        productDetailsTabContainer = findViewById(R.id.product_details_tabs_container);
        //  productDetailsOnlyContainer = findViewById(R.id.product_details_container);
        // productOnlyDescriptionBody = findViewById(R.id.product_details_body);
        // productDescription = (TextView)findViewById(R.id.tvHello);

        // ratings //
        totalRatings = findViewById(R.id.total_ratings);
        ratingsNumberContainer = findViewById(R.id.raitings_number_container);
        txtTotalFigure = findViewById(R.id.txtTotal);
        ratingsProgressBarContainer = findViewById(R.id.ratings_progress_container);
        addToCart = (Button) findViewById(R.id.btnCart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser == null) {
                    dialog.show();
                } else {
                    if (!running_cart_query) {
                        running_cart_query = true;
                        if (ADDED_TO_CART) {
                            // int index = DBQueries.wishList.indexOf(productid);
                            //  DBQueries.removeFromWishList(index, ProdutDetailsActivity.this);

                            // addToWishlisBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));

                        } else {

                            if (!running_cart_query) {
                                running_cart_query = true;
                                if (ADDED_TO_CART) {
                                    Toast.makeText(ProdutDetailsActivity.this, "Already Added To cart", Toast.LENGTH_LONG).show();

                                } else {
                                    Map<String, Object> addProduct = new HashMap<>();
                                    addProduct.put("product_ID_" + String.valueOf(DBQueries.cartList.size()), productid);
                                    addProduct.put("list_size", (long) DBQueries.wishList.size() + 1);

                                    firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_CART")
                                            .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                if (DBQueries.cartItemModelList.size() != 0) {
                                                    DBQueries.cartItemModelList.add(new CartItemModel(CartItemModel.CART_ITEM, productid, documentSnapshot.get("product_image_1").toString(), documentSnapshot.get("product_title").toString(),
                                                            (Long) documentSnapshot.get("free_coupens"), documentSnapshot.get("product_price").toString(), documentSnapshot.get("cutted_price").toString(), (long) 1,
                                                            (long) 0, (long) 0,(boolean)documentSnapshot.get("in_stock")));
                                                }
                                                ADDED_TO_CART = true;
                                                DBQueries.cartList.add(productid);
                                                Toast.makeText(ProdutDetailsActivity.this, "ADDED TO CART", Toast.LENGTH_LONG).show();
                                                running_cart_query = false;

                                            } else {
                                                running_cart_query = false;
                                            }
                                        }
                                    });
                                }
                            }

                        }
                    }

                }

            }
        });
        buyNowbtn = findViewById(R.id.buyNowbtn);
        buyNowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser == null) {
                    dialog.show();
                } else {
                    ///// testing purpose cart //
                    if (!running_cart_query) {
                        running_cart_query = true;
                        if (ADDED_TO_CART) {
                            running_cart_query = false;
                            Toast.makeText(ProdutDetailsActivity.this, "Already Added To cart", Toast.LENGTH_LONG).show();

                        } else {
                            Map<String, Object> addProduct = new HashMap<>();
                            addProduct.put("product_ID_" + String.valueOf(DBQueries.cartList.size()), productid);
                            addProduct.put("list_size", (long) DBQueries.cartList.size() + 1);

                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_CART")
                                    .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        if (DBQueries.cartItemModelList.size() != 0) {
                                            DBQueries.cartItemModelList.add(0,new CartItemModel(CartItemModel.CART_ITEM, productid, documentSnapshot.get("product_image_1").toString(), documentSnapshot.get("product_title").toString(),
                                                    (Long) documentSnapshot.get("free_coupens"), documentSnapshot.get("product_price").toString(), documentSnapshot.get("cutted_price").toString(), (long) 1,
                                                    (long) 0, (long) 0,(boolean)documentSnapshot.get("in_stock")));
                                        }
                                        ADDED_TO_CART = true;
                                        DBQueries.cartList.add(productid);
                                        Toast.makeText(ProdutDetailsActivity.this, "ADDED TO CART", Toast.LENGTH_LONG).show();
                                        invalidateOptionsMenu();
                                        running_cart_query = false;

                                    } else {
                                        running_cart_query = false;
                                    }
                                }
                            });
                        }
                    }

                    ///// testing purpose cart //

                    // Intent deliverIntent = new Intent(ProdutDetailsActivity.this, DeliveryActivity.class);
                    // startActivity(deliverIntent);
                }

            }
        });


        // ratings //


        viewPagerIndicator = findViewById(R.id.viewPagerIndicatore);
        //// loading dialog .///
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        //// loading dialog .///
        //2//
        firebaseFirestore = FirebaseFirestore.getInstance();
        final List<String> productImages = new ArrayList<>();
        productid = getIntent().getStringExtra("productId");
        firebaseFirestore.collection("PRODUCTS").document(productid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    documentSnapshot = task.getResult();
                    for (long x = 1; x < (long) documentSnapshot.get("no_of_product_images") + 1; x++) {
                        productImages.add(documentSnapshot.get("product_image_" + x).toString());

                    }
                    ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
                    productImagesViewPager.setAdapter(productImagesAdapter);
                    productTitle.setText(documentSnapshot.get("product_title").toString());
                    averageRatingMiniView.setText(documentSnapshot.get("average_rating").toString());
                    totalRatingMiniView.setText("(" + (long) documentSnapshot.get("total_rating") + ")ratings");
                    productPrice.setText(documentSnapshot.get("product_price").toString());
                    cuttedPrice.setText(documentSnapshot.get("cutted_price").toString());
                    //3//
                    if ((boolean) documentSnapshot.get("COD")) {
                        codIndicatorImage.setVisibility(View.VISIBLE);
                        txtCod_indicator.setVisibility(View.VISIBLE);
                    } else {
                        codIndicatorImage.setVisibility(View.INVISIBLE);
                        txtCod_indicator.setVisibility(View.INVISIBLE);

                    }
                    rewardTitle.setText((long) documentSnapshot.get("free_coupens") + documentSnapshot.get("free_coupen_title").toString());
                    rewardContent.setText(documentSnapshot.get("free_coupen_body").toString());
                    //---------------------------------------------------------------------------------//
                    if ((boolean) documentSnapshot.get("use_tab_layout")) {
                        productDetailsTabContainer.setVisibility(View.VISIBLE);
                        // productDetailsOnlyContainer.setVisibility(View.GONE);

                        //ProductDescriptionFragment.productDescription = documentSnapshot.get("product_description").toString();

                        productDescription = documentSnapshot.get("product_description").toString();

                        // under  test//
                        productOtherDetails = documentSnapshot.get("product_other_details").toString();
                        // ProductSpecificationFragment.productSpecificationModelList = new ArrayList<>();
                        //ProductSpecificationFragment.productSpecificationModelList.add()
                        for (long x = 1; x < (long) documentSnapshot.get("total_spec_titles") + 1; x++) {
                            productSpecificationModelList.add(new ProductSpecificationModel(0, documentSnapshot.get("spec_title_" + x).toString()));

                            for (long y = 1; y < (long) documentSnapshot.get("spec_title_" + x + "_total_fields") + 1; y++) {
                                productSpecificationModelList.add(new ProductSpecificationModel(1, documentSnapshot.get("spec_title_" + x + "_field_" + y + "_name").toString(), documentSnapshot.get("spec_title_" + x + "_field_" + y + "_value").toString()));


                            }
                        }


                    } else {
                        productDetailsTabContainer.setVisibility(View.GONE);
                        // productDetailsOnlyContainer.setVisibility(View.VISIBLE);
                        // productOnlyDescriptionBody.setText(documentSnapshot.get("product_description").toString());


                    }
                    //txt_cod_indicator.setText(documentSnapshot.get(""));
                    // ratings //
                       /* totalRatings.setText((long)documentSnapshot.get("total_rating")+"ratings");
                        for (int x =0; x < 5; x++){
                            TextView rating = (TextView) ratingsNumberContainer.getChildAt(x);
                            rating.setText(String.valueOf((long)documentSnapshot.get((5-x)+"_star")));

                            // progress //
                            ProgressBar progressBar = (ProgressBar) ratingsProgressBarContainer.getChildAt(x);
                             int maxProgress = Integer.parseInt(String.valueOf((long)documentSnapshot.get("total_rating")));
                            progressBar.setMax(maxProgress);
                            progressBar.setProgress(Integer.parseInt(String.valueOf((long)documentSnapshot.get((5-x)+"_star"))));

                            // progress //


                        }
                        txtTotalFigure.setText(String.valueOf((long)documentSnapshot.get("total_rating")));*/
                    productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTablayout.getTabCount(), productDescription, productOtherDetails, productSpecificationModelList));
                    // wishlist//
                    if (currentUser != null) {
                        if (DBQueries.wishList.size() == 0) {
                            DBQueries.loadWishList(ProdutDetailsActivity.this, loadingDialog, false);
                            // loadingDialog.hide();
                        } else {
                            loadingDialog.dismiss();
                        }

                        if (DBQueries.cartList.size() == 0) {
                            DBQueries.loadCartList(ProdutDetailsActivity.this, loadingDialog, false,badgeCount,new TextView(ProdutDetailsActivity.this));
                            // loadingDialog.hide();
                        }


                    } else {
                        loadingDialog.dismiss();

                    }
                    // for cart //
                    if (DBQueries.cartList.contains(productid)) {
                        ADDED_TO_CART = true;
                        //addToWishlisBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#2874f0")));
                    } else {
                        // ProdutDetailsActivity.addToWishlisBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                        ADDED_TO_CART = false;
                    }
                    // for cart //

                    // for wishList //
                    if (DBQueries.wishList.contains(productid)) {
                        ADDED_TO_WISHLIST = true;
                        addToWishlisBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#2874f0")));
                    } else {
                        ProdutDetailsActivity.addToWishlisBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                        ADDED_TO_WISHLIST = false;
                    }
                    // wishlist//

                    //// part 74 // out of stock feature //
                    if ((boolean)documentSnapshot.get("in_stock")){

                    }else {
                       // buyNowbtn.setVisibility(View.GONE);
                        addToCart.setVisibility(View.GONE);
                       // TextView outOfStock = (TextView)buyNowbtn.
                        buyNowbtn.setText("OUT of Stock");
                        buyNowbtn.setTextColor(Color.parseColor("#9e9e9e"));


                    }

                    //// part 74 // out of stock feature //


                } else {
                    loadingDialog.dismiss();
                    String error = task.getException().getMessage();
                    Toast.makeText(ProdutDetailsActivity.this, error, Toast.LENGTH_LONG).show();
                }
            }
        });

        viewPagerIndicator.setupWithViewPager(productImagesViewPager, true);
        addToWishlisBtn = findViewById(R.id.btnAddToWish);
        addToWishlisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser == null) {
                    addToWishlisBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                    //loadingDialog.show();
                    Toast.makeText(ProdutDetailsActivity.this, "Please Go to SignUp", Toast.LENGTH_LONG).show();

                } else {
                    //  addToWishlisBtn.setEnabled(false);
                    if (!running_wishList_query) {
                        running_wishList_query = true;
                        if (ADDED_TO_WISHLIST) {
                            int index = DBQueries.wishList.indexOf(productid);
                            DBQueries.removeFromWishList(index, ProdutDetailsActivity.this);

                            addToWishlisBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));

                        } else {

                            addToWishlisBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#2874f0")));

                            Map<String, Object> addProduct = new HashMap<>();
                            addProduct.put("product_ID_" + String.valueOf(DBQueries.wishList.size()), productid);
                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_WISHLIST")
                                    .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Map<String, Object> updateListSize = new HashMap<>();
                                        updateListSize.put("list_size", (long) DBQueries.wishList.size() + 1);
                                        firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_WISHLIST")
                                                .update(updateListSize).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    if (DBQueries.wishlistModelList.size() != 0) {
                                                        DBQueries.wishlistModelList.add(new WishlistModel(productid, documentSnapshot.get("product_image_1").toString(), documentSnapshot.get("product_full_title").toString(),
                                                                (Long) documentSnapshot.get("free_coupens"), documentSnapshot.get("average_rating").toString(), (Long) documentSnapshot.get("total_ratings"),
                                                                documentSnapshot.get("product_price").toString(), documentSnapshot.get("cutted_price").toString(), (boolean) documentSnapshot.get("COD"),(boolean)documentSnapshot.get("in_stock")
                                                        ));

                                                    }

                                                    ADDED_TO_WISHLIST = true;
                                                    //  addToWishlisBtn.setSupportImageTintList(getResources().getColorStateList(R.color.wishColor));
                                                    addToWishlisBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#2874f0")));
                                                    DBQueries.wishList.add(productid);
                                                    Toast.makeText(ProdutDetailsActivity.this, "Product Added To Wish List", Toast.LENGTH_LONG).show();


                                                } else {
                                                    String error = task.getException().getMessage();
                                                    Toast.makeText(ProdutDetailsActivity.this, error, Toast.LENGTH_LONG).show();

                                                }
                                                // addToWishlisBtn.setEnabled(true);
                                                running_wishList_query = false;
                                            }
                                        });

                                    } else {
                                        running_wishList_query = false;
                                        //  addToWishlisBtn.setEnabled(true);
                                        String error = task.getException().getMessage();
                                        Toast.makeText(ProdutDetailsActivity.this, error, Toast.LENGTH_LONG).show();

                                    }
                                }
                            });


                        }
                    }

                }

            }
        });
        // viewPAger //
        productDetailsViewPager =

                findViewById(R.id.productDetailsViewPager);

        productDetailsTablayout =

                findViewById(R.id.productDetailsTab);


        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTablayout));
        productDetailsTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // tabPosition = tab.getPosition();

                productDetailsViewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // viewPAger //
        // rating //

        rateNowLayout =

                findViewById(R.id.rate_now_container);
        for (
                int x = 0; x < rateNowLayout.getChildCount(); x++) {
            final int startPosition = x;
            rateNowLayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setRating(startPosition);
                }
            });
        }
        // rating //
        coupenRedeemBtn =

                findViewById(R.id.coupenRedemption);
        coupenRedeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog coupen = new Dialog(ProdutDetailsActivity.this);
                coupen.setContentView(R.layout.coupen_reddem_dialog);
                coupen.setCancelable(true);
                coupen.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ImageView openCoupenRecyclerView = coupen.findViewById(R.id.toggerl_recycler);
                coupenRecycleView = coupen.findViewById(R.id.coupensRecyclerView);
                selectedCoupen = coupen.findViewById(R.id.coupenContainer);
                coupenTitle = coupen.findViewById(R.id.coupen_title);
                coupenBody = coupen.findViewById(R.id.coupen_body);
                coupenExpiryDate = coupen.findViewById(R.id.coupen_validity);


                TextView originalPrice = coupen.findViewById(R.id.original_price);

                TextView discountedPrice = coupen.findViewById(R.id.discount_Price);

                LinearLayoutManager layoutManager = new LinearLayoutManager(ProdutDetailsActivity.this);
                layoutManager.setOrientation(RecyclerView.VERTICAL);
                coupenRecycleView.setLayoutManager(layoutManager);
                List<RewardModel> list = new ArrayList<>();
                list.add(new RewardModel("Cash Back", "5/2/2019", "til 2 june 2019"));
                list.add(new RewardModel("Cash Back", "5/2/2019", "til 2 june 2019"));
                list.add(new RewardModel("Cash Back", "5/2/2019", "til 2 june 2019"));
                list.add(new RewardModel("Cash Back", "5/2/2019", "til 2 june 2019"));
                RewardAdapter rewardAdapter = new RewardAdapter(list, true);
                coupenRecycleView.setAdapter(rewardAdapter);
                rewardAdapter.notifyDataSetChanged();
                openCoupenRecyclerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialogRecyclerView();
                    }
                });
                coupen.show();

            }
        });
        /// dialog //
        dialog = new

                Dialog(ProdutDetailsActivity.this);
        dialog.setContentView(R.layout.sign_in_dialog);
        dialog.setCancelable(true);
        dialog.getWindow().

                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button signIndialogBtn = dialog.findViewById(R.id.btnSignIn);
        Button signUpDialog = dialog.findViewById(R.id.btnSiugnUp);
        final Intent intentRegister = new Intent(ProdutDetailsActivity.this, RegisterActivity.class);
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
        /// dialog //
    }

    public static void showDialogRecyclerView() {
        if (coupenRecycleView.getVisibility() == View.GONE) {
            coupenRecycleView.setVisibility(View.VISIBLE);
            selectedCoupen.setVisibility(View.GONE);
        } else {
            coupenRecycleView.setVisibility(View.GONE);
            selectedCoupen.setVisibility(View.VISIBLE);
        }

    }

    private void setRating(int startPosition) {
        for (int x = 0; x < rateNowLayout.getChildCount(); x++) {
            ImageView starBtn = (ImageView) rateNowLayout.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if (x <= startPosition) {
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ProdutDetailsActivityy = null;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
            return true;
        } else if (id == R.id.cart) {
          //  invalidateOptionsMenu();

            return true;
        } else if (id == R.id.notification) {
            return true;
        } else if (id == android.R.id.home) {
            ProdutDetailsActivityy = null;
            finish();
            return true;
        }else if (id == R.id.action_settings){
            if (currentUser == null){
                signIndialog.show();
            }else {

                DeliveryActivity.fromCart = false;
                loadingDialog.show();
             //  ProdutDetailsActivityy =  ProdutDetailsActivity.this;
                DeliveryActivity.cartItemModelList = new ArrayList<>();
                if (DeliveryActivity.cartItemModelList.size() > 0) {
                    DeliveryActivity.cartItemModelList.clear();
                }
                DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.CART_ITEM, productid, documentSnapshot.get("product_image_1").toString(), documentSnapshot.get("product_title").toString(),
                        (Long) documentSnapshot.get("free_coupens"), documentSnapshot.get("product_price").toString(), documentSnapshot.get("cutted_price").toString(), (long) 1,
                        (long) 0, (long) 0,(boolean)documentSnapshot.get("in_stock")));

                DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));


                if(DBQueries.addressesModelList.size() == 0) {
                    DBQueries.loadAddresses(ProdutDetailsActivity.this, loadingDialog);
                }else {
                    loadingDialog.dismiss();
                    Intent delivery = new Intent(ProdutDetailsActivity.this,DeliveryActivity.class);
                    startActivity(delivery);
                }
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.prodcutmenu, menu);
         cartItem = menu.findItem(R.id.cart);
      //  getMenuInflater().inflate(R.menu.main, menu);
       // if (DBQueries.cartList.size() > 0){

            cartItem.setActionView(R.layout.badge_layouit);
            ImageView badgeIcon = cartItem.getActionView().findViewById(R.id.badge_icon);
            badgeIcon.setImageResource(R.drawable.ic_cart);
             badgeCount = cartItem.getActionView().findViewById(R.id.badgeCount);
           // badgeCount.setText(String.valueOf(DBQueries.cartList.size()));

            if (currentUser != null){
                if (DBQueries.cartList.size() == 0){
                    DBQueries.loadCartList(ProdutDetailsActivity.this,loadingDialog,false,badgeCount,new TextView(ProdutDetailsActivity.this));

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

                   /* if (currentUser == null){
                        dialog.show();
                    }else{
                        myCart();
                    }*/
                }
            });

        return true;
    }
}


