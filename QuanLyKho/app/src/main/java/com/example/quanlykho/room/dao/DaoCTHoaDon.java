package com.example.quanlykho.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quanlykho.room.entities.CTHoaDon;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface DaoCTHoaDon {
    @Insert
    void insert(CTHoaDon ctHoaDon);

    @Update
    void update(CTHoaDon ctHoaDon);

    @Delete
    void delete(CTHoaDon ctHoaDon);

    @Query("SELECT * FROM CTHoaDon")
    Flowable<List<CTHoaDon>> getAll();

}
