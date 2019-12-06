package lathigara.harsh.flipkart;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lathigara.harsh.flipkart.Activities.AddAddressActivity;
import lathigara.harsh.flipkart.Activities.DeliveryActivity;
import lathigara.harsh.flipkart.Activities.ProdutDetailsActivity;
import lathigara.harsh.flipkart.Adapters.CategoryaAdapter;
import lathigara.harsh.flipkart.Adapters.HomePageAdapter;
import lathigara.harsh.flipkart.Fragments.HomeFragment;
import lathigara.harsh.flipkart.Fragments.MyCartFragment;
import lathigara.harsh.flipkart.Fragments.WishlistFragment;
import lathigara.harsh.flipkart.Models.AddressesModel;
import lathigara.harsh.flipkart.Models.CartItemModel;
import lathigara.harsh.flipkart.Models.CategoryModel;
import lathigara.harsh.flipkart.Models.HomePageModel;
import lathigara.harsh.flipkart.Models.HorizontalProductScrollModel;
import lathigara.harsh.flipkart.Models.SliderModel;
import lathigara.harsh.flipkart.Models.WishlistModel;

public class DBQueries {
    // for address //
    // public static boolean addressSelected = false;


    // for address //
    //
    //  public  static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    // public static FirebaseUser  currentUser = firebaseAuth.getCurrentUser();
    //
    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
    public static List<String> wishList = new ArrayList<>();
    // public static final List<HomePageModel> homePageModelList = new ArrayList<>();
    // categories //
    public static List<List<HomePageModel>> list = new ArrayList<>();
    public static List<String> loadedCategoriesNames = new ArrayList<>();
    public static List<WishlistModel> wishlistModelList = new ArrayList<>();

    // for Cart //
    public static List<String> cartList = new ArrayList<>();
    public static List<CartItemModel> cartItemModelList = new ArrayList<>();
    // for Cart //

    // for adress //
    public static List<AddressesModel> addressesModelList = new ArrayList<>();
    public static int selecteAdress = -1;

    // for adress //

