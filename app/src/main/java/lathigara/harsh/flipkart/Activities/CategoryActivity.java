package lathigara.harsh.flipkart.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import lathigara.harsh.flipkart.Adapters.CategoryaAdapter;
import lathigara.harsh.flipkart.Adapters.HomePageAdapter;
import lathigara.harsh.flipkart.Models.CategoryModel;
import lathigara.harsh.flipkart.Models.HomePageModel;
import lathigara.harsh.flipkart.Models.HorizontalProductScrollModel;
import lathigara.harsh.flipkart.Models.SliderModel;
import lathigara.harsh.flipkart.R;

import static lathigara.harsh.flipkart.DBQueries.list;
import static lathigara.harsh.flipkart.DBQueries.loadFragmentData;
import static lathigara.harsh.flipkart.DBQueries.loadedCategoriesNames;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView catRecyclerView;
    private HomePageAdapter adapter;
    // test //

    CategoryaAdapter categoryaAdapter;
    // test //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        String title = getIntent().getStringExtra("categoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        catRecyclerView = findViewById(R.id.cat_recyclerView);

        // test //

       // List<SliderModel> sliderModelList = new ArrayList<>();
       /* sliderModelList.add(new SliderModel(R.mipmap.image4,"#000000"));
        sliderModelList.add(new SliderModel(R.mipmap.flipkart,"#000000"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#000000"));
        sliderModelList.add(new SliderModel(R.mipmap.logo,"#000000"));
        sliderModelList.add(new SliderModel(R.mipmap.image4,"#000000"));
        sliderModelList.add(new SliderModel(R.mipmap.image4,"#000000"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#000000"));
        sliderModelList.add(new SliderModel(R.mipmap.logo,"#000000"));
        sliderModelList.add(new SliderModel(R.mipmap.image4,"#000000"));
        sliderModelList.add(new SliderModel(R.mipmap.logo,"#000000"));
        sliderModelList.add(new SliderModel(R.mipmap.image4,"#000000"));
        sliderModelList.add(new SliderModel(R.mipmap.flipkart,"#000000"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#000000"));
        sliderModelList.add(new SliderModel(R.mipmap.logo,"#000000"));
        sliderModelList.add(new SliderModel(R.mipmap.image4,"#000000"));
        sliderModelList.add(new SliderModel(R.mipmap.image4,"#000000"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#000000"));
        sliderModelList.add(new SliderModel(R.mipmap.image4,"#000000"));
        sliderModelList.add(new SliderModel(R.mipmap.flipkart,"#000000"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#000000"));
        sliderModelList.add(new SliderModel(R.mipmap.logo,"#000000"));
        sliderModelList.add(new SliderModel(R.mipmap.image4,"#000000"));
        sliderModelList.add(new SliderModel(R.mipmap.image4,"#000000"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#000000"));*/

        //List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
        /*horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.oppo,"oppo f11 pro","Good One","Rs 20990"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.oppo,"oppo f11 pro","Good One","Rs 20990"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.oppo,"oppo f11 pro","Good One","Rs 20990"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.oppo,"oppo f11 pro","Good One","Rs 20990"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.oppo,"oppo f11 pro","Good One","Rs 20990"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.oppo,"oppo f11 pro","Good One","Rs 20990"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.oppo,"oppo f11 pro","Good One","Rs 20990"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.oppo,"oppo f11 pro","Good One","Rs 20990"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.oppo,"oppo f11 pro","Good One","Rs 20990"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.oppo,"oppo f11 pro","Good One","Rs 20990"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.oppo,"oppo f11 pro","Good One","Rs 20990"));*/


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        catRecyclerView.setLayoutManager(linearLayoutManager);

        int listPosition = 0;

       for(int x =0; x< loadedCategoriesNames.size(); x++){
            if (loadedCategoriesNames.get(x).equals(title.toUpperCase())){
                listPosition = x;
            }
       }

       if (listPosition == 0){
           loadedCategoriesNames.add(title.toUpperCase());
           list.add(new ArrayList<HomePageModel>());
           adapter = new HomePageAdapter(list.get(loadedCategoriesNames.size() -1));
           loadFragmentData(adapter,this,loadedCategoriesNames.size() -1,title);


       }else {
           adapter = new HomePageAdapter(list.get(listPosition));

       }


        catRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
            return true;
        }

        if (id == android.R.id.home ){
            finish();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}
