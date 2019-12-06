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

import lathigara.harsh.flipkart.Activities.ProdutDetailsActivity;
import lathigara.harsh.flipkart.Adapters.ProductSpecificationAdapter;
import lathigara.harsh.flipkart.Models.ProductSpecificationModel;
import lathigara.harsh.flipkart.R;



/**
 * A simple {@link Fragment} subclass.
 */
public class ProductSpecificationFragment extends Fragment {
    private RecyclerView productRecyclerView;
     public  static List<ProductSpecificationModel>productSpecificationModelList;


    public ProductSpecificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_specification, container, false);
        productRecyclerView = view.findViewById(R.id.productSpecificationRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        productRecyclerView.setLayoutManager(linearLayoutManager);

       /* productSpecificationModelList.add(new ProductSpecificationModel(0, "General"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "RAM", "4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "RAM", "4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "RAM", "4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "RAM", "4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "RAM", "4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(0, "Display"));
        productSpecificationModelList.add(new ProductSpecificationModel(0, "General"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "RAM", "4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "RAM", "4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "RAM", "4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "RAM", "4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(1, "RAM", "4GB"));*/


        ProductSpecificationAdapter productSpecificationAdapter = new ProductSpecificationAdapter(productSpecificationModelList);

        productRecyclerView.setAdapter(productSpecificationAdapter);
        productSpecificationAdapter.notifyDataSetChanged();
        return view;
    }

}
