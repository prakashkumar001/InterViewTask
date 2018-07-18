package com.list.show.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.list.show.pojo.Comments;
import com.list.show.pojo.RootObject;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmController {
 
    private static RealmController instance;
    private final Realm realm;
 
    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }
 
    public static RealmController with(Fragment fragment) {
 
        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }
 
    public static RealmController with(Activity activity) {
 
        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }
 
    public static RealmController with(Application application) {
 
        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }
 
    public static RealmController getInstance() {
 
        return instance;
    }
 
    public Realm getRealm() {
 
        return realm;
    }
 
    //Refresh the realm istance
    public void refresh() {
 
        realm.isAutoRefresh();
    }
 
    //clear all objects from Book.class
    public void clearAll() {
 
        realm.beginTransaction();
        realm.delete(RootObject.class);
        realm.commitTransaction();
    }
    public void clearAllComments() {

        realm.beginTransaction();
        realm.delete(Comments.class);
        realm.commitTransaction();
    }
    //find all objects in the Book.class
    public RealmResults<RootObject> getRootObjects() {
 
        return realm.where(RootObject.class).findAll();
    }
 
    //query a single item with the given id
    public RootObject getRootObject(String id) {
 
        return realm.where(RootObject.class).equalTo("id", id).findFirst();
    }
 

 
    //query example
    public RealmResults<RootObject> queryedRootObject() {
 
        return realm.where(RootObject.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();
 
    }
}