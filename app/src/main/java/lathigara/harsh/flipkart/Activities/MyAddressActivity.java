package lathigara.harsh.flipkart.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import lathigara.harsh.flipkart.Adapters.AddressesAdapter;
import lathigara.harsh.flipkart.Models.AddressesModel;
import lathigara.harsh.flipkart.R;

import static lathigara.harsh.flipkart.Activities.DeliveryActivity.SELECT_ADDRESS;

public class MyAddressActivity extends AppCompatActivity {
    private RecyclerView addressesRecyclrView;
    private static AddressesAdapter addressesAdapter;
    private Button deliverHerebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
            addressesRecyclrView = findViewById(R.id.addresses_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        addressesRecyclrView.setLayoutManager(layoutManager);
        deliverHerebtn = findViewById(R.id.delivery_here_btn);

        List<AddressesModel> addressesModelList = new ArrayList<>();
        addressesModelList.add(new AddressesModel("Harsh Lathigara","Gujarati Wadi Bhavik Nagar socity jetpur","360370",true));
        addressesModelList.add(new AddressesModel("Harsh Lathigara","Gujarati Wadi Bhavik Nagar socity jetpur","360370",false));
        addressesModelList.add(new AddressesModel("Harsh Lathigara","Gujarati Wadi Bhavik Nagar socity jetpur","360370",false));

        int mode = getIntent().getIntExtra("MODE",-1);
        if (mode == SELECT_ADDRESS){
            deliverHerebtn.setVisibility(View.VISIBLE);
        }else
        {
            deliverHerebtn.setVisibility(View.GONE);
        }
         addressesAdapter = new AddressesAdapter(addressesModelList,mode);
        addressesRecyclrView.setAdapter(addressesAdapter);
        ((SimpleItemAnimator)addressesRecyclrView.getItemAnimator()).setSupportsChangeAnimations(false);
        addressesAdapter.notifyDataSetChanged();
    }
    public static  void refreshItem(int deselect,int select){
            addressesAdapter.notifyItemChanged(deselect);
        addressesAdapter.notifyItemChanged(select);
    }
}
