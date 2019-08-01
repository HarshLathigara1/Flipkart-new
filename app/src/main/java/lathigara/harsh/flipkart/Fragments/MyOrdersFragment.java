package lathigara.harsh.flipkart.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lathigara.harsh.flipkart.Adapters.MyOrdersAdapter;
import lathigara.harsh.flipkart.Models.MyOrderItemModel;
import lathigara.harsh.flipkart.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrdersFragment extends Fragment {


    public MyOrdersFragment() {
        // Required empty public constructor
    }
    private RecyclerView myOrdersRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view =  inflater.inflate(R.layout.fragment_my_orders, container, false);
        myOrdersRecyclerView = view.findViewById(R.id.my_orders_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        myOrdersRecyclerView.setLayoutManager(layoutManager);
        List<MyOrderItemModel> list = new ArrayList<>();
        list.add(new MyOrderItemModel(R.mipmap.oppo,2,"oppo f11 Pro","Pending"));
        list.add(new MyOrderItemModel(R.mipmap.oppo,2,"oppo f11 Pro","Pending"));
        list.add(new MyOrderItemModel(R.mipmap.oppo,2,"oppo f11 Pro","Pending"));
        list.add(new MyOrderItemModel(R.mipmap.oppo,2,"oppo f11 Pro","Pending"));
        MyOrdersAdapter myOrdersAdapter = new MyOrdersAdapter(list);
        myOrdersRecyclerView.setAdapter(myOrdersAdapter);
        myOrdersAdapter.notifyDataSetChanged();

        return  view;
    }

}
