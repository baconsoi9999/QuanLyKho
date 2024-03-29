package com.example.quanlykho.main.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.quanlykho.room.VatTu;
import com.example.quanlykho.room.entities.DMKho;
import com.example.quanlykho.room.repository.RepositoryDMKho;
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

public class KhoViewModel extends ViewModel {
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
    BehaviorSubject<List<DMKho>> dmKhoBehaviorSubject = BehaviorSubject.create();
    BehaviorSubject<InsertResponse> insertBehaviorSubject = BehaviorSubject.create();

    RepositoryDMKho repositoryDMKho;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public KhoViewModel(RepositoryDMKho repositoryDMKho) {
        this.repositoryDMKho = repositoryDMKho;
        getAll();
    }

    private void getAll(){
        Disposable disposableGetAll = repositoryDMKho.getAll().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<List<DMKho>>() {
                    @Override
                    public void accept(List<DMKho> dmKhos) throws Exception {
                        dmKhoBehaviorSubject.onNext(dmKhos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ShowLog.d("error get kho " + throwable.getMessage());
                    }
                });

        compositeDisposable.add(disposableGetAll);
    }

    public void insert(DMKho dmKho){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                repositoryDMKho.insert(dmKho);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        insertBehaviorSubject.onNext(new InsertResponse(dmKho,null));
                    }

                    @Override
                    public void onError(Throwable e) {
                        ShowLog.d("error insert dmkho: " + e.getMessage());
                        insertBehaviorSubject.onNext(new InsertResponse(null,e));
                    }
                });
    }
    public BehaviorSubject<List<DMKho>> getDmKhoBehaviorSubject() {
        return dmKhoBehaviorSubject;
    }

    public BehaviorSubject<InsertResponse> getInsertBehaviorSubject() {
        return insertBehaviorSubject;
    }

    public void dispose(){
        compositeDisposable.dispose();
    }
}
