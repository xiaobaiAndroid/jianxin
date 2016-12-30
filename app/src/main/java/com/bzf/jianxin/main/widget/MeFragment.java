package com.bzf.jianxin.main.widget;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bzf.jianxin.R;
import com.bzf.jianxin.base.BaseFragment;
import com.bzf.jianxin.set.widget.SetActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 自己页面
 * com.bzf.jianxin.main.widget
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class MeFragment extends BaseFragment {


    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.tv_fxid)
    TextView tvFxid;
    @BindView(R.id.vProfileHeader)
    RelativeLayout vProfileHeader;
    @BindView(R.id.vQR)
    ImageView vQR;
    @BindView(R.id.re_myinfo)
    RelativeLayout reMyinfo;
    @BindView(R.id.re_xiangce)
    RelativeLayout reXiangce;
    @BindView(R.id.re_shoucang)
    RelativeLayout reShoucang;
    @BindView(R.id.re_money_bag)
    RelativeLayout reMoneyBag;
    @BindView(R.id.re_card_bag)
    RelativeLayout reCardBag;
    @BindView(R.id.re_setting)
    RelativeLayout reSetting;

    @Override
    protected View createView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_me, null);
    }

    @Override
    protected void init() {
        reSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SetActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
