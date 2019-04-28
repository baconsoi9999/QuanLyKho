package com.example.quanlykho.room.repository;

import com.example.quanlykho.room.DataSourceQLVT;
import com.example.quanlykho.room.VatTu;
import com.example.quanlykho.room.dao.DaoCTHoaDon;
import com.example.quanlykho.room.dao.DaoDMKho;
import com.example.quanlykho.room.entities.CTHoaDon;
import com.example.quanlykho.room.entities.DMKho;

import java.util.List;

import io.reactivex.Flowable;

public class RepositoryCTHoaDon implements DataSourceQLVT<CTHoaDon> {
    DaoCTHoaDon dao;

    public RepositoryCTHoaDon(DaoCTHoaDon daoCTHoaDon) {
        this.dao = daoCTHoaDon;
    }

    @Override
    public void insert(CTHoaDon ctHoaDon) {
        dao.insert(ctHoaDon);
    }

    @Override
    public void update(CTHoaDon ctHoaDon) {
        dao.update(ctHoaDon);
    }

    @Override
    public void delete(CTHoaDon ctHoaDon) {
        dao.delete(ctHoaDon);
    }

    @Override
    public Flowable<List<CTHoaDon>> getAll() {
        return dao.getAll();
    }
}
