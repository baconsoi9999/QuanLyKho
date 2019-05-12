package com.example.quanlykho.room.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.quanlykho.room.VatTu;

@Entity
public class DMVT extends VatTu {
    @PrimaryKey(autoGenerate = true)
    private int maVT;
    private int donGiaGoc;
    private String tenVT,DVT;

    public int getMaVT() {
        return maVT;
    }

    public int getDonGiaGoc() {
        return donGiaGoc;
    }

    public String getTenVT() {
        return tenVT;
    }

    public String getDVT() {
        return DVT;
    }

    public void setMaVT(int maVT) {
        this.maVT = maVT;
    }

    public void setDonGiaGoc(int donGiaGoc) {
        this.donGiaGoc = donGiaGoc;
    }

    public void setTenVT(String tenVT) {
        this.tenVT = tenVT;
    }

    public void setDVT(String DVT) {
        this.DVT = DVT;
    }

    public DMVT(int donGiaGoc, String tenVT, String DVT) {
        this.donGiaGoc = donGiaGoc;
        this.tenVT = tenVT;
        this.DVT = DVT;
    }
}
