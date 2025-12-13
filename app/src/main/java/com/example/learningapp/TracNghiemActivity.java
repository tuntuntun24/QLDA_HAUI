package com.example.learningapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class TracNghiemActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radB; // Giả sử đáp án B là đúng
    Button btnNopBai;
    TextView tvKetQua;

    String maDeThi = ""; // Lưu mã đề (KTTX1, KTTX2...) được gửi sang

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trac_nghiem);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Làm bài thi");
        }

        // Nhận mã đề thi từ màn hình OnTap gửi sang
        Intent intent = getIntent();
        maDeThi = intent.getStringExtra("MA_DE_THI");

        radioGroup = findViewById(R.id.radioGroup);
        radB = findViewById(R.id.radB);
        btnNopBai = findViewById(R.id.btnNopBai);
        tvKetQua = findViewById(R.id.tvKetQua);

        btnNopBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(TracNghiemActivity.this, "Vui lòng chọn đáp án!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int diemSo = 0;
                if (radB.isChecked()) {
                    diemSo = 10; // Giả sử đúng được 10 điểm
                    tvKetQua.setText("Chính xác! Bạn được 10 điểm");
                    tvKetQua.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                } else {
                    diemSo = 0;
                    tvKetQua.setText("Sai rồi! Bạn được 0 điểm");
                    tvKetQua.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                }

                // --- LƯU ĐIỂM VÀO BỘ NHỚ ---
                luuDiem(diemSo);

                // Khóa nút nộp bài lại
                btnNopBai.setEnabled(false);
            }
        });
    }

    private void luuDiem(int diem) {
        if(maDeThi != null && !maDeThi.isEmpty()) {
            SharedPreferences prefs = getSharedPreferences("LUU_DIEM_SO", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            // Lưu điểm với cái tên là Mã đề thi (KEY_KTTX1, KEY_KTTX2...)
            editor.putInt(maDeThi, diem);
            editor.apply();

            Toast.makeText(this, "Đã lưu kết quả thi!", Toast.LENGTH_SHORT).show();
        }
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