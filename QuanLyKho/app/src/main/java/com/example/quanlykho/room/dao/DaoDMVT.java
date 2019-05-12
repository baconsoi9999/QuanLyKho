package com.example.quanlykho.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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
