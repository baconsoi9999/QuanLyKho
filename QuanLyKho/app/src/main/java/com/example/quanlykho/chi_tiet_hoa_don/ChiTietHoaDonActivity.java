package com.example.quanlykho.chi_tiet_hoa_don;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykho.R;
import com.example.quanlykho.chi_tiet_hoa_don.adapter.CTHDAdapter;
import com.example.quanlykho.chi_tiet_hoa_don.viewmodel.ChiTietHoaDonViewModel;
import com.example.quanlykho.hoa_don.HoaDonActivity;
import com.example.quanlykho.room.QLVTDatabase;
import com.example.quanlykho.room.entities.CTHoaDon;
import com.example.quanlykho.room.entities.DMVT;
import com.example.quanlykho.room.repository.RepositoryCTHoaDon;
import com.example.quanlykho.room.repository.RepositoryDMVT;
import com.example.quanlykho.utils.ShowLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ChiTietHoaDonActivity extends AppCompatActivity {

    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.listCTHD)
    RecyclerView listCTHD;
    @BindView(R.id.editSL)
    EditText editSL;
    @BindView(R.id.btnSave)
    Button btnSave;
    ArrayList<String> listSpinner = new ArrayList<>();
    ArrayAdapter<String> dataAdapter;

    QLVTDatabase qlvtDatabase;
    RepositoryDMVT repositoryDMVT;
    RepositoryCTHoaDon repositoryCTHoaDon;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    CTHDAdapter cthdAdapter;
    ChiTietHoaDonViewModel chiTietHoaDonViewModel;

    ArrayList<DMVT> dmvtArrayList = new ArrayList<>();
    int soHd;
    int maDMVT = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hoa_don);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if(intent!=null){
            soHd = intent.getIntExtra(HoaDonActivity.MAHD,0);
        }
        init();
        onClick();
        listener();
    }

    private void  init(){


        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listSpinner);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        cthdAdapter = new CTHDAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listCTHD.setLayoutManager(layoutManager);
        listCTHD.setAdapter(cthdAdapter);
        qlvtDatabase = QLVTDatabase.getInstance(this);
        repositoryCTHoaDon = new RepositoryCTHoaDon(qlvtDatabase.daoCTHoaDon());
        repositoryDMVT = new RepositoryDMVT(qlvtDatabase.daoDMVT());

        chiTietHoaDonViewModel = new ChiTietHoaDonViewModel(repositoryCTHoaDon,repositoryDMVT);


        ShowLog.d("init");
    }

    private void onClick(){
        btnSave.setOnClickListener(v->{
            String sl = editSL.getText().toString();
            if(sl.length()>0){
                int pos= spinner.getSelectedItemPosition();
                chiTietHoaDonViewModel.insert(new CTHoaDon(soHd,dmvtArrayList.get(pos).getMaVT(),Integer.parseInt(sl)));
            }
        });
    }
    private void listener(){
        chiTietHoaDonViewModel.getCtHoadonBehaviorSubject().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<List<CTHoaDon>>() {
                    @Override
                    public void accept(List<CTHoaDon> ctHoaDons) throws Exception {
                        cthdAdapter.setVatTuList(ctHoaDons);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ShowLog.d("error get chitiet cthoadon " + throwable.getMessage());

                    }
                });
        chiTietHoaDonViewModel.getDmvtBehaviorSubject().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<List<DMVT>>() {
                    @Override
                    public void accept(List<DMVT> dmvts) throws Exception {
                        listSpinner.clear();
                        dmvtArrayList.clear();
                        dmvtArrayList.addAll(dmvts);
                        for(DMVT dmvt:dmvts){
                            ShowLog.d("dmvt: " + dmvt.getTenVT());
                            listSpinner.add(dmvt.getTenVT());
                        }
                        dataAdapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ShowLog.d("error get chitiet dmvt " + throwable.getMessage());

                    }
                });
        chiTietHoaDonViewModel.getInsertBehaviorSubject().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<ChiTietHoaDonViewModel.InsertResponse>() {
                    @Override
                    public void accept(ChiTietHoaDonViewModel.InsertResponse insertResponse) throws Exception {
                        if(insertResponse.getVatTu()!=null){
                            cthdAdapter.insert(insertResponse.getVatTu());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ShowLog.d("error get chitiet insert " + throwable.getMessage());

                    }
                });
    }
}
