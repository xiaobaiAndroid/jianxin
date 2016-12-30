package com.bzf.jianxin.register.widget;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bzf.jianxin.R;
import com.bzf.jianxin.base.BaseActivity;
import com.bzf.jianxin.bean.User;
import com.bzf.jianxin.commonutils.AppTool;
import com.bzf.jianxin.commonutils.DateTool;
import com.bzf.jianxin.commonutils.FileTool;
import com.bzf.jianxin.commonutils.ToastTool;
import com.bzf.jianxin.login.widget.LoginActivity;
import com.bzf.jianxin.register.presenter.RegistePresenterImpl;
import com.bzf.jianxin.register.view.RegisterView;
import com.bzf.jianxin.register.view.UploadAvatarView;

import java.io.File;
import java.util.Date;

import butterknife.BindView;

public class RegisterActivity extends BaseActivity<RegistePresenterImpl> implements RegisterView,UploadAvatarView {

    @BindView(R.id.et_username)
    EditText mEt_username;

    @BindView(R.id.ib_clearUserName)
    ImageButton mIb_clearName;

    @BindView(R.id.v_usernameLine)
    View mV_nameLine;

    @BindView(R.id.et_psw)
    EditText mEt_psw;

    @BindView(R.id.ib_showPsw)
    ImageButton mIb_showPsw;

    @BindView(R.id.v_pswLine)
    View mV_pswLine;

    @BindView(R.id.bt_register)
    Button mBt_register;
    @BindView(R.id.tv_usernick)
    TextView tvUsernick;
    @BindView(R.id.et_usernick)
    EditText mEt_usernick;
    @BindView(R.id.v_nickName)
    View vNickNameLine;
    @BindView(R.id.iv_photo)
    ImageView mIv_photo;
    @BindView(R.id.tv_city)
    TextView tvCity;

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
    private Uri mImageUri;
    private File mOutputImageFile;
    private User mUser;
    private ProgressDialog mProgressDialog;


    @Override
    protected void init() {
        mPresenter = new RegistePresenterImpl();
        initView();
        initListener();
    }

    @Override
    protected void createContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
    }

    private void initListener() {
        mEt_username.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                mEt_username.setSelection(charSequence.length());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if (TextUtils.isEmpty(text)) {
                    mIb_clearName.setVisibility(View.INVISIBLE);
                } else {
                    mIb_clearName.setVisibility(View.VISIBLE);
                }
                setLoginButtonState(!checkUserNamePsw());
            }
        });

        mEt_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                if (isFocus) {
                    mV_nameLine.setBackgroundResource(R.drawable.line_press);
                } else {
                    mV_nameLine.setBackgroundResource(R.drawable.line_normal);
                }
            }
        });

        mEt_psw.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                mEt_psw.setSelection(charSequence.length());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                setLoginButtonState(!checkUserNamePsw());
            }
        });


        mEt_psw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                if (isFocus) {
                    mV_pswLine.setBackgroundResource(R.drawable.line_press);
                } else {
                    mV_pswLine.setBackgroundResource(R.drawable.line_normal);
                }

            }
        });

        mIb_clearName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEt_username.setText("");
            }
        });

        mIb_showPsw.setOnClickListener(new View.OnClickListener() {
            private boolean isShowPsw = false;

            @Override
            public void onClick(View view) {
                if (isShowPsw) {
                    mEt_psw.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    mEt_psw.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
                }
                isShowPsw = !isShowPsw;
            }
        });

        mBt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPresenter != null) {
                    String userName = mEt_username.getText().toString().trim();
                    String psw = mEt_psw.getText().toString().trim();
                    String nickName = mEt_usernick.getText().toString().trim();
                    mPresenter.register(RegisterActivity.this, userName, psw,nickName);
                }
            }
        });

        //选择头像
        mIv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAvatar();
            }
        });

        mEt_usernick.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    vNickNameLine.setBackgroundResource(R.drawable.line_press);
                } else {
                    vNickNameLine.setBackgroundResource(R.drawable.line_normal);
                }
            }
        });

    }

    /**
     * 选择头像
     */
    private void selectAvatar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setItems(new String[]{"拍照", "相册"}, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                mOutputImageFile = null;
                try {
                    createSaveImageFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                switch (which) {
                    case 0:
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Uri uri = Uri.fromFile(mOutputImageFile);
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

    private void createSaveImageFile() throws Exception {
        String packageName = AppTool.getPackageName(getApplicationContext());
        String dirName = packageName.substring(packageName.lastIndexOf(".") + 1) + "/avatar";
        File dir = FileTool.createDir(dirName);
        String imagName = DateTool.fromateDate(new Date(), DateTool.PATTERN_1) + ".jpg";
        mOutputImageFile = FileTool.createFile(dir.getAbsolutePath(), imagName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    startPhotoZoom(Uri.fromFile(mOutputImageFile));
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    startPhotoZoom(data.getData());
                }
                break;
            case CUTTING:
                if (resultCode == RESULT_OK) {
                    setPicToView(data);
                }
            default:
                break;
        }
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CUTTING);
    }

    /**
     * 保存图片数据
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            mIv_photo.setImageBitmap(photo);
            mPresenter.saveAvatar(photo, 80, mOutputImageFile);

        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void initView() {
        initToolbar("注册");
        mIb_clearName.setVisibility(View.INVISIBLE);
        setLoginButtonState(false);
    }

    /**
     * 设置登录按钮的状态
     *
     * @param isShow
     */
    private void setLoginButtonState(boolean isShow) {
        mBt_register.setSelected(isShow);

    }

    /**
     * 检查用户名，密码是否为null
     */
    private boolean checkUserNamePsw() {
        return TextUtils.isEmpty(mEt_username.getText().toString()) || TextUtils.isEmpty(mEt_psw.getText().toString());
    }

    @Override
    public void showRegisterDialog() {
        if(mProgressDialog==null){
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("注册中。。");
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.show();
    }

    @Override
    public void closeRegisterDialog() {
        if(mProgressDialog!=null){
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void registerSuccess(User user) {
        mUser = user;
        mPresenter.uploadAvatar(this,mOutputImageFile,user.getUsername());
    }


    @Override
    public void registerFail(String msg) {
        ToastTool.shotMessage(msg);
    }

    @Override
    public void uploadAvatarSuccess() {
        toLogin();
    }

    private void toLogin() {
        try {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("username", mUser.getUsername());
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uploadAvatartFail(String msg) {
        ToastTool.shotMessage("头像设置失败:"+msg);
        toLogin();
    }

    @Override
    public void showUpdateAvatarDialog() {
    }

    @Override
    public void closeUpdateAvatarDialog() {
        if(mProgressDialog!=null){
            mProgressDialog.dismiss();
        }
    }
}
