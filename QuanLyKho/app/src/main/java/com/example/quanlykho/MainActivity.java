package com.example.quanlykho;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.quanlykho.dmkho.DMKhoActivity;
import com.example.quanlykho.room.QLVTDatabase;
import com.example.quanlykho.room.entities.DMKho;
import com.example.quanlykho.room.entities.HoaDon;
import com.example.quanlykho.room.repository.RepositoryDMKho;
import com.example.quanlykho.room.repository.RepositoryHoaDon;
import com.example.quanlykho.utils.ShowLog;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    QLVTDatabase qlvtDatabase;
    RepositoryDMKho repositoryDMKho;
    RepositoryHoaDon repositoryHoaDon;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
     //   insertDMKho(new DMKho("kho4","kho4_dc4"));
        get();
        getHoaDon();
        //startActivity(new Intent(this, DMKhoActivity.class));
    }
    private void  init(){
        qlvtDatabase = QLVTDatabase.getInstance(this);
        repositoryDMKho = new RepositoryDMKho(qlvtDatabase.daoDMKho());
        repositoryHoaDon = new RepositoryHoaDon(qlvtDatabase.daoHoaDon());

        ShowLog.d("init");
    }
    private void insertDMKho(final DMKho dmKho){

        Disposable disposableInsert = Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                repositoryDMKho.insert(dmKho);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        ShowLog.d("running");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ShowLog.d("insert fail: " + throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposableInsert);
    }
    private void insertHoaDon(final HoaDon hoaDon){
        Disposable disposableInsert = Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                repositoryHoaDon.insert(hoaDon);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        ShowLog.d("running");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ShowLog.d("insert fail: " + throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposableInsert);

    }
    private void get(){
        ShowLog.d("getting");
        Disposable disposableDMKho = repositoryDMKho.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<DMKho>>() {
                    @Override
                    public void accept(List<DMKho> dmKhos) throws Exception {
                        ShowLog.d("size: " + dmKhos.size());
                        for(DMKho dmKho:dmKhos){
                            ShowLog.d(dmKho.getMaKho()+"_"+dmKho.getTenKho()+"_"+dmKho.getDcKho());
                        }
                        if(dmKhos.size()>0){
                            insertHoaDon(new HoaDon("2/2/2","nameKH2",dmKhos.get(0).getMaKho()));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ShowLog.d("error getting: "+throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposableDMKho);
    }
    private void getHoaDon(){
        Disposable disposableGet = repositoryHoaDon.getAll().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<List<HoaDon>>() {
                    @Override
                    public void accept(List<HoaDon> hoaDons) throws Exception {
                        ShowLog.d("hoadon size: " + hoaDons.size());
                        for(HoaDon t:hoaDons){
                            ShowLog.d("hoadon: " + t.getSoHD()+"_"+t.getNgayHD()+"_"+t.getHotenKH()+"_"+t.getMaKho());

                        }
                    }
                });
        compositeDisposable.add(disposableGet);
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.dispose();
    }
}
