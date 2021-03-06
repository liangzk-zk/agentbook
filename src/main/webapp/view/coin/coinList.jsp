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
<title>数字货币列表</title>
<%-- <script type="text/javascript" src="${contextPath}/resources/js/user/userList.js"></script> --%>
<link rel="stylesheet" href="${contextPath}/resources/assets/css/demo.css"/>
<script type="text/javascript" src="${contextPath}/resources/js/coin/coinList.js"></script>
</head>
<body>
  <div class="page-inner">
    <div class="page-header">
      <h4 class="page-title">数字货币管理</h4>
      <ul class="breadcrumbs">
        <li class="nav-home"><a href="#"> <i class="flaticon-home"></i>
        </a></li>
        <li class="separator"><i class="flaticon-right-arrow"></i></li>
        <li class="nav-item"><a href="#">数字货币管理</a></li>
        <li class="separator"><i class="flaticon-right-arrow"></i></li>
        <li class="nav-item"><a href="#">数字货币管理</a></li>
      </ul>
    </div>
    <div class="row">
      <div class="col-md-12">
        <div class="card">
          <div class="card-header">
            <div class="d-flex align-items-center">
              <button class="btn btn-primary btn-round ml-auto" id="addRow" data-toggle="modal" data-target="#addRowModal">
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
                      <span class="fw-mediumbold">
                      新</span> 
                      <span class="fw-light">
                        增
                      </span>
                    </h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                      <span aria-hidden="true">&times;</span>
                    </button>
                  </div>
                  <div class="modal-body">
                    <p class="small">Create a new row using this form, make sure you fill them all</p>
                    <form>
                      <div class="row">
                        <div class="col-sm-12">
                          <input id="id" type="text" hidden="true" class="form-control" placeholder="名称">
                          <div class="form-group form-group-default">
                            <em style="color: red;">*</em>
                            <label>名称</label>
                            <input id="coinName" type="text" class="form-control" placeholder="名称">
                          </div>
                        </div>
                        <div class="col-md-6 pr-0">
                          <div class="form-group form-group-default">
                            <em style="color: red;">*</em>
                            <label>类型</label>
                            <select class="form-control" id="coinType" disabled>
                              <option value="1" selected>比特币</option>
                            </select>
                          </div>
                        </div>
                        <div class="col-md-6">
                          <div class="form-group form-group-default">
                            <em style="color: red;">*</em>
                            <label>单价</label>
                            <input id="coinPrice" type="text" class="form-control" placeholder="充值金额">
                          </div>
                        </div>
                        <div class="col-md-6" style="display: none">
                          <div class="form-group form-group-default">
                            <label>启用</label>
                            <input id="isEnable" type="text" readonly="readonly" class="form-control" placeholder="启用" value="1">
                          </div>
                        </div>
                      </div>
                    </form>
                  </div>
                  <div class="modal-footer no-bd">
                    <button type="button" id="addRowButton" class="btn btn-primary">Add</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                  </div>
                </div>
              </div>
            </div>
            <div class="table-responsive">
              <table id="coinList" class="display table table-striped table-hover">
              </table>
              <!-- <table id="add-row" class="display table table-striped table-hover">
                     
                      <tbody>
                        <tr>
                          <td>Tiger Nixon</td>
                          <td>System Architect</td>
                          <td>Edinburgh</td>
                          <td>
                            <div class="form-button-action">
                              <button type="button" data-toggle="tooltip" title=""
                                class="btn btn-link btn-primary btn-lg" data-original-title="Edit Task">
                                <i class="fa fa-edit"></i>
                              </button>
                              <button type="button" data-toggle="tooltip" title="" class="btn btn-link btn-danger"
                                data-original-title="Remove">
                                <i class="fa fa-times"></i>
                              </button>
                            </div>
                          </td>
                        </tr>
                      </tbody>
                    </table> -->
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>