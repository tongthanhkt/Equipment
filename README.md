# Quản lý thư viện

Đề bài:
Xây dựng website quản lý thư viện hỗ trợ công tác quản lý cho thủ thư.
Ứng dụng cần đáp ứng được các yêu cầu sau:

## 1. Quản lý đầu sách & sách:

- Mỗi đầu sách thư viện sẽ nhập về một số lượng sách khác nhau
- Mỗi đầu sách sẽ có giá tiền nhất định (tùy vào nhà sản xuất)


## 2. Kiểm thư viện:

Tổng số lượng sách mà thư viện có
Tổng số đầu sách đang hiện diện ở thư viện
Tổng số đầu sách đang cho mượn
Tổng số đầu sách đã bị quá hạn chưa trả
Tổng số đầu sách bị mất, thất lạc
Tổng số đầu sách đã được đền bù


## 3. Quản lý mượn sách:

Mỗi sinh viên được mượn tối đa 5 cuốn sách khác nhau cho mỗi lần (không được phép mượn 2 cuốn sách giống nhau).
Điều kiện để mượn sách:

Sinh viên phải đặt cọc 200,000 VND thì mới được phép mượn sách
Sinh viên phải hoàn trả toàn bộ sách đã mượn (nếu như có mượn trước đó) thì mới được phép mượn tiếp.
Sinh viên sẽ bị phạt khi trả sách bị trễ:

Nếu sinh viên trả sách trễ N ngày thì sau N ngày kể từ ngày trả sách sinh viên mới được phép tiếp tục mượn sách ở thư viện.
Trong trường hợp sách bị mất, sinh viên phải bồi thường số tiền tương ứng với số tiền của cuốn sách. Nếu sinh viên không bồi thường thì thư viện sẽ từ chối không cho sinh viên mượn sách nữa và lấy số tiền cọc sách để bù vào các cuốn sách bị mất.



## Lưu ý:

Sau khi không còn nhu cầu mượn sách & đã hoàn trả toàn bộ sách đã mượn cho thư viện thì sinh viên được phép lấy lại tiền sách đã cọc.


## Yêu cầu khác:
Frontend: Sử dụng Vue 3 & Typescript
Backend: scala & finatra & mysql / mariadb


## Điều kiện chấm bài:

Hoàn thiện tính năng: 6Đ (hoàn thành tất cả yêu cầu của đề bài)
Code và tổ chức code: 4Đ (rõ ràng, logic, dễ phát triển thêm sau này)
Lỗi: -unlimited
Lỗi UI/UX: -0.125Đ / Lỗi
Lỗi logic: -0.25Đ / Lỗi

=> Đạt yêu cầu: 8/10
