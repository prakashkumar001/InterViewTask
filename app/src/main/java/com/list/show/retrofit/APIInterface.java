package com.list.show.retrofit;



import com.list.show.pojo.Comments;
import com.list.show.pojo.RootObject;
import com.list.show.pojo.User;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface APIInterface {
    String BASE_URL = "https://api.github.com";

    @GET("/repos/crashlytics/secureudid/issues")
    Call<List<RootObject>> getRootList();
    @GET
    Call<List<Comments>> getRootListComments(@Url String url);


}