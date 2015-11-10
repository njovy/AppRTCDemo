# AppRTCDemo

Android Studio project for AppRTCDemo of WebRTC project. The revision number of this build is 10532.
https://chromium.googlesource.com/external/webrtc/+/b7a5c16d2c6dbe5ca17fca86a3180b8aad5054f7

https://computeengineondemand.appspot.com/turn doesn't return a valid TURN response anymore so I changed [RoomParametersFetcher](app/src/main/java/org/appspot/apprtc/RoomParametersFetcher.java) to use Google's public STUN server to test it.
STUN server doesn't always work due to a network environment. If this is a case you can provide your own ICE server.
