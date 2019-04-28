package com.example.quanlykho.room.repository;

import com.example.quanlykho.room.DataSourceQLVT;
import com.example.quanlykho.room.dao.DaoDMKho;
import com.example.quanlykho.room.entities.DMKho;

import java.util.List;

import io.reactivex.Flowable;

public class RepositoryDMKho implements DataSourceQLVT<DMKho> {
    DaoDMKho dao;

    public RepositoryDMKho(DaoDMKho daoDMKho) {
        this.dao = daoDMKho;
    }

    @Override
    public void insert(DMKho dmKho) {
        dao.insert(dmKho);
    }

    @Override
    public void update(DMKho dmKho) {
        dao.update(dmKho);
    }

    @Override
    public void delete(DMKho dmKho) {
        dao.delete(dmKho);
    }

    @Override
    public Flowable<List<DMKho>> getAll() {
        return dao.getAll();
    }

}
