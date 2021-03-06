$(function() {
  var fieldMapping = {
    account : "account",
    username : "username",
  };
  $("#rechargeList").DataTable({
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
                  title : "账户",
                  data : "account",
                  width : "10%",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "account")
                  }
                }, {
                  title : "姓名",
                  data : "username",
                  width : "10%",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "username")
                  }
                }, {
                  title : "额度",
                  data : "quota",
                  width : "10%",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "quota")
                  }
                }, {
                  title : "星级",
                  data : "level",
                  width : "10%",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "level")
                  }
                }, {
                  title : "币数",
                  data : "coinnum",
                  width : "10%",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "coinnum")
                  }
                }, {
                  title : "币单价",
                  data : "coinprivace",
                  width : "10%",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "coinprivace")
                  }
                }, {
                  title : "分红",
                  data : "dividends",
                  width : "10%",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "dividends")
                  }
                }, {
                  title : "现金点值",
                  data : "cashdividends",
                  width : "10%",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "cashdividends")
                  }
                }, {
                  title : "已提现金额",
                  data : "cashd",
                  width : "10%",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "cashd")
                  }
                }],
            columnDefs : [ {
                  targets : 10,// 自定义列的序号，从0开始
                  title : "",
                  data : "id", // 需要引用的数据列，一般是主键
                  render : function(data, type, full) {
                    return "<div class=\"form-button-action\">"
                    + "<button type=\"button\" data-toggle=\"tooltip\" title=\"\" "
                    + "class=\"btn btn-link btn-primary\" cmd=\"editBtn\" data-original-title=\"编辑\">"
                    + "<i class=\"fa fa-edit\"></i>编辑</button>";
                  }
              }],
            "order" : [ [ 2, 'asc' ] ],
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
              // exprs.push({
              // name : 'keyId',
              // op : 'eq',
              // stringValue : parentId
              // });
              if (data.search && data.search.value && data.search.value != '') {
                var account = {};
                account.name = 'account';
                account.op = "like";
                account.dataType = "str";
                account.stringValue = "%" + data.search.value + "%";
                var username = {};
                username.name = 'username';
                username.op = "like";
                username.dataType = "str";
                username.stringValue = "%" + data.search.value + "%";
                var value = [];
                value.push(account);
                value.push(username);
                exprs.push({
                  dataType:'exps',
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
                url : GlobalParam.context+ "/rest/recharge/getRechargeList",
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
                  if ($('#rechargeList tbody tr input:checkbox[name="levelListChk"]:checked').length == 0) {
                    $("#allparameterList .rowSelected").addClass(
                        "disabled");
                  }
                } else {
                  $("#rechargeList").dataTable.$("tr.selected").find(
                      "input[type='checkbox']").prop("checked", false);
                  $("#rechargeList").dataTable.$("tr.selected")
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
    		        var id = $('#rechargeList').DataTable().row(rowIndex).data().id; 
    		        var quota = $('#rechargeList').DataTable().row(rowIndex).data().quota;
    		        var level = $('#rechargeList').DataTable().row(rowIndex).data().level;
    		        var coinnum = $('#rechargeList').DataTable().row(rowIndex).data().coinnum;
    		        var dividends = $('#rechargeList').DataTable().row(rowIndex).data().dividends;
    		        var cashdividends = $('#rechargeList').DataTable().row(rowIndex).data().cashdividends;
    		        $("#editRow").click(function(){
    		        	$('#editRowModal').on('show.bs.modal', function (event) {
    		        	  var modal = $(this)
    		        	  modal.find('#id').val(id);
    		        	  modal.find('#quota').val(quota);
    		        	  modal.find('#level').val(level);
    		        	  modal.find('#coinnum').val(coinnum);
    		        	  modal.find('#dividends').val(dividends);
    		        	  modal.find('#cashdividends').val(cashdividends);
    		        	  id="";
    		        	  quota="";
    		        	  level="";
    		        	  coinnum="";
    		        	  dividends="";
    		        	  cashdividends="";
    					});
		        });
		        $("#editRow").click();
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
		                  url : GlobalParam.context + "/rest/user/deleteUser/"+id,
		                  contentType : 'application/json',
		                  success : function(ret) {
		                    $('#userList').DataTable().draw(true);
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
  $("#editButton").click(function(e) {
    var id = $("#id").val();
    var quota = $("#quota").val();  
    var level = $("#level").val();
    var coinnum = $("#coinnum").val();
    var dividends = $("#dividends").val();
    var cashdividends = $("#cashdividends").val();
    var data = {
      "id": id,
      "quota" : quota,
      "level" : level,
      "coinnum": coinnum,
      "dividends":dividends,
      "cashdividends" : cashdividends
    };
    $.ajax({
          type : "PUT",
          url : GlobalParam.context + "/rest/recharge/updateRecharge",
          data : JSON.stringify(data),
          contentType : 'application/json',
          success : function(ret) {
            if (ret.code == 200) {
            $('#rechargeList').DataTable().draw(true);
            $('#editRowModal').modal('hide');
              swal({
                title : '提示',
                text: "更新成功！",
                buttons : {
                  confirm : {
                    className : 'btn btn-success'
                  }
                },
              });
            } else {
              alert(ret.msg);
            }
          }
        });
    });
});