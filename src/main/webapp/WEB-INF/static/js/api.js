function askVerifyCode(){
    $.get('/bookmanager/api/auth/verify-code', {
        email: $("#input-email").val()
    }, function () {
        alert("邮件发送成功!")
    })
}