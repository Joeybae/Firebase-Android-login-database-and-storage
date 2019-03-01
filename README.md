Firebase Example
-----------------
Google Login, Database, Storage

Version
--------
1. Android studio build:gradle:3.3.1
2. firebase-core:16.0.7; firebase-auth:16.1.0'; firebase-database:16.0.6'; firebase-storage:16.0.5'

Before Start
------------
1. Sign In Firebase (https://firebase.google.com/)
2. Make New Project and add package name (You can find app/manifests/AndroidManifest.xml/package="com.example.rhkdg.sharethetrip")
3. Download 'google-services.json' and move to FirebaseLogin-DatabaseExample/app/)
4. Sync firebase json file (Android Studio Tools -> Firebase -> Authentication -> Email and password authentication -> Click 'Connect to Firebase' -> Click 'Sync'
5. Go to your project in Firebase -> Click 'authentication' -> Click 'providers' and activate Google login
6. Go to database(realtime database) -> Click 'rules' -> Change like this (".read": "true", ".write": "true") -> Save
7. Go to storage -> Click 'rules' -> Change like this (allow read, write: if request.auth != null;) -> Save
8. tip) When you upload post in this app, You should select image if not upload image it will make error!

Contact Information
--------------------
If you guys have a question Contact me to rhkdgks48@gmail.com

Screenshots
-----------

<div>
  <img width="170" src="https://user-images.githubusercontent.com/45925992/53642651-a0e96000-3c75-11e9-88b1-aa338002661a.jpg">
  <img width="170" src="https://user-images.githubusercontent.com/45925992/53642666-a646aa80-3c75-11e9-9132-ab116e2dd568.png">
  <img width="170" src="https://user-images.githubusercontent.com/45925992/53642674-aa72c800-3c75-11e9-9b5e-13942e71420b.png">
  <img width="170" src="https://user-images.githubusercontent.com/45925992/53642691-ae064f00-3c75-11e9-95bf-c0e4612634a8.png">
  <img width="170" src="https://user-images.githubusercontent.com/45925992/53642709-b068a900-3c75-11e9-8cf0-cf4be39590b9.png">
</div>


