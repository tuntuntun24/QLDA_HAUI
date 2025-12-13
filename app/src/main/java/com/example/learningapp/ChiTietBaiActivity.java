package com.example.learningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

public class ChiTietBaiActivity extends AppCompatActivity {

    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_bai);

        pdfView = findViewById(R.id.pdfView);

        // --- NHẬN DỮ LIỆU TỪ INTENT ---
        Intent intent = getIntent();
        String tenFilePdf = intent.getStringExtra("pdfFileName");
        String tieuDe = intent.getStringExtra("TIEU_DE_ACTIONBAR");

        // --- CÀI ĐẶT THANH TIÊU ĐỀ (ACTION BAR) ---
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // Nếu có tiêu đề gửi sang (ví dụ: "Đề cương bài 1") thì dùng nó
            if (tieuDe != null && !tieuDe.isEmpty()) {
                getSupportActionBar().setTitle(tieuDe);
            } else {
                // Nếu không có thì hiện mặc định
                getSupportActionBar().setTitle("Nội dung bài học");
            }
        }

        // --- XỬ LÝ HIỂN THỊ PDF ---
        if (tenFilePdf != null && !tenFilePdf.isEmpty()) {
            hienThiPdfTuTenFile(tenFilePdf);
        } else {
            // Fallback code cũ (để phòng hờ)
            int pdfResourceId = intent.getIntExtra("PDF_RESOURCE_ID", -1);
            if (pdfResourceId != -1) {
                hienThiPdfTuId(pdfResourceId);
            }
        }
    }

    private void hienThiPdfTuTenFile(String tenFileCuaBan) {
        try {
            String tenResource = tenFileCuaBan.replace(".pdf", "");
            int resId = getResources().getIdentifier(tenResource, "raw", getPackageName());

            if (resId != 0) {
                hienThiPdfTuId(resId);
                // Lưu ý: Mình đã XÓA đoạn code tự đổi tên tiêu đề ở đây
                // để nó không ghi đè lên tiêu đề "Đề cương bài 1" của bạn.
            } else {
                Toast.makeText(this, "Không tìm thấy file: " + tenFileCuaBan, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hienThiPdfTuId(int idTaiNguyen) {
        pdfView.fromStream(getResources().openRawResource(idTaiNguyen))
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .load();
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