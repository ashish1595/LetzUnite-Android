package com.letzunite.letzunite.ui.common;

import android.view.View;

/**
 * Created by Deven Singh on 14 Jun, 2018.
 */
public interface HybridRecyclerCickListener {

    void onItemClickListener(View view, String feedType, int position);

    void onChildItemClickListener(View view, String feedType, int position, int childPosition);
}
