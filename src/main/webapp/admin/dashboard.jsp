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
    <%-- Ajax --%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <!-- DataTable -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css">
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
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
        <h4 class="main-title-dashboard">Thống kê</h4>
        <div class="row">
            <div class="col-12 col-md-6 col-lg-4">
                <div class="card-wrapper bg-gradient-end-1">
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
                                    <span class="title">Tổng người dùng</span>
                                    <div class="amount-value">${totalUser}</div>
                                </div>
                            </div>
                        </div>
                        <div class="card-right">
                            <svg id="SvgjsSvg7243" width="80" height="42" xmlns="http://www.w3.org/2000/svg"
                                 version="1.1" xmlns:xlink="http://www.w3.org/1999/xlink" class="apexcharts-svg"
                                 transform="translate(0, 0)"
                                 style="background: transparent;">
                                <foreignObject x="0" y="0" width="80" height="42">
                                    <div class="apexcharts-legend" xmlns="http://www.w3.org/1999/xhtml"
                                         style="max-height: 21px;"></div>
                                </foreignObject>
                                <rect id="SvgjsRect7248" width="0" height="0" x="0" y="0" rx="0" ry="0" opacity="1"
                                      stroke-width="0" stroke="none" stroke-dasharray="0" fill="#fefefe"></rect>
                                <g id="SvgjsG7245" class="apexcharts-inner apexcharts-graphical"
                                   transform="translate(0, -3)">
                                    <defs id="SvgjsDefs7244">
                                        <clipPath id="gridRectMaskklrjvxwf">
                                            <rect id="SvgjsRect7250" width="86" height="47" x="-3" y="-1" rx="0" ry="0"
                                                  opacity="1"
                                                  stroke-width="0" stroke="none" stroke-dasharray="0"
                                                  fill="#fff"></rect>
                                        </clipPath>
                                        <linearGradient id="SvgjsLinearGradient7256" x1="0" y1="0" x2="0" y2="1">
                                            <stop id="SvgjsStop7257" stop-opacity="0.75"
                                                  stop-color="rgba(69,179,105,0.75)" offset="0"></stop>
                                            <stop id="SvgjsStop7258" stop-opacity="0.3" stop-color="#45b36900"
                                                  offset="1"></stop>
                                            <stop id="SvgjsStop7259" stop-opacity="0.3" stop-color="#45b36900"
                                                  offset="1"></stop>
                                        </linearGradient>
                                    </defs>
                                    <g id="SvgjsG7252" class="apexcharts-area-series apexcharts-plot-series">
                                        <g id="SvgjsG7253" class="apexcharts-series" seriesName="series1"
                                           data:longestSeries="true" rel="1"
                                           data:realIndex="0">
                                            <path id="SvgjsPath7260"
                                                  d="M 0 45 L 0 37.5C 3.5 37.5 6.5 22.5 10 22.5C 13.5 22.5 16.5 33 20 33C 23.5 33 26.5 28.5 30 28.5C 33.5 28.5 36.5 36 40 36C 43.5 36 46.5 25.5 50 25.5C 53.5 25.5 56.5 34.5 60 34.5C 63.5 34.5 66.5 7.5 70 7.5C 73.5 7.5 76.5 30 80 30C 80 30 80 30 80 45M 80 30z"
                                                  fill="url(#SvgjsLinearGradient7256)" fill-opacity="1"
                                                  stroke-opacity="1" stroke-linecap="round"
                                                  stroke-width="0" stroke-dasharray="0" class="apexcharts-area"
                                                  index="0"
                                                  clip-path="url(#gridRectMaskklrjvxwf)"></path>
                                            <path id="SvgjsPath7261"
                                                  d="M 0 37.5C 3.5 37.5 6.5 22.5 10 22.5C 13.5 22.5 16.5 33 20 33C 23.5 33 26.5 28.5 30 28.5C 33.5 28.5 36.5 36 40 36C 43.5 36 46.5 25.5 50 25.5C 53.5 25.5 56.5 34.5 60 34.5C 63.5 34.5 66.5 7.5 70 7.5C 73.5 7.5 76.5 30 80 30"
                                                  fill="none" fill-opacity="1" stroke="#45b369" stroke-opacity="1"
                                                  stroke-linecap="round"
                                                  stroke-width="2" stroke-dasharray="0" class="apexcharts-area"
                                                  index="0"
                                                  clip-path="url(#gridRectMaskklrjvxwf)" fill-rule="evenodd"></path>
                                        </g>
                                    </g>
                                </g>
                            </svg>
                        </div>
                    </div>
                    <c:choose>
                        <c:when test="${userCountChange >= 0}">
                            <p class="text-sm mt-2 mb-0" style="color: #485563; font-size: 15px">
                                Tăng <span
                                    class="bg-success-focus px-1 rounded-2 fw-medium text-success-main text-sm">+${userCountChange}</span>
                                trong tuần này
                            </p>
                        </c:when>
                        <c:when test="${userCountChange < 0}">
                            <p class="text-sm mt-2 mb-0" style="color: #485563; font-size: 15px">
                                Giảm <span
                                    class="bg-danger-focus px-1 rounded-2 fw-medium text-danger-main text-sm">${userCountChange}</span>
                                trong tuần này
                            </p>
                        </c:when>
                    </c:choose>
                </div>
            </div>
            <div class="col-12 col-md-6 col-lg-4">
                <div class="card-wrapper bg-gradient-end-2">
                    <div>
                        <div class="">
                            <div class="d-flex gap-3">
                                <div class="card-left dark-orange">
                                    <i class="fa-solid fa-calendar-check"
                                       style="width:24.75px; height:24.75px; font-size:24.75px;"></i>
                                </div>
                                <div>
                                    <span class="title">Tổng đơn hàng</span>
                                    <div class="amount-value">${totalOrder}</div>
                                </div>
                            </div>
                        </div>
                        <div class="card-right">
                            <svg id="SvgjsSvg7288" width="80" height="42" xmlns="http://www.w3.org/2000/svg"
                                 version="1.1" xmlns:xlink="http://www.w3.org/1999/xlink"
                                 class="apexcharts-svg" transform="translate(0, 0)"
                                 style="background: transparent;">
                                <foreignObject x="0" y="0" width="80" height="42">
                                    <div class="apexcharts-legend" xmlns="http://www.w3.org/1999/xhtml"
                                         style="max-height: 21px;"></div>
                                </foreignObject>
                                <rect id="SvgjsRect7293" width="0" height="0" x="0" y="0" rx="0" ry="0" opacity="1"
                                      stroke-width="0" stroke="none" stroke-dasharray="0" fill="#fefefe"></rect>
                                <g id="SvgjsG7290" class="apexcharts-inner apexcharts-graphical"
                                   transform="translate(0, -3)">
                                    <defs id="SvgjsDefs7289">
                                        <clipPath id="gridRectMask1ep7xexu">
                                            <rect id="SvgjsRect7295" width="86" height="47" x="-3" y="-1" rx="0" ry="0"
                                                  opacity="1" stroke-width="0" stroke="none" stroke-dasharray="0"
                                                  fill="#fff"></rect>
                                        </clipPath>
                                        <clipPath id="forecastMask1ep7xexu"></clipPath>
                                        <clipPath id="nonForecastMask1ep7xexu"></clipPath>
                                        <clipPath id="gridRectMarkerMask1ep7xexu">
                                            <rect id="SvgjsRect7296" width="84" height="49" x="-2" y="-2" rx="0" ry="0"
                                                  opacity="1" stroke-width="0" stroke="none" stroke-dasharray="0"
                                                  fill="#fff"></rect>
                                        </clipPath>
                                        <linearGradient id="SvgjsLinearGradient7301" x1="0" y1="0" x2="0" y2="1">
                                            <stop id="SvgjsStop7302" stop-opacity="0.75"
                                                  stop-color="rgba(244,148,30,0.75)" offset="0"></stop>
                                            <stop id="SvgjsStop7303" stop-opacity="0.3" stop-color="#f4941e00"
                                                  offset="1"></stop>
                                            <stop id="SvgjsStop7304" stop-opacity="0.3" stop-color="#f4941e00"
                                                  offset="1"></stop>
                                        </linearGradient>
                                    </defs>
                                    <g id="SvgjsG7307" class="apexcharts-grid">
                                        <g id="SvgjsG7308" class="apexcharts-gridlines-horizontal"></g>
                                        <g id="SvgjsG7309" class="apexcharts-gridlines-vertical"></g>
                                    </g>
                                    <g id="SvgjsG7297" class="apexcharts-area-series apexcharts-plot-series">
                                        <g id="SvgjsG7298" class="apexcharts-series" seriesName="series1"
                                           data:longestSeries="true" rel="1" data:realIndex="0">
                                            <path id="SvgjsPath7305"
                                                  d="M 0 45 L 0 37.5C 3.5 37.5 6.5 22.5 10 22.5C 13.5 22.5 16.5 33 20 33C 23.5 33 26.5 28.5 30 28.5C 33.5 28.5 36.5 36 40 36C 43.5 36 46.5 25.5 50 25.5C 53.5 25.5 56.5 34.5 60 34.5C 63.5 34.5 66.5 7.5 70 7.5C 73.5 7.5 76.5 30 80 30C 80 30 80 30 80 45M 80 30z"
                                                  fill="url(#SvgjsLinearGradient7301)" fill-opacity="1"
                                                  stroke-opacity="1" stroke-linecap="round" stroke-width="0"
                                                  stroke-dasharray="0" class="apexcharts-area" index="0"
                                                  clip-path="url(#gridRectMask1ep7xexu)"></path>
                                            <path id="SvgjsPath7306"
                                                  d="M 0 37.5C 3.5 37.5 6.5 22.5 10 22.5C 13.5 22.5 16.5 33 20 33C 23.5 33 26.5 28.5 30 28.5C 33.5 28.5 36.5 36 40 36C 43.5 36 46.5 25.5 50 25.5C 53.5 25.5 56.5 34.5 60 34.5C 63.5 34.5 66.5 7.5 70 7.5C 73.5 7.5 76.5 30 80 30"
                                                  fill="none" fill-opacity="1" stroke="#f4941e" stroke-opacity="1"
                                                  stroke-linecap="round" stroke-width="2" stroke-dasharray="0"
                                                  class="apexcharts-area" index="0"
                                                  clip-path="url(#gridRectMask1ep7xexu)"></path>
                                        </g>
                                    </g>
                                    <g id="SvgjsG7310" class="apexcharts-grid-borders"></g>
                                </g>
                            </svg>

                        </div>
                    </div>
                    <c:choose>
                        <c:when test="${orderCountChange >= 0}">
                            <p class="text-sm mt-2 mb-0" style="color: #485563; font-size: 15px">
                                Tăng <span
                                    class="bg-success-focus px-1 rounded-2 fw-medium text-success-main text-sm">+${orderCountChange}</span>
                                trong tuần này
                            </p>
                        </c:when>
                        <c:when test="${orderCountChange < 0}">
                            <p class="text-sm mt-2 mb-0" style="color: #485563; font-size: 15px">
                                Giảm <span
                                    class="bg-danger-focus px-1 rounded-2 fw-medium text-danger-main text-sm">${orderCountChange}</span>
                                trong tuần này
                            </p>
                        </c:when>
                    </c:choose>
                </div>
            </div>
            <div class="col-12 col-md-6 col-lg-4">
                <div class="card-wrapper bg-gradient-end-3">
                    <div>
                        <div class="">
                            <div class="d-flex gap-3">
                                <div class="card-left dark-lightblue">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24.75"
                                         height="24.75"
                                         viewBox="0 0 14 14">
                                        <path fill="currentColor" fill-rule="evenodd"
                                              d="M13.463 9.692C13.463 12.664 10.77 14 7 14S.537 12.664.537 9.713c0-3.231 1.616-4.868 4.847-6.505L4.24 1.077A.7.7 0 0 1 4.843 0H9.41a.7.7 0 0 1 .603 1.023L8.616 3.208c3.23 1.615 4.847 3.252 4.847 6.484M7.625 4.887a.625.625 0 1 0-1.25 0v.627a1.74 1.74 0 0 0-.298 3.44l1.473.322a.625.625 0 0 1-.133 1.236h-.834a.625.625 0 0 1-.59-.416a.625.625 0 1 0-1.178.416a1.88 1.88 0 0 0 1.56 1.239v.636a.625.625 0 1 0 1.25 0v-.636a1.876 1.876 0 0 0 .192-3.696l-1.473-.322a.49.49 0 0 1 .105-.97h.968a.62.62 0 0 1 .59.416a.625.625 0 0 0 1.178-.417a1.87 1.87 0 0 0-1.56-1.238z"
                                              clip-rule="evenodd"></path>
                                    </svg>
                                </div>
                                <div>
                                    <span class="title">Doanh thu</span>
                                    <div class="amount-value"><f:formatNumber value="${totalRevenue}" type="number"
                                                                              pattern="#,##0"/></div>
                                </div>
                            </div>
                        </div>
                        <div class="card-right">
                            <svg id="SvgjsSvg9460" width="80" height="42" xmlns="http://www.w3.org/2000/svg"
                                 xmlns:xlink="http://www.w3.org/1999/xlink"
                                 class="apexcharts-svg" transform="translate(0, 0)"
                                 style="background: transparent;">
                                <foreignObject x="0" y="0" width="80" height="42">
                                    <div class="apexcharts-legend" xmlns="http://www.w3.org/1999/xhtml"
                                         style="max-height: 21px;"></div>
                                </foreignObject>
                                <rect id="SvgjsRect9465" width="0" height="0" x="0" y="0" rx="0" ry="0" opacity="1"
                                      stroke-width="0" stroke="none" stroke-dasharray="0" fill="#fefefe"></rect>
                                <g id="SvgjsG9498" class="apexcharts-yaxis" rel="0" transform="translate(-18, 0)"></g>
                                <g id="SvgjsG9462" class="apexcharts-inner apexcharts-graphical"
                                   transform="translate(0, -3)">
                                    <defs id="SvgjsDefs9461">
                                        <clipPath id="gridRectMaskg6jfhpnn">
                                            <rect id="SvgjsRect9467" width="86" height="47" x="-3" y="-1" rx="0" ry="0"
                                                  opacity="1" stroke-width="0" stroke="none" stroke-dasharray="0"
                                                  fill="#fff"></rect>
                                        </clipPath>
                                        <clipPath id="forecastMaskg6jfhpnn"></clipPath>
                                        <clipPath id="nonForecastMaskg6jfhpnn"></clipPath>
                                        <clipPath id="gridRectMarkerMaskg6jfhpnn">
                                            <rect id="SvgjsRect9468" width="84" height="49" x="-2" y="-2" rx="0" ry="0"
                                                  opacity="1" stroke-width="0" stroke="none" stroke-dasharray="0"
                                                  fill="#fff"></rect>
                                        </clipPath>
                                        <linearGradient id="SvgjsLinearGradient9473" x1="0" y1="0" x2="0" y2="1">
                                            <stop id="SvgjsStop9474" stop-opacity="0.75"
                                                  stop-color="rgba(0,184,242,0.75)" offset="0"></stop>
                                            <stop id="SvgjsStop9475" stop-opacity="0.3" stop-color="#00b8f200"
                                                  offset="1"></stop>
                                            <stop id="SvgjsStop9476" stop-opacity="0.3" stop-color="#00b8f200"
                                                  offset="1"></stop>
                                        </linearGradient>
                                    </defs>
                                    <g id="SvgjsG9479" class="apexcharts-grid">
                                        <g id="SvgjsG9480" class="apexcharts-gridlines-horizontal"></g>
                                        <g id="SvgjsG9481" class="apexcharts-gridlines-vertical"></g>
                                        <line id="SvgjsLine9484" x1="0" y1="45" x2="80" y2="45" stroke="transparent"
                                              stroke-dasharray="0" stroke-linecap="butt"></line>
                                        <line id="SvgjsLine9483" x1="0" y1="1" x2="0" y2="45" stroke="transparent"
                                              stroke-dasharray="0" stroke-linecap="butt"></line>
                                    </g>
                                    <g id="SvgjsG9469" class="apexcharts-area-series apexcharts-plot-series">
                                        <g id="SvgjsG9470" class="apexcharts-series" seriesName="series1"
                                           data:longestSeries="true" rel="1" data:realIndex="0">
                                            <path id="SvgjsPath9477"
                                                  d="M 0 45 L 0 37.5C 3.5 37.5 6.5 22.5 10 22.5C 13.5 22.5 16.5 33 20 33C 23.5 33 26.5 28.5 30 28.5C 33.5 28.5 36.5 36 40 36C 43.5 36 46.5 25.5 50 25.5C 53.5 25.5 56.5 34.5 60 34.5C 63.5 34.5 66.5 7.5 70 7.5C 73.5 7.5 76.5 30 80 30C 80 30 80 30 80 45M 80 30z"
                                                  fill="url(#SvgjsLinearGradient9473)" fill-opacity="1"
                                                  stroke-opacity="1" stroke-linecap="round" stroke-width="0"
                                                  stroke-dasharray="0" class="apexcharts-area" index="0"
                                                  clip-path="url(#gridRectMaskg6jfhpnn)"
                                                  pathTo="M 0 45 L 0 37.5C 3.5 37.5 6.5 22.5 10 22.5C 13.5 22.5 16.5 33 20 33C 23.5 33 26.5 28.5 30 28.5C 33.5 28.5 36.5 36 40 36C 43.5 36 46.5 25.5 50 25.5C 53.5 25.5 56.5 34.5 60 34.5C 63.5 34.5 66.5 7.5 70 7.5C 73.5 7.5 76.5 30 80 30C 80 30 80 30 80 45M 80 30z"
                                                  pathFrom="M -1 90 L -1 90 L 10 90 L 20 90 L 30 90 L 40 90 L 50 90 L 60 90 L 70 90 L 80 90"></path>
                                            <path id="SvgjsPath9478"
                                                  d="M 0 37.5C 3.5 37.5 6.5 22.5 10 22.5C 13.5 22.5 16.5 33 20 33C 23.5 33 26.5 28.5 30 28.5C 33.5 28.5 36.5 36 40 36C 43.5 36 46.5 25.5 50 25.5C 53.5 25.5 56.5 34.5 60 34.5C 63.5 34.5 66.5 7.5 70 7.5C 73.5 7.5 76.5 30 80 30"
                                                  fill="none" fill-opacity="1" stroke="#00b8f2" stroke-opacity="1"
                                                  stroke-linecap="round" stroke-width="2" stroke-dasharray="0"
                                                  class="apexcharts-area" index="0"
                                                  clip-path="url(#gridRectMaskg6jfhpnn)"
                                                  pathTo="M 0 37.5C 3.5 37.5 6.5 22.5 10 22.5C 13.5 22.5 16.5 33 20 33C 23.5 33 26.5 28.5 30 28.5C 33.5 28.5 36.5 36 40 36C 43.5 36 46.5 25.5 50 25.5C 53.5 25.5 56.5 34.5 60 34.5C 63.5 34.5 66.5 7.5 70 7.5C 73.5 7.5 76.5 30 80 30"
                                                  pathFrom="M -1 90 L -1 90 L 10 90 L 20 90 L 30 90 L 40 90 L 50 90 L 60 90 L 70 90 L 80 90"
                                                  fill-rule="evenodd"></path>
                                            <g id="SvgjsG9471"
                                               class="apexcharts-series-markers-wrap apexcharts-hidden-element-shown"
                                               data:realIndex="0">
                                                <g class="apexcharts-series-markers">
                                                    <circle id="SvgjsCircle9502" r="0" cx="50" cy="25.5"
                                                            class="apexcharts-marker w9486a4km no-pointer-events"
                                                            stroke="#ffffff" fill="#00b8f2" fill-opacity="1"
                                                            stroke-width="2" stroke-opacity="0.9"
                                                            default-marker-size="0"></circle>
                                                </g>
                                            </g>
                                        </g>
                                        <g id="SvgjsG9472" class="apexcharts-datalabels" data:realIndex="0"></g>
                                    </g>
                                    <g id="SvgjsG9482" class="apexcharts-grid-borders"></g>
                                    <line id="SvgjsLine9485" x1="0" y1="0" x2="80" y2="0" stroke="#b6b6b6"
                                          stroke-dasharray="0" stroke-width="1" stroke-linecap="butt"
                                          class="apexcharts-ycrosshairs"></line>
                                    <line id="SvgjsLine9486" x1="0" y1="0" x2="80" y2="0" stroke-dasharray="0"
                                          stroke-width="0" stroke-linecap="butt"
                                          class="apexcharts-ycrosshairs-hidden"></line>
                                    <g id="SvgjsG9487" class="apexcharts-xaxis" transform="translate(0, 0)">
                                        <g id="SvgjsG9488" class="apexcharts-xaxis-texts-g"
                                           transform="translate(0, 4)"></g>
                                    </g>
                                    <g id="SvgjsG9499" class="apexcharts-yaxis-annotations"></g>
                                    <g id="SvgjsG9500" class="apexcharts-xaxis-annotations"></g>
                                    <g id="SvgjsG9501" class="apexcharts-point-annotations"></g>
                                </g>
                            </svg>
                        </div>
                    </div>
                    <c:choose>
                        <c:when test="${revenueCountChange >= 0}">
                            <p class="text-sm mt-2 mb-0" style="color: #485563; font-size: 15px">
                                Tăng <span class="bg-success-focus px-1 rounded-2 fw-medium text-success-main text-sm">+<f:formatNumber
                                    value="${revenueCountChange}" type="number" pattern="#,##0"/></span>
                                trong tuần này
                            </p>
                        </c:when>
                        <c:when test="${revenueCountChange < 0}">
                            <p class="text-sm mt-2 mb-0" style="color: #485563; font-size: 15px">
                                Giảm <span
                                    class="bg-danger-focus px-1 rounded-2 fw-medium text-danger-main text-sm"><f:formatNumber
                                    value="${revenueCountChange}" type="number" pattern="#,##0"/></span>
                                trong tuần này
                            </p>
                        </c:when>
                    </c:choose>
                </div>
            </div>
            <div class="col-12 col-md-6 col-lg-4">
                <div class="card-wrapper bg-gradient-end-3">
                    <div>
                        <div class="">
                            <div class="d-flex gap-3">
                                <div class="card-left dark-blue">
                                    <i class="fa-solid fa-syringe" style="width:24.75px; height:24.75px; font-size:24.75px;"></i>
                                </div>
                                <div>
                                    <span class="title">Tổng vắc xin còn hàng</span>
                                    <div class="amount-value">${countInStock}</div>
                                </div>
                            </div>
                        </div>
                        <div class="card-right">
                            <svg id="SvgjsSvg1724" width="80" height="42" xmlns="http://www.w3.org/2000/svg" version="1.1" transform="translate(0, 0)" style="background: transparent;">
                                <foreignObject x="0" y="0" width="80" height="42">
                                    <div class="apexcharts-legend" xmlns="http://www.w3.org/1999/xhtml" style="max-height: 21px;"></div>
                                </foreignObject>
                                <rect id="SvgjsRect1729" width="0" height="0" x="0" y="0" rx="0" ry="0" opacity="1" stroke-width="0" stroke="none" stroke-dasharray="0" fill="#fefefe"></rect>
                                <g id="SvgjsG1762" class="apexcharts-yaxis" rel="0" transform="translate(-18, 0)"></g>
                                <g id="SvgjsG1726" class="apexcharts-inner apexcharts-graphical" transform="translate(0, -3)">
                                    <defs id="SvgjsDefs1725">
                                        <clipPath id="gridRectMaskjbwqio8z">
                                            <rect id="SvgjsRect1731" width="86" height="47" x="-3" y="-1" rx="0" ry="0" opacity="1" stroke-width="0" stroke="none" stroke-dasharray="0" fill="#fff"></rect>
                                        </clipPath>
                                        <clipPath id="forecastMaskjbwqio8z"></clipPath>
                                        <clipPath id="nonForecastMaskjbwqio8z"></clipPath>
                                        <clipPath id="gridRectMarkerMaskjbwqio8z">
                                            <rect id="SvgjsRect1732" width="84" height="49" x="-2" y="-2" rx="0" ry="0" opacity="1" stroke-width="0" stroke="none" stroke-dasharray="0" fill="#fff"></rect>
                                        </clipPath>
                                        <linearGradient id="SvgjsLinearGradient1737" x1="0" y1="0" x2="0" y2="1">
                                            <stop id="SvgjsStop1738" stop-opacity="0.75" stop-color="rgba(72,127,255,0.75)" offset="0"></stop>
                                            <stop id="SvgjsStop1739" stop-opacity="0.3" stop-color="#487fff00" offset="1"></stop>
                                            <stop id="SvgjsStop1740" stop-opacity="0.3" stop-color="#487fff00" offset="1"></stop>
                                        </linearGradient>
                                    </defs>
                                    <g id="SvgjsG1743" class="apexcharts-grid">
                                        <g id="SvgjsG1744" class="apexcharts-gridlines-horizontal"></g>
                                        <g id="SvgjsG1745" class="apexcharts-gridlines-vertical"></g>
                                        <!-- Các line bên trong grid bị xóa nếu có stroke-dasharray -->
                                        <line id="SvgjsLine1748" x1="0" y1="45" x2="80" y2="45" stroke="transparent" stroke-dasharray="0" stroke-linecap="butt"></line>
                                        <line id="SvgjsLine1747" x1="0" y1="1" x2="0" y2="45" stroke="transparent" stroke-dasharray="0" stroke-linecap="butt"></line>
                                    </g>
                                    <g id="SvgjsG1733" class="apexcharts-area-series apexcharts-plot-series">
                                        <g id="SvgjsG1734" class="apexcharts-series" seriesName="series1" data:longestSeries="true" rel="1" data:realIndex="0">
                                            <path id="SvgjsPath1741" d="M 0 45 L 0 37.5C 3.5 37.5 6.5 22.5 10 22.5C 13.5 22.5 16.5 33 20 33C 23.5 33 26.5 28.5 30 28.5C 33.5 28.5 36.5 36 40 36C 43.5 36 46.5 25.5 50 25.5C 53.5 25.5 56.5 34.5 60 34.5C 63.5 34.5 66.5 7.5 70 7.5C 73.5 7.5 76.5 30 80 30C 80 30 80 30 80 45M 80 30z" fill="url(#SvgjsLinearGradient1737)" fill-opacity="1" stroke-opacity="1" stroke-linecap="round" stroke-width="0" stroke-dasharray="0" class="apexcharts-area" index="0" clip-path="url(#gridRectMaskjbwqio8z)"></path>
                                            <path id="SvgjsPath1742" d="M 0 37.5C 3.5 37.5 6.5 22.5 10 22.5C 13.5 22.5 16.5 33 20 33C 23.5 33 26.5 28.5 30 28.5C 33.5 28.5 36.5 36 40 36C 43.5 36 46.5 25.5 50 25.5C 53.5 25.5 56.5 34.5 60 34.5C 63.5 34.5 66.5 7.5 70 7.5C 73.5 7.5 76.5 30 80 30" fill="none" stroke="#487fff" stroke-opacity="1" stroke-linecap="round" stroke-width="2" class="apexcharts-area" index="0" clip-path="url(#gridRectMaskjbwqio8z)"></path>
                                            <g id="SvgjsG1735" class="apexcharts-series-markers-wrap apexcharts-hidden-element-shown" data:realIndex="0">
                                                <g class="apexcharts-series-markers">
                                                    <circle id="SvgjsCircle1766" r="0" cx="0" cy="0" class="apexcharts-marker w39oozns9 no-pointer-events" stroke="#ffffff" fill="#487fff" fill-opacity="1" stroke-width="2" stroke-opacity="0.9"></circle>
                                                </g>
                                            </g>
                                        </g>
                                        <g id="SvgjsG1736" class="apexcharts-datalabels" data:realIndex="0"></g>
                                    </g>
                                    <g id="SvgjsG1746" class="apexcharts-grid-borders"></g>
                                    <line id="SvgjsLine1750" x1="0" y1="0" x2="80" y2="0" stroke-dasharray="0" stroke-width="0" stroke-linecap="butt" class="apexcharts-ycrosshairs-hidden"></line>
                                    <g id="SvgjsG1751" class="apexcharts-xaxis" transform="translate(0, 0)">
                                        <g id="SvgjsG1752" class="apexcharts-xaxis-texts-g" transform="translate(0, 4)"></g>
                                    </g>
                                    <g id="SvgjsG1763" class="apexcharts-yaxis-annotations"></g>
                                    <g id="SvgjsG1764" class="apexcharts-xaxis-annotations"></g>
                                    <g id="SvgjsG1765" class="apexcharts-point-annotations"></g>
                                </g>
                            </svg>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-12 col-md-6 col-lg-4">
                <div class="card-wrapper bg-gradient-end-4">
                    <div>
                        <div class="">
                            <div class="d-flex gap-3">
                                <div class="card-left dark-red">
                                    <i class="fas fa-syringe" style="width:24.75px; height:24.75px; font-size:24.75px;"></i>
                                </div>
                                <div>
                                    <span class="title">Tổng vắc xin hết hàng</span>
                                    <div class="amount-value">${countOutOfStock}</div>
                                </div>
                            </div>
                        </div>
                        <div class="card-right">
                            <svg id="SvgjsSvgRed" width="80" height="42" xmlns="http://www.w3.org/2000/svg" version="1.1" style="background: transparent;">
                                <foreignObject x="0" y="0" width="80" height="42">
                                    <div class="apexcharts-legend" xmlns="http://www.w3.org/1999/xhtml" style="max-height: 21px;"></div>
                                </foreignObject>
                                <rect width="0" height="0" x="0" y="0" fill="#fefefe"></rect>
                                <g class="apexcharts-yaxis" transform="translate(-18, 0)"></g>
                                <g class="apexcharts-inner apexcharts-graphical" transform="translate(0, -3)">
                                    <defs>
                                        <clipPath id="gridRectMaskRed">
                                            <rect width="86" height="47" x="-3" y="-1" fill="#fff"></rect>
                                        </clipPath>
                                        <clipPath id="forecastMaskRed"></clipPath>
                                        <clipPath id="nonForecastMaskRed"></clipPath>
                                        <clipPath id="gridRectMarkerMaskRed">
                                            <rect width="84" height="49" x="-2" y="-2" fill="#fff"></rect>
                                        </clipPath>
                                        <linearGradient id="SvgjsLinearGradientRed" x1="0" y1="0" x2="0" y2="1">
                                            <stop stop-opacity="0.75" stop-color="rgba(255,77,77,0.75)" offset="0"></stop>
                                            <stop stop-opacity="0.3" stop-color="rgba(255,77,77,0)" offset="1"></stop>
                                        </linearGradient>
                                    </defs>
                                    <g class="apexcharts-grid">
                                        <line x1="0" y1="45" x2="80" y2="45" stroke="transparent"></line>
                                        <line x1="0" y1="1" x2="0" y2="45" stroke="transparent"></line>
                                    </g>
                                    <g class="apexcharts-area-series apexcharts-plot-series">
                                        <g class="apexcharts-series" seriesName="series1">
                                            <path d="M 0 45 L 0 37.5C 3.5 37.5 6.5 22.5 10 22.5C 13.5 22.5 16.5 33 20 33C 23.5 33 26.5 28.5 30 28.5C 33.5 28.5 36.5 36 40 36C 43.5 36 46.5 25.5 50 25.5C 53.5 25.5 56.5 34.5 60 34.5C 63.5 34.5 66.5 7.5 70 7.5C 73.5 7.5 76.5 30 80 30C 80 30 80 30 80 45M 80 30z"
                                                  fill="url(#SvgjsLinearGradientRed)" stroke-width="0"></path>
                                            <path d="M 0 37.5C 3.5 37.5 6.5 22.5 10 22.5C 13.5 22.5 16.5 33 20 33C 23.5 33 26.5 28.5 30 28.5C 33.5 28.5 36.5 36 40 36C 43.5 36 46.5 25.5 50 25.5C 53.5 25.5 56.5 34.5 60 34.5C 63.5 34.5 66.5 7.5 70 7.5C 73.5 7.5 76.5 30 80 30"
                                                  fill="none" stroke="#ff4d4d" stroke-width="2"></path>
                                            <g class="apexcharts-series-markers-wrap">
                                                <g class="apexcharts-series-markers">
                                                    <circle r="0" cx="0" cy="0" stroke="#ffffff" fill="#ff4d4d" stroke-width="2"></circle>
                                                </g>
                                            </g>
                                        </g>
                                    </g>
                                    <line x1="0" y1="0" x2="80" y2="0" class="apexcharts-ycrosshairs-hidden"></line>
                                    <g class="apexcharts-xaxis" transform="translate(0, 0)">
                                        <g class="apexcharts-xaxis-texts-g" transform="translate(0, 4)"></g>
                                    </g>
                                    <g class="apexcharts-yaxis-annotations"></g>
                                    <g class="apexcharts-xaxis-annotations"></g>
                                    <g class="apexcharts-point-annotations"></g>
                                </g>
                            </svg>

                        </div>
                    </div>
                </div>
            </div>
            <%-- Phan kho  --%>
            <%--            <div class="col-12 col-md-6 col-lg-4">--%>
            <%--                <div class="card-wrapper light-blue">--%>
            <%--                    <div class="card-left dark-blue">--%>
            <%--                        <i class="fa-solid fa-tags fa-2x"></i>--%>
            <%--                    </div>--%>
            <%--                    <div class="card-right">--%>
            <%--                        <span class="title">Tổng vắc xin</span>--%>
            <%--                        <div class="amount-value">${totalVacine} vắc xin</div>--%>
            <%--                    </div>--%>
            <%--                </div>--%>
            <%--            </div>--%>

            <%--            <div class="col-12 col-md-6 col-lg-4">--%>
            <%--                <div class="card-wrapper light-red">--%>
            <%--                    <div class="card-left dark-red">--%>
            <%--                        <i class="fa-solid fa-id-badge fa-2x"></i>--%>
            <%--                    </div>--%>
            <%--                    <div class="card-right">--%>
            <%--                        <span class="title">Hết hàng</span>--%>
            <%--                        <div class="amount-value">${totalExpire} vắc xin</div>--%>
            <%--                    </div>--%>
            <%--                </div>--%>
            <%--            </div>--%>
        </div>
    </div>
    <div class="tabular-wrapper">
        <h5 class="main-title">Người dùng đăng ký trong tháng</h5>
        <div class="table-container">
            <table class="w-100 table table-striped" id="user">
                <thead>
                <tr>
                    <th>Tài khoản</th>
                    <th>Giới tính</th>
                    <th>Ngày đăng ký</th>
                    <th>Tình trạng</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="ur" items="${userRegister}">
                    <tr>
                        <td>
                            <div>${ur.fullname}</div>
                            <div class="user-email">${ur.email}</div>
                        </td>
                        <td class="align-middle">${ur.gender}</td>
                        <td class="align-middle">
                            <f:formatDate value="${ur.createdAt}" pattern="dd-MM-yyyy"/>
                        </td>
                        <td class="align-middle">
                            <select class="status" name="status" data-id="${ur.id}" disabled>
                                <option value="-1" class="status-select" ${ur.status == -1 ? 'selected' : ''}>Khóa
                                    tài khoản
                                </option>
                                <option value="0" class="status-select" ${ur.status == 0 ? 'selected' : ''}>Chưa xác
                                    thực
                                </option>
                                <option value="1" class="status-select" ${ur.status == 1 ? 'selected' : ''}>Đã xác
                                    thực
                                </option>
                            </select>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
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
