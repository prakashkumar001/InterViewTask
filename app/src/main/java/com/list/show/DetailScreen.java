package com.list.show;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.list.show.adapter.DataListAdapter;

import com.list.show.network.NetworkUtils;
import com.list.show.pojo.Comments;
import com.list.show.pojo.RootObject;
import com.list.show.realm.RealmController;
import com.list.show.retrofit.APIInterface;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailScreen extends AppCompatActivity {
    RootObject data;
    TextView name,comments;
    ImageView avatar;
    ImageLoader loader;
    Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_screen);
        this.realm = RealmController.with(this).getRealm();

        intialise();
        loader=ImageLoader.getInstance();
        Intent i=getIntent();
        data= DataListAdapter.object;
        if(NetworkUtils.isNetworkAvailable(DetailScreen.this)){
            name.setText(data.getUser().getLogin());
            comments.setText(data.getBody());
            loader.displayImage(data.getUser().getAvatarUrl(),avatar);
            parseCommentFromApi();
        }else
        {
            getDatafromDB();
        }



    }

    void intialise()
    {
        name=(TextView)findViewById(R.id.username);
        comments=(TextView)findViewById(R.id.description);
        avatar=(ImageView)findViewById(R.id.avatar);
    }
    //parsing
    private void parseCommentFromApi() {
        //Creating a retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        //creating the api interface
        APIInterface api = retrofit.create(APIInterface.class);

        String [] urls=data.getCommentsUrl().split("/");

        for(int i=0;i<urls.length;i++)
        {
            String url=urls[i];
        }
        //now making the call object
        //Here we are using the api method that we created inside the api interface
      ;
        Call<List<Comments>> call = api.getRootListComments(data.getCommentsUrl());
        call.enqueue(new Callback<List<Comments>>() {


            @Override
            public void onResponse(Call<List<Comments>> call, retrofit2.Response<List<Comments>> response) {
                RealmController.getInstance().clearAllComments();
                List<Comments> rootList=response.body();


                // RealmResults<RootObject > results = realm.where(RootObject.class).findAll();

                for (Comments b : rootList) {
                    // Persist your data easily
                    realm.beginTransaction();
                    realm.copyToRealm(b);
                    realm.commitTransaction();
                }
                name.setText(data.getUser().getLogin());
                String dataitem="";
                for(int i=0;i<rootList.size();i++)
                {
                    dataitem=".";
                     dataitem=dataitem+rootList.get(i).getBody()+"\n";


                }
                comments.setText(dataitem);
                loader.displayImage(data.getUser().getAvatarUrl(),avatar);


            }

            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {
                Toast.makeText(DetailScreen.this,"Not data available",Toast.LENGTH_SHORT).show();

            }
        });

    }

    void getDatafromDB()
    {

        name.setText(data.getUser().getLogin());
        comments.setText(data.getBody());
        loader.displayImage(data.getUser().getAvatarUrl(),avatar);

    }
}
