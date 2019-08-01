package lathigara.harsh.flipkart.Fragments;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lathigara.harsh.flipkart.Adapters.CategoryaAdapter;
import lathigara.harsh.flipkart.Adapters.GridProductLayoutAdapter;
import lathigara.harsh.flipkart.Adapters.HomePageAdapter;
import lathigara.harsh.flipkart.Adapters.HorizontalProductScrollAdapter;
import lathigara.harsh.flipkart.Adapters.SliderAdapter;
import lathigara.harsh.flipkart.Models.CategoryModel;
import lathigara.harsh.flipkart.Models.HomePageModel;
import lathigara.harsh.flipkart.Models.HorizontalProductScrollModel;
import lathigara.harsh.flipkart.Models.SliderModel;
import lathigara.harsh.flipkart.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    // CATEGORY //
    private RecyclerView recyclerView;
    private CategoryaAdapter categoryaAdapter;
    // CATEGORY //

    // BANNER SLIDER //
    private ViewPager bannerSliderViewPager;
    private List<SliderModel> sliderModelList;
    private int currentPage = 2;
    private Timer timer;
    final private long DELAY_TIME = 3000;
    final private long PERIOD_TIME = 3000;
    private HomePageAdapter homePageAdapter;
    // BANNER SLIDER //

    // Strip Ad Image Banner //
    private ImageView stripAdImage;
    private ConstraintLayout stripadContainer;

    // Strip Ad Image Banner //

    // Horizontal ProductLayout//
    private TextView horizontalLayoutTitle;
    private Button horizontalViewAllBtn;
    private RecyclerView horizontalRecyclerView;

    // Horizontal ProductLayout//

    public HomeFragment() {
        // Required empty public constructor
    }

    private List<CategoryModel> categoryModelList;
    private FirebaseFirestore firebaseFirestore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // CATEGORY //
        recyclerView = view.findViewById(R.id.category_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        categoryModelList = new ArrayList<CategoryModel>();
        categoryaAdapter = new CategoryaAdapter(categoryModelList);
        recyclerView.setAdapter(categoryaAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(), documentSnapshot.get("categoryName").toString()));
                            }
                            categoryaAdapter.notifyDataSetChanged();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                        }
                    }
                });
        /*categoryModelList.add(new CategoryModel("link", "Home"));
        categoryModelList.add(new CategoryModel("link", "Home"));
        categoryModelList.add(new CategoryModel("link", "Home"));
        categoryModelList.add(new CategoryModel("link", "Home"));
        categoryModelList.add(new CategoryModel("link", "Home"));
        categoryModelList.add(new CategoryModel("link", "Home"));
        categoryModelList.add(new CategoryModel("link", "Home"));
        categoryModelList.add(new CategoryModel("link", "Home"));
        categoryModelList.add(new CategoryModel("link", "Home"));
        categoryModelList.add(new CategoryModel("link", "Home"));*/

        // CATEGORY //
        //BANNER//
        bannerSliderViewPager = view.findViewById(R.id.banner_slider_view_pager);
        sliderModelList = new ArrayList<SliderModel>();
        /*sliderModelList.add(new SliderModel(R.mipmap.image4,"#000000"));
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


        SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
        bannerSliderViewPager.setAdapter(sliderAdapter);
        //LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        bannerSliderViewPager.setClipToPadding(false);
        bannerSliderViewPager.setPageMargin(20);
        bannerSliderViewPager.setCurrentItem(currentPage);
        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    pageLooper();
                }

            }
        };
        bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);
        startBannerAnimaiton();
        bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                pageLooper();
                stopBannerSlideShow();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    startBannerAnimaiton();

                }
                return false;
            }
        });

        //BANNER//
        // Strip Ad Image Banner //
        stripAdImage = view.findViewById(R.id.strip_ad_image);
        stripadContainer = view.findViewById(R.id.stripAdContainer);
        stripAdImage.setImageResource(R.mipmap.image4);
        stripadContainer.setBackgroundColor(Color.parseColor("#000000"));
        // Strip Ad Image Banner //

        //horizontal layout//
        horizontalLayoutTitle = view.findViewById(R.id.horizontal_scroll_layout_title);
        horizontalViewAllBtn = view.findViewById(R.id.horizontal_scroll_view_all_button);
        horizontalRecyclerView = view.findViewById(R.id.horizontal_scroll_layout_recyclerView);
        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
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
        HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
        horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        horizontalRecyclerView.setLayoutManager(linearLayoutManager1);
        horizontalProductScrollAdapter.notifyDataSetChanged();
        //horizontal layout//

        //Grid Layout //
        TextView gridLyoutTitle = view.findViewById(R.id.grid_product_layout_title);
        Button gridLayoutButton = view.findViewById(R.id.grid_product_layout_btn);
        // GridView gridView = view.findViewById(R.id.grid_product_layout_gridView);
        //  gridView.setAdapter(new GridProductLayoutAdapter(horizontalProductScrollModelList));
        //Grid Layout //

        // testing //

        RecyclerView testing = view.findViewById(R.id.testing);
        LinearLayoutManager testinglayout = new LinearLayoutManager(getContext());
        testinglayout.setOrientation(RecyclerView.VERTICAL);
        testing.setLayoutManager(testinglayout);

        final List<HomePageModel> homePageModelList = new ArrayList<>();

        /*homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.mipmap.oppo,"#000000"));
        homePageModelList.add(new HomePageModel(2,"Deals Of The Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(3,"Deals Of The Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.mipmap.ic_launcher,"#000000"));
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.mipmap.oppo,"#000000"));
*/

        homePageAdapter = new HomePageAdapter(homePageModelList);
        testing.setAdapter(homePageAdapter);

        firebaseFirestore.collection("CATEGORIES").document("HOME").collection("TOP_DEALS").orderBy("index").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        if ((long) documentSnapshot.get("view_type") == 0) {
                            List<SliderModel> sliderModelList = new ArrayList<>();
                            long no_of_banners = (long) documentSnapshot.get("no_of_banners");
                            for (long x = 1; x < no_of_banners + 1; x++) {
                                sliderModelList.add(new SliderModel(documentSnapshot.get("banner_" + x).toString(), documentSnapshot.get("banner_" + x + "_background").toString()));
                            }
                            homePageModelList.add(new HomePageModel(0, sliderModelList));

                        } else if ((long) documentSnapshot.get("view_type") == 1) {
                            homePageModelList.add(new HomePageModel(1, documentSnapshot.get("strip_ad_banner").toString(), documentSnapshot.get("background").toString()));

                        } else if ((long) documentSnapshot.get("view_type") == 2) {
                            List<HorizontalProductScrollModel>horizontalProductScrollModels = new ArrayList<>();
                            long no_of_products = (long) documentSnapshot.get("no_of_products");
                            for (long x = 1; x < no_of_products + 1; x++) {
                               // horizontalProductScrollModels.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID"+x).toString(),(documentSnapshot.get("product_image"+x).toString(),(documentSnapshot.get("product_title"+x).toString(),(documentSnapshot.get("product_subtitle"+x).toString(),(documentSnapshot.get("product_price"+x).toString()));
                                horizontalProductScrollModels.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_"+x).toString(),
                                        documentSnapshot.get("product_image_"+x).toString(),documentSnapshot.get("product_title_"+x).toString(),documentSnapshot.get("product_subtitle_"+x).toString(),documentSnapshot.get("product_price_"+x).toString()));

                            }
                            homePageModelList.add(new HomePageModel(2,documentSnapshot.get("layout_title").toString(),documentSnapshot.get("layout_background").toString(), horizontalProductScrollModels));

                        } else if ((long) documentSnapshot.get("view_type") == 3) {

                        }

                    }
                    homePageAdapter.notifyDataSetChanged();
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                }
            }
        });
        // testing //

        return view;
    }

    private void pageLooper() {
        if (currentPage == sliderModelList.size() - 2) {
            currentPage = 2;
            bannerSliderViewPager.setCurrentItem(currentPage, false);

        }
        if (currentPage == 1) {
            currentPage = sliderModelList.size() - 3;
            bannerSliderViewPager.setCurrentItem(currentPage, false);

        }

    }

    private void startBannerAnimaiton() {
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPage >= sliderModelList.size()) {
                    currentPage = 1;
                }
                bannerSliderViewPager.setCurrentItem(currentPage++, true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_TIME, PERIOD_TIME);
    }

    private void stopBannerSlideShow() {
        timer.cancel();

    }

}
