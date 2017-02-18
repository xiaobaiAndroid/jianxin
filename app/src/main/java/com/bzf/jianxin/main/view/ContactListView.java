package com.bzf.jianxin.main.view;

import com.bzf.jianxin.base.BaseView;
import com.bzf.jianxin.bean.Contact;

import java.util.List;

/**
 * 获取联系人列表的View
 * com.bzf.jianxin.main.view
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public interface ContactListView extends BaseView{
    void contactListSuccess(List<Contact> contacts);
    void contactListfail(String msg);

}
