<section id="login" class="row">
    <div class="col-md-6 md-offset-3">
        <form id="loginForm">
            <section id="error">
                <p style="color: red">Login failed!</p>
            </section>
            <div class="form-group">
                <label for="emailInp">Email</label>
                <input id="emailInp" type="text" name="email" class="form-control">
            </div>
            <div class="form-group">
                <label for="passwordInp">Password</label>
                <input id="passwordInp" type="password" name="password" class="form-control">
            </div>
            <button type="button" class="btn btn-primary" onclick="login()">Login</button>
        </form>
    </div>
</section>