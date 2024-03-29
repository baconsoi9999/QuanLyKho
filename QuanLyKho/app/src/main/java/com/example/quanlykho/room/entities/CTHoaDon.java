package com.example.quanlykho.room.entities;


import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.quanlykho.room.VatTu;

import static androidx.room.ForeignKey.CASCADE;


@Entity(primaryKeys = {"soHD","maVT"},
        foreignKeys = {
        @ForeignKey(
                entity = HoaDon.class,
                parentColumns = "soHD",
                childColumns = "soHD",
                onDelete = CASCADE,
                onUpdate = CASCADE),
        @ForeignKey(
                entity = DMVT.class,
                parentColumns = "maVT",
                childColumns = "maVT",
                onDelete = CASCADE,
                onUpdate = CASCADE)})
public class CTHoaDon extends VatTu {
    private int soHD,maVT;
    private int soLuong;

    public int getSoHD() {
        return soHD;
    }

    public int getMaVT() {
        return maVT;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoHD(int soHD) {
        this.soHD = soHD;
    }

    public void setMaVT(int maVT) {
        this.maVT = maVT;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public CTHoaDon(int soHD, int maVT, int soLuong) {
        this.soHD = soHD;
        this.maVT = maVT;
        this.soLuong = soLuong;
    }
}
