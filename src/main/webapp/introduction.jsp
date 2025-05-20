<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Giới thiệu</title>
  <link rel="icon" type="image/png" href="image/logo1.png">
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="css/common-vaccination.css">
</head>
<body>
<div id="wrapper">
  <!-- Header -->
  <jsp:include page="header.jsp"></jsp:include>
  <!-- Content -->
  <div class="main">
    <div class="container">
      <h4 class="title">Giới thiệu dự án website đặt lịch hẹn tiêm vắc xin</h4>
      <p>Dự án website đặt lịch hẹn tiêm vắc xin là sản phẩm nhóm chúng em phát triển trong quá trình học tập môn Lập trình Web, với mong muốn áp dụng kiến thức học được vào xây dựng một giải pháp thực tế. Website hướng đến hỗ trợ cộng đồng trong việc tiếp cận dịch vụ tiêm chủng một cách dễ dàng, nhanh chóng và hiện đại.</p>
      <h5>1. Mục đích</h5>
      <p>Website được thiết kế nhằm giải quyết các vấn đề thường gặp trong việc đặt lịch tiêm, như mất thời gian xếp hàng, khó theo dõi lịch hẹn hoặc tìm kiếm thông tin về các loại vắc xin. Với trang web này, người dùng có thể xem thông tin vắc xin chi tiết, chọn gói tiêm phù hợp và đặt lịch hẹn tại trung tâm tiêm chủng mong muốn mà không cần phải đến trực tiếp.</p>
      <h5>2. Tính năng nổi bật</h5>
      <ul>
        <li>
          <b>Giao diện người dùng thân thiện:</b> Website cung cấp giao diện dễ sử dụng, hỗ trợ tốt trên cả máy tính và điện thoại di động.
        </li>
        <li>
          <b>Chức năng quản lý lịch hẹn: </b> Người dùng có thể tạo, xem và quản lý lịch hẹn tiêm chủng một cách đơn giản.
        </li>
        <li>
          <b>Thông tin chi tiết: </b> Danh sách các loại vắc xin và gói tiêm được cập nhật đầy đủ, kèm theo mô tả và giá cả.
        </li>
        <li>
          <b>Tìm kiếm: </b>  trang web tìm kiếm nhanh chóng.
        </li>
      </ul>
      <h5>3. Hạn chế và mục tiêu cải thiện</h5>
      <p>
        Là sinh viên năm 3 ngành Công nghệ Thông tin, chúng em vẫn đang trong quá trình học tập và trau dồi kỹ năng.
        Vì vậy, website này còn nhiều điểm cần cải thiện, đặc biệt về hiệu năng, giao diện người dùng và khả năng mở rộng chức năng.
        Tuy nhiên, dự án là một bước đầu quan trọng để chúng em áp dụng lý thuyết vào thực tế, đồng thời rèn luyện khả năng làm việc nhóm và quản lý dự án.
        Chúng em hy vọng sẽ tiếp tục hoàn thiện sản phẩm trong tương lai để đáp ứng tốt hơn nhu cầu của người dùng.
      </p>
    </div>
  </div>
  <!-- Footer -->
  <jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
<script src="js/show_navbarNav.js"></script>
</html>