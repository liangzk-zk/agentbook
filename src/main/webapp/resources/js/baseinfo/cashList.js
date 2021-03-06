$(function() {
  var fieldMapping = {
    name : "name"
  };
  $("#cashList").DataTable({
            columns : [
                {
                  data : "id",
                  'bSortable' : false,
                  className : "text-center",
                  width : "5%",
                  render : function(data) {
                    return "<label class=\"mt-checkbox mt-checkbox-outline\"><input type=\"checkbox\" name=\"levelListChk\" value=\""+data+"\"><span></span></label>";
                  }
                }, {
                  title : "用户帐号",
                  data : "account",
                  width : "10%",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "account")
                  }
                }, {
                  title : "用户名称",
                  data : "username",
                  width : "15%",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "username")
                  }
                }, {
                  title : "提现银行卡号",
                  data : "cardId",
                  width : "15%",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "cardId")
                  }
                }, {
                  title : "提现金额",
                  data : "cashed",
                  width : "15%",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "cashed")
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
                }],
            columnDefs : [ {
	              targets : 6,// 自定义列的序号，从0开始
	              title : "",
	              data : "level", // 需要引用的数据列，一般是主键
	              render : function(data, type, full) {
	              	return "<div class=\"form-button-action\">"
                    + "<button type=\"button\" data-toggle=\"tooltip\" title=\"\" "
                    + "class=\"btn btn-link btn-primary\" cmd=\"cashedBtn\" data-original-title=\"确认提现\">"
                    + "<i class=\"fa fa-edit\"></i>确认提现</button>"
                    + "<button type=\"button\" data-toggle=\"tooltip\" title=\"\" "
                    + "class=\"btn btn-link btn-danger\" cmd=\"deleteBtn\" data-original-title=\"删除\">"
                    + "<i class=\"fa fa-times\"></i>删除</button>";
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
              exprs.push({
               name : 'isCashed',
               op : 'eq',
               dataType:"int",
               intValue : 0
              });
              if (data.search && data.search.value && data.search.value != '') {
                var username = {};
                username.name = 'username';
                username.op = "like";
                username.dataType = "str";
                username.stringValue = "%" + data.search.value + "%";
                var account = {};
                account.name = 'account';
                account.op = "like";
                account.dataType = "str";
                account.stringValue = "%" + data.search.value + "%";
                //var telephone = {};
                //telephone.name = 'telephone';
                //telephone.op = "like";
                //telephone.stringValue = "%" + data.search.value + "%";
                var value = [];
                value.push(username);
                value.push(account);
                //value.push(telephone);
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
                    + "/rest/cash/getCashList",
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
                  if ($('#cashList tbody tr input:checkbox[name="levelListChk"]:checked').length == 0) {
                    $("#allparameterList .rowSelected").addClass(
                        "disabled");
                  }
                } else {
                  $("#cashList").dataTable.$("tr.selected").find(
                      "input[type='checkbox']").prop("checked", false);
                  $("#cashList").dataTable.$("tr.selected")
                      .removeClass("selected");
                  $(nRow).addClass("selected");
                  $(nRow).find("input[type='checkbox']").prop(
                      "checked", true);
                }
              });
            },
            "fnDrawCallback" : function(oSettings) {
              $('button[cmd="cashedBtn"]').click(function(e) {
                var rowIndex = $(this).parents("tr").index();
                var id = $('#cashList').DataTable().row(rowIndex).data().id; 
                var data = {
					"id": id,
			        "isCashed" : 1
			      };
				$.ajax({
			        type : "PUT",
			        url : GlobalParam.context
                    + "/rest/cash/updateCash",
			        data : JSON.stringify(data),
			        contentType : 'application/json',
			        success : function(ret) {
			          if (ret.code == 200) {
			          $('#cashList').DataTable().draw(true);
			          	swal({
			              title : '提示',
			              text: ret.msg,
			              buttons : {
			                confirm : {
			                  className : 'btn btn-success'
			                }
			              },
			            });
			          } else {
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
			        }
			      });
              });
              $('button[cmd="deleteBtn"]').click(function(e) {
                var rowIndex = $(this).parents("tr").index();
                var id = $('#cashList').DataTable().row(rowIndex).data().id; 
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
                          url : GlobalParam.context + "/rest/cash/deleteCash/"+id,
                          contentType : 'application/json',
                          success : function(ret) {
                            $('#cashList').DataTable().draw(true);
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
});