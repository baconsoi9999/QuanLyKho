package com.example.quanlykho.chi_tiet_hoa_don.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.quanlykho.room.VatTu;
import com.example.quanlykho.room.entities.CTHoaDon;
import com.example.quanlykho.room.entities.DMVT;
import com.example.quanlykho.room.repository.RepositoryCTHoaDon;
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

public class ChiTietHoaDonViewModel extends ViewModel {
    RepositoryCTHoaDon repositoryCTHoaDon;
    RepositoryDMVT repositoryDMVT;

    public ChiTietHoaDonViewModel(RepositoryCTHoaDon repositoryCTHoaDon, RepositoryDMVT repositoryDMVT) {
        this.repositoryCTHoaDon = repositoryCTHoaDon;
        this.repositoryDMVT = repositoryDMVT;
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
    BehaviorSubject<List<CTHoaDon>> ctHoadonBehaviorSubject = BehaviorSubject.create();
    BehaviorSubject<List<DMVT>> dmvtBehaviorSubject = BehaviorSubject.create();
    BehaviorSubject<InsertResponse> insertBehaviorSubject = BehaviorSubject.create();

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private void getAll(){
        Disposable disposableGetAll = repositoryCTHoaDon.getAll().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<List<CTHoaDon>>() {
                    @Override
                    public void accept(List<CTHoaDon> ctHoaDonList) throws Exception {
                        ctHoadonBehaviorSubject.onNext(ctHoaDonList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ShowLog.d("error get cthoadon " + throwable.getMessage());
                    }
                });

        Disposable disposableGetDMVT = repositoryDMVT.getAll().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<List<DMVT>>() {
                    @Override
                    public void accept(List<DMVT> dmvts) throws Exception {
                        dmvtBehaviorSubject.onNext(dmvts);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ShowLog.d("error get dmvt " + throwable.getMessage());
                    }
                });

        compositeDisposable.add(disposableGetAll);
        compositeDisposable.add(disposableGetDMVT);
    }

    public void insert(CTHoaDon ctHoaDon){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                repositoryCTHoaDon.insert(ctHoaDon);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        insertBehaviorSubject.onNext(new InsertResponse(ctHoaDon,null));
                    }

                    @Override
                    public void onError(Throwable e) {
                        ShowLog.d("error insert dmkho: " + e.getMessage());
                        insertBehaviorSubject.onNext(new InsertResponse(null,e));
                    }
                });
    }
    public BehaviorSubject<List<CTHoaDon>> getCtHoadonBehaviorSubject() {
        return ctHoadonBehaviorSubject;
    }

    public BehaviorSubject<InsertResponse> getInsertBehaviorSubject() {
        return insertBehaviorSubject;
    }

    public BehaviorSubject<List<DMVT>> getDmvtBehaviorSubject() {
        return dmvtBehaviorSubject;
    }

    public void dispose(){
        compositeDisposable.dispose();
    }

}
