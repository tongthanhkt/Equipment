# Quản lý thiết bị

Đề bài:
Xây dựng website quản lý thiết bị hỗ trợ công tác quản lý cho bộ phận mua hàng.
Ứng dụng cần đáp ứng được các yêu cầu sau:

## 1. Quản lý thiết bị & danh mục thiết bị:

- Mỗi thiết bị sẽ được phân loại thành danh mục và được đánh mã số riêng biệt, có hình ảnh khi mới nhập về.
- Thông tin thiết bị sẽ bao gồm cả tình trạng khi nhập thiết bị vào cty và giá tiền khi nhập (VD: thiết bị mới, 40tr,...)
- Mỗi thiết bị có giá tiền và khấu hao khác nhau (Khấu hao tính theo tháng. VD: Laptop Dell 30tr - khấu hao 5 năm (60 tháng) thì giá trị khấu hao mỗi năm sẽ là 6%)
- Mỗi lần nhập thiết bị, giá tiền và khấu hao sẽ có thể khác những lần trước đó.

## 2. Kiểm kho

Nhu cầu kiểm kê thiết bị của bộ phận mua hàng.

- Tổng số thiết bị đang có trong kho (chưa có bàn giao), phân loại theo danh mục
- Tổng số thiết bị đang được nhân viên sử dụng, phân loại theo danh mục
- Tổng số thiết bị đã bị mất & được đền bù, phân loại theo danh mục
- Tổng số thiết bị đã bị mất & chưa được đền bù, phân loại theo danh mục
- Thống kê số thiết bị nhập mới & tổng tiền trong tháng / quý / năm
- Thống kê số thiết bị đã bàn giao & tổng tiền trong tháng / quý / năm

## 3. Bàn giao & thu hồi thiết bị:

### 3.1 Bàn giao thiết bị

- Bộ phận mua hàng cần ghi nhận thời gian bàn giao thiết bị, hình ảnh bàn giao, giấy tờ và chứng từ
- Một nhân viên có thể được bàn giao nhiều thiết bị

### 3.2 Thu hồi thiết bị

- Bộ phận mua hàng cần ghi nhận thời gian thu hồi thiết bị kèm lý do thu hồi, hình ảnh bàn giao, giấy tờ và chứng từ.
- Khi thu hồi thiết bị, nếu thiết bị bị mất thì nhân viên phải đền bù, giá trị đền bù sẽ dựa vào giá tiền và khấu hao .

## 4. Tra cứu thông tin

- Bộ phận mua hàng có thể tra cứu được nhân viên nào đang sử dụng các thiết bị nào. Giá trị cũng như khấu hao của thiết bị.

## Lưu ý:

- Nhân viên phải hoàn trả toàn bộ các thiết bị khi nghỉ việc.
- Nếu thiết bị đã hết khấu hao thì không cần hoàn trả.
- Trong quá trình nhân viên sử dụng thiết bị, nếu có hư hỏng và chính sách cty hỗ trợ việc sửa chữa thì bộ phận mua hàng cần có nơi lưu trữ hành động sửa chữa thiết bị:
  - Thông tin về thiết bị
  - Nhân viên
  - Thời gian
  - Chi phí sửa chữa
  - Giấy tờ & chứng từ liên quan
  - Hình ảnh thiết bị
  - Lý do sửa chữa

## Yêu cầu khác:

- Frontend: Sử dụng Vue 3 & Typescript
- Backend: scala & finatra & mysql / mariadb

## Điều kiện chấm bài:

- Thời gian: 2 tháng kể từ ngày đọc đề
- Hoàn thiện tính năng: 6Đ (hoàn thành tất cả yêu cầu của đề bài)
- Code và tổ chức code: 4Đ (rõ ràng, logic, dễ phát triển thêm sau này)
- Lỗi: -0.25Đ / Lỗi

=> Đạt yêu cầu: 8/10
/// document
https://www.bezkoder.com/vue-upload-image-axios/
