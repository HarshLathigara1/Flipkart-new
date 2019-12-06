package lathigara.harsh.flipkart.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import lathigara.harsh.flipkart.Activities.MyAddressActivity;
import lathigara.harsh.flipkart.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment {


    public MyAccountFragment() {
        // Required empty public constructor
    }
        // logic //
    public static final int MANAGE_ADDRESS  =1;
    // logic //
    private Button viewAll;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        viewAll = view.findViewById(R.id.viewAll);
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoA = new Intent(getContext(), MyAddressActivity.class);
                gotoA.putExtra("MODE",MANAGE_ADDRESS);
                getContext().startActivity(gotoA);
            }
        });
        return view;
    }

}
