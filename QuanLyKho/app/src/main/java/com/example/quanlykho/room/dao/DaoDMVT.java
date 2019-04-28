package com.example.quanlykho.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.quanlykho.room.entities.DMVT;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface DaoDMVT {
    @Insert
    void insert(DMVT dmvt);

    @Update
    void update(DMVT dmvt);

    @Delete
    void delete(DMVT dmvt);

    @Query("SELECT * FROM DMVT")
    Flowable<List<DMVT>> getAll();

}
