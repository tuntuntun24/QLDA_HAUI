package com.example.learningapp;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

// Import thư viện JSON có sẵn của Android để lưu dữ liệu phức tạp
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class NhacHenActivity extends AppCompatActivity {

    ListView lvNhacHen;
    Button btnThemHen;

    ArrayList<HenGio> arrHenGio = new ArrayList<>();
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhac_hen);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Nhắc hẹn học tập");
        }

        lvNhacHen = findViewById(R.id.lvNhacHen);
        btnThemHen = findViewById(R.id.btnThemHen);

        // 1. ĐỌC DỮ LIỆU CŨ TỪ BỘ NHỚ LÊN
        docDuLieu();

        // Nếu lần đầu chưa có gì thì tạo mẫu cho đẹp (Optional)
        if(arrHenGio.isEmpty()) {
            arrHenGio.add(new HenGio("08:00", "Ví dụ: Lên lớp đúng giờ", true));
        }

        adapter = new MyAdapter(this, R.layout.item_nhac_hen, arrHenGio);
        lvNhacHen.setAdapter(adapter);

        // --- SỰ KIỆN 1: THÊM MỚI ---
        btnThemHen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hienThiDialogNhapThongTin(-1);
            }
        });

        // --- SỰ KIỆN 2: SỬA ---
        lvNhacHen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hienThiDialogNhapThongTin(position);
            }
        });

        // --- SỰ KIỆN 3: XÓA ---
        lvNhacHen.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                xacNhanXoa(position);
                return true;
            }
        });
    }

    // --- HÀM LƯU DỮ LIỆU VÀO BỘ NHỚ ---
    private void luuDuLieu() {
        SharedPreferences sharedPreferences = getSharedPreferences("DATA_NHAC_HEN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Dùng JSONArray để chuyển danh sách Object thành chuỗi JSON
        JSONArray jsonArray = new JSONArray();
        try {
            for (HenGio item : arrHenGio) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("gio", item.gio);
                jsonObject.put("noiDung", item.noiDung);
                jsonObject.put("dangBat", item.dangBat);
                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Lưu chuỗi JSON vào SharedPreferences
        editor.putString("LIST_JSON", jsonArray.toString());
        editor.apply(); // Lưu ngay lập tức
    }

    // --- HÀM ĐỌC DỮ LIỆU TỪ BỘ NHỚ ---
    private void docDuLieu() {
        SharedPreferences sharedPreferences = getSharedPreferences("DATA_NHAC_HEN", Context.MODE_PRIVATE);
        String jsonString = sharedPreferences.getString("LIST_JSON", "");

        if (!jsonString.isEmpty()) {
            try {
                arrHenGio.clear(); // Xóa sạch list tạm để nạp dữ liệu từ bộ nhớ
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String gio = obj.getString("gio");
                    String noiDung = obj.getString("noiDung");
                    boolean dangBat = obj.getBoolean("dangBat");

                    arrHenGio.add(new HenGio(gio, noiDung, dangBat));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // --- HÀM HIỂN THỊ HỘP THOẠI (GIỮ NGUYÊN CODE TRƯỚC, CHỈ THÊM LỆNH SAVE) ---
    private void hienThiDialogNhapThongTin(int viTriCanSua) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(viTriCanSua == -1 ? "Thêm nhắc nhở mới" : "Chỉnh sửa nhắc nhở");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);

        final TextView tvChonGio = new TextView(this);
        tvChonGio.setTextSize(24);
        tvChonGio.setTextColor(Color.BLUE);
        tvChonGio.setPadding(0, 0, 0, 30);
        layout.addView(tvChonGio);

        final EditText edtNoiDung = new EditText(this);
        edtNoiDung.setHint("Nhập nội dung công việc...");
        layout.addView(edtNoiDung);

        builder.setView(layout);

        final String[] gioTamThoi = {"08:00"};

        if (viTriCanSua != -1) {
            HenGio itemCu = arrHenGio.get(viTriCanSua);
            gioTamThoi[0] = itemCu.gio;
            edtNoiDung.setText(itemCu.noiDung);
        } else {
            Calendar c = Calendar.getInstance();
            gioTamThoi[0] = String.format("%02d:%02d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
        }
        tvChonGio.setText("Giờ hẹn: " + gioTamThoi[0]);

        tvChonGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] parts = gioTamThoi[0].split(":");
                int h = Integer.parseInt(parts[0]);
                int m = Integer.parseInt(parts[1]);

                TimePickerDialog timePickerDialog = new TimePickerDialog(NhacHenActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                gioTamThoi[0] = String.format("%02d:%02d", hourOfDay, minute);
                                tvChonGio.setText("Giờ hẹn: " + gioTamThoi[0]);
                            }
                        }, h, m, true);
                timePickerDialog.show();
            }
        });

        builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String noiDungMoi = edtNoiDung.getText().toString();
                if (noiDungMoi.isEmpty()) noiDungMoi = "Không có nội dung";

                if (viTriCanSua == -1) {
                    arrHenGio.add(new HenGio(gioTamThoi[0], noiDungMoi, true));
                    Toast.makeText(NhacHenActivity.this, "Đã thêm mới!", Toast.LENGTH_SHORT).show();
                } else {
                    arrHenGio.get(viTriCanSua).gio = gioTamThoi[0];
                    arrHenGio.get(viTriCanSua).noiDung = noiDungMoi;
                    Toast.makeText(NhacHenActivity.this, "Đã cập nhật!", Toast.LENGTH_SHORT).show();
                }

                luuDuLieu(); // <--- QUAN TRỌNG: LƯU LẠI SAU KHI SỬA
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void xacNhanXoa(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Xóa nhắc nhở: " + arrHenGio.get(position).noiDung + "?")
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        arrHenGio.remove(position);

                        luuDuLieu(); // <--- QUAN TRỌNG: LƯU LẠI SAU KHI XÓA

                        adapter.notifyDataSetChanged();
                        Toast.makeText(NhacHenActivity.this, "Đã xóa!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Model
    public class HenGio {
        public String gio;
        public String noiDung;
        public boolean dangBat;

        public HenGio(String gio, String noiDung, boolean dangBat) {
            this.gio = gio;
            this.noiDung = noiDung;
            this.dangBat = dangBat;
        }
    }

    // Adapter
    class MyAdapter extends ArrayAdapter<HenGio> {
        AppCompatActivity context;
        int resource;
        ArrayList<HenGio> data;

        public MyAdapter(AppCompatActivity context, int resource, ArrayList<HenGio> data) {
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

            TextView tvGio = v.findViewById(R.id.tvGioHen);
            TextView tvNoiDung = v.findViewById(R.id.tvNoiDungNhac);
            Switch sw = v.findViewById(R.id.swBatTat);

            HenGio henGio = data.get(position);

            if(tvGio != null) tvGio.setText(henGio.gio);
            if(tvNoiDung != null) tvNoiDung.setText(henGio.noiDung);
            if(sw != null) {
                sw.setChecked(henGio.dangBat);

                // Xử lý sự kiện bật/tắt Switch và LƯU LẠI
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        henGio.dangBat = sw.isChecked();
                        luuDuLieu(); // <--- QUAN TRỌNG: Lưu trạng thái bật/tắt
                    }
                });
            }

            return v;
        }
    }
}