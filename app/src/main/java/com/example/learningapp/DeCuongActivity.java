package com.example.learningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class DeCuongActivity extends AppCompatActivity {

    ListView lvBaiHoc;
    String[] danhSachChuong; // Chỉ khai báo, chưa gán dữ liệu cứng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_de_cuong);

        // 1. ĐỔI TÊN ACTION BAR THEO NGÔN NGỮ
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_de_cuong); // Dùng R.string
        }

        lvBaiHoc = findViewById(R.id.lvBaiHoc);

        // 2. LẤY DỮ LIỆU TỪ STRINGS.XML (Để tự dịch)
        danhSachChuong = getResources().getStringArray(R.array.arr_de_cuong);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.item_bai_hoc,
                danhSachChuong
        );
        lvBaiHoc.setAdapter(adapter);

        // Sự kiện click giữ nguyên logic cũ
        lvBaiHoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DeCuongActivity.this, DanhSachBaiActivity.class);
                intent.putExtra("VITRI_CHUONG", position);
                // Gửi tên chương (đã dịch) sang
                intent.putExtra("TEN_CHUONG", danhSachChuong[position]);
                startActivity(intent);
            }
        });
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