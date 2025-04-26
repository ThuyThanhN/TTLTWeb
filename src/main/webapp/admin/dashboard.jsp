<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin</title>
    <!-- Bootstrap, jquery   -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
    <!-- Font awesome-->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <!-- Font chữ   -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap"
          rel="stylesheet">
    <!-- DataTable -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css">
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>
    <!-- Css   -->
    <link rel="stylesheet" href="../css/dashboard.css">
</head>
<body>
<jsp:include page="sidebar.jsp"></jsp:include>
<!-- Main Content -->
<div class="main-content">
    <!-- Header -->
    <jsp:include page="headerAdmin.jsp"></jsp:include>
    <div class="card-container">
        <h5 class="main-title">Thống kê</h5>
        <div class="row">
            <div class="col-12 col-md-6 col-lg-4">
                <div class="card-wrapper">
                    <div>
                        <div class="">
                            <div class="d-flex gap-3">
                                <div class="card-left drak-green">
                                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24.75"
                                         height="24.75">
                                        <g fill="none">
                                            <path d="m12.593 23.258l-.011.002l-.071.035l-.02.004l-.014-.004l-.071-.035q-.016-.005-.024.005l-.004.01l-.017.428l.005.02l.01.013l.104.074l.015.004l.012-.004l.104-.074l.012-.016l.004-.017l-.017-.427q-.004-.016-.017-.018m.265-.113l-.013.002l-.185.093l-.01.01l-.003.011l.018.43l.005.012l.008.007l.201.093q.019.005.029-.008l.004-.014l-.034-.614q-.005-.018-.02-.022m-.715.002a.02.02 0 0 0-.027.006l-.006.014l-.034.614q.001.018.017.024l.015-.002l.201-.093l.01-.008l.004-.011l.017-.43l-.003-.012l-.01-.01z"></path>
                                            <path fill="currentColor"
                                                  d="M16 14a5 5 0 0 1 5 5v1a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-1a5 5 0 0 1 5-5zm5.414-4.919a1 1 0 0 1 1.498 1.32l-.084.095L20 13.324a1 1 0 0 1-1.32.083l-.094-.083l-1.414-1.414a1 1 0 0 1 1.32-1.498l.094.084l.707.707zM12 2a5 5 0 1 1 0 10a5 5 0 0 1 0-10"></path>
                                        </g>
                                    </svg>
                                </div>
                                <div>
                                    <span class="title">Người dùng</span>
                                    <div class="amount-value">${totalUser}</div>
                                </div>
                            </div>
                        </div>
                        <div class="card-right">
                            <svg id="SvgjsSvg7243" width="80" height="42" xmlns="http://www.w3.org/2000/svg"
                                 version="1.1"
                                 xmlns:xlink="http://www.w3.org/1999/xlink"
                                 class="apexcharts-svg" transform="translate(0, 0)"
                                 style="background: transparent;">
                                <foreignObject x="0" y="0" width="80" height="42">
                                    <div class="apexcharts-legend" xmlns="http://www.w3.org/1999/xhtml"
                                         style="max-height: 21px;"></div>
                                </foreignObject>
                                <rect id="SvgjsRect7248" width="0" height="0" x="0" y="0" rx="0" ry="0" opacity="1"
                                      stroke-width="0" stroke="none" stroke-dasharray="0" fill="#fefefe"></rect>
                                <g id="SvgjsG7281" class="apexcharts-yaxis" rel="0" transform="translate(-18, 0)"></g>
                                <g id="SvgjsG7245" class="apexcharts-inner apexcharts-graphical"
                                   transform="translate(0, -3)">
                                    <defs id="SvgjsDefs7244">
                                        <clipPath id="gridRectMaskklrjvxwf">
                                            <rect id="SvgjsRect7250" width="86" height="47" x="-3" y="-1" rx="0" ry="0"
                                                  opacity="1" stroke-width="0" stroke="none" stroke-dasharray="0"
                                                  fill="#fff"></rect>
                                        </clipPath>
                                        <clipPath id="forecastMaskklrjvxwf"></clipPath>
                                        <clipPath id="nonForecastMaskklrjvxwf"></clipPath>
                                        <clipPath id="gridRectMarkerMaskklrjvxwf">
                                            <rect id="SvgjsRect7251" width="84" height="49" x="-2" y="-2" rx="0" ry="0"
                                                  opacity="1" stroke-width="0" stroke="none" stroke-dasharray="0"
                                                  fill="#fff"></rect>
                                        </clipPath>
                                        <linearGradient id="SvgjsLinearGradient7256" x1="0" y1="0" x2="0" y2="1">
                                            <stop id="SvgjsStop7257" stop-opacity="0.75"
                                                  stop-color="rgba(69,179,105,0.75)"
                                                  offset="0"></stop>
                                            <stop id="SvgjsStop7258" stop-opacity="0.3" stop-color="#45b36900"
                                                  offset="1"></stop>
                                            <stop id="SvgjsStop7259" stop-opacity="0.3" stop-color="#45b36900"
                                                  offset="1"></stop>
                                        </linearGradient>
                                    </defs>
                                    <line id="SvgjsLine7249" x1="39.5" y1="0" x2="39.5" y2="45" stroke="#b6b6b6"
                                          stroke-dasharray="3" stroke-linecap="butt" class="apexcharts-xcrosshairs"
                                          x="39.5"
                                          y="0" width="1" height="45" fill="#b1b9c4" filter="none" fill-opacity="0.9"
                                          stroke-width="1"></line>
                                    <g id="SvgjsG7262" class="apexcharts-grid">
                                        <g id="SvgjsG7263" class="apexcharts-gridlines-horizontal"></g>
                                        <g id="SvgjsG7264" class="apexcharts-gridlines-vertical"></g>
                                        <line id="SvgjsLine7267" x1="0" y1="45" x2="80" y2="45" stroke="transparent"
                                              stroke-dasharray="0" stroke-linecap="butt"></line>
                                        <line id="SvgjsLine7266" x1="0" y1="1" x2="0" y2="45" stroke="transparent"
                                              stroke-dasharray="0" stroke-linecap="butt"></line>
                                    </g>
                                    <g id="SvgjsG7252" class="apexcharts-area-series apexcharts-plot-series">
                                        <g id="SvgjsG7253" class="apexcharts-series" seriesName="series1"
                                           data:longestSeries="true" rel="1" data:realIndex="0">
                                            <path id="SvgjsPath7260"
                                                  d="M 0 45 L 0 37.5C 3.5 37.5 6.5 22.5 10 22.5C 13.5 22.5 16.5 33 20 33C 23.5 33 26.5 28.5 30 28.5C 33.5 28.5 36.5 36 40 36C 43.5 36 46.5 25.5 50 25.5C 53.5 25.5 56.5 34.5 60 34.5C 63.5 34.5 66.5 7.5 70 7.5C 73.5 7.5 76.5 30 80 30C 80 30 80 30 80 45M 80 30z"
                                                  fill="url(#SvgjsLinearGradient7256)" fill-opacity="1"
                                                  stroke-opacity="1"
                                                  stroke-linecap="round" stroke-width="0" stroke-dasharray="0"
                                                  class="apexcharts-area" index="0"
                                                  clip-path="url(#gridRectMaskklrjvxwf)"
                                                  pathTo="M 0 45 L 0 37.5C 3.5 37.5 6.5 22.5 10 22.5C 13.5 22.5 16.5 33 20 33C 23.5 33 26.5 28.5 30 28.5C 33.5 28.5 36.5 36 40 36C 43.5 36 46.5 25.5 50 25.5C 53.5 25.5 56.5 34.5 60 34.5C 63.5 34.5 66.5 7.5 70 7.5C 73.5 7.5 76.5 30 80 30C 80 30 80 30 80 45M 80 30z"
                                                  pathFrom="M -1 90 L -1 90 L 10 90 L 20 90 L 30 90 L 40 90 L 50 90 L 60 90 L 70 90 L 80 90"></path>
                                            <path id="SvgjsPath7261"
                                                  d="M 0 37.5C 3.5 37.5 6.5 22.5 10 22.5C 13.5 22.5 16.5 33 20 33C 23.5 33 26.5 28.5 30 28.5C 33.5 28.5 36.5 36 40 36C 43.5 36 46.5 25.5 50 25.5C 53.5 25.5 56.5 34.5 60 34.5C 63.5 34.5 66.5 7.5 70 7.5C 73.5 7.5 76.5 30 80 30"
                                                  fill="none" fill-opacity="1" stroke="#45b369" stroke-opacity="1"
                                                  stroke-linecap="round" stroke-width="2" stroke-dasharray="0"
                                                  class="apexcharts-area" index="0"
                                                  clip-path="url(#gridRectMaskklrjvxwf)"
                                                  pathTo="M 0 37.5C 3.5 37.5 6.5 22.5 10 22.5C 13.5 22.5 16.5 33 20 33C 23.5 33 26.5 28.5 30 28.5C 33.5 28.5 36.5 36 40 36C 43.5 36 46.5 25.5 50 25.5C 53.5 25.5 56.5 34.5 60 34.5C 63.5 34.5 66.5 7.5 70 7.5C 73.5 7.5 76.5 30 80 30"
                                                  pathFrom="M -1 90 L -1 90 L 10 90 L 20 90 L 30 90 L 40 90 L 50 90 L 60 90 L 70 90 L 80 90"
                                                  fill-rule="evenodd"></path>
                                            <g id="SvgjsG7254"
                                               class="apexcharts-series-markers-wrap apexcharts-hidden-element-shown"
                                               data:realIndex="0">
                                                <g class="apexcharts-series-markers">
                                                    <circle id="SvgjsCircle7285" r="0" cx="40" cy="36"
                                                            class="apexcharts-marker ww8hj3cp4 no-pointer-events"
                                                            stroke="#ffffff" fill="#45b369" fill-opacity="1"
                                                            stroke-width="2" stroke-opacity="0.9"
                                                            default-marker-size="0"></circle>
                                                </g>
                                            </g>
                                        </g>
                                        <g id="SvgjsG7255" class="apexcharts-datalabels" data:realIndex="0"></g>
                                    </g>
                                    <g id="SvgjsG7265" class="apexcharts-grid-borders"></g>
                                    <line id="SvgjsLine7268" x1="0" y1="0" x2="80" y2="0" stroke="#b6b6b6"
                                          stroke-dasharray="0" stroke-width="1" stroke-linecap="butt"
                                          class="apexcharts-ycrosshairs"></line>
                                    <line id="SvgjsLine7269" x1="0" y1="0" x2="80" y2="0" stroke-dasharray="0"
                                          stroke-width="0" stroke-linecap="butt"
                                          class="apexcharts-ycrosshairs-hidden"></line>
                                    <g id="SvgjsG7270" class="apexcharts-xaxis" transform="translate(0, 0)">
                                        <g id="SvgjsG7271" class="apexcharts-xaxis-texts-g"
                                           transform="translate(0, 4)"></g>
                                    </g>
                                    <g id="SvgjsG7282" class="apexcharts-yaxis-annotations"></g>
                                    <g id="SvgjsG7283" class="apexcharts-xaxis-annotations"></g>
                                    <g id="SvgjsG7284" class="apexcharts-point-annotations"></g>
                                </g>
                            </svg>
                        </div>
                    </div>
                    <p class="text-sm mt-2 mb-0" style="color: #485563; font-size: 15px">Tăng <span
                            class="bg-success-focus px-1 rounded-2 fw-medium text-success-main text-sm">+200</span>
                        trong tuần này</p>
                </div>
            </div>
            <div class="col-12 col-md-6 col-lg-4">
                <div class="card-wrapper light-blue">
                    <div class="card-left dark-blue">
                        <i class="fa-solid fa-tags fa-2x"></i>
                    </div>
                    <div class="card-right">
                        <span class="title">Tổng vắc xin</span>
                        <div class="amount-value">${totalVacine} vắc xin</div>
                    </div>
                </div>
            </div>
            <div class="col-12 col-md-6 col-lg-4">
                <div class="card-wrapper light-orange">
                    <div class="card-left dark-orange">
                        <i class="fa-solid fa-calendar-check fa-2x"></i>
                    </div>
                    <div class="card-right">
                        <span class="title">Tổng đơn hàng</span>
                        <div class="amount-value">${totalOrder} đơn hàng</div>
                    </div>
                </div>
            </div>
            <div class="col-12 col-md-6 col-lg-4">
                <div class="card-wrapper light-red">
                    <div class="card-left dark-red">
                        <i class="fa-solid fa-id-badge fa-2x"></i>
                    </div>
                    <div class="card-right">
                        <span class="title">Hết hàng</span>
                        <div class="amount-value">${totalExpire} vắc xin</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="tabular-wrapper">
        <h5 class="main-title">Vắc xin bán chạy trong tháng</h5>
        <div class="table-container">
            <table class="w-100 table table-striped" id="admin">
                <thead>
                <tr>
                    <th>Mã vắc xin</th>
                    <th>Tên vắc xin</th>
                    <th style="width: 60%">Mô tả</th>
                    <th>Số lần đặt</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="cd" items="${countOrder}">
                    <tr>
                        <td>${cd.id}</td>
                        <td>${cd.name}</td>
                        <td>${cd.description}</td>
                        <td>${cd.orderCount}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script src="../js/dashboard.js"></script>
</body>
</html>
