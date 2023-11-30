function askVerifyCode(){
    $.get('/bookmanager/api/auth/verify-code', {
        email: $("#input-email").val()
    })
}