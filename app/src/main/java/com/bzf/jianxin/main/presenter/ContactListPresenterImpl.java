package com.bzf.jianxin.main.presenter;

import com.bzf.jianxin.base.BaseCallbackListener;
import com.bzf.jianxin.base.BasePresenter;
import com.bzf.jianxin.bean.Contact;
import com.bzf.jianxin.login.module.UserModelImpl;
import com.bzf.jianxin.main.view.ContactListView;

import java.util.List;

/**
 * com.bzf.jianxin.main.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class ContactListPresenterImpl extends BasePresenter implements ContactListPresenter {

    private UserModelImpl mUserModel;

    public ContactListPresenterImpl(){
        super();
        mUserModel = new UserModelImpl();
    }

    @Override
    public void getContactList(final ContactListView view) {
        mUserModel.getContactList(new BaseCallbackListener<List<Contact>>() {
            @Override
            public void success(final List<Contact> contacts) {
                if(mHandler!=null){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.contactListSuccess(contacts);
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
                            view.contactListfail("获取联系人列表失败："+e.getMessage());
                        }
                    });
                }
            }
        });
    }
}
