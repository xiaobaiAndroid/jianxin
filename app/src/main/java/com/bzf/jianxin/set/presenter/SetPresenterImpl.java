package com.bzf.jianxin.set.presenter;

import com.bzf.jianxin.base.BaseCallbackListener;
import com.bzf.jianxin.base.BasePresenter;
import com.bzf.jianxin.login.module.UserModel;
import com.bzf.jianxin.login.module.UserModelImpl;
import com.bzf.jianxin.set.view.ExitLoginView;

/**
 * com.bzf.jianxin.set.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class SetPresenterImpl extends BasePresenter implements SetPresenter{

    private UserModel mUserModel;

    public SetPresenterImpl() {
        super();
        mUserModel = new UserModelImpl();
    }

    @Override
    public void exitLogin(final ExitLoginView view) {
        mUserModel.exitLogin(new BaseCallbackListener<String>() {
            @Override
            public void success(String s) {
                if(mHandler!=null){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.exitLoginSuccess();
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
                            view.exitLoginFail("退出登录失败"+e.getMessage());
                        }
                    });
                }
            }
        });
    }
}
