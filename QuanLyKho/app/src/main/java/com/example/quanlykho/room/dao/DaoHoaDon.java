package com.example.quanlykho.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quanlykho.room.entities.HoaDon;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface DaoHoaDon {
    @Insert
    void insert(HoaDon hoaDon);

    @Update
    void update(HoaDon hoaDon);

    @Delete
    void delete(HoaDon hoaDon);

    @Query("SELECT * FROM HoaDon")
    Flowable<List<HoaDon>> getAll();

}
