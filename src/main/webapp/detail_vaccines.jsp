<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>${vaccineContents.name}</title>
  <link rel="icon" type="image/png" href="image/logo1.png">
  <!-- Bootstrap, jquery   -->
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
  <!-- Font awesome-->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
  <!-- Font chữ   -->
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap" rel="stylesheet">
  <!-- Css -->
  <link rel="stylesheet" href="css/detail_vaccines.css">
</head>
<body>
<div id="wrapper">
  <!-- Header -->
  <jsp:include page="header.jsp"></jsp:include>
  <!-- Content -->
  <div id="section-content">
    <div class="container">
      <div class="row">
        <!-- Danh sách các mục vaccine -->
        <div class="col-md-3">
          <!-- List-group cho màn hình lớn -->
          <div id="vaccine-nav" class="list-group">
            <a class="list-group-item active" data-target="vaccine-info" href="#vaccine-info">1. Thông tin vắc xin</a>
            <a class="list-group-item" data-target="target-group" href="#target-group">2. Đối tượng</a>
            <a class="list-group-item" data-target="vaccination-schedule" href="#vaccination-schedule">3. Lịch tiêm</a>
            <a class="list-group-item" data-target="adverse-reactions" href="#adverse-reactions">4. Phản ứng sau khi tiêm</a>
            <a class="list-group-item" data-target="vaccine-status" href="#vaccine-status">5. Tình trạng vắc xin</a>
          </div>

          <!-- Accordion cho màn hình < 768px -->
          <div class="accordion" id="accordionPanelsStayOpenExample">
            <div class="accordion-item">
              <h2 class="accordion-header" id="panelsStayOpen-headingOne">
                <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseOne" aria-expanded="true" aria-controls="panelsStayOpen-collapseOne">
                  <i class="fa-solid fa-bars"></i> Mục lục
                </button>
              </h2>
              <div id="panelsStayOpen-collapseOne" class="accordion-collapse collapse show" aria-labelledby="panelsStayOpen-headingOne">
                <div class="accordion-body">
                  <a class="list-group-item active" data-target="vaccine-info" href="#vaccine-info">1. Thông tin vắc xin</a>
                  <a class="list-group-item" data-target="target-group" href="#target-group">2. Đối tượng</a>
                  <a class="list-group-item" data-target="vaccination-schedule" href="#vaccination-schedule">3. Lịch tiêm</a>
                  <a class="list-group-item" data-target="adverse-reactions" href="#adverse-reactions">4. Phản ứng sau khi tiêm</a>
                  <a class="list-group-item" data-target="vaccine-status" href="#vaccine-status">5. Tình trạng vắc xin</a>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Nội dung chi tiết vaccine -->
        <div class="col-md-9">
          <div class="section-vaccine-detail">
            <div id="vaccine-info" class="detail-contents">
              <h4 class="section-title">1. Thông tin vắc xin </h4>

                            <div class="vaccine-image text-center">
                              <img src="${vaccineContents.imageUrl}" alt="${vaccineContents.name}"
                                   style="max-width: 100%; height: auto; margin-bottom: 20px; border-radius: 10px;"/>
                            </div>
              <div class="section-body">
                <!-- Thông tin vaccine -->
                <h5><strong>Nguồn gốc vắc xin ${vaccineContents.name} </strong></h5>
                <ul>
                  <c:forTokens items="${vaccineContents.origin}" delims="/" var="line">
                    <li>${line}</li>
                  </c:forTokens>
                </ul>
                <!-- administrationRoute đường tiêm -->
                <c:if test="${not empty vaccineContents.administrationRoute}">
                  <h5><strong>Đường tiêm</strong></h5>
                  <ul>
                    <li>${vaccineContents.administrationRoute}</li>
                  </ul>
                </c:if>

                <!-- Chống chỉ định -->
                <c:if test="${not empty vaccineContents.contraindications}">
                  <h5><strong>Chống chỉ định</strong></h5>
                  <ul>
                    <c:forTokens items="${vaccineContents.contraindications}" delims="." var="contraindication">
                      <c:if test="${not empty contraindication}">
                        <li>${contraindication}</li>
                      </c:if>
                    </c:forTokens>
                  </ul>
                </c:if>
                <!-- Thận trọng khi sử dụng -->
                <h5><strong>Thận trọng khi sử dụng</strong></h5>
                <ul>
                  <c:forTokens items="${vaccineContents.precaution}" delims="." var="line">
                    <li>${line}</li>
                  </c:forTokens>
                    </ul>

                <!-- Tương tác thuốc -->
                <c:if test="${not empty vaccineContents.drugInteractions}">
                  <h5><strong>Tương tác thuốc</strong></h5>
                  <ul>
                    <c:forTokens items="${vaccineContents.drugInteractions}" delims="." var="interaction">
                      <c:if test="${not empty interaction}">
                        <li>${interaction}</li>
                      </c:if>
                    </c:forTokens>
                  </ul>
                </c:if>
                <c:if test="${not empty vaccineContents.sideEffects}">
                  <h5><strong>Tác dụng không mong muốn</strong></h5>
                  <ul>
                    <c:forTokens items="${vaccineContents.sideEffects}" delims="." var="sideEffect">
                      <c:if test="${not empty sideEffect}">
                        <li>${sideEffect}</li>
                      </c:if>
                    </c:forTokens>
                  </ul>
                </c:if>

              </div>
            </div>

            <div id="target-group" class="detail-contents">
              <h4 class="section-title">2. Đối tượng</h4>
              <div class="section-body">
                <p>${vaccineDetails.targetGroup}</p>
              </div>
            </div>

            <div id="vaccination-schedule" class="detail-contents">
              <h4 class="section-title">3. Lịch tiêm</h4>
              <div class="section-body">
                <ul>
                  <c:forTokens items="${vaccineDetails.immunization}" delims="." var="line">
                    <c:if test="${not empty line}">
                      <li>${line}</li>
                    </c:if>
                  </c:forTokens>
                </ul>
              </div>
            </div>

            <c:if test="${not empty vaccineDetails.adverseReactions}">
              <div id="adverse-reactions" class="detail-contents">
                <h4 class="section-title">4. Phản ứng sau khi tiêm</h4>
                <div class="section-body">
                  <ul>
                    <c:forTokens items="${vaccineDetails.adverseReactions}" delims="." var="reaction">
                      <c:if test="${not empty reaction}">
                        <li>${reaction}</li>
                      </c:if>
                    </c:forTokens>
                  </ul>
                </div>
              </div>
            </c:if>

          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- Footer -->
  <jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
<script src="js/detail_vaccines.js"></script>
<script src="js/show_navbarNav.js"></script>
</html>