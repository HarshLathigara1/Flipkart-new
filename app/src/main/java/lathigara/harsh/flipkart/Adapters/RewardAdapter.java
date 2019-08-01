package lathigara.harsh.flipkart.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lathigara.harsh.flipkart.Activities.ProdutDetailsActivity;
import lathigara.harsh.flipkart.Models.RewardModel;
import lathigara.harsh.flipkart.R;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.ViewHolder> {
    private List<RewardModel>rewardModelList;
    private Boolean useMiniLayout = false;

    public RewardAdapter(List<RewardModel> rewardModelList,Boolean useMiniLayout) {
        this.rewardModelList = rewardModelList;
        this.useMiniLayout = useMiniLayout;
    }

    @NonNull
    @Override
    public RewardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (useMiniLayout){
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mini_rewards_item_layouto,parent,false);

        }else {
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rewards_item_layout,parent,false);
        }


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardAdapter.ViewHolder holder, int position) {
            String title = rewardModelList.get(position).getTitle();
            String body = rewardModelList.get(position).getCoupenBody();
            String date = rewardModelList.get(position).getExpiryDate();
            holder.setDate(title,body,date);
    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView coupenTitle,coupenExpiryDate,coupenBody;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coupenTitle = itemView.findViewById(R.id.coupen_title);
            coupenBody = itemView.findViewById(R.id.coupen_body);
            coupenExpiryDate = itemView.findViewById(R.id.coupen_validity);

        }
        private void setDate(final String title, final String body, final String date){
            coupenTitle.setText(title);
            coupenBody.setText(body);
            coupenExpiryDate.setText(date);

            if (useMiniLayout){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProdutDetailsActivity.coupenTitle.setText(title);
                        ProdutDetailsActivity.coupenBody.setText(body);
                        ProdutDetailsActivity.coupenExpiryDate.setText(date);
                        ProdutDetailsActivity.showDialogRecyclerView();
                    }
                });
            }
        }

    }
}
