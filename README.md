# BookShop
Xây dựng một trang website bán sách.
## Với các chức năng:
- Có 2 role là User role và admin role.
- Xác thực và phân quyền người dùng.
- Đối với User role, Guest :
    + User có thể xem thông tin các sách.
    + Tìm kiếm theo nhiều tiêu chí(thể loại, giá tiền, từ khóa, sắp xếp tăng giảm...).
    + Thêm sách vào giỏ hàng nếu Guest thì lưu giỏ hành ở LocalStorage, nếu User role thì lưu giở hàng ở DB.
    + Mua hàng và nhận mail đã đặt hàng.
    + Bình luận các sách đã mua.
- Đối với Admin:
  + CRUD sách
  + Cấu hình tài khoản người dùng
  + Xem danh sách đơn đặt hàng
## Công nghệ sử dụng:
- Spring boot, Spring data JPA, Spring Security, Spring Mail, Thymeleaf, HTML, CSS, JS, JQuery.
- Database: MySQL

