package com.example.absensikaryawan.api;

import com.example.absensikaryawan.model.absen.Absen;
import com.example.absensikaryawan.model.login.Login;
import com.example.absensikaryawan.model.register.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login.php")
    Call<Login> loginResponse(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<Register> registerResponse(
            @Field("username") String username,
            @Field("password") String password,
            @Field("name") String name,
            @Field("nip") String nip
    );

    @FormUrlEncoded
    @POST("absen.php")
    Call<Absen> absenResponse(
            @Field("nip") String nip,
            @Field("name") String name,
            @Field("keterangan") String keterangan,
            @Field("waktu") String waktu,
            @Field("tanggal") String tanggal,
            @Field("alamat_ip") String alamat_ip
    );
}
