<%@page pageEncoding="UTF-8" contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>列表测试</title>

<link href="${contextPath}/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="${contextPath}/resources/plugins/dataTables/DataTables-1.10.18/css/dataTables.bootstrap.css" rel="stylesheet" />
<link href="${contextPath}/resources/plugins/dataTables/Buttons-1.5.1/css/buttons.dataTables.css" rel="stylesheet" />

</head>
<body>
  <div class="container">
    <div class="row">
      <div class="text-center">
      <h2>列表测试</h2>
      </div>
    </div>
    <div class="row">
      <div class="col-md-10">
        <table id="example" class="table table-striped table-bordered" style="width: 100%">
          </div>
          </div>
        </table>
      </div>

      <script src="${contextPath}/dwr/engine.js"></script>
      <script src="${contextPath}/dwr/util.js"></script>
      <script src="${contextPath}/dwr/interface/dwrWorkflowListService.js" type="text/javascript"></script>


      <script type="text/javascript" src="${contextPath}/resources/plugins//jquery/jquery.min.js"></script>
      <script type="text/javascript" src="${contextPath}/resources/plugins/dataTables/dataTables-1.10.18/js/jquery.dataTables.js"></script>
      <script type="text/javascript" src="${contextPath}/resources/plugins/dataTables/dataTables-1.10.18/js/dataTables.bootstrap.js"></script>
      <script type="text/javascript" src="${contextPath}/resources/plugins/dataTables/Buttons-1.5.1/js/dataTables.buttons.js"></script>
      <script type="text/javascript" src="${contextPath}/resources/plugins/dataTables/Buttons-1.5.1/js/buttons.colVis.js"></script>
      <script type="text/javascript" src="${contextPath}/resources/plugins/dataTables/ColReorderWithResize.js"></script>

      <script type="text/javascript">
              $(document).ready(function() {
                var fieldMapping = {
                  flowInstanceId : 'ID'
                };
                $('#example').DataTable({
                  columns : [ {
                    title : 'ID',
                    data : 'flowInstanceId',
                    width: '30px'
                  }, {
                    title : '标题',
                    data : 'title'
                  } , {
                    title : '流程名称',
                    data : 'flowName'
                  }],
                  language : {
                    "sProcessing" : "处理中...",
                    "sLengthMenu" : "显示 _MENU_ 项结果",
                    "sZeroRecords" : "没有匹配结果",
                    "sInfo" : "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                    "sInfoEmpty" : "显示第 0 至 0 项结果，共 0 项",
                    "sInfoFiltered" : "(由 _MAX_ 项结果过滤)",
                    "sInfoPostFix" : "",
                    "sSearch" : "搜索:",
                    "sUrl" : "",
                    "sEmptyTable" : "表中数据为空",
                    "sLoadingRecords" : "载入中...",
                    "sInfoThousands" : ",",
                    "oPaginate" : {
                      "sFirst" : "首页",
                      "sPrevious" : "上页",
                      "sNext" : "下页",
                      "sLast" : "末页"
                    },
                    "oAria" : {
                      "sSortAscending" : ": 以升序排列此列",
                      "sSortDescending" : ": 以降序排列此列"
                    },
                    buttons : {
                      colvis : "可见列"
                    }
                  },
                  dom : 'Bfrtip',
                  buttons : [ {
                    extend : 'colvis',
                    collectionLayout : 'fixed two-column'
                  } ],
                  colReorder : true,
                  serverSide : true,
                  ajax : function(data, cb, settings) {
                    var limit = data.length;
                    var offset = data.start;
                    var orders = data.order || [];
                    var orderBy = '';
                    var columns = data.columns;
                    for (var i = 0; i < orders.length; i++) {
                      if (i > 0) {
                        orderBy += ',';
                      }
                      var field = columns[orders[i].column].data;
                      orderBy += fieldMapping[field] ? fieldMapping[field] : field;
                      orderBy += ' ';
                      orderBy += orders[i].dir;
                    }
                    var exprs = [];
                    if (data.search && data.search.value && data.search.value != '') {
                      exprs.push({
                        name : 'title',
                        op : 'like',
                        stringValue : '%' + data.search.value + '%'
                      });
                    }

                    dwrWorkflowListService.findEntryList(false, exprs, offset, limit, orderBy, {
                      callback : function(ret) {
                        cb({
                          recordsTotal : ret.totalSize,
                          recordsFiltered : ret.totalSize,
                          data : ret.data
                        });

                      }
                    });
                  }
                });
              });
            </script>
</body>
</html>
