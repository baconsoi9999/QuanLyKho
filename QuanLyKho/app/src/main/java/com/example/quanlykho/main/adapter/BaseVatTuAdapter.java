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
import com.example.quanlykho.utils.ShowLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseVatTuAdapter extends RecyclerView.Adapter<BaseVatTuAdapter.Holder> {
    List<VatTu> vatTuList = new ArrayList<>();

    private OnClickItem onClickItem;
    public interface OnClickItem{
        void onClick(int position,VatTu vatTu);
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_base_vattu,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(position);
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public void setVatTuList(List<? extends VatTu> vatTuList) {
        this.vatTuList.clear();
        this.vatTuList.addAll(vatTuList);
        ShowLog.d("set list: " + this.vatTuList.size());
        notifyDataSetChanged();
    }

    public void insert(VatTu vatTu){
        this.vatTuList.add(vatTu);
        ShowLog.d("size after insert: "+vatTuList.size());
        notifyDataSetChanged();
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
            onClick();
        }
        private void onClick(){
            itemView.setOnClickListener(v->{
                if(onClickItem!=null){
                    int position = getAdapterPosition();
                    onClickItem.onClick(position,vatTuList.get(position));
                }
            });
        }
        public void bind(int position){
            VatTu vatTu = vatTuList.get(position);
            ShowLog.d("binding adapter: " + position);
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
