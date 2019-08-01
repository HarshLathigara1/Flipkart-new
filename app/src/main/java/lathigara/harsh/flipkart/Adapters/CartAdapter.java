package lathigara.harsh.flipkart.Adapters;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lathigara.harsh.flipkart.Models.CartItemModel;
import lathigara.harsh.flipkart.R;

public class CartAdapter extends RecyclerView.Adapter {
    private List<CartItemModel> cartItemModelList;

    public CartAdapter(List<CartItemModel> cartItemModelList) {
        this.cartItemModelList = cartItemModelList;
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
                int resource = cartItemModelList.get(position).getProductImage();
                String title = cartItemModelList.get(position).getProductTitle();
                int freeCoupens = cartItemModelList.get(position).getFreeCoupens();
                String productPrice = cartItemModelList.get(position).getProductPrice();
                String cuttedPrice = cartItemModelList.get(position).getCuttedPrice();
                int offersApplied = cartItemModelList.get(position).getOffersApplied();
                ((cartItemViewHolder)holder).setitemDetails(resource,title,freeCoupens,productPrice,cuttedPrice,offersApplied);
                break;
            case CartItemModel.TOTAL_AMOUNT:
                String totalItems = cartItemModelList.get(position).getTotalItems();
                String totalItemsPrice = cartItemModelList.get(position).getTotalItemPrice();
                String deliveryPrice = cartItemModelList.get(position).getDeliveryPice();
                String totalAmount = cartItemModelList.get(position).getTotalAmount();
                String savedAmount = cartItemModelList.get(position).getSavedAmount();
                ((cartTotalAmountViewHolder)holder).setTotalAmount(totalItems,totalItemsPrice,deliveryPrice,totalAmount,savedAmount);


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

        public cartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            freeCoupensIcon = itemView.findViewById(R.id.freeCoupensIcon);
            freecoupens = itemView.findViewById(R.id.txtfreeCoupen);
            productPrice = itemView.findViewById(R.id.product_price);
            cutterdPrice = itemView.findViewById(R.id.cuttedPrice);
            offersApplied = itemView.findViewById(R.id.offerApplied);
            coupensApplied = itemView.findViewById(R.id.CouppenApplied);
            productQuantity = itemView.findViewById(R.id.Productquantity);
        }

        private void setitemDetails(int resource, String title, int freecoupensNo, String productPriceText, String cuttedPriceText, int offersAppliedNo) {
            productImage.setImageResource(resource);
            productTitle.setText(title);
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
            if (offersAppliedNo > 0) {
                offersApplied.setVisibility(View.VISIBLE);
                offersApplied.setText(offersAppliedNo + "offers applied");
            } else {
                offersApplied.setVisibility(View.INVISIBLE);
            }
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

        private void setTotalAmount(String totalItemText, String totalItemPriceText, String deliveryPriceText, String totalAmountText, String savedAmountText) {
            totalItems.setText(totalItemText);
            totalItemPrice.setText(totalItemPriceText);
            deliveryPrice.setText(deliveryPriceText);
            totalAmount.setText(totalAmountText);
            savedAmount.setText(savedAmountText);
        }
    }
}
