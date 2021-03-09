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
<title>公司列表</title>
<%-- <script type="text/javascript" src="${contextPath}/resources/js/user/userList.js"></script> --%>
<link rel="stylesheet" href="${contextPath}/resources/assets/css/demo.css"/>
<script type="text/javascript" src="${contextPath}/resources/js/company/companyList.js"></script>
</head>
<body>
  <div class="page-inner">
    <div class="page-header">
      <h4 class="page-title">公司管理</h4>
      <ul class="breadcrumbs">
        <li class="nav-home"><a href="#"> <i class="flaticon-home"></i>
        </a></li>
        <li class="separator"><i class="flaticon-right-arrow"></i></li>
        <li class="nav-item"><a href="#">公司管理</a></li>
        <li class="separator"><i class="flaticon-right-arrow"></i></li>
        <li class="nav-item"><a href="#">公司管理</a></li>
      </ul>
    </div>
    <div class="row">
      <div class="col-md-12">
        <div class="card">
          <div class="card-header">
            <div class="d-flex align-items-center">
              <button class="btn btn-primary btn-round ml-auto" style="display:none" id="addRow" data-toggle="modal" data-target="#addRowModal">
                <i class="fa fa-plus"></i> 新增
              </button>
              <button class="btn btn-primary btn-round ml-3" style="display:none" id="uploadPay" data-toggle="modal" data-target="#uploadPayModal">
                <i class="fa fa-plus"></i> 新增
              </button>
            </div>
          </div>
          <div class="card-body">
            <!-- Modal -->
            <div class="modal fade" id="addRowModal" tabindex="-1" role="dialog" aria-hidden="true">
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
                                <input id="ids" type="text" style="display: none" class="form-control" placeholder="fill name">
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default">
                                            <em style="color: red;">*</em>
                                            <label>名称</label>
                                            <input id="companyname" type="text" class="form-control" placeholder="名称">
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <label>收款人</label>
                                            <input id="payee" type="text" class="form-control" placeholder="姓名">
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <label>银行卡开户行</label>
                                            <input id="bankname" type="text" class="form-control" placeholder="昵称">
                                        </div>
                                    </div>
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default">
                                            <label>银行卡号</label>
                                            <input id="cardid" type="text" class="form-control" placeholder="推荐人账户">
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <label>客服电话</label>
                                            <input id="telephone" type="text" class="form-control" placeholder="客服电话">
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
            <div class="modal fade" id="uploadPayModal" tabindex="-1" role="dialog" aria-hidden="true">
              <div class="modal-dialog" role="document">
                <div class="modal-content">
                  <div class="modal-header no-bd">
                    <h5 class="modal-title">
                      <span class="fw-mediumbold">
                      上</span> 
                      <span class="fw-light">
                        传
                      </span>
                    </h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                      <span aria-hidden="true">&times;</span>
                    </button>
                  </div>
                  <div class="modal-body">
                    <p class="small">上传收款码信息</p>
                    <form id="uploadPayForm" enctype ="multipart/form-data">
                      <div class="row">
                        <input id="id" type="text" style="display: none" class="form-control" placeholder="fill name">
                        <div class="col-sm-12">
                          <div class="form-group form-group-default">
                            <em style="color: red;">*</em>
                            <label>支付类型</label>
                            <select class="form-control" id="payType" >
                              <option value="0">微信收款码</option>
                              <option value="1" selected>支付宝收款码</option>
                              <option value="2">客服二维码</option>
                              <option value="3">公司简介</option>
                            </select>
                          </div>
                        </div>
                        <div class="form-group">
                            <em style="color: red;">*</em>
                            <label for="file">上传</label>
                            <input type="file" class="form-control-file" id="file" name="poster">
                        </div>
                      </div>
                    </form>
                  </div>
                  <div class="modal-footer no-bd">
                    <button type="button" id="uploadPayBtn" class="btn btn-primary">确定</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">关闭</button>
                  </div>
                </div>
              </div>
            </div>
            <div class="table-responsive">
              <table id="companyList" class="display table table-striped table-hover">
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>