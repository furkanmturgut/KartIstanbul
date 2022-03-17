package com.kunai.istanbulkart.retrofit;

public class APIUtils {
    public static  String BaseURL = "http://furkanturgut.xyz/";

    public static RetrofitInterface getIstanbulKartInterface(){
        return RetrofitBuilder.getClient(BaseURL).create(RetrofitInterface.class);
    }
}
