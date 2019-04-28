package com.example.quanlykho.room.repository;

import com.example.quanlykho.room.DataSourceQLVT;
import com.example.quanlykho.room.VatTu;
import com.example.quanlykho.room.dao.DaoDMKho;
import com.example.quanlykho.room.dao.DaoHoaDon;
import com.example.quanlykho.room.entities.DMKho;
import com.example.quanlykho.room.entities.HoaDon;

import java.util.List;

import io.reactivex.Flowable;

public class RepositoryHoaDon implements DataSourceQLVT<HoaDon> {
    DaoHoaDon dao;

    public RepositoryHoaDon(DaoHoaDon daoHoaDon) {
        this.dao = daoHoaDon;
    }

    @Override
    public void insert(HoaDon hoaDon) {
        dao.insert(hoaDon);
    }

    @Override
    public void update(HoaDon hoaDon) {
        dao.update(hoaDon);
    }

    @Override
    public void delete(HoaDon hoaDon) {
        dao.delete(hoaDon);
    }

    @Override
    public Flowable<List<HoaDon>> getAll() {
        return dao.getAll();
    }

}
