package com.example.quanlykho.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.quanlykho.room.dao.DaoCTHoaDon;
import com.example.quanlykho.room.dao.DaoDMKho;
import com.example.quanlykho.room.dao.DaoDMVT;
import com.example.quanlykho.room.dao.DaoHoaDon;
import com.example.quanlykho.room.entities.CTHoaDon;
import com.example.quanlykho.room.entities.DMKho;
import com.example.quanlykho.room.entities.DMVT;
import com.example.quanlykho.room.entities.HoaDon;



@Database(entities = {CTHoaDon.class, DMKho.class, DMVT.class, HoaDon.class},version = 2)
public abstract  class QLVTDatabase extends RoomDatabase {

    public static final int DB_VERSION = 1;
    private static QLVTDatabase qlvtDatabase = null;

    private static String DB_NAME = "QLVT";
    //private static final int DB_VERSION = 1;

    public abstract DaoCTHoaDon daoCTHoaDon();
    public abstract DaoDMKho daoDMKho();
    public abstract DaoDMVT daoDMVT();
    public abstract DaoHoaDon daoHoaDon();

    public static QLVTDatabase getInstance(Context context) {
        if(qlvtDatabase == null){
            qlvtDatabase = Room.databaseBuilder(context,QLVTDatabase.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return qlvtDatabase;
    }

}
