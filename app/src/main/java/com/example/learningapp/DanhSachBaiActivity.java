package com.example.learningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class DanhSachBaiActivity extends AppCompatActivity {

    ListView lvBaiHoc;
    // Danh sách hiển thị lên màn hình (Text)
    ArrayList<String> arrHienThi = new ArrayList<>();
    // Danh sách file PDF tương ứng
    ArrayList<String> arrFilePdf = new ArrayList<>();

    int viTriChuong = 0; // Biến lưu vị trí chương

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sử dụng layout de_cuong (chỉ chứa 1 listview)
        setContentView(R.layout.activity_de_cuong);

        // --- 1. NHẬN DỮ LIỆU TỪ MÀN HÌNH CHƯƠNG ---
        Intent intent = getIntent();
        // Lưu ý: Gán vào biến toàn cục (không khai báo int viTriChuong mới ở đây)
        viTriChuong = intent.getIntExtra("VITRI_CHUONG", 0);
        String tenChuong = intent.getStringExtra("TEN_CHUONG");

        // --- 2. CẤU HÌNH THANH TIÊU ĐỀ ---
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (tenChuong != null) {
                getSupportActionBar().setTitle(tenChuong);
            }
        }

        lvBaiHoc = findViewById(R.id.lvBaiHoc);

        // --- 3. TẠO DỮ LIỆU ---
        taoDuLieuBaiHoc(viTriChuong);

        // --- 4. GÁN ADAPTER ---
        MyAdapter adapter = new MyAdapter(this, R.layout.item_bai_hoc, arrHienThi);
        lvBaiHoc.setAdapter(adapter);

        // --- 5. XỬ LÝ SỰ KIỆN CLICK ---
        lvBaiHoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Logic: Vị trí chẵn (0, 2...) là Đề cương, Lẻ (1, 3...) là Trắc nghiệm
                if (position % 2 == 0) {
                    // === CLICK VÀO ĐỀ CƯƠNG (PDF) ===
                    int pdfIndex = position / 2;

                    if (pdfIndex < arrFilePdf.size()) {
                        Intent i = new Intent(DanhSachBaiActivity.this, ChiTietBaiActivity.class);

                        // Gửi tên file PDF (Key phải khớp với ChiTietBaiActivity)
                        i.putExtra("FILE_PDF", arrFilePdf.get(pdfIndex));

                        // Gửi tiêu đề hiển thị
                        String tieuDeCanHien = arrHienThi.get(position);
                        i.putExtra("TEN_BAI_HOC", tieuDeCanHien);

                        startActivity(i);
                    }
                } else {
                    // === CLICK VÀO TRẮC NGHIỆM ===
                    Intent i = new Intent(DanhSachBaiActivity.this, TracNghiemActivity.class);

                    // Tính số bài: Chương 0 -> Bài 1
                    int soBai = viTriChuong + 1;

                    // Gửi mã đề thi đặc biệt. Ví dụ: "LESSON_1"
                    // Bên file TracNghiemActivity cần xử lý mã này để load câu hỏi tương ứng
                    i.putExtra("MA_DE_THI", "LESSON_" + soBai);

                    startActivity(i);
                }
            }
        });
    }

    // --- HÀM TẠO DỮ LIỆU ---
    private void taoDuLieuBaiHoc(int viTriChuong) {
        arrHienThi.clear();
        arrFilePdf.clear();

        // Quy tắc: Chương 1 (index 0) -> Bài 1
        int soBai = viTriChuong + 1;

        // Tạo tên file PDF: bai1.pdf, bai2.pdf... (Đảm bảo file này có trong thư mục assets)
        String tenFilePdf = "bai" + soBai + ".pdf";

        // Thêm vào danh sách (Sử dụng Resource String để đa ngôn ngữ)

        // Dòng 1: Đề cương (Sử dụng chuỗi từ strings.xml + số bài)
        String textDeCuong = getString(R.string.txt_de_cuong_bai) + " " + soBai;
        arrHienThi.add(textDeCuong);
        arrFilePdf.add(tenFilePdf);

        // Dòng 2: Trắc nghiệm
        String textTracNghiem = getString(R.string.txt_trac_nghiem_bai) + " " + soBai;
        arrHienThi.add(textTracNghiem);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // --- ADAPTER TÙY CHỈNH (GIỮ NGUYÊN) ---
    class MyAdapter extends ArrayAdapter<String> {
        AppCompatActivity context;
        int resource;
        ArrayList<String> data;

        public MyAdapter(AppCompatActivity context, int resource, ArrayList<String> data) {
            super(context, resource, data);
            this.context = context;
            this.resource = resource;
            this.data = data;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View v = inflater.inflate(resource, null);
            TextView tvTen = v.findViewById(R.id.tvTenBai); // Đảm bảo ID này đúng trong item_bai_hoc.xml

            // Nếu item_bai_hoc.xml chỉ là TextView thì dùng dòng dưới:
            if (tvTen == null && v instanceof TextView) {
                tvTen = (TextView) v;
            }

            String text = data.get(position);
            if (tvTen != null) {
                tvTen.setText(text);
            }

            return v;
        }
    }
}