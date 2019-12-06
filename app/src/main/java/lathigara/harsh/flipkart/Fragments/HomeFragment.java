package lathigara.harsh.flipkart.Fragments;


import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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

import com.bumptech.glide.Glide;
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

import static lathigara.harsh.flipkart.DBQueries.categoryModelList;
import static lathigara.harsh.flipkart.DBQueries.firebaseFirestore;

import static lathigara.harsh.flipkart.DBQueries.list;
import static lathigara.harsh.flipkart.DBQueries.loadCategories;
import static lathigara.harsh.flipkart.DBQueries.loadFragmentData;
import static lathigara.harsh.flipkart.DBQueries.loadedCategoriesNames;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    // CATEGORY //
    private RecyclerView recyclerView;
    private CategoryaAdapter categoryaAdapter;
    public static SwipeRefreshLayout swipeRefreshLayout;
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

    private ImageView noInternet;

    // Horizontal ProductLayout//

    public HomeFragment() {
        // Required empty public constructor
    }


    // private FirebaseFirestore firebaseFirestore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        noInternet = view.findViewById(R.id.no_internetConnection);
        ConnectivityManager connectivityManager =(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            noInternet.setVisibility(View.GONE);

        }else {
            Glide.with(this).load(R.drawable.noconnectionimage).into(noInternet);
            noInternet.setVisibility(View.VISIBLE);

        }
        // CATEGORY //
        recyclerView = view.findViewById(R.id.category_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout = view.findViewById(R.id.refreshLayout);
        categoryaAdapter = new CategoryaAdapter(categoryModelList);
        recyclerView.setAdapter(categoryaAdapter);
        if (categoryModelList.size() == 0) {
            loadCategories(categoryaAdapter, getContext());

        } else {
            categoryaAdapter.notifyDataSetChanged();
        }

        // firebaseFirestore = FirebaseFirestore.getInstance();

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



        /*homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.mipmap.oppo,"#000000"));
        homePageModelList.add(new HomePageModel(2,"Deals Of The Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(3,"Deals Of The Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.mipmap.ic_launcher,"#000000"));
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.mipmap.oppo,"#000000"));
*/


        if (list.size() == 0) {
            loadedCategoriesNames.add("HOME");
            list.add(new ArrayList<HomePageModel>());
            homePageAdapter = new HomePageAdapter(list.get(0));
            loadFragmentData(homePageAdapter, getContext(), 0, "Home");
        } else {
            homePageAdapter = new HomePageAdapter(list.get(0));
            homePageAdapter.notifyDataSetChanged();
        }

        testing.setAdapter(homePageAdapter);


        // testing //
        //refresh//

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                categoryModelList.clear();
                list.clear();
                loadedCategoriesNames.clear();


                loadCategories(categoryaAdapter, getContext());
                loadedCategoriesNames.add("HOME");
                list.add(new ArrayList<HomePageModel>());
                loadFragmentData(homePageAdapter, getContext(), 0, "HOME");


            }
        });
        //refresh//

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
