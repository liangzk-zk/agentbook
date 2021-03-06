$(function() {
  var fieldMapping = {
    userId : 'account',
    name : "creator",
    createTime : "createTime"
  };
  $("#paymentedList")
      .DataTable(
          {
            columns : [
                {
                  data : "id",
                  'bSortable' : false,
                  className : "text-center",
                  width : "5%",
                  render : function(data) {
                    return "<label class=\"mt-checkbox mt-checkbox-outline\"><input type=\"checkbox\" name=\"userListChk\" value=\""+data+"\"><span></span></label>";
                  }
                }, {
                  title : "userId",
                  data : "userId",
                  width : "10%",
                  visible:false,
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "userId")
                  }
                }, {
                  title : "账号",
                  data : "account",
                  width : "15%",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "account")
                  }
                }, {
                  title : "姓名",
                  data : "creator",
                  width : "15%",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "creator")
                  }
                }, {
                  title : "创建时间",
                  data : "createTime",
                  width : "15%",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "createTime")
                  },
                  render: function(data, type, row) {
                return moment(data).format("YYYY-MM-DD HH:ss:mm");
              }
                }, {
                  title : "凭证",
                  data : "voucherURL",
                  width : "20%",
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "voucherURL");
                  },
                  render : function(data, type, row, meta) {
                    return "<div class=\"avatar avatar-xs\">"
                    + "<img src=\""+data+"\" alt=\"...\" class=\"avatar-img\">"
                    + "</div>";
                  }
                } ],
            columnDefs : [ {
              targets : 6,// 自定义列的序号，从0开始
              title : "操作",
              data : "id", // 需要引用的数据列，一般是主键
              render : function(data, type, full) {
                return "<div class=\"form-button-action\">"
                    + "<button type=\"button\" cmd=\"viewBtn\" data-toggle=\"tooltip\" title=\"\" "
                    + "class=\"btn btn-link btn-primary\" data-original-title=\"查看凭证\">"
                    + "<i class=\"fas fa-eye\"></i>查看凭证</button>"
                    + "<button type=\"button\" data-toggle=\"tooltip\" title=\"\" "
                    + "class=\"btn btn-link btn-danger\" cmd=\"deleteBtn\" data-original-title=\"删除\">"
                    + "<i class=\"fa fa-times\"></i>删除</button>";
              }
            } ],
            "order" : [ [ 4, 'asc' ] ],
            "orderCellsTop" : true,
            info : true,
            bLengthChange : true,
            "bDestroy" : true,
            colReorder : true,
            "aaSorting" : [], // 禁用默认第一列排序
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
              exprs.push({
                 name : 'isRecharge',
                 dataType:'int',
                 op : 'eq',
                 intValue : 1
              });
              if (data.search && data.search.value && data.search.value != '') {
                var creator = {};
                creator.name = 'creator';
                creator.op = "like";
                creator.dataType = "str";
                creator.stringValue = "%" + data.search.value + "%";
                var account = {};
                account.name = 'account';
                account.op = "like";
                account.dataType = "str";
                account.stringValue = "%" + data.search.value + "%";
                var value = [];
                value.push(creator);
                value.push(account);
                exprs.push({
                  dataType : 'exps',
                  op : 'or',
                  expValue : value
                });
              }
              var data = {
                "expressions" : exprs,
                "startPosition" : offset,
                "maxResults" : limit,
                "orderBy" : orderBy,
              };
              $.ajax({
                type : "POST",
                url : GlobalParam.context
                    + "/rest/paymentVoucher/getPaymentVoucherList",
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
                    alert(ret.msg);
                  }
                }
              });
            },
            "fnCreatedRow" : function(nRow, aData, iDataIndex) {
              if (typeof (aData.id) != "undefined") {
                $(nRow).attr("id", aData.id);
                $(nRow).attr("rowIndex", iDataIndex);
              }
              $(nRow).on("click",function() {
                if ($(nRow).find("input[type='checkbox']").prop(
                    "checked")) {
                  // $(nRow).removeClass("selected");
                  $(nRow).find("input[type='checkbox']").prop(
                      "checked", false);
                  if ($('#paymentedList tbody tr input:checkbox[name="paymentedListChk"]:checked').length == 0) {
                    $("#allparameterList .rowSelected").addClass(
                        "disabled");
                  }
                } else {
                  $("#paymentedList").dataTable.$("tr.selected").find(
                      "input[type='checkbox']").prop("checked", false);
                  $("#paymentedList").dataTable.$("tr.selected")
                      .removeClass("selected");
                  $(nRow).addClass("selected");
                  $(nRow).find("input[type='checkbox']").prop(
                      "checked", true);
                }
              });
            },
            "fnDrawCallback" : function(oSettings) {
              $('button[cmd="viewBtn"]').click(function(e) {
                var tr = this.parentNode.parentNode.parentNode;
                var voucherURL = $(tr).find('td[model="voucherURL"]').find(".avatar-img").attr("src");;
                swal(
                    {
                      title : '转账凭证',
                      html : true,
                      content : {
                        element : "img",
                        attributes : {
                          src : voucherURL,
                          className : "avatar-img"
                        },
                      },
                      buttons : {
                        confirm : {
                          className : 'btn btn-success'
                        }
                      },
                    }).then(
                    function() {
//                      swal("", "You entered : "
//                          + $('#input-field').val(), "success");
                    });
              });
              $('button[cmd="deleteBtn"]').click(function(e) {
                var tr = this.parentNode.parentNode.parentNode;
                var id = $(tr).attr("id");;
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
                          url : GlobalParam.context + "/rest/paymentVoucher/deletePaymentVoucher/"+id,
                          contentType : 'application/json',
                          success : function(ret) {
                            $('#paymentedList').DataTable().draw(true);
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
              // 重新计算iframe高度，且左边树高度需要等于右边表格数据
              //$('.role-tree').innerHeight($('.role-content').height());
              //refreshIframeHeight();
            }
          });
});