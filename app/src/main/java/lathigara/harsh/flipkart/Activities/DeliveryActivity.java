package lathigara.harsh.flipkart.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGActivity;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lathigara.harsh.flipkart.Adapters.CartAdapter;
import lathigara.harsh.flipkart.DBQueries;
import lathigara.harsh.flipkart.Models.CartItemModel;
import lathigara.harsh.flipkart.R;

public class DeliveryActivity extends AppCompatActivity {
    private RecyclerView myDelveryRecyclerView;
    private Button changeAdd;
     // logic //
    public static final int SELECT_ADDRESS = 0;
    private TextView totalAmount;
    // logic //

    //address //
        private TextView fullName,savedAdd,pinCode;

    //address //

     //part 75 cahanges

        public static List<CartItemModel>cartItemModelList;
    //part 75 cahanges

        // part 76 //
    private Button continueBtn;
    private Dialog loadingDialog;
    private Dialog payMentDialog;
    private ImageView payTm;

    private ConstraintLayout orderConfirmLayout;
    private ImageButton continueShoping;
    private TextView orderId;
    private boolean succesResponse = false;
    public static boolean fromCart;
    @Override
    protected void onStart() {
        super.onStart();
        fullName.setText(DBQueries.addressesModelList.get(DBQueries.selecteAdress).getFullname());
        savedAdd.setText(DBQueries.addressesModelList.get(DBQueries.selecteAdress).getAddress());
        pinCode.setText(DBQueries.addressesModelList.get(DBQueries.selecteAdress).getPincode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Delivery Details");
        orderConfirmLayout = findViewById(R.id.order_confirmation_ayout);
        orderConfirmLayout.setVisibility(View.GONE);
        continueShoping = (ImageButton)findViewById(R.id.continueShoppingButton);
        orderId = findViewById(R.id.orderId);

        myDelveryRecyclerView = findViewById(R.id.delivery_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        myDelveryRecyclerView.setLayoutManager(layoutManager);
       // List<CartItemModel>cartItemModelList = new ArrayList<>();
       // cartItemModelList.add(new CartItemModel(0,R.mipmap.oppo,"oppo F11 Pro",2,"Rs 49999","Rs 59999",1,0,0));
      //  cartItemModelList.add(new CartItemModel(0,R.mipmap.oppo,"oppo F11 Pro",0,"Rs 49999","Rs 59999",1,1,0));
      // cartItemModelList.add(new CartItemModel(1,"Price (3 items)","Rs.160000","Free","Rs 100000","Rs500000"));
        CartAdapter cartAdapter = new CartAdapter(cartItemModelList,totalAmount);
        myDelveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        changeAdd = findViewById(R.id.btnVhangeAddress);
        changeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoAdd = new Intent(DeliveryActivity.this,MyAddressActivity.class);
                gotoAdd.putExtra("MODE",SELECT_ADDRESS);
                startActivity(gotoAdd);

            }
        });

        totalAmount = findViewById(R.id.totalCartPrice);
        fullName = findViewById(R.id.fullName);
        savedAdd = findViewById(R.id.addressSaved);
        pinCode = findViewById(R.id.pinCodeDel);
        continueBtn = findViewById(R.id.cart_Continue_btn);
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       // loadingDialog.show();

        payMentDialog = new Dialog(this);
        payMentDialog.setContentView(R.layout.payment_method);
        payMentDialog.setCancelable(true);
        payMentDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        payMentDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // loadingDialog.show();
        payTm = payMentDialog.findViewById(R.id.payTmBtn);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // payMentDialog.show();
                 String order_id = UUID.randomUUID().toString().substring(0,28);
                 orderId.setText(order_id);
                 orderConfirmLayout.setVisibility(View.VISIBLE);
                 continueShoping.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         succesResponse = true;
                         if (MainActivity.mainActivity != null){
                             MainActivity.mainActivity.finish();
                             MainActivity.mainActivity = null;
                         }
                         if (ProdutDetailsActivity.ProdutDetailsActivityy != null){
                             ProdutDetailsActivity.ProdutDetailsActivityy.finish();
                             ProdutDetailsActivity.ProdutDetailsActivityy = null;
                         }

