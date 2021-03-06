$(function() {
  var fieldMapping = {
    userId : 'userId',
    name : "name",
    createTime : "createTime"
  };
  $("#paymentList")
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
                    + "<i class=\"fa fa-times\"></i>删除</button>"
                    + "<button type=\"button\" data-toggle=\"tooltip\" title=\"\" "
                    + "class=\"btn btn-link btn-danger\" cmd=\"rechargeBtn\" data-original-title=\"充值\">"
                    + "<i class=\"fas fa-donate\"></i>充值</button></div>";
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
                 intValue : 0
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
                  if ($('#paymentList tbody tr input:checkbox[name="paymentListChk"]:checked').length == 0) {
                    $("#allparameterList .rowSelected").addClass(
                        "disabled");
                  }
                } else {
                  $("#paymentList").dataTable.$("tr.selected").find(
                      "input[type='checkbox']").prop("checked", false);
                  $("#paymentList").dataTable.$("tr.selected")
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
		        var id = $('#paymentList').DataTable().row(rowIndex).data().id; 
		        var account = $('#paymentList').DataTable().row(rowIndex).data().account;
		        var name = $('#paymentList').DataTable().row(rowIndex).data().name;
		        var nickName = $('#paymentList').DataTable().row(rowIndex).data().nickName;
		        var referrerAccount = $('#paymentList').DataTable().row(rowIndex).data().referrerAccount;
		        var passwd = $('#paymentList').DataTable().row(rowIndex).data().passwd;
		        var payPasswd = $('#paymentList').DataTable().row(rowIndex).data().payPasswd;
		        var telephone = $('#paymentList').DataTable().row(rowIndex).data().telephone;
		        $("#addRow").click(function(){
		        	$('#addRowModal').on('show.bs.modal', function (event) {
					  //var button = $(event.relatedTarget) // Button that triggered the modal
					  //var recipient = button.data('whatever') // Extract info from data-* attributes
					  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
					  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
					  var modal = $(this)
					  modal.find('#id').val(id);
					  modal.find('#account').val(account);
				      modal.find('#name').val(name);
				      modal.find('#nickName').val(nickName);
				      modal.find('#referrerAccount').val(referrerAccount);
				      modal.find('#passwd').val(passwd);
				      modal.find('#payPasswd').val(payPasswd);
				      modal.find('#telephone').val(telephone);
					});
		        });
		        $("#addRow").click();
		      });
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
              $('button[cmd="rechargeBtn"]').click(function(e) {
                var rowIndex = $(this).parents("tr").index();
                var paymentId = $('#paymentList').DataTable().row(rowIndex).data().id;
                var userId = $('#paymentList').DataTable().row(rowIndex).data().userId; 
                swal({
                      title : '充值',
                      html: '<br><input class="form-control" placeholder="Input Something" id="input-field">',
                      content : {
                        element : "input",
                        attributes : {
                          placeholder: "请输入100的整数倍！",
                          type: "text",
                          id: "rechargeAmount",
                          className: "form-control"
                        },
                      },
                      buttons : {
                        confirm : {
                          className : 'btn btn-success'
                        }
                      },
                    }).then(function() {
                      var reg = /^[1-9][0-9]*0{2}$/;
                      var rechargeAmount = $('#rechargeAmount').val();
                      if(reg.test(rechargeAmount)){
                        swal({
                          title : '确认充值',
                          text: "确定充值 : "+ $('#rechargeAmount').val()+"么？",
                          buttons : {
                            confirm : {
                              className : 'btn btn-success'
                            }
                          },
                        }).then(function(OK) {
                        	if(OK){
                        		var data = {
                        			"paymentId": paymentId,
                        			"userId": userId,
                        			"rechargeAmount" : rechargeAmount
                        		};
                        		$.ajax({
		                          type : "POST",
		                          data :JSON.stringify(data),
		                          url : GlobalParam.context + "/rest/paymentVoucher/recharge",
		                          contentType : 'application/json',
		                          success : function(ret) {
		                            $('#paymentList').DataTable().draw(true);
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
                        	}
                        });
                      }else{
                        swal({
                          title : '输入错误',
                          text: "输入错误！请确定输入值为100的整数倍，您当前的输入金额为 : "+ $('#rechargeAmount').val(),
                          buttons : {
                            confirm : {
                              className : 'btn btn-success'
                            }
                          },
                        });
                      }
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
                            $('#paymentList').DataTable().draw(true);
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