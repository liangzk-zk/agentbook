//require.config({
//			baseUrl : contextPath + "/js",
//			paths : {
//				"jquery.ui.widget" : "plugins/jqueryFileUpload/vendor/jquery.ui.widget",
//				"tmpl" : "plugins/jqueryFileUpload/js/tmpl.min",
//				"load-image" : "plugins/jqueryFileUpload/js/load-image.all.min",
//				"jquery.blueimp-gallery" : "plugins/jqueryFileUpload/js/jquery.blueimp-gallery",
//				"jquery.iframe-transport" : "plugins/jqueryFileUpload/js/jquery.iframe-transport",
//				"jquery.fileupload" : "plugins/jqueryFileUpload/js/jquery.fileupload",
//				"jquery.fileupload-image" : "plugins/jqueryFileUpload/js/jquery.fileupload-image",
//				"jquery.fileupload-audio" : "plugins/jqueryFileUpload/js/jquery.fileupload-audio",
//				"jquery.fileupload-video" : "plugins/jqueryFileUpload/js/jquery.fileupload-video",
//				"jquery.fileupload-validate" : "plugins/jqueryFileUpload/js/jquery.fileupload-validate",
//				"jquery.fileupload-ui" : "plugins/jqueryFileUpload/js/jquery.fileupload-ui",
//				"jquery.fileupload-process" : "plugins/jqueryFileUpload/js/jquery.fileupload-process",
//				"load-image-meta" : "plugins/jqueryFileUpload/js/load-image-meta",
//				"load-image-exif" : "plugins/jqueryFileUpload/js/load-image-exif",
//				"load-image-ios" : "plugins/jqueryFileUpload/js/load-image-ios",
//				"canvas-to-blob" : "plugins/jqueryFileUpload/js/canvas-to-blob.min",
//				"blueimp-helper" : "plugins/jqueryFileUpload/js/blueimp-helper",
//				"blueimp-gallery" : "plugins/jqueryFileUpload/js/blueimp-gallery",
//				"blueimp-gallery-video" : "plugins/jqueryFileUpload/js/blueimp-gallery-video",
//				"docViewer-main" : "plugins/docViewer/js/docViewer-main"
//			},
//			shim : {
//			// "jquery.fileupload-image":{
//			// deps:["jquery.fileupload-process"]
//			// },
//			// "jquery.fileupload-audio":{
//			// deps:["jquery.fileupload-process"]
//			// },
//			// "jquery.fileupload-video":{
//			// deps:["jquery.fileupload-process"]
//			// },
//			// "jquery.fileupload-validate":{
//			// deps:["jquery.fileupload-process"]
//			// },
//			// "load-image-meta":{
//			// deps:["load-image"]
//			// },
//			// "load-image-exif":{
//			// deps:["load-image","load-image-meta"]
//			// },
//			// "jquery.fileupload-ui":{
//			// //
//			// deps:["jquery.ui.widget","bootstrap","tmpl","jquery.fileupload","jquery.fileupload-process","jquery.fileupload-image","jquery.fileupload-video","jquery.fileupload-audio","jquery.fileupload-validate"]
//			// }
//			}
//		})
// 上传控件用到的模版
function uploadTemplateInit() {
    var templStr = '';
    templStr += '<script id="template-upload" type="text/x-tmpl">';
    templStr += '{% for (var i=0, file; file=o.files[i]; i++) { %}';
    templStr += '<tr class="template-upload">';
    templStr += '<td>';
    templStr += '<span class="preview"></span>';
    templStr += '</td>';
    templStr += '<td>';
    templStr += '<p class="name">{%=file.name%}</p>';
    templStr += '<strong class="error text-danger"></strong>';
    templStr += '</td>';
    templStr += '<td>';
    templStr += '<p class="size">'+$.fn.jqUploadText.uploadIng+'</p>';
    templStr += '<div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>';
    templStr += '</td>';
    templStr += '<td>';
    templStr += '{% if (!i && !o.options.autoUpload) { %}';
    templStr += '<a class="btn btn-primary start" href="javascript:void(0); disabled>';
    templStr += '<i class="glyphicon glyphicon-upload"></i>';
    templStr += '<span>'+$.fn.jqUploadText.uploadText+'</span>';
    templStr += '</a>';
    templStr += '{% } %}';
    templStr += '{% if (!i) { %}';
    templStr += '<a class="btn btn-warning cancel" href="javascript:void(0);>';
    templStr += '<i class="glyphicon glyphicon-ban-circle"></i>';
    templStr += '<span>'+$.fn.jqUploadText.cancel+'</span>';
    templStr += '</a>';
    templStr += '{% } %}';
    templStr += '</td>';
    templStr += '</tr>';
    templStr += '{% } %}';
    templStr += '</script>';
    var topW = $(window.document.body);
    if (topW.find("#template-upload")) {
        tempStr = '';
    }
    return templStr;
}
// 加载图片
function loadImg(obj) {
    $(obj).parent().next().hide();
    $(obj).show();
    if ($(obj).attr("src") == contextPath + "/resources/plugins/jqueryFileUpload/img/word.png" || $(obj).attr("src") == contextPath + "/resources/plugins/jqueryFileUpload/img/xlsx.png" || $(obj).attr("src") == contextPath + "/resources/plugins/jqueryFileUpload/img/pptx.png" || $(obj).attr("src") == contextPath + "/resources/plugins/jqueryFileUpload/img/js.png" || $(obj).attr("src") == contextPath + "/resources/plugins/jqueryFileUpload/img/css.png" || $(obj).attr("src") == contextPath + "/resources/plugins/jqueryFileUpload/img/pdf.png" || $(obj).attr("src") == contextPath + "/resources/plugins/jqueryFileUpload/img/html.png" || $(obj).attr("src") == contextPath + "/resources/plugins/jqueryFileUpload/img/zip.png" || $(obj).attr("src") == contextPath + "/resources/plugins/jqueryFileUpload/img/txt.png") {} else {
        $(obj).parents(".template-download").find(".prewButton").removeClass("disabled");
    }
}
// 图片加载失败
function loadImgError(obj) {
    $(obj).parent().next().hide();
    $(obj).show();
}

//var docViewerType = getProperty("attachment.docViewerType", "false");
var docViewerType = true;

