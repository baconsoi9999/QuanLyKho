package com.example.quanlykho.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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
