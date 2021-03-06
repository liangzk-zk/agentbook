
function preLoad() {
	if (!this.support.loading) {
		showMsg(getI18nMessage("attachment.swfupload.notavailable"));
		return false;
	}
}

function loadFailed() {
	showMsg(getI18nMessage("attachment.swfupload.loaderror"));
}

function fileQueued(file) {
	try {
		var options = new Object();
		options.file = file;
		options.targetID = "pt"+this.groupId;
		options.mode = this.mode;
		options.parentDiv=$(this.movieElement).parents("div").eq(0);
		var progress = new FileProgress(options);
		progress.setStatus(getI18nMessage("SWFUpload.PROCESS.WAIT_UPLOAD"), false);
		progress.toggleCancel(true, this);
	} catch (ex) {
		this.debug(ex);
	}
}

function fileQueueError(file, errorCode, message) {
	try {
		var errorMsg = "";
		if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
			if (message === this.maxNum) {
				errorMsg = getI18nMessage("SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED.maxNum", message);
			} else if (message === "0"){
				errorMsg = getI18nMessage("SWFUpload.PROCESS.ALREADY_MAXNUM");
			} else {
				errorMsg = getI18nMessage("SWFUpload.PROCESS.FILENUM_LEFT", message);
			}
			showMsg(errorMsg);
			return;
		}

		var options = new Object();
		options.file = file;
		options.targetID = "pt"+this.groupId;
		options.mode = this.mode;
		options.parentDiv=$(this.movieElement).parents("div").eq(0);
		var progress = new FileProgress(options);
		progress.setError();
		progress.toggleCancel(false);
		
		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			errorMsg = getI18nMessage("SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT", this.settings.file_size_limit);
			break;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			errorMsg = getI18nMessage("SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE");
			break;
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
			errorMsg = getI18nMessage("SWFUpload.QUEUE_ERROR.INVALID_FILETYPE");
			break;
		default:
			if (file !== null) {
				errorMsg = getI18nMessage("SWFUpload.UNKNOWN_ERROR");
			}
			break;
		}
		progress.setStatus(errorMsg, false);
		this.setTimer(setTimeout(function () {
			progress.disappear();
		}, 5000));
	} catch (ex) {
        this.debug(ex);
    }
}

function fileDialogComplete(numFilesSelected, numFilesQueued) {
	try {
		this.startUpload();
	} catch (ex)  {
        this.debug(ex);
	}
}

function uploadStart(file) {
	try {
		var options = new Object();
		options.file = file;
		options.targetID = "pt"+this.groupId;
		options.mode = this.mode;
		options.parentDiv=$(this.movieElement).parents("div").eq(0);
		var progress = new FileProgress(options);
		progress.setStatus(getI18nMessage("SWFUpload.PROCESS.UPLOADING"), false);
		progress.toggleCancel(true, this);
	}
	catch (ex) {
	}
	
	return true;
}

