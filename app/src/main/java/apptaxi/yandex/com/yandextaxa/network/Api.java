package apptaxi.yandex.com.yandextaxa.network;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
    // reg.php
    // to
    @POST("/ytaxi/gps.php")
    @FormUrlEncoded
    Call<ResponseBody> setGPS(@FieldMap Map<String, String> map);

    @POST("/ytaxi/to.php")
    @FormUrlEncoded
    Call<ResponseBody> setTO(@FieldMap Map<String, String> map);

    @POST("/ytaxi/reg.php")
    @FormUrlEncoded
    Call<ResponseBody> setREG(@FieldMap Map<String, String> map);

}
