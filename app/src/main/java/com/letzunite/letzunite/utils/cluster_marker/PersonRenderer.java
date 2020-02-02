package com.letzunite.letzunite.utils.cluster_marker;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.letzunite.letzunite.R;
import com.letzunite.letzunite.pojo.search.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deven Singh on 02 Aug, 2018.
 */
public class PersonRenderer extends DefaultClusterRenderer<Person> {

    private AppCompatActivity activity;

    private IconGenerator mIconGenerator;
    private IconGenerator mClusterIconGenerator;
    private final ImageView mImageView;
    private final ImageView mClusterImageView[]=new ImageView[4];
    private final LinearLayout mClusterLl;
    private final int mDimension;

    public PersonRenderer(AppCompatActivity activity, GoogleMap googleMap, ClusterManager<Person> mClusterManager) {
        super(activity, googleMap, mClusterManager);
        this.activity = activity;
        mIconGenerator = new IconGenerator(activity);
        mClusterIconGenerator = new IconGenerator(activity);
        View multiProfile = activity.getLayoutInflater().inflate(R.layout.layout_multi_profile, null);
        mClusterIconGenerator.setContentView(multiProfile);
        mClusterImageView[0] = multiProfile.findViewById(R.id.iv_cluster1);
        mClusterImageView[1] = multiProfile.findViewById(R.id.iv_cluster2);
        mClusterImageView[2] = multiProfile.findViewById(R.id.iv_cluster3);
        mClusterImageView[3] = multiProfile.findViewById(R.id.iv_cluster4);
        mClusterLl=multiProfile.findViewById(R.id.ll_cluster);
        mImageView = new ImageView(activity);
        mDimension = (int) activity.getResources().getDimension(R.dimen.custom_profile_image);
        mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));
        int padding = (int) activity.getResources().getDimension(R.dimen.custom_profile_padding);
        mImageView.setPadding(padding, padding, padding, padding);
        mIconGenerator.setContentView(mImageView);
    }

    @Override
    protected void onBeforeClusterItemRendered(Person person, MarkerOptions markerOptions) {
        // Draw a single person.
        // Set the info window to show their name.
        mImageView.setImageResource(person.profilePhoto);
        Bitmap icon = mIconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(person.name);
    }

    @Override
    protected void onClusterItemRendered(Person clusterItem, Marker marker) {

    }

    @Override
    protected void onBeforeClusterRendered(Cluster<Person> cluster, MarkerOptions markerOptions) {
        // Draw multiple people.
        // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).
        List<Drawable> profilePhotos = new ArrayList<Drawable>(Math.min(4, cluster.getSize()));
        int width = mDimension;
        int height = mDimension;

        for (Person p : cluster.getItems()) {
            // Draw 4 at most.
            if (profilePhotos.size() == 4) break;
            Drawable drawable = activity.getResources().getDrawable(p.profilePhoto);
            drawable.setBounds(0, 0, width, height);
            profilePhotos.add(drawable);
        }
        MultiDrawable multiDrawable = new MultiDrawable(profilePhotos);
        multiDrawable.setBounds(0, 0, width, height);
        mClusterLl.setVisibility(View.GONE);
        mClusterImageView[1].setVisibility(View.GONE);
        mClusterImageView[2].setVisibility(View.GONE);
        mClusterImageView[3].setVisibility(View.GONE);
        mClusterImageView[0].setImageDrawable(multiDrawable);
        Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
    }

    @Override
    protected void onClusterRendered(Cluster<Person> cluster, Marker marker) {

    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster cluster) {
        // Always render clusters.
        return cluster.getSize() > 1;
    }
}
