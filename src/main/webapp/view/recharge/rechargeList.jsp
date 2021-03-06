<%@page pageEncoding="UTF-8" contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
  content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
<%@ include file="/view/common/common-pubilc.jsp"%>
<title>用户额度列表</title>
<%-- <script type="text/javascript" src="${contextPath}/resources/js/user/userList.js"></script> --%>
<link rel="stylesheet" href="${contextPath}/resources/assets/css/demo.css"/>
<script type="text/javascript" src="${contextPath}/resources/js/recharge/rechargeList.js"></script>
</head>
<body>
  <div class="page-inner">
    <div class="page-header">
      <h4 class="page-title">用户额度管理</h4>
      <ul class="breadcrumbs">
        <li class="nav-home"><a href="#"> <i class="flaticon-home"></i>
        </a></li>
        <li class="separator"><i class="flaticon-right-arrow"></i></li>
        <li class="nav-item"><a href="#">用户额度管理</a></li>
        <li class="separator"><i class="flaticon-right-arrow"></i></li>
        <li class="nav-item"><a href="#">用户额度管理</a></li>
      </ul>
    </div>
    <div class="row">
      <div class="col-md-12">
        <div class="card">
          <div class="card-header">
            <div class="d-flex align-items-center">
              <button class="btn btn-primary btn-round ml-auto" id="editRow" style="display: none" data-toggle="modal" data-target="#editRowModal">
                <i class="fa fa-plus"></i> 新增
              </button>
            </div>
          </div>
          <!-- Modal -->
            <div class="modal fade" id="editRowModal" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header no-bd">
                            <h5 class="modal-title">
                                <span class="fw-mediumbold">新</span> 
                                <span class="fw-light">增</span>
                            </h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="关闭">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="row">
                                    <input id="id" type="text" style="display: none" class="form-control" placeholder="fill name">
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <em style="color: red;">*</em>
                                            <label>额度</label>
                                            <input id="quota" type="text" class="form-control" placeholder="额度">
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <em style="color: red;">*</em>
                                            <label>星级</label>
                                            <input id="level" type="text" class="form-control" placeholder="星级">
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <em style="color: red;">*</em>
                                            <label>币数</label>
                                            <input id="coinnum" type="text" class="form-control" placeholder="币数">
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <em style="color: red;">*</em>
                                            <label>分红</label>
                                            <input id="dividends" type="text" class="form-control" placeholder="分红">
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <em style="color: red;">*</em>
                                            <label>现金点值</label>
                                            <input id="cashdividends" type="text" class="form-control" placeholder="现金点值">
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer no-bd">
                            <button type="button" id="editButton" class="btn btn-primary">确定</button>
                            <button type="button" class="btn btn-danger" data-dismiss="modal">关闭</button>
                        </div>
                    </div>
                </div>
            </div>
          <div class="card-body">
            <div class="table-responsive">
              <table id="rechargeList" class="display table table-striped table-hover">
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>