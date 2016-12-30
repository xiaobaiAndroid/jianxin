package com.bzf.jianxin.addcontact.presenter;

import com.bzf.jianxin.addcontact.view.QueryContactView;
import com.bzf.jianxin.base.BaseCallbackListener;
import com.bzf.jianxin.base.BasePresenter;
import com.bzf.jianxin.bean.User;
import com.bzf.jianxin.login.module.UserModel;
import com.bzf.jianxin.login.module.UserModelImpl;

import java.util.List;

/**
 * com.bzf.jianxin.addcontact.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class AddContactPresenterImpl extends BasePresenter implements AddContactPresenter{
    private UserModel mUserModel;

    public AddContactPresenterImpl(){
        mUserModel = new UserModelImpl();
    }

    @Override
    public void queryUser(final QueryContactView view, String username) {
        view.showQueryContactDialog();
        mUserModel.queryUser(username, new BaseCallbackListener<List<User>>() {
            @Override
            public void success(final List<User> userList) {
                if(mHandler!=null){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.closeQueryContactDialog();
                            view.queryContactSuccess(userList);
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
                            view.closeQueryContactDialog();
                            view.queryContactFail(e.getMessage());
                        }
                    });
                }
            }
        });
    }

    @Override
    public void addUser(String username, String reason) {
        mUserModel.addUser(username,reason);
    }
}
