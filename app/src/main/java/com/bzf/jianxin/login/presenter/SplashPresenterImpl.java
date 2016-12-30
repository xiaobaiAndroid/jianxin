package com.bzf.jianxin.login.presenter;

import com.bzf.jianxin.base.BaseCallbackListener;
import com.bzf.jianxin.base.BasePresenter;
import com.bzf.jianxin.bean.User;
import com.bzf.jianxin.bean.Users;
import com.bzf.jianxin.login.module.UserModel;
import com.bzf.jianxin.login.module.UserModelImpl;
import com.bzf.jianxin.login.view.LoginView;

/**
 * com.bzf.jianxin.login.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class SplashPresenterImpl extends BasePresenter implements SplashPresenter{
    private UserModel mUserModel;

    public SplashPresenterImpl(){
        super();
        mUserModel = new UserModelImpl();
    }

    @Override
    public Users getCurrentUser() {
        return mUserModel.getCurrentUser();
    }

    @Override
    public void auoLogin(final LoginView view, Users currentUser) {

        mUserModel.login(currentUser.getUsername(), currentUser.getPsw(), new BaseCallbackListener<User>() {
            @Override
            public void success(final User user) {
                if(mHandler!=null){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.loginSucess(user);
                        }
                    });
                }
            }

            @Override
            public void fail(final Throwable e) {
                if(mHandler!=null){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.loginFail(e.getMessage());
                        }
                    });
                }
            }
        });
    }
}
