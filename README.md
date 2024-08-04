# Ứng dụng web Fashion Shop

## Tổng quan

Fashion Shop là ứng dụng web thương mại điện tử, nơi người dùng có thể duyệt, tìm kiếm và mua các mặt hàng thời trang. Ứng dụng bao gồm trang quản trị để quản lý sản phẩm, danh mục và đơn hàng.

## Tính năng

- Xác thực và phân quyền người dùng
- Tìm kiếm sản phẩm theo danh mục
- Quản lý giỏ hàng
- Đặt hàng và quản lý đơn hàng
- Trang quản trị để quản lý sản phẩm, danh mục và đơn hàng
- Tải lên và lưu trữ hình ảnh bằng AWS S3

## Công Nghệ Sử Dụng

### Front-end

- **Vue.js**: Một framework JavaScript linh động dùng để xây dựng giao diện người dùng
- **Vite**: Bộ công cụ cho front-end framework
- **Axios**: Thư viện HTTP Client dựa trên Promise dành cho Node.js và trình duyệt
- **Pinia**: Thư viện quản lý trạng thái cho Vue.js

### Back-end

- **Spring Boot**: Một framework Java được sử dụng để xây dựng các ứng dụng và dịch vụ web dễ dàng và nhanh chóng
- **JWT**: Một tiêu chuẩn mở cho việc tạo ra các token truy cập an toàn dựa trên JSON, thường được sử dụng để xác thực và ủy quyền người dùng
- **Spring Security**: Một trong những framework bảo mật phổ biến nhất cho ứng dụng Java

### Cơ sở dữ liệu

- **Oracle Database**: Hệ quản trị cơ sở dữ liệu phổ biến

### Dịch vụ web

- **AWS S3**: Dịch vụ lưu trữ có thể mở rộng để lưu trữ hình ảnh và các đối tượng khác

## Bắt đầu

### Yêu cầu

- **Node.js và npm**: Tải và cài đặt từ [Node.js website](https://nodejs.org/).
- **Java Development Kit (JDK)**: Tải và cài đặt từ [Liberica JDK website](https://bell-sw.com/pages/downloads/).
- **Oracle Database**: Tải và cài đặt từ [Oracle Database website](https://www.oracle.com/database/technologies/oracle-database-software-downloads.html).

### Cài đặt

1. **Clone repository:**

```bash
git clone https://github.com/nmtzz/nmtzzshop.git
```

2. **Cấu hình cơ sở dữ liệu:**

- Sử dung file `script.sql` trong `server/src/main/sql` để tạo bảng và các đối tượng khác của cơ sở dữ liệu.

- Cập nhật tệp `application.properties` trong Thư mục `server/src/main/resources` với cấu hình cơ sở dữ liệu Oracle của bạn:

```properties
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
```

3. **Cấu hình Back-end:**

Điều hướng đến thư mục `server` và chạy file `1.Start_server.bat` để tự động khởi chạy Spring Boot. Hoặc chạy thủ công như sau:

```bash
cd server
cd target
java -jar server-0.0.1-SNAPSHOT.jar
```

### Chạy ứng dụng

1. **Khởi động Vite:**

Điều hướng đến thư mục `client` và chạy file `1.Start_client.bat` để tự động cài đặt dependencies và chạy Vite.

2. **Truy cập trên trình duyệt:**

Mở trình duyệt và truy cập vào URL của Vite, mặc định: `http://localhost:5173`

## Sử dụng

### Chức năng của người dùng

- **Đăng ký**: Người dùng có thể đăng ký tài khoản.
- **Đăng nhập**: Người dùng có thể đăng nhập vào tài khoản của họ.
- **Duyệt sản phẩm**: Người dùng có thể duyệt và tìm kiếm các mặt hàng.
- **Giỏ hàng**: Người dùng có thể thêm các mặt hàng vào giỏ hàng của họ và tiến hành thanh toán.
- **Quản lý đơn hàng**: Người dùng có thể xem lịch sử đơn hàng và theo dõi đơn hàng của họ.

### Chức năng quản trị

- **Quản lý sản phẩm**: Quản trị viên có thể thêm, cập nhật và xóa sản phẩm.
- **Quản lý danh mục**: Quản trị viên có thể quản lý danh mục sản phẩm.
- **Quản lý đơn hàng**: Quản trị viên có thể xem và cập nhật trạng thái đơn hàng.

## Lời cảm ơn

- Xin gửi lời cảm ơn đặc biệt đến cộng đồng nguồn mở vì các công cụ và thư viện được sử dụng trong dự án này.