    public static void loadCategories(final CategoryaAdapter categoryaAdapter, final Context context) {
        categoryModelList.clear();
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(), documentSnapshot.get("categoryName").toString()));
                            }
                            categoryaAdapter.notifyDataSetChanged();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public static void loadFragmentData(final HomePageAdapter homePageAdapter, final Context context, final int index, String categoryName) {
        firebaseFirestore.collection("CATEGORIES").document(categoryName.toUpperCase()).collection("TOP_DEALS").orderBy("index").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        if ((long) documentSnapshot.get("view_type") == 0) {
                            List<SliderModel> sliderModelList = new ArrayList<>();
                            long no_of_banners = (long) documentSnapshot.get("no_of_banners");
                            for (long x = 1; x < no_of_banners + 1; x++) {
                                sliderModelList.add(new SliderModel(documentSnapshot.get("banner_" + x).toString(), documentSnapshot.get("banner_" + x + "_background").toString()));
                            }
                            list.get(index).add(new HomePageModel(0, sliderModelList));

                        } else if ((long) documentSnapshot.get("view_type") == 1) {
                            list.get(index).add(new HomePageModel(1, documentSnapshot.get("strip_ad_banner").toString(), documentSnapshot.get("background").toString()));

                        } else if ((long) documentSnapshot.get("view_type") == 2) {
                            List<WishlistModel> ViewAllactivitywishlistModelList = new ArrayList<>();


                            List<HorizontalProductScrollModel> horizontalProductScrollModels = new ArrayList<>();

                            long no_of_products = (long) documentSnapshot.get("no_of_products");
                            for (long x = 1; x < no_of_products + 1; x++) {
                                // horizontalProductScrollModels.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID"+x).toString(),(documentSnapshot.get("product_image"+x).toString(),(documentSnapshot.get("product_title"+x).toString(),(documentSnapshot.get("product_subtitle"+x).toString(),(documentSnapshot.get("product_price"+x).toString()));
                                horizontalProductScrollModels.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_" + x).toString(),
                                        documentSnapshot.get("product_image_" + x).toString(), documentSnapshot.get("product_title_" + x).toString(), documentSnapshot.get("product_subtitle_" + x).toString(), documentSnapshot.get("product_price_" + x).toString()));

                                ViewAllactivitywishlistModelList.add(new WishlistModel(documentSnapshot.get("product_ID_" + x).toString(), documentSnapshot.get("product_image_" + x).toString(), documentSnapshot.get("product_full_title_" + x).toString(),
                                        (Long) documentSnapshot.get("free_coupens_" + x), documentSnapshot.get("average_rating_" + x).toString(), (Long) documentSnapshot.get("total_ratings_" + x),
                                        documentSnapshot.get("product_price_" + x).toString(), documentSnapshot.get("cutted_price_" + x).toString(), (boolean) documentSnapshot.get("COD_" + x), (boolean) documentSnapshot.get("in_stock_" + x)
                                ));
                            }
                            list.get(index).add(new HomePageModel(2, documentSnapshot.get("layout_title").toString(), documentSnapshot.get("layout_background").toString(), horizontalProductScrollModels, ViewAllactivitywishlistModelList));

                        } else if ((long) documentSnapshot.get("view_type") == 3) {
                            List<HorizontalProductScrollModel> gridProductList = new ArrayList<>();
                            long no_of_products = (long) documentSnapshot.get("no_of_products");
                            for (long x = 1; x < no_of_products + 1; x++) {
                                // horizontalProductScrollModels.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID"+x).toString(),(documentSnapshot.get("product_image"+x).toString(),(documentSnapshot.get("product_title"+x).toString(),(documentSnapshot.get("product_subtitle"+x).toString(),(documentSnapshot.get("product_price"+x).toString()));
                                gridProductList.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_" + x).toString(),
                                        documentSnapshot.get("product_image_" + x).toString(), documentSnapshot.get("product_title_" + x).toString(), documentSnapshot.get("product_subtitle_" + x).toString(), documentSnapshot.get("product_price_" + x).toString()));

                            }
                            list.get(index).add(new HomePageModel(3, documentSnapshot.get("layout_title").toString(), documentSnapshot.get("layout_background").toString(), gridProductList));

                        }

                    }
                    homePageAdapter.notifyDataSetChanged();
                    HomeFragment.swipeRefreshLayout.setRefreshing(false);
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public static void loadWishList(final Context context, final Dialog dialog, final Boolean loadProductData) {
        wishList.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_WISHLIST")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                        wishList.add(task.getResult().get("product_ID_" + x).toString());
                        if (loadProductData) {
                            wishlistModelList.clear();
                            final String productId = task.getResult().get("product_ID_" + x).toString();
                            if (DBQueries.wishList.contains(ProdutDetailsActivity.productid)) {
                                ProdutDetailsActivity.ADDED_TO_WISHLIST = true;
                                if (ProdutDetailsActivity.addToWishlisBtn != null) {
                                    ProdutDetailsActivity.addToWishlisBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#2874f0")));


                                }
                            } else {
                                if (ProdutDetailsActivity.addToWishlisBtn != null) {
                                    ProdutDetailsActivity.addToWishlisBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));


                                }
                                ProdutDetailsActivity.ADDED_TO_WISHLIST = false;
                            }


                            firebaseFirestore.collection("PRODUCTS").document(productId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {

                                        wishlistModelList.add(new WishlistModel(productId, task.getResult().get("product_image_1").toString(), task.getResult().get("product_title").toString(),
                                                (Long) task.getResult().get("free_coupens"), task.getResult().get("average_rating").toString(), (Long) task.getResult().get("total_ratings"),
                                                task.getResult().get("product_price").toString(), task.getResult().get("cutted_price").toString(), (boolean) task.getResult().get("COD"),
                                                (boolean) task.getResult().get("in_stock")
                                        ));


                                        WishlistFragment.wishlistAdapter.notifyDataSetChanged();


                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(context, error, Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        }
                    }


                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        });
    }

    public static void removeFromWishList(final int index, final Context context) {
        final String removeProductId = wishList.get(index);
        wishList.remove(index);
        Map<String, Object> updateWishList = new HashMap<>();
        for (int x = 0; x < wishList.size(); x++) {
            updateWishList.put("product_ID_" + x, wishList.get(x));

        }
        updateWishList.put("list_size", (long) wishList.size());

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_WISHLIST")
                .set(updateWishList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (wishlistModelList.size() != 0) {
                        wishlistModelList.remove(index);
                        WishlistFragment.wishlistAdapter.notifyDataSetChanged();
                    }
                    ProdutDetailsActivity.ADDED_TO_WISHLIST = false;
                    Toast.makeText(context, "remove Sucessfully", Toast.LENGTH_LONG).show();

                } else {
                    if (ProdutDetailsActivity.addToWishlisBtn != null) {
                        ProdutDetailsActivity.addToWishlisBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#2874f0")));
                    }

                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show();

                }

                ProdutDetailsActivity.running_wishList_query = false;
            }
        });
    }

    public static void clearData() {
        categoryModelList.clear();
        list.clear();
        loadedCategoriesNames.clear();
        wishList.clear();
        wishlistModelList.clear();
        cartList.clear();
        cartItemModelList.clear();
        addressesModelList.clear();



    }

    //// cart //
    public static void loadCartList(final Context context, final Dialog dialog, final boolean loadProductData, final TextView badgeCount, final TextView totalCartPrice) {

        cartList.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_CART")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                        cartList.add(task.getResult().get("product_ID_" + x).toString());
                        if (DBQueries.cartList.contains(ProdutDetailsActivity.productid)) {
                            ProdutDetailsActivity.ADDED_TO_CART = true;
                        } else {
                            ProdutDetailsActivity.ADDED_TO_CART = false;
                        }
                        if (loadProductData) {
                            cartItemModelList.clear();
                            final String productId = task.getResult().get("product_ID_" + x).toString();
                           /* if (DBQueries.wishList.contains(ProdutDetailsActivity.productid)) {
                                ProdutDetailsActivity.ADDED_TO_WISHLIST = true;
                                if (ProdutDetailsActivity.addToWishlisBtn != null) {
                                    ProdutDetailsActivity.addToWishlisBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#2874f0")));


                                }
                            } else {
                                if (ProdutDetailsActivity.addToWishlisBtn != null) {
                                    ProdutDetailsActivity.addToWishlisBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));


                                }
                                ProdutDetailsActivity.ADDED_TO_WISHLIST = false;
                            }*/


                            firebaseFirestore.collection("PRODUCTS").document(productId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        int index = 0;
                                        if (cartList.size() >= 2) {
                                            index = cartList.size() - 2;
                                        }

                                        cartItemModelList.add(index, new CartItemModel(CartItemModel.CART_ITEM, productId, task.getResult().get("product_image_1").toString(), task.getResult().get("product_title").toString(),
                                                (Long) task.getResult().get("free_coupens"), task.getResult().get("product_price").toString(), task.getResult().get("cutted_price").toString(), (long) 1,
                                                (long) 0, (long) 0, (boolean) task.getResult().get("in_stock")));

                                        if (cartList.size() == 1) {
                                            cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));
                                            LinearLayout parent = (LinearLayout) totalCartPrice.getParent().getParent();
                                            parent.setVisibility(View.VISIBLE);

                                        }
                                        if (cartList.size() == 0) {
                                            cartItemModelList.clear();
                                        }


                                        MyCartFragment.cartAdapter.notifyDataSetChanged();


                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(context, error, Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        }

                    }
                    if (cartList.size() != 0) {
                        badgeCount.setVisibility(View.VISIBLE);

                    } else {

                        badgeCount.setVisibility(View.INVISIBLE);

                    }
                    if (DBQueries.cartList.size() < 99) {
                        badgeCount.setText(String.valueOf(DBQueries.cartList.size()));


                    } else {
                        badgeCount.setText("99");
                    }


                }


            }


        });/* else{
            String error = task.getException().getMessage();
            Toast.makeText(context, error, Toast.LENGTH_LONG).show();
        }
        dialog.dismiss();*/

    }

    public static void removeFromCart(final int index, final Context context, final TextView totalCartPrice) {
        final String removeProductId = cartList.get(index);
        cartList.remove(index);
        Map<String, Object> updateCartList = new HashMap<>();
        for (int x = 0; x < cartList.size(); x++) {
            updateCartList.put("product_ID_" + x, cartList.get(x));

        }
        updateCartList.put("list_size", (long) cartList.size());

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_CART")
                .set(updateCartList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (cartItemModelList.size() != 0) {
                        cartItemModelList.remove(index);
                        MyCartFragment.cartAdapter.notifyDataSetChanged();
                    }
                    if (cartList.size() == 0) {
                        LinearLayout parent = (LinearLayout) totalCartPrice.getParent().getParent();
                        parent.setVisibility(View.GONE);
                        cartItemModelList.clear();

                    }
                    /*if (ProdutDetailsActivity.cartItem != null) {
                        ProdutDetailsActivity.cartItem.setActionView(null);
                    }*/


                    // ProdutDetailsActivity.ADDED_TO_CART  = false;
                    Toast.makeText(context, "removed from cart Sucessfully", Toast.LENGTH_LONG).show();

                } else {
                    cartList.add(index, removeProductId);

                   /* if (ProdutDetailsActivity.addToWishlisBtn != null) {
                        ProdutDetailsActivity.addToWishlisBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#2874f0")));
                    }*/

                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show();

                }

                ProdutDetailsActivity.running_cart_query = false;
            }
        });


    }


    //// cart //

    // for Address //
    public static void loadAddresses(final Context context, final Dialog loadingDialog) {
        addressesModelList.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_ADDRESSES")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {


                    Intent deliveryIntent;

                    if ((long) task.getResult().get("list_size") == 0) {
                        deliveryIntent = new Intent(context, AddAddressActivity.class);
                        deliveryIntent.putExtra("INTENT", "deliveryIntent");
                        // addressSelected = false;
                    } else {
                        //  addressSelected = true;
                        for (long x = 1; x < (long) task.getResult().get("list_size") + 1; x++) {
                            addressesModelList.add(new AddressesModel(task.getResult().get("fullname_" + x).toString(),
                                    task.getResult().get("address_" + x).toString(),
                                    task.getResult().get("pincode_" + x).toString(),
                                    (boolean) task.getResult().get("selected_" + x)
                            ));
                            if ((boolean) task.getResult().get("selected_" + x)) {
                                selecteAdress = Integer.parseInt(String.valueOf(x - 1));
                            }
                        }
                        deliveryIntent = new Intent(context, DeliveryActivity.class);


                    }
                    context.startActivity(deliveryIntent);


                } else {
                    String error = task.getException().getMessage();
                    //  Toast.makeText(context,error,Toast.LENGTH_LONG).show();
                }
                loadingDialog.dismiss();
            }
        });
    }
    // for Address //

}
