package com.example.quanlykho.main.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.quanlykho.room.VatTu;
import com.example.quanlykho.room.entities.DMVT;
import com.example.quanlykho.room.repository.RepositoryDMVT;
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

public class VTViewmodel extends ViewModel {

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
    RepositoryDMVT repositoryDMVT;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    BehaviorSubject<List<DMVT>> dmKhoBehaviorSubject = BehaviorSubject.create();
    BehaviorSubject<InsertResponse> insertBehaviorSubject = BehaviorSubject.create();

    public VTViewmodel(RepositoryDMVT repositoryDMVT) {
        this.repositoryDMVT = repositoryDMVT;
        getAll();
    }

    private void getAll(){
        Disposable disposableGetAll = repositoryDMVT.getAll().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<List<DMVT>>() {
                    @Override
                    public void accept(List<DMVT> dmvts) throws Exception {
                        dmKhoBehaviorSubject.onNext(dmvts);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ShowLog.d("error get kho " + throwable.getMessage());
                    }
                });

        compositeDisposable.add(disposableGetAll);
    }

    public void insert(DMVT dmvt){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                repositoryDMVT.insert(dmvt);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        insertBehaviorSubject.onNext(new InsertResponse(dmvt,null));
                    }

                    @Override
                    public void onError(Throwable e) {
                        ShowLog.d("error insert dmkho: " + e.getMessage());
                        insertBehaviorSubject.onNext(new InsertResponse(null,e));
                    }
                });
    }

}
