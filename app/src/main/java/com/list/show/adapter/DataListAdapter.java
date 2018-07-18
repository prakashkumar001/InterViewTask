package com.list.show.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.list.show.DetailScreen;
import com.list.show.MainActivity;
import com.list.show.R;
import com.list.show.pojo.RootObject;
import com.list.show.pojo.User;
import com.list.show.realm.RealmController;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;


public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.MyViewHolder> {

    private List<RootObject> dataSet;
    ImageLoader imageLoader;
    Context context;
    Realm realm;
    public static RootObject object=null;
    public DataListAdapter(List<RootObject> data) {
        this.dataSet = data;
        imageLoader=ImageLoader.getInstance();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        context=parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {



        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher) // resource or drawable
                .showImageForEmptyUri(R.mipmap.ic_launcher) // resource or drawable
                .showImageOnFail(R.mipmap.ic_launcher) // resource or drawable
                .resetViewBeforeLoading(false)  // default
                .delayBeforeLoading(10)
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .build();


        TextView title = holder.title;
        TextView description = holder.description;
        TextView updated_date = holder.updated_date;
        TextView username = holder.username;
        CircleImageView usericon = holder.usericon;

        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        String strDate = format.format(dataSet.get(listPosition).getUpdatedAt());

        title.setText(dataSet.get(listPosition).getTitle());
        description.setText(dataSet.get(listPosition).getBody());
        updated_date.setText(strDate);
        username.setText(dataSet.get(listPosition).getUser().getLogin());
        imageLoader.displayImage(dataSet.get(listPosition).getUser().getAvatarUrl(),usericon, options);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                object=dataSet.get(listPosition);
                Intent i=new Intent(context, DetailScreen.class);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        CircleImageView usericon;
        TextView username;
        TextView updated_date;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.description = (TextView) itemView.findViewById(R.id.description);
            this.usericon = (CircleImageView) itemView.findViewById(R.id.avatar);
            this.username = (TextView) itemView.findViewById(R.id.username);
            this.updated_date = (TextView) itemView.findViewById(R.id.updated_date);

        }
    }
}