package com.labela.sinem.myapplication.Controller;

import com.labela.sinem.myapplication.utils.Constants;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by esineka on 24.04.2018.
 */

public class RestApiClient{

    public  Retrofit retrofit = null;


    public  Retrofit getClient() {

//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Response response = chain.proceed(chain.request());
//                        System.out.println("request = " + chain.request().url().toString());
//                        System.out.println("response = " + response);
//                        return response;
//                    }
//                }).build();
//

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public  RestInterfaceController getService(){
        return getClient().create(RestInterfaceController.class);
    }


}
