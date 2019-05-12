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
import com.example.quanlykho.main.viewmodel.VTViewmodel;
import com.example.quanlykho.room.QLVTDatabase;
import com.example.quanlykho.room.entities.DMVT;
import com.example.quanlykho.room.repository.RepositoryDMVT;
import com.example.quanlykho.utils.ShowLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class DMVatTuFragment extends Fragment {

    @BindView(R.id.btnAddVatTu)
    Button btnAddVatTu;
    @BindView(R.id.listVatTu)
    RecyclerView listVatTu;

    QLVTDatabase qlvtDatabase;

    RepositoryDMVT repositoryDMVT;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    BaseVatTuAdapter vatTuAdapter;

    VTViewmodel vtViewmodel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_dmvattu_fragment,container,false);
        ButterKnife.bind(this,view);
        ShowLog.d("create view");
        init();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ShowLog.d("view create");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShowLog.d("create");

    }
    private void  init(){
        vatTuAdapter = new BaseVatTuAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        listVatTu.setAdapter(vatTuAdapter);
        listVatTu.setLayoutManager(layoutManager);

        qlvtDatabase = QLVTDatabase.getInstance(getContext());
        repositoryDMVT = new RepositoryDMVT(qlvtDatabase.daoDMVT());
        vtViewmodel = new VTViewmodel(repositoryDMVT);

    }


    private void showDialogAddKho(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_addvt);
        EditText editName,editPrice;
        Button btnOk;
        editName = dialog.findViewById(R.id.edit_addvt_tenvt);
        editPrice = dialog.findViewById(R.id.edit_addvt_price);
        btnOk = dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(v->{
            String name,price;
            name = editName.getText().toString();
            price = editPrice.getText().toString();
            if(name.length()>0 && price.length()>0){
                int priceNum = Integer.parseInt(price);
                DMVT dmvt = new DMVT(priceNum,name,"VND");
                vtViewmodel.insert(dmvt);
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