function uploadProgress(file, bytesLoaded, bytesTotal) {
	try {
		var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
		var options = new Object();
		options.file = file;
		options.targetID = "pt"+this.groupId;
		options.mode = this.mode;
		options.parentDiv=$(this.movieElement).parents("div").eq(0);
		var progress = new FileProgress(options);
		progress.setProgress(percent);
		progress.setStatus(getI18nMessage("SWFUpload.PROCESS.UPLOADING")+percent+"%", false);
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadSuccess(file, serverData) {
	var o = JSON.parse(serverData);
	try {
		var options = new Object();
		options.file = file;
		options.targetID = "pt"+this.groupId;
		options.parentDiv=$(this.movieElement).parents("div").eq(0);
		$("#ATAH_PARAM_ADD_"+this.groupId,options.parentDiv).val($("#ATAH_PARAM_ADD_"+this.groupId,options.parentDiv).val() + o.message + ",");
		if ($("#pt"+this.groupId).is(":visible")) {
			options.mode = this.mode;
			
			var progress = new FileProgress(options);
			progress.setComplete();
			progress.toggleCancel(false);
			if ("smart" === this.mode) {
				progress.setStatus("", false);
			} else {
				progress.setStatus(getI18nMessage("SWFUpload.UPLOAD_SUCCESS"), true);
			}
			try {
				progress.showOptionLink(this, o.message);
			} catch (ex1) {
				this.debug(ex1);
			}
	  		var stats = this.getStats();
			if (parseInt(stats.successful_uploads) >= parseInt(this.maxNum)) {
				$("#"+this.movieName).css("width","0px");
				$("#"+this.movieName).css("height","0px");
				if ($("#"+this.objectSpanId).length > 0) {
					$("#"+this.objectSpanId).addClass("btnHidden");
				}
			}
			
			$("#num"+this.groupId).attr("value", stats.successful_uploads);
	  		attaAutoAdapting();
  		}
  		if (this.succFun != undefined && this.succFun != "") {
  			eval(this.succFun+"('"+o.message+"')");
  		}
  		//刷新indiDocx
  		//初始化附件在线编辑
  		var isIndiDoc=$("#isIndiDoc"+this.groupId,options.parentDiv).val();
  		if(isIndiDoc=="true"){
  		require("indiDocxCommon").init()};
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadComplete(file) {
	try {
		this.startUpload();
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadError(file, errorCode, message) {
	try {
		var options = new Object();
		options.file = file;
		options.targetID = "pt"+this.groupId;
		options.mode = this.mode;
		options.parentDiv=$(this.movieElement).parents("div").eq(0);
		var progress = new FileProgress(options);
		progress.setError();
		progress.toggleCancel(false);
		var errorMsg = "";
		switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
			errorMsg = getI18nMessage("SWFUpload.UPLOAD_ERROR.HTTP_ERROR") + message + "。";
			break;
		case SWFUpload.UPLOAD_ERROR.MISSING_UPLOAD_URL:
			errorMsg = getI18nMessage("SWFUpload.UPLOAD_ERROR.MISSING_UPLOAD_URL");
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
			errorMsg = getI18nMessage("SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED");
			break;
		case SWFUpload.UPLOAD_ERROR.IO_ERROR:
			errorMsg = getI18nMessage("SWFUpload.UPLOAD_ERROR.IO_ERROR");
			break;
		case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
			errorMsg = getI18nMessage("SWFUpload.UPLOAD_ERROR.SECURITY_ERROR");
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			errorMsg = getI18nMessage("SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED");
			break;
		case SWFUpload.UPLOAD_ERROR.SPECIFIED_FILE_ID_NOT_FOUND:
			errorMsg = getI18nMessage("SWFUpload.UPLOAD_ERROR.SPECIFIED_FILE_ID_NOT_FOUND");
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
			errorMsg = getI18nMessage("SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED");
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
			errorMsg = getI18nMessage("SWFUpload.UPLOAD_ERROR.FILE_CANCELLED");
			progress.setCancelled();
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			errorMsg = getI18nMessage("SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED");
			break;
		default:
			errorMsg = getI18nMessage("SWFUpload.UPLOAD_ERROR.UNKNOWN_ERROR") + error_code + "。";
			break;
		}
		progress.setStatus(errorMsg, false);
	} catch (ex) {
        this.debug(ex);
    }
}

function showMsg(msg) {
	bootbox.alert(msg);
}

function createUL(targetDivId, mode, groupId) {
	var clsName = "attachments";
	if ("smart" === mode) {
		clsName = "attachments small";
	}
	var displayUL = document.createElement("ul");
	displayUL.id = "pt"+groupId;
	displayUL.className = clsName;
	$("#"+targetDivId).append(displayUL);
}

function loadAttachments(groupId) {
	var timestamp = (new Date()).valueOf(); 
	var actionUrl = GlobalParam.context + "/component/AttachmentController/getValidAttachments.html";
	var swfUpload = this;
	var groupId=groupId;
	$.ajax({
		url: actionUrl,
		type: 'GET',
		data: {"groupId":groupId||this.groupId, "t":timestamp},
		success: function(data) {
			if(swfUpload!=window){
				createAttachmentLi(data, swfUpload);
				}else{
					showAttachmentLi(data, groupId);
				}
			//attaAutoAdapting();
		},
		complete:function(){
			if (swfUpload.addable == "false") {
				$("#"+swfUpload.movieName).css("width","0px");
				$("#"+swfUpload.movieName).css("height","0px");
				if ($("#"+swfUpload.objectSpanId).length > 0) {
					$("#"+swfUpload.objectSpanId).addClass("btnHidden");
				}
			}
			if(typeof(resort)=="function"){
				resort();
			}
		}
	});
}

//modified by yanligang 2015-03-11 忽略已删除附件的显示
function createAttachmentLi(attachmentArray, swfUpload) {
	var stats = swfUpload.getStats();
	var deleteds = $("#ATAH_PARAM_DEL_" + swfUpload.groupId).val();
	for (var i = 0;i < attachmentArray.length; i++) {
		var attachmentId = attachmentArray[i].id;
		if(deleteds && deleteds.indexOf(attachmentId) != -1) {
			continue;
		}
		attachmentArray[i].id = swfUpload.groupId + attachmentId;
		var options = new Object();
		options.file = attachmentArray[i];
		options.targetID = "pt" + swfUpload.groupId;
		options.mode = swfUpload.mode;
		options.parentDiv=$(swfUpload.movieElement).parents("div").eq(0);
		var progress = new FileProgress(options);
		progress.setComplete();
		progress.toggleCancel(false);
		progress.setStatus("", false);
		progress.showOptionLink(swfUpload, attachmentId);
		stats.successful_uploads++;
	}
	if (parseInt(stats.successful_uploads) >= parseInt(swfUpload.maxNum)) {
		$("#"+swfUpload.movieName).css("width","0px");
		$("#"+swfUpload.movieName).css("height","0px");
		if ($("#"+swfUpload.objectSpanId).length > 0) {
			$("#"+swfUpload.objectSpanId).addClass("btnHidden");
		}
		
	}
	swfUpload.setStats(stats);
	$("#num"+swfUpload.groupId).attr("value", stats.successful_uploads);
}


function showAttachments(groupId, mode, downloadable, deleteable,printable,editable,isMarkable,viewable,parentDiv) {
	var timestamp = (new Date()).valueOf(); 
	var actionUrl = GlobalParam.context + "/component/AttachmentController/getValidAttachments.html";
	$.ajax({
		url: actionUrl,
		type: 'GET',
		data: {"groupId":groupId, "t":timestamp},
		success: function(data) {
			showAttachmentLi(data, groupId, mode, downloadable, deleteable,printable,editable,isMarkable,viewable,parentDiv);
			attaAutoAdapting();
			if(typeof(resort)=="function"){
				resort();
			}
		}
	});
}

function showAttachmentLi(attachmentArray, groupId, mode, downloadable, deleteable,printable,editable,isMarkable,viewable,parentDiv) {
	var parentTags=$(".attachments").parents();
	var hideObjs=[];
	for(var i=parentTags.length;i>=0;i--){
		if($(parentTags[i]).is(":hidden")){
			var obj=new Object();
			obj.tag=parentTags[i];
			obj.style=$(parentTags[i]).attr("style");
			hideObjs.push(obj);
			$(parentTags[i]).show();
		}
	}
	
	for (var i = 0;i < attachmentArray.length; i++) {
		var attachmentId = attachmentArray[i].id;
		attachmentArray[i].id = groupId + attachmentId;
		var options = new Object();
		options.file = attachmentArray[i];
		options.targetID = "pt" + groupId;
		options.mode = mode;
		options.parentDiv=parentDiv;
		var progress = new FileProgress(options);
		progress.setComplete();
		progress.toggleCancel(false);
		progress.setStatus("", false);
		progress.showDeleteOrDownload(downloadable, deleteable,printable,editable,isMarkable,viewable, groupId, attachmentId,parentDiv);
	}
	for(var i=0;i<hideObjs.length;i++){
		//$(hideObjs[i]).hide();
		var obj=hideObjs[i];
		$(obj.tag).removeAttr("style");
		if(obj.style){
		$(obj.tag).attr("style",obj.style);
		}
	}
	
}

//获取附件数量
function getAttachmentNumber(groupId) {
	var size = $('#formIds'+groupId+ ' .container-fileupload .files tr').length;
	if(size == 0){
		size = $("#num"+groupId).val();
	}
	if(size == undefined || size ==''){
		size = 0;
	}
	return size;
}

function attaAutoAdapting() {
	$(".attachments").each(function(idx,ul){
		var groupId = $(ul).attr("id").substring(2,$(ul).attr("id").length);
		var attaLiW = 0;
		var itemDefaultWidth = $("#itemDefaultWidth"+groupId).val();
		if (itemDefaultWidth != "") {
			attaLiW = parseInt(itemDefaultWidth);
		}else if ($("#pt" + groupId + " li:first-child").length > 0) {
			attaLiW = $("#pt" + groupId + " li:first-child").width();
		} else {
			var attachmentsW = $(ul).width();
			var maxSize = Math.floor(attachmentsW/300);
			if (maxSize < 1) {
				maxSize = 1;
			}
			attaLiW = Math.floor(attachmentsW / maxSize) - 29;
			if (attaLiW < 0) {
				attaLiW = 0;
			}
		}
		$(ul).find("li").css({"width":attaLiW});
		var blankWidth = 150;
		if ("normal" === $("#mode"+groupId).val()) {
			blankWidth = 105;
		}
		var textW = 0;
		if (attaLiW - blankWidth > 0) {
			textW = attaLiW - blankWidth;
		}
		var dispalyLinkNum = 0;
		$($(ul).find("li").get(0)).find("a").each(function(j,aItem){
			if ("none" != $(aItem).css("display")) {
				dispalyLinkNum++;
			}
		});
		if (dispalyLinkNum <= 4) {
			textW = textW + 30 - dispalyLinkNum*30;
		}
		//$(ul).find(".attaTitle").css({"width":textW});
	});
};

$(window).resize(function(){
	attaAutoAdapting();
});

var slCtl;
/** 打开word文件 */
function openOfficeFile(unidId){
	var unid = unidId;

	//通过这个参数设置只读
	$("#flowdef_idxeditable").val(false);
	//dojo.require("dijit.Tooltip");
	slCtl= document.getElementById("indidocx-wfeditor");
	//pluginloaded();
	//OpenMyFile(fname);
	OpenFileByUnid(unid);
}

/** 编辑word文件 */
function editOfficeFile(unidId,isMarkable){
	var unid = unidId;
	//通过这个参数设置可编辑
	$("#flowdef_idxeditable").val(true);
	//通过这个参数设置是否留痕
	if(isMarkable=="true"){
		$("#idx_fileopenoriginal").val(1);
	}else{
		$("#idx_fileopenoriginal").val(0);
	}
	//dojo.require("dijit.Tooltip");
	slCtl= document.getElementById("indidocx-wfeditor");
	//pluginloaded();
	OpenFileByUnid(unid);
}

/** 保存编辑的word文件 */
function saveEditOfficeFile() {
	var slCtl = document
			.getElementById("indidocx-wfeditor");
	//pluginloaded();
	if (slCtl && slCtl.Content && slCtl.Content.Control) {
		require("grcspAlert").alert({
			context : "在线编辑组件:附件正在上传，请等待...",
			targetID : "alertDiv",
			cls : "alert",
			timeout : 2000
		});
		slCtl.Content.Files.AllFilePost = function() {// 如果文件上传成功，则提交表单
		};
		slCtl.Content.Files.ErrorOccurred = function() {
			$("#indiform").removeAttr("disabled");
			$("#alertDiv").hide();
			return false;
		};
		slCtl.Content.Control.UpdateAllFiles();

	}
}



/** 打印word文件 */
function printOfficeFile(unidId){
	
	var funid = unidId;
	//dojo.require("dijit.Tooltip");
	slCtl= document.getElementById("indidocx-wfeditor");
	//pluginloaded();
	//PrintFile(fname,true);
	PrintOutByUnid(funid,true);
}
/*点击图片名称，进行预览*/
function previewImg(src){
	var modalStr="<div id='previewImgModal' class='modal   fade in' style='right: auto;width:auto;left:50%;top:17%;margin-left:0px;margin-top:0px; z-index:10051'>" +
	"<div class='modal-body icon-loading' style='padding: 10px;max-height:auto;'>" +
		'<div class="preview-tools-btn">'+
			'<button id="bigzoom" class="btn" style="background-color:#eee;" onclick="imgBigzoom()"><span class="glyphicon glyphicon-zoom-in" style="font-size:25px;"></span></button>'+
			'<button id="smallzoom" class="btn" style="background-color:#eee; margin-right:6px;" onclick="imgSmallzoom()"><span class="glyphicon glyphicon-zoom-out" style="font-size:25px;"></span></button>'+
			"<a onclick='previewImgColsed()' style='position: absolute;top: 0px;right: 0px;width: 30px;height: 30px;background: transparent url("+ contextPath +"/resources/plugins/jqueryFileUpload/img/fancybox.png) -40px 0;cursor: pointer'></a>"+
		'</div>'+
		'<div class="preview-content" align="center">'+
			"<img id='image1' src='"+src+"' style='' onload=''/>"+
		'</div>'+
	"</div></div>"+
	"<div class='modal-backdrop fade in'></div>";
	var topW = $(window.top.document.body);
	if($("#previewImgModal",topW).length>0){
		$("#previewImgModal",topW).remove();
	}
	topW.append(modalStr);
	$("#previewImgModal img",topW).on("load",previewImgLoaded);
	//使用mCustomScrollbar实现图片加滚动条，支持ie8浏览器   
	//require(["mCustomScrollbar","mousewheel"],function(){
		$(".preview-content").width(900).height(500);
		$(".preview-content").css("overflow","auto");
	    $(".preview-content").mCustomScrollbar();
	//});
}
function previewImgLoaded(){
	var topW = $(window.top.document.body);
//	topW.find("#previewImgModal").modal("show");
	setTimeout( function (e) {
		var _this=$("#previewImgModal",topW);
		_this.show();
		$(".modal-body",_this).removeClass("icon-loading");
//		var left=_this[0].offsetLeft-_this.width()/2;
//		var top=_this[0].offsetTop-_this.height()/2;
		var left=window.top.document.documentElement.clientWidth/2 -_this.width()/2;
		var top=window.top.document.documentElement.clientHeight/2 -_this.height()/2;
		_this.css({"left":left,"top":top});  
	},200);
	
}
function previewImgColsed(){
	var topW = $(window.top.document.body);
	topW.find(".modal-backdrop").remove();
	topW.find("#previewImgModal").remove();
}
/*图片预览放大专用*/
function imgBigzoom() {
	var img = document.getElementById('image1');
	if(img.offsetWidth <= img.naturalWidth*5){
	img.style.width = 1.2 * img.width + "px";
	img.style.height = 1.2 * img.height + "px";
	}
}
/*图片预览缩小专用*/
function imgSmallzoom() {
	var img = document.getElementById('image1');
	if(img.offsetWidth >= img.naturalWidth/5){
	img.style.width = 0.8 * img.width + "px";
	img.style.height = 0.8 * img.height + "px";
	}
}