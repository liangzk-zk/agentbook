function getUrlParam(name){
  // 用该属性获取页面 URL 地址从问号 (?) 开始的 URL（查询部分）
  var url = window.location.search;
  // 正则筛选地址栏
  var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
  // 匹配目标参数
  var result = url.substr(1).match(reg);
  //返回参数值
  return result ? decodeURIComponent(result[2]) : null;
};