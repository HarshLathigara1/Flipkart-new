package lathigara.harsh.flipkart.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lathigara.harsh.flipkart.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailsFragement extends Fragment {


    public OrderDetailsFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_details_fragement, container, false);
    }

}
