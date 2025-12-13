package com.example.learningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class DanhSachDeActivity extends AppCompatActivity {

    ListView lvDeThi;
    ArrayList<String> arrDeThi = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Tái sử dụng layout của activity_danh_sach_bai vì cấu trúc y hệt (ListView)
        setContentView(R.layout.activity_danh_sach_bai);

        Intent intent = getIntent();
        int viTriChuong = intent.getIntExtra("VITRI_CHUONG", 0);
        String tenChuong = intent.getStringExtra("TEN_CHUONG");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(tenChuong);
        }

        // Tìm ListView (Lưu ý: layout activity_danh_sach_bai có id là lvBaiHoc)
        lvDeThi = findViewById(R.id.lvBaiHoc);

        // Tạo dữ liệu giả lập (Mỗi chương có 3 đề ôn tập)
        taoDuLieuDeThi(viTriChuong);

        // Adapter hiển thị
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.item_bai_hoc, // Dùng lại item đẹp
                arrDeThi
        );
        lvDeThi.setAdapter(adapter);

        // Bấm vào Đề -> Vào làm Trắc Nghiệm
        lvDeThi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(DanhSachDeActivity.this, TracNghiemActivity.class);
                // Bạn có thể gửi thêm dữ liệu để biết đang làm đề nào
                startActivity(i);
            }
        });
    }

    private void taoDuLieuDeThi(int viTriChuong) {
        arrDeThi.clear();
        // Giả sử mỗi chương có 3 đề ôn tập
        int chuongSo = viTriChuong + 1;
        arrDeThi.add("Đề ôn tập số 1 - Chương " + chuongSo);
        arrDeThi.add("Đề ôn tập số 2 - Chương " + chuongSo);
        arrDeThi.add("Đề ôn tập số 3 - Chương " + chuongSo);
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