var imgs = {
    png: "true",
    jpg: "true",
    jpeg: "true",
    bmp: "true",
    gif: "true"
};
//初始化下载模版
function dwonloadTemplateInit(attachmentId) {
    var templStr = '';
    templStr += '<script id="template-download" type="text/x-tmpl">';
    templStr += '{% for (var i=0, file; file=o.files[i]; i++) { %}';
    templStr += '<tr class="template-download" id="{%=file.code%}">';
    templStr += '<td class="tdOne" style="width:5%;">';
    templStr += '<span>';
    templStr += '{% if (file.thumbnailUrl) { %}';
    templStr += '<a  title="{%=file.name%}" data-gallery><img src="{%=file.thumbnailUrl%}" onload="loadImg(this)" style="width:80px;display:none;" draggable="false" onerror="loadImgError(this)"></a>';
    templStr += '<img src="' + contextPath + '/resources/plugins/jqueryFileUpload/img/loading.gif" style="width:50px;"/>';
    templStr += '{% } %}';
    templStr += '</span>';
    templStr += '</td>';
    templStr += '<td class="tdTwo" style="width:30%;">';
    templStr += '<p class="name">';
    templStr += '{% if (file.url) { %}';
    // templStr+='<a title="{%=file.name%}" >{%=file.name%}</a>';
    // templStr+='{% } else { %}';
    templStr += '<span><a href="{%=file.url %}" target="{%=file.target %}">{%=file.name%}</a></span>';
    templStr += '{% } %}';
    templStr += '</p>';
    templStr += '{% if (file.error) { %}';
    templStr += '<div><span class="label label-danger">Error</span> {%=file.error%}</div>';
    templStr += '{% } %}';
    templStr += '</td>';
    templStr += '<td class="tdThree" style="width:5%;">';
    templStr += '<span class="size">{%=o.formatFileSize(file.size)%}</span>';
    templStr += '</td>';
    templStr += '<td class="tdFour" style="width:60%;">';
    templStr += '{% if (file.url) { %}';
    var isIE9 = !!navigator.userAgent.match(/MSIE 9.0/);
    if (docViewerType && docViewerType == 'true') { // 在线预览功能打开
        templStr += '<a class="btn btn-primary prewButton "  onclick="previewAttment(\'{%=file.url %}\',\'{%=file.type%}\',\'{%=file.name%}\',\'{%=file.docId%}\',' + (isIE9 ? '\'{%=file.name.substring(file.name.lastIndexOf(\'.\') + 1)%}\'' : '\'{%=file.suffix%}\'') + ')">';
    } else {
        templStr += '<a class="btn btn-primary prewButton "  style="display:none" onclick="previewAttment(\'{%=file.url %}\',\'{%=file.type%}\',\'{%=file.name%}\',\'{%=file.docId%}\',' + (isIE9 ? '\'{%=file.name.substring(file.name.lastIndexOf(\'.\') + 1)%}\'' : '\'{%=file.suffix%}\'') + ')">';
    }
    templStr += '<i class="glyphicon glyphicon-eye-open"></i>&nbsp;';
    templStr += '<span>'+$.fn.jqUploadText.preview+'</span>';
    templStr += '</a>\r\n';
    templStr += '{% } %}';
    templStr += '{% if (file.url) { %}';
    templStr += '<a class="btn btn-info download" target="_blank" href="{%=file.url %}">';
    templStr += '<i class="glyphicon glyphicon-download-alt"></i>';
    templStr += '<span>'+$.fn.jqUploadText.download+'</span>';
    templStr += '</a>\r\n';
    templStr += '{% } %}';
    templStr += '{% if (file.deleteUrl) { %}';
    templStr += '<a class="btn btn-danger delete" data-attachmentId = "{%=file.code%}" href="javascript:void(0);">';
    templStr += '<i class="glyphicon glyphicon-trash"></i>';
    templStr += '<span>'+$.fn.jqUploadText.deletes+'</span>';
    templStr += '</a>\r\n';
    templStr += '{% } else { %}\r\n';
    templStr += '<a class="btn btn-warning cancel" href="javascript:void(0);">';
    templStr += '<i class="glyphicon glyphicon-ban-circle"></i>';
    templStr += '<span>'+$.fn.jqUploadText.cancel+'</span>';
    templStr += '</a>\r\n';
    templStr += '{% } %}';
    templStr += '<a class="btn upatt" href="javascript:void(0);">';
    templStr += '<i class="glyphicon glyphicon-arrow-up"></i>';
    templStr += '</a>\r\n';
    templStr += '<a class="btn downatt" href="javascript:void(0);">';
    templStr += '<i class="glyphicon glyphicon-arrow-down"></i>';
    templStr += '</a>\r\n';
    templStr += '</td>';
    templStr += '</tr>';
    templStr += '{% } %}';
    templStr += '</script>';
    var topW = $(window.document.body);
    if (topW.find("#template-download")[0]) {
        topW.find("#template-download").remove();
    }
    return templStr;
}

$.jsRenderTemplate.register({
	id:"jqueryAttach.list",
	url:"/resources/plugins/jqueryFileUpload/templete/jqueryAttach.list.html"
});

