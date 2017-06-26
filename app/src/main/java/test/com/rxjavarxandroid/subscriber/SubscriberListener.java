package test.com.rxjavarxandroid.subscriber;


import test.com.rxjavarxandroid.exception.ApiException;

/**
 * 数据返回处理类
 * Created by xiaomo on 2016/8/9.
 */
public interface SubscriberListener<T> {

    /**
     * 处理数据相关的逻辑
     * @param t
     */
    void onNext(T t);
    /**
     * 事件队列异常
     * @param e
     */
    void onError(ApiException e);
    /**
     * 事件队列完结
     */
    void onCompleted();

}
