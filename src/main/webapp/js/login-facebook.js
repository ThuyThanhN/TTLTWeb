// login-facebook.js

window.fbAsyncInit = function() {
    FB.init({
        appId      : '1610596812975790',
        cookie     : true,
        xfbml      : true,
        version    : 'v19.0'
    });
    FB.AppEvents.logPageView();
};

(function(d, s, id){
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) {return;}
    js = d.createElement(s); js.id = id;
    js.src = "https://connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

function fbLogin() {
    FB.login(function(response) {
        if (response.authResponse) {
            FB.api('/me', {fields: 'id,name,email'}, function(userInfo) {
                // Gửi dữ liệu về server qua form ẩn
                document.getElementById('fbUserId').value = userInfo.id;
                document.getElementById('fbUserName').value = userInfo.name;
                document.getElementById('fbUserEmail').value = userInfo.email || ''; // email có thể null
                document.getElementById('fbLoginForm').submit();
            });
        } else {
            alert('Bạn chưa cho phép đăng nhập Facebook.');
        }
    }, {scope: 'email'});
}
