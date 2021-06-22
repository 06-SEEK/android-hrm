package com.seek.hrm.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class HistoryMeasure {
    @SerializedName( "_id" )
    private String id;
    @SerializedName( "user" )
    private String user;
    @SerializedName( "create_at" )
    private Date time;
    @SerializedName( "result" )
    private int result;
    @SerializedName( "createdAt" )
    private Date createdAt;
    @SerializedName( "updatedAt" )
    private Date updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

//    static public void deleteItemById(List<HistoryMeasure> list, int Id){
//        Iterator itr = list.iterator();
//        while (itr.hasNext())
//        {
//            HistoryMeasure x = (HistoryMeasure)itr.next();
//            if (x.getUser() == Id){
//                itr.remove();
//                return;
//            }
//
//        }
//
//    }
}
