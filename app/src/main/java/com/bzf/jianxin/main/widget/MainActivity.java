package com.bzf.jianxin.main.widget;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.bzf.jianxin.R;
import com.bzf.jianxin.addcontact.widget.AddContactActivity;
import com.bzf.jianxin.base.BaseActivity;
import com.bzf.jianxin.main.customwidget.maintab.MainTabGroup;
import com.bzf.jianxin.service.MessageService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.vViewPager)
     ViewPager mViewPager;

    @BindView(R.id.vMainTabGroup)
     MainTabGroup mMainTabGroup;



    private MoreOptionsPopupWindow mPopupWindow;
    private ConversationListFragment mMessageListFragment;
    private ContactListFragment mContactListFragment;
    private DiscoverFragment mDiscoverFragment;
    private MeFragment mMeFragment;

    @Override
    protected void init() {
        initToolbar("简信");
        initFragment();
        startService(new Intent(this, MessageService.class));
    }

    @Override
    protected void createContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initToolbar(String title) {
        mToolbar.setTitle(title);
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
                break;
            case R.id.menu_add:
                showMoreOptions();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void showMoreOptions() {
        if(mPopupWindow==null){
            mPopupWindow = new MoreOptionsPopupWindow(this);
            mPopupWindow.setOnItemClickListener(new MoreOptionsPopupWindow.PopupWindowOnClickListener() {
                @Override
                public void onClickAddContact() {
                    mPopupWindow.dismiss();
                    startActivity(new Intent(MainActivity.this, AddContactActivity.class));
                }
            });
        }
        mPopupWindow.showPopupWindow(mToolbar);
    }

    private void initFragment() {
        mMessageListFragment = new ConversationListFragment();
        mContactListFragment = new ContactListFragment();
        mDiscoverFragment = new DiscoverFragment();
        mMeFragment = new MeFragment();
        List<Fragment> list = new ArrayList<Fragment>();
        list.add(mMessageListFragment);
        list.add(mContactListFragment);
        list.add(mDiscoverFragment);
        list.add(mMeFragment);
        mMainTabGroup.init(list,getSupportFragmentManager());
    }

    @Override
    public void onBackPressed() {
        //返回手机主界面
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
