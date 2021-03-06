$(function(){
    $('#table').basictable({
      breakpoint: 768
    });
    var exprs=[];
    var offset=0;
    var limit = 10;
    var orderBy = "account asc";
    var data={
        "expressions":exprs,
        "startPosition":offset,
        "maxResults":limit,
        "orderBy":orderBy,
    };
    var result=[];
    $.ajax({
      type: "POST",
      url:"rest/user/getUserList",
      data: JSON.stringify(data),
      contentType : 'application/json',
      success: function(ret){
        if(ret.code == 200){
          $("#table").reponsetable({
            "id":"table",
            "operation": "editer",
            "type":"numbers",
            "colum": [
              {"field": "weight","title": "序号"},
              {"field": "account","title": "账号"},
              {"field": "name","title": "名称"},
              {"field": "nickName","title": "昵称"},
              {"field": "telephone","title": "联系电话"},
              {"field": "createTime","title": "创建时间"}
            ],
            "data": ret.data.datas
          });
        }else{
          alert(ret.msg);
        }
       }
      }); 
  })
  function addtr() {
    layer.open({
      type: 1,
      title: '编辑人员信息',
      closeBtn: 1,
      area: '516px',
      skin: '#fff', //没有背景色
      shadeClose: true,
      content: $('#editcontent'),
      btn:["保存","关闭"],
      btn1:function(index,layero){
        var Name=$("#Name").val();
        var Age=$("#Age").val();
        var Gender=$("#Gender").val();
        var Height=$("#Height").val();
        var Province=$("#Province").val();
        var Sport=$("#Sport").val();
        var obj = {
        "Name":Name,
          "Age": Age,
          "Gender": Gender,
          "Height": Height,
          "Province": Province,
        "Sport":Sport
        };
        reponse.addtr(obj, "table");
        //reponse.editsavetr(obj, "table");
        layer.close(index);
      },btn2:function(index,layero){
        layer.close(index);
      }
    });
  };
  function uptr(btn, e) {
    var tr = $(btn).parent().parent();
    reponse.moveuptr(tr, "table");
  }
  function downtr(btn, e) {
    var tr = $(btn).parent().parent();
    reponse.moveDown(tr, "table");
  }
  function ExportExcel() {
    var tableobj=$("#table").data("tableObj");
    reponse.JSONToCSVConvertor(tableobj, true,"人员表格");
  }
  function resivetabledata(){
    var pp=$("#table").data("tableObj").data;
    alert(JSON.stringify(pp));
  }
  function reloadtable(){
    var data=[
      {"Name": "1111","Age": 15,"Gender": "1","Height": 189,"Province": "1","Sport": "1"},
      {"Name": "2222","Age": 15,"Gender": "2","Height": 2,"Province": "2","Sport": "2"},
      {"Name": "3333","Age": 15,"Gender": "4","Height": 189,"Province": "3","Sport": "3"}
    ]
    reponse.reloadtable(data,"table");
  }
  function edittr(a, e) {
    var tr = $(a).parent().parent();
    reponse.resiverowdata(tr, "table");
    var rowdata=$("#table").data("rowdata");
    //alert(JSON.stringify(rowdata));
    //页面层-佟丽娅
    
    layer.open({
      type: 1,
      title: '编辑人员信息',
      closeBtn: 1,
      area: '516px',
      skin: '#fff', //没有背景色
      shadeClose: true,
      content: $('#editcontent'),
      btn:["保存","关闭"],
      btn1:function(index,layero){
        var Name=$("#Name").val();
        var Age=$("#Age").val();
        var Gender=$("#Gender").val();
        var Height=$("#Height").val();
        var Province=$("#Province").val();
        var Sport=$("#Sport").val();
        var obj = {
        "Name":Name,
          "Age": Age,
          "Gender": Gender,
          "Height": Height,
          "Province": Province,
        "Sport":Sport
        };
        reponse.editsavetr(obj, "table");
        layer.close(index);
      },btn2:function(index,layero){
        layer.close(index);
      }
    });
  }
  function deletetr(a, e) {
    var tr = $(a).parent().parent();
    reponse.deletetr(tr, e);
  }
  function coldata(){
    var pp=reponse.Columndata("Name","table");
    alert(pp);
  }