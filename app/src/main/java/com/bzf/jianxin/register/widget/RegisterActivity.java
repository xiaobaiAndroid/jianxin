package com.bzf.jianxin.register.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bzf.jianxin.R;
import com.bzf.jianxin.base.BaseActivity;
import com.bzf.jianxin.bean.User;
import com.bzf.jianxin.login.widget.LoginActivity;
import com.bzf.jianxin.register.presenter.RegistePresenterImpl;
import com.bzf.jianxin.register.presenter.RegisterPresenter;
import com.bzf.jianxin.register.view.RegisterView;

public class RegisterActivity extends BaseActivity implements RegisterView{

    private Toolbar mToolbar;
    private EditText mEt_username;
    private ImageButton mIb_clearName;
    private View mV_nameLine;
    private EditText mEt_psw;
    private ImageButton mIb_clearPsw;
    private View mV_pswLine;
    private Button mBt_register;
    private RegisterPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mPresenter = new RegistePresenterImpl(this);
        initView();
        initListener();
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEt_psw.setSelection(s.length());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
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

        mBt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPresenter!=null){
                    String userName = mEt_username.getText().toString().trim();
                    String psw = mEt_psw.getText().toString().trim();
                    mPresenter.register(userName,psw);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {//监听ActionBar的返回键
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            onBackPressed();
        }
        return true;
    }

    private void initView() {
        mToolbar = getView(R.id.toolbar);
        mToolbar.setTitle("注册");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEt_username = getView(R.id.et_username);
        mIb_clearName = getView(R.id.ib_clearUserName);
        mV_nameLine = getView(R.id.v_usernameLine);

        mEt_psw = getView(R.id.et_psw);
        mIb_clearPsw = getView(R.id.ib_clearPsw);
        mV_pswLine = getView(R.id.v_pswLine);

        mBt_register = getView(R.id.bt_register);
        mIb_clearName.setVisibility(View.INVISIBLE);
        mIb_clearPsw.setVisibility(View.INVISIBLE);
        setLoginButtonState(false);
    }

    /**
     * 设置登录按钮的状态
     *
     * @param isShow
     */
    private void setLoginButtonState(boolean isShow) {
        mBt_register.setClickable(isShow);
        mBt_register.setSelected(isShow);

    }

    /**
     * 检查用户名，密码是否为null
     */
    private boolean checkUserNamePsw() {
        return TextUtils.isEmpty(mEt_username.getText().toString()) || TextUtils.isEmpty(mEt_psw.getText().toString());
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void closeDialog() {

    }

    @Override
    public void success(User user) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("username",user.getUsername());
        startActivity(intent);
        finish();
    }

    @Override
    public void fail(String e) {

    }
}
