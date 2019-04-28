package com.example.quanlykho.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
