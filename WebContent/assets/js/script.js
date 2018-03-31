$(document).ready(function () {
    window.section = {
        login: $('#login'),
        welcome: $('#welcomeScreen'),
        gameBox: $('#gameBox'),
    };

    $('section').hide();
    section.login.show();

    if(localStorage.getItem('token')){
        section.login.hide(500);
        section.welcome.show(500);
        connectSocket();
    }
});