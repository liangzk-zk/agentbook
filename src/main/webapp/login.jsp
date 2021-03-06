<%@page pageEncoding="UTF-8" contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <%@ include file="/view/common/common-pubilc.jsp" %>
    <title>登陆</title>
    <link rel="icon" href="${contextPath}/resources/assets/img/icon.ico" type="image/x-icon"/>
    <!-- Fonts and icons -->
    <script src="${contextPath}/resources/assets/js/plugin/webfont/webfont.min.js" type="text/javascript"></script>
    <%-- <script src="${contextPath}/resources/js/login/login.js"></script> --%>
</head>
<body class="login">
    <div class="wrapper wrapper-login">
        <div class="container container-login animated fadeIn">
            <h3 class="text-center">管理员登陆</h3>
            <form action="${contextPath}/login" method="post">
                <div class="login-form">
                  <div class="form-group form-floating-label">
                      <input id="username" name="username" type="text" class="form-control input-border-bottom" required>
                      <label for="username" class="placeholder">账户</label>
                  </div>
                  <div class="form-group form-floating-label">
                      <input id="password" name="password" type="password" class="form-control input-border-bottom" required>
                      <label for="password" class="placeholder">密码</label>
                      <div class="show-password">
                          <i class="flaticon-interface"></i>
                      </div>
                  </div>
                  <div class="row form-sub m-0">
                      <div class="custom-control custom-checkbox">
                          <input type="checkbox" class="custom-control-input" id="rememberme">
                          <label class="custom-control-label" for="rememberme">记住我</label>
                      </div>
                      
                      <a href="#" class="link float-right">忘记密码</a>
                  </div>
                  <div class="form-action mb-3">
                      <button href="#" class="btn btn-primary btn-rounded btn-login">登陆</button>
                  </div>
                  <div class="login-account">
                      <span class="msg">还没有账号？</span>
                      <a href="#" id="show-signup" class="link">注册</a>
                  </div>
              </div>
            </form>
        </div>

        <div class="container container-signup animated fadeIn">
            <h3 class="text-center">注册</h3>
            <div class="login-form">
                <div class="form-group form-floating-label">
                    <input  id="fullname" name="fullname" type="text" class="form-control input-border-bottom" required>
                    <label for="fullname" class="placeholder">名称</label>
                </div>
                <div class="form-group form-floating-label">
                    <input  id="email" name="email" type="email" class="form-control input-border-bottom" required>
                    <label for="email" class="placeholder">邮箱</label>
                </div>
                <div class="form-group form-floating-label">
                    <input  id="passwordsignin" name="passwordsignin" type="password" class="form-control input-border-bottom" required>
                    <label for="passwordsignin" class="placeholder">密码</label>
                    <div class="show-password">
                        <i class="flaticon-interface"></i>
                    </div>
                </div>
                <div class="form-group form-floating-label">
                    <input  id="confirmpassword" name="confirmpassword" type="password" class="form-control input-border-bottom" required>
                    <label for="confirmpassword" class="placeholder">确认密码</label>
                    <div class="show-password">
                        <i class="flaticon-interface"></i>
                    </div>
                </div>
                <div class="row form-sub m-0">
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" name="agree" id="agree">
                        <label class="custom-control-label" for="agree">I Agree the terms and conditions.</label>
                    </div>
                </div>
                <div class="form-action">
                    <a href="#" id="show-signin" class="btn btn-danger btn-rounded btn-login mr-3">取消</a>
                    <a href="#" class="btn btn-primary btn-rounded btn-login">注册</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>