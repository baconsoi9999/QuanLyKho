package com.example.quanlykho.main.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykho.R;
import com.example.quanlykho.room.QLVTDatabase;
import com.example.quanlykho.room.repository.RepositoryDMKho;
import com.example.quanlykho.room.repository.RepositoryHoaDon;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class DMVatTuFragment extends Fragment {

    @BindView(R.id.btnAddVatTu)
    Button btnAddVatTu;
    @BindView(R.id.listVatTu)
    RecyclerView listVatTu;

    QLVTDatabase qlvtDatabase;
    RepositoryDMKho repositoryDMKho;
    RepositoryHoaDon repositoryHoaDon;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_dmvattu_fragment,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void  init(){
        qlvtDatabase = QLVTDatabase.getInstance(getContext());
        repositoryDMKho = new RepositoryDMKho(qlvtDatabase.daoDMKho());
        repositoryHoaDon = new RepositoryHoaDon(qlvtDatabase.daoHoaDon());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();

    }
}
