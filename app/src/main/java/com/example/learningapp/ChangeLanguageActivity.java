package com.example.learningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;

public class ChangeLanguageActivity extends AppCompatActivity {

    RadioButton rbVi, rbEn;
    Button btnSave;
    LanguageManager langManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);

        // --- CẤU HÌNH ACTION BAR ---
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Hiện nút Back
            getSupportActionBar().setTitle(R.string.tieu_de_doi_ngon_ngu); // Set tiêu đề từ string
        }

        // --- ÁNH XẠ ---
        rbVi = findViewById(R.id.rbVi);
        rbEn = findViewById(R.id.rbEn);
        btnSave = findViewById(R.id.btnSaveLang);

        langManager = new LanguageManager(this);

        // Kiểm tra ngôn ngữ hiện tại để tích vào RadioButton đúng
        if (langManager.getLang().equals("en")) {
            rbEn.setChecked(true);
        } else {
            rbVi.setChecked(true);
        }

        // --- SỰ KIỆN LƯU ---
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbVi.isChecked()) {
                    langManager.updateResource("vi");
                } else if (rbEn.isChecked()) {
                    langManager.updateResource("en");
                }

                // Khởi động lại App về màn hình chính để cập nhật toàn bộ ngôn ngữ
                Intent i = new Intent(ChangeLanguageActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
    }

    // --- XỬ LÝ NÚT BACK TRÊN ACTION BAR ---
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Đóng màn hình này quay lại Cài đặt
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}