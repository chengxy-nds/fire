<template>
  <div id="login">
    <div class="m-login-bg">
      <div class="m-login">
        <h3>fire</h3>
        <div class="m-login-warp">
          <form class="layui-form">
            <div class="layui-form-item">
              <input type="text" name="title" required lay-verify="required" placeholder="用户名" autocomplete="off"
                     class="layui-input">
            </div>
            <div class="layui-form-item">
              <input type="text" name="password" required lay-verify="required" placeholder="密码"
                     autocomplete="off" class="layui-input">
            </div>
            <div class="layui-form-item">
              <div class="layui-inline">
                <input type="text" name="verity" required lay-verify="required" placeholder="验证码"
                       autocomplete="off" class="layui-input">
              </div>
              <div class="layui-inline">
                <img class="verifyImg" onclick=""
                     src="@/assets/images/login/yzm.jpg"/>
              </div>
            </div>
            <div class="layui-form-item m-login-btn">
              <div class="layui-inline">
                <button class="layui-btn layui-btn-normal" lay-submit lay-filter="login">登录</button>
              </div>
              <div class="layui-inline">
                <button type="reset" class="layui-btn layui-btn-primary">取消</button>
              </div>
            </div>

            <div class="layui-form-item">
              <div class="layui-inline">
                <img class="login-img-third" v-on:click="loginByGithub()" title="Github"
                     src="@/assets/images/login/github.jpg"/>
              </div>
              <div class="layui-inline" style="">
                <img class="login-img-third" v-on:click="facelogin()" title="人脸识别"
                     src="@/assets/images/login/renlian.jpg"/>
              </div>
            </div>
            <div class="layui-form-item">
              <p style="font-size: 12px; text-align: center">其他登录方式</p>
            </div>
          </form>
          <div class="toast" v-show="toastShow">
            {{toastText}}
          </div>
        </div>
        <p class="copyright"></p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      toastShow: false,
      toastText: ''
    }
  },
  methods: {
    loginByGithub: function () {
      window.location.href = 'https://github.com/login/oauth/authorize?client_id=87b51f747b615b4ec5e0&redirect_uri=http://47.93.6.5:8081/authorize/redirect'
    },
    facelogin: function () {
      this.$router.push('/face')
    },
    toast (e) {
      let self = this
      self.toastText = e
      self.toastShow = true
      setTimeout(function () {
        self.toastShow = false
      }, 1500)
    }
  }
}
</script>

<style>
  @import "../assets/css/login.css";
  @import "../assets/css/tools.css";
  @import "../assets/layui/css/layui.css";
</style>