// 初始化按钮
function buttonTemplateInit(id) {
    var templStr = '';
//    var isPosition = getProperty("attachment.buttonAndTableListPosition");
    var isPosition = "down";
    if (isPosition == "down") { // 上传按钮在table列表下部
        templStr += '<div class="container-fileupload"><table role="presentation" class="table table-striped"><tbody class="files"></tbody></table>';
        templStr += '<div class="row fileupload-buttonbar">';
        templStr += '<div class="col-lg-7">';
        templStr += '<span class="btn btn-primary fileinput-button defaultStyle" style="float:left;margin-right:10px;">';
        templStr += '<span>{tempName}</span>';
        templStr += '<input type="file" name="Filedata" multiple >';
        templStr += '</span>';
        templStr += '<div class="addElementBox"></div>';
        templStr += '</div>';
        templStr += '</div></div>';
    } else if (isPosition == "up" || isPosition == undefined) { // 上传按钮在table列表上部
        templStr += '<div class="container-fileupload">';
        templStr += '<div class="row fileupload-buttonbar">';
        templStr += '<div class="col-lg-7">';
        templStr += '<span class="btn btn-primary fileinput-button defaultStyle" style="float:left;margin-right:10px;">';
        templStr += '<span>{tempName}</span>';
        templStr += '<input type="file" name="Filedata" multiple >';
        templStr += '</span>';
        templStr += '<div class="addElementBox"></div>';
        templStr += '</div>';
        templStr += '</div>';
        templStr += '<table role="presentation" class="table table-striped"><tbody class="files"></tbody></table>';
        templStr += '</div>';
    }

    return templStr;
}
// 展示预览
function previewAttment(url, fileType, docName, docId, suffix) {
    var tempStr = '';
    tempStr += '<div id="grcspPreviewPic" style="display:none;" >';
    tempStr += '<div class="modal-backdrop fade in"></div>';
    tempStr += '<div style="opacity: 1;position: absolute;z-index: 99999;left: 50%;right: 50%;min-height:300px;width: 1000px;top:50%;line-height: 400px;';
    tempStr += 'background: #333;text-align: center;vertical-align: middle;"><div onclick="closePreviewAttachment()" style="color: white;line-height: 25px;';
    tempStr += 'width: 26px;height: 26px;font-size: 18px;position: absolute;background: #000;right: 0px;cursor: pointer;">x</div>';
    if (suffix == "png" || suffix == "gif" || suffix == "jpg" || suffix == "jpeg" || suffix == "bmp" || fileType.indexOf("image") != -1) {
        tempStr += '<img src="' + url + '" onload="loadPreviewAttachment()" style="max-width: 1000px;">';
    } else {
        tempStr += '<div id="GrcspDocumentViewer" style="max-width: 1000px;height:600px;"></div>';
    }
    tempStr += '</div>';
    tempStr += '</div>';
    var topW = $(window.document.body);
    if (topW.find("#grcspPreviewPic")[0]) {
        topW.find("#grcspPreviewPic").remove();
    }

    if (suffix == "png" || suffix == "gif" || suffix == "jpg" || suffix == "jpeg" || suffix == "bmp" || fileType.indexOf("image") != -1) {
//        require(["attachment"],
//        function() {
            previewImg(url);
//        });
    } else {
//        require(["component/attachmentPreview"],
//        function() {
//            var options = {
//                title: docName,
//                attachmentId: docId
//            };
//            attachmentPreview.render(options);
//        });
    }
}
function closePreviewAttachment() {
    var topW = $(window.parent.document.body);
    topW.find("#grcspPreviewPic").remove();
}
function loadPreviewAttachment() {
    var topW = $(window.parent.document.body);
    topW.find("#grcspPreviewPic").show();
    var width = topW.find("#grcspPreviewPic>div").eq(1).width() / 2;
    var height = topW.find("#grcspPreviewPic>div").eq(1).height() / 2;
    topW.find("#grcspPreviewPic>div").eq(1).css({
        "margin-left": "-" + width + "px",
        "margin-top": "-" + height + "px"
    });

}
function extendAttachmentBtnFun(attachment, attachmentId, groupId) {
    var extendBtnVariable = $("#extendBtnVariable" + groupId).val();
    // 扩展附件操作按钮
    if (typeof ExtendAttachmentButtons !== "undefined" && !extendBtnVariable) {
        for (var i = 0; i < ExtendAttachmentButtons.length; i++) {
            var button = ExtendAttachmentButtons[i];
            var buttonLink = document.createElement("a");
            buttonLink.href = "#";
            buttonLink.className = "btn btn-primary prewButton " + button.className;
            buttonLink.innerHTML = button.text;
            buttonLink.onclick = function() {
                button.onClick(attachment, attachmentId, groupId);
            }
            attachment.fileOptionDiv.eq(1).append(buttonLink);
            // buttonLink.append('<i class="glyphicon
            // glyphicon-eye-open"></i>');
        }
    } else if (extendBtnVariable) {
        var extendBtnObjs = eval(extendBtnVariable);
        for (var i = 0; i < extendBtnObjs.length; i++) {
            var button = extendBtnObjs[i];
            var buttonLink = document.createElement("a");
            buttonLink.href = "#";
            buttonLink.className = "btn btn-primary prewButton " + button.className;
            buttonLink.innerHTML = button.text;
            // buttonLink.onclick=function(){button.onClick(attachment,attachmentId);}
            $(buttonLink).on("click", {
                clickEvent: button.onClick
            },
            function(e) {
                e.data.clickEvent(attachment, attachmentId);
            });
            attachment.fileOptionDiv.eq(1).append(buttonLink);
            // buttonLink.append('<i class="glyphicon
            // glyphicon-eye-open"></i>');
        }
    }
    // 设置宽度
    var liW = $(attachment.fileProgressWrapper).width();
    var opW = $(attachment.fileOptionDiv).width() + 20;
    var iconW = $(attachment.fileImgSpan).width();
    var inW = liW - opW - iconW;
    if ($(attachment.fileProgressWrapper).parent().hasClass("small")) {
        inW = inW - 50
    }
    $(attachment.fileProcessDiv).find(".attaTitle").width(inW);
}
//define(
//		[ "jquery", "jquery.ui.widget", "tmpl", "jquery.blueimp-gallery",
//				"jquery.iframe-transport", "jquery.fileupload",
//				"jquery.fileupload-ui" ],
//		function() {
var fileuploadMain = new Object();

// 图片小图标的判断显示
function setFileType(file, attachmentId,groupId) {
    var filename = file.name;
    var index1 = filename.lastIndexOf(".");
    var index2 = filename.length + 1;
    var fileType = filename.substring(index1, index2).substring(1);

    // application text image
    if (fileType.indexOf("jpg") != -1 || fileType.indexOf("png") != -1 || fileType.indexOf("gif") != -1 || fileType.indexOf("jpeg") != -1 || fileType.indexOf("bmp") != -1) {
        // file.thumbnailUrl=contextPath+"/user/component/AttachmentController/viewImage.html?attachmentId="+attachmentId;
        file.thumbnailUrl = contextPath + "/resources/plugins/jqueryFileUpload/img/jpg.png";
    } else if (fileType.indexOf("doc") != -1 || fileType.indexOf("uot") != -1) {
        file.thumbnailUrl = contextPath + "/resources/plugins/jqueryFileUpload/img/word.png";
    } else if (fileType == "pdf" || fileType == "ofd") {
        file.thumbnailUrl = contextPath + "/resources/plugins/jqueryFileUpload/img/pdf.png";
    } else if (fileType.indexOf("xls") != -1) {
        file.thumbnailUrl = contextPath + "/resources/plugins/jqueryFileUpload/img/xlsx.png";
    } else if (fileType.indexOf("ppt") != -1) {
        file.thumbnailUrl = contextPath + "/resources/plugins/jqueryFileUpload/img/pptx.png";
    } else if (fileType == "js") {
        file.thumbnailUrl = contextPath + "/resources/plugins/jqueryFileUpload/img/js.png";
    } else if (fileType == "css") {
        file.thumbnailUrl = contextPath + "/resources/plugins/jqueryFileUpload/img/css.png";
    } else if (fileType.indexOf("htm") != -1 && fileType.indexOf("sp") != -1 && fileType.indexOf("php") != -1) {
        file.thumbnailUrl = contextPath + "/resources/plugins/jqueryFileUpload/img/html.png";
    } else if (fileType.indexOf("zip") != -1 || fileType.indexOf("rar") != -1 || fileType.indexOf("7z") != -1 || fileType.indexOf("tar") != -1 || fileType.indexOf("lzh") != -1) {
        file.thumbnailUrl = contextPath + "/resources/plugins/jqueryFileUpload/img/zip.png";
    } else {
        file.thumbnailUrl = contextPath + "/resources/plugins/jqueryFileUpload/img/txt.png";
    }
    file.docId = attachmentId;
    var source = $("#fileSourceType"+ groupId).val();
    var sourceId = $("#fileSourceId"+ groupId).val();
    var instId = $("#instId").val();
    file.url = "../user/component/AttachmentController/download?attachmentId=" + attachmentId+"&source="+source;
    if(sourceId){
      file.url =  file.url +"&sourceId="+sourceId;
    }
    if(instId){
      file.url =  file.url +"&instId="+instId;
    }
    
    file.target = "_self";
    return file;
}

