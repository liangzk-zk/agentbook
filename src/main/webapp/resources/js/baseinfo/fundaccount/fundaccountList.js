$(function() {
  var fieldMapping = {
    code : "code",
    selfcode : "selfcode",
    name : "name"
  };
  $("#fundaccountList")
      .DataTable(
          {
            columns : [
                {
                  data : "id",
                  'bSortable' : false,
                  className : "text-center",
                  width : "5%",
                  render : function(data) {
                    return "<label class=\"mt-checkbox mt-checkbox-outline\"><input type=\"checkbox\" name=\"fundaccountListChk\" value=\""
                        + data + "\"><span></span></label>";
                  }
                }, {
                  title : "资金编码",
                  data : "fundcode",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "fundcode")
                  }
                }, {
                  title : "资金名称",
                  data : "fundname",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "fundname")
                  }
                }, {
                  title : "助记码",
                  data : "selfcode",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "selfcode")
                  }
                }, {
                  title : "期初金额",
                  data : "intialamount",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "intialamount")
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
                url : GlobalParam.context + "/rest/baseinfo/fundAccount/getFundAccountList",
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
                          if ($('#fundaccountList tbody tr input:checkbox[name="fundaccountListChk"]:checked').length == 0) {
                            $("#allparameterList .rowSelected").addClass(
                                "disabled");
                          }
                        } else {
                          $("#fundaccountList").dataTable.$("tr.selected").find(
                              "input[type='checkbox']").prop("checked", false);
                          $("#fundaccountList").dataTable.$("tr.selected")
                              .removeClass("selected");
                          $(nRow).addClass("selected");
                          $(nRow).find("input[type='checkbox']").prop(
                              "checked", true);
                        }
                      });
            },
            "fnDrawCallback" : function(oSettings) {
              $('button[cmd="editBtn"]').click(function(e) {
                  var rowIndex = $(this).parents("tr").index();
                  var id = $('#fundaccountList').DataTable().row(rowIndex).data().id;
                  var fundcode = $('#fundaccountList').DataTable().row(rowIndex).data().fundcode;
                  var fundname = $('#fundaccountList').DataTable().row(rowIndex).data().fundname;
                  var selfcode = $('#fundaccountList').DataTable().row(rowIndex).data().selfcode;
                  var intialamount = $('#fundaccountList').DataTable().row(rowIndex).data().intialamount;
                  var remarks = $('#fundaccountList').DataTable().row(rowIndex).data().remarks;
                  $("#addRow").click(
                      function() {
                        $('#addRowModal').on(
                            'show.bs.modal',
                            function(event) {
                              var modal = $(this)
                              modal.find('#id').val(id)
                              modal.find('#fundcode').val(fundcode)
                              modal.find('#fundname').val(fundname)
                              modal.find('#selfcode').val(selfcode)
                              modal.find('#intialamount').val(intialamount)
                              modal.find('#remarks').val(remarks)
                            });
                      });
                  $("#addRow").click();
                });
              $('button[cmd="deleteBtn"]').click(function(e) {
                var rowIndex = $(this).parents("tr").index();
                var id = $('#fundaccountList').DataTable().row(rowIndex).data().id; 
                swal({
                      title : '确认删除',
                      html : true,
                      text : "是否确认删除？",
                      buttons : {
                        cancel: {
                          visible: true,
                          className: 'btn btn-danger'
                        }, 
                        confirm : {
                          className : 'btn btn-success'
                        }
                      },
                    }).then((Delete) => {
                      if (Delete) {
                        $.ajax({
                          type : "DELETE",
                          url : GlobalParam.context + "/rest/baseinfo/fundAccount/deleteFundAccount/"+id,
                          contentType : 'application/json',
                          success : function(ret) {
                            $('#fundaccountList').DataTable().draw(true);
                            swal({
                              title : '提示',
                              text: ret.msg,
                              buttons : {
                                confirm : {
                                  className : 'btn btn-success'
                                }
                              },
                            });
                          }
                        });
                      } else {
                        swal.close();
                      }
                    });
              });
            }
          });
  $("#addButton").click(function(e) {
    var id = $("#id").val();
    var fundcode = $("#fundcode").val();
    var fundname = $("#fundname").val();
    var selfcode = $("#selfcode").val();
    var intialamount = $("#intialamount").val();
    var remarks = $("#remarks").val();
    var url = GlobalParam.context + "/rest/baseinfo/fundAccount/addFundAccount";
    var type = "POST";
    var text = "新增成功！";
    if (id) {
      type = "PUT";
      url = GlobalParam.context + "/rest/baseinfo/fundAccount/updateFundAccount";
      text = "更新成功！";
    }
    if (!fundcode) {
      swal({
        title : '提示',
        text : "资金编码不允许为空！",
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
        text : "资金名称不允许为空！",
        buttons : {
          confirm : {
            className : 'btn btn-success'
          }
        },
      });
    }
    var data = {
      "id" : id,
      "fundcode" : fundcode,
      "fundname" : fundname,
      "selfcode" : selfcode,
      "intialamount" : intialamount,
      "remarks": remarks
    };
    $.ajax({
      type : type,
      url : url,
      data : JSON.stringify(data),
      contentType : 'application/json',
      success : function(ret) {
        $('#fundaccountList').DataTable().draw(true);
        $('#addRowModal').modal('hide');
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
    });
  });
});