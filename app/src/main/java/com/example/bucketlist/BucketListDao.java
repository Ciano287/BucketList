package com.example.bucketlist;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao

public interface BucketListDao {


    @Query("SELECT * FROM bucket_list")
    List<BucketList> getAllBucketLists();


    @Insert

    void insertBucketList(BucketList bucketlist);


    @Delete

    void deleteBucketList(List<BucketList> bucketlists);

    @Delete

    void deleteBucketList(BucketList bucketlist);


    @Update

    void updateBucketList(BucketList bucketlist);

}
