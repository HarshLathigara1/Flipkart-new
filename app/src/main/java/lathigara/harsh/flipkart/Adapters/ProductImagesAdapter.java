package lathigara.harsh.flipkart.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import lathigara.harsh.flipkart.R;

public class ProductImagesAdapter extends PagerAdapter {
    private List<String>productsImages;


    public ProductImagesAdapter(List<String> productsImages) {
        this.productsImages = productsImages;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView productImage = new ImageView(container.getContext());
      // productImage.setImageResource(productsImages.get(position));
        //1//
        Glide.with(container.getContext()).load(productsImages.get(position)).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round)).into(productImage);
       container.addView(productImage,0);
       return productImage;

    }

    @Override
    public int getCount() {
        return productsImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((ImageView)object);
    }
}
