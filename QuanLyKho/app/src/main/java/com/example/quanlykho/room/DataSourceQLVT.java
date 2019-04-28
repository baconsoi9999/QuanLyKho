package com.example.quanlykho.room;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.quanlykho.room.entities.CTHoaDon;

import java.util.List;

import io.reactivex.Flowable;

public interface DataSourceQLVT<T extends VatTu> {
    void insert(T t);

    void update(T t);

    void delete(T t);

    Flowable<List<T>> getAll();

}
