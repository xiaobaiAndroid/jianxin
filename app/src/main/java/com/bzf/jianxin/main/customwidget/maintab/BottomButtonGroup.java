package com.bzf.jianxin.main.customwidget.maintab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bzf.jianxin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页的底部按钮控制
 * @author bai
 *
 */
public class BottomButtonGroup extends LinearLayout{
	
	private Context mContext;
	private RelativeLayout vWeiXinRoot;
	private GradientIconView vIconWX;
	private GradientTextView vTextWX;
	private TextView vUnreadMsgNumber;
	private RelativeLayout vContactRoot;
	private GradientIconView vIconContacts;
	private GradientTextView vTextContacts;
	private TextView vUnreadAddressNum;
	private RelativeLayout vFindRoot;
	private GradientIconView vtIconFind;
	private GradientTextView vTextFind;
	private RelativeLayout vProfileRoot;
	private GradientIconView vtIconMe;
	private GradientTextView vTextMe;
	private List<GradientIconView> vTabIconList = new ArrayList<GradientIconView>();
	private List<GradientTextView> vTabTextList = new ArrayList<GradientTextView>();
	
	private BottomButtonListener listener;
	
	/**按钮的角标*/
	private int mWeiXinPosition;
	private int mContactsPosition;
	private int mDiscoverPosition;
	private int mProfilePosition;

	private int mLastOffset = 0;

	public BottomButtonGroup(Context context) {
		this(context,null,0);
	}
	
	public BottomButtonGroup(Context context, AttributeSet attrs) {
		this(context, attrs,0);

	}
	public BottomButtonGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		initView();
		initListener();
	}

	private void initListener() {
		vWeiXinRoot.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listener.clickWeiXin();
			}
		});
		
		vContactRoot.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listener.clickContact();
			}
		});
		
		vFindRoot.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listener.clickDiscover();
			}
		});
		
		vProfileRoot.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listener.clickProfile();
			}
		});
		
	}

	public void showCurrentTab(int currentPosition) {
		for(int i=0; i<vTabIconList.size(); i++){
			if(i==currentPosition){
				vTabIconList.get(i).setIconAlpha(0);
				vTabTextList.get(i).setTextAlpha(0);
			}else{
				vTabIconList.get(i).setIconAlpha(1);
				vTabTextList.get(i).setTextAlpha(1);
			}
		}
	}

	private void initView() {
		//布局必须这样加载，这个控件才有效
		View view = LayoutInflater.from(mContext).inflate(R.layout.tab_main, this,true);

		
		vWeiXinRoot = (RelativeLayout) view.findViewById(R.id.re_weixin);		
		vIconWX = (GradientIconView) view.findViewById(R.id.vIconWX);
		vTextWX = (GradientTextView) view.findViewById(R.id.vTextWX);
		vUnreadMsgNumber = (TextView) view.findViewById(R.id.unread_msg_number);
		vTabIconList.add(vIconWX);
		vTabTextList.add(vTextWX);
		mWeiXinPosition = 0;

//		BadgeView badgeView = new BadgeView(mContext, vUnreadMsgNumber);
//		badgeView.setText("12");
//		badgeView.setBadgePosition(BadgeView.POSITION_TOP_LEFT);
//		badgeView.show();

		vContactRoot = (RelativeLayout) view.findViewById(R.id.re_contact_list);		
		vIconContacts = (GradientIconView) view.findViewById(R.id.vtIconContacts);
		vTextContacts = (GradientTextView) view.findViewById(R.id.vTextContacts);
		vUnreadAddressNum = (TextView) view.findViewById(R.id.unread_address_number);
		vTabIconList.add(vIconContacts);
		vTabTextList.add(vTextContacts);
		mContactsPosition = 1;
		
		vFindRoot = (RelativeLayout) view.findViewById(R.id.re_find);		
		vtIconFind = (GradientIconView) view.findViewById(R.id.vtIconFind);
		vTextFind = (GradientTextView) view.findViewById(R.id.vTextFind);
		vTabIconList.add(vtIconFind);
		vTabTextList.add(vTextFind);
		mDiscoverPosition = 2;
		
		vProfileRoot = (RelativeLayout) view.findViewById(R.id.re_profile);		
		vtIconMe = (GradientIconView) view.findViewById(R.id.vtIconMe);
		vTextMe = (GradientTextView) view.findViewById(R.id.vTextMe);
		vTabIconList.add(vtIconMe);
		vTabTextList.add(vTextMe);
		mProfilePosition = 3;
		
		showCurrentTab(mWeiXinPosition);	
	}
	
	/**
	 * 设置微信未读消息的数
	 * @param number
	 */
	public void setUnreadMsgNum(int number){
		if(number>0){
			if(!vUnreadMsgNumber.isShown()){
				vUnreadMsgNumber.setVisibility(View.VISIBLE);
			}
			vUnreadMsgNumber.setText(String.valueOf(number));
		}else{
			vUnreadMsgNumber.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 设置通讯录信息未读数
	 * @param number
	 */
	public void setUnreadContactsNum(int number){
		if(number>0){
			if(!vUnreadAddressNum.isShown()){
				vUnreadAddressNum.setVisibility(View.VISIBLE);
			}
			vUnreadAddressNum.setText(String.valueOf(number));
		}else{
			vUnreadAddressNum.setVisibility(View.GONE);
		}
	}

	public int getmWeiXinPosition() {
		return mWeiXinPosition;
	}

	public int getmContactsPosition() {
		return mContactsPosition;
	}

	public int getmDiscoverPosition() {
		return mDiscoverPosition;
	}

	public int getmProfilePosition() {
		return mProfilePosition;
	}

	public void setClickListener(BottomButtonListener listener){
		this.listener = listener;
	}

	public List<GradientIconView> getvTabIconList() {
		return vTabIconList;
	}

	public List<GradientTextView> getvTabTextList() {
		return vTabTextList;
	}

	private int currentFragment = 0;

	/**
	 * tab实现渐变
	 * @param currentPosition 当前位置
	 * @param nextPosition  要滑动到的位置
	 * @param positionOffset
	 */
	public void showGradientTab(int currentPosition,int nextPosition, float positionOffset) {
			vTabIconList.get(currentPosition).setIconAlpha(1-positionOffset);
			vTabIconList.get(nextPosition).setIconAlpha(positionOffset);
			
			vTabTextList.get(currentPosition).setTextAlpha(1-positionOffset);
			vTabTextList.get(nextPosition).setTextAlpha(positionOffset);
	}


	/**
	 * 判断滑动的方向
	 * @param position
	 * @param positionOffset
	 * @param positionOffsetPixels
	 */
	public void judgeOrientation(int position, float positionOffset, int positionOffsetPixels) {
		if (positionOffsetPixels == 0) {
			mLastOffset = 0;
			showCurrentTab(position);
			return;
		}
		if (mLastOffset > positionOffsetPixels) {//向右滑
			if (position >= 0) {
				showGradientTab(position + 1,position, positionOffset);
			}
		} else if (mLastOffset < positionOffsetPixels) {//向左滑
			if (position < vTabIconList.size() - 1) {
				showGradientTab(position + 1,position, positionOffset);
			}
		}
		mLastOffset = positionOffsetPixels;
	}
}
