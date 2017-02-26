package com.bzf.jianxin.register.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.bzf.jianxin.base.BaseCallbackListener;
import com.bzf.jianxin.base.BasePresenter;
import com.bzf.jianxin.bean.User;
import com.bzf.jianxin.commonutils.AppTool;
import com.bzf.jianxin.commonutils.BitmapTool;
import com.bzf.jianxin.commonutils.DateTool;
import com.bzf.jianxin.commonutils.FileTool;
import com.bzf.jianxin.login.module.UserModelImpl;
import com.bzf.jianxin.register.view.RegisterView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import io.reactivex.functions.Consumer;

/**
 * com.bzf.jianxin.register.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class RegistePresenterImpl extends BasePresenter<RegisterView, UserModelImpl> implements RegisterPresenter {

    public File mOutputImageFile;

    public RegistePresenterImpl(RegisterView view) {
        super(view, new UserModelImpl());
    }


    @Override
    public void register(String username, String psw, String nickName) {
        if (username.length() < 5) {
            view.registerFail("账号太短，请输入大于5位数的账号");
        } else if (psw.length() < 6) {
            view.registerFail("密码太短，请输入大于6位数的密码");
        } else {
            view.showRegisterDialog();
            mModel.register(username, psw, nickName, new BaseCallbackListener<User>() {

                @Override
                public void success(final User user) {
                    asyncRequest(user, new Consumer<User>() {
                        @Override
                        public void accept(User user) throws Exception {
                            view.registerSuccess(user);
                        }
                    });
                }

                @Override
                public void fail(final Throwable e) {
                    String errorMsg = "注册失败：" + e.getMessage();
                    asyncRequest(errorMsg, new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            view.closeRegisterDialog();
                            view.registerFail(s);
                        }
                    });
                }
            });
        }
    }

    @Override
    public void saveAvatar(Bitmap photo, int quality, File file) {
        if (file != null) {
            try {
                BitmapTool.saveBitmap(photo, quality, new FileOutputStream(file));
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
                asyncRequest(s, new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        view.closeRegisterDialog();
                        view.uploadAvatarSuccess();
                    }
                });
            }

            @Override
            public void fail(final Throwable e) {
                asyncRequest(e.getMessage(), new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        view.closeRegisterDialog();
                        view.uploadAvatartFail(e.getMessage());
                    }
                });
            }
        });

    }

    @Override
    public void createSaveImageFile(Context context) throws Exception{
        String packageName = AppTool.getPackageName(context);
        String dirName = packageName.substring(packageName.lastIndexOf(".") + 1) + "/avatar";
        File dir = FileTool.createDir(context,dirName);
        String imagName = DateTool.fromateDate(new Date(), DateTool.PATTERN_1) + ".jpg";
        mOutputImageFile = FileTool.createFile(dir.getAbsolutePath(), imagName);
    }

    @Override
    public Intent getPhotoZoomIntent(Uri uri) throws Exception {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        return intent;
    }


}
