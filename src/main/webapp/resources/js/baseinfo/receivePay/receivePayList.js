$(function() {
  var fieldMapping = {
    code : "code",
    selfcode : "selfcode",
    name : "name"
  };
  var loadTreeObj = function(){
    var ztreeObj;
    var settings = {
        check : {
          enable : true
        },
        view : {
          selectedMulti : false,
        // addHoverDom: addHoverDom,
        // removeHoverDom: removeHoverDom,
        },
        data : {
          key : {
            name : "name"
          },
          simpleData : {
            enable : true,
            idKey : "id",
            pIdKey : "pId"
          }
        },
        async : {
          enable : true,
          url : GlobalParam.context + "/rest/baseinfo/incomeCategory/getIncomeCategoryList",
          autoParam : [ "id"],
          dataType : 'json'
        },
    };
    $.ajax({
      type : "POST",
      url : GlobalParam.context + "/rest/baseinfo/incomeCategory/getIncomeCategoryList",
      async:true,
      data : {
        id:1
      },
      dataType: "json",
      success : function(ret) {
        if (ret) {
          ztreeObj = $.fn.zTree.init($("#incomeCategoryTree"), settings,ret);
          var nodes = ztreeObj.getNodes();
          ztreeObj.expandNode(nodes[0], true, false, false);
        }
      }
    });
    return ztreeObj;
  };
  var incomeCategoryObj = loadTreeObj();
  $("#receivePayList")
      .DataTable(
          {
            columns : [
                {
                  data : "id",
                  'bSortable' : false,
                  className : "text-center",
                  width : "5%",
                  render : function(data) {
                    return "<label class=\"mt-checkbox mt-checkbox-outline\"><input type=\"checkbox\" name=\"receivePayListChk\" value=\""
                        + data + "\"><span></span></label>";
                  }
                }, {
                  title : "编码",
                  data : "code",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "code")
                  }
                }, {
                  title : "类别",
                  data : "receivepaytype",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "receivepaytype")
                  }
                },{
                  title : "名称",
                  data : "name",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "name")
                  }
                }, {
                  title : "是否计算提成",
                  data : "iscommission",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "iscommission")
                  }
                }, {
                  title : "备注",
                  data : "remarks",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "remarks")
                  }
                }],
            columnDefs : [ {
              targets : 6,// 自定义列的序号，从0开始
              title : "",
              data : "id", // 需要引用的数据列，一般是主键
              render : function(data, type, full) {
                return "<div class=\"form-button-action\">"
                    + "<button type=\"button\" data-toggle=\"tooltip\" title=\"\" "
                    + "class=\"btn btn-link btn-primary\" cmd=\"editBtn\" data-original-title=\"编辑\">"
                    + "<i class=\"fa fa-edit\"></i>编辑</button>"
                    + "<button type=\"button\" data-toggle=\"tooltip\" title=\"\" "
                    + "class=\"btn btn-link btn-danger\" cmd=\"deleteBtn\" data-original-title=\"删除\">"
                    + "<i class=\"fa fa-times\"></i>删除</button>";
              }
            } ],
            "order" : [ [ 2, 'asc' ] ],
            "orderCellsTop" : true,
            info : true,
            bLengthChange : true,
            "bDestroy" : true,
            colReorder : true,
            "aaSorting" : [], // 禁用默认第一列排序
            bFilter : false,
            "bAutoWidth" : false,
            "sDom" : 'Rlfrtip',
            "headerContextMenu" : true,
            "oColReorder" : {
              "headerContextMenu" : true
            },
            "pagingType" : 'full_numbers',
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
              // exprs.push({
              // name : 'keyId',
              // op : 'eq',
              // stringValue : parentId
              // });
              if (data.search && data.search.value && data.search.value != '') {
                var code = {};
                code.name = 'code';
                code.op = "like";
                code.stringValue = "%" + data.search.value + "%";
                var selfcode = {};
                selfcode.name = 'selfcode';
                selfcode.op = "like";
                selfcode.stringValue = "%" + data.search.value + "%";
                var name = {};
                name.name = 'name';
                name.op = "like";
                name.stringValue = "%" + data.search.value + "%";
                var value = [];
                value.push(code);
                value.push(selfcode);
                value.push(name);
                exprs.push({
                  op : 'or',
                  expValue : value
                });
                exprs.push(value);
              }
              var data = {
                "expressions" : exprs,
                "startPosition" : offset,
                "maxResults" : limit,
                "orderBy" : orderBy,
              };
              $.ajax({
                type : "POST",
                url : GlobalParam.context + "/rest/baseinfo/receivePay/getReceivePayList",
                data : JSON.stringify(data),
                contentType : 'application/json',
                success : function(ret) {
                  if (ret.code == 200) {
                    cb({
                      recordsTotal : ret.data.totalSize,
                      recordsFiltered : ret.data.totalSize,
                      data : ret.data.datas
                    });
                  } else {
                    swal({
                      title : '提示',
                      text : ret.msg,
                      buttons : {
                        confirm : {
                          className : 'btn btn-success'
                        }
                      },
                    });
                  }
                }
              });
            },
            "fnCreatedRow" : function(nRow, aData, iDataIndex) {
              if (typeof (aData.id) != "undefined") {
                $(nRow).attr("id", aData.id);
                $(nRow).attr("rowIndex", iDataIndex);
              }
              $(nRow)
                  .on(
                      "click",
                      function() {
                        if ($(nRow).find("input[type='checkbox']").prop(
                            "checked")) {
                          // $(nRow).removeClass("selected");
                          $(nRow).find("input[type='checkbox']").prop(
                              "checked", false);
                          if ($('#receivePayList tbody tr input:checkbox[name="receivePayListChk"]:checked').length == 0) {
                            $("#allparameterList .rowSelected").addClass(
                                "disabled");
                          }
                        } else {
                          $("#receivePayList").dataTable.$("tr.selected").find(
                              "input[type='checkbox']").prop("checked", false);
                          $("#receivePayList").dataTable.$("tr.selected")
                              .removeClass("selected");
                          $(nRow).addClass("selected");
                          $(nRow).find("input[type='checkbox']").prop(
                              "checked", true);
                        }
                      });
            },
            "fnDrawCallback" : function(oSettings) {
              $('button[cmd="editBtn"]')
                  .click(
                      function(e) {
                        var rowIndex = $(this).parents("tr").index();
                        var id = $('#receivePayList').DataTable().row(rowIndex).data().id;
                        var code = $('#receivePayList').DataTable().row(rowIndex).data().fundcode;
                        var name = $('#receivePayList').DataTable().row(rowIndex).data().fundname;
                        var receivepaytype = $('#receivePayList').DataTable().row(rowIndex).data().receivepaytype;
                        var iscommission = $('#receivePayList').DataTable().row(rowIndex).data().iscommission;
                        var remarks = $('#receivePayList').DataTable().row(rowIndex).data().remarks;
                        $("#addRow").click(
                            function() {
                              $('#addRowModal').on(
                                  'show.bs.modal',
                                  function(event) {
                                    var modal = $(this)
                                    modal.find('#ids').val(id)
                                    modal.find('#code').val(code)
                                    modal.find('#name').val(name)
                                    modal.find('#receivepaytype').val(receivepaytype)
                                    modal.find('#iscommission').val(iscommission)
                                    modal.find('#remarks').val(remarks)
                                  });
                            });
                        $("#addRow").click();
                      });
            }
          });
  $("#addButton").click(function(e) {
    var id = $("#id").val();
    var code = $("#code").val();
    var name = $("#name").val();
    var receivepaytype = $("#receivepaytype").val();
    var iscommission = $("#iscommission").val();
    var remarks = $("#remarks").val();
    var url = GlobalParam.context + "/rest/baseinfo/receivePay/addreceivePay";
    var type = "POST";
    var text = "新增成功！";
    if (id) {
      type = "PUT";
      url = GlobalParam.context + "/rest/baseinfo/receivePay/updatereceivePay";
      text = "更新成功！";
    }
    if (!fundcode) {
      swal({
        title : '提示',
        text : "编码不允许为空！",
        buttons : {
          confirm : {
            className : 'btn btn-success'
          }
        },
      });
    }
    if (!fundname) {
      swal({
        title : '提示',
        text : "名称不允许为空！",
        buttons : {
          confirm : {
            className : 'btn btn-success'
          }
        },
      });
    }
    var data = {
      "id" : id,
      "code" : code,
      "name" : name,
      "receivepaytype" : receivepaytype,
      "iscommission" : iscommission,
      "remarks": remarks
    };
    $.ajax({
      type : type,
      url : url,
      data : JSON.stringify(data),
      contentType : 'application/json',
      success : function(ret) {
        if (ret.code == 200) {
          $('#receivePayList').DataTable().draw(true);
          $('#addRowModal').modal('hide');
          swal({
            title : '提示',
            text : text,
            buttons : {
              confirm : {
                className : 'btn btn-success'
              }
            },
          });
        } else {
          swal({
            title : '提示',
            text : ret.msg,
            buttons : {
              confirm : {
                className : 'btn btn-success'
              }
            },
          });
        }
      }
    });
  });
});