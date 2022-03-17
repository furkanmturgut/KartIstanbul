package com.kunai.istanbulkart.retrofit;

import com.kunai.istanbulkart.model.BoolModel;
import com.kunai.istanbulkart.model.PhoneLoginModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @FormUrlEncoded
    @POST("filmcarki/uyeol.php")
    Call<BoolModel> uyeol(@Field("tc") String tc, @Field("isim") String isim, @Field("soyad") String soyad, @Field("dogum_tarihi") String dogum, @Field("mail_adres")String mail, @Field("sifre")String sifre, @Field("telefon")String telefon);

    @FormUrlEncoded
    @POST("filmcarki/girisyap.php")
    Call<BoolModel> girisYap(@Field("telefon")String telefon, @Field("sifre")String sifre);

    @FormUrlEncoded
    @POST("filmcarki/odemeal.php")
    Call<BoolModel> odemeAl(@Field("kart_numara") String kartNo,@Field("tutar") int tutar,@Field("ccv") int ccv,@Field("skt") String skt);


}
