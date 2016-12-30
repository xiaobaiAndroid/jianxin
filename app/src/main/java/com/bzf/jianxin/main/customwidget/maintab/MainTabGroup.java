package com.bzf.jianxin.main.customwidget.maintab;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bzf.jianxin.R;

import java.util.List;

/**
 * com.bzf.jianxin.main.tabviewpager
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class MainTabGroup extends LinearLayout implements BottomButtonListener {

    private Context mContext;
    private ViewPager mViewPager;
    private BottomButtonGroup mBottomButtonGroup;
    private List<Fragment> mFragmentList;
    /**
     * 缓存多少页
     */
    private int mLimit = 3;


    public MainTabGroup(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public MainTabGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_maintab, this, true);
        mViewPager = (ViewPager) view.findViewById(R.id.vViewPager);
        mBottomButtonGroup = (BottomButtonGroup) view.findViewById(R.id.vBottomButtonGroup);
        //缓存四个页面
        mViewPager.setOffscreenPageLimit(mLimit);
        mBottomButtonGroup.setClickListener(this);
    };

    /**
     * 初始化
     * @param list
     */
    public void init(List<Fragment> list, FragmentManager manager) {
        mFragmentList = list;

        mViewPager.setAdapter(new FragmentPagerAdapter(manager) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * 此方法是页面跳转完后得到调用，arg0是你当前选中的页面的Position（位置编号）
             * @param position
             */
            @Override
            public void onPageSelected(int position) {
            }

            /**
             * @param position 当前页面，即你点击滑动的页面,向左滑position会变，向右滑不会变
             * @param positionOffset 当前页面偏移的百分比
             * @param positionOffsetPixels 当前页面偏移的像素位置 以屏幕的右上角为参考点，向右滑，值由大变小，向左滑，值由小变大
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mBottomButtonGroup.judgeOrientation(position, positionOffset, positionOffsetPixels);
            }

            /**
             * 此方法是在状态改变的时候调用，其中arg0这个参数
             *有三种状态（0，1，2）。arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。
             * @param arg0
             */
            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    @Override
    public void clickWeiXin() {
        mBottomButtonGroup.showCurrentTab(mBottomButtonGroup.getmWeiXinPosition());
        mViewPager.setCurrentItem(mBottomButtonGroup.getmWeiXinPosition(), false);
    }

    @Override
    public void clickContact() {
        mBottomButtonGroup.showCurrentTab(mBottomButtonGroup.getmContactsPosition());
        mViewPager.setCurrentItem(mBottomButtonGroup.getmContactsPosition(), false);
    }

    @Override
    public void clickDiscover() {
        mBottomButtonGroup.showCurrentTab(mBottomButtonGroup.getmDiscoverPosition());
        mViewPager.setCurrentItem(mBottomButtonGroup.getmDiscoverPosition(), false);
    }

    @Override
    public void clickProfile() {
        mBottomButtonGroup.showCurrentTab(mBottomButtonGroup.getmProfilePosition());
        mViewPager.setCurrentItem(mBottomButtonGroup.getmProfilePosition(), false);
    }
}
