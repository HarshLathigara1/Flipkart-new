package lathigara.harsh.flipkart.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lathigara.harsh.flipkart.Activities.ProdutDetailsActivity;
import lathigara.harsh.flipkart.Models.WishlistModel;
import lathigara.harsh.flipkart.R;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {
    List<WishlistModel>wishlistModelList;
    private Boolean wishlist;

    public WishlistAdapter(List<WishlistModel> wishlistModelList, Boolean wishlist) {
        this.wishlistModelList = wishlistModelList;
        this.wishlist = wishlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int resource = wishlistModelList.get(position).getProductImage();
        String title = wishlistModelList.get(position).getProductTitle();
        int freeCoupens = wishlistModelList.get(position).getFreeCoupens();
        String raiting = wishlistModelList.get(position).getRating();
        int totalRating = wishlistModelList.get(position).getTotalRatings();
        String productPrice = wishlistModelList.get(position).getProductPrice();
        String cuttedPrice = wishlistModelList.get(position).getCuttedPrice();
        String paymentMethod = wishlistModelList.get(position).getPaymentMethod();
        holder.setData(resource,title,freeCoupens,raiting,totalRating,productPrice,cuttedPrice,paymentMethod);




    }

    @Override
    public int getItemCount() {
        return wishlistModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage,coupenIcon,delete_btn;
        private TextView productTitle,freeCoupens,productPrice,cuttedPrice,paymentMethod,rating,totalRatings;
        private View priceCut;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.prodyctImage);
            productTitle = itemView.findViewById(R.id.prodcutTitle);
            freeCoupens = itemView.findViewById(R.id.freeCoupens);
            coupenIcon= itemView.findViewById(R.id.coupenIcon);
            rating = itemView.findViewById(R.id.txtProductRating);
            totalRatings = itemView.findViewById(R.id.totalRatings);
            priceCut = itemView.findViewById(R.id.price_cut);
            productPrice = itemView.findViewById(R.id.totalPrice);
            cuttedPrice = itemView.findViewById(R.id.cuttedPrice);
            paymentMethod = itemView.findViewById(R.id.paymentMethod);
            delete_btn = itemView.findViewById(R.id.delete_btn);


        }
        private void setData(int resource,String title, int freeCoupensNo,String averageRate,int totalRatingsNo,String price,String cuttedPriceValue,String payMethod ){
            productImage.setImageResource(resource);
            productTitle.setText(title);
            if (freeCoupensNo != 0){
                coupenIcon.setVisibility(View.VISIBLE);
                if (freeCoupensNo == 1) {
                    freeCoupens.setText("free " + freeCoupensNo + " coupen");
                }else{
                    freeCoupens.setText("free " + freeCoupensNo + " coupen");
                }
            }else{
                coupenIcon.setVisibility(View.INVISIBLE);
                freeCoupens.setVisibility(View.INVISIBLE);
            }
            rating.setText(averageRate);
            totalRatings.setText(totalRatingsNo+"(ratings)");
            productPrice.setText(price);
            cuttedPrice.setText(cuttedPriceValue);
            paymentMethod.setText(payMethod);
            if (wishlist){
                delete_btn.setVisibility(View.VISIBLE);
            }else{
                delete_btn.setVisibility(View.GONE);
            }
            delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(),"Delete Button",Toast.LENGTH_LONG).show();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent gotoAll= new Intent(itemView.getContext(), ProdutDetailsActivity.class);
                    itemView.getContext().startActivity(gotoAll);
                }
            });
        }
    }
}
