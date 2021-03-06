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
<title>往来分类列表</title>
<%-- <script type="text/javascript" src="${contextPath}/resources/js/user/userList.js"></script> --%>
<link rel="stylesheet" href="${contextPath}/resources/assets/css/demo.css" />
<style type="text/css">
.form-check, .form-group1 {
  margin-bottom: 0;
  padding: 2px
}

.card .card-header1 {
  padding: 0rem 0.25rem;
}

.card-title1 {
  font-size: 10px;
}
</style>
<script type="text/javascript" src="${contextPath}/resources/js/customerInfo/customerInfoList.js"></script>
</head>
<body>
  <div class="page-inner">
    <div class="page-header">
      <h4 class="page-title">客户信息</h4>
      <ul class="breadcrumbs">
        <li class="nav-home"><a href="#"> <i class="flaticon-home"></i>
        </a></li>
        <li class="separator"><i class="flaticon-right-arrow"></i></li>
        <li class="nav-item"><a href="#">客户信息</a></li>
        <li class="separator"><i class="flaticon-right-arrow"></i></li>
        <li class="nav-item"><a href="#">客户信息</a></li>
      </ul>
    </div>
    <div class="row">
      <div class="card col-md-2">
        <div class="card-header">
          <div class="d-flex align-items-center">
            <button class="btn btn-round ml-auto" id="addClassifiInfo" data-toggle="modal"
              data-target="#addClassifiInfoModal">
              <i class="fa fa-plus"></i>分类
            </button>
          </div>
        </div>
        <div class="card-body">
          <ul class="ztree" id="transactionTree"></ul>
        </div>
      </div>
      <div class="col-md-10">
        <div class="form-inline">
          <div class="col-md-7">
            <div class="card">
              <div class="card-body">
                <div class="card-header card-header1">
                  <div class="card-title card-title1" style="">基本信息</div>
                </div>
                <div class="card-body">
                  <div class="form-group form-group1">
                    <label for="squareInput">客户编码</label> <input type="text" class="form-control form-control-sm"
                      id="smallInput" placeholder="客户编码"> <label for="squareInput">电话</label> <input type="text"
                      class="form-control form-control-sm" id="smallInput" placeholder="电话"> <label
                      for="squareInput">备注一</label> <input type="text" class="form-control form-control-sm"
                      id="smallInput" placeholder="备注一">
                  </div>
                  <div class="form-group form-group1">
                    <label for="squareInput">客户分类</label> <input type="text" class="form-control form-control-sm"
                      id="smallInput" placeholder="客户分类"> <label for="squareInput">收集</label> <input type="text"
                      class="form-control form-control-sm" id="smallInput" placeholder="收集"> <label
                      for="squareInput">备注二</label> <input type="text" class="form-control form-control-sm"
                      id="smallInput" placeholder="备注二">
                  </div>
                  <div class="form-group form-group1">
                    <label for="squareInput">客户名称</label> <input type="text" class="form-control form-control-sm"
                      id="smallInput" placeholder="客户名称"> <label for="squareInput">联系人</label> <input
                      type="text" class="form-control form-control-sm" id="smallInput" placeholder="联系人"> <label
                      for="squareInput">拿票地址</label> <input type="text" class="form-control form-control-sm"
                      id="smallInput" placeholder="拿票地址">
                  </div>
                  <div class="form-group form-group1">
                    <label for="squareInput">自编码</label> <input type="text" class="form-control form-control-sm"
                      id="smallInput" placeholder="自编码"> <label for="squareInput">地址</label> <input type="text"
                      class="form-control form-control-sm" id="smallInput" placeholder="地址">
                  </div>
                </div>
                <div class="card-header card-header1">
                  <div class="card-title card-title1" style="">企业信息</div>
                </div>
                <div class="card-body">
                  <div class="form-group form-group1">
                    <label for="squareInput">信用代码</label> <input type="text" class="form-control form-control-sm"
                      id="smallInput" placeholder="信用代码"> <label for="squareInput">电话</label> <input type="text"
                      class="form-control form-control-sm" id="smallInput" placeholder="电话"> <label
                      for="squareInput">备注一</label> <input type="text" class="form-control form-control-sm"
                      id="smallInput" placeholder="备注一">
                  </div>
                  <div class="form-group form-group1">
                    <label for="squareInput">客户分类</label> <input type="text" class="form-control form-control-sm"
                      id="smallInput" placeholder="客户分类"> <label for="squareInput">收集</label> <input type="text"
                      class="form-control form-control-sm" id="smallInput" placeholder="收集"> <label
                      for="squareInput">备注二</label> <input type="text" class="form-control form-control-sm"
                      id="smallInput" placeholder="备注二">
                  </div>
                  <div class="form-group form-group1">
                    <label for="squareInput">客户名称</label> <input type="text" class="form-control form-control-sm"
                      id="smallInput" placeholder="客户名称"> <label for="squareInput">联系人</label> <input
                      type="text" class="form-control form-control-sm" id="smallInput" placeholder="联系人"> <label
                      for="squareInput">拿票地址</label> <input type="text" class="form-control form-control-sm"
                      id="smallInput" placeholder="拿票地址">
                  </div>
                  <div class="form-group form-group1">
                    <label for="squareInput">自编码</label> <input type="text" class="form-control form-control-sm"
                      id="smallInput" placeholder="自编码"> <label for="squareInput">地址</label> <input type="text"
                      class="form-control form-control-sm" id="smallInput" placeholder="地址">
                  </div>
                </div>
                <div class="modal fade" id="addClassifiInfoModal" tabindex="-1" role="dialog" aria-hidden="true">
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
                              placeholder="fill name"> <input id="companyId" type="text" style="display: none"
                              value="1" class="form-control" placeholder="fill name">
                            <div class="col-sm-12">
                              <div class="form-group">
                                <em style="color: red;">*</em> <label>所属分类</label> <input id="parentClass" type="text"
                                  class="form-control" placeholder="所属分类"> <input id="parentId" type="hidden"
                                  style="display: none" class="form-control">
                                <ul class="ztree" id="classifiTree" style="display: none"></ul>
                              </div>

                            </div>
                            <div class="col-sm-12">
                              <div class="form-group">
                                <em style="color: red;">*</em> <label>编码</label> <input id="code" type="text"
                                  class="form-control" placeholder="编码">
                              </div>
                            </div>
                            <div class="col-sm-12">
                              <div class="form-group">
                                <em style="color: red;">*</em> <label>名称</label> <input id="name" type="text"
                                  class="form-control" placeholder="名称">
                              </div>
                            </div>
                            <div class="col-sm-12">
                              <div class="form-group">
                                <em style="color: red;">*</em> <label>助记码</label> <input id="mnemonicCode" type="text"
                                  class="form-control" placeholder="助记码">
                              </div>
                            </div>
                            <div class="col-sm-12">
                              <div class="form-group">
                                <label>备注</label> <input id="remarks" type="text" class="form-control" placeholder="备注">
                              </div>
                            </div>
                          </div>
                        </form>
                      </div>
                      <div class="modal-footer no-bd">
                        <button type="button" id="addClassifiInfoButton" class="btn btn-primary">确定</button>
                        <button type="button" class="btn btn-danger" data-dismiss="modal">关闭</button>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="card-action">
                  <button class="btn btn-success">Submit</button>
                  <button class="btn btn-danger">Cancel</button>
                </div>
                <div class="table-responsive">
                  <table id="classificationList" class="display table table-striped table-hover">
                  </table>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-5">
            <div class="card">
              <div class="card-header">
                <div class="d-flex align-items-center">
                  <button class="btn btn-primary btn-round ml-auto" id="addCustomerInfo" data-toggle="modal"
                    data-target="#addRowModal">
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
                              placeholder="fill name"> <input id="companyId" type="text" style="display: none"
                              value="1" class="form-control" placeholder="fill name">
                            <div class="col-sm-12">
                              <div class="form-group form-group-default">
                                <em style="color: red;">*</em> <label>编码</label> <input id="code" type="text"
                                  class="form-control" placeholder="编码">
                              </div>
                            </div>
                            <div class="col-sm-12">
                              <div class="form-group form-group-default">
                                <em style="color: red;">*</em> <label>名称</label> <input id="name" type="text"
                                  class="form-control" placeholder="名称">
                              </div>
                            </div>
                            <div class="col-sm-12">
                              <div class="form-group form-group-default">
                                <em style="color: red;">*</em> <label>助记码</label> <input id="mnemonicCode" type="text"
                                  class="form-control" placeholder="助记码">
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
                <div class="table-responsive">
                  <table id="classificationList" class="display table table-striped table-hover">
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="card">
          <div class="card-header">
            <div class="d-flex align-items-center">
              <button class="btn btn-primary btn-round ml-auto" id="addCustomerInfo" data-toggle="modal"
                data-target="#addRowModal">
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
                      <span class="fw-mediumbold">新</span> <span class="fw-light">增</span>
                    </h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="关闭">
                      <span aria-hidden="true">&times;</span>
                    </button>
                  </div>
                  <div class="modal-body">
                    <form>
                      <div class="row">
                        <input id="id" type="text" style="display: none" class="form-control" placeholder="fill name">
                        <input id="companyId" type="text" style="display: none" value="1" class="form-control"
                          placeholder="fill name">
                        <div class="col-sm-12">
                          <div class="form-group form-group-default">
                            <em style="color: red;">*</em> <label>编码</label> <input id="code" type="text"
                              class="form-control" placeholder="编码">
                          </div>
                        </div>
                        <div class="col-sm-12">
                          <div class="form-group form-group-default">
                            <em style="color: red;">*</em> <label>名称</label> <input id="name" type="text"
                              class="form-control" placeholder="名称">
                          </div>
                        </div>
                        <div class="col-sm-12">
                          <div class="form-group form-group-default">
                            <em style="color: red;">*</em> <label>助记码</label> <input id="mnemonicCode" type="text"
                              class="form-control" placeholder="助记码">
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
            <div class="table-responsive">
              <table id="classificationList" class="display table table-striped table-hover">
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>