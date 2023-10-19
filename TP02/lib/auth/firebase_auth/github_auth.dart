// ignore_for_file: unused_import, unnecessary_import, unused_field
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/foundation.dart';

// https://firebase.flutter.dev/docs/auth/social/#github
Future<UserCredential?> githubSignInFunc() async {
  // Create a new provider
  GithubAuthProvider githubProvider = GithubAuthProvider();

  // Once signed in, return the UserCredential
  return await FirebaseAuth.instance.signInWithPopup(githubProvider);
}
