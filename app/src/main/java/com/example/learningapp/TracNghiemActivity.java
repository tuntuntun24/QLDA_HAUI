package com.example.learningapp;

import android.os.Bundle;
import android.view.MenuItem; // <--- Quan trọng
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class TracNghiemActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radB;
    Button btnNopBai;
    TextView tvKetQua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trac_nghiem);

        // --- CÀI ĐẶT THANH TIÊU ĐỀ & NÚT BACK ---
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Làm bài trắc nghiệm");
        }

        radioGroup = findViewById(R.id.radioGroup);
        radB = findViewById(R.id.radB);
        btnNopBai = findViewById(R.id.btnNopBai);
        tvKetQua = findViewById(R.id.tvKetQua);

        btnNopBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(TracNghiemActivity.this, "Vui lòng chọn 1 đáp án!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (radB.isChecked()) {
                    tvKetQua.setText("Chính xác! +10 điểm");
                    tvKetQua.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                } else {
                    tvKetQua.setText("Sai rồi! Đáp án đúng là B");
                    tvKetQua.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                }
            }
        });
    }

    // --- XỬ LÝ NÚT BACK ---
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}