package com.example.learningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem; // <--- Quan trọng
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class OnTapActivity extends AppCompatActivity {

    BarChart chartBieuDo;
    Button btnLamBai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_tap);

        // --- CÀI ĐẶT THANH TIÊU ĐỀ & NÚT BACK ---
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Ôn tập & Kiểm tra");
        }

        chartBieuDo = findViewById(R.id.chartBieuDo);
        btnLamBai = findViewById(R.id.btnLamBai);

        veBieuDo();

        btnLamBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnTapActivity.this, TracNghiemActivity.class);
                startActivity(intent);
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

    private void veBieuDo() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 5f));
        entries.add(new BarEntry(1, 7f));
        entries.add(new BarEntry(2, 9f));

        BarDataSet dataSet = new BarDataSet(entries, "Điểm số qua các lần thi");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(14f);

        BarData barData = new BarData(dataSet);
        chartBieuDo.setData(barData);
        chartBieuDo.getDescription().setEnabled(false);
        chartBieuDo.animateY(1000);

        String[] labels = {"Lần 1", "Lần 2", "Lần 3"};
        XAxis xAxis = chartBieuDo.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);

        chartBieuDo.invalidate();
    }
}