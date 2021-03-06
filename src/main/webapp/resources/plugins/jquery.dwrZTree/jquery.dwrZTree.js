/**
 * 使用方法:
 * $("#tree").tree({
 * 		dwrCall:pageDesignService.getChildren,	// dwr服务的方法
 * 		baseParam: [ true ],					// dwr服务的固定参数
 * 		autoParam: ['id'],						// 从树的节点属性中提取值并作为dwr服务的参数
 * 		setting:{},								// ztree的settings参数，默认展开根节点，至少大于1
 * 		expandedLevel:2,				// 展开的层级
 * 		data:									// 树的数据列表
 * 		[{
 * 			id: 'CX',
 * 			pId: null,
 * 			name: 'XXX',
 * 			isParent: true			//这个是必须的
 * 		}]
 * });
 * 
 $("#workArea").dwrTree({
	dwrCall:pageDesignService.getChildren,
	baseParam: [ true ],
	autoParam: ['id'],
	setting:{},
	data:[{
		id: 'CX',
     		pId: null,
     		name: 'XXX',
      		isParent: true
	}]
});
 * 
 */
(function($) {
  "use strict";
  $.fn.dwrTree = function(options){
	  var defualtOptions = {
		dwrCall:null,
		baseParam:[],
		autoParam:[],
		setting:{},
		data:[]
	  };
	  var dwrZtree = null;
	  options = $.extend({},defualtOptions, options);
	  var originalSetting = options.setting;

	  var innerSetting = {
	    async: {
			enable: true,
			dwrCall: options.dwrCall,
			baseParam: options.baseParam,
			autoParam: options.autoParam,
			dataFilter: options.dataFilter
		},
		view: {
			showLine: true,
			selectedMulti: false,
			dblClickExpand: false
		},
		callback:{
			onAsyncSuccess:function(event, treeId, parendNode, childrenData) {
				var expLevel = parseInt(options.expandedLevel);
				if(!isNaN(options.expandedLevel) && parendNode.level < (options.expandedLevel - 1)) {
					dwrZtree.expandNode(parendNode, true, false , true);
					$.each(parendNode.children, function(index,child){
						dwrZtree.expandNode(child, true, false , true);
					});
				}
				if(parendNode.cascadeExpand) {
					var ch = null;
					var subId = parendNode.cascadeExpand.shift();
					$.each(parendNode.children, function(index,child){
						if(child.id==subId){
							ch = child;
						}
					});
					if(ch) {
						if(parendNode.cascadeExpand.length != 0){
							ch.cascadeExpand = parendNode['cascadeExpand'];
						}
						dwrZtree.expandNode(ch, true, false , true);
						dwrZtree.selectNode(ch);
					}
					delete parendNode['cascadeExpand'];
				}
				// 根据父节点的只读属性设置节点为只读
				if(parendNode.permType && parendNode.permType == "VIEW") {
					var childNodes = parendNode.children;
			    	if (childNodes.length>0) {
						for(var i=0; i<childNodes.length; i++){
							childNodes[i].permType = "VIEW";
						}
			    	}
				}
				if(originalSetting.callback && originalSetting.callback.onAsyncSuccess){
					originalSetting.callback.onAsyncSuccess(event, treeId, parendNode, childrenData);
				}
			}
		}
	  };
	  options.setting = $.extend(true,{},innerSetting, options.setting);
	  $.extend(true,options.setting.callback || {},innerSetting.callback);
	  var self = $(this);
	  self.addClass("ztree");
	  dwrZtree = $.fn.zTree.init(self, options.setting, options.data);
	  // 根据需要是否展开第一层节点
	  if(options.firstNodeAutoOpen == null || options.firstNodeAutoOpen){
		  dwrZtree.expandNode(dwrZtree.getNodes()[0], true, true, true);
	  }
	  return dwrZtree;
	  
  };
})(jQuery);