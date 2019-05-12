package com.example.quanlykho.hoa_don;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykho.R;
import com.example.quanlykho.chi_tiet_hoa_don.ChiTietHoaDonActivity;
import com.example.quanlykho.main.MainActivity;
import com.example.quanlykho.room.QLVTDatabase;
import com.example.quanlykho.room.VatTu;
import com.example.quanlykho.room.entities.HoaDon;
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

public class HoaDonActivity extends AppCompatActivity implements HoaDonAdapter.OnClickItem{

    @BindView(R.id.btnAddKho)
    Button btnAddKho;
    @BindView(R.id.listKho)
    RecyclerView listKho;

    QLVTDatabase qlvtDatabase;
    RepositoryDMKho repositoryDMKho;
    RepositoryHoaDon repositoryHoaDon;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    HoaDonAdapter hoadonAdapter;
    HoaDonViewModel hoadonViewModel;

    public static final String MAHD = "MAHD";
    private int makho;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        ButterKnife.bind(this);

        Intent intent= getIntent();

        if(intent!=null){
            makho = intent.getIntExtra(MainActivity.MAKHO,0);
        }
        init();
        onClick();
        listener();
    }

    private void  init(){
        hoadonAdapter = new HoaDonAdapter();
        hoadonAdapter.setOnClickItem(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listKho.setAdapter(hoadonAdapter);
        listKho.setLayoutManager(layoutManager);

        qlvtDatabase = QLVTDatabase.getInstance(this);
        repositoryHoaDon = new RepositoryHoaDon(qlvtDatabase.daoHoaDon());
        hoadonViewModel = new HoaDonViewModel(repositoryHoaDon);
    }
    private void onClick(){
        btnAddKho.setOnClickListener(v->{
            showDialogAddKho();
        });
    }
    private void listener(){
        Disposable disposableKhoGetAll = hoadonViewModel.getHoadonBehaviorSubject().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<List<HoaDon>>() {
                    @Override
                    public void accept(List<HoaDon> hoaDonList) throws Exception {
                        ShowLog.d("dm vattu fm get success "+hoaDonList.size());
                        hoadonAdapter.setVatTuList(hoaDonList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ShowLog.d("error listener kho: "+throwable.getMessage());
                    }
                });

        Disposable disposableKhoInsert = hoadonViewModel.getInsertBehaviorSubject().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<HoaDonViewModel.InsertResponse>() {
                    @Override
                    public void accept(HoaDonViewModel.InsertResponse insertResponse) throws Exception {
                        if(insertResponse.getVatTu()!=null){
                            ShowLog.d("insert hoa don success");
                            hoadonAdapter.insert(insertResponse.getVatTu());
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
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_add_hoadon);
        EditText editName,editAddress;
        Button btnOk;
        editName = dialog.findViewById(R.id.edit_addkho_tenkho);
        editAddress = dialog.findViewById(R.id.edit_addkho_dckho);
        btnOk = dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(v->{
            String name,ngayHd;
            name = editName.getText().toString();
            ngayHd = editAddress.getText().toString();
            if(name.length()>0 && ngayHd.length()>0){
                HoaDon hoaDon = new HoaDon(name,ngayHd,makho);
                hoadonViewModel.insert(hoaDon);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
        hoadonViewModel.dispose();
    }

    @Override
    public void onClick(int position, VatTu vatTu) {
        if(vatTu instanceof HoaDon) {
            HoaDon hoaDon = (HoaDon) vatTu;
            Intent intent = new Intent(this, ChiTietHoaDonActivity.class);
            intent.putExtra(MAHD, hoaDon.getSoHD());
            startActivity(intent);
        }
    }

}
