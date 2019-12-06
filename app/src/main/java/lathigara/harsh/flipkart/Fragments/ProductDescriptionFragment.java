package lathigara.harsh.flipkart.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lathigara.harsh.flipkart.R;



/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDescriptionFragment extends Fragment {


    public ProductDescriptionFragment() {
        // Required empty public constructor
    }

        private TextView descriptionBody;

    public String body;
     // under test //

        // under test //
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_description, container, false);
        descriptionBody = view.findViewById(R.id.tvHello);
        descriptionBody.setText(body);
       /* if (tabPosition == 0){
            descriptionBody.setText(productDescription);

        }else {

            descriptionBody.setText(productOtherDetails);
        }*/

       // descText.setText(descriptionData);

        return view;
    }

}
