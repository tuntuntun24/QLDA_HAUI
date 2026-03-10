# 📚 Learning App - HaUI

Ứng dụng hỗ trợ học tập và ôn tập trắc nghiệm dành cho sinh viên.

---

## ✨ Tính năng chính

### 📖 Học tập & Tài liệu
- **Xem đề cương:** Hỗ trợ đọc 10 bài học dưới dạng tệp PDF tích hợp sẵn.
- **Tương tác mượt mà:** Sử dụng `FileProvider` để mở tài liệu an toàn trên thiết bị.

### 📝 Hệ thống Kiểm tra
- **Ngân hàng câu hỏi:** Sử dụng SQLite để quản lý bộ câu hỏi trắc nghiệm phong phú.
- **Chế độ thi thử:**
    - **KTTX1:** 10 câu ngẫu nhiên (Bài 1-4).
    - **KTTX2:** 10 câu ngẫu nhiên (Bài 5-8).
    - **Thi kết thúc HP:** 15 câu tổng hợp toàn bộ chương trình.
- **Luyện tập:** Cho phép chọn câu hỏi ôn tập theo từng bài học cụ thể.

### ⚙️ Tiện ích & Cài đặt
- **Nhắc hẹn:** Đặt lịch thông báo nhắc nhở giờ học qua hệ thống báo thức.
- **Đa ngôn ngữ:** Chuyển đổi linh hoạt giữa Tiếng Việt và Tiếng Anh.
- **Giao diện:** Hỗ trợ chế độ nền tối (Dark Mode) giúp bảo vệ thị giác.

---

## 🛠 Công nghệ sử dụng
- **Ngôn ngữ:** Java (Android SDK).
- **Dữ liệu:** SQLite (Local Database).
- **Thư viện:** Android Material Components, PDF Viewer components.
- **Hệ thống:** AlarmManager, BroadcastReceiver, Notification Channel.

---

## 🚀 Hướng dẫn cài đặt

1. **Mở dự án:** Dùng Android Studio mở thư mục chứa mã nguồn.
2. **Đồng bộ:** Chờ Gradle sync xong các thư viện.
3. **Cấp quyền:** Đảm bảo cấp quyền thông báo khi ứng dụng yêu cầu (để dùng tính năng Nhắc hẹn).
4. **Chạy app:** Nhấn **Run** (biểu tượng tam giác xanh) để cài lên máy ảo hoặc điện thoại.

---

## 📂 Cấu trúc Database
Dữ liệu lưu tại: `app/src/main/res/raw/data_real.db`
- **Bảng Cau:** Lưu mã câu, nội dung, đáp án và liên kết bài học.

---
*Phát triển cho học phần Quản lý dự án - HaUI*