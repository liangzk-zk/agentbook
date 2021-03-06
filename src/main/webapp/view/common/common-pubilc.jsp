<%@page pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Comparator"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="java.util.Locale"%>
    <%
String contextPath = request.getContextPath();
ApplicationContext applicationContext = WebApplicationContextUtils
        .getRequiredWebApplicationContext(getServletConfig().getServletContext());
%>
<c:set var="contextPath" value="<%= contextPath%>"/>
<script type="text/javascript">
  /**
   * 全局变量。
   */
  var GlobalParam = new Object();
  GlobalParam.context = "${contextPath}";
  GlobalParam.localPath = window.location.host+GlobalParam.context;
  window.contextPath  = "${contextPath}";
</script>
<%-- <link rel="stylesheet" href="${contextPath}/resources/plugins/font-awesome/css/font-awesome.min.css"> --%>
<!--登陆login CSS Files -->
<link rel="icon" href="${contextPath}/resources/assets/img/icon.ico" type="image/x-icon"/>
<link rel="stylesheet" href="${contextPath}/resources/assets/css/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="${contextPath}/resources/assets/css/azzara.min.css" type="text/css">
<!-- 公共JS -->
<!-- bootstrap/jquery -->
<script src="${contextPath}/resources/plugins/jquery/jquery.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/plugins/placeholders.js" type="text/javascript"></script>
<!-- 登陆login JS Files -->
<script src="${contextPath}/resources/assets/js/plugin/webfont/webfont.min.js" type="text/javascript"></script>
<script>
        WebFont.load({
            google: {"families":["Open+Sans:300,400,600,700"]},
            custom: {"families":["Flaticon", "Font Awesome 5 Solid", "Font Awesome 5 Regular", "Font Awesome 5 Brands"], urls: ['${contextPath}/resources/assets/css/fonts.css']},
            active: function() {
                sessionStorage.fonts = true;
            }
        });
    </script>
<script src="${contextPath}/resources/assets/js/plugin/jquery-ui-1.12.1.custom/jquery-ui.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/assets/js/core/popper.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/assets/js/core/bootstrap.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/assets/js/ready.js" type="text/javascript"></script>
<script src="${contextPath}/resources/assets/js/plugin/jquery-ui-touch-punch/jquery.ui.touch-punch.min.js" type="text/javascript"></script>
<!-- Sweet Alert -->
<script src="${contextPath}/resources/assets/js/plugin/sweetalert/sweetalert.min.js" type="text/javascript"></script>
<!-- Bootstrap Toggle -->
<script src="${contextPath}/resources/assets/js/plugin/bootstrap-toggle/bootstrap-toggle.min.js" type="text/javascript"></script>
<!-- jQuery Scrollbar -->
<script src="${contextPath}/resources/assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js" type="text/javascript"></script>
<!-- Datatables -->
<script src="${contextPath}/resources/assets/js/plugin/datatables/datatables.min.js" type="text/javascript"></script>
<%-- <script src="${contextPath}/resources/plugins/dataTables/DataTables-1.10.18/js/jquery.dataTables.min.js"></script> --%>
<!-- Azzara JS -->
<script src="${contextPath}/resources/assets/js/setting-demo.js" type="text/javascript"></script>
<script src="${contextPath}/resources/js/util/util.js" type="text/javascript"></script>
<script src="${contextPath}/resources/plugins/ajaxFileUpload/ajaxfileupload.js" type="text/javascript"></script>
<script src="${contextPath}/resources/plugins/moment.min.js" type="text/javascript"></script>
<!-- Ztree相关引入 -->
<!-- Ztree相关引入 -->
<script src="${contextPath}/resources/plugins/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<%-- <script src="${contextPath}/resources/plugins/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<script src="${contextPath}/resources/plugins/ztree/js/jquery.ztree.exhide-3.5.min.js"></script> --%>
<link href="${contextPath}/resources/plugins/ztree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>