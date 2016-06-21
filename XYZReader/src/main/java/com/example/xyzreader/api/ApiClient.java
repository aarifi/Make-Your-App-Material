package com.example.xyzreader.api;

import android.content.Context;

import com.example.xyzreader.utils.Constants;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.client.Request;
import retrofit.client.Response;

/**
 * Created by AdonisArifi on 12.4.2016 - 2016 . xyzreader
 */
public class ApiClient implements RequestInterceptor, Client {
    private static final String LOG = ApiClient.class.getSimpleName();
    private static ApiClient apiClientInstance;
    private static XyzReaderApi xyzReaderApiInterfaceMethod;
    public String bookId = "";

    public static ApiClient getApiClientInstance(Context context) {
        if (apiClientInstance == null) {
            apiClientInstance = new ApiClient();
        }
        return apiClientInstance;
    }

    public XyzReaderApi getBooksApiInterfaceMethod() {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);
        if (xyzReaderApiInterfaceMethod == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setRequestInterceptor(this)
                    .setEndpoint(Constants.BASE_URL_)
                    .setErrorHandler(ErrorHandler.DEFAULT)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setClient(new OkClient(okHttpClient))
                    .build();
            xyzReaderApiInterfaceMethod = restAdapter.create(XyzReaderApi.class);
        }

        return xyzReaderApiInterfaceMethod;
    }

    /*public List<ListResponsData> getData() {
        List<ListResponsData> listResponsData = null;
        try {
            listResponsData = getBooksApiInterfaceMethod().getData();
        } catch (Exception e) {
            e.getMessage();
        }

        return listResponsData;
    }*/


    @Override
    public Response execute(Request request) throws IOException {
        return null;
    }

    @Override
    public void intercept(RequestFacade request) {

    }
}
