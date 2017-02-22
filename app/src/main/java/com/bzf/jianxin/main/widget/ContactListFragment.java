package com.bzf.jianxin.main.widget;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bzf.jianxin.R;
import com.bzf.jianxin.base.BaseFragment;
import com.bzf.jianxin.bean.Contact;
import com.bzf.jianxin.chat.widget.ChatActivity;
import com.bzf.jianxin.commonutils.ToastTool;
import com.bzf.jianxin.main.presenter.ContactListPresenterImpl;
import com.bzf.jianxin.main.view.ContactListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;

/**
 * 通讯录
 * com.bzf.jianxin.main.widget
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class ContactListFragment extends BaseFragment<ContactListPresenterImpl> implements ContactListView {

    @BindView(R.id.il_contacts)
    IndexableLayout mIl_contacts;


    private ContactsAdapter mContactsAdapter;
    private List<Contact> mList = new ArrayList<Contact>();


    @Override
    protected View createView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_contactlist, null);
    }

    @Override
    protected void init() {
        mPresenter = new ContactListPresenterImpl(this);
        mIl_contacts.setOverlayStyle_MaterialDesign(getResources().getColor(R.color.colorPrimary));
        mIl_contacts.setCompareMode(IndexableLayout.MODE_FAST);//设置排序规则
        setContacts();
        intiListener();
        getContactList();
    }

    private void intiListener() {
        mContactsAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<Contact>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, Contact entity) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("contact", entity);
                startActivity(intent);
            }
        });

    }

    private void setContacts() {
        if (mContactsAdapter == null) {
            mContactsAdapter = new ContactsAdapter(getContext());
            mIl_contacts.setAdapter(mContactsAdapter);
        } else {
            mContactsAdapter.setDatas(mList);
        }
    }

    public void getContactList() {
        if (mPresenter != null) {
            mPresenter.getContactList();
        }
    }

    @Override
    public void contactListSuccess(List<Contact> contacts) {
        if (contacts == null || contacts.size() <= 0) {
            return;
        }
        mList.clear();
        mList.addAll(contacts);
        setContacts();
    }

    @Override
    public void contactListfail(String msg) {
        ToastTool.shotMessage(msg);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

}
