package com.letzunite.letzunite.ui.feeds;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.letzunite.letzunite.R;
import com.letzunite.letzunite.enums.FeedsType;
import com.letzunite.letzunite.pojo.feeds.Feeds;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Deven Singh on 14 Jun, 2018.
 */
public class FeedsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Feeds> feedsList;

    public FeedsAdapter(List<Feeds> feedsList) {
        this.feedsList = feedsList;
    }

    @Override
    public int getItemViewType(int position) {
        if (feedsList == null || feedsList.isEmpty()) return FeedsType.DEFAULT_FEEDS.getFeedType();
        return feedsList.get(position).getNotificationTypeId();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (FeedsType.getType(viewType)) {
            case BLOOD_REQUEST_FEEDS:
            case CHILD_CARE_FEEDS:
                return new ListOfFeedsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_feeds, parent, false));
            case GENERAL_FEEDS:
            default:
                return new GeneralFeedsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_general_feeds, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Feeds feeds = feedsList.get(position);
        switch (FeedsType.getType(holder.getItemViewType())) {
            case BLOOD_REQUEST_FEEDS:
            case CHILD_CARE_FEEDS:
                configureListFeeds(holder, position);
                return;
            case GENERAL_FEEDS:
            default:
                configureGeneralFeeds(feeds, holder);
                return;
        }
    }

    private void configureListFeeds(RecyclerView.ViewHolder holder, int position) {
        ListOfFeedsViewHolder viewHolder = (ListOfFeedsViewHolder) holder;
        viewHolder.horizontalFeedsAdapter.setData(feedsList.get(position).getNotificationDetailsList(), position);
    }

    private void configureGeneralFeeds(Feeds feeds, RecyclerView.ViewHolder holder) {
        GeneralFeedsViewHolder viewHolder = (GeneralFeedsViewHolder) holder;
        viewHolder.descriptionTv.setText(feeds.getNotificationDetails().getFeedData().getDescription());
    }

    @Override
    public int getItemCount() {
        return feedsList != null ? feedsList.size() : 0;
    }

    class ListOfFeedsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rv_list_feeds)
        RecyclerView listFeedsRv;
        private HorizontalFeedsAdapter horizontalFeedsAdapter;

        public ListOfFeedsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            horizontalFeedsAdapter = new HorizontalFeedsAdapter();
            listFeedsRv.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            listFeedsRv.setAdapter(horizontalFeedsAdapter);
        }
    }

    class GeneralFeedsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_description)
        TextView descriptionTv;

        public GeneralFeedsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
