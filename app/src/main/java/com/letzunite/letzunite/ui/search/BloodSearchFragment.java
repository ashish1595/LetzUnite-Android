package com.letzunite.letzunite.ui.search;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.letzunite.letzunite.R;
import com.letzunite.letzunite.ViewModelProviderFactory;
import com.letzunite.letzunite.enums.BloodType;
import com.letzunite.letzunite.pojo.common.ResponseResource;
import com.letzunite.letzunite.pojo.search.NearbyUserData;
import com.letzunite.letzunite.pojo.search.Person;
import com.letzunite.letzunite.ui.common.BaseFragment;
import com.letzunite.letzunite.ui.main_home.MainActivity;
import com.letzunite.letzunite.utils.Logger;
import com.letzunite.letzunite.utils.cluster_marker.PersonRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

import static com.letzunite.letzunite.ui.common.BaseActivityModule.BaseDeclarations.ACTIVITY_SUPPORT_FRAGMENT_MANAGER;

/**
 * Created by Deven Singh on 10 Jun, 2018.
 */
public class BloodSearchFragment extends BaseFragment implements OnMapReadyCallback, ClusterManager.OnClusterClickListener<Person>, ClusterManager.OnClusterInfoWindowClickListener<Person>, ClusterManager.OnClusterItemClickListener<Person>, ClusterManager.OnClusterItemInfoWindowClickListener<Person> {


    public static final String TAG = "BloodSearchFragment";

    @BindView(R.id.rv_blood_search)
    RecyclerView bloodSearchRv;

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private Handler handler;
    private SearchedBloodAdapter adapter;

    @Named(ACTIVITY_SUPPORT_FRAGMENT_MANAGER)
    @Inject
    FragmentManager fragmentManager;
    @Inject
    ViewModelProviderFactory providerFactory;
    private SearchBloodViewModel bloodViewModel;
    private List<NearbyUserData> nearbyUserList = new ArrayList<>();
    private ClusterManager<Person> mClusterManager;
    private Random mRandom = new Random(1984);

    @Override
    protected int getFragLayoutId() {
        return R.layout.fragment_search_nearby_user;
    }

    @Override
    protected ViewModel getViewModel() {
        return bloodViewModel = ViewModelProviders.of(this, providerFactory).get(SearchBloodViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).lockAppBarClosed();
        ((MainActivity) getActivity()).setToolbarTitle("Search");
        initializeGoogleMap();
        initComponents();
        bloodViewModel.getNearbyUsers().observe(this, observer);
        bloodViewModel.fetchNearbyUsers(1, "28.4595,77.0266",
                BloodType.DEFAULT.getBloodType(), 1000);
    }

    private void initComponents() {
        bloodSearchRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new SearchedBloodAdapter(getActivity(), nearbyUserList);
        bloodSearchRv.setAdapter(adapter);
    }

    private void initializeGoogleMap() {
        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    Observer<ResponseResource<List<NearbyUserData>>> observer = listResponseResource -> {
        switch (listResponseResource.status) {
            case LOADING:
//                progressBar.setVisibility(View.VISIBLE);
                break;
            case SUCCESS:
//                progressBar.setVisibility(View.GONE);
                nearbyUserList.clear();
                nearbyUserList.addAll(listResponseResource.data);
                adapter.notifyDataSetChanged();
                updateNearbyUserMarkersOnMap();
                Logger.debug("Success", "Response: " + listResponseResource.data);
                break;
            case ERROR:
//                progressBar.setVisibility(View.GONE);
                Logger.debug("Failure", "Code: " + listResponseResource.code +
                        "Message: " + listResponseResource.message);
                break;
            case LOGOUT:
//                progressBar.setVisibility(View.GONE);
                break;
        }
    };

    private void updateNearbyUserMarkersOnMap() {
        if (mMap == null || nearbyUserList == null || nearbyUserList.isEmpty()) return;
//        for (NearbyUserData nearbyUser : nearbyUserList) {
//
//        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setPadding(0, (int) appUtils.convertDpToPixel(60), 0, (int) appUtils.convertDpToPixel(156));
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getActivity(), R.raw.map_style));
            if (!success) {
                Logger.error(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Logger.error(TAG, "Can't find style. Error: ", e);
        }

        startClusteringMarker();
    }

    private void startClusteringMarker() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 9.5f));
        mClusterManager = new ClusterManager<Person>(getActivity(), mMap);
        mClusterManager.setRenderer(new PersonRenderer(getBaseActivity(), mMap, mClusterManager));
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(mClusterManager);
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);

        addItems();
        mClusterManager.cluster();
    }

    private void addItems() {
        // http://www.flickr.com/photos/sdasmarchives/5036248203/
        mClusterManager.addItem(new Person(position(), "Walter", R.drawable.walter));

        // http://www.flickr.com/photos/usnationalarchives/4726917149/
        mClusterManager.addItem(new Person(position(), "Gran", R.drawable.gran));

        // http://www.flickr.com/photos/nypl/3111525394/
        mClusterManager.addItem(new Person(position(), "Ruth", R.drawable.ruth));

        // http://www.flickr.com/photos/smithsonian/2887433330/
        mClusterManager.addItem(new Person(position(), "Stefan", R.drawable.stefan));

        // http://www.flickr.com/photos/library_of_congress/2179915182/
        mClusterManager.addItem(new Person(position(), "Mechanic", R.drawable.mechanic));

        // http://www.flickr.com/photos/nationalmediamuseum/7893552556/
        mClusterManager.addItem(new Person(position(), "Yeats", R.drawable.yeats));

        // http://www.flickr.com/photos/sdasmarchives/5036231225/
        mClusterManager.addItem(new Person(position(), "John", R.drawable.john));

        // http://www.flickr.com/photos/anmm_thecommons/7694202096/
        mClusterManager.addItem(new Person(position(), "Trevor the Turtle", R.drawable.turtle));

        // http://www.flickr.com/photos/usnationalarchives/4726892651/
        mClusterManager.addItem(new Person(position(), "Teach", R.drawable.teacher));
    }

    private LatLng position() {
        return new LatLng(random(51.6723432, 51.38494009999999), random(0.148271, -0.3514683));
    }

    private double random(double min, double max) {
        return mRandom.nextDouble() * (max - min) + min;
    }

    @Override
    public boolean onClusterClick(Cluster<Person> cluster) {
        // Show a toast with some info when the cluster is clicked.
        String firstName = cluster.getItems().iterator().next().name;
        Toast.makeText(getActivity(), cluster.getSize() + " (including " + firstName + ")", Toast.LENGTH_SHORT).show();

        // Zoom in the cluster. Need to create LatLngBounds and including all the cluster items
        // inside of bounds, then animate to center of the bounds.

        // Create the builder to collect all essential cluster items for the bounds.
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (ClusterItem item : cluster.getItems()) {
            builder.include(item.getPosition());
        }
        // Get the LatLngBounds
        final LatLngBounds bounds = builder.build();

        // Animate camera to the bounds
        try {
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<Person> cluster) {

    }

    @Override
    public boolean onClusterItemClick(Person person) {
        return false;
    }

    @Override
    public void onClusterItemInfoWindowClick(Person person) {

    }
}
