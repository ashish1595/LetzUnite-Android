package com.letzunite.letzunite.ui.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.letzunite.letzunite.R;
import com.letzunite.letzunite.di.module.GlideApp;
import com.letzunite.letzunite.pojo.search.NearbyUserData;
import com.letzunite.letzunite.utils.UserAvatar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Deven Singh on 31 Jul, 2018.
 */
public class SearchedBloodAdapter extends RecyclerView.Adapter<SearchedBloodAdapter.SearchedBloodViewHolder> {

    private List<NearbyUserData> nearbyUsers;
    private Context context;

    public SearchedBloodAdapter(Context context, List<NearbyUserData> nearbyUsers) {
        this.context = context;
        this.nearbyUsers = nearbyUsers;
    }

    @Override
    public SearchedBloodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_nearby_user, parent, false);
        return new SearchedBloodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchedBloodViewHolder holder, int position) {
        NearbyUserData item = nearbyUsers.get(position);
        GlideApp.with(context)
                .load(item.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.userAvatar);
        holder.nameTv.setText(item.getName());
        holder.userTypeTv.setText(item.getType()+"");
        holder.bloodTypeTv.setText(item.getBloodType());
        holder.addressTv.setText(item.getAddress());
    }

    @Override
    public int getItemCount() {
        return nearbyUsers != null ? nearbyUsers.size() : 0;
    }

    class SearchedBloodViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_user_avatar)
        UserAvatar userAvatar;
        @BindView(R.id.tv_name)
        TextView nameTv;
        @BindView(R.id.tv_user_type)
        TextView userTypeTv;
        @BindView(R.id.tv_blood_type)
        TextView bloodTypeTv;
        @BindView(R.id.tv_address)
        TextView addressTv;

        public SearchedBloodViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
