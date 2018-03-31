function login(){
    let form = document.forms["loginForm"];
    let params = new URLSearchParams();
    params.append('email', form.email.value);
    params.append('password', form.password.value);

    axios({
        method: 'POST',
        url: '/api/authentication/login',
        headers: {
            'content-type': "application/x-www-form-urlencoded",
        },
        data: params
    })
        .then(({data}) => {
            localStorage.setItem('token', data.token);
            section.login.hide(500);
            section.welcome.show(500);
            connectSocket();
        })
        .catch(() => {
            document.getElementById('error').classList.add('active');
        });
}