package com.example.learningapp;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem; // <--- Quan trọng
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class NhacHenActivity extends AppCompatActivity {

    ListView lvNhacHen;
    Button btnAddTimer;
    ArrayList<String> listGioHen;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhac_hen);

        // --- CÀI ĐẶT THANH TIÊU ĐỀ & NÚT BACK ---
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Nhắc hẹn");
        }

        lvNhacHen = findViewById(R.id.lvNhacHen);
        btnAddTimer = findViewById(R.id.btnAddTimer);

        listGioHen = new ArrayList<>();
        docDanhSach(); // Đọc dữ liệu cũ

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listGioHen);
        lvNhacHen.setAdapter(adapter);

        btnAddTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hienDongHoChonGio();
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

    private void hienDongHoChonGio() {
        Calendar calendar = Calendar.getInstance();
        int gioHienTai = calendar.get(Calendar.HOUR_OF_DAY);
        int phutHienTai = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String gioString = String.format("%02d:%02d", hourOfDay, minute);
                listGioHen.add(gioString);
                adapter.notifyDataSetChanged();
                luuDanhSach(); // Lưu ngay khi thêm
            }
        }, gioHienTai, phutHienTai, true);
        timePickerDialog.show();
    }

    private void luuDanhSach() {
        StringBuilder sb = new StringBuilder();
        for (String s : listGioHen) {
            sb.append(s).append(",");
        }
        SharedPreferences sharedPreferences = getSharedPreferences("DATA_HEN_GIO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("LIST_GIO", sb.toString());
        editor.apply();
    }

    private void docDanhSach() {
        SharedPreferences sharedPreferences = getSharedPreferences("DATA_HEN_GIO", Context.MODE_PRIVATE);
        String chuoiDaLuu = sharedPreferences.getString("LIST_GIO", "");
        if (!chuoiDaLuu.isEmpty()) {
            String[] mangGio = chuoiDaLuu.split(",");
            for (String gio : mangGio) {
                if(!gio.trim().isEmpty()) listGioHen.add(gio);
            }
        } else {
            listGioHen.add("08:00 (Mẫu)");
        }
    }
}