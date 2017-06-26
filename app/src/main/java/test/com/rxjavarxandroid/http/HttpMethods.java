package test.com.rxjavarxandroid.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import test.com.rxjavarxandroid.api.ApiService;

/**
 * retrofit网络请求初始化类
 * Created by xiaomo on 2016/8/3.
 */
public class HttpMethods {


    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private ApiService apiService;

    /**
     * 初始化Retrofit
     */
    private HttpMethods(){

        retrofit=new Retrofit.Builder()
                .client(OKHttpFactory.INSTANCE.getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(ApiService.BASE_URL)
                .build();
        apiService=retrofit.create(ApiService.class);
    }

    private static class SingletonHodler{
        private static final HttpMethods INSTANCE=new HttpMethods();
    }

    public static HttpMethods getInstance(){
        return SingletonHodler.INSTANCE;
    }

    public ApiService getApiService(){
        return apiService;
    }

}
