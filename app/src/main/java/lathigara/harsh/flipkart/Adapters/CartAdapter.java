package lathigara.harsh.flipkart.Adapters;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import lathigara.harsh.flipkart.Activities.ProdutDetailsActivity;
import lathigara.harsh.flipkart.DBQueries;
import lathigara.harsh.flipkart.Models.CartItemModel;
import lathigara.harsh.flipkart.R;

public class CartAdapter extends RecyclerView.Adapter {
    private List<CartItemModel> cartItemModelList;
    private TextView totalCartPrice;

    public CartAdapter(List<CartItemModel> cartItemModelList,TextView totalCartPrice) {
        this.cartItemModelList = cartItemModelList;
        this.totalCartPrice = totalCartPrice;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cartItemModelList.get(position).getType()) {
            case 0:
                return CartItemModel.CART_ITEM;

            case 1:
                return CartItemModel.TOTAL_AMOUNT;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case CartItemModel.CART_ITEM:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
                return new cartItemViewHolder(view);
            case CartItemModel.TOTAL_AMOUNT:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_total_amount, parent, false);
                return new cartTotalAmountViewHolder(view1);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (cartItemModelList.get(position).getType()) {
            case CartItemModel.CART_ITEM:
                String productId =  cartItemModelList.get(position).getProductId();
                String resource = cartItemModelList.get(position).getProductImage();
                String title = cartItemModelList.get(position).getProductTitle();
                Long freeCoupens = cartItemModelList.get(position).getFreeCoupens();
                String productPrice = cartItemModelList.get(position).getProductPrice();
                String cuttedPrice = cartItemModelList.get(position).getCuttedPrice();
                Long offersApplied = cartItemModelList.get(position).getOffersApplied();
                boolean inStock = cartItemModelList.get(position).isInStock();
                ((cartItemViewHolder)holder).setitemDetails(productId,resource,title,freeCoupens,productPrice,cuttedPrice,offersApplied,position,inStock);
                break;
            case CartItemModel.TOTAL_AMOUNT:

                int totalItems =  0;
                int totalItemPrice = 0;
                String deliveryPrice;
                int totalAmount;
                int savedAmount  = 0;




                for (int x =0;x < cartItemModelList.size();x++){
                    if (cartItemModelList.get(x).getType() == CartItemModel.CART_ITEM && cartItemModelList.get(x).isInStock()){

                        totalItems++;

                        totalItemPrice = totalItemPrice + Integer.parseInt(cartItemModelList.get(x).getProductPrice());

                    }
                }

                if (totalItemPrice > 500){
                    deliveryPrice = "FREE";
                    totalAmount = totalItemPrice;
                }else {
                    deliveryPrice = "60";
                    totalAmount = totalItemPrice + 60;
                }



              //  String totalItems = cartItemModelList.get(position).getTotalItems();
               // String totalItemsPrice = cartItemModelList.get(position).getTotalItemPrice();
                ((cartTotalAmountViewHolder)holder).setTotalAmount(totalItems,totalItemPrice,deliveryPrice,totalAmount,savedAmount);


                break;
            default:
                return;
        }


    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }

    class cartItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage, freeCoupensIcon;
        private TextView productTitle, freecoupens, productPrice, cutterdPrice, offersApplied, coupensApplied, productQuantity;
        private LinearLayout deleteButton;
        private LinearLayout coupenRedemptionLayout;

        public cartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            freeCoupensIcon = itemView.findViewById(R.id.freeCoupensIcon);
            freecoupens = itemView.findViewById(R.id.txtfreeCoupen);
            productPrice = itemView.findViewById(R.id.product_price);
            cutterdPrice = itemView.findViewById(R.id.cuttedPricee);
            offersApplied = itemView.findViewById(R.id.offerApplied);
            coupensApplied = itemView.findViewById(R.id.CouppenApplied);
            productQuantity = itemView.findViewById(R.id.Productquantity);
            deleteButton = itemView.findViewById(R.id.removeContainer);
            coupenRedemptionLayout = itemView.findViewById(R.id.coupen_redeemptionLayout);
        }

        private void setitemDetails(String productId, String resource, String title, Long freecoupensNo, String productPriceText, String cuttedPriceText, Long offersAppliedNo, final int position,boolean inStock) {
           // productImage.setImageResource(resource);
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background)).into(productImage);
            productTitle.setText(title);



             //// main thing /////
            if (inStock) {

                if (freecoupensNo > 0) {
                    freeCoupensIcon.setVisibility(View.VISIBLE);
                    freecoupens.setVisibility(View.VISIBLE);
                    if (freecoupensNo == 1) {
                        freecoupens.setText("free" + freecoupensNo + "Coupen");
                    } else {
                        freecoupens.setText("free" + freecoupensNo + "Coupen");
                    }


                } else {
                    freeCoupensIcon.setVisibility(View.INVISIBLE);
                    freecoupens.setVisibility(View.INVISIBLE);

                }

                productPrice.setText(productPriceText);
                cutterdPrice.setText(cuttedPriceText);
                coupenRedemptionLayout.setVisibility(View.VISIBLE);
                productQuantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog qyantityDailog = new Dialog(itemView.getContext());
                        qyantityDailog.setContentView(R.layout.quantity_dialog);
                        qyantityDailog.setCancelable(false);
                        final EditText quantityNo  = qyantityDailog.findViewById(R.id.quantity_coount);
                        Button cancelButton = qyantityDailog.findViewById(R.id.quantityCancelBtn);
                        Button okBtn = qyantityDailog.findViewById(R.id.quantityOkbtn);

                        cancelButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                qyantityDailog.dismiss();

                            }
                        });

                        okBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                productQuantity.setText("Qty: "+quantityNo.getText());
                                qyantityDailog.dismiss();

                            }
                        });
                        qyantityDailog.show();

                    }
                });

                if (offersAppliedNo > 0) {
                    offersApplied.setVisibility(View.VISIBLE);
                    offersApplied.setText(offersAppliedNo + "offers applied");
                } else {
                    offersApplied.setVisibility(View.INVISIBLE);
                }

            }else {
                productPrice.setText("OUT OF STOCK");
                productPrice.setTextColor(Color.parseColor("#9e9e9e"));
                cutterdPrice.setText("");
                coupenRedemptionLayout.setVisibility(View.GONE);
                freecoupens.setVisibility(View.INVISIBLE);
                productQuantity.setText("Qty: "+0);
                productQuantity.setTextColor(Color.parseColor("#9e9e9e"));
                productQuantity.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                coupensApplied.setVisibility(View.GONE);
                offersApplied.setVisibility(View.GONE);
                freeCoupensIcon.setVisibility(View.INVISIBLE);

            }



            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!ProdutDetailsActivity.running_cart_query){
                        ProdutDetailsActivity.running_cart_query = true;
                        DBQueries.removeFromCart(position,itemView.getContext(),totalCartPrice);
                    }
                }
            });

        }
    }

    class cartTotalAmountViewHolder extends RecyclerView.ViewHolder {
        private TextView totalItems, totalItemPrice, deliveryPrice, totalAmount, savedAmount;

        public cartTotalAmountViewHolder(@NonNull View itemView) {
            super(itemView);
            totalItems = itemView.findViewById(R.id.total_items);
            totalItemPrice = itemView.findViewById(R.id.total_items_price);
            deliveryPrice = itemView.findViewById(R.id.delivery_price);
            totalAmount = itemView.findViewById(R.id.total_price);
            savedAmount = itemView.findViewById(R.id.saved_amount);

        }

        private void setTotalAmount(int totalItemText, int totalItemPriceText, String deliveryPriceText, int totalAmountText, int savedAmountText) {
            totalItems.setText("Price("+totalItemText+"items)");
            totalItemPrice.setText("Rs."+totalItemPriceText+"/-");
            if (deliveryPriceText.equals("FREE")){
                deliveryPrice.setText(deliveryPriceText);

            }else {

                deliveryPrice.setText("Rs."+deliveryPriceText+"/-");

            }
            totalAmount.setText("Rs."+totalAmountText+"/-");
            totalCartPrice.setText("Rs."+totalAmountText+"/-");

            LinearLayout parent = (LinearLayout) totalCartPrice.getParent().getParent();

            savedAmount.setText("You Saved Rs."+savedAmountText+"/- on This order");
            if (totalItemPriceText == 0){
                DBQueries.cartItemModelList.remove(DBQueries.cartItemModelList.size() -1);
                parent.setVisibility(View.GONE);
            }else {
                parent.setVisibility(View.VISIBLE);

            }
        }
    }
}
