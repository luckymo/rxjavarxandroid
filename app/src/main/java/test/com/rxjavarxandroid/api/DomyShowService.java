package test.com.rxjavarxandroid.api;

import android.content.Context;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import test.com.rxjavarxandroid.entity.ApiResult;
import test.com.rxjavarxandroid.entity.ChannleEntity;
import test.com.rxjavarxandroid.exception.ApiException;
import test.com.rxjavarxandroid.http.HttpMethods;
import test.com.rxjavarxandroid.subscriber.SubscriberListener;
import test.com.rxjavarxandroid.subscriber.TransactionSubscriber;


/**
 * 网络请求计息类
 * Created by xiaomo on 2016/8/12.
 */
public class DomyShowService {

    /**
     * @param SubscriberListener
     * @param context
     */
    public void getChannleList(SubscriberListener SubscriberListener, Context context, String url,LinkedHashMap<String, String> map){
        if(map==null) {
         map=new LinkedHashMap<>();
        }
        Observable observable =    HttpMethods.getInstance().getApiService().getChannleList(url,map);
        toSubscribe(observable, new TransactionSubscriber<ApiResult<List<ChannleEntity>>>(SubscriberListener,context));
    }

    /**
     * @param SubscriberListener
     * @param context
     */
    public void post(SubscriberListener SubscriberListener, Context context, String url, HashMap<String, String> map){
        if(map==null) {
            map=new LinkedHashMap<>();
        }
        Observable observable =    HttpMethods.getInstance().getApiService().post(url,map);
        toSubscribe(observable, new TransactionSubscriber<ApiResult>(SubscriberListener,context));
    }

    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //设置重连请求次数
                .retry(new Func2<Integer, Throwable, Boolean>() {
                    @Override
                    public Boolean call(Integer integer, Throwable throwable) {
                        if (throwable instanceof SocketTimeoutException && integer < 2)
                            return true;
                        else
                            return false;
                    }
                })
                .onErrorResumeNext(new HttpResponseFunc<T>())//网络&解析错误转换为友好提示
                .subscribe(s);
    }

    /**
     * 服务器返回的错误码(400，504等)转换为用户可读的文字
     * @param <T>
     */
    private static class HttpResponseFunc<T> implements Func1<Throwable, Observable<T>> {
        @Override public Observable<T> call(Throwable t) {
            return Observable.error(ApiException.handleException(t));
        }
    }

}
