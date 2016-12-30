package com.bzf.jianxin.login.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bzf.jianxin.R;
import com.bzf.jianxin.base.BaseActivity;
import com.bzf.jianxin.bean.User;
import com.bzf.jianxin.login.presenter.LoginPresenter;
import com.bzf.jianxin.login.presenter.LoginPresenterImpl;
import com.bzf.jianxin.login.view.LoginView;
import com.bzf.jianxin.main.widget.MainActivity;
import com.bzf.jianxin.register.widget.RegisterActivity;

public class LoginActivity extends BaseActivity implements LoginView{

    private Toolbar mToolbar;
    private TextView mTv_city;
    private EditText mEt_username;
    private ImageButton mIb_clearName;
    private View mV_nameLine;
    private EditText mEt_psw;
    private ImageButton mIb_clearPsw;
    private View mV_pswLine;
    private Button mBt_login;
    private TextView mTv_loginProblem;
    private TextView mTv_otherLogin;

    private LoginPresenter mPresenter;
    private AlertDialog mAlertDialog;
    private TextView mTv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPresenter = new LoginPresenterImpl(this);
        initView();
        initListener();
        initData();
        showKeyboard();
    }

    private void initData() {
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        if(TextUtils.isEmpty(username)){//注册页面跳转过来的
            mEt_username.setText(username);
        }
    }

    private void showKeyboard() {
        InputMethodManager inputManager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(mEt_username, 0);
    }

    @Override
    protected void onDestroy() {
        if(mPresenter!=null){
            mPresenter.detach();
        }
        super.onDestroy();
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
                    mV_nameLine.setBackgroundResource(R.drawable.an2);
                } else {
                    mV_nameLine.setBackgroundResource(R.drawable.an1);
                }
            }
        });

        mEt_psw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                if (isFocus) {
                    mV_pswLine.setBackgroundResource(R.drawable.an2);
                } else {
                    mV_pswLine.setBackgroundResource(R.drawable.an1);
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
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
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
        mToolbar = getView(R.id.toolbar);
        mToolbar.setTitle("登录");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        //显示返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTv_city = getView(R.id.tv_city);
        mEt_username = getView(R.id.et_username);
        mIb_clearName = getView(R.id.ib_clearUserName);
        mV_nameLine = getView(R.id.v_usernameLine);

        mEt_psw = getView(R.id.et_psw);
        mIb_clearPsw = getView(R.id.ib_clearPsw);
        mV_pswLine = getView(R.id.v_pswLine);

        mBt_login = getView(R.id.bt_login);
        mTv_loginProblem = getView(R.id.tv_loginProblem);
        mTv_otherLogin = getView(R.id.tv_otherLogin);
        mTv_register = getView(R.id.tv_register);

        mIb_clearName.setVisibility(View.INVISIBLE);
        mIb_clearPsw.setVisibility(View.INVISIBLE);
        setLoginButtonState(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {//监听ActionBar的返回键
            super.onBackPressed();
        }
        return true;
    }

    @Override
    public void showDialog() {
        if(mAlertDialog==null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("登录中。。");
            builder.setCancelable(false);
            mAlertDialog = builder.create();
        }
        mAlertDialog.show();
    }

    @Override
    public void closeDialog() {
        mAlertDialog.dismiss();
    }

    @Override
    public void success(User user) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);
        finish();
    }

    @Override
    public void fail(String message) {

    }
}
