package com.example.quanlykho.room.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.quanlykho.room.VatTu;

@Entity
public class DMKho extends VatTu {
    @PrimaryKey(autoGenerate = true)
    private int maKho;
    private String tenKho,dcKho;

    public String getTenKho() {
        return tenKho;
    }

    public String getDcKho() {
        return dcKho;
    }

    public int getMaKho() {
        return maKho;
    }

    public void setMaKho(int maKho) {
        this.maKho = maKho;
    }

    public void setTenKho(String tenKho) {
        this.tenKho = tenKho;
    }

    public void setDcKho(String dcKho) {
        this.dcKho = dcKho;
    }

    public DMKho(String tenKho, String dcKho) {
        this.tenKho = tenKho;
        this.dcKho = dcKho;
    }
}
