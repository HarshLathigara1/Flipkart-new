package lathigara.harsh.flipkart.Adapters;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lathigara.harsh.flipkart.Activities.ViewAllActivity;
import lathigara.harsh.flipkart.Models.HomePageModel;
import lathigara.harsh.flipkart.Models.HorizontalProductScrollModel;
import lathigara.harsh.flipkart.Models.SliderModel;
import lathigara.harsh.flipkart.R;

public class HomePageAdapter extends RecyclerView.Adapter {
    private List<HomePageModel> homePageModelList;
    private RecyclerView.RecycledViewPool recycledViewPool;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageModelList.get(position).getType()) {
            case 0:
                return HomePageModel.BANNER_SLIDER;
            case 1:
                return HomePageModel.STRIP_AD_BANNER;
            case 2:
                return HomePageModel.HORINZONTAL_PRODUCT_VIEW;
            case 3:
                return HomePageModel.GRID_PRODUCT_VIEW;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case HomePageModel.BANNER_SLIDER:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sliding_ad_layout, parent, false);
                return new BannerSlideViewHolder(view);
            case HomePageModel.STRIP_AD_BANNER:
                View strip = LayoutInflater.from(parent.getContext()).inflate(R.layout.srtip_ad_layout, parent, false);
                return new StripAdBannerViewHolder(strip);
            case HomePageModel.HORINZONTAL_PRODUCT_VIEW:
                View horizontal = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout, parent, false);
                return new horizontalProductViewHolder(horizontal);
            case HomePageModel.GRID_PRODUCT_VIEW:
                View grid = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_layout, parent, false);
                return new gridProductViewHolder(grid);

            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (homePageModelList.get(position).getType()) {
            case HomePageModel.BANNER_SLIDER:
                List<SliderModel> sliderModelList = homePageModelList.get(position).getSliderModelList();
                ((BannerSlideViewHolder) holder).setBannerSliderViewPager(sliderModelList);
                break;
            case HomePageModel.STRIP_AD_BANNER:
                String resource = homePageModelList.get(position).getResource();
                String color = homePageModelList.get(position).getBackgroundColor();
                ((StripAdBannerViewHolder) holder).setStripAd(resource, color);
                break;
            case HomePageModel.HORINZONTAL_PRODUCT_VIEW:
                String hcolor = homePageModelList.get(position).getBackgroundColor();
                String htitle = homePageModelList.get(position).getTitle();
                List<HorizontalProductScrollModel> horizontalProductScrollModelList = homePageModelList.get(position).getHorizontalProductScrollModelList();
                ((horizontalProductViewHolder) holder).setHorizontalProductLayout(horizontalProductScrollModelList, htitle,hcolor);
                break;
            case HomePageModel.GRID_PRODUCT_VIEW:
                String title = homePageModelList.get(position).getTitle();
                List<HorizontalProductScrollModel> gridProductScrollModelList = homePageModelList.get(position).getHorizontalProductScrollModelList();
                ((gridProductViewHolder) holder).setGridLyoutProductLayout(gridProductScrollModelList, title);
                break;

            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }

    public class BannerSlideViewHolder extends RecyclerView.ViewHolder {
        private ViewPager bannerSliderViewPager;
        private List<SliderModel> sliderModelList;
        private int currentPage =2;
        private Timer timer;
        final private long DELAY_TIME = 3000;
        final private long PERIOD_TIME = 3000;
      //  private List<SliderModel>arrangList;

        public BannerSlideViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPager = itemView.findViewById(R.id.banner_slider_view_pager);


        }

        private void pageLooper(List<SliderModel> sliderModelList) {
            if (currentPage == sliderModelList.size() - 2) {
                currentPage = 2;
                bannerSliderViewPager.setCurrentItem(currentPage, false);

            }
            if (currentPage == 1) {
                currentPage = sliderModelList.size() - 3;
                bannerSliderViewPager.setCurrentItem(currentPage, false);

            }

        }

        private void startBannerAnimaiton(final List<SliderModel> sliderModelList) {
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

        private void stopBannerSlideShow(List<SliderModel> sliderModelList) {
            timer.cancel();

        }

        private void setBannerSliderViewPager(final List<SliderModel> sliderModelList) {
           // currentPage =2;
            if (timer != null){
                timer.cancel();
            }
           /* arrangList = new ArrayList<>();
            for (int x =0;x< sliderModelList.size();x++){
                arrangList.set(x,sliderModelList.get(x));
            }*/
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
                        pageLooper(sliderModelList);
                    }

                }
            };
            bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);
            startBannerAnimaiton(sliderModelList);
            bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    pageLooper(sliderModelList);
                    stopBannerSlideShow(sliderModelList);
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        startBannerAnimaiton(sliderModelList);

                    }
                    return false;
                }
            });

        }

    }

    public class StripAdBannerViewHolder extends RecyclerView.ViewHolder {
        private ImageView stripAdImage;
        private ConstraintLayout stripadContainer;

        public StripAdBannerViewHolder(@NonNull View itemView) {
            super(itemView);
            stripAdImage = itemView.findViewById(R.id.strip_ad_image);
            stripadContainer = itemView.findViewById(R.id.stripAdContainer);
        }

        private void setStripAd(String resource, String color) {
           // stripAdImage.setImageResource(resource);
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher)).into(stripAdImage);
            stripadContainer.setBackgroundColor(Color.parseColor(color));
        }
    }

    public class horizontalProductViewHolder extends RecyclerView.ViewHolder {
        private  ConstraintLayout container;
        private TextView horizontalLayoutTitle;
        private Button horizontalViewAllBtn;
        private RecyclerView horizontalRecyclerView;

        public horizontalProductViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.Container);
            horizontalLayoutTitle = itemView.findViewById(R.id.horizontal_scroll_layout_title);
            horizontalViewAllBtn = itemView.findViewById(R.id.horizontal_scroll_view_all_button);
            horizontalRecyclerView = itemView.findViewById(R.id.horizontal_scroll_layout_recyclerView);
            horizontalRecyclerView.setRecycledViewPool(recycledViewPool);
        }

        private void setHorizontalProductLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelList, String title,String color) {
            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            horizontalLayoutTitle.setText(title);
            if (horizontalProductScrollModelList.size() > 8) {
                horizontalViewAllBtn.setVisibility(View.VISIBLE);
               horizontalViewAllBtn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent gotoView = new Intent(itemView.getContext(), ViewAllActivity.class);
                       gotoView.putExtra("layout_code",0);
                       itemView.getContext().startActivity(gotoView);
                   }
               });


            } else {
                horizontalViewAllBtn.setVisibility(View.INVISIBLE);

            }

            HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
            horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager1.setOrientation(RecyclerView.HORIZONTAL);
            horizontalRecyclerView.setLayoutManager(linearLayoutManager1);
            horizontalProductScrollAdapter.notifyDataSetChanged();

        }
    }

    public class gridProductViewHolder extends RecyclerView.ViewHolder {
        TextView gridLyoutTitle;
        Button gridLayoutButton;
        GridView gridView;


        public gridProductViewHolder(@NonNull View itemView) {
            super(itemView);
            gridLyoutTitle = itemView.findViewById(R.id.grid_product_layout_title);
            gridLayoutButton = itemView.findViewById(R.id.grid_product_layout_btn);
            gridView = itemView.findViewById(R.id.grid_product_layout_gridView);
        }

        private void setGridLyoutProductLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelList, String title) {
           // Glide.with(itemView.getContext()).load(horizontalProductScrollModelList.get(x))
            gridLyoutTitle.setText(title);
            gridView.setAdapter(new GridProductLayoutAdapter(horizontalProductScrollModelList));

            gridLayoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent gotoView = new Intent(itemView.getContext(), ViewAllActivity.class);
                    gotoView.putExtra("layout_code",1);
                    itemView.getContext().startActivity(gotoView);

                }
            });
        }
    }


}
