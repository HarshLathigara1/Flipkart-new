package lathigara.harsh.flipkart.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import lathigara.harsh.flipkart.Activities.AddAddressActivity;
import lathigara.harsh.flipkart.Activities.DeliveryActivity;
import lathigara.harsh.flipkart.Adapters.CartAdapter;
import lathigara.harsh.flipkart.Models.CartItemModel;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        button = view.findViewById(R.id.cart_Continue_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoActivity = new Intent(getContext(), AddAddressActivity.class);
                getContext().startActivity(gotoActivity);
            }
        });
        cartRecyclerView  =view.findViewById(R.id.cart_items_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        cartRecyclerView.setLayoutManager(layoutManager);
        List<CartItemModel> cartItemModelList = new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0,R.mipmap.oppo,"oppo F11 Pro",2,"Rs 49999","Rs 59999",1,0,0));
        cartItemModelList.add(new CartItemModel(0,R.mipmap.oppo,"oppo F11 Pro",0,"Rs 49999","Rs 59999",1,1,0));
        cartItemModelList.add(new CartItemModel(1,"Price (3 items)","Rs.160000","Free","Rs 100000","Rs500000"));
        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        cartRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        return view;
    }

}
