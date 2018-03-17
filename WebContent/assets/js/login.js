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
            document.getElementById('login').classList.remove('active');
            document.getElementById('welcomeScreen').classList.add('active');
            connect();
        })
        .catch(() => {
            document.getElementById('error').classList.add('active');
        });
}