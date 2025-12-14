package com.example.learningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.github.barteksc.pdfviewer.PDFView; // Thư viện PDF
import java.io.InputStream;

public class ChiTietBaiActivity extends AppCompatActivity {

    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_bai);

        pdfView = findViewById(R.id.pdfView);

        // 1. Nhận dữ liệu
        Intent intent = getIntent();
        String tenBaiHoc = intent.getStringExtra("TEN_BAI_HOC");
        String filePdfName = intent.getStringExtra("FILE_PDF"); // Ví dụ: "bai1.pdf"

        // 2. Cài đặt Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (tenBaiHoc != null && !tenBaiHoc.isEmpty()) {
                getSupportActionBar().setTitle(tenBaiHoc);
            } else {
                getSupportActionBar().setTitle(R.string.title_chi_tiet_bai);
            }
        }

        // 3. Load File PDF từ res/raw
        loadPdfFromRaw(filePdfName);
    }

    private void loadPdfFromRaw(String pdfFileName) {
        try {
            // Bước 1: Cắt đuôi ".pdf" đi để lấy tên tài nguyên (VD: "bai1.pdf" -> "bai1")
            String resourceName = pdfFileName.replace(".pdf", "");

            // Bước 2: Tìm ID của file trong thư mục raw dựa vào tên
            int resId = getResources().getIdentifier(resourceName, "raw", getPackageName());

            // Nếu tìm thấy file (ID khác 0)
            if (resId != 0) {
                // Bước 3: Mở dòng dữ liệu (Stream) từ raw
                InputStream inputStream = getResources().openRawResource(resId);

                // Bước 4: Load PDF từ Stream
                pdfView.fromStream(inputStream)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .defaultPage(0)
                        .load();
            } else {
                Toast.makeText(this, "Lỗi: Không tìm thấy file " + pdfFileName + " trong res/raw", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi đọc file PDF", Toast.LENGTH_SHORT).show();
        }
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