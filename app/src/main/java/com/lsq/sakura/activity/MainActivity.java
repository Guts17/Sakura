package com.lsq.sakura.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lsq.sakura.R;
import com.lsq.sakura.base.BaseActivity;
import com.lsq.sakura.utils.FragmentsGenerator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment[] mFragments;
    @BindView(R.id.et_search) EditText et_search;
    @BindView(R.id.tv_search) TextView tv_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return true;
    }

    void initView(){
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_toolbar_search:
                        if(et_search.getVisibility() == View.GONE){
                            tv_search.setVisibility(View.GONE);
                            et_search.setVisibility(View.VISIBLE);
                            et_search.setFocusable(true);
                            et_search.setFocusableInTouchMode(true);
                            et_search.requestFocus();
                        }else {
                            et_search.setVisibility(View.GONE);
                            tv_search.setText(getString(R.string.search_text,et_search.getText().toString()));
                            tv_search.setVisibility(View.VISIBLE);
                        }
                        break;
                }
                return true;
            }
        });
        mFragments = FragmentsGenerator.getFragments();
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                onTabItemSelected(menuItem.getItemId());
                return true;
            }
        });
        onTabItemSelected(R.id.menu_tab_home);
    }

    private void onTabItemSelected(int id){
        Fragment fragment = null;
        switch (id){
            case R.id.menu_tab_home:
                fragment = mFragments[0];
                break;
            case R.id.menu_tab_allvideos:
                fragment = mFragments[1];
                break;
            case R.id.menu_tab_history:
                fragment = mFragments[2];
                break;
            case R.id.menu_tab_mine:
                fragment = mFragments[3];
                break;
        }
        if(fragment!=null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_container,fragment).commit();
        }
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
