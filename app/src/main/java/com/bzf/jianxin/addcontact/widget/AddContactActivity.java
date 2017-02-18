package com.bzf.jianxin.addcontact.widget;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;

import com.bzf.jianxin.R;
import com.bzf.jianxin.addcontact.presenter.AddContactPresenterImpl;
import com.bzf.jianxin.addcontact.view.QueryContactView;
import com.bzf.jianxin.base.BaseActivity;
import com.bzf.jianxin.bean.User;
import com.bzf.jianxin.commonutils.ToastTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddContactActivity extends BaseActivity<AddContactPresenterImpl> implements QueryContactView{

    @BindView(R.id.mSv_searchContact)
    SearchView mSvSearchContact;
    @BindView(R.id.mRv_searchResult)
    RecyclerView mRvSearchResult;

    private QueryUserAdapter mAdapter;
    private List<User> mList;

    @Override
    protected void init() {
        mPresenter = new AddContactPresenterImpl(this);
        initToolbar("添加朋友");
        initSearchView();
        initData();
    }

    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new QueryUserAdapter(mList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvSearchResult.setLayoutManager(linearLayoutManager);
        mRvSearchResult.setHasFixedSize(true);
        mRvSearchResult.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new QueryUserAdapter.OnItemClickListener() {
            @Override
            public void OnItemListener(View view, int position) {
                mPresenter.addUser(mList.get(position).getUsername(),"想加你为好友");
            }
        });
    }

    private void initSearchView() {
        mSvSearchContact.setIconifiedByDefault(false);
        mSvSearchContact.setSubmitButtonEnabled(true);
        mSvSearchContact.setQueryHint("请输入要查询的用户名");
        mSvSearchContact.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                queryContact(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    /**
     * 查询联系人
     * @param username
     */
    private void queryContact(String username) {
        if(TextUtils.isEmpty(username)){
            return;
        }
        mPresenter.queryUser(username);
    }

    @Override
    protected void createContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_contact);
    }

    @Override
    public void queryContactSuccess(List<User> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void queryContactFail(String msg) {
        ToastTool.shotMessage(msg);
    }

    @Override
    public void showQueryContactDialog() {

    }

    @Override
    public void closeQueryContactDialog() {

    }
}
