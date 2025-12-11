package com.example.learningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast; // Thêm thư viện thông báo
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Khai báo hết các nút
    Button btnDeCuong, btnNhacHen, btnOnTap;
    Button btnDong, btnTrangChu, btnCaiDat; // Các nút mới thêm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ẩn thanh tiêu đề mặc định đi cho giống thiết kế trong ảnh
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // --- ÁNH XẠ CÁC NÚT ---
        btnDeCuong = findViewById(R.id.btnDeCuong);
        btnNhacHen = findViewById(R.id.btnNhacHen);
        btnOnTap = findViewById(R.id.btnOnTap);

        // Các nút mới
        btnDong = findViewById(R.id.btnDong);
        btnTrangChu = findViewById(R.id.btnTrangChu);
        btnCaiDat = findViewById(R.id.btnCaiDat);

        // --- XỬ LÝ SỰ KIỆN CHUYỂN MÀN HÌNH ---

        btnDeCuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeCuongActivity.class);
                startActivity(intent);
            }
        });

        btnNhacHen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NhacHenActivity.class);
                startActivity(intent);
            }
        });

        btnOnTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OnTapActivity.class);
                startActivity(intent);
            }
        });

        // --- XỬ LÝ CÁC NÚT PHỤ ---

        // Nút Đóng: Thoát ứng dụng
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity(); // Đóng hoàn toàn ứng dụng
                System.exit(0);
            }
        });

        // Nút Trang chủ & Cài đặt: Hiện thông báo (vì chưa có màn hình chức năng này)
        btnTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Bạn đang ở Trang chủ", Toast.LENGTH_SHORT).show();
            }
        });

        btnCaiDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Chức năng Cài đặt đang phát triển", Toast.LENGTH_SHORT).show();
            }
        });
    }
}