package com.example.quanlykho.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykho.R;
import com.example.quanlykho.room.VatTu;
import com.example.quanlykho.room.entities.DMKho;
import com.example.quanlykho.room.entities.DMVT;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseVatTuAdapter extends RecyclerView.Adapter<BaseVatTuAdapter.Holder> {
    List<VatTu> vatTuList = new ArrayList<>();
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_base_vattu,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    public void setVatTuList(List<VatTu> vatTuList) {
        this.vatTuList.addAll(vatTuList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.layout_item_base_vattu;
    }


    @Override
    public int getItemCount() {
        return vatTuList.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        @BindView(R.id.base_title_vattu)
        TextView base_title_vattu;
        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void bind(int position){
            VatTu vatTu = vatTuList.get(position);
            if(vatTu instanceof DMVT){
                DMVT dmvt = (DMVT) vatTu;
                base_title_vattu.setText(dmvt.getTenVT());
            }
            if(vatTu instanceof DMKho){
                DMKho kho = (DMKho) vatTu;
                base_title_vattu.setText(kho.getTenKho());
            }
        }
    }
}
