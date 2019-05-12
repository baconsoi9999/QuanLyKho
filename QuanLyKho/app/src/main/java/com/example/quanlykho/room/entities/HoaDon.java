package com.example.quanlykho.room.entities;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.quanlykho.room.VatTu;

import static androidx.room.ForeignKey.CASCADE;


@Entity(foreignKeys = @ForeignKey(
        entity = DMKho.class,
        parentColumns = "maKho",
        childColumns = "maKho",
        onDelete = CASCADE,
        onUpdate = CASCADE))
public class HoaDon extends VatTu {
    @PrimaryKey(autoGenerate = true)
   private int soHD;
    private int maKho;
   private String ngayHD, hotenKH;

    public int getSoHD() {
        return soHD;
    }

    public String getNgayHD() {
        return ngayHD;
    }

    public String getHotenKH() {
        return hotenKH;
    }

    public int getMaKho() {
        return maKho;
    }

    public void setSoHD(int soHD) {
        this.soHD = soHD;
    }

    public void setNgayHD(String ngayHD) {
        this.ngayHD = ngayHD;
    }

    public void setHotenKH(String hotenKH) {
        this.hotenKH = hotenKH;
    }

    public void setMaKho(int maKho) {
        this.maKho = maKho;
    }

    public HoaDon(String ngayHD, String hotenKH, int maKho) {
        this.ngayHD = ngayHD;
        this.hotenKH = hotenKH;
        this.maKho = maKho;
    }
}
