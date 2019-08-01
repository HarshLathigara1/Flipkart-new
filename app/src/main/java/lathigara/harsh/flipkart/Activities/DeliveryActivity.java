package lathigara.harsh.flipkart.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import lathigara.harsh.flipkart.Adapters.CartAdapter;
import lathigara.harsh.flipkart.Models.CartItemModel;
import lathigara.harsh.flipkart.R;

public class DeliveryActivity extends AppCompatActivity {
    private RecyclerView myDelveryRecyclerView;
    private Button changeAdd;
    public static final int SELECT_ADDRESS = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Delivery Details");
        myDelveryRecyclerView = findViewById(R.id.delivery_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        myDelveryRecyclerView.setLayoutManager(layoutManager);
        List<CartItemModel>cartItemModelList = new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0,R.mipmap.oppo,"oppo F11 Pro",2,"Rs 49999","Rs 59999",1,0,0));
        cartItemModelList.add(new CartItemModel(0,R.mipmap.oppo,"oppo F11 Pro",0,"Rs 49999","Rs 59999",1,1,0));
        cartItemModelList.add(new CartItemModel(1,"Price (3 items)","Rs.160000","Free","Rs 100000","Rs500000"));
        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        myDelveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        changeAdd = findViewById(R.id.btnVhangeAddress);
        changeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoAdd = new Intent(DeliveryActivity.this,MyAddressActivity.class);
                gotoAdd.putExtra("MODE",SELECT_ADDRESS);
                startActivity(gotoAdd);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
