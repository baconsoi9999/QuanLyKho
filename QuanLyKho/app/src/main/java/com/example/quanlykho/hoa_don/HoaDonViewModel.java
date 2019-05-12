package com.example.quanlykho.hoa_don;

import androidx.lifecycle.ViewModel;

import com.example.quanlykho.room.VatTu;
import com.example.quanlykho.room.entities.HoaDon;
import com.example.quanlykho.room.repository.RepositoryHoaDon;
import com.example.quanlykho.utils.ShowLog;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class HoaDonViewModel extends ViewModel {
    RepositoryHoaDon repositoryHoaDon;

    public HoaDonViewModel(RepositoryHoaDon repositoryHoaDon) {
        this.repositoryHoaDon = repositoryHoaDon;
        getAll();
    }
    public class InsertResponse{
        Throwable throwable;
        VatTu vatTu;

        public InsertResponse(VatTu vatTu,Throwable throwable) {
            this.throwable = throwable;
            this.vatTu = vatTu;
        }

        public Throwable getThrowable() {
            return throwable;
        }

        public VatTu getVatTu() {
            return vatTu;
        }
    }
    BehaviorSubject<List<HoaDon>> hoadonBehaviorSubject = BehaviorSubject.create();
    BehaviorSubject<InsertResponse> insertBehaviorSubject = BehaviorSubject.create();

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private void getAll(){
        Disposable disposableGetAll = repositoryHoaDon.getAll().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<List<HoaDon>>() {
                    @Override
                    public void accept(List<HoaDon> hoaDonList) throws Exception {
                        hoadonBehaviorSubject.onNext(hoaDonList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ShowLog.d("error get kho " + throwable.getMessage());
                    }
                });

        compositeDisposable.add(disposableGetAll);
    }

    public void insert(HoaDon hoaDon){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                repositoryHoaDon.insert(hoaDon);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        insertBehaviorSubject.onNext(new InsertResponse(hoaDon,null));
                    }

                    @Override
                    public void onError(Throwable e) {
                        ShowLog.d("error insert dmkho: " + e.getMessage());
                        insertBehaviorSubject.onNext(new InsertResponse(null,e));
                    }
                });
    }
    public BehaviorSubject<List<HoaDon>> getHoadonBehaviorSubject() {
        return hoadonBehaviorSubject;
    }

    public BehaviorSubject<InsertResponse> getInsertBehaviorSubject() {
        return insertBehaviorSubject;
    }

    public void dispose(){
        compositeDisposable.dispose();
    }

}
