package com.example.learningapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

// Import thư viện biểu đồ
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ChiTietOnTapActivity extends AppCompatActivity {

    TextView tvTenBaiThi;
    Button btnVaoThi;
    BarChart chartLichSu;
    String maDeThi = ""; // Ví dụ: KEY_KTTX1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_on_tap);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_chi_tiet); // Đổi tiêu đề Action Bar
        }

        // Nhận mã đề từ màn hình trước
        Intent intent = getIntent();
        maDeThi = intent.getStringExtra("MA_DE_THI");
        String tenHienThi = intent.getStringExtra("TEN_HIEN_THI");

        // Ánh xạ
        tvTenBaiThi = findViewById(R.id.tvTenBaiThi);
        btnVaoThi = findViewById(R.id.btnVaoThi);
        chartLichSu = findViewById(R.id.chartLichSu);

        tvTenBaiThi.setText(tenHienThi);

        // Sự kiện nút làm bài -> Chuyển sang trắc nghiệm
        btnVaoThi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChiTietOnTapActivity.this, TracNghiemActivity.class);
                i.putExtra("MA_DE_THI", maDeThi);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Mỗi lần quay lại màn hình này thì vẽ lại biểu đồ để cập nhật điểm mới nhất
        veBieuDoLichSu();
    }

    private void veBieuDoLichSu() {
        SharedPreferences prefs = getSharedPreferences("LUU_DIEM_SO", Context.MODE_PRIVATE);
        String historyString = prefs.getString("HISTORY_" + maDeThi, "");

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        if (!historyString.isEmpty()) {
            String[] diemSo = historyString.split(",");
            for (int i = 0; i < diemSo.length; i++) {
                try {
                    float diem = Float.parseFloat(diemSo[i]);
                    entries.add(new BarEntry(i, diem));

                    // --- SỬA CHỖ NÀY: Thay "Lần " cứng bằng getString(...) ---
                    // Cũ: labels.add("Lần " + (i + 1));
                    labels.add(getString(R.string.label_lan_thi) + " " + (i + 1));

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        // --- SỬA CHỖ NÀY: Thay tiêu đề cứng bằng getString(...) ---
        // Cũ: BarDataSet dataSet = new BarDataSet(entries, "Điểm số qua các lần thi");
        BarDataSet dataSet = new BarDataSet(entries, getString(R.string.label_bieu_do_chart));

        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(14f);
        dataSet.setValueTextColor(Color.BLACK);

        BarData barData = new BarData(dataSet);
        chartLichSu.setData(barData);
        chartLichSu.getDescription().setEnabled(false);
        chartLichSu.animateY(1000);

        XAxis xAxis = chartLichSu.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.size());

        chartLichSu.invalidate();
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