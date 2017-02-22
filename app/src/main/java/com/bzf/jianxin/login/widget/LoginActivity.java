package com.bzf.jianxin.login.widget;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bzf.jianxin.MyApplication;
import com.bzf.jianxin.R;
import com.bzf.jianxin.base.BaseActivity;
import com.bzf.jianxin.bean.User;
import com.bzf.jianxin.commonutils.LogTool;
import com.bzf.jianxin.commonutils.ToastTool;
import com.bzf.jianxin.login.presenter.LoginPresenterImpl;
import com.bzf.jianxin.login.view.LoginView;
import com.bzf.jianxin.main.widget.MainActivity;
import com.bzf.jianxin.register.widget.RegisterActivity;

import butterknife.BindView;

public class LoginActivity extends BaseActivity<LoginPresenterImpl> implements LoginView {

    @BindView(R.id.tv_city)
     TextView mTv_city;
    @BindView(R.id.et_username)
     EditText mEt_username;
    @BindView(R.id.ib_clearUserName)
     ImageButton mIb_clearName;
    @BindView(R.id.v_usernameLine)
     View mV_nameLine;
    @BindView(R.id.et_psw)
     EditText mEt_psw;
    @BindView(R.id.ib_clearPsw)
     ImageButton mIb_clearPsw;
    @BindView(R.id.v_pswLine)
     View mV_pswLine;
    @BindView(R.id.bt_login)
     Button mBt_login;
    @BindView(R.id.tv_loginProblem)
     TextView mTv_loginProblem;
    @BindView(R.id.tv_otherLogin)
     TextView mTv_otherLogin;
    @BindView(R.id.tv_register)
     TextView mTv_register;

    private ProgressDialog mProgressDialog;

    @Override
    protected void init() {
        mPresenter = new LoginPresenterImpl(this);
        initView();
        initListener();
        initData();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogTool.i("bzf",LoginActivity.class.getName()+"-----onDestroy（）");
    }

    @Override
    protected void createContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
    }

    private void initData() {
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        if(!TextUtils.isEmpty(username)){//注册页面跳转过来的
            mEt_username.setText(username);
        }
    }

    private void initListener() {
        mTv_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

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

        mEt_psw.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mEt_psw.setSelection(charSequence.length());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable.toString())) {
                    mIb_clearPsw.setVisibility(View.INVISIBLE);
                } else {
                    mIb_clearPsw.setVisibility(View.VISIBLE);
                }
                setLoginButtonState(!checkUserNamePsw());
            }
        });

        mIb_clearName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEt_username.setText("");
            }
        });

        mIb_clearPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEt_psw.setText("");
            }
        });

        mBt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPresenter!=null){
                    String userName = mEt_username.getText().toString().trim();
                    String psw = mEt_psw.getText().toString().trim();
                    mPresenter.login(userName,psw);
                }
            }
        });

        mTv_loginProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mTv_otherLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mTv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOtherActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }


    /**
     * 设置登录按钮的状态
     *
     * @param isShow
     */
    private void setLoginButtonState(boolean isShow) {
        mBt_login.setClickable(isShow);
        mBt_login.setSelected(isShow);

    }

    /**
     * 检查用户名，密码是否为null
     */
    private boolean checkUserNamePsw() {
        return TextUtils.isEmpty(mEt_username.getText().toString()) || TextUtils.isEmpty(mEt_psw.getText().toString());
    }

    private void initView() {
       initToolbar("登录");
        mIb_clearName.setVisibility(View.INVISIBLE);
        mIb_clearPsw.setVisibility(View.INVISIBLE);
        setLoginButtonState(false);
    }


    @Override
    public void showLoginDialog() {
        if(mProgressDialog==null){
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("正在登陆...");
        }
        mProgressDialog.show();
    }

    @Override
    public void closeLoginDialog() {
        if(mProgressDialog!=null){
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void loginSucess(User user) {
        MyApplication.getIns().initCurrentUser();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user",user);
        startOtherActivity(intent);
    }

    @Override
    public void loginFail(String message) {
        if(!TextUtils.isEmpty(message)){
            ToastTool.shotMessage(message);
        }
    }
}
