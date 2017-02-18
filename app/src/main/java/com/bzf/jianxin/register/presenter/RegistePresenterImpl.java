package com.bzf.jianxin.register.presenter;

import android.graphics.Bitmap;

import com.bzf.jianxin.base.BaseCallbackListener;
import com.bzf.jianxin.base.BasePresenter;
import com.bzf.jianxin.bean.User;
import com.bzf.jianxin.commonutils.BitmapTool;
import com.bzf.jianxin.commonutils.FileTool;
import com.bzf.jianxin.login.module.UserModelImpl;
import com.bzf.jianxin.register.view.RegisterView;

import java.io.File;
import java.io.FileOutputStream;

/**
 * com.bzf.jianxin.register.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class RegistePresenterImpl extends BasePresenter<RegisterView,UserModelImpl> implements RegisterPresenter{

    public RegistePresenterImpl(RegisterView view){
        super(view,new UserModelImpl());
    }


    @Override
    public void register(final RegisterView view, String username, String psw, String nickName) {
        if(username.length()<5){
            view.registerFail("账号太短，请输入大于5位数的账号");
        }else if(psw.length()<6){
            view.registerFail("密码太短，请输入大于6位数的密码");
        }else{
            view.showRegisterDialog();
            mModel.register(username, psw,nickName,new BaseCallbackListener<User>(){

                @Override
                public void success(final User user) {
                    if(mHandler!=null){
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                view.registerSuccess(user);
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
                                view.closeRegisterDialog();
                                view.registerFail("注册失败："+e.getMessage());
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public void saveAvatar(Bitmap photo, int quality, File file) {
        if(file!=null){
            try {
                file = FileTool.createFile(file.getParent(), file.getName());
                BitmapTool.saveBitmap(photo,quality, new FileOutputStream(file));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void uploadAvatar(File file, String username) {
        mModel.updateUserAvatar(file, username, new BaseCallbackListener<String>() {
            @Override
            public void success(String s) {
                if(mHandler!=null){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.uploadAvatarSuccess();
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
                            view.uploadAvatartFail(e.getMessage());
                        }
                    });
                }
            }
        });

    }

}
