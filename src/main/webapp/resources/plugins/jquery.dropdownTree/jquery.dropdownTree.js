jQuery.jsRenderTemplate.register({
	id:"plugin.dropdownTree",
	url: "resources/plugins/jquery.dropdownTree/jquery.dropdownTree.html"
});
/**
 * 比$.dwrTree多添加了两个必填参数: id,生成控件的ID, 其它参数使用方法同$.dwrTree， name: 用于表单值的提交
 */
(function($) {
  "use strict";
  $.fn.dropdownTree = function(options){
	  var self = $(this);
	  $.jsRenderTemplate.render("plugin.dropdownTree",self,options);
	  var tree = self.find("#dropDownTree_" + options.id);
	  var okClick = function(event, treeId, treeNode){
		  // 层级授权的情况下，对虚拟节点进行处理
		  if(treeNode.id == 'authRoot' || treeNode.id == 'auth_edit' || treeNode.id == "auth_root" || 
				  treeNode.id == "auth_oper" || treeNode.id == "auth_oper_edit" ||
				  treeNode.id == "auth_flow" ||treeNode.id == "auth_flow_edit"){
			  $.tip('该节点不允许操作！')
			  return;
		  }
		  self.find("#dropDownTree_display_value_" + options.id).val(treeNode.name);
		  self.find("#" + options.id).val(treeNode.id).trigger("change");
		  self.find("#dropTree_" + options.id).removeClass("open");
		 
	  };
	  if(self.closest(".dropdown-content").length>0){
		  self.find("#dropDownTree_display_value_" + options.id).click(function(){
			  self.find("#dropTree_" + options.id).addClass("open");
		  });
	  }
	  
	  if(!options.setting) {
		  options.setting = {};
	  }
	  if(!options.setting.callback) {
		  options.setting.callback = {};
	  }
	  if(options.setting.callback.onClick) {
		  var originalOkClick = options.setting.callback.onClick;
		  options.setting.callback.onClick = function(event, treeId, treeNode){
			  okClick(event, treeId, treeNode);
			  originalOkClick(event, treeId, treeNode);
		  };
	  } else {
		  options.setting.callback.onClick = okClick;
	  }
	  return tree.dwrTree(options);
  };
})(jQuery);