package lathigara.harsh.flipkart.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import lathigara.harsh.flipkart.Adapters.ProductDetailsAdapter;
import lathigara.harsh.flipkart.Adapters.ProductImagesAdapter;
import lathigara.harsh.flipkart.Adapters.RewardAdapter;
import lathigara.harsh.flipkart.Models.RewardModel;
import lathigara.harsh.flipkart.R;

public class ProdutDetailsActivity extends AppCompatActivity {
    private ViewPager productImagesViewPager;
    private TabLayout viewPagerIndicator;
    private Button coupenRedeemBtn;

    /// coupenDialog //

    public static TextView coupenTitle;
    public static TextView coupenExpiryDate;
    public static TextView coupenBody;
    private static RecyclerView coupenRecycleView;
    private static LinearLayout selectedCoupen;


    //    /// coupenDialog //

    private  ViewPager productDetailsViewPager;
    private  TabLayout productDetailsTablayout;
  //  private FloatingActionButton addToWishlisBtn;
    // rating layout //

    private LinearLayout rateNowLayout;



  // rating layout //
    private static Boolean ADDED_TO_WISHLIST = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produt_details);
        productImagesViewPager = findViewById(R.id.products_images_view_pager);
        viewPagerIndicator = findViewById(R.id.viewPagerIndicatore);
        List<Integer>productImages = new ArrayList<>();
        productImages.add(R.mipmap.oppo);
        productImages.add(R.mipmap.oppo);
        productImages.add(R.mipmap.oppo);
        productImages.add(R.mipmap.oppo);
        ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
        productImagesViewPager.setAdapter(productImagesAdapter);
        viewPagerIndicator.setupWithViewPager(productImagesViewPager);
       /* addToWishlisBtn = findViewById(R.id.floatWhishList);
        addToWishlisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ADDED_TO_WISHLIST){
                    ADDED_TO_WISHLIST = false;
                    addToWishlisBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#2874f0")));

                }else{
                    ADDED_TO_WISHLIST = true;
                    addToWishlisBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));

                }
            }
        });*/
       // viewPAger //
       productDetailsViewPager = findViewById(R.id.productDetailsViewPager);
       productDetailsTablayout = findViewById(R.id.productDetailsTab);

       productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(),productDetailsTablayout.getTabCount()));
       productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTablayout));
       productDetailsTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               productDetailsViewPager.setCurrentItem(tab.getPosition());
           }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {

           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {

           }
       });

        // viewPAger //
        // rating //

        rateNowLayout = findViewById(R.id.rate_now_container);
        for (int x =0; x< rateNowLayout.getChildCount();x++){
            final  int startPosition = x;
            rateNowLayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setRating(startPosition);
                }
            });
        }
        // rating //
            coupenRedeemBtn = findViewById(R.id.coupenRedemption);
            coupenRedeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog coupen = new Dialog(ProdutDetailsActivity.this);
                coupen.setContentView(R.layout.coupen_reddem_dialog);
                coupen.setCancelable(true);
                coupen.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                ImageView openCoupenRecyclerView = coupen.findViewById(R.id.toggerl_recycler);
                 coupenRecycleView = coupen.findViewById(R.id.coupensRecyclerView);
                selectedCoupen = coupen.findViewById(R.id.coupenContainer);
                coupenTitle  =coupen.findViewById(R.id.coupen_title);
                coupenBody  =coupen.findViewById(R.id.coupen_body);
                coupenExpiryDate = coupen.findViewById(R.id.coupen_validity);


                TextView originalPrice = coupen.findViewById(R.id.original_price);

                TextView discountedPrice = coupen.findViewById(R.id.discount_Price);

                LinearLayoutManager layoutManager = new LinearLayoutManager(ProdutDetailsActivity.this);
                layoutManager.setOrientation(RecyclerView.VERTICAL);
                coupenRecycleView.setLayoutManager(layoutManager);
                List<RewardModel>list = new ArrayList<>();
                list.add(new RewardModel("Cash Back","5/2/2019","til 2 june 2019"));
                list.add(new RewardModel("Cash Back","5/2/2019","til 2 june 2019"));
                list.add(new RewardModel("Cash Back","5/2/2019","til 2 june 2019"));
                list.add(new RewardModel("Cash Back","5/2/2019","til 2 june 2019"));
                RewardAdapter rewardAdapter = new RewardAdapter(list,true);
                coupenRecycleView.setAdapter(rewardAdapter);
                rewardAdapter.notifyDataSetChanged();
                openCoupenRecyclerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            showDialogRecyclerView();
                    }
                });
                coupen.show();

            }
        });
    }

        public static void showDialogRecyclerView(){
            if (coupenRecycleView.getVisibility() == View.GONE){
                coupenRecycleView.setVisibility(View.VISIBLE);
                selectedCoupen.setVisibility(View.GONE);
            }else{
                coupenRecycleView.setVisibility(View.GONE);
                selectedCoupen.setVisibility(View.VISIBLE);
            }

        }
    private void setRating(int startPosition) {
        for (int x =0; x < rateNowLayout.getChildCount();x++){
            ImageView starBtn = (ImageView)rateNowLayout.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if (x <= startPosition){
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));

            }
        }
    }
}
