package com.example.learningapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    // Khai báo các nút bấm (Tất cả đều là Button)
    Button btnDeCuong, btnNhacHen, btnOnTap;
    Button btnDong;
    Button btnTrangChu, btnCaiDat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LanguageManager langManager = new LanguageManager(this);
        langManager.updateResource(langManager.getLang());
        setContentView(R.layout.activity_main);

        // Ẩn thanh tiêu đề mặc định (Action Bar) cho đẹp
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        // --- 1. ÁNH XẠ (TÌM ID TỪ FILE XML) ---
        // Lưu ý: Các ID này phải khớp chính xác với android:id trong file activity_main.xml
        btnDeCuong = findViewById(R.id.btnDeCuong);
        btnNhacHen = findViewById(R.id.btnNhacHen);
        btnOnTap = findViewById(R.id.btnOnTap);
        btnDong = findViewById(R.id.btnDong);

        // ID mới sửa lại (btnTrangChu thay vì btnNavTrangChu)
        btnTrangChu = findViewById(R.id.btnTrangChu);
        btnCaiDat = findViewById(R.id.btnCaiDat);

        // --- 2. XỬ LÝ CÁC CHỨC NĂNG CHÍNH ---

        // Chức năng 1: Vào Đề Cương
        btnDeCuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeCuongActivity.class);
                startActivity(intent);
            }
        });

        // Chức năng 2: Vào Nhắc Hẹn
        btnNhacHen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NhacHenActivity.class);
                startActivity(intent);
            }
        });

        // Chức năng 3: Vào Ôn Tập & Kiểm Tra
        btnOnTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OnTapActivity.class);
                startActivity(intent);
            }
        });

        // --- 3. XỬ LÝ CÁC NÚT ĐIỀU HƯỚNG & ĐÓNG ---

        // Nút Đóng (X): Thoát hẳn ứng dụng
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity(); // Đóng tất cả các màn hình
                System.exit(0);   // Thoát tiến trình
            }
        });

        // Nút Trang Chủ: Hiện thông báo vì đang ở trang chủ rồi
        btnTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Bạn đang ở Trang chủ", Toast.LENGTH_SHORT).show();
            }
        });

        // Nút Cài Đặt: Chuyển sang màn hình Cài Đặt
        btnCaiDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CaiDatActivity.class);
                startActivity(intent);
            }
        });

    }

}