package com.example.quanlykho.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.quanlykho.room.entities.DMKho;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface DaoDMKho {
    @Insert
    void insert(DMKho dmKho);

    @Update
    void update(DMKho dmKho);

    @Delete
    void delete(DMKho dmKho);

    @Query("SELECT * FROM DMKho")
    Flowable<List<DMKho>> getAll();
}
