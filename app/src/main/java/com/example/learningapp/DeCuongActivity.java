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
            "Bài 1: Giới thiệu nhập môn",
            "Bài 2: Quy trình phần mềm",
            "Bài 3: Phân tích yêu cầu",
            "Bài 4: Thiết kế hệ thống",
            "Bài 5: Kiểm thử phần mềm"
    };

    // Mảng chứa ID tài nguyên PDF tương ứng
    int[] pdfResources = {
            R.raw.bai1, // ID của res/raw/bai1.pdf
            R.raw.bai2, // ID của res/raw/bai2.pdf
            R.raw.bai3, // ID của res/raw/bai3.pdf
            R.raw.bai4, // ID của res/raw/bai4.pdf
            R.raw.bai5  // ID của res/raw/bai5.pdf
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

        lvBaiHoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy ID tài nguyên PDF tương ứng
                int pdfResourceId = pdfResources[position];

                // Gửi ID tài nguyên sang ChiTietBaiActivity để mở PDF
                Intent intent = new Intent(DeCuongActivity.this, ChiTietBaiActivity.class);
                intent.putExtra("TEN_BAI", danhSachBai[position]);
                intent.putExtra("PDF_RESOURCE_ID", pdfResourceId); // Gửi ID PDF
                startActivity(intent);
            }
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