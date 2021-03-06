$(function () {
    $('input').val('')
    $('.btn-login').on('click', function () {
      var username = $("#username").val();
      var password = $("#password").val();
      if(!username){
        alert("用户名不允许为空！");
        return ;
      }
      if(!password){
        alert("密码不允许为空！");
        return ;
      }
      $.ajax({
        type: "POST",
        url:"sign",
        data: {account:username, passwd:password},
        success: function(data){
          if(data.code == 200){
            window.location="main"
          }else{
            alert(data.msg);
          }
         }
        });
    })
})