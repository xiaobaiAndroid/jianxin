package com.bzf.jianxin.set.presenter;

import com.bzf.jianxin.base.BasePresenter;
import com.bzf.jianxin.login.module.UserModelImpl;
import com.bzf.jianxin.set.view.ExitLoginView;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observers.DisposableObserver;

/**
 * com.bzf.jianxin.set.presenter
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class SetPresenterImpl extends BasePresenter<ExitLoginView, UserModelImpl> implements SetPresenter {

    public SetPresenterImpl(ExitLoginView view) {
        super(view, new UserModelImpl());
    }

    @Override
    public void exitLogin() {
        asyncRequest(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> oe) throws Exception {
                try {
                    mModel.exitLogin();
                    oe.onNext("退出登录成功");
                } catch (Exception e) {
                    e.printStackTrace();
                    oe.onError(e);
                }
            }
        },new DisposableObserver<String>() {
            @Override
            public void onNext(String value) {
                view.exitLoginSuccess();
            }

            @Override
            public void onError(Throwable e) {
                view.exitLoginFail("退出登录失败" + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
