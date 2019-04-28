package com.example.quanlykho.room.repository;

import com.example.quanlykho.room.DataSourceQLVT;
import com.example.quanlykho.room.VatTu;
import com.example.quanlykho.room.dao.DaoDMKho;
import com.example.quanlykho.room.dao.DaoDMVT;
import com.example.quanlykho.room.entities.DMKho;
import com.example.quanlykho.room.entities.DMVT;

import java.util.List;

import io.reactivex.Flowable;

public class RepositoryDMVT implements DataSourceQLVT<DMVT> {
    DaoDMVT dao;

    public RepositoryDMVT(DaoDMVT daoDMVT) {
        this.dao = daoDMVT;
    }

    @Override
    public void insert(DMVT dmvt) {
        dao.insert(dmvt);
    }

    @Override
    public void update(DMVT dmvt) {
        dao.update(dmvt);
    }

    @Override
    public void delete(DMVT dmvt) {
        dao.delete(dmvt);
    }

    @Override
    public Flowable<List<DMVT>> getAll() {
        return dao.getAll();
    }
}
