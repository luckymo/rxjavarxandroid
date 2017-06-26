package test.com.rxjavarxandroid.http;

import android.util.Log;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import test.com.rxjavarxandroid.comman.RxjavaRetrofitApplication;

import static test.com.rxjavarxandroid.utils.Utils.isNetworkAvailable;

/**
 * 拦截器
 * 有网络时,缓存时间1分钟
 * 无网络时,强制读取缓存,缓存保存时长为一个月
 * 缓存满了会按照最近最少使用（ LRU Policy ）的策略被移除。
 */
public class OnOffLineCachedInterceptor implements Interceptor {
  private static final int MAX_AGE = 60;
  private static final String TAG="OnOffLineCachedInterceptor";

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();

    if (isNetworkAvailable(RxjavaRetrofitApplication.getInstance())) {
      Response response = chain.proceed(request);
      Log.e("yjbo-cache", "在线请求-在线缓存" );
      return response.newBuilder()
              .removeHeader("Pragma")
              .removeHeader("Cache-Control")
              .header("Cache-Control", "public, max-age=" + MAX_AGE)
              .build();
    } else {
      Log.e("yjbo-cache", "离线时缓存时间设置");
      request = request.newBuilder()
              .cacheControl(FORCE_CACHE1)//此处设置了一个月---修改了系统方法
              .build();

      Response response = chain.proceed(request);
      //下面注释的部分设置也没有效果，因为在上面已经设置了
      return response.newBuilder()
              .build();
    }
  }


  //这是设置在多长时间范围内获取缓存里面
  public static final CacheControl FORCE_CACHE1 = new CacheControl.Builder()
          .onlyIfCached()
          .maxStale(4*7*24*60*MAX_AGE, TimeUnit.SECONDS)//这里是一个月，CacheControl.FORCE_CACHE--是int型最大值
          .build();
}
