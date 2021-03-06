<%@page pageEncoding="UTF-8" contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <%@ include file="/view/common/common-pubilc.jsp" %>
    <title>控制台</title>
    <link rel="stylesheet" href="${contextPath}/resources/assets/css/demo.css"/>
    <script src="${contextPath}/resources/js/main/main.js"></script>
</head>
<body>
<div class="wrapper">
    <!--
                Tip 1: You can change the background color of the main header using: data-background-color="blue | purple | light-blue | green | orange | red"
        -->
    <div class="main-header" data-background-color="purple">
      <!-- Logo Header -->
      <div class="logo-header">

        <a class="logo"> <img src="${contextPath}/resources/assets/img/logoazzara.svg" alt="navbar brand"
          class="navbar-brand">
        </a>
        <button class="navbar-toggler sidenav-toggler ml-auto" type="button" data-toggle="collapse"
          data-target="collapse" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"> <i class="fa fa-bars"></i>
          </span>
        </button>
        <button class="topbar-toggler more">
          <i class="fa fa-ellipsis-v"></i>
        </button>
        <div class="navbar-minimize">
          <button class="btn btn-minimize btn-rounded">
            <i class="fa fa-bars"></i>
          </button>
        </div>
      </div>
      <!-- End Logo Header -->

      <!-- Navbar Header -->
      <nav class="navbar navbar-header navbar-expand-lg">
      </nav>
      <!-- End Navbar -->
    </div>
    <!-- Sidebar -->
    <div class="sidebar">
      <div class="sidebar-wrapper scrollbar-inner">
        <div class="sidebar-content">
          <div class="user">
            <div class="avatar-sm float-left mr-2">
              <img src="${contextPath}/resources/assets/img/profile.jpg" alt="..." class="avatar-img rounded-circle">
            </div>
            <div class="info">
              <a data-toggle="collapse" href="#collapseExample" aria-expanded="true"> <span> Hizrian <span
                  class="user-level">Administrator</span> <span class="caret"></span>
              </span>
              </a>
              <div class="clearfix"></div>

              <div class="collapse in" id="collapseExample">
                <ul class="nav">
                  <li><a href="#profile"> <span class="link-collapse">My Profile</span>
                  </a></li>
                  <li><a href="#edit"> <span class="link-collapse">Edit Profile</span>
                  </a></li>
                  <li><a href="#settings"> <span class="link-collapse">Settings</span>
                  </a></li>
                </ul>
              </div>
            </div>
          </div>
          <ul class="nav" id="menu">
            <li class="nav-item"><a href="../index.html"> <i class="fas fa-home"></i>
                <p>Dashboard</p> <span class="badge badge-count">5</span>
            </a></li>
            <li class="nav-section"><span class="sidebar-mini-icon"> <i class="fa fa-ellipsis-h"></i>
            </span>
              <h4 class="text-section">Components</h4></li>
            <li id="baseinfo" class="nav-item active submenu"><a data-toggle="collapse" href="#baseinfoManager" aria-expanded="true"> <i
                class="fas fa-table"></i>
                <p>基础设置</p> <span class="caret"></span>
            </a>
              <div class="collapse" id="baseinfoManager">
                <ul class="nav nav-collapse">
                  <li class="active"><a inner-href="pcFundAccount/page/fundAccountList"> <span class="sub-item">资金账户管理</span>
                  </a></li>
                  <li class="active"><a href="pcReceivePay/page/receivePayList" target="view_window"> <span class="sub-item">收款项目</span>
                  </a></li>
                  <li class="active"><a href="pcPayment/page/paymentList" target="view_window"> <span class="sub-item">付款项目</span>
                  </a></li>
                  <li class="active"><a href="pcCash/page/cashedList" target="view_window"> <span class="sub-item">部门信息</span>
                  </a></li>
                  <li class="active"><a href="pcCash/page/cashedList" target="view_window"> <span class="sub-item">客户信息</span>
                  </a></li>
                  <li class="active"><a href="pcSalesman/page/salesmanList" target="view_window"> <span class="sub-item">业务员信息</span>
                  </a></li>
                </ul>
              </div></li>
            <li id="user" class="nav-item active submenu"><a data-toggle="collapse" href="#userManager" aria-expanded="true"> <i
                class="fas fa-table"></i>
                <p>客户管理</p> <span class="caret"></span>
            </a>
              <div class="collapse" id="userManager">
                <ul class="nav nav-collapse">
                  <li class="active"><a href="pcCustomerInfo/page/customerInfoList" target="view_window"> <span class="sub-item">客户管理</span>
                  </a></li>
                </ul>
              </div></li>
          </ul>
        </div>
      </div>
    </div>
    <div class="main-panel">
      <iframe id="contentFrame" width="100%" height="100%" frameborder=0></iframe>
      <!-- <div class="content"> -->
      </div> 
    </div>
    <!-- Custom template | don't include it in your project! -->
    <div class="custom-template">
      <div class="title">Settings</div>
      <div class="custom-content">
        <div class="switcher">
          <div class="switch-block">
            <h4>Topbar</h4>
            <div class="btnSwitch">
              <button type="button" class="changeMainHeaderColor" data-color="blue"></button>
              <button type="button" class="selected changeMainHeaderColor" data-color="purple"></button>
              <button type="button" class="changeMainHeaderColor" data-color="light-blue"></button>
              <button type="button" class="changeMainHeaderColor" data-color="green"></button>
              <button type="button" class="changeMainHeaderColor" data-color="orange"></button>
              <button type="button" class="changeMainHeaderColor" data-color="red"></button>
            </div>
          </div>
          <div class="switch-block">
            <h4>Background</h4>
            <div class="btnSwitch">
              <button type="button" class="changeBackgroundColor" data-color="bg2"></button>
              <button type="button" class="changeBackgroundColor selected" data-color="bg1"></button>
              <button type="button" class="changeBackgroundColor" data-color="bg3"></button>
            </div>
          </div>
        </div>
      </div>
      <div class="custom-toggle">
        <i class="flaticon-settings"></i>
      </div>
    </div>
  </div>
</body>
</html>