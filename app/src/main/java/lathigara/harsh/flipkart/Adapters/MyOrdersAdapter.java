package lathigara.harsh.flipkart.Adapters;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;


import java.util.List;

import lathigara.harsh.flipkart.Activities.OrderDetailsActivity;
import lathigara.harsh.flipkart.Models.MyOrderItemModel;
import lathigara.harsh.flipkart.R;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.ViewHolder> {
    private List<MyOrderItemModel>myOrdersItemModelList;

    public MyOrdersAdapter(List<MyOrderItemModel> myOrdersItemModelList) {
        this.myOrdersItemModelList = myOrdersItemModelList;
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersAdapter.ViewHolder viewHolder, int i) {
        int resource = myOrdersItemModelList.get(i).getProductImage();
        int  rating = myOrdersItemModelList.get(i).getRating();
        String title = myOrdersItemModelList.get(i).getProductTitle();
        String deliveredDate = myOrdersItemModelList.get(i).getDeliveryStatus();
            viewHolder.setData(resource,title,deliveredDate,rating);


    }

    @Override
    public int getItemCount() {
        return myOrdersItemModelList.size();

    }

    @NonNull
    @Override
    public MyOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_order_item_layout,viewGroup,false);
        return new ViewHolder(view);

    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView productImage,deliveryIndiacator;
        private TextView productTitle,deliveryStatus;
        private LinearLayout rateNow;



        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.myOrderItemImage);
            productTitle = itemView.findViewById(R.id.orderTitle);
            deliveryIndiacator = itemView.findViewById(R.id.ordersStatusIndicator);
            deliveryStatus = itemView.findViewById(R.id.ordersDeliveryStatus);
            rateNow = itemView.findViewById(R.id.rate_now_container);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent orderIntent = new Intent(itemView.getContext(), OrderDetailsActivity.class);
                    itemView.getContext().startActivity(orderIntent);
                }
            });

        }
        private void setData(int resources,String title,String deliveredDate,int rating){
            productImage.setImageResource(resources);
            productTitle.setText(title);
            if (deliveredDate.equals("cancelled")) {
                deliveryIndiacator.setImageTintList(ColorStateList.valueOf(itemView.getResources().getColor(R.color.colorPrimary)));
            }else{
                deliveryIndiacator.setImageTintList(ColorStateList.valueOf(itemView.getResources().getColor(R.color.colorPrimaryDark)));
            }
            deliveryStatus.setText(deliveredDate);
            setRating(rating);
            for (int x =0;x< rateNow.getChildCount();x++){
                final  int startPosition = x;
                rateNow.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setRating(startPosition);
                    }
                });
            }


        }
        private void setRating(int starPosition) {
            for (int x=0;x<rateNow.getChildCount();x++){
                ImageView imageView = (ImageView)rateNow.getChildAt(x);
                imageView.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
                if (x <= starPosition){
                    imageView.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));

                }

            }
        }

    }

}

