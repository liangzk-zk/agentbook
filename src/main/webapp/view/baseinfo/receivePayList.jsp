<%@page pageEncoding="UTF-8" contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <%@ include file="/view/common/common-pubilc.jsp" %>
    <title>收款项目列表</title>
    <%-- <script type="text/javascript" src="${contextPath}/resources/js/user/userList.js"></script> --%>
    <link rel="stylesheet" href="${contextPath}/resources/assets/css/demo.css"/>
    <script type="text/javascript" src="${contextPath}/resources/js/baseinfo/receivePay/receivePayList.js"></script>
    <style type="text/css">
      .modal-dialog {
          max-width: 80%;
          margin: 1.75rem auto;
      }
      .modal-content {
          height: 800px;
      }
    </style>
</head>
<body>
<div class="page-inner">
    <div class="page-header">
        <h4 class="page-title">收款项目管理</h4>
        <ul class="breadcrumbs">
            <li class="nav-home"><a href="#"> <i class="flaticon-home"></i>
            </a></li>
            <li class="separator"><i class="flaticon-right-arrow"></i></li>
            <li class="nav-item"><a href="#">收款项目管理</a></li>
            <li class="separator"><i class="flaticon-right-arrow"></i></li>
            <li class="nav-item"><a href="#">收款项目管理</a></li>
        </ul>
    </div>
    <div class="row">
        <div class="card col-md-2">
            <div class="card-header">
                <div class="d-flex align-items-center">
                    <button class="btn btn-round ml-auto" id="addIncomeCategoryInfo" data-toggle="modal"
                            data-target="#addIncomeCategoryInfoModal">
                        <i class="fa fa-plus"></i>分类
                    </button>
                </div>
            </div>
            <div class="card-body">
                <ul class="ztree" id="incomeCategoryTree"></ul>
            </div>
        </div>
        <div class="col-md-10">
            <div class="card">
                <div class="card-header">
                    <div class="d-flex align-items-center">
                        <button class="btn btn-primary btn-round ml-auto" id="addRow" data-toggle="modal"
                                data-target="#addRowModal">
                            <i class="fa fa-plus"></i> 新增
                        </button>
                    </div>
                </div>
                <div class="card-body">
                    <!-- Modal -->
                    <div class="table-responsive">
                        <table id="receivePayList" class="display table table-striped table-hover">
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="addRowModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header no-bd">
                    <h5 class="modal-title">
                        <span class="fw-mediumbold">新</span> <span class="fw-light">增</span>
                    </h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="关闭">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="row">
                            <input id="id" type="text" style="display: none" class="form-control"
                                   placeholder="fill name">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <em style="color: red;">*</em> <label>编码</label> <input id="code"
                                                                                            type="text"
                                                                                            class="form-control"
                                                                                            placeholder="编码">
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <div class="form-group">
                                    <em style="color: #ff0000;">*</em> <label>类别</label> <input
                                        id="receivepayname" type="text"
                                        class="form-control" placeholder="所属分类"> <input
                                        id="receivepaytype"
                                        type="hidden"
                                        style="display: none"
                                        class="form-control">
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <div class="form-group">
                                    <ul class="ztree" id="classifiTree" style="display: none"></ul>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label>名称</label> <input id="name" type="text" class="form-control"
                                                             placeholder="名称">
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label>备注</label> <input id="remarks" type="text"
                                                             class="form-control" placeholder="备注">
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label>计算提成</label> <input id="iscommission" type="text"
                                                               class="form-control"
                                                               placeholder="计算提成">
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer no-bd">
                    <button type="button" id="addButton" class="btn btn-primary">确定</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="addIncomeCategoryInfoModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header no-bd">
                    <h5 class="modal-title">
                        <span class="fw-mediumbold">新</span> <span class="fw-light">增</span>
                    </h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="关闭">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <iframe id="contentSubFrame" src="<%= contextPath%>/pcIncomeCategory/page/incomeCategoryList" width="100%" height="100%" frameborder=0></iframe>
                </div>
                <div class="modal-footer no-bd">
                    <%--<button type="button" id="addButton" class="btn btn-primary">确定</button>--%>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>