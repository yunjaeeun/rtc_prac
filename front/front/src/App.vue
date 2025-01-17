<template>
  <div>
    <h1>WebRTC Video Chat</h1>
    <div class="videos">
      <video ref="localVideo" autoplay playsinline></video>
      <video ref="remoteVideo" autoplay playsinline></video>
    </div>
    <button @click="startCall">Start Call</button>
  </div>
</template>

<script>
import { Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";

export default {
  data() {
    return {
      stompClient: null,
      localStream: null,
      peerConnection: null,
      signalingServerUrl: "http://localhost:8080/ws",
      configuration: {
        iceServers: [
          { urls: "stun:stun.l.google.com:19302" }, // Google STUN server
        ],
      },
    };
  },
  methods: {
    async startCall() {
      // Initialize local video
      this.localStream = await navigator.mediaDevices.getUserMedia({
        video: true,
        audio: true,
      });
      this.$refs.localVideo.srcObject = this.localStream;

      // Initialize WebRTC connection
      this.peerConnection = new RTCPeerConnection(this.configuration);

      // Add local stream tracks to the connection
      this.localStream.getTracks().forEach((track) => {
        this.peerConnection.addTrack(track, this.localStream);
      });

      // Handle incoming ICE candidates
      this.peerConnection.onicecandidate = (event) => {
        if (event.candidate) {
          this.sendSignal(JSON.stringify({ candidate: event.candidate }));
        }
      };

      // Handle incoming remote stream
      this.peerConnection.ontrack = (event) => {
        this.$refs.remoteVideo.srcObject = event.streams[0];
      };

      // Connect to the signaling server
      this.connectToSignalingServer();
    },
    connectToSignalingServer() {
      const socket = new SockJS(this.signalingServerUrl);
      this.stompClient = Stomp.over(socket);

      this.stompClient.connect({}, () => {
        this.stompClient.subscribe("/topic/signaling", (message) => {
          const data = JSON.parse(message.body);
          this.handleSignal(data);
        });

        // Create an offer to start the call
        this.createOffer();
      });
    },
    async createOffer() {
      const offer = await this.peerConnection.createOffer();
      await this.peerConnection.setLocalDescription(offer);
      this.sendSignal(JSON.stringify({ offer }));
    },
    async handleSignal(data) {
      if (data.offer) {
        await this.peerConnection.setRemoteDescription(
          new RTCSessionDescription(data.offer)
        );
        const answer = await this.peerConnection.createAnswer();
        await this.peerConnection.setLocalDescription(answer);
        this.sendSignal(JSON.stringify({ answer }));
      } else if (data.answer) {
        await this.peerConnection.setRemoteDescription(
          new RTCSessionDescription(data.answer)
        );
      } else if (data.candidate) {
        await this.peerConnection.addIceCandidate(new RTCIceCandidate(data.candidate));
      }
    },
    sendSignal(message) {
      this.stompClient.send("/app/signal", {}, message);
    },
  },
};
</script>

<style>
.videos {
  display: flex;
  gap: 10px;
}
video {
  width: 45%;
  border: 1px solid #ccc;
  border-radius: 8px;
}
</style>
