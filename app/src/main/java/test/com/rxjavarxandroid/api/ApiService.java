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

package test.com.rxjavarxandroid.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;
import test.com.rxjavarxandroid.entity.ApiResult;
import test.com.rxjavarxandroid.entity.ChannleEntity;

/**
 * <p>描述：通用的的api接口</p>
 * <p>
 * 1.加入基础API，减少Api冗余<br>
 * 2.支持多种方式访问网络（get,put,post,delete），包含了常用的情况<br>
 * 3.传统的Retrofit用法，服务器每增加一个接口，就要对应一个api，非常繁琐<br>
 * 4.最初是想采用<T> Observable<ApiResult<T>> executePost()这样的方式获取需要的对象，看似比较通用，无奈retrofit不支持二次泛型，真是哭晕啊！！
 * 只能返回比较顶级的ResponseBody。<br>
 * 5.如果返回ResponseBody在返回的结果中去获取T,又会报错，这是因为在运行过程中,通过泛型传入的类型T丢失了,所以无法转换,这叫做泛型擦除。
 * 《泛型擦除》不知道的童鞋自己百度哦！！<br>
 * </p>
 * <p>
 * 注意事项：<br>
 * 1.使用@url,而不是@Path注解,后者放到方法体上,会强制先urlencode,然后与baseurl拼接,请求无法成功<br>
 * 2..注意事项： map不能为null,否则该请求不会执行,但可以size为空.<br>
 * </p>
 * 作者：lhj
 */
public interface ApiService {

    //最初我是想采用<T> Observable<ApiResult<T>> executePost()这样的方式获取需要的对象，看似比较通用，无奈retrofit不支持二次泛型.
    //《无奈retrofit不支持二次泛型》
   /* @POST()
    @FormUrlEncoded
    <T> Observable<ApiResult<T>> executePost(@Url() String url, @FieldMap Map<String, String> maps);
    */
    String BASE_URL="http://api.newsapp.cnr.cn/news/";

    @POST()
    @FormUrlEncoded
    Observable<ApiResult> post(@Url() String url, @FieldMap Map<String, String> maps);


    @GET()
    Observable<ApiResult<List<ChannleEntity>>> getChannleList(@Url String url, @QueryMap Map<String, String> maps);

}
