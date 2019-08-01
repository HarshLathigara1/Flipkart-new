package lathigara.harsh.flipkart.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lathigara.harsh.flipkart.Models.AddressesModel;
import lathigara.harsh.flipkart.R;

import static lathigara.harsh.flipkart.Activities.DeliveryActivity.SELECT_ADDRESS;
import static lathigara.harsh.flipkart.Activities.MyAddressActivity.refreshItem;
import static lathigara.harsh.flipkart.Fragments.MyAccountFragment.MANAGE_ADDRESS;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.ViewHolder>{
    List<AddressesModel>addressesModelList;
    private int MODE;
    private int preSelectedPosition;

    public AddressesAdapter(List<AddressesModel> addressesModelList,int MODE) {
        this.addressesModelList = addressesModelList;
        this.MODE = MODE;
    }


    @NonNull
    @Override
    public AddressesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addresses_item_layout,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressesAdapter.ViewHolder holder, int position) {
        String address = addressesModelList.get(position).getAddress();
        String name = addressesModelList.get(position).getFullname();
        String pinCode = addressesModelList.get(position).getPincode();
        Boolean selected = addressesModelList.get(position).getSelected();
        holder.setData(name,address,pinCode,selected,position);

    }

    @Override
    public int getItemCount() {
        return addressesModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView fullname,addresses,pincode;
        private ImageView icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.name);
            addresses = itemView.findViewById(R.id.address);
            pincode = itemView.findViewById(R.id.pinCode);
            icon = itemView.findViewById(R.id.icon_view);
        }
        private void setData(String name, String add, String pin, Boolean selected, final int position){
            fullname.setText(name);
            addresses.setText(add);
            pincode.setText(pin);
            if (MODE == SELECT_ADDRESS){
                icon.setImageResource(R.drawable.ic_checkkkk);
                if (selected){
                    icon.setVisibility(View.VISIBLE);
                    preSelectedPosition = position;
                }else{
                    icon.setVisibility(View.GONE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            if (preSelectedPosition != position) {
                                addressesModelList.get(position).setSelected(true);
                                addressesModelList.get(preSelectedPosition).setSelected(false);
                                refreshItem(preSelectedPosition, position);
                                preSelectedPosition = position;
                            }
                    }
                });
            }else if (MODE == MANAGE_ADDRESS){

            }
        }
    }
}