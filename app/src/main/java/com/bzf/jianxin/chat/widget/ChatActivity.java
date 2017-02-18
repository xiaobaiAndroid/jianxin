package com.bzf.jianxin.chat.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.bzf.jianxin.MyApplication;
import com.bzf.jianxin.R;
import com.bzf.jianxin.base.BaseActivity;
import com.bzf.jianxin.bean.Contact;
import com.bzf.jianxin.chat.presenter.ChatPresenterImpl;
import com.bzf.jianxin.chat.view.ChatView;
import com.bzf.jianxin.commonutils.LogTool;
import com.bzf.jianxin.commonutils.ToastTool;
import com.bzf.jianxin.service.MessageDisposeManger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ChatActivity extends BaseActivity<ChatPresenterImpl> implements ChatView{

    private static final String TAG = ChatActivity.class.getName();

    @BindView(R.id.et_chat_content)
    EditText mEt_content;
    @BindView(R.id.bt_send)
    Button mBt_send;
    @BindView(R.id.listView_chat)
    ListView mLv_chatList;

    private Contact mContact;
    private ChatItemListViewAdapter mChatAdapter;
    private List<ChatItemListViewBean> mChatList = new ArrayList<ChatItemListViewBean>();
    private LocalBroadcastManager mLocalBroadcastManager;

    @Override
    protected void init() {
        mPresenter = new ChatPresenterImpl(this);
        initView();
        initListener();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        mLocalBroadcastManager.registerReceiver(mBroadcasrReceiver, new IntentFilter(MessageDisposeManger.ACTION_MESSAGE));
        initData();
    }

    @Override
    protected void createContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_chat);
    }

    @Override
    protected void onDestroy() {
        MyApplication.getIns().mCurrentChatUserName = null;
        mLocalBroadcastManager.unregisterReceiver(mBroadcasrReceiver);
        super.onDestroy();
    }

    private void initData() {
        Intent intent = getIntent();
        mContact = (Contact) intent.getSerializableExtra("contact");
        if (mContact != null) {
            mToolbar.setTitle(mContact.getContactUsername());
            MyApplication.getIns().mCurrentChatUserName = mContact.getContactUsername();
        } else {
            finish();
        }
        initToolbar(mContact.getContactUsername());
    }

    @Override
    protected void showNewMessageToNotification() {
        MyApplication.getIns().mShowNewMessage = false;
    }

    private void initListener() {
        mBt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mEt_content.getText().toString();
                if (!TextUtils.isEmpty(content) && mContact != null) {
                    mEt_content.setText("");
                    ChatItemListViewBean bean = new ChatItemListViewBean();
                    bean.setType(ChatItemListViewBean.SEND_TYPE);
                    bean.setText(content);
                    mChatList.add(bean);
                    mChatAdapter.notifyDataSetChanged();
                     mPresenter.sendTextMessage(content, mContact.getContactUsername());
                    mLv_chatList.setSelection(mLv_chatList.getCount());
                }
            }
        });
    }

    private void initView() {

        mChatAdapter = new ChatItemListViewAdapter(this, mChatList);
        mLv_chatList.setAdapter(mChatAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_user:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 新消息的广播
     */
    private BroadcastReceiver mBroadcasrReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogTool.i(TAG, "收到新消息");
            try {
                Bundle bundle = intent.getExtras();
                String msgId = bundle.getString("msgId");
                if(!TextUtils.isEmpty(msgId)){
                    mPresenter.getNewMsg(mContact.getContactUsername(),msgId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    public void sendSuccess() {

    }

    @Override
    public void sendFail(String msg) {

    }

    @Override
    public void showSendMsgDialog() {

    }

    @Override
    public void dimissSendMsgDialog() {

    }

    @Override
    public void showSendMsgProgress(int progress) {

    }

    @Override
    public void getNewMsgSuccess(ChatItemListViewBean bean) {
        mChatList.add(bean);
        mChatAdapter.notifyDataSetChanged();
    }

    @Override
    public void getNewMsgFail(String msg) {
        ToastTool.shotMessage(msg);
    }
}
