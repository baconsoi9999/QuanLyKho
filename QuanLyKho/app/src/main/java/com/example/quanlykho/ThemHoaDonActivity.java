package com.example.quanlykho;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlykho.chi_tiet_hoa_don.ChiTietHoaDonActivity;

public class ThemHoaDonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_hoa_don);

        Button btnThemVatTu = findViewById(R.id.btnThemVattu);
        Button btnSave = findViewById(R.id.btnSave);
        btnThemVatTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    void navigate(){
        startActivity(new Intent(this, ChiTietHoaDonActivity.class));
    }

}
