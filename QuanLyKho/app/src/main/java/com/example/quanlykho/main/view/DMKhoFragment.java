package com.example.quanlykho.main.view;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykho.R;
import com.example.quanlykho.main.adapter.BaseVatTuAdapter;
import com.example.quanlykho.main.base.MainInterface;
import com.example.quanlykho.main.viewmodel.KhoViewModel;
import com.example.quanlykho.room.QLVTDatabase;
import com.example.quanlykho.room.VatTu;
import com.example.quanlykho.room.entities.DMKho;
import com.example.quanlykho.room.repository.RepositoryDMKho;
import com.example.quanlykho.room.repository.RepositoryHoaDon;
import com.example.quanlykho.utils.ShowLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DMKhoFragment extends Fragment implements BaseVatTuAdapter.OnClickItem{


    @BindView(R.id.btnAddKho)
    Button btnAddKho;
    @BindView(R.id.listKho)
    RecyclerView listKho;

    MainInterface mainInterface;
    QLVTDatabase qlvtDatabase;
    RepositoryDMKho repositoryDMKho;
    RepositoryHoaDon repositoryHoaDon;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    BaseVatTuAdapter vatTuAdapter;
    KhoViewModel khoViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_dmkho_fragment,container,false);
        ButterKnife.bind(this,view);


        init();
        listener();
        onClick();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void  init(){
        vatTuAdapter = new BaseVatTuAdapter();
        vatTuAdapter.setOnClickItem(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        listKho.setAdapter(vatTuAdapter);
        listKho.setLayoutManager(layoutManager);

        qlvtDatabase = QLVTDatabase.getInstance(getContext());
        repositoryDMKho = new RepositoryDMKho(qlvtDatabase.daoDMKho());
        khoViewModel = new KhoViewModel(repositoryDMKho);
    }
    private void onClick(){
        btnAddKho.setOnClickListener(v->{
            showDialogAddKho();
        });
    }
    private void listener(){
        Disposable disposableKhoGetAll = khoViewModel.getDmKhoBehaviorSubject().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<List<DMKho>>() {
                    @Override
                    public void accept(List<DMKho> dmKhos) throws Exception {
                        ShowLog.d("dm vattu fm get success "+dmKhos.size());
                        vatTuAdapter.setVatTuList(dmKhos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ShowLog.d("error listener kho: "+throwable.getMessage());
                    }
                });

        Disposable disposableKhoInsert = khoViewModel.getInsertBehaviorSubject().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<KhoViewModel.InsertResponse>() {
                    @Override
                    public void accept(KhoViewModel.InsertResponse insertResponse) throws Exception {
                        if(insertResponse.getVatTu()!=null){
                            vatTuAdapter.insert(insertResponse.getVatTu());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ShowLog.d("error listener insert: " + throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposableKhoGetAll);
        compositeDisposable.add(disposableKhoInsert);
    }

    private void showDialogAddKho(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_addkho);
        EditText editName,editAddress;
        Button btnOk;
        editName = dialog.findViewById(R.id.edit_addkho_tenkho);
        editAddress = dialog.findViewById(R.id.edit_addkho_dckho);
        btnOk = dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(v->{
            String name,address;
            name = editName.getText().toString();
            address = editAddress.getText().toString();
            if(name.length()>0 && address.length()>0){
                DMKho dmKho = new DMKho(name,address);
                khoViewModel.insert(dmKho);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
        khoViewModel.dispose();
    }

    @Override
    public void onClick(int position, VatTu vatTu) {
        if(mainInterface!=null){
            mainInterface.navigateHoaDon(position,vatTu);
        }
    }

    public void setMainInterface(MainInterface mainInterface) {
        this.mainInterface = mainInterface;
    }
}
