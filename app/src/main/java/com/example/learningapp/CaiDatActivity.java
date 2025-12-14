package com.example.learningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CaiDatActivity extends AppCompatActivity {

    Button btnTrangChu, btnCaiDat;
    Button btnCheDoSangToi, btnDoiNgonNgu;
    Button btnDong; // Khai báo nút đóng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cai_dat);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // --- ÁNH XẠ ---
        btnTrangChu = findViewById(R.id.btnTrangChu);
        btnCaiDat = findViewById(R.id.btnCaiDat);
        btnCheDoSangToi = findViewById(R.id.btnCheDoSangToi);
        btnDoiNgonNgu = findViewById(R.id.btnDoiNgonNgu);

        // Ánh xạ nút đóng
        btnDong = findViewById(R.id.btnDong);

        // --- XỬ LÝ NÚT ĐÓNG (X) ---
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thoát hoàn toàn ứng dụng (Giống trang chủ)
                finishAffinity();
                System.exit(0);
            }
        });

        // --- XỬ LÝ NÚT TRANG CHỦ ---
        btnTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaiDatActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // --- XỬ LÝ NÚT CÀI ĐẶT ---
        btnCaiDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CaiDatActivity.this, "Bạn đang ở Cài đặt", Toast.LENGTH_SHORT).show();
            }
        });

        // --- CÁC NÚT CHỨC NĂNG KHÁC ---
        btnCheDoSangToi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển từ CaiDatActivity sang DarkModeActivity
                Intent intent = new Intent(CaiDatActivity.this, DarkModeActivity.class);
                startActivity(intent);
            }
        });

        btnDoiNgonNgu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở màn hình thay đổi ngôn ngữ
                Intent intent = new Intent(CaiDatActivity.this, ChangeLanguageActivity.class);
                startActivity(intent);
            }
        });

    }
}