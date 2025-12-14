package com.example.learningapp;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class NhacHenActivity extends AppCompatActivity {

    ListView lvNhacHen;
    Button btnThemHen;
    ArrayList<String> arrHenGio = new ArrayList<>();
    HenGioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhac_hen);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_nhac_hen);
        }

        lvNhacHen = findViewById(R.id.lvNhacHen);
        btnThemHen = findViewById(R.id.btnThemHen);

        docDuLieu();

        // Sử dụng Adapter tùy chỉnh
        adapter = new HenGioAdapter(this, R.layout.item_nhac_hen, arrHenGio);
        lvNhacHen.setAdapter(adapter);

        // Sự kiện Thêm
        btnThemHen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hienThiDialogNhapThongTin(-1);
            }
        });

        // Sự kiện Sửa
        lvNhacHen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hienThiDialogNhapThongTin(position);
            }
        });

        // Sự kiện Xóa
        lvNhacHen.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                xacNhanXoa(position);
                return true;
            }
        });
    }

    // --- HÀM HIỂN THỊ DIALOG CÓ CHỌN GIỜ ---
    private void hienThiDialogNhapThongTin(final int viTriCanSua) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (viTriCanSua == -1) builder.setTitle(getString(R.string.dialog_them_tieu_de));
        else builder.setTitle(getString(R.string.dialog_sua_tieu_de));

        // 1. Tạo Layout chứa các view (EditText + Button chọn giờ)
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 20, 30, 20);

        // 2. Ô nhập nội dung
        final EditText edtNoiDung = new EditText(this);
        edtNoiDung.setHint(getString(R.string.hint_nhap_noi_dung));
        layout.addView(edtNoiDung);

        // 3. TextView hiển thị giờ đã chọn
        final TextView tvGioDaChon = new TextView(this);
        tvGioDaChon.setTextSize(16);
        tvGioDaChon.setPadding(0, 20, 0, 0);

        // Mặc định lấy giờ hiện tại
        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        tvGioDaChon.setText(getString(R.string.label_gio_hen) + timeFormat.format(calendar.getTime())); // "Giờ hẹn: 08:30"

        layout.addView(tvGioDaChon);

        // 4. Nút Chọn giờ
        Button btnChonGio = new Button(this);
        btnChonGio.setText(getString(R.string.btn_chon_gio));
        btnChonGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiện TimePickerDialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(NhacHenActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                tvGioDaChon.setText(getString(R.string.label_gio_hen) + timeFormat.format(calendar.getTime()));
                            }
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true); // true = chế độ 24h
                timePickerDialog.show();
            }
        });
        layout.addView(btnChonGio);

        // --- XỬ LÝ DỮ LIỆU CŨ NẾU ĐANG SỬA ---
        if (viTriCanSua != -1) {
            String duLieuCu = arrHenGio.get(viTriCanSua);
            // Định dạng lưu: "[HH:mm] Nội dung"
            // Tách giờ và nội dung ra
            try {
                int indexKetThucGio = duLieuCu.indexOf("] ");
                if (indexKetThucGio != -1) {
                    String gioCu = duLieuCu.substring(1, indexKetThucGio); // Lấy "HH:mm"
                    String noiDungCu = duLieuCu.substring(indexKetThucGio + 2); // Lấy phần nội dung

                    edtNoiDung.setText(noiDungCu);
                    tvGioDaChon.setText(getString(R.string.label_gio_hen) + gioCu);

                    // Cập nhật lại calendar để nếu người dùng không chọn lại giờ thì vẫn giữ giờ cũ
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(gioCu.split(":")[0]));
                    calendar.set(Calendar.MINUTE, Integer.parseInt(gioCu.split(":")[1]));
                } else {
                    edtNoiDung.setText(duLieuCu);
                }
            } catch (Exception e) {
                edtNoiDung.setText(duLieuCu);
            }
        }

        builder.setView(layout);

        // Nút Lưu
        builder.setPositiveButton(getString(R.string.btn_luu), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String noiDung = edtNoiDung.getText().toString().trim();
                String gio = timeFormat.format(calendar.getTime());

                if (!noiDung.isEmpty()) {
                    // Ghép chuỗi: "[HH:mm] Nội dung"
                    String ketQua = "[" + gio + "] " + noiDung;

                    if (viTriCanSua == -1) {
                        arrHenGio.add(ketQua);
                        Toast.makeText(NhacHenActivity.this, getString(R.string.msg_da_them), Toast.LENGTH_SHORT).show();
                    } else {
                        arrHenGio.set(viTriCanSua, ketQua);
                        Toast.makeText(NhacHenActivity.this, getString(R.string.msg_da_cap_nhat), Toast.LENGTH_SHORT).show();
                    }

                    // Sắp xếp danh sách theo giờ tăng dần
                    Collections.sort(arrHenGio);

                    luuDuLieu();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        builder.setNegativeButton(getString(R.string.btn_huy), null);
        builder.show();
    }

    private void xacNhanXoa(final int position) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.btn_xoa))
                .setMessage(getString(R.string.msg_xac_nhan_xoa))
                .setPositiveButton(getString(R.string.btn_xoa), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        arrHenGio.remove(position);
                        luuDuLieu();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(NhacHenActivity.this, getString(R.string.msg_da_xoa), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(getString(R.string.btn_huy), null)
                .show();
    }

    private void luuDuLieu() {
        SharedPreferences prefs = getSharedPreferences("NHAC_HEN_DATA_V3", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        StringBuilder sb = new StringBuilder();
        for (String s : arrHenGio) sb.append(s).append("\n");
        editor.putString("KEY_LIST_STRING", sb.toString());
        editor.apply();
    }

    private void docDuLieu() {
        arrHenGio.clear();
        SharedPreferences prefs = getSharedPreferences("NHAC_HEN_DATA_V3", Context.MODE_PRIVATE);
        String data = prefs.getString("KEY_LIST_STRING", "");
        if (!data.isEmpty()) {
            String[] items = data.split("\n");
            for (String item : items) {
                if (!item.trim().isEmpty()) arrHenGio.add(item);
            }
        }
        Collections.sort(arrHenGio); // Sắp xếp lại khi load
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class HenGioAdapter extends ArrayAdapter<String> {
        Context context;
        int resource;
        ArrayList<String> data;

        public HenGioAdapter(Context context, int resource, ArrayList<String> data) {
            super(context, resource, data);
            this.context = context;
            this.resource = resource;
            this.data = data;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(resource, parent, false);
            }
            TextView tv = convertView.findViewById(R.id.tvNoiDungHen);

            // Dữ liệu dạng: "[08:30] Đi học"
            String itemText = data.get(position);
            tv.setText(itemText);

            return convertView;
        }
    }
}