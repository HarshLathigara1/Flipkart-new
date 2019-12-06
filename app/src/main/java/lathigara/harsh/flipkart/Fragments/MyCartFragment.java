package lathigara.harsh.flipkart.Fragments;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lathigara.harsh.flipkart.Activities.AddAddressActivity;
import lathigara.harsh.flipkart.Activities.DeliveryActivity;
import lathigara.harsh.flipkart.Adapters.CartAdapter;
import lathigara.harsh.flipkart.DBQueries;
import lathigara.harsh.flipkart.Models.CartItemModel;
import lathigara.harsh.flipkart.Models.CategoryModel;
import lathigara.harsh.flipkart.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCartFragment extends Fragment {



    public MyCartFragment() {
        // Required empty public constructor
    }

    private RecyclerView cartRecyclerView;
    private Button button;
    private Dialog loadingDialog;
    public static CartAdapter cartAdapter;

    private TextView totalCartPrice;

    @Override
    public void onStart() {
        super.onStart();

        if (DBQueries.cartItemModelList.size() == 0){
            DBQueries.cartList.clear();
            DBQueries.loadCartList(getContext(),loadingDialog,true,new TextView(getContext()),totalCartPrice);
            loadingDialog.dismiss();

        }else {
            if (DBQueries.cartItemModelList.get(DBQueries.cartItemModelList.size()-1).getType() == CartItemModel.TOTAL_AMOUNT){


            }
            LinearLayout parent = (LinearLayout) totalCartPrice.getParent().getParent();
            parent.setVisibility(View.VISIBLE);
            loadingDialog.dismiss();
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        totalCartPrice = view.findViewById(R.id.totalCartPrice);

        button = view.findViewById(R.id.cart_Continue_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeliveryActivity.cartItemModelList = new ArrayList<>();
                DeliveryActivity.fromCart  =true;
                for (int x = 0; x<DBQueries.cartItemModelList.size();x++){
                    CartItemModel cartItemModel = DBQueries.cartItemModelList.get(x);
                    if (cartItemModel.isInStock()){
                        DeliveryActivity.cartItemModelList.add(cartItemModel);
                    }
                }
                DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));
               // if (DeliveryActivity.cartItemModelList)
                loadingDialog.show();
                if(DBQueries.addressesModelList.size() == 0) {
                    DBQueries.loadAddresses(getContext(), loadingDialog);
                }else {
                    loadingDialog.dismiss();
                    Intent delivery = new Intent(getContext(),DeliveryActivity.class);
                    startActivity(delivery);
                }

               // Intent gotoActivity = new Intent(getContext(), AddAddressActivity.class);
              //  getContext().startActivity(gotoActivity);
            }
        });
        cartRecyclerView  =view.findViewById(R.id.cart_items_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        cartRecyclerView.setLayoutManager(layoutManager);

        // adding data to adapter /fragment from DbQueries

      //  List<CartItemModel> cartItemModelList = new ArrayList<>();

       // cartItemModelList.add(new CartItemModel(1,"Price (3 items)","Rs.160000","Free","Rs 100000","Rs500000"));
         cartAdapter = new CartAdapter(DBQueries.cartItemModelList,totalCartPrice);
        cartRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();



        return view;
    }

}
