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

    // Dữ liệu mẫu
    String[] danhSachBai = {
            "Bài 1: Tổng quan quản lý dự án",
            "Bài 2: Quản lý yêu cầu",
            "Bài 3: Lập kế hoạch dự án dựa trên agile",
            "Bài 4: Phát triển sản phẩm",
            "Bài 5: Lập lịch dự án",
            "Bài 6: Thực hiện dự án",
            "Bài 7: Kết thúc dự án",
            "Bài 8: Mẫu báo cáo bài tập lớn"
    };

    // Mảng chứa ID tài nguyên PDF tương ứng
    int[] pdfResources = {
            R.raw.bai1, // ID của res/raw/bai1.pdf
            R.raw.bai2, // ID của res/raw/bai2.pdf
            R.raw.bai3, // ID của res/raw/bai3.pdf
            R.raw.bai4, // ID của res/raw/bai4.pdf
            R.raw.bai5,  // ID của res/raw/bai5.pdf
            R.raw.bai6, // ID của res/raw/bai3.pdf
            R.raw.bai7, // ID của res/raw/bai4.pdf
            R.raw.bai8  // ID của res/raw/bai5.pdf
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_de_cuong);

        // --- CÀI ĐẶT THANH TIÊU ĐỀ & NÚT BACK ---
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Đề cương bài giảng");
        }

        // --- CODE LOGIC LISTVIEW ---
        lvBaiHoc = findViewById(R.id.lvBaiHoc);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.item_bai_hoc, // Giao diện hộp bo tròn
                danhSachBai
        );
        lvBaiHoc.setAdapter(adapter);

        lvBaiHoc.setOnItemClickListener((parent, view, position, id) -> {
            // 1. Lấy ID tài nguyên PDF tương ứng (Mình đã bỏ comment dòng này để code chạy được)
            int pdfResourceId = pdfResources[position];

            // 2. Gửi ID tài nguyên sang ChiTietBaiActivity để mở PDF
            Intent intent = new Intent(DeCuongActivity.this, ChiTietBaiActivity.class);
            intent.putExtra("TEN_BAI", danhSachBai[position]);
            intent.putExtra("PDF_RESOURCE_ID", pdfResourceId);
            startActivity(intent);
        });
    }

    // --- XỬ LÝ KHI BẤM NÚT BACK ---
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}