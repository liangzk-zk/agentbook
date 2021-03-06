(function($) {

	var _setting = {
		data : {
			simpleData : {
				enable : true,
				idKey : 'id',
				pIdKey : 'pId'
			}
		}
	};
	var zt = $.fn.zTree;
	var tools = zt._z.tools;
	var consts = zt.consts;
	var view = zt._z.view;
	var data = zt._z.data;
	var event = zt._z.event;
	var $$ = tools.$;

	data.exSetting(_setting);
	view.asyncNode = function(setting, node, isSilent, callback) {
		var i, l;
		if (node && !node.isParent) {
			tools.apply(callback);
			return false;
		} else if (node && node.isAjaxing) {
			return false;
		} else if (tools.apply(setting.callback.beforeAsync, [ setting.treeId, node ], true) == false) {
			tools.apply(callback);
			return false;
		}
		if (node) {
			node.isAjaxing = true;
			var icoObj = $$(node, consts.id.ICON, setting);
			icoObj.attr({
				"style" : "",
				"class" : consts.className.BUTTON + " " + consts.className.ICO_LOADING
			});
		}

		var callParams = [];
		if (tools.isArray(setting.async.baseParam)) {
			for (i = 0, l = setting.async.baseParam.length; i < l; i++) {
				callParams.push(setting.async.baseParam[i]);
			}
		}
		for (i = 0, l = setting.async.autoParam.length; node && i < l; i++) {
			var pKey = setting.async.autoParam[i].split("="), spKey = pKey;
			if (pKey.length > 1) {
				spKey = pKey[1];
				pKey = pKey[0];
			}
			callParams.push(node[pKey]);
			// tmpParam[spKey] = node[pKey];
		}
		if (tools.isArray(setting.async.otherParam)) {
			for (i = 0, l = setting.async.otherParam.length; i < l; i += 2) {
				// tmpParam[setting.async.otherParam[i]] =
				// setting.async.otherParam[i + 1];
				callParams.push(setting.async.otherParam[i + 1]);
			}
		} else {
			for ( var p in setting.async.otherParam) {
				// tmpParam[p] = setting.async.otherParam[p];
				callParams.push(setting.async.otherParam[p]);
			}
		}

		callParams.push({
			callback : function(ret) {
				var newNodes = [];
				for ( var i = 0; i < ret.length; i++) {
					var item = ret[i];
					var isParent = item.leaf !== true; 
					newNodes.push({
						groupId: item.groupId,
						id : item.id,
						pId : item.props.parentId,
						name : item.text,
						isParent: isParent,
						attrs: $.extend(true, {}, item)
					});
				}

				if (node) {
					node.isAjaxing = null;
					node.zAsync = true;
				}
				view.setNodeLineIcos(setting, node);
				if (newNodes && newNodes !== "") {
					newNodes = tools.apply(setting.async.dataFilter, [ setting.treeId, node, newNodes ], newNodes);
					view.addNodes(setting, node, !!newNodes ? tools.clone(newNodes) : [], !!isSilent);
				} else {
					view.addNodes(setting, node, [], !!isSilent);
				}
				setting.treeObj.trigger(consts.event.ASYNC_SUCCESS, [ setting.treeId, node, ret ]);
				tools.apply(callback);
			},
			exceptionHandler : function(errorString, exception) {
				var exClassName = exception.javaClassName;

				if (node)
					node.isAjaxing = null;
				view.setNodeLineIcos(setting, node);
				setting.treeObj.trigger(consts.event.ASYNC_ERROR, [ setting.treeId, node, errorString, exception ]);
			}
		});

		setting.async.dwrCall.apply(this, callParams);

		return true;
	};

})(jQuery);