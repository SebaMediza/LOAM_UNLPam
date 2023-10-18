import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/foundation.dart';

Future initFirebase() async {
  if (kIsWeb) {
    await Firebase.initializeApp(
        options: FirebaseOptions(
            apiKey: "AIzaSyDE8jAz1OGvVBh_NuEVLis698ZMhhWkjLE",
            authDomain: "fwitterv2.firebaseapp.com",
            projectId: "fwitterv2",
            storageBucket: "fwitterv2.appspot.com",
            messagingSenderId: "452099455055",
            appId: "1:452099455055:web:5c585f93c2fd98c9e6d23a"));
  } else {
    await Firebase.initializeApp();
  }
}
