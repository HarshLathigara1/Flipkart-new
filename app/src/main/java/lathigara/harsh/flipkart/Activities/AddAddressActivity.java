package lathigara.harsh.flipkart.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import lathigara.harsh.flipkart.DBQueries;
import lathigara.harsh.flipkart.Models.AddressesModel;
import lathigara.harsh.flipkart.R;

public class AddAddressActivity extends AppCompatActivity {
    private Button saveBtn;
    private EditText city, locality, flatNo, pinCode, landMark, name, mobileNumber, alternateNumber;
    private Spinner stateSpinner;
    private String selectedSate;
    private String[] stateList;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        getSupportActionBar().setTitle("Add New Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // loadingDialog.show();
        city = findViewById(R.id.cirty);
        locality = findViewById(R.id.edtLocality);
        flatNo = findViewById(R.id.edtFlatno);
        pinCode = findViewById(R.id.pinCode);
        landMark = findViewById(R.id.LandMark);
        name = findViewById(R.id.Namee);
        mobileNumber = findViewById(R.id.Numberr);
        alternateNumber = findViewById(R.id.altNumber);
        stateSpinner = findViewById(R.id.stateSpinner);
        stateList = getResources().getStringArray(R.array.india_states);

        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, stateList);
        ;
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(spinnerAdapter);
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSate = stateList[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        saveBtn = findViewById(R.id.btnSave);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(city.getText())) {
                    if (!TextUtils.isEmpty(locality.getText())) {
                        if (!TextUtils.isEmpty(flatNo.getText())) {
                            if (!TextUtils.isEmpty(pinCode.getText()) && pinCode.getText().length()== 6 ) {
                                if (!TextUtils.isEmpty(name.getText())) {
                                    if (!TextUtils.isEmpty(mobileNumber.getText()) && mobileNumber.getText().length() == 10) {
                                        loadingDialog.show();
                                        final String fullAdd = flatNo.getText().toString() + "" + locality.getText().toString() + "" + landMark.getText().toString() + "" + city.getText().toString() + "" + selectedSate;

                                        Map<String, Object> addAddress = new HashMap<>();
                                        addAddress.put("list_size", (long) DBQueries.addressesModelList.size() + 1);
                                        if (TextUtils.isEmpty(alternateNumber.getText())) {
                                            addAddress.put("fullname_" + String.valueOf((long) DBQueries.addressesModelList.size() + 1), name.getText().toString() + "-" + mobileNumber.getText().toString());
                                        }else {
                                            addAddress.put("fullname_" + String.valueOf((long) DBQueries.addressesModelList.size() + 1), name.getText().toString() + "-" + mobileNumber.getText().toString()+ "or" + alternateNumber.getText().toString());

                                        }
                                        addAddress.put("address_" +String.valueOf((long) DBQueries.addressesModelList.size() + 1), fullAdd);
                                        addAddress.put("pincode_" +String.valueOf((long) DBQueries.addressesModelList.size() + 1), pinCode.getText().toString());
                                        addAddress.put("selected_" + String.valueOf((long) DBQueries.addressesModelList.size() + 1), true);
                                        if (DBQueries.addressesModelList.size() > 0) {
                                            addAddress.put("selected_" + (DBQueries.selecteAdress + 1), false);
                                        }


                                       // if (!TextUtils.isGraphic(mobileNumber.getText())) {
                                            FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_ADDRESSES")
                                                    .update(addAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        if (DBQueries.addressesModelList.size() > 0) {
                                                            DBQueries.addressesModelList.get(DBQueries.selecteAdress).setSelected(false);

                                                        }

                                                        if (TextUtils.isEmpty(alternateNumber.getText())) {
                                                            DBQueries.addressesModelList.add(new AddressesModel(name.getText().toString() + "-" + mobileNumber.getText().toString(), fullAdd, pinCode.getText().toString(), true));
                                                        }else {
                                                            DBQueries.addressesModelList.add(new AddressesModel(name.getText().toString() + "-" + mobileNumber.getText().toString() + "or " + alternateNumber.getText().toString(), fullAdd, pinCode.getText().toString(), true));

                                                        }

                                                        if (getIntent().getStringExtra("INTENT").equals("deliveryIntent")) {

                                                            Intent deliveryActivity = new Intent(AddAddressActivity.this, DeliveryActivity.class);
                                                            startActivity(deliveryActivity);
                                                        }else {
                                                           MyAddressActivity.refreshItem(DBQueries.selecteAdress,DBQueries.addressesModelList.size() -1);
                                                        }
                                                        DBQueries.selecteAdress = DBQueries.addressesModelList.size() - 1;

                                                        finish();


                                                    } else {
                                                        String error = task.getException().getMessage();
                                                    }
                                                    loadingDialog.dismiss();
                                                }
                                            });


                                       // }
                                    }else {
                                        mobileNumber.requestFocus();

                                    }

                                }else {
                                    name.requestFocus();

                                }


                            }else {
                                pinCode.requestFocus();

                            }

                        }else {
                            flatNo.requestFocus();

                        }

                    }else{
                        locality.requestFocus();
                    }

                }else {
                    city.requestFocus();
                }
                //Intent saveIntent = new Intent(getApplicationContext(), DeliveryActivity.class);
                //startActivity(saveIntent);
                //finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

