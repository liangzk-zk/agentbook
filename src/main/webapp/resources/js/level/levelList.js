$(function() {
  var fieldMapping = {
    name : "name"
  };
  $("#levelList").DataTable({
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
                  title : "名称",
                  data : "name",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "name")
                  }
                }, {
                  title : "级别",
                  data : "level",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "level")
                  }
                }, {
                  title : "级别名称",
                  data : "levelName",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "level")
                  }
                }, {
                  title : "每日释放金额",
                  data : "releaseQuota",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "releaseQuota")
                  }
                }, {
                  title : "充值金额",
                  data : "rechargePrice",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "rechargePrice")
                  }
                }, {
                  title : "币数",
                  data : "coinnum",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "coinnum")
                  }
                }, {
                  title : "股票点值",
                  data : "stock",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "stock")
                  }
                }],
            columnDefs : [ {
	              targets : 8,// 自定义列的序号，从0开始
	              title : "",
	              data : "level", // 需要引用的数据列，一般是主键
	              render : function(data, type, full) {
	              	return "<div class=\"form-button-action\">"
                    + "<button type=\"button\" data-toggle=\"tooltip\" title=\"\" "
                    + "class=\"btn btn-link btn-primary\" cmd=\"editBtn\" data-original-title=\"编辑\">"
                    + "<i class=\"fa fa-edit\"></i>编辑</button>"
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
              // exprs.push({
              // name : 'keyId',
              // op : 'eq',
              // stringValue : parentId
              // });
              if (data.search && data.search.value && data.search.value != '') {
                var levelName = {};
                levelName.name = 'levelName';
                levelName.op = "like";
                levelName.dataType = "str";
                levelName.stringValue = "%" + data.search.value + "%";
                //var nickName = {};
                //nickName.name = 'nickName';
                //nickName.op = "like";
                //nickName.stringValue = "%" + data.search.value + "%";
                //var telephone = {};
                //telephone.name = 'telephone';
                //telephone.op = "like";
                //telephone.stringValue = "%" + data.search.value + "%";
                var value = [];
                value.push(name);
                //value.push(nickName);
                //value.push(telephone);
                //exprs.push({
                  //op : 'or',
                  //expValue : value
                //});
                exprs.push(levelName);
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
                    + "/rest/level/getLevelList",
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
                  if ($('#levelList tbody tr input:checkbox[name="levelListChk"]:checked').length == 0) {
                    $("#allparameterList .rowSelected").addClass(
                        "disabled");
                  }
                } else {
                  $("#levelList").dataTable.$("tr.selected").find(
                      "input[type='checkbox']").prop("checked", false);
                  $("#levelList").dataTable.$("tr.selected")
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
                var id = $('#levelList').DataTable().row(rowIndex).data().id; 
                var level = $('#levelList').DataTable().row(rowIndex).data().level;
                var releaseQuota = $('#levelList').DataTable().row(rowIndex).data().releaseQuota;
                var rechargePrice = $('#levelList').DataTable().row(rowIndex).data().rechargePrice;
                var coinnum = $('#levelList').DataTable().row(rowIndex).data().coinnum;
                var rechargeMultiple = $('#levelList').DataTable().row(rowIndex).data().rechargeMultiple;
                var stock = $('#levelList').DataTable().row(rowIndex).data().stock;
                $("#addRow").click(function(){
                	$('#addRowModal').on('show.bs.modal', function (event) {
        					  //var button = $(event.relatedTarget) // Button that triggered the modal
        					  //var recipient = button.data('whatever') // Extract info from data-* attributes
        					  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
        					  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
        					  var modal = $(this);
        					  modal.find('#id').val(id);
        					  modal.find('#level').val(level);
        					  modal.find('#releaseQuota').val(releaseQuota);
        					  modal.find('#rechargeMultiple').val(rechargeMultiple);
        					  modal.find('#rechargePrice').val(rechargePrice);
        					  modal.find('#coinnum').val(coinnum);
        					  modal.find('#stock').val(stock);
        					  id="";
        					  level=1;
        					  releaseQuota=0
        					  rechargePrice="";
        					  rechargeMultiple="";
        					  coinnum="";
        					  stock="";
        					});
                });
                $("#addRow").click();
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
                          url : GlobalParam.context + "/rest/level/deleteLevel/"+id,
                          contentType : 'application/json',
                          success : function(ret) {
                            $('#levelList').DataTable().draw(true);
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
	$("#addRowButton").click(function(e) {
	  var id = $("#id").val()
		var name = $("#name").val();	
		var level = $("#level").val();
		var levelName = $("#level option:selected").text();
		var releaseQuota = $("#releaseQuota").val();
		var rechargePrice = $("#rechargePrice").val();
		var coinnum = $("#coinnum").val();
		var rechargeMultiple = $("#rechargeMultiple").val();
		var stock = $("#stock").val();
		var isEnable = $("#isEnable").val();
		var url = GlobalParam.context + "/rest/level/addLevel";
    var type = "POST";
    var text= "新增成功！";
    if(id){
      type="PUT";
      url = GlobalParam.context + "/rest/level/updateLevel";
      text="更新成功！";
    }
		if(!name){
			swal({
              title : '提示',
              text: "等级名称不允许为空！",
              buttons : {
                confirm : {
                  className : 'btn btn-success'
                }
              },
            });
		}
		if(!level){
			swal({
              title : '提示',
              text: "等级不允许为空！",
              buttons : {
                confirm : {
                  className : 'btn btn-success'
                }
              },
            });
		}
		if(!releaseQuota){
			swal({
              title : '提示',
              text: "每日释放金额不允许为空！",
              buttons : {
                confirm : {
                  className : 'btn btn-success'
                }
              },
            });
		}
		if(!rechargePrice){
			swal({
              title : '提示',
              text: "充值金额不允许为空！",
              buttons : {
                confirm : {
                  className : 'btn btn-success'
                }
              },
            });
		}
		var data = {
		      "id": id,
	        "name" : name,
	        "level" : level,
	        "levelName":levelName,
	        "releaseQuota":releaseQuota,
	        "rechargeMultiple":rechargeMultiple,
	        "rechargePrice" : rechargePrice,
	        "coinnum": coinnum,
	        "isEnable" : isEnable,
	        "stock" : stock
	      };
		$.ajax({
	        type : type,
	        url : url,
	        data : JSON.stringify(data),
	        contentType : 'application/json',
	        success : function(ret) {
	          if (ret.code == 200) {
	          $('#levelList').DataTable().draw(true);
	          $('#addRowModal').modal('hide');
	          	swal({
	              title : '提示',
	              text: text,
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
});