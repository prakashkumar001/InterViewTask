package com.list.show;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.list.show.adapter.DataListAdapter;
import com.list.show.network.NetworkUtils;
import com.list.show.pojo.RootObject;
import com.list.show.pojo.User;
import com.list.show.realm.RealmController;
import com.list.show.retrofit.APIInterface;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

RecyclerView dataListView;
DataListAdapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.realm = RealmController.with(this).getRealm();
        //to intialise a view

        intialise();

        if(NetworkUtils.isNetworkAvailable(MainActivity.this)){
            //CONNECTED
            parseDataFromApi();
            mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if(NetworkUtils.isNetworkAvailable(MainActivity.this)) {
                        parseDataFromApi();
                    }else {
                        Toast.makeText(MainActivity.this,"No Internet Connection.. Please enable Internet",Toast.LENGTH_SHORT).show();

                        getDataFromDB();
                    }
                }
            });

        }else {
            //DISCONNECTED
            getDataFromDB();
            Toast.makeText(MainActivity.this,"No Internet Connection.. Please enable Internet",Toast.LENGTH_SHORT).show();
            mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if(NetworkUtils.isNetworkAvailable(MainActivity.this)) {
                        parseDataFromApi();
                    }else {
                        Toast.makeText(MainActivity.this,"No Internet Connection.. Please enable Internet",Toast.LENGTH_SHORT).show();

                        getDataFromDB();
                    }

                }
            });





        }



    }
    void intialise()
    {
        dataListView=(RecyclerView)findViewById(R.id.dataList);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
    }


    //parsing
    private void parseDataFromApi() {
        //Creating a retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        //creating the api interface
        APIInterface api = retrofit.create(APIInterface.class);

        //now making the call object
        //Here we are using the api method that we created inside the api interface
        Call<List<RootObject>> call = api.getRootList();
        call.enqueue(new Callback<List<RootObject>>() {


            @Override
            public void onResponse(Call<List<RootObject>> call, retrofit2.Response<List<RootObject>> response) {
                RealmController.getInstance().clearAll();
                List<RootObject> rootList=response.body();
                adapter=new DataListAdapter(rootList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                dataListView.setLayoutManager(linearLayoutManager);
                dataListView.setItemAnimator(new DefaultItemAnimator());
                dataListView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));
                dataListView.setAdapter(adapter);
                mSwipeRefreshLayout.setRefreshing(false);
                for (RootObject b : rootList) {
                    // Persist your data easily
                    realm.beginTransaction();
                    realm.copyToRealm(b);
                    realm.commitTransaction();
                }

            }

            @Override
            public void onFailure(Call<List<RootObject>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Not data available",Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);

            }
        });

    }

  void getDataFromDB()
  {
      mSwipeRefreshLayout.setRefreshing(false);

      RealmResults<RootObject > results = realm.where(RootObject.class).findAll();

      adapter=new DataListAdapter(results);
      LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
      dataListView.setLayoutManager(linearLayoutManager);
      dataListView.setItemAnimator(new DefaultItemAnimator());
      dataListView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));
      dataListView.setAdapter(adapter);
  }



}
