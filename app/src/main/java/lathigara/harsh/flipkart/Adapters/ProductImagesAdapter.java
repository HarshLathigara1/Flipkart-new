package lathigara.harsh.flipkart.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class ProductImagesAdapter extends PagerAdapter {
    private List<Integer>productsImages;

    public ProductImagesAdapter(List<Integer> productsImages) {
        this.productsImages = productsImages;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView productImage = new ImageView(container.getContext());
       productImage.setImageResource(productsImages.get(position));
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
