package lathigara.harsh.flipkart.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import lathigara.harsh.flipkart.Adapters.GridProductLayoutAdapter;
import lathigara.harsh.flipkart.Adapters.WishlistAdapter;
import lathigara.harsh.flipkart.Models.HorizontalProductScrollModel;
import lathigara.harsh.flipkart.Models.WishlistModel;
import lathigara.harsh.flipkart.R;

public class ViewAllActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GridView gridView;
    public static List<WishlistModel>wishlistModelsList;
    public static  List<HorizontalProductScrollModel>horizontalProductScrollModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        recyclerView = findViewById(R.id.recycler_view);
        gridView = findViewById(R.id.gridView);
        int layout_code = getIntent().getIntExtra("layout_code",-1);
        if (layout_code ==0) {
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            List<WishlistModel> list = new ArrayList<>();
                list = wishlistModelsList;
            WishlistAdapter wishlistAdapter = new WishlistAdapter(list, false);
            recyclerView.setAdapter(wishlistAdapter);
            wishlistAdapter.notifyDataSetChanged();
        }else{

            gridView.setVisibility(View.VISIBLE);
           /* List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();*/
           /* horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.oppo, "oppo f11 pro", "Good One", "Rs 20990"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.oppo, "oppo f11 pro", "Good One", "Rs 20990"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.oppo, "oppo f11 pro", "Good One", "Rs 20990"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.oppo, "oppo f11 pro", "Good One", "Rs 20990"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.oppo, "oppo f11 pro", "Good One", "Rs 20990"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.oppo, "oppo f11 pro", "Good One", "Rs 20990"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.oppo, "oppo f11 pro", "Good One", "Rs 20990"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.oppo, "oppo f11 pro", "Good One", "Rs 20990"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.oppo, "oppo f11 pro", "Good One", "Rs 20990"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.oppo, "oppo f11 pro", "Good One", "Rs 20990"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.oppo, "oppo f11 pro", "Good One", "Rs 20990"));*/
            GridProductLayoutAdapter gridProductLayoutAdapter = new GridProductLayoutAdapter(horizontalProductScrollModelList);
            gridView.setAdapter(gridProductLayoutAdapter);
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
