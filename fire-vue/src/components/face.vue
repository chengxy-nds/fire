<template>
  <div id="face_login" class="body-bg">
    <div id="app">
      <div v-show="showContainer">
        <video ref="refVideo" id="video" width="800" height="400" preload autoplay loop muted></video>
        <canvas ref="refCanvas" id="canvas" width="800" height="400"></canvas>
      </div>
      <h2 class="scanTip">{{scanTip}}</h2>
      <img v-show="!showContainer" :src="imgUrl" width="800" height="400" class="imgpre"/>
    </div>
  </div>
</template>

<script>

import 'tracking/build/tracking';
import '@/assets/js/face/face-min.js'
import axios from 'axios'

export default {
    name: 'face_login',
    data () {
      return {
        showContainer: true,   // 显示
        tracker: null,
        tipFlag: false,         // 提示用户已经检测到
        flag: false,            // 判断是否已经拍照
        context: null,          // canvas上下文
        removePhotoID: null,    // 停止转换图片
        scanTip: '人脸识别中...', // 提示文字
        imgUrl: '',              // base64格式图片
        canvas: null,
        video: null,
        streamIns: null        // 视频流
      }
    },
    mounted () {
      this.playVideo()
    },
    methods: {
      // 访问用户媒体设备
      getUserMedia(constrains, success, error) {
        if (navigator.mediaDevices.getUserMedia) {
          //最新标准API
          navigator.mediaDevices.getUserMedia(constrains).then(success).catch(error);
        } else if (navigator.webkitGetUserMedia) {
          //webkit内核浏览器
          navigator.webkitGetUserMedia(constrains).then(success).catch(error);
        } else if (navigator.mozGetUserMedia) {
          //Firefox浏览器
          navagator.mozGetUserMedia(constrains).then(success).catch(error);
        } else if (navigator.getUserMedia) {
          //旧版API
          navigator.getUserMedia(constrains).then(success).catch(error);
        } else {
          this.scanTip = "你的浏览器不支持访问用户媒体设备"
        }
      },
      success(stream) {
        this.streamIns = stream
        // webkit内核浏览器
        this.URL = window.URL || window.webkitURL
        if ("srcObject" in this.$refs.refVideo) {
          this.$refs.refVideo.srcObject = stream
        } else {
          this.$refs.refVideo.src = this.URL.createObjectURL(stream)
        }
        this.$refs.refVideo.onloadedmetadata = e => {
          this.$refs.refVideo.play()
        }
      },
      error(e) {
        this.scanTip = "访问用户媒体失败" + e.name + "," + e.message
      },

      playVideo () {

        this.getUserMedia({
          video: {
            width: 800, height: 400, facingMode: "user"
          }     /* 前置优先 */
        }, this.success, this.error)

        this.video = document.getElementById('video')
        this.canvas = document.getElementById('canvas')
        this.context = this.canvas.getContext('2d')
        this.tracker = new tracking.ObjectTracker('face')
        this.tracker.setInitialScale(4)
        this.tracker.setStepSize(2)
        this.tracker.setEdgesDensity(0.1)

        tracking.track('#video', this.tracker, {camera: true})

        this.tracker.on('track', this.handleTracked)
      },

      handleTracked (event) {
        this.context.clearRect(0, 0, this.canvas.width, this.canvas.height)
        if (event.data.length === 0) {
          this.scanTip = '未识别到人脸'
        } else {
          if (!this.tipFlag) {
            this.scanTip = '识别成功，正在拍照，请勿乱动~'
          }
          // 1秒后拍照，仅拍一次
          if (!this.flag) {
            this.scanTip = '拍照中...'
            this.flag = true
            this.removePhotoID = setTimeout(() => {
                this.tackPhoto()
                this.tipFlag = true
              },
              2000
            )
          }
          event.data.forEach(this.plot)
        }
      },

      plot (rect) {
        this.context.strokeStyle = '#eb652e'
        this.context.strokeRect(rect.x, rect.y, rect.width, rect.height)
        this.context.font = '11px Helvetica'
        this.context.fillStyle = '#fff'
        this.context.fillText('x: ' + rect.x + 'px', rect.x + rect.width + 5, rect.y + 11)
        this.context.fillText('y: ' + rect.y + 'px', rect.x + rect.width + 5, rect.y + 22)
      },

      // 拍照
      tackPhoto () {
        this.context.drawImage(this.$refs.refVideo, 0, 0, 800, 400)
        // 保存为base64格式
        this.imgUrl = this.saveAsPNG(this.$refs.refCanvas)
        var formData = new FormData()
        formData.append('file', this.imgUrl)
        this.scanTip = '登录中，请稍等~'

        alert('开始')
        axios.post(`http://47.93.6.5:8081/faceDiscern`, formData)
          .then(res => {
            alert(response.data.data)
            this.$router.push('/home')
          }).catch(function (error) {
          console.log(error)
        })

        this.close()
      },

      // 保存为png,base64格式图片
      saveAsPNG (c) {
        return c.toDataURL('image/png', 0.3)
      },

      // 关闭并清理资源
      close () {
        this.video.srcObject.getTracks()[0].stop()
        this.flag = false
        this.tipFlag = false
        this.showContainer = false
        this.tracker && this.tracker.removeListener('track', this.handleTracked) && tracking.track('#video', this.tracker, {camera: false})
        this.tracker = null
        this.context = null
        this.scanTip = ''
        clearTimeout(this.removePhotoID)
        if (this.streamIns) {
          this.streamIns.enabled = false
          this.streamIns.getTracks()[0].stop()
          this.streamIns.getVideoTracks()[0].stop()
        }
        this.streamIns = null
      }
    }
  }
</script>

<style>
  @import "../assets/css/face.css";

  video, canvas {
    margin: 200px auto;
    position: absolute;
    left: 0;
    right: 0;
  }

  .scanTip {
    text-align: center;
    color: white;
    margin: 0px auto;
    font-size: 18px;
  }

</style>
