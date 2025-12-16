package com.example.learningapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar; // Nhớ import cái này
import android.view.MenuItem;

public class DarkModeActivity extends AppCompatActivity {

    Switch switchDarkMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean isNightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dark_mode);

//        // --- PHẦN MỚI THÊM: Cấu hình Toolbar ---
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        // Hiện nút Back và đặt Tiêu đề
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Hiện mũi tên
//            getSupportActionBar().setTitle("Cài đặt giao diện"); // Đặt tên ở đây
//        }
        // --- CẤU HÌNH ACTION BAR ---
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Hiện nút Back
            getSupportActionBar().setTitle(R.string.tieu_de_doi_nen_sang_toi); // Set tiêu đề từ string
        }
        // ---------------------------------------

        switchDarkMode = findViewById(R.id.switchDarkMode);
        sharedPreferences = getSharedPreferences("AppSettingPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        isNightMode = sharedPreferences.getBoolean("NightMode", false);
        switchDarkMode.setChecked(isNightMode);

        switchDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("NightMode", true);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("NightMode", false);
                }
                editor.apply();
            }
        });
    }

    // --- PHẦN MỚI THÊM: Xử lý khi bấm nút Back trên Toolbar ---
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Đóng Activity này để quay lại màn hình trước
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}