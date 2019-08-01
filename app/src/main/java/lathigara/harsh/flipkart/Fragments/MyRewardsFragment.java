package lathigara.harsh.flipkart.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lathigara.harsh.flipkart.Adapters.RewardAdapter;
import lathigara.harsh.flipkart.Models.RewardModel;
import lathigara.harsh.flipkart.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyRewardsFragment extends Fragment {


    public MyRewardsFragment() {
        // Required empty public constructor
    }

        private RecyclerView rewardRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_rewards, container, false);
        rewardRecyclerView = view.findViewById(R.id.my_rewards_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rewardRecyclerView.setLayoutManager(layoutManager);
        List<RewardModel>list = new ArrayList<>();
        list.add(new RewardModel("Cash Back","5/2/2019","til 2 june 2019"));
        list.add(new RewardModel("Cash Back","5/2/2019","til 2 june 2019"));
        list.add(new RewardModel("Cash Back","5/2/2019","til 2 june 2019"));
        list.add(new RewardModel("Cash Back","5/2/2019","til 2 june 2019"));
        RewardAdapter rewardAdapter = new RewardAdapter(list,false);
        rewardRecyclerView.setAdapter(rewardAdapter);
        rewardAdapter.notifyDataSetChanged();
        return view;
    }

}
