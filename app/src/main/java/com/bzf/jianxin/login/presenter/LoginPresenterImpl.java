package com.bzf.jianxin.login.presenter;

import com.bzf.jianxin.base.BaseCallbackListener;
import com.bzf.jianxin.base.BasePresenter;
import com.bzf.jianxin.bean.User;
import com.bzf.jianxin.login.module.UserModelImpl;
import com.bzf.jianxin.login.view.LoginView;

import io.reactivex.functions.Consumer;


/**
 * com.bzf.jianxin.login.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class LoginPresenterImpl extends BasePresenter<LoginView, UserModelImpl> implements LoginPresenter {


    public LoginPresenterImpl(LoginView view) {
        super(view, new UserModelImpl());
    }

    @Override
    public void login(final String userName, final String psw) {
        view.showLoginDialog();
        mModel.login(userName, psw, new BaseCallbackListener<User>() {
            @Override
            public void success(final User user) {
                asyncRequest(user,new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        if (view != null) {
                            view.closeLoginDialog();
                            view.loginSucess(user);
                        }

                    }
                });

            }

            @Override
            public void fail(Throwable e) {
                asyncRequest("登录失败：" + e.getMessage(),new Consumer<String>() {

                    @Override
                    public void accept(String e) throws Exception {
                        if (view != null) {
                            view.closeLoginDialog();
                            view.loginFail(e);
                        }
                    }
                });

            }

        });
    }
}
