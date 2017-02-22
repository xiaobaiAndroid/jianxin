package com.bzf.jianxin.main.presenter;

import com.bzf.jianxin.base.BaseCallbackListener;
import com.bzf.jianxin.base.BasePresenter;
import com.bzf.jianxin.bean.Contact;
import com.bzf.jianxin.login.module.UserModelImpl;
import com.bzf.jianxin.main.view.ContactListView;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * com.bzf.jianxin.main.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class ContactListPresenterImpl extends BasePresenter<ContactListView,UserModelImpl> implements ContactListPresenter {

    public ContactListPresenterImpl(ContactListView view){
        super(view,new UserModelImpl());
    }

    @Override
    public void getContactList() {
        mModel.getContactList(new BaseCallbackListener<List<Contact>>() {
            @Override
            public void success(final List<Contact> contacts) {
                asyncRequest(contacts, new Consumer<List<Contact>>() {
                    @Override
                    public void accept(List<Contact> contacts) throws Exception {
                        view.contactListSuccess(contacts);
                    }
                });
            }

            @Override
            public void fail(final Throwable e) {
                String errorMsg = "get ContacctList fail "+e.getMessage();
                asyncRequest(errorMsg, new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        view.contactListfail(s);
                    }
                });
            }
        });
    }
}
