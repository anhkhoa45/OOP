<template>
  <div class="row">
    <div class="col-md-6 offset-md-3">
      <form id="loginForm">
        <section style="display: none" id="error">
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
        <button type="button" class="btn btn-primary" @click="login">Login</button>
      </form>
    </div>
  </div>
</template>

<script>
  import { post } from '../helper/request'

  export default {
    methods: {
      login() {
        let form = document.forms["loginForm"];
        post('/api/authentication/login', {'email': form.email.value, 'password': form.password.value})
          .then(({data}) => {
            this.$store.commit('saveToken', data.token);
            this.$router.push({name: 'welcomeScreen'});
          })
          .catch(() => {
            document.getElementById('error').classList.add('active');
          });
      }
    }
  }
</script>
