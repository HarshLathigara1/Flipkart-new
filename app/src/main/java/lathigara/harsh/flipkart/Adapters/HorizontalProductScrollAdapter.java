package lathigara.harsh.flipkart.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import lathigara.harsh.flipkart.Activities.ProdutDetailsActivity;
import lathigara.harsh.flipkart.Models.HorizontalProductScrollModel;
import lathigara.harsh.flipkart.R;

public class HorizontalProductScrollAdapter extends RecyclerView.Adapter<HorizontalProductScrollAdapter.ViewHolder> {
    private List<HorizontalProductScrollModel>horizontalProductScrollModelLis;

    public HorizontalProductScrollAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModelLis) {
        this.horizontalProductScrollModelLis = horizontalProductScrollModelLis;
    }

    @NonNull
    @Override
    public HorizontalProductScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductScrollAdapter.ViewHolder holder, int position) {
        String icon  = horizontalProductScrollModelLis.get(position).getProductImage();
        String title  = horizontalProductScrollModelLis.get(position).getProductTitle();
        String description  = horizontalProductScrollModelLis.get(position).getProductDescription();
        String price  = horizontalProductScrollModelLis.get(position).getProductPrice();
        holder.setProductImage(icon);
        holder.setProductTitle(title);
        holder.setProductPrice(price);
        holder.setProductDescription(description);

    }

    @Override
    public int getItemCount() {
       if (horizontalProductScrollModelLis.size() > 8){
           return 8;

       }else{
           return horizontalProductScrollModelLis.size();
       }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView productTitle,productDescription,productPrice;
        private ImageView productImage;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.h_s_product_image);
            productTitle = itemView.findViewById(R.id.h_s_product_title);
            productDescription = itemView.findViewById(R.id.h_s_product_description);
            productPrice = itemView.findViewById(R.id.h_s_product_price);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent productDetailsIntent = new Intent(itemView.getContext(), ProdutDetailsActivity.class);
                    itemView.getContext().startActivity(productDetailsIntent);
                }
            });
        }
        private void setProductImage(String resource){
           // productImage.setImageResource(resource);
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher)).into(productImage);

        }
        private void setProductTitle(String title){
            productTitle.setText(title);
        }
        private void setProductDescription(String description){
            productDescription.setText(description);
        }
        private void setProductPrice(String price){
            productPrice.setText(price);
        }
    }
}
