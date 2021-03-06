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
<title>会员列表</title>
<%-- <script type="text/javascript" src="${contextPath}/resources/js/user/userList.js"></script> --%>
<link rel="stylesheet" href="${contextPath}/resources/assets/css/demo.css"/>
<script type="text/javascript" src="${contextPath}/resources/js/user/userSubList.js"></script>
</head>
<body>
  <div class="page-inner">
    <div class="row">
      <div class="col-md-12">
        <div class="card">
          <div class="card-header">
            <div class="d-flex align-items-center">
              <!-- <button class="btn btn-primary btn-round ml-auto" id="addRow" data-toggle="modal" data-target="#addRowModal">
                <i class="fa fa-plus"></i> 新增
              </button> -->
              <button class="btn btn-primary btn-round ml-3" style="display:none" id="viewSubRow" data-toggle="modal" data-target="#subRowModal">
                <i class="fa fa-plus"></i> 查看下级
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
                                <input id="id" type="text" style="display: none" class="form-control" placeholder="fill name">
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default">
                                            <em style="color: red;">*</em>
                                            <label>账户</label>
                                            <input id="account" type="text" class="form-control" placeholder="fill name">
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <label>姓名</label>
                                            <input id="name" type="text" class="form-control" placeholder="fill position">
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <label>昵称</label>
                                            <input id="nickName" type="text" class="form-control" placeholder="fill office">
                                        </div>
                                    </div>
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default">
                                            <label>推荐人账户</label>
                                            <input id="referrerAccount" type="text" class="form-control" placeholder="fill position">
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <em style="color: red;">*</em>
                                            <label>密码</label>
                                            <input id="passwd" type="text" class="form-control" placeholder="fill office" onblur="checkpas1();">
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group form-group-default">
                                            <em style="color: red;">*</em>
                                            <label>确认密码</label>
                                            <input id="confirmpasswd" type="text" class="form-control" placeholder="fill office" onChange="checkpas();">
                                        </div>
                                        <span class="tip" style="display:none;color: red;">两次输入的密码不一致</span><br>
                                    </div>
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default">
                                            <label>支付密码</label>
                                            <input id="payPasswd" type="text" class="form-control" placeholder="fill office">
                                        </div>
                                    </div>
                                    <div class="col-sm-12">
                                        <div class="form-group form-group-default">
                                            <em style="color: red;">*</em>
                                            <label>联系方式</label>
                                            <input id="telephone" type="text" class="form-control" placeholder="fill office">
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
            <div class="modal fade" id="subRowModal" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header no-bd">
                            <h5 class="modal-title">
                                <span class="fw-mediumbold">查</span> 
                                <span class="fw-light">看</span>
                            </h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="关闭">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <iframe id="contentSubFrame" width="100%" height="100%" frameborder=0></iframe>
                        </div>
                        <div class="modal-footer no-bd">
                            <!-- <button type="button" id="addButton" class="btn btn-primary">确定</button> -->
                            <button type="button" class="btn btn-danger" data-dismiss="modal">关闭</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="table-responsive">
              <table id="userSubList" class="display table table-striped table-hover">
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