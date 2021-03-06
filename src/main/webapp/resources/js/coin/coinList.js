$(function() {
  var fieldMapping = {
    name : "name"
  };
  $("#coinList").DataTable({
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
                  title : "名称",
                  data : "coinName",
                  width : "10%",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "coinName")
                  }
                }, {
                  title : "类别",
                  data : "coinType",
                  width : "15%",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "coinType")
                  }
                }, {
                  title : "单价",
                  data : "coinPrice",
                  width : "15%",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "coinPrice")
                  }
                }],
            columnDefs : [ {
	              targets : 4,// 自定义列的序号，从0开始
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
                var coinName = {};
                coinName.name = 'coinName';
                coinName.op = "like";
                coinName.dataType = "str";
                coinName.stringValue = "%" + data.search.value + "%";
                //var nickName = {};
                //nickName.name = 'nickName';
                //nickName.op = "like";
                //nickName.stringValue = "%" + data.search.value + "%";
                //var telephone = {};
                //telephone.name = 'telephone';
                //telephone.op = "like";
                //telephone.stringValue = "%" + data.search.value + "%";
                var value = [];
                value.push(coinName);
                //value.push(nickName);
                //value.push(telephone);
                exprs.push(coinName);
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
                    + "/rest/coin/getCoinList",
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
                  if ($('#coinList tbody tr input:checkbox[name="coinListChk"]:checked').length == 0) {
                    $("#allparameterList .rowSelected").addClass(
                        "disabled");
                  }
                } else {
                  $("#coinList").dataTable.$("tr.selected").find(
                      "input[type='checkbox']").prop("checked", false);
                  $("#coinList").dataTable.$("tr.selected")
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
			        var id = $('#coinList').DataTable().row(rowIndex).data().id; 
			        var coinName = $('#coinList').DataTable().row(rowIndex).data().coinName;
			        var coinType = $('#coinList').DataTable().row(rowIndex).data().coinType;
			        var coinPrice = $('#coinList').DataTable().row(rowIndex).data().coinPrice;
			        $("#addRow").click(function(){
			        	$('#addRowModal').on('show.bs.modal', function (event) {
      						  //var button = $(event.relatedTarget) // Button that triggered the modal
      						  //var recipient = button.data('whatever') // Extract info from data-* attributes
      						  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
      						  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
      						  var modal = $(this)
      						  modal.find('#id').val(id);
      						  modal.find('#coinName').val(coinName);
    					      //modal.find('#coinType').val(coinType);
    					      modal.find('#coinPrice').val(coinPrice);
    					      id="";
    					      coinName = "";
    					      coinPrice = "";
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
			                  url : GlobalParam.context + "/rest/coin/deleteCoin/"+id,
			                  contentType : 'application/json',
			                  success : function(ret) {
			                    $('#coinList').DataTable().draw(true);
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
          $("#addRowButton").click(function(e) {
            var id = $("#id").val();
            var coinName = $("#coinName").val();	
            var coinType = $("#coinType").val();
            var coinPrice = $("#coinPrice").val();
            var isEnable = $("#isEnable").val();
            var url = GlobalParam.context + "/rest/coin/addCoin";
            var type = "POST";
            var text= "新增成功！";
            if(id){
              type="PUT";
              url = GlobalParam.context + "/rest/coin/updateCoin";
              text="更新成功！";
            }
            if(!coinName){
              swal({
	              title : '提示',
	              text: "名称不允许为空！",
	              buttons : {
	                confirm : {
	                  className : 'btn btn-success'
	                }
	              },
	            });
	            return;
            }
            if(!coinPrice){
              swal({
	              title : '提示',
	              text: "单价不允许为空！",
	              buttons : {
	                confirm : {
	                  className : 'btn btn-success'
	                }
	              },
	            });
            }
            var data = {
  			      "id": id,
  		        "coinName" : coinName,
  		        "coinType" : coinType,
  		        "coinPrice":coinPrice,
  		        "isEnable" : isEnable
  		      };
            $.ajax({
  		        type : type,
  		        url : url,
  		        data : JSON.stringify(data),
  		        contentType : 'application/json',
  		        success : function(ret) {
  		          if (ret.code == 200) {
  		          $('#coinList').DataTable().draw(true);
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
  		            alert(ret.msg);
  		          }
  		        }
  		      });
	  	});
});