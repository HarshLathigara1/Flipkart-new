package lathigara.harsh.flipkart.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lathigara.harsh.flipkart.Adapters.AddressesAdapter;
import lathigara.harsh.flipkart.DBQueries;
import lathigara.harsh.flipkart.Models.AddressesModel;
import lathigara.harsh.flipkart.R;

import static lathigara.harsh.flipkart.Activities.DeliveryActivity.SELECT_ADDRESS;

public class MyAddressActivity extends AppCompatActivity {
    private int previouAddress;
    private RecyclerView addressesRecyclrView;
    private static AddressesAdapter addressesAdapter;
    private Button deliverHerebtn;
    private LinearLayout addNewBtn;
    private TextView addres_saved;
    private Dialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_address);
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(this.getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       // loadingDialog.show();
        addressesRecyclrView = findViewById(R.id.addresses_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        addressesRecyclrView.setLayoutManager(layoutManager);
        deliverHerebtn = findViewById(R.id.delivery_here_btn);
        addNewBtn = findViewById(R.id.addnewaddressBtn);
        addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendToAdd = new Intent(MyAddressActivity.this, AddAddressActivity.class);
                sendToAdd.putExtra("INTENT", "null");
                startActivity(sendToAdd);

            }
        });
        addres_saved = findViewById(R.id.addres_saved);
        previouAddress = DBQueries.selecteAdress;
        deliverHerebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DBQueries.selecteAdress != previouAddress) {
                    final int previousAddresInex = previouAddress;
                    loadingDialog.show();
                    Map<String, Object> updateSelection = new HashMap<>();
                    updateSelection.put("selected" + String.valueOf(previouAddress + 1), false);
                    updateSelection.put("selected" + String.valueOf(DBQueries.selecteAdress + 1), true);
                    previouAddress = DBQueries.selecteAdress;

                    FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                            .document("MY_ADDRESSES").update(updateSelection).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                finish();

                            } else {
                                previouAddress = previousAddresInex;

                                String erroe = task.getException().getMessage();

                            }
                            loadingDialog.dismiss();
                        }
                    });

                }else {
                    finish();
                }

            }
        });

        // List<AddressesModel> addressesModelList = new ArrayList<>();
        // addressesModelList.add(new AddressesModel("Harsh Lathigara","Gujarati Wadi Bhavik Nagar socity jetpur","360370",true));
        // addressesModelList.add(new AddressesModel("Harsh Lathigara","Gujarati Wadi Bhavik Nagar socity jetpur","360370",false));
        //  addressesModelList.add(new AddressesModel("Harsh Lathigara","Gujarati Wadi Bhavik Nagar socity jetpur","360370",false));

        int mode = getIntent().getIntExtra("MODE", -1);
        if (mode == SELECT_ADDRESS) {
            deliverHerebtn.setVisibility(View.VISIBLE);
        } else {
            deliverHerebtn.setVisibility(View.GONE);
        }
        addressesAdapter = new AddressesAdapter(DBQueries.addressesModelList, mode);
        addressesRecyclrView.setAdapter(addressesAdapter);
        ((SimpleItemAnimator) addressesRecyclrView.getItemAnimator()).setSupportsChangeAnimations(false);
        addressesAdapter.notifyDataSetChanged();
    }

    public static void refreshItem(int deselect, int select) {
        addressesAdapter.notifyItemChanged(deselect);
        addressesAdapter.notifyItemChanged(select);
    }

    @Override
    protected void onStart() {

        super.onStart();
        addres_saved.setText(String.valueOf(DBQueries.addressesModelList.size()));

    }

    @Override
    public void onBackPressed() {
        if (DBQueries.selecteAdress != previouAddress) {
            DBQueries.addressesModelList.get(DBQueries.selecteAdress).setSelected(false);
            DBQueries.addressesModelList.get(previouAddress).setSelected(true);
            DBQueries.selecteAdress = previouAddress;

        }
        super.onBackPressed();
    }
}
