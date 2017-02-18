package com.bzf.jianxin.addcontact.presenter;

import com.bzf.jianxin.addcontact.view.QueryContactView;
import com.bzf.jianxin.base.BaseCallbackListener;
import com.bzf.jianxin.base.BasePresenter;
import com.bzf.jianxin.bean.User;
import com.bzf.jianxin.login.module.UserModelImpl;

import java.util.List;

/**
 * com.bzf.jianxin.addcontact.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class AddContactPresenterImpl extends BasePresenter<QueryContactView,UserModelImpl> implements AddContactPresenter{

    public AddContactPresenterImpl(QueryContactView view){
        super(view,new UserModelImpl());
    }

    @Override
    public void queryUser(String username) {
        view.showQueryContactDialog();
        mModel.queryUser(username, new BaseCallbackListener<List<User>>() {
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
        mModel.addUser(username,reason);
    }
}
