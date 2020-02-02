package com.letzunite.letzunite.ui.feeds;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.letzunite.letzunite.R;
import com.letzunite.letzunite.enums.FeedsType;
import com.letzunite.letzunite.pojo.feeds.FeedData;
import com.letzunite.letzunite.pojo.feeds.NotificationDetails;
import com.letzunite.letzunite.utils.UserAvatar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Deven Singh on 16 Jun, 2018.
 */
public class HorizontalFeedsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NotificationDetails> notificationDetailsList;
    private int parentPosition = -1;

    protected void setData(List<NotificationDetails> notificationDetailsList, int parentPosition) {
        if (this.notificationDetailsList != notificationDetailsList) {
            this.notificationDetailsList = notificationDetailsList;
            notifyDataSetChanged();
        }
        this.parentPosition = parentPosition;
    }

    @Override
    public int getItemViewType(int position) {
        if (notificationDetailsList == null || notificationDetailsList.get(position) == null ||
                notificationDetailsList.get(position).getNotificationTypeId() == null)
            return FeedsType.DEFAULT_FEEDS.getFeedType();
        return notificationDetailsList.get(position).getNotificationTypeId();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (FeedsType.getType(viewType)) {
            case CHILD_CARE_FEEDS:
                return new ChildCareViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.row_child_care, parent, false));
            case BLOOD_REQUEST_FEEDS:
            default:
                return new BloodViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.row_requested_blood, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NotificationDetails details = notificationDetailsList.get(position);
        switch (FeedsType.getType(holder.getItemViewType())) {
            case CHILD_CARE_FEEDS:
                configureChildCareView(holder, details);
                break;
            case BLOOD_REQUEST_FEEDS:
            default:
                configureBloodRequestView(holder, details);
                break;
        }
    }

    private void configureBloodRequestView(RecyclerView.ViewHolder holder, NotificationDetails details) {
        BloodViewHolder viewHolder = (BloodViewHolder) holder;
        FeedData feedData = details.getFeedData();
        if (feedData.getPatientName().startsWith("A")){
            viewHolder.userAvatar.setImageResource(R.drawable.deven);
        }else if (feedData.getPatientName().startsWith("H")){
            viewHolder.userAvatar.setImageResource(R.drawable.deven);
        }else {
            viewHolder.userAvatar.setImageResource(R.drawable.deven);
        }
        viewHolder.nameTv.setText(feedData.getPatientName());
        viewHolder.bloodGroupTv.setText(feedData.getBloodType());
        viewHolder.contactNumTv.setText(feedData.getContactPersonNumber());
        viewHolder.requestDateTv.setText(details.getCreatedDate());
        viewHolder.addressTv.setText(feedData.getLocation() + ", " + feedData.getCity() + ", " + feedData.getCity());
        viewHolder.descriptionTv.setText("I need O+ blood urgently. Please contact mentioned number if any one wants to donate blood." +
                " If your blood group is not O+ even then you can donate, blood bank will give O+ blood in place of your blood.");
    }

    private void configureChildCareView(RecyclerView.ViewHolder holder, NotificationDetails details) {
        ChildCareViewHolder viewHolder = (ChildCareViewHolder) holder;

    }

    @Override
    public int getItemCount() {
        return notificationDetailsList != null ? notificationDetailsList.size() : 0;
    }

    class BloodViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView nameTv;
        @BindView(R.id.tv_blood_group)
        TextView bloodGroupTv;
        @BindView(R.id.tv_requested_date)
        TextView requestDateTv;
        @BindView(R.id.tv_address)
        TextView addressTv;
        @BindView(R.id.tv_description)
        TextView descriptionTv;
        @BindView(R.id.tv_show_details)
        TextView showDetailsTv;
        @BindView(R.id.tv_contact_num)
        TextView contactNumTv;
        @BindView(R.id.iv_user_avatar)
        UserAvatar userAvatar;

        public BloodViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ChildCareViewHolder extends RecyclerView.ViewHolder {

        public ChildCareViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
