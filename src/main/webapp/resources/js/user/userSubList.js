$(function(){
  var fieldMapping = {
      id : "id",
      name : 'name',
      weight : "weight",
      nickName : "nickName",
      telephone : "telephone",
      createTime : "createTime"
    };
  $("#userSubList").DataTable({
    columns : [
      {data:"id",'bSortable' : false, className:"text-center",width:"5%",
      render : function(data){
        return "<label class=\"mt-checkbox mt-checkbox-outline\"><input type=\"checkbox\" name=\"userListChk\"><span></span></label>";
    }},{title:"序号",data:"weight",orderable : true, createdCell: function(nTd, sData, oData, iRow, iCol) {
      $(nTd).attr("model","weight")
    }},{title:"账号",data:"account",orderable : true, createdCell: function(nTd, sData, oData, iRow, iCol) {
      $(nTd).attr("model","account")
    }},{title:"姓名",data:"name",orderable : true,createdCell: function(nTd, sData, oData, iRow, iCol) {
      $(nTd).attr("model","name")
    }},{title:"昵称",data:"nickName",createdCell: function(nTd, sData, oData, iRow, iCol) {
      $(nTd).attr("model","nickName")
    }},{title:"联系电话",data:"telephone",createdCell: function(nTd, sData, oData, iRow, iCol) {
      $(nTd).attr("model","telephone")
    }},{title:"创建时间",data:"createTime",createdCell: function(nTd, sData, oData, iRow, iCol) {
      $(nTd).attr("model","createTime")
    },
	  render: function(data, type, row) {
	    return moment(data).format("YYYY-MM-DD HH:ss:mm");
	  }}],
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
      var id = getUrlParam("id");
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
        name : 'id',
        op : 'eq',
        longValue : id
      });
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
        account.stringValue = "%" + data.search.value + "%";
        var value = [];
        value.push(name);
        value.push(account);
        exprs.push({
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
        url:GlobalParam.context+"/rest/user/getUserSubList",
        data: JSON.stringify(data),
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
              if($('#userSubList tbody tr input:checkbox[name="userListChk"]:checked').length==0){
                $("#userSubList .rowSelected").addClass("disabled");
              }
            }else{
              $("#userSubList").dataTable.$("tr.selected").find("input[type='checkbox']").prop("checked", false);
              $("#userSubList").dataTable.$("tr.selected").removeClass("selected");
              $(nRow).addClass("selected");
              $(nRow).find("input[type='checkbox']").prop("checked", true);
            } 
      });
    },
    "fnDrawCallback": function(oSettings) {
      $('button[cmd="editBtn"]').click(function(e) {
        var rowIndex = $(this).parents("tr").index();
        var id = $('#userSubList').DataTable().row(rowIndex).data().id; 
        var account = $('#userSubList').DataTable().row(rowIndex).data().account;
        var name = $('#userSubList').DataTable().row(rowIndex).data().name;
        var nickName = $('#userSubList').DataTable().row(rowIndex).data().nickName;
        var referrerAccount = $('#userSubList').DataTable().row(rowIndex).data().referrerAccount;
        var passwd = $('#userSubList').DataTable().row(rowIndex).data().passwd;
        var payPasswd = $('#userSubList').DataTable().row(rowIndex).data().payPasswd;
        var telephone = $('#userSubList').DataTable().row(rowIndex).data().telephone;
        $("#addRow").click(function(){
        	$('#addRowModal').on('show.bs.modal', function (event) {
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
      $('button[cmd="viewSubBtn"]').click(function(){
        var rowIndex = $(this).parents("tr").index();
        var id = $('#userList').DataTable().row(rowIndex).data().id;
        var url = "pcUser/page/userList?id="+id;
        var contentSubFrame = $("#contentSubFrame");
        contentFrame.attr("src",url);
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
	          $('#userSubList').DataTable().draw(true);
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