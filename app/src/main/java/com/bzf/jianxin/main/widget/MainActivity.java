package com.bzf.jianxin.main.widget;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bzf.jianxin.R;
import com.bzf.jianxin.base.BaseActivity;
import com.bzf.jianxin.main.maintab.MainTabGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private MainTabGroup mMainTabGroup;

    private AddPopWindow addPopWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initToolbar();
        initFragment();
    }

    private void initToolbar() {
        mToolbar.setTitle("微信");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                Toast.makeText(mContext, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_add:
                Toast.makeText(mContext, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void initFragment() {
        List<Fragment> list = new ArrayList<Fragment>();
        list.add(new MessageListFragment());
        list.add(new ContactListFragment());
        list.add(new DiscoverFragment());
        list.add(new MeFragment());
        mMainTabGroup.init(list,getSupportFragmentManager());
    }

    private void initView() {
        mToolbar = getView(R.id.toolbar);
        mViewPager = getView(R.id.vViewPager);
        mMainTabGroup = getView(R.id.vMainTabGroup);

    }

    @Override
    public void onBackPressed() {
        //返回手机主界面
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
