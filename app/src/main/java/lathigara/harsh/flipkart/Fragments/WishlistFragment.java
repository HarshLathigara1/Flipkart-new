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

import lathigara.harsh.flipkart.Adapters.WishlistAdapter;
import lathigara.harsh.flipkart.Models.WishlistModel;
import lathigara.harsh.flipkart.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistFragment extends Fragment {


    public WishlistFragment() {
        // Required empty public constructor
    }
    private RecyclerView wishlitRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        wishlitRecyclerView = view.findViewById(R.id.wishlist_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        wishlitRecyclerView.setLayoutManager(layoutManager);
        List<WishlistModel>list = new ArrayList<>();
        list.add(new WishlistModel(R.mipmap.oppo,"opp F11 Pro",2,"3",145,"Rs 49999","rs 599999","Cash On Delivery"));
        list.add(new WishlistModel(R.mipmap.oppo,"opp F11 Pro",2,"3",145,"Rs 49999","rs 599999","Cash On Delivery"));
        list.add(new WishlistModel(R.mipmap.oppo,"opp F11 Pro",2,"3",145,"Rs 49999","rs 599999","Cash On Delivery"));
        list.add(new WishlistModel(R.mipmap.oppo,"opp F11 Pro",2,"3",145,"Rs 49999","rs 599999","Cash On Delivery"));
        WishlistAdapter wishlistAdapter = new WishlistAdapter(list,true);
        wishlitRecyclerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();
        return view;
    }

}
