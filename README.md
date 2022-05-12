# Quản lý thư viện

Đề bài:
Xây dựng website quản lý thư viện hỗ trợ công tác quản lý cho thủ thư & tra cứu thông tin cho thành viên.
Ứng dụng cần đáp ứng được các yêu cầu sau:

## 1. Quản lý đầu sách & sách:

- Mỗi đầu sách thư viện sẽ nhập về một số lượng sách khác nhau
- Mỗi đầu sách sẽ có giá tiền nhất định (tùy vào nhà sản xuất)
- Tùy vào mỗi đợt nhập sách vào thư viện, giá của đầu sách có thể khác nhau ở mỗi lần nhập.


## 2. Kiểm thư viện:

Tổng số lượng sách mà thư viện có
Tổng số sách đang hiện diện ở thư viện phân loại theo đầu sách
Tổng số sách đang cho mượn phân loại theo đầu sách
Tổng số sách đã bị quá hạn chưa trả phân loại theo đầu sách
Tổng số sách bị mất, thất lạc phân loại theo đầu sách
Tổng số sách đã được đền bù phân loại theo đầu sách


## 3. Quản lý mượn sách:

Mỗi thành viên được mượn tối đa 5 cuốn sách khác nhau cho mỗi lần (không được phép mượn 2 cuốn sách giống nhau).
Điều kiện để mượn sách:

Thành viên phải đặt cọc 200,000 VND thì mới được phép mượn sách
Thành viên phải hoàn trả toàn bộ sách đã mượn (nếu như có mượn trước đó) thì mới được phép mượn tiếp.
Thành viên sẽ bị phạt khi trả sách bị trễ:

Nếu thành viên trả sách trễ N ngày thì sau N ngày kể từ ngày trả sách thành viên mới được phép tiếp tục mượn sách ở thư viện.
Trong trường hợp sách bị mất, thành viên phải bồi thường số tiền tương ứng với số tiền của cuốn sách. Nếu thành viên không bồi thường thì thư viện sẽ từ chối không cho thành viên mượn sách nữa và lấy số tiền cọc sách để bù vào các cuốn sách bị mất.

## 4. Tra cứu thông tin
- Website có tính năng cho phép mọi người có thể tra cứu được các cuốn sách đang sẵn có ở thư viện. Ngoài ra họ có thể nhập mã thành viên của mình để tra cứu tình trạng mượn sách.

## Lưu ý:

Sau khi không còn nhu cầu mượn sách & đã hoàn trả toàn bộ sách đã mượn cho thư viện thì thành viên được phép lấy lại tiền sách đã cọc.


## Yêu cầu khác:
Frontend: Sử dụng Vue 3 & Typescript
Backend: scala & finatra & mysql / mariadb


## Điều kiện chấm bài:

Hoàn thiện tính năng: 6Đ (hoàn thành tất cả yêu cầu của đề bài)
Code và tổ chức code: 4Đ (rõ ràng, logic, dễ phát triển thêm sau này)
Lỗi: -0.25Đ / Lỗi

=> Đạt yêu cầu: 8/10
