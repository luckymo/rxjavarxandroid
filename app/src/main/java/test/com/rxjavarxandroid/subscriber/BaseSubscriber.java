/*
 * Copyright (C) 2017 zhouyou(478319399@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package test.com.rxjavarxandroid.subscriber;

import android.content.Context;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import test.com.rxjavarxandroid.exception.ApiException;
import test.com.rxjavarxandroid.utils.HttpLog;

import static test.com.rxjavarxandroid.utils.Utils.isNetworkAvailable;


/**
 * <p>描述：订阅的基类</p>
 * 1.可以防止内存泄露。<br>
 * 2.在onStart()没有网络时直接onCompleted();<br>
 * 3.统一处理了异常<br>
 * 作者：lhj
 */
public abstract class BaseSubscriber<T> implements Observer<T> {
    public WeakReference<Context> contextWeakReference;

    public BaseSubscriber() {
    }

    public BaseSubscriber(Context context) {
        if (context != null)
            contextWeakReference = new WeakReference<Context>(context);
    }


    @Override
    public final void onError(java.lang.Throwable e) {
        HttpLog.e("-->http is err");
        if (e instanceof ApiException) {
            HttpLog.e("--> e instanceof ApiException" + e.getMessage());
            onError((ApiException) e);
        } else {
            HttpLog.e("e !instanceof ApiException" + e.getMessage());
            onError(ApiException.handleException(e));
        }
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        HttpLog.e("-->http is start");
        if (contextWeakReference != null && !isNetworkAvailable(contextWeakReference.get())) {
            //Toast.makeText(context, "无网络，读取缓存数据", Toast.LENGTH_SHORT).show();
            onComplete();
            return;
        }
    }


    @Override
    public void onComplete() {
        HttpLog.e("-->http is Complete");
    }


    public abstract void onError(ApiException e);

}
