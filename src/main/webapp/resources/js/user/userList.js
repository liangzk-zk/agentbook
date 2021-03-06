$(function(){
  var fieldMapping = {
      id : "id",
      name : 'name',
      weight : "weight",
      nickName : "nickName",
      telephone : "telephone",
      createTime : "createTime"
    };
  $("#userList").DataTable({
    columns : [
      {data:"id",'bSortable' : false, className:"text-center",width:"5%",
      render : function(data){
        return "<label class=\"mt-checkbox mt-checkbox-outline\"><input type=\"checkbox\" name=\"userListChk\"><span></span></label>";
    }},{title:"序号",data:"weight",orderable : true, createdCell: function(nTd, sData, oData, iRow, iCol) {
      $(nTd).attr("model","weight")
    }},{title:"账号",data:"account",orderable : true, createdCell: function(nTd, sData, oData, iRow, iCol) {
      $(nTd).attr("model","account")
    }},{title:"推荐人账号",data:"referrerAccount", width: "10%",createdCell: function(nTd, sData, oData, iRow, iCol) {
      $(nTd).attr("model","referrerAccount")
    }},{title:"姓名",data:"name",orderable : true,createdCell: function(nTd, sData, oData, iRow, iCol) {
      $(nTd).attr("model","name")
    }},{title:"联系电话",data:"telephone", width: "10%",createdCell: function(nTd, sData, oData, iRow, iCol) {
      $(nTd).attr("model","telephone")
    }},{title:"创建时间",data:"createTime", width: "10%",createdCell: function(nTd, sData, oData, iRow, iCol) {
      $(nTd).attr("model","createTime")
    },
	  render: function(data, type, row) {
	    return moment(data).format("YYYY-MM-DD HH:ss:mm");
	  }}],
    columnDefs : [ {
	      targets : 7,// 自定义列的序号，从0开始
	      title : "",
	      data : "id", // 需要引用的数据列，一般是主键
	      render : function(data, type, full) {
	      	return "<div class=\"form-button-action\">"
	        + "<button type=\"button\" data-toggle=\"tooltip\" title=\"\" "
	        + "class=\"btn btn-link btn-primary\" cmd=\"editBtn\" data-original-title=\"编辑\">"
	        + "<i class=\"fa fa-edit\"></i>编辑</button>"
	        + "<button type=\"button\" data-toggle=\"tooltip\" title=\"\" "
	        + "class=\"btn btn-link btn-danger\" cmd=\"deleteBtn\" data-original-title=\"删除\">"
	        + "<i class=\"fa fa-times\"></i>删除</button>"
	        + "<button type=\"button\" data-toggle=\"tooltip\" title=\"\" "
          	+ "class=\"btn btn-link\" cmd=\"viewSubBtn\" data-original-title=\"查看推荐\">"
          	+ "<i class=\"fas fa-eye\"></i>查看推荐</button>"
          	+ "<button type=\"button\" data-toggle=\"tooltip\" title=\"\" "
          	+ "class=\"btn btn-link\" cmd=\"viewQuotaBtn\" data-original-title=\"查看推荐\">"
          	+ "<i class=\"fas fa-eye\"></i>查看额度信息</button>";
	      }
	  }],
    "order":[[ 2, 'asc' ]],
    "orderCellsTop" : true,
    info: true,
    bLengthChange: true,
    "bDestroy": true,
    colReorder : true,
    "aaSorting": [], // 禁用默认第一列排序
    "bAutoWidth": false,
    "sDom": 'Rlfrtip',
    "headerContextMenu": true,
    "oColReorder": {
        "headerContextMenu": true
    },
    "pagingType": 'full_numbers',
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
//      exprs.push({
//        name : 'keyId',
//        op : 'eq',
//        stringValue : parentId
//      });
      if (data.search && data.search.value && data.search.value != '') {
        var name = {};
        name.name = 'name';
        name.op = "like";
        name.dataType = "str";
        name.stringValue = "%"+data.search.value+"%";
        var account = {};
        account.name = 'account';
        account.op = "like";
        account.dataType = "str";
        account.stringValue = "%"+data.search.value+"%";
        var value = [];
        value.push(name);
        value.push(account);
        exprs.push({
          dataType : 'exps',
          op : 'or',
          expValue : value
        });
      }
      var data={
          "expressions":exprs,
          "startPosition":offset,
          "maxResults":limit,
          "orderBy":orderBy,
      };
      $.ajax({
        type: "POST",
        url:GlobalParam.context+"/rest/user/getUserList",
        data: JSON.stringify(data),
        dataType:"json",
        contentType : 'application/json',
        success: function(ret){
          if(ret.code == 200){
            cb({
              recordsTotal : ret.data.totalSize,
              recordsFiltered : ret.data.totalSize,
              data : ret.data.datas
            });
          }else{
            alert(ret.msg);
          }
         }
        });
    },
    "fnCreatedRow": function( nRow, aData, iDataIndex ) {
      if( typeof( aData.id ) != "undefined" ) {
          $( nRow ).attr( "id", aData.id);
          $( nRow ).attr( "rowIndex", iDataIndex);
      }
      $(nRow).on("click", function() {
          if($(nRow).find("input[type='checkbox']").prop("checked")){
              $(nRow).removeClass("selected");
              $(nRow).find("input[type='checkbox']").prop("checked", false);
              if($('#userList tbody tr input:checkbox[name="userListChk"]:checked').length==0){
                $("#allparameterList .rowSelected").addClass("disabled");
              }
            }else{
              $("#userList").dataTable.$("tr.selected").find("input[type='checkbox']").prop("checked", false);
              $("#userList").dataTable.$("tr.selected").removeClass("selected");
              $(nRow).addClass("selected");
              $(nRow).find("input[type='checkbox']").prop("checked", true);
            } 
      });
    },
    "fnDrawCallback": function(oSettings) {
      $('button[cmd="editBtn"]').click(function(e) {
        var rowIndex = $(this).parents("tr").index();
        var id = $('#userList').DataTable().row(rowIndex).data().id; 
        var account = $('#userList').DataTable().row(rowIndex).data().account;
        var name = $('#userList').DataTable().row(rowIndex).data().name;
        var nickName = $('#userList').DataTable().row(rowIndex).data().nickName;
        var referrerAccount = $('#userList').DataTable().row(rowIndex).data().referrerAccount;
        var passwd = $('#userList').DataTable().row(rowIndex).data().passwd;
        var payPasswd = $('#userList').DataTable().row(rowIndex).data().payPasswd;
        var telephone = $('#userList').DataTable().row(rowIndex).data().telephone;
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
    		      id="";
    		      account="";
    		      name="";
    		      nickName="";
    		      referrerAccount="";
    		      passwd="";
    		      payPasswd="";
    		      telephone="";
    			});
        });
        $("#addRow").click();
      });
      $("#addRow").click(function(){
        	$('#addRowModal').on('show.bs.modal', function (event) {
			    var modal = $(this)
			    modal.find('#id').val("");
			    modal.find('#account').val("");
		      	modal.find('#name').val("");
		      	modal.find('#nickName').val("");
		      	modal.find('#referrerAccount').val("");
		      	modal.find('#passwd').val("");
		      	modal.find('#confirmpasswd').val("");
		      	modal.find('#payPasswd').val("");
		      	modal.find('#telephone').val("");
			});
        });
      $('button[cmd="viewQuotaBtn"]').click(function(e) {
        var rowIndex = $(this).parents("tr").index();
        var id = $('#userList').DataTable().row(rowIndex).data().id;
        $.ajax({
          type : "POST",
          url : GlobalParam.context + "/rest/recharge/getRecharge/"+id,
          contentType : 'application/json',
          success : function(ret) {
          	if(ret.data){
          		$("#viewQuota").click(function(){
        			$('#viewQuotaModal').on('show.bs.modal', function (event) {
	    			  var modal = $(this)
	    			  modal.find('#account').html("");
	    		      modal.find('#username').html("");
	    		      modal.find('#quota').html("");
	    		      modal.find('#level').html("");
	    		      modal.find('#coinprivace').html("");
	    		      modal.find('#dividends').html("");
	    		      modal.find('#cashdividends').html("");
	    		      modal.find('#cashd').html("");
	    			  modal.find('#account').html(ret.data.account);
	    		      modal.find('#username').html(ret.data.username);
	    		      modal.find('#quota').html(ret.data.quota);
	    		      modal.find('#level').html(ret.data.level);
	    		      modal.find('#coinprivace').html(ret.data.coinprivace);
	    		      modal.find('#dividends').html(ret.data.dividends);
	    		      modal.find('#cashdividends').html(ret.data.cashdividends);
	    		      modal.find('#cashd').html(ret.data.cashd);
	    			});
	        	});
        		$("#viewQuota").click();
          	}else{
          		swal({
	              title : '提示',
	              text: "当前用户暂未开放额度！",
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
      $('button[cmd="viewSubBtn"]').click(function(){
        var rowIndex = $(this).parents("tr").index();
        var id = $('#userList').DataTable().row(rowIndex).data().id;
        var url = GlobalParam.context+"/pcUser/page/userSubList?id="+id;
        $("#viewSubRow").click(function(){
          $('#subRowModal').on('show.bs.modal', function (event) {
              //var button = $(event.relatedTarget) // Button that triggered the modal
              //var recipient = button.data('whatever') // Extract info from data-* attributes
              // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
              // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
              var modal = $(this)
              var contentSubFrame =modal.find("#contentSubFrame");
              contentSubFrame.attr("src",url);
          });
        });
        $("#viewSubRow").click();
      });
      $('button[cmd="deleteBtn"]').click(function(e) {
        var tr = this.parentNode.parentNode.parentNode;
        var id = $(tr).attr("id");
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
  $("#addButton").click(function(e) {
  	var id = $("#id").val();
		var account = $("#account").val();	
		var name = $("#name").val();
		var nickName = $("#nickName").val();
		var referrerAccount = $("#referrerAccount").val();
		var passwd = $("#passwd").val();
		var confirmpasswd = $("#confirmpasswd").val();
		var payPasswd = $("#payPasswd").val();
		var telephone = $("#telephone").val();
		var url = GlobalParam.context + "/rest/user/addUserInfo";
		var type = "POST";
		if(id){
			type="PUT";
			url = GlobalParam.context + "/rest/user/updateUser";
		}
		if(!account){
			swal({
              title : '提示',
              text: "帐号不允许为空！",
              buttons : {
                confirm : {
                  className : 'btn btn-success'
                }
              },
            });
            return;
		}
		if(!passwd){
			swal({
              title : '提示',
              text: "密码不允许为空！",
              buttons : {
                confirm : {
                  className : 'btn btn-success'
                }
              },
            });
		}
		if(!telephone){
			swal({
              title : '提示',
              text: "联系方式不允许为空！",
              buttons : {
                confirm : {
                  className : 'btn btn-success'
                }
              },
            });
            return ;
		}
		if(passwd!=confirmpasswd&&confirmpasswd!=""){//此事件当两个密码不相等且第二个密码是空的时候会显示错误信息
          $(".tip").show();
          return;
        }else{
          $(".tip").hide();//若两次输入的密码相等且都不为空时，不显示错误信息。
        }
		var data = {
			"id": id,
	        "account" : account,
	        "name" : name,
	        "nickName":nickName,
	        "referrerAccount" : referrerAccount,
	        "passwd" : passwd,
	        "payPasswd" : payPasswd,
	        "telephone" : telephone
	      };
		$.ajax({
	        type : type,
	        url : url,
	        data : JSON.stringify(data),
	        contentType : 'application/json',
	        success : function(ret) {
	          if (ret.code == 200) {
	          $('#userList').DataTable().draw(true);
	          $('#addRowModal').modal('hide');
	          	swal({
	              title : '提示',
	              text: "新增成功！",
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
function checkpas1(){//当第一个密码框失去焦点时，触发checkpas1事件
    var pas1=document.getElementById("passwd").value;
    var pas2=document.getElementById("confirmpasswd").value;//获取两个密码框的值
    if(pas1!=pas2&&pas2!=""){//此事件当两个密码不相等且第二个密码是空的时候会显示错误信息
      $(".tip").show();
    }else{
      $(".tip").hide();//若两次输入的密码相等且都不为空时，不显示错误信息。
    }
}
function checkpas(){//当第一个密码框失去焦点时，触发checkpas2件
    var pas1=document.getElementById("passwd").value;
    var pas2=document.getElementById("confirmpasswd").value;//获取两个密码框的值
    if(pas1!=pas2){
      $(".tip").show();//当两个密码不相等时则显示错误信息
    }else{
      $(".tip").hide();
    }
}