function maxNumFun(id, attachmentId) {
    var addable = $("#addable" + id).val();
    if (addable != "false") {
        var maxNum = $("#maxNum" + id).val();
        if (maxNum != "" && maxNum != undefined) {
            maxNum = parseInt(maxNum);
            if ($('#formIds' + id + ' .container-fileupload .files>tr').length > maxNum) {
                $('#formIds' + id + ' .container-fileupload .btn.btn-primary.fileinput-button.defaultStyle').hide();
            } else {
                $('#formIds' + id + ' .container-fileupload .btn.btn-primary.fileinput-button.defaultStyle').show();
            }
        }
    }

    var groupId = $("#formIds" + id).find("input[name=groupId]").val();
    $("#formIds" + id + " #ATAH_PARAM_DEL_" + groupId).val($("#ATAH_PARAM_DEL_" + groupId).val() + attachmentId + ",");
}

function minLengthFun(id, showMode) {
  var minLength = $('#minLength' + id).val();
  if (minLength) {
    var msgEle = $('#popover_' + id);
    var fileCount = $('#formIds' + id + ' .container-fileupload .files>tr').length;
    if (showMode == 'delete') {
      fileCount--;
    }
    if (fileCount < parseInt(minLength)) {
      if (msgEle.length > 0) {
        $(msgEle).show();
      } else {
        var html = '<div class="popover fade top in" id="popover_' + id + '" style="margin-top: 0px;display: block;">' + 
        '<div class="arrow" style="left: 50.5814%;"></div>' + 
        '<div class="popover-inner" style="width: 180px"><div class="popover-content">' + $.fn.jqUploadText.validAttachText + minLength + '</div></div></div>';
        $('#formIds' + id + ' .container-fileupload .btn.btn-primary.fileinput-button.defaultStyle').after(html);
      }
    } else {
      if (msgEle.length > 0) {
        $(msgEle).hide();
      }
    }
  }
}

