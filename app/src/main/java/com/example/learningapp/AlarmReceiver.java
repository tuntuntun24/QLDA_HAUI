package com.example.learningapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Lấy nội dung nhắc hẹn từ intent
        String noiDung = intent.getStringExtra("noi_dung");
        if (noiDung == null) noiDung = "Đến giờ học bài rồi!";

        // HIỂN THỊ TOAST ĐỂ KIỂM TRA NHANH (Nếu thấy dòng này hiện trên LDPlayer là code đã chạy)
        Toast.makeText(context, "BÁO THỨC: " + noiDung, Toast.LENGTH_LONG).show();

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "nhac_hen_channel";

        // Tạo Channel cho Android 8.0 trở lên (Bắt buộc)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Nhắc hẹn học tập",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Kênh thông báo cho ứng dụng học tập");
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        // Xây dựng thông báo
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm) // Sử dụng icon báo thức của hệ thống
                .setContentTitle("Thông báo từ Learning App")
                .setContentText(noiDung)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setAutoCancel(true);

        // Hiển thị thông báo
        if (manager != null) {
            manager.notify((int) System.currentTimeMillis(), builder.build());
        }
    }
}