package com.wzm.baselib.net;


import com.wzm.baselib.net.netlistener.CallBackLis;
import com.wzm.baselib.utils.LogUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class HttpCall {
    private static String exMsg = "请求数据异常！";

    /**
     * 网络请求统一封装
     *
     * @param observable
     * @param callBackLis 网络请求回调
     * @param flag        区分不同请求
     * @param <T>
     */
    public static <T> void doCall(Observable<ResponseModel<T>> observable, final CallBackLis<T> callBackLis, final String flag, PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {

        if (observable == null || callBackLis == null) {
            throw new IllegalArgumentException("doCall方法前两个参数不能为空");
        }

        //被观察者_生命周期
        Observable<ActivityLifeCycleEvent> compareLifecycleObservable =
                lifecycleSubject.takeUntil(new Predicate<ActivityLifeCycleEvent>() {
                    @Override
                    public boolean test(ActivityLifeCycleEvent activityLifeCycleEvent) {
                        return activityLifeCycleEvent.equals(ActivityLifeCycleEvent.DESTROY);
                    }
                });

        //观察者_网络请求状态
        Observer<ResponseModel<T>> observer = new Observer<ResponseModel<T>>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseModel<T> baseModel) {
                try {
                    if (baseModel != null) {
                        if (baseModel.getCode() == ResponseModel.SUCCESS) {
                            if (baseModel.getData() != null) {
                                callBackLis.onSuccess(flag, baseModel.getData());
                            } else {
                                callBackLis.onFailure(flag, exMsg);
                            }
                        } else {
                            callBackLis.onFailure(flag, baseModel.getMsg());
                        }
                    } else {
                        callBackLis.onFailure(flag, exMsg);
                    }
                } catch (Exception e) {
                    LogUtils.e("doCall",e);
                    callBackLis.onFailure(flag, "解析错误！");
                }finally {
                    callBackLis.onComplete();
                }
            }

            @Override
            public void onError(Throwable e) {
                try {
                    callBackLis.onFailure(flag, e.getMessage());
                }catch (Exception ex){
                    LogUtils.e("doCall",ex);
                    callBackLis.onFailure(flag, exMsg);
                }finally {
                   callBackLis.onComplete();
                }
            }

            @Override
            public void onComplete() {
                LogUtils.e("doCall","onComplete");
            }
        };
        //被观察者订阅观察者，根据生命周期取消订阅，子线程订阅主线程观察
        observable.takeUntil(compareLifecycleObservable).subscribeOn(Schedulers.newThread()).
                unsubscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(observer);
    }

    /**
     * 网络请求统一封装
     *
     * @param observable
     * @param callBackLis 网络请求回调
     * @param flag        区分不同请求
     * @param <T>
     */
    public static <T> void doCallWithoutIntercept(Observable<T> observable, final CallBackLis<T> callBackLis, final String flag) {

        if (observable == null || callBackLis == null) {
            throw new IllegalArgumentException("参数为空");
        }

        //观察者_网络请求状态
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(T t) {
                try {
                    if (t != null) {
                        callBackLis.onSuccess(flag, t);
                    } else {
                        callBackLis.onFailure(flag, exMsg);
                    }
                } catch (Exception e) {
                    LogUtils.e("doCallWithout",e);
                    callBackLis.onFailure(flag, "解析错误！");
                }finally {
                    callBackLis.onComplete();
                }
            }

            @Override
            public void onError(Throwable e) {
                try {
                    callBackLis.onFailure(flag, e.getMessage());
                }catch (Exception ex){
                    LogUtils.e("doCallWithout",ex);
                    callBackLis.onFailure(flag, exMsg);
                }finally {
                    callBackLis.onComplete();
                }
            }

            @Override
            public void onComplete() {
            }
        };
        observable.subscribeOn(Schedulers.newThread()).
                unsubscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(observer);
    }

}
