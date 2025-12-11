package com.example.learningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// Import thư viện PDF
import com.github.barteksc.pdfviewer.PDFView;

public class ChiTietBaiActivity extends AppCompatActivity {

    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_bai);

        // --- CÀI ĐẶT THANH TIÊU ĐỀ & NÚT BACK ---
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Nội dung bài học"); // Tiêu đề mặc định
        }

        pdfView = findViewById(R.id.pdfView);

        // Nhận dữ liệu từ màn hình trước
        Intent intent = getIntent();
        String tenBai = intent.getStringExtra("TEN_BAI");
        int pdfResourceId = intent.getIntExtra("PDF_RESOURCE_ID", -1);

        // Cập nhật tiêu đề theo tên bài
        if (getSupportActionBar() != null && tenBai != null) {
            getSupportActionBar().setTitle(tenBai);
        }

        // --- HIỂN THỊ PDF ---
        if (pdfResourceId != -1) {
            // Lệnh này đọc file PDF từ res/raw và hiển thị luôn
            pdfView.fromStream(getResources().openRawResource(pdfResourceId))
                    .enableSwipe(true) // Cho phép vuốt trang
                    .swipeHorizontal(false) // Vuốt dọc (false) hay ngang (true)
                    .enableDoubletap(true) // Cho phép nhấp đúp để phóng to
                    .load();
        } else {
            Toast.makeText(this, "Lỗi: Không tìm thấy file bài học!", Toast.LENGTH_SHORT).show();
        }
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