fileuploadMain.init = function() {
        var arrIds = new Array();
        var ids = $(".uploadPlaceHolder").length;
        for (var i = 0; i < ids; i++) {
            arrIds.push($(".uploadPlaceHolder").eq(i).attr("id"));
        }
        var uploadTemp = uploadTemplateInit();
        var dwonloadTemp = dwonloadTemplateInit();
        $.each(arrIds,
        function(i) {
            var id = arrIds[i];
            if ($("#formIds" + id).length == 1) {
                return;
            } else {
                var buttonTemp = buttonTemplateInit(id).replace("{tempName}", $("#text" + id).val());
                $("#" + id).after(uploadTemp);
                $("#" + id).after(dwonloadTemp);
                $("#" + id).parents("div").eq(0).attr("id", "formIds" + id);
                
                // 上传按钮渲染前(自定义上传按钮样式)
                if(typeof _jqueryAttach_extend != 'undefined' 
                  && _jqueryAttach_extend && _jqueryAttach_extend.beforeLoadUploadButton 
                  && typeof _jqueryAttach_extend.beforeLoadUploadButton === 'function'){
                  buttonTemp = _jqueryAttach_extend.beforeLoadUploadButton($("#formIds" + id),buttonTemp);
                }
                $("#" + id).parents("div").eq(0).append(buttonTemp);
                
                var files = eval("attachments_" + id);
                if(files.length >= parseInt($("#maxNum" + id).val())){
                  $("#formIds" + id +' .btn.btn-primary.fileinput-button.defaultStyle').hide();
                }
                // 判断附件标签是否是在模态窗口里面
                var modalBody = $("#" + id).parents(".modal-body")[0];
                var form = null;
                if (modalBody) {
                    form = $("#" + id).parents(".modal-body form")[0];
                } else {
                    form = $("#" + id).parents("form")[0];
                }
                // var
                // form=$("#"+id).parents("form")[0];
                // 判断页面是否存在form标签
                if (!form) {
                    var html = $("#" + id).parents("div").eq(0).html();
                    var obj = $("#" + id).parents("div").eq(0);
                    obj.html("");
                    obj.append("<form method=\"get\" enctype=\"multipart/form-data\"></form>");
                    obj.find("form").html(html);
                }
                var formId = $(form).find('input[name=formId]').val();
                var beanId = $(form).find('input[name=beanId]').val() == "" ? -1 : $(form).find('input[name=beanId]').val();
                var instId = null;
                if ($(form).find('input[name=mode]').val() == "CREATE") {
                  instId = $(form).find('input[name=instId]').val() == "0" ? -1 : $(form).find('input[name=instId]').val();
                } else {
                  instId = $(form).find('input[name=instId]').val() == "" ? -1 : $(form).find('input[name=instId]').val();
                }
                // 解决部门情况下以下变量为undefined的情况
                if(!formId){
                  formId = -1;
                }                
                if(!beanId){
                  beanId = -1;
                }                
                if(!instId){
                  instId = -1;
                }
                var form = $("#formIds" + id);
                var cId = form.parent().attr("rscc");
                // 上传文件类型
                var fileType = form.find('input[name=file_types]').val();
                if (fileType != "*.*" && fileType) {
                  allowType = fileType.split("*.").join("").substr(0).split(";").join("|");
                  form.fileupload({
                      acceptFileTypes: new RegExp('(\.|\/)(' + allowType + ')$')
                  });
                  var acceptType = fileType.split("*").join("").substr(0).split(";").join(",");
                  form.find('input[name=Filedata]').attr("accept", acceptType);
                } else {//限制文件上传的类型为默认配置的
                    var defaultFileType = form.find('input[name=default_file_types]').val();
                    if (!defaultFileType){
                      defaultFileType = $('input[name=default_file_types]').val();
                    }
                    defaultFileType = defaultFileType || "";
                    var defaultAllowType = defaultFileType.replace(/\./g, "").split(",").join("|");
                    form.fileupload({
                      acceptFileTypes: new RegExp('(\.|\/)(' + defaultAllowType + ')$')
                    });
                    form.find("input[name=Filedata]").attr("accept", defaultFileType);
                }
                
                // 定义按钮样式
                var styleStr = "<style type='text/css'>" + $("#style" + id).val() + "</style>";
                $("#" + id).after(styleStr);
                // 设置按钮宽度
                var btnWidth = form.find('input[name=button_width]').val();
                if (btnWidth != "") {
                    form.find('.btn.btn-primary.fileinput-button.defaultStyle').width(btnWidth);
                }
                // 设置按钮高度
                var btnHeight = form.find('input[name=button_height]').val();
                if (btnHeight != "") {
                    form.find('.btn.btn-primary.fileinput-button.defaultStyle').height(btnHeight);
                }
                // 设置是否可以添加附件
                var addable = $("#addable" + id).val();
                if (addable == "false") {
                    form.find('.btn.btn-primary.fileinput-button.defaultStyle').hide();
                } else {
                  // 上传按钮及设置样式 渲染完成后(自定义上传按钮样式)
                  if(typeof _jqueryAttach_extend != 'undefined' 
                    && _jqueryAttach_extend && _jqueryAttach_extend.afterLoadUploadButton 
                    && typeof _jqueryAttach_extend.afterLoadUploadButton === 'function'){
                    _jqueryAttach_extend.afterLoadUploadButton(form, form.find('.btn.btn-primary.fileinput-button.defaultStyle'));
                  }
                }

                // 设置是否可以下载附件
                var downloadable = $("#downloadable" + id).val();
                if (downloadable == "false") {
                    var styleStr1 = "<style type='text/css'>#formIds" + id + " .container-fileupload .btn.btn-info.download{display:none;}</style>";
                    $("#" + id).after(styleStr1);
                }
                // 设置最大上传文件数量
                var maxNum = $("#maxNum" + id).val();
                if (maxNum != "" && maxNum != undefined) {
                    maxNum = parseInt(maxNum);
                    form.fileupload({
                        maxNumberOfFiles: maxNum
                    });
                    if ($('#formIds' + id + ' .container-fileupload .files>tr').length >= maxNum) {
                        $('#formIds' + id + ' .container-fileupload .btn.btn-primary.fileinput-button.defaultStyle').hide();
                    }
                }
                // 取消按钮事件
                $('#formIds' + id + ' .container-fileupload  .btn.btn-warning.cancel').unbind().on("click",
                function(e) {
                    var maxNum = $("#maxNum" + id).val();
                    if (maxNum != "" && maxNum != undefined) {
                        maxNum = parseInt(maxNum);
                        if ($('#formIds' + id + ' .container-fileupload .files>tr').length < maxNum) {
                            $('#formIds' + id + ' .container-fileupload .btn.btn-primary.fileinput-button.defaultStyle').show();
                        }
                    }
                });
                // 自定义元素追加回调
                var func = $("#addElementFun" + id).val();
                if (func && (typeof(window[func]) === "function")) {
                    window[func]();
                }

                // 最大上传文件大小（单位M）
                var maxSize = form.find('input[name=file_size_limit]').val();
                if (maxSize) {
                    maxSize = parseInt(maxSize.replace("MB", "")) * 1024 * 1024;
                    form.fileupload({
                        maxFileSize: maxSize
                    });
                }
                var creatorId = $("#formIds" + id).find("input[name=grcspCreatorId]").val();
                var typeCode = $("#formIds" + id).find("input[name=typeCode]").val();
                var encrypt = $("#formIds" + id).find("input[name=encrypt]").val();
                var validImmediately = $("#formIds" + id).find("input[name=validImmediately]").val();
                var typeName = $("#formIds" + id).find("input[name=typeName]").val();
                form.fileupload({
                    autoUpload: true,
                    messages: {
                        maxNumberOfFiles: $.fn.jqUploadText.maxfiles,
                        acceptFileTypes: $.fn.jqUploadText.fileTypeNo,
                        maxFileSize: $.fn.jqUploadText.fileLarge,
                        minFileSize: $.fn.jqUploadText.fileSmall
                    },

                    url: "../user/component/AttachmentController/upload",
                    dataType: 'text',
                    formData: {
                    	formId: formId,
                    	beanId: beanId,
                    	instId: instId,
                      groupId: id,
                      grcspCreatorId: creatorId,
                      typeCode: typeCode,
                      encrypt: encrypt,
                      validImmediately: validImmediately,
                      cId:cId,
                      pageId:pageConfig.id
                    },abort:function(jqXHR, textStatus, errorThrown){
                      var maxNum = $("#maxNum" + id).val();
                      if (maxNum != "" && maxNum != undefined) {
                          maxNum = parseInt(maxNum);
                          if ($('#formIds' + id + ' .container-fileupload .files>tr').length < maxNum) {
                              $('#formIds' + id + ' .container-fileupload .btn.btn-primary.fileinput-button.defaultStyle').show();
                          }
                      }
                    },
                    done: function(e, data) { // 上传后执行
                        if (e.isDefaultPrevented()) {
                            return false;
                        }
                        // 设置最大上传文件数量
                        var newfiles = JSON.parse(data.result);
                        var maxNum = $("#maxNum" + id).val();
                        if (maxNum != "" && maxNum != undefined) {
                            maxNum = parseInt(maxNum);
                            if ($('#formIds' + id + ' .container-fileupload .files>tr').length >= maxNum) {
                                $('#formIds' + id + ' .container-fileupload .btn.btn-primary.fileinput-button.defaultStyle').hide();
                            }
                        }
                        //校验最小上传文件个数
                        minLengthFun(id, 'add');
                        
//                        conlose.log(data);
//                        conlose.log("----------------------------------");
//                        conlose.log(data.result);
                        var rulObj = $.parseJSON(data.result);
                        if(rulObj.value && rulObj.value=='notype'){
                          $(this).find('.error').text($.fn.jqUploadText.fileTypeNo);
                        }
                        var attachmentId = $.parseJSON(data.result).message;
                        var fileName = $.parseJSON($.parseJSON(data.result).value).name;
                        var that = $(this).data('blueimp-fileupload') || $(this).data('fileupload'),
                        getFilesFromResponse = data.getFilesFromResponse || that.options.getFilesFromResponse,
                        files = data.files,
                        template,
                        deferred;
                        if (data.context) {
                            data.context.each(function(index) {
                                var file = files[index] || {
                                    error: 'Empty file upload result'
                                };
                                var fileClone = new Object();
                                $.extend(fileClone,file);
                                file = fileClone;
                                file.name =fileName;
                                // 图片预览
                                file = setFileType(file, attachmentId,id);
                                file.deleteUrl = "../user/component/AttachmentController/deleteAttachment";
                                deferred = that._addFinishedDeferreds();
                                var beforeDrawFileTrFun = "beforeDrawFileTrFun";
                                if ((typeof(window[beforeDrawFileTrFun]) === "function")) {
                                    window[beforeDrawFileTrFun](file);
                                }
                                that._transition($(this)).done(function() {
                                    var node = $(this);
                                    template = that._renderDownload([file]).replaceAll(node);
                                    // 点击删除
                                    $(template).find(".delete").unbind().click({
                                        id: id,
                                        attachmentId: attachmentId
                                    }, function(e) {
                                        //var validImmediately = getProperty("validImmediately");
                                    	//var validImmediately = "true";
                                        if (validImmediately == true || validImmediately == "true") {
                                            var paramObj = new Object();
                                            paramObj.groupId = e.data.id;
                                            paramObj.attachmentId = e.data.attachmentId;
                                            $.ajax({
                                                url: "../user/component/AttachmentController/deleteAttachment",
                                                type: "POST",
                                                async: false,
                                                data: paramObj,
                                                success: function(data) {
                                                    // 设置删除成功回调函数
                                                    var delSuccFun = $("#delSuccFun" + id).val();
                                                    if (delSuccFun != undefined && delSuccFun != "") {
                                                        if (delSuccFun.indexOf(")") != -1) {
                                                            delSuccFun = delSuccFun.substring(0, delSuccFun.lastIndexOf(")")) + ",'" + attachmentId + "')";
                                                            eval(delSuccFun);
                                                        } else {
                                                            eval(delSuccFun + "('" + attachmentId + "')");
                                                        }
                                                    }
                                                    // 设置最大上传文件数量
                                                    maxNumFun(id, attachmentId);
                                                    // 校验最小上传文件个数
                                                    minLengthFun(id, 'delete');
                                                }
                                            });
                                        } else {
                                            // 设置删除成功回调函数
                                            var delSuccFun = $("#delSuccFun" + id).val();
                                            if (delSuccFun != undefined && delSuccFun != "") {
                                                if (delSuccFun.indexOf(")") != -1) {
                                                    delSuccFun = delSuccFun.substring(0, delSuccFun.lastIndexOf(")")) + ",'" + attachmentId + "')";
                                                    eval(delSuccFun);
                                                } else {
                                                    eval(delSuccFun + "('" + attachmentId + "')");
                                                }
                                            }
                                            // 设置最大上传文件数量
                                            maxNumFun(id, attachmentId);
                                            // 校验最小上传文件个数
                                            minLengthFun(id, 'delete');
                                        }
                                    });
                                 // 点击上移
                                    $(template).find(".upatt").unbind().click({
                                        id: id,
                                        attachmentId: attachmentId
                                    }, function(e) {
                                    	var objParentTR = $(this).parent().parent();
                                        var prevTR = objParentTR.prev();
                                        if(prevTR.length > 0) {
                                            prevTR.insertAfter(objParentTR);
                                        }
                                    });
                                 // 点击下移
                                    $(template).find(".downatt").unbind().click({
                                        id: id,
                                        attachmentId: attachmentId
                                    }, function(e) {
                                    	var objParentTR = $(this).parent().parent();
                                        var nextTR = objParentTR.next();
                                        if(nextTR.length > 0) {
                                            nextTR.insertBefore(objParentTR);
                                        }
                                    });
                                 // 点击重命名
                                    $(template).find(".rename").unbind().click({
                                        id: id,
                                        attachmentId: attachmentId
                                    }, function(e) {
                                    	var fjname = this.parentNode.parentNode.getElementsByClassName("tdTwo")[0].getElementsByTagName("a")[0];
                                    	$.seniorDialog({title:$.fn.jqUploadText.fileNameReset,buttons:[{label:$.fn.jqUploadText.okBut, click:function(dialog){
                                 			var newName = dialog.find('#newName').val();
                                 			var paramObj = new Object();
                                 			paramObj.attachmentId = e.data.attachmentId;
                                            paramObj.newName = newName;
                                            $.ajax({
                                               url: "../user/component/AttachmentController/attachmentReName",
                                               type: "POST",
                                               async: false,
                                               data: paramObj,
                                               success: function(data) {
                                            	   if(data=="true"){
                                            		   $.tip($.fn.jqUploadText.editOk);
                                            		   dialog.modal("hide");
                                            		   var name = fjname.innerText;
                                            		   var namel = name.split(".");
                                            		   fjname.innerText = newName+"."+namel[1];
                                            	   }
                                               }
                                            });
                                 		 }}],onload:function(dialog){
                                 			 dialog.find(".modal-body").append("<form action=\"#\" class=\"form-horizontal\"><div class=\"form-group\" ><label class=\"col-md-3 control-label\">"+$.fn.jqUploadText.newName+"</label><div class=\"col-md-6\"><input name=\"newName\"  class=\"form-control\"  value=\"\" id=\"newName\"></div></div>" +
                                 					 "</form>");
                                 		 }});
                                    	 
                                    });
                                    that._forceReflow(template);
                                    that._transition(template).done(function() {
                                        data.context = $(this);
                                        that._trigger('completed', e, data);
                                        that._trigger('finished', e, data);
                                        deferred.resolve();
                                        file.fileOptionDiv = $("td a[href$='" + attachmentId + "']").parent();
                                        try {
                                            extendAttachmentBtnFun(file, attachmentId, id);
                                        } catch(e) {
                                            this.debug(e);
                                        }
                                        $(this).attr("id", attachmentId);
                                        // $("#"+id).after(dwonloadTemp);
                                        var groupId = $("#formIds" + id).find("input[name=groupId]").val();
                                        $("#formIds" + id + " #ATAH_PARAM_ADD_" + groupId).val($("#ATAH_PARAM_ADD_" + groupId).val() + attachmentId + ",");
                                        if (!!navigator.userAgent.match(/MSIE 9.0/)) {//获取文件类型时，对ie9特殊处理
                                          var fileName = file.name;
                                          var fileType = fileName.substring(fileName.lastIndexOf('.') + 1);
                                        } else {
                                          var fileType = file.type;
                                          var sufLen = fileType.indexOf('/');
                                          if (sufLen != -1 && fileType.length > (sufLen + 1)) {
                                              fileType = fileType.substring(fileType.indexOf('/') + 1);
                                          }
                                        }
                                        if (fileType in imgs) { // 图片直接就可以预览
                                            template.find("a[class='btn btn-primary prewButton ']").removeAttr("style");
                                        }

                                        // 上传成功后的回调函数名称
                                        var succFun = $("#succFun" + groupId).val();
                                        if (succFun != undefined && succFun != "") { // 上传成功后会调方法
                                            if (succFun.indexOf(")") != -1) {
                                                succFun = succFun.substring(0, succFun.lastIndexOf(")")) + ",'" + groupId + "','" + attachmentId + "')";
                                                eval(succFun);
                                            } else {
                                                eval(succFun + "('" + groupId + "','" + attachmentId + "')");
                                            }
                                        }
                                    });

                                    // 设置是否可以下载附件
                                    var downloadable = $("#downloadable" + id).val();
                                    if (downloadable == "false") {
                                        $("#formIds" + id + " .container-fileupload .btn.btn-info.download").hide();
                                    }

                                });
                            });
                        }
                    }
                });

            }
        });

        $.each(arrIds, function(i) {
            var id = arrIds[i];
            var groupId = id;
            var creatorId = $("#formIds" + id).find("input[name=grcspCreatorId]").val();
            //获取意见签署狂带附件控件中的groupId
            var opinionAttachmentgroupId = $("#opinionAttachmentId").find("div").find("input[name='button_placeholder_id']").val();
//            var showFileTypeCode = $("#showFileTypeCode" + id).val();
//            // 初始化加载附件组
//            var url = null;
//            if (showFileTypeCode == "") {
//                url = contextPath + "/user/component/AttachmentController/getValidAttachments?groupId=" + groupId + "&t=" + new Date().getTime();
//            } else {
//                showFileTypeCode = showFileTypeCode + "," + typeCode;
//                url = contextPath + "/user/component/AttachmentController/getValidAttachmentsByTypeCode?groupId=" + groupId + "&showFileTypeCode=" + showFileTypeCode + "&t=" + new Date().getTime();
//            }
            
            var validImmediately = $("#formIds" + id).find("input[name=validImmediately]").val();
            
                    $("#objSpan" + id).hide();
                    $("#dFrame" + id).hide();
                    var files = eval("attachments_" + groupId);
                    var downloadTempId = "template-download";
                    /**
                     * 如果页面中存在jquery附件控件以及意见签署框带附件控件时，在清除页面时没有判断具体是清除jquery附件还是意见签署框
                     */
                    if (files.length == 0 && groupId == opinionAttachmentgroupId) {
                        $("#formIds" + id + " .container-fileupload .files").html("");
                    } else {
                        for (var i = 0; i < files.length; i++) {
                            var file = files[i];
                            file.preview = $.fn.jqUploadText.preview;
                            file.download = $.fn.jqUploadText.download;
                            file.deletes = $.fn.jqUploadText.deletes;
                            file.cancel = $.fn.jqUploadText.cancel;
                            file.isSort = $("#addable"+id).val()=="true"?true:false;
                            var attachmentId = file.code;
                            file = setFileType(file, attachmentId,groupId);
                            // file.url=contextPath+"/user/component/AttachmentController/download.html?attachmentId="+attachmentId;
                            file.deleteUrl = "../user/component/AttachmentController/deleteAttachment";
                            
                            //var temple = $("#template-download").tmpl(downloadTempId);
                            //var temple = $("#" + downloadTempId).tmpl();
                            // 是否显示删除按钮
                            file.deleteable = JSON.parse($("#deleteable" + id).val());
                            
                            var fileType = file.type;
                            var sufLen = fileType.indexOf('/');
                            if (sufLen != -1 && fileType.length > (sufLen + 1)) {
                                fileType = fileType.substring(fileType.indexOf('/') + 1);
                            }
                            if (fileType in imgs) { // 图片直接就可以预览
                            	file.isImgs = true;
                            }
                            
                            var beforeDrawFileTrFun = "beforeDrawFileTrFun";
                            if ((typeof(window[beforeDrawFileTrFun]) === "function")) {
                                window[beforeDrawFileTrFun](file);
                            }
                            
//                            var htmlStr = $("#template-download").tmpl({
//                                files: [file],
//                                formatFileSize: function() {
//                                    var bytes = file.size;
//                                    if (typeof bytes !== 'number') {
//                                        bytes = parseInt(bytes);
//                                    }
//                                    if (bytes >= 1000000000) {
//                                        return (bytes / 1000000000).toFixed(2) + ' GB';
//                                    }
//                                    if (bytes >= 1000000) {
//                                        return (bytes / 1000000).toFixed(2) + ' MB';
//                                    }
//                                    return (bytes / 1000).toFixed(2) + ' KB';
//                                },
//                                options: {}
//                            });
                            if (i == 0) {
                                $("#formIds" + id + " .container-fileupload .files").html("");
                            }
                            
                            //$("#formIds" + id + " .container-fileupload .files").append(htmlStr);
                            
                            $.views.helpers({
                            	formatFileSize: function(bytes) {
                                    //var bytes = file.size;
                                    if (typeof bytes !== 'number') {
                                        bytes = parseInt(bytes);
                                    }
                                    if (bytes >= 1024000000) {
                                        return (bytes / 1024000000).toFixed(2) + ' GB';
                                    }
                                    if (bytes >= 1024000) {
                                        return (bytes / 1024000).toFixed(2) + ' MB';
                                    }
                                    if (bytes >= 1024) {
                                      return (bytes / 1000).toFixed(2) + ' KB';
                                    }
                                    return (bytes).toFixed(2) + ' B';
                                }
                            });
                            
                            // jquery控件渲染之前调用
                            if(typeof _jqueryAttach_extend != 'undefined' 
                              && _jqueryAttach_extend && _jqueryAttach_extend.beforeLoad 
                              && typeof _jqueryAttach_extend.beforeLoad === 'function'){
                              _jqueryAttach_extend.beforeLoad($("#formIds" + id + " .container-fileupload .files"),files);
                            }
                            
                            
                           
                            file.fileOptionDiv = $("td a[href$='" + attachmentId + "']").parent();
                            extendAttachmentBtnFun(file, attachmentId, groupId);
                            $("#formIds" + id + " .template-download").removeClass("fade").addClass("fadeIn");

                            // jquery控件页面渲染完成后回调
                            if(typeof _jqueryAttach_extend != 'undefined' 
                              && _jqueryAttach_extend && _jqueryAttach_extend.afterLoadSuccess 
                              && typeof _jqueryAttach_extend.afterLoadSuccess === 'function'){
                              _jqueryAttach_extend.afterLoadSuccess($("#formIds" + id + " .container-fileupload .files"),files);
                            }
                            
                            // 加载附件成功后的回调函数
                            var func = $("#loadSuccFun" + groupId).val();
                            if (func && (typeof(window[func]) === "function")) {
                                window[func](data);
                            }
                            
                            // 设置最大上传文件数量
                            var maxNum = $("#maxNum" + groupId).val();
                            if (maxNum != "" && maxNum != undefined) {
                                maxNum = parseInt(maxNum);
                                if ($('#formIds' + groupId + ' .container-fileupload .files>tr').length >= maxNum) {
                                    $('#formIds' + groupId + ' .container-fileupload .btn.btn-primary.fileinput-button.defaultStyle').hide();
                                }
                            }

                            // 设置是否可以下载附件
                            var downloadable = $("#downloadable" + id).val();
                            if (downloadable == "false") {
                                $("#formIds" + id + " .container-fileupload .btn.btn-info.download").hide();
                            }
                        }
                        // files 数据准备好，可一次渲染完成
                        $.jsRenderTemplate.render("jqueryAttach.list", "#formIds" + id + " .container-fileupload .files", {"files":files,"contextPath":contextPath}, function (){
                          //jqueryAttach.list 可编辑页面的附件列表排序
                          $('#formIds' + id + ' .container-fileupload .btn.downatt').on("click", function(e){
                            var objParentTR = $(this).parent().parent();
                            var nextTR = objParentTR.next();
                            if(nextTR.length > 0) {
                                nextTR.insertBefore(objParentTR);
                            }
                          });
                          //jqueryAttach.list 可编辑页面的附件列表排序
                          $('#formIds' + id + ' .container-fileupload .btn.upatt').on("click", function(e){
                            var objParentTR = $(this).parent().parent();
                            var prevTR = objParentTR.prev();
                            if(prevTR.length > 0) {
                                prevTR.insertAfter(objParentTR);
                            }
                          });
                          // 删除操作
                          $("#formIds" + id + " .container-fileupload .files .btn.btn-danger.delete").on("click", function(e) {
                            var groupId = $("#formIds" + id).find("input[name=groupId]").val();
                            var fileId = $(this).attr("data-attachmentId");
                            $("#formIds" + id + " #ATAH_PARAM_DEL_" + groupId).val($("#ATAH_PARAM_DEL_" + groupId).val() + fileId + ",");
                            // 即时删除
                            //var validImmediately = getProperty("validImmediately");
                            // var validImmediately = "true";
                            if (validImmediately == true || validImmediately == "true") {
                                var paramObj = new Object();
                                paramObj.groupId = id;
                                paramObj.attachmentId = fileId;
                                $.ajax({
                                    url: "../user/component/AttachmentController/deleteAttachment",
                                    type: "POST",
                                    async: false,
                                    data: paramObj,
                                    success: function(data) {
                                        // 设置删除成功回调函数
                                        var delSuccFun = $("#delSuccFun" + id).val();
                                        // if(func&&(typeof(window[func])==="function")){
                                        // window[func](files);
                                        // }
                                        if (delSuccFun != undefined && delSuccFun != "") {
                                            if (delSuccFun.indexOf(")") != -1) {
                                                eval(delSuccFun);
                                            } else {
                                                eval(delSuccFun + "('" + attachmentId + "')");
                                            }
                                        }
                                        // 设置最大上传文件数量
                                        maxNumFun(id, fileId);
                                        // 校验最小上传文件个数
                                        minLengthFun(id, 'delete');
                                    }
                                });
                            } else {
                                // 设置删除成功回调函数
                                var delSuccFun = $("#delSuccFun" + id).val();
                                if (delSuccFun != undefined && delSuccFun != "") {
                                    if (delSuccFun.indexOf(")") != -1) {
                                        delSuccFun = delSuccFun.substring(0, delSuccFun.lastIndexOf(")")) + ",'" + attachmentId + "')";
                                        eval(delSuccFun);
                                    } else {
                                        eval(delSuccFun + "('" + attachmentId + "')");
                                    }
                                }
                                // 设置最大上传文件数量
                                maxNumFun(id, attachmentId);
                                // 校验最小上传文件个数
                                minLengthFun(id, 'delete');
                            } 
                          });
                        });
                    }
    });
}

