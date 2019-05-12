package com.example.quanlykho.room;


import java.util.List;

import io.reactivex.Flowable;

public interface DataSourceQLVT<T extends VatTu> {
    void insert(T t);

    void update(T t);

    void delete(T t);

    Flowable<List<T>> getAll();

}
