package com.onlyedu.newclassesschedule.util;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author Andy
 * @date 2018/11/22 12:55
 */
public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public final static int CONNECT_TIMEOUT =60;
    public final static int READ_TIMEOUT=100;
    public final static int WRITE_TIMEOUT=60;


    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT,TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)
            .build();

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static RestResult post(String url,String json){

        logger.info("HttpUtil.post before, url:{},json:{}",url,json);

        try{

            RequestBody body = RequestBody.create(JSON,json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            RestResult result =  JsonUtil.json2Object(response.body().string(),RestResult.class);

            logger.info("HttpUtil.post after, url:{},json:{},result:{}",url,json,JsonUtil.object2Jon(result));
            return result;
        }catch (Exception e){

            logger.error("HttpUtil.post 报错，url:{},json:{},报错信息：{}",url,json,e.fillInStackTrace());
            return RestResult.fail(e.getMessage());
        }
    }

    public static RestResult get(String url){

        logger.info("HttpUtil.get before, url:{}",url);

        try{

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            RestResult result =  JsonUtil.json2Object(response.body().string(),RestResult.class);

            logger.info("HttpUtil.get after, url:{},result:{}",url,JsonUtil.object2Jon(result));
            return result;
        }catch (Exception e){

            logger.error("HttpUtil.get 报错，url:{},报错信息：{}",url,e.fillInStackTrace());
            return RestResult.fail(e.getMessage());
        }
    }
}