$(function(){
	function validAttach(event, cbscope, retdata){
	  	var arrIds = new Array();
	    var ids = $(".uploadPlaceHolder").length;
	    for (var i = 0; i < ids; i++) {
	        arrIds.push($(".uploadPlaceHolder").eq(i).attr("id"));
	    }
	    
	    $.each(arrIds,function(i){
	    	var id = arrIds[i];
            var groupId = id;
            
            var validImmediately = $("#formIds" + id).find("input[name=validImmediately]").val();
            if (validImmediately != true || validImmediately != "true"){
            	var addVal = $("#ATAH_PARAM_ADD_" + groupId).val();
            	var delVal = $("#ATAH_PARAM_DEL_" + groupId).val();
            	
            	if(addVal && addVal.lastIndexOf(",") == addVal.length-1){
            		addVal = addVal.substring(0,addVal.length-1);
            	}
            	if(delVal && delVal.lastIndexOf(",") == delVal.length-1){
            		delVal = delVal.substring(0,delVal.length-1);
            	}
            	var atah_param_add_array = null;
            	if(addVal){
            		atah_param_add_array = addVal.split(",");
            	}
            	var atah_param_del_array = null;
            	
            	if(delVal){
            		atah_param_del_array = delVal.split(",");
            	}
            	
		    	if(atah_param_add_array &&atah_param_add_array.length>0){
		    		var request_param_add = new Object(); 
		    		request_param_add.groupId = groupId;
		    		request_param_add.attachmentIds = atah_param_add_array;
		    		request_param_add.valid = true;
		    		// 添加部分
		    		$.ajax({
		    			url: "../user/component/AttachmentController/updateAttachValid",
		    			async: false,
		    			type: "POST",
		    			data: request_param_add,
		    			success: function(data) {
		    				
		    			}
		    		});
		    	}
		    	if(atah_param_del_array&&atah_param_del_array.length>0){
		    		var request_param_del = new Object(); 
		    		request_param_del.groupId = groupId;
		    		request_param_del.attachmentIds = atah_param_del_array;
		    		request_param_del.valid = false;
		    		// 删除部分
		    		$.ajax({
		    			url: "../user/component/AttachmentController/updateAttachValid",
		    			async: false,
		    			type: "POST",
		    			data: request_param_del,
		    			success: function(data) {
		    				
		    			}
		    		});
		    	}
            }
	    });
	}
	$(document).bind("after.workflow.doSave",function(event, cbscope, retdata){
		validAttach(event, cbscope, retdata);
	});
	  
	$(document).bind("after.workflow.doApprove",function(event, cbscope, retdata){
		validAttach(event, cbscope, retdata);
	});
	
	$(document).bind("after.workflow.ajaxSubmitPageflowForm",function(event, cbscope, retdata){
		validAttach(event, cbscope, retdata);
	});
});
