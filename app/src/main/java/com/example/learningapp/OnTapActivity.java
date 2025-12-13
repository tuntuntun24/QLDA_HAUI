package com.example.learningapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class OnTapActivity extends AppCompatActivity {

    Button btnKttx1, btnKttx2, btnKthp;
    TextView tvDiem1, tvDiem2, tvDiem3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_tap);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Ôn tập & Kiểm tra");
        }

        // Ánh xạ
        btnKttx1 = findViewById(R.id.btnKttx1);
        btnKttx2 = findViewById(R.id.btnKttx2);
        btnKthp = findViewById(R.id.btnKthp);

        tvDiem1 = findViewById(R.id.tvDiemKttx1);
        tvDiem2 = findViewById(R.id.tvDiemKttx2);
        tvDiem3 = findViewById(R.id.tvDiemKthp);

        // Xử lý sự kiện bấm nút
        btnKttx1.setOnClickListener(v -> moManHinhThi("KEY_KTTX1"));
        btnKttx2.setOnClickListener(v -> moManHinhThi("KEY_KTTX2"));
        btnKthp.setOnClickListener(v -> moManHinhThi("KEY_KTHP"));
    }

    // Hàm mở màn hình thi và gửi kèm "Mã đề"
    private void moManHinhThi(String maDeThi) {
        Intent intent = new Intent(OnTapActivity.this, TracNghiemActivity.class);
        intent.putExtra("MA_DE_THI", maDeThi);
        startActivity(intent);
    }

    // Hàm này tự chạy mỗi khi quay lại màn hình này -> Để cập nhật điểm mới nhất
    @Override
    protected void onResume() {
        super.onResume();
        capNhatBangDiem();
    }

    private void capNhatBangDiem() {
        SharedPreferences prefs = getSharedPreferences("LUU_DIEM_SO", Context.MODE_PRIVATE);

        // Lấy điểm, mặc định là -1 nếu chưa thi
        int d1 = prefs.getInt("KEY_KTTX1", -1);
        int d2 = prefs.getInt("KEY_KTTX2", -1);
        int d3 = prefs.getInt("KEY_KTHP", -1);

        tvDiem1.setText(d1 == -1 ? "Chưa thi" : d1 + " điểm");
        tvDiem2.setText(d2 == -1 ? "Chưa thi" : d2 + " điểm");
        tvDiem3.setText(d3 == -1 ? "Chưa thi" : d3 + " điểm");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}