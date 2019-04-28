package com.example.quanlykho.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
