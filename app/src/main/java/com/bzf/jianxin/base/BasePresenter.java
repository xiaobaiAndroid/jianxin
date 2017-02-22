package com.bzf.jianxin.base;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Presenter基类
 * com.bzf.jianxin.base
 * Author: baizhengfu
 * Email：709889312@qq.com
 */
public class BasePresenter<T extends BaseView,V extends BaseModel> {


    /**
     * 具体的View类型
     */
    protected T view;

    /**
     * Model类型
     */
    protected V mModel;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public BasePresenter(T view,V model){
        this.view = view;
        mModel = model;
    }

    /**
     * 在Activity的destory()中调用此方法，分离Activity的引用，防止内存泄漏
     */
    public void detach(){
        view = null;
        mDisposable.clear();
    };

    /**
     * 异步请求，主线程处理返回结果
     * @param observableOnSubscribe 异步要处理的逻辑对象，异步要实现的逻辑在此方法中实现
     * @param disposableObserver  主线程要操作的对象,主线程要实现的逻辑在此方法中实现
     */
    protected <E> void asyncRequest(ObservableOnSubscribe<E> observableOnSubscribe, DisposableObserver<E> disposableObserver){
        mDisposable.add(Observable
                .create(observableOnSubscribe)
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(disposableObserver));
    }

    /**
     * 异步请求，主线程处理返回结果
     * @param msg 要处理的消息
     * @param consumer    主线程操作对象，在主线程要操作的逻辑在这个对象中执行
     */
    protected <E> void asyncRequest(E msg, Consumer<E> consumer){
        mDisposable.add(Observable
                .just(msg)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer));
    }

}