                         if (fromCart){

                             loadingDialog.show();

                             Map<String, Object> updateCartList = new HashMap<>();
                             long cartListSize = 0;
                             final List<Integer>indexList = new ArrayList<>();
                             for (int x = 0; x < DBQueries.cartList.size(); x++) {

                                if (!cartItemModelList.get(x).isInStock()){
                                    updateCartList.put("product_ID_"+cartListSize,cartItemModelList.get(x).getProductId());
                                    cartListSize++;
                                }else {
                                    indexList.add(x);
                                }

                             }
                             updateCartList.put("list_size", cartListSize);

                             FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_CART")
                                     .set(updateCartList).addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                     if (task.isSuccessful()){
                                         for (int x = 0;x<indexList.size();x++){
                                             DBQueries.cartList.remove(indexList.get(x).intValue());
                                             DBQueries.cartItemModelList.remove(indexList.get(x).intValue());
                                             DBQueries.cartItemModelList.remove(DBQueries.cartItemModelList.size()-1);



                                         }

                                     }else {
                                         String error = task.getException().getMessage();
                                         Toast.makeText(DeliveryActivity.this,error,Toast.LENGTH_LONG).show();
                                     }
                                     loadingDialog.dismiss();
                                     Intent toProduct  = new Intent(DeliveryActivity.this,MainActivity.class);
                                     startActivity(toProduct);

                                 }
                             });

                         }else {
                             Intent toProduct  = new Intent(DeliveryActivity.this,MainActivity.class);
                           //  toProduct.putExtra("HOME",MainActivity.HOME_FRAGMENT);
                             startActivity(toProduct);
                         }

                        // finish();

                     }
                 });



            }
        });

        payTm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payMentDialog.dismiss();
                loadingDialog.show();
                if (ContextCompat.checkSelfPermission(DeliveryActivity.this, Manifest.permission.READ_SMS)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(DeliveryActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
                }

                final String M_id = "WYqTYG04731238682595";
               final String Customer_id = FirebaseAuth.getInstance().getUid();
                final String order_id = UUID.randomUUID().toString().substring(0,28);
                String url = "https://ecommecrce.000webhostapp.com/paytm/generateChecksum.php";
               final String CallBackUrl = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";
                RequestQueue requestQueue = Volley.newRequestQueue(DeliveryActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //loadingDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.has("CHECKSUMHASH")){
                                String checksumHash = jsonObject.getString("CHECKSUMHASH");
                                PaytmPGService paytmPGService = PaytmPGService.getStagingService();
                                HashMap<String, String> paramMap = new HashMap<String,String>();
                                paramMap.put( "MID" , M_id);
                                paramMap.put( "ORDER_ID" , order_id);
                                paramMap.put( "CUST_ID" , Customer_id);
                                //  paramMap.put( "MOBILE_NO" , "7777777777");
                                //  paramMap.put( "EMAIL" , "username@emailprovider.com");
                                paramMap.put( "CHANNEL_ID" , "WAP");
                                paramMap.put( "TXN_AMOUNT" , totalAmount.getText().toString().substring(3,totalAmount.getText().length()-2));
                                paramMap.put( "WEBSITE" , "WEBSTAGING");
                                paramMap.put( "INDUSTRY_TYPE_ID" , "Retail");
                                paramMap.put( "CALLBACK_URL", CallBackUrl);
                                paramMap.put("CHECKSUMHAS",checksumHash);
                                PaytmOrder order = new PaytmOrder(paramMap);
                                paytmPGService.initialize(order,null);
                                paytmPGService.startPaymentTransaction(DeliveryActivity.this, true, true, new PaytmPaymentTransactionCallback() {
                                    @Override
                                    public void onTransactionResponse(Bundle inResponse) {
                                        Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();

                                    }

                                    @Override
                                    public void networkNotAvailable() {
                                        Toast.makeText(getApplicationContext(), "Network connection error: Check your internet connectivity", Toast.LENGTH_LONG).show();


                                    }

                                    @Override
                                    public void clientAuthenticationFailed(String inResponse) {
                                        Toast.makeText(getApplicationContext(), "Authentication failed: Server error" + inResponse.toString(), Toast.LENGTH_LONG).show();


                                    }

                                    @Override
                                    public void someUIErrorOccurred(String inErrorMessage) {
                                        Toast.makeText(getApplicationContext(), "UI Error " + inErrorMessage , Toast.LENGTH_LONG).show();


                                    }

                                    @Override
                                    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                                        Toast.makeText(getApplicationContext(), "Unable to load webpage " + inErrorMessage.toString(), Toast.LENGTH_LONG).show();


                                    }

                                    @Override
                                    public void onBackPressedCancelTransaction() {
                                        Toast.makeText(getApplicationContext(), "Transaction cancelled" , Toast.LENGTH_LONG).show();


                                    }

                                    @Override
                                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                                        Toast.makeText(getApplicationContext(), "Transaction cancelled" + inResponse.toString() , Toast.LENGTH_LONG).show();



                                    }
                                });
                            }
                        }catch (JSONException e){

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingDialog.dismiss();
                        Toast.makeText(DeliveryActivity.this,"something Went Wrong",Toast.LENGTH_LONG).show();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> paramMap = new HashMap<String,String>();
                        paramMap.put( "MID" , M_id);
                        paramMap.put( "ORDER_ID" , order_id);
                        paramMap.put( "CUST_ID" , Customer_id);
                      //  paramMap.put( "MOBILE_NO" , "7777777777");
                      //  paramMap.put( "EMAIL" , "username@emailprovider.com");
                        paramMap.put( "CHANNEL_ID" , "WAP");
                        paramMap.put( "TXN_AMOUNT" , totalAmount.getText().toString().substring(3,totalAmount.getText().length()-2));
                        paramMap.put( "WEBSITE" , "WEBSTAGING");
                        paramMap.put( "INDUSTRY_TYPE_ID" , "Retail");
                        paramMap.put( "CALLBACK_URL", CallBackUrl);
                      //  paramMap.put( "CHECKSUMHASH" , "w2QDRMgp1234567JEAPCIOmNgQvsi+BhpqijfM9KvFfRiPmGSt3Ddzw+oTaGCLneJwxFFq5mqTMwJXdQE2EzK4px2xruDqKZjHupz9yXev4=")
                       // PaytmOrder Order = new PaytmOrder(paramMap);
                        return  paramMap;
                    }
                };
                requestQueue.add(stringRequest);



            }
        });


    }

    @Override
    public void onBackPressed() {
        if (succesResponse){
            finish();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        loadingDialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
