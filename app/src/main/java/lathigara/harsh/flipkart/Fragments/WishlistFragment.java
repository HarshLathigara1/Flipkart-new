package lathigara.harsh.flipkart.Fragments;


import android.app.Dialog;
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
import lathigara.harsh.flipkart.DBQueries;
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
    private Dialog loadingDialog;
    public  static WishlistAdapter wishlistAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        wishlitRecyclerView = view.findViewById(R.id.wishlist_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        wishlitRecyclerView.setLayoutManager(layoutManager);
       // List<WishlistModel>list = new ArrayList<>();
        if(DBQueries.wishlistModelList.size() == 0){
            DBQueries.wishList.clear();
            DBQueries.loadWishList(getContext(),loadingDialog,true);
        }else {
            loadingDialog.dismiss();
        }

         wishlistAdapter = new WishlistAdapter( DBQueries.wishlistModelList,true);
        wishlitRecyclerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();
        return view;
    }

}
