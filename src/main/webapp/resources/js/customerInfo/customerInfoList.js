$(function() {
  var fieldMapping = {
    name : "name"
  };
  var loadTreeObj = function(){
    var ztreeObj;
    var settings = {
        check : {
          enable : true
        },
        view : {
          selectedMulti : false,
        // addHoverDom: addHoverDom,
        // removeHoverDom: removeHoverDom,
        },
        data : {
          key : {
            name : "name"
          },
          simpleData : {
            enable : true,
            idKey : "id",
            pIdKey : "pId"
          }
        },
        async : {
          enable : true,
          url : GlobalParam.context + "/rest/customerInfo/typeTransaction/getTransactionTreeList",
          autoParam : [ "id"],
          dataType : 'json'
        },
    };
    $.ajax({
      type : "POST",
      url : GlobalParam.context + "/rest/customerInfo/typeTransaction/getTransactionTreeList",
      async:true,
      data : {
        id : 1,
        isRoot : true
      },
      dataType: "json",
      success : function(ret) {
        if (ret) {
          ztreeObj = $.fn.zTree.init($("#transactionTree"), settings,ret);
          var nodes = ztreeObj.getNodes();
          ztreeObj.expandNode(nodes[0], true, false, false);
        }
      }
    });
    return ztreeObj;
  };
  var transactionTreeObj = loadTreeObj();
  
  $("#addClassifiInfo").click(function() {
        $('#addClassifiInfoModal').on(
            'show.bs.modal',
            function(event) {
              $("#parentClass").click(function(){
                $("#classifiTree").css("display","block");
                var settings = {
                    check : {
                      enable : true
                    },
                    view : {
                      selectedMulti : false,
                    // addHoverDom: addHoverDom,
                    // removeHoverDom: removeHoverDom,
                    },
                    data : {
                      key : {
                        name : "name"
                      },
                      simpleData : {
                        enable : true,
                        idKey : "id",
                        pIdKey : "pId"
                      }
                    },
                    async : {
                      enable : true,
                      url : GlobalParam.context + "/rest/customerInfo/typeTransaction/getTransactionTreeList",
                      autoParam : [ "id"],
                      dataType : 'json'
                    },
                    callback: {
                      onClick: function(event, treeId, treeNode){
                        $("#parentId").val(treeNode.id);
                        $("#parentClass").val(treeNode.name);
                        $("#classifiTree").css("display","none");
                      }
                    }
                };
                $.ajax({
                  type : "POST",
                  url : GlobalParam.context + "/rest/customerInfo/typeTransaction/getTransactionTreeList",
                  async:true,
                  data : {
                    id:1
                  },
                  dataType: "json",
                  success : function(ret) {
                    if (ret) {
                      ztreeObj = $.fn.zTree.init($("#classifiTree"), settings,ret);
                      var nodes = ztreeObj.getSelectedNodes();
                      ztreeObj.expandNode(nodes, true, false, false);
                    }
                  }
                });
              });
            });
      });
  $("#customerInfoList")
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
                  title : "公司名称",
                  data : "code",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "companyname")
                  }
                }, {
                  title : "标题",
                  data : "name",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "name")
                  }
                },{
                  title : "主页轮播图",
                  data : "carouselUrl",
                  orderable : true,
                  createdCell : function(nTd, sData, oData, iRow, iCol) {
                    $(nTd).attr("model", "carouselUrl")
                  },
                  render : function(data, type, row, meta) {
                      return "<div class=\"avatar avatar-xs\">"
                        + "<img src=\""+data+"\" alt=\"...\" class=\"avatar-img\">"
                        + "</div>";
                  }
                } ],
            columnDefs : [ {
              targets : 4,// 自定义列的序号，从0开始
              title : "",
              data : "id", // 需要引用的数据列，一般是主键
              render : function(data, type, full) {
                return "<div class=\"form-button-action\">"
//                    + "<button type=\"button\" data-toggle=\"tooltip\" title=\"\" "
//                    + "class=\"btn btn-link btn-primary\" cmd=\"editBtn\" data-original-title=\"编辑\">"
//                    + "<i class=\"fa fa-edit\"></i>编辑</button>"
                    + "<button type=\"button\" data-toggle=\"tooltip\" title=\"\" "
                    + "class=\"btn btn-link btn-danger\" cmd=\"deleteBtn\" data-original-title=\"删除\">"
                    + "<i class=\"fa fa-times\"></i>删除</button>";
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
                var name = {};
                name.name = 'name';
                name.op = "like";
                name.stringValue = "%" + data.search.value + "%";
                // var nickName = {};
                // nickName.name = 'nickName';
                // nickName.op = "like";
                // nickName.stringValue = "%" + data.search.value + "%";
                // var telephone = {};
                // telephone.name = 'telephone';
                // telephone.op = "like";
                // telephone.stringValue = "%" + data.search.value + "%";
                var value = [];
                value.push(name);
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
                url : GlobalParam.context + "/rest/customerInfo/getCustomerInfoList",
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
                          if ($('#customerInfoList tbody tr input:checkbox[name="companyListChk"]:checked').length == 0) {
                            $("#allparameterList .rowSelected").addClass(
                                "disabled");
                          }
                        } else {
                          $("#customerInfoList").dataTable.$("tr.selected").find(
                              "input[type='checkbox']").prop("checked", false);
                          $("#customerInfoList").dataTable.$("tr.selected")
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
                var id = $('#customerInfoList').DataTable().row(rowIndex).data().id;
                var companyId = $('#customerInfoList').DataTable().row(rowIndex).data().companyId;
                var name = $('#customerInfoList').DataTable().row(rowIndex).data().name;
                var carouselFile = $('#customerInfoList').DataTable().row(rowIndex).data().carouselUrl;
                $("#addRow").click(
                    function() {
                      $('#addRowModal').on(
                          'show.bs.modal',
                          function(event) {
                            var modal = $(this)
                            modal.find('#id').val(id)
                            modal.find('#companyId').val(companyId)
                            modal.find('#name').val(name)
                            modal.find('#carouselFile').val("")
                          });
                    });
                $("#addRow").click();
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
                          url : GlobalParam.context + "/rest/customerInfo/deleteCustomerInfo/"+id,
                          contentType : 'application/json',
                          success : function(ret) {
                            $('#customerInfoList').DataTable().draw(true);
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
    var id = $("#id").val()
    var companyId = $("#companyId").val();
    var name = $("#name").val();
    var carouselFile = $("#carouselFile")[0];
    if(carouselFile){
      swal({
        title : '提示',
        text : '请上传图片！',
        buttons : {
          confirm : {
            className : 'btn btn-success'
          }
        },
      });
    }
    var url = GlobalParam.context + "/rest/carousel/addCarousel";
    var type = "POST";
    var text = "新增成功！";
    if (id) {
      type = "PUT";
      url = GlobalParam.context + "/rest/carousel/updateCarousel";
      text = "更新成功！";
    }
    var datas = {
      companyId : companyId,
      name : name,
    };
    $.ajaxFileUpload({
        url: url,
        secureuri : false,
        fileElementId: 'carouselFile',
        dataType: 'json',
        data: datas,//增加推送的属性
        type: type,
        async: true,
        complete: false,
        success:
            function (ret) {
              if (ret.code == 200) {
                $('#customerInfoList').DataTable().draw(true);
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
        },
        error:
            function (data) {
                console.log(data);
                console.log("error");
            }
    });
  });
});
function getTransactionData(){
  
}