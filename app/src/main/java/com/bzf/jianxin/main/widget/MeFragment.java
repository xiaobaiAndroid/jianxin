package com.bzf.jianxin.main.widget;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bzf.jianxin.R;
import com.bzf.jianxin.base.BaseFragment;
import com.bzf.jianxin.bean.User;
import com.bzf.jianxin.commonutils.ToastTool;
import com.bzf.jianxin.main.presenter.MePresenterImpl;
import com.bzf.jianxin.main.view.MeView;
import com.bzf.jianxin.set.widget.SetActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * 自己页面
 * com.bzf.jianxin.main.widget
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class MeFragment extends BaseFragment<MePresenterImpl> implements MeView{

    private static final String TAG = MeFragment.class.getName();

    @BindView(R.id.iv_avatar)
    SimpleDraweeView ivAvatar;
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

    private static final String USERNAME_TAG = "简信号：";

    /**
     * 选择相册
     */
    private static final int CROP_PHOTO = 2;

    /**
     * 拍照
     */
    private static final int TAKE_PHOTO = 1;

    /**
     * 裁剪完毕
     */
    private static final int CUTTING = 3;

    @Override
    protected View createView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_me, null);
    }

    @Override
    protected void init() {
        mPresenter = new MePresenterImpl(this);
        mPresenter.getUserInfo();
    }

    /**
     * 设置头像
     */
    @OnClick(R.id.iv_avatar)
    public void setAvatarClick(){
        selectAvatar();
    }

    /**
     * 跳转到设置页面
     */
    @OnClick(R.id.re_setting)
    public void IntoSettingActivity(){
        Intent intent = new Intent(getActivity(), SetActivity.class);
        MainActivity activity = (MainActivity) getActivity();
        activity.startOtherActivity(intent);
    }

    /**
     * 选择头像
     */
    private void selectAvatar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setItems(new String[]{"拍照", "相册"}, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                try {
                    mPresenter.createSaveImageFile(getApplicationContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                switch (which) {
                    case 0:
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Uri uri = Uri.fromFile(mPresenter.mOutputImageFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, TAKE_PHOTO); // 启动相机程序
                        break;
                    case 1:
                        intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, CROP_PHOTO);
                        break;
                }
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Intent intent = mPresenter.getPhotoZoomIntent(Uri.fromFile(mPresenter.mOutputImageFile));
                        startActivityForResult(intent,CUTTING);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        startActivityForResult(mPresenter.getPhotoZoomIntent(data.getData()), CUTTING);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CUTTING:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap avatar = extras.getParcelable("data");
                        mPresenter.updateAvatar(avatar);
                    }
                }
            default:
                break;
        }
    }


    @Override
    public void requestUserMessageSuccess(User user) {
        String nickName = user.getNickName();
        if(TextUtils.isEmpty(nickName)){
            tvName.setText(user.getUsername());
        }else{
            tvName.setText(nickName);
        }
        tvFxid.setText(USERNAME_TAG+user.getUsername());
        mPresenter.loadAvatar(user.getAvatarUrl(),ivAvatar);
    }

    @Override
    public void requestUserMessageFail(String msg) {
        ToastTool.shotMessage(msg);
    }

    @Override
    public void updateAvatarSuccess(String avatarUrl) {
        mPresenter.loadAvatar(avatarUrl,ivAvatar);
    }

    @Override
    public void updateAvatarFail(String msg) {
        ToastTool.shotMessage(msg);
    }
}
