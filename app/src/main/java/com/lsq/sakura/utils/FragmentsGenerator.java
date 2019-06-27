package com.lsq.sakura.utils;

import android.support.v4.app.Fragment;

import com.lsq.sakura.fragment.AllVideosFragment;
import com.lsq.sakura.fragment.HistoryFragment;
import com.lsq.sakura.fragment.HomeFragment;
import com.lsq.sakura.fragment.MineFragment;

public class FragmentsGenerator {
//    public static final int[] mTabRes = new int[]{R.drawable.tab_home_selector,R.drawable.tab_discovery_selector,R.drawable.tab_attention_selector,R.drawable.tab_profile_selector};
//    public static final int[] mTabResPressed = new int[]{R.drawable.ic_tab_strip_icon_feed_selected,R.drawable.ic_tab_strip_icon_category_selected,R.drawable.ic_tab_strip_icon_pgc_selected,R.drawable.ic_tab_strip_icon_profile_selected};
    public static final String[] mTabTitle = new String[]{"首页","所有动画","观看记录","我的"};

    public static Fragment[] getFragments(){
        Fragment[] fragments = new Fragment[4];
        fragments[0] = new HomeFragment();
        fragments[1] = new AllVideosFragment();
        fragments[2] = new HistoryFragment();
        fragments[3] = new MineFragment();
        return fragments;
    }

}
