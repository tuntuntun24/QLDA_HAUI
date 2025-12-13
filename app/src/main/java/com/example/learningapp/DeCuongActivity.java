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

    // Dữ liệu danh sách các Chương (Thay vì Bài như cũ)
    String[] danhSachChuong = {
            "Chương 1: Tổng quan quản lý dự án",
            "Chương 2: Quản lý yêu cầu",
            "Chương 3: Lập kế hoạch dự án",
            "Chương 4: Phát triển sản phẩm",
            "Chương 5: Lập lịch dự án",
            "Chương 6: Thực hiện dự án",
            "Chương 7: Kết thúc dự án",
            "Chương 8: Báo cáo bài tập lớn"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_de_cuong);

        // --- CÀI ĐẶT THANH TIÊU ĐỀ & NÚT BACK ---
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Đề cương môn học");
        }

        // --- KHỞI TẠO LISTVIEW ---
        lvBaiHoc = findViewById(R.id.lvBaiHoc);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.item_bai_hoc, // Sử dụng lại giao diện item cũ
                danhSachChuong
        );
        lvBaiHoc.setAdapter(adapter);

        // --- SỰ KIỆN KHI BẤM VÀO 1 CHƯƠNG ---
        lvBaiHoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Mở màn hình DanhSachBaiActivity (Màn hình trung gian mới)
                Intent intent = new Intent(DeCuongActivity.this, DanhSachBaiActivity.class);

                // Gửi vị trí chương (0, 1, 2...) để bên kia biết load dữ liệu bài nào
                intent.putExtra("VITRI_CHUONG", position);

                // Gửi tên chương sang để làm tiêu đề
                intent.putExtra("TEN_CHUONG", danhSachChuong[position]);

                startActivity(intent);
            }
        });
    }

    // --- XỬ LÝ KHI BẤM NÚT BACK TRÊN THANH TIÊU ĐỀ ---
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Đóng màn hình này
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}