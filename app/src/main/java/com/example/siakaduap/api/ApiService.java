package com.example.siakaduap.api;

import com.example.siakaduap.models.LoginResponse;
import com.example.siakaduap.models.BeritaResponse;
import com.example.siakaduap.models.KrsResponse;
import com.example.siakaduap.models.KhsResponse;
import com.example.siakaduap.models.JadwalResponse;
import com.example.siakaduap.models.AbsensiResponse;
import com.example.siakaduap.models.PembayaranResponse;
import com.example.siakaduap.models.RiwayatResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(@Field("npm") String npm, @Field("password") String password);

    @GET("berita.php")
    Call<BeritaResponse> getBerita();

    @GET("krs.php")
    Call<KrsResponse> getKrs(@Query("npm") String npm);

    @GET("khs.php")
    Call<KhsResponse> getKhs(@Query("npm") String npm);

    @GET("jadwal.php")
    Call<JadwalResponse> getJadwal(@Query("npm") String npm);

    @GET("absensi.php")
    Call<AbsensiResponse> getAbsensi(@Query("npm") String npm);

    @GET("pembayaran.php")
    Call<PembayaranResponse> getPembayaran(@Query("npm") String npm);

    @GET("riwayat.php")
    Call<RiwayatResponse> getRiwayat(@Query("npm") String npm);
    
    @GET("pengumuman.php")
    Call<com.example.siakaduap.models.PengumumanResponse> getPengumuman();

    @FormUrlEncoded
    @POST("absen_action.php")
    Call<com.example.siakaduap.models.LoginResponse> absenAction(@Field("npm") String npm, @Field("kode_mk") String kodeMk, @Field("status") String status);

    @FormUrlEncoded
    @POST("pembayaran_action.php")
    Call<com.example.siakaduap.models.LoginResponse> pembayaranAction(@Field("npm") String npm, @Field("semester") String semester);
    @FormUrlEncoded
    @POST("transkrip.php")
    Call<com.example.siakaduap.models.TranskripResponse> getTranskrip(@Field("npm") String npm);

    @FormUrlEncoded
    @POST("get_mk_tersedia.php")
    Call<com.example.siakaduap.models.MkTersediaResponse> getMkTersedia(@Field("npm") String npm);

    @FormUrlEncoded
    @POST("action_krs.php")
    Call<com.example.siakaduap.models.LoginResponse> actionKrs(@Field("npm") String npm, @Field("action") String action, @Field("kode_mk") String kodeMk);
}
