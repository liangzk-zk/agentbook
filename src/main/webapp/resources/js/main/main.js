$(function(){
  var menuDiv = $(".nav-item,.active,.submenu");
  menuDiv.find("li").click(function(e){
      var self = $(this);
      var contentFrame = $("#contentFrame");
      var linkDom = self.children("a[inner-href]"); 
      var innerHref = linkDom.attr("inner-href");
      if(innerHref) {
        //contentFrame.remove();
        //contentDiv.load(innerHref);
        contentFrame.attr("src",innerHref);
      }
  });
  var defaultDom = $("#user");
  defaultDom.click();
})