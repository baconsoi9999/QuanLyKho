package com.example.quanlykho.dmkho;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.quanlykho.HoaDonActivity;
import com.example.quanlykho.R;
import com.example.quanlykho.ThemHoaDonActivity;

public class DMKhoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmkho);

        Button btnNh = findViewById(R.id.btnNhapHang);

        btnNh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate();
            }
        });
    }
    private void navigate(){
        startActivity(new Intent(this, ThemHoaDonActivity.class));
    }
}
