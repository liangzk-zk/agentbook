$(function() {
  var fieldMapping = {
    name : "name"
  };
  $("#companyList")
      .DataTable(
          {
            columns : [
                {
                  data : "id",
                  'bSortable' : false,
                  className : "text-center",
                  width : "5%",
                  render : function(data) {
                    return "<label class=\"mt-checkbox mt-checkbox-outline\"><input type=\"checkbox\" name=\"levelListChk\" value=\""
                        + data + "\"><span></span></label>";
                  }
                }, {
                  title : "名称",
                  data : "companyname",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "companyname")
                  }
                }, {
                  title : "收款人",
                  data : "payee",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "payee")
                  }
                }, {
                  title : "银行卡开户行",
                  data : "bankname",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "bankname")
                  }
                }, {
                  title : "银行卡号",
                  data : "cardid",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "cardid")
                  }
                }, {
                  title : "客服",
                  data : "telephone",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "telephone")
                  },
                  render : function(data, type, row, meta) {
                    return "<div class=\"avatar avatar-xs\">"
                    + "<img src=\""+data+"\" alt=\"...\" class=\"avatar-img\">"
                    + "</div>";
                  }
                }, {
                  title : "微信收款码",
                  data : "wechatpay",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "cardid")
                  },
                  render : function(data, type, row, meta) {
                    return "<div class=\"avatar avatar-xs\">"
                    + "<img src=\""+data+"\" alt=\"...\" class=\"avatar-img\">"
                    + "</div>";
                  }
                }, {
                  title : "支付宝收款码",
                  data : "alipay",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "alipay")
                  },
                  render : function(data, type, row, meta) {
                    return "<div class=\"avatar avatar-xs\">"
                    + "<img src=\""+data+"\" alt=\"...\" class=\"avatar-img\">"
                    + "</div>";
                  }
                }],
            columnDefs : [ {
              targets : 8,// 自定义列的序号，从0开始
              title : "",
              data : "id", // 需要引用的数据列，一般是主键
              render : function(data, type, full) {
                return "<div class=\"form-button-action\">"
                    + "<button type=\"button\" data-toggle=\"tooltip\" title=\"\" "
                    + "class=\"btn btn-link btn-primary\" cmd=\"editBtn\" data-original-title=\"编辑\">"
                    + "<i class=\"fa fa-edit\"></i>编辑</button>"
                    + "<button type=\"button\" data-toggle=\"tooltip\" title=\"\" "
                    + "class=\"btn btn-link btn-primary\" cmd=\"uploadBtn\" data-original-title=\"上传二维码\">"
                    + "<i class=\"fas fa-inbox\"></i>上传二维码</button>";
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
                var companyname = {};
                companyname.name = 'companyname';
                companyname.op = "like";
                companyname.stringValue = "%" + data.search.value + "%";
                // var nickName = {};
                // nickName.name = 'nickName';
                // nickName.op = "like";
                // nickName.stringValue = "%" + data.search.value + "%";
                // var telephone = {};
                // telephone.name = 'telephone';
                // telephone.op = "like";
                // telephone.stringValue = "%" + data.search.value + "%";
                var value = [];
                value.push(companyname);
                // value.push(nickName);
                // value.push(telephone);
                // exprs.push({
                // op : 'or',
                // expValue : value
                // });
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
                url : GlobalParam.context + "/rest/company/getCompanyList",
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
                          if ($('#companyList tbody tr input:checkbox[name="companyListChk"]:checked').length == 0) {
                            $("#allparameterList .rowSelected").addClass(
                                "disabled");
                          }
                        } else {
                          $("#companyList").dataTable.$("tr.selected").find(
                              "input[type='checkbox']").prop("checked", false);
                          $("#companyList").dataTable.$("tr.selected")
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
                        var id = $('#companyList').DataTable().row(rowIndex).data().id;
                        var companyname = $('#companyList').DataTable().row(rowIndex).data().companyname;
                        var payee = $('#companyList').DataTable().row(rowIndex).data().payee;
                        var bankname = $('#companyList').DataTable().row(rowIndex).data().bankname;
                        var cardid = $('#companyList').DataTable().row(rowIndex).data().cardid;
                        var telephone = $('#companyList').DataTable().row(rowIndex).data().telephone;
                        $("#addRow").click(
                            function() {
                              $('#addRowModal').on(
                                  'show.bs.modal',
                                  function(event) {
                                    var modal = $(this)
                                    modal.find('#ids').val(id)
                                    modal.find('#companyname').val(companyname)
                                    modal.find('#payee').val(payee)
                                    modal.find('#bankname').val(bankname)
                                    modal.find('#cardid').val(cardid)
                                    modal.find('#telephone').val(telephone)
                                  });
                            });
                        $("#addRow").click();
                      });
              $('button[cmd="uploadBtn"]').click(function(e) {
                var rowIndex = $(this).parents("tr").index();
                var id = $('#companyList').DataTable().row(rowIndex).data().id;
                $("#uploadPay").click(
                            function() {
                              $('#uploadPayModal').on(
                                  'show.bs.modal',
                                  function(event) {
                                    var modal = $(this)
                                    modal.find('#id').val(id)
                                  });
                            });
                $("#uploadPay").click();
              });
              $('button[cmd="upCarouselBtn"]').click(function(e) {
                var rowIndex = $(this).parents("tr").index();
                var id = $('#companyList').DataTable().row(rowIndex).data().id;
                $("#uploadCarousel").click(
                            function() {
                              $('#uploadCarouselModal').on(
                                  'show.bs.modal',
                                  function(event) {
                                    var modal = $(this)
                                    modal.find('#idc').val(id)
                                  });
                            });
                $("#uploadCarousel").click();
              });
            }
          });
  $("#addButton").click(function(e) {
    var id = $("#ids").val()
    var companyname = $("#companyname").val();
    var payee = $("#payee").val();
    var bankname = $("#bankname").val();
    var cardid = $("#cardid").val();
    var telephone = $("#telephone").val();
    var url = GlobalParam.context + "/rest/company/addCompany";
    var type = "POST";
    var text = "新增成功！";
    if (id) {
      type = "PUT";
      url = GlobalParam.context + "/rest/company/updateCompany";
      text = "更新成功！";
    }
    if (!bankname) {
      swal({
        title : '提示',
        text : "开户行不允许为空！",
        buttons : {
          confirm : {
            className : 'btn btn-success'
          }
        },
      });
    }
    if (!cardid) {
      swal({
        title : '提示',
        text : "银行卡号不允许为空！",
        buttons : {
          confirm : {
            className : 'btn btn-success'
          }
        },
      });
    }
    var data = {
      "id" : id,
      "companyname" : companyname,
      "payee" : payee,
      "bankname" : bankname,
      "cardid" : cardid,
      "telephone": telephone
    };
    $.ajax({
      type : type,
      url : url,
      data : JSON.stringify(data),
      contentType : 'application/json',
      success : function(ret) {
        if (ret.code == 200) {
          $('#companyList').DataTable().draw(true);
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
  $("#uploadPayBtn").click(function(e) {
    var id = $("#id").val();
    var payType = $("#payType").val();
    var file = $("#file")[0];
    if (!payType) {
      swal({
        title : '提示',
        text : "收款码类型不允许为空！",
        buttons : {
          confirm : {
            className : 'btn btn-success'
          }
        },
      });
    }
    if (!file) {
      swal({
        title : '提示',
        text : "上传收款码不允许为空！",
        buttons : {
          confirm : {
            className : 'btn btn-success'
          }
        },
      });
    }
    var datas = {
      id : id,
      payType: payType
    };
    $.ajaxFileUpload({
        url: GlobalParam.context + "/rest/company/updateCpmpayPayQRcode",
        secureuri : false,
        fileElementId: 'file',
        dataType: 'json',
        data: datas,//增加推送的属性
        type: 'POST',
        async: true,
        complete: false,
        success:
            function (ret) {
              if (ret.code == 200) {
                $('#companyList').DataTable().draw(true);
                $('#uploadPayModal').modal('hide');
                swal({
                  title : '提示',
                  text : ret.msg,
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
        },
        error:
            function (data) {
                console.log(data);
                console.log("error");
            }
    });
  });
});