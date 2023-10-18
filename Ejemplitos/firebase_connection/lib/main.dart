// ignore_for_file: use_key_in_widget_constructors, library_private_types_in_public_api, prefer_const_constructors_in_immutables

import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'firebase_options.dart';
Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(options: DefaultFirebaseOptions.currentPlatform);
  runApp(FirestoreExampleApp());
}
final fwittersRef =
    FirebaseFirestore.instance.collection('fwitters').withConverter<Fwitter>(
          fromFirestore: (snapshots, _) => Fwitter.fromJson(snapshots.data()!),
          toFirestore: (fwitter, _) => fwitter.toJson(),
        );
class FirestoreExampleApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Firestore Example App',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const Scaffold(
        body: Center(child: FlutterList()),
      ),
    );
  }
}
class FlutterList extends StatefulWidget {
  const FlutterList({Key? key}) : super(key: key);
  @override
  _FlutterListState createState() => _FlutterListState();
}
class _FlutterListState extends State<FlutterList> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            const Text('Firestore Example: Fwitter'),
            StreamBuilder(
              stream: FirebaseFirestore.instance.snapshotsInSync(),
              builder: (context, _) {
                return Text(
                  'Latest Snapshot: ${DateTime.now()}',
                  style: Theme.of(context).textTheme.bodySmall,
                );
              },
            ),
          ],
        ),
      ),
      body: StreamBuilder<QuerySnapshot<Fwitter>>(
        // stream: fwittersRef.queryBy(query).snapshots(),
        stream: fwittersRef.snapshots(),
        builder: (context, snapshot) {
          if (snapshot.hasError) {
            return Center(
              child: Text(snapshot.error.toString()),
            );
          }
          if (!snapshot.hasData) {
            return const Center(child: CircularProgressIndicator());
          }
          final data = snapshot.requireData;
          return ListView.builder(
            itemCount: data.size,
            itemBuilder: (context, index) {
              return _FwitterItem(
                data.docs[index].data(),
                data.docs[index].reference,
              );
            },
          );
        },
      ),
    );
  }
}
class _FwitterItem extends StatelessWidget {
  _FwitterItem(this.fwitter, this.reference);
  final Fwitter fwitter;
  final DocumentReference<Fwitter> reference;
  Widget get datePost {
    return Text(
      fwitter.date,
      style: const TextStyle(fontSize: 14),
    );
  }
  Widget get details {
    return Padding(
      padding: const EdgeInsets.only(left: 8, right: 8),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [title, likes],
      ),
    );
  }
  Widget get title {
    return Text(
      fwitter.post,
      style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
    );
  }
  Widget get likes {
    return Text(
      fwitter.likes.toString(),
      style: const TextStyle(fontSize: 15, color: Colors.red),
    );
  }
  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 4, top: 4),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          datePost,
          Flexible(child: details),
        ],
      ),
    );
  }
}
@immutable
class Fwitter {
  Fwitter({
    required this.likes,
    required this.post,
    required this.date,
  });
  Fwitter.fromJson(Map<String, Object?> json)
      : this(
          likes: json['likes']! as int,
          post: json['post']! as String,
          date: json['date']! as String,
        );
  final String post;
  final int likes;
  final String date;
  Map<String, Object?> toJson() {
    return {
      'likes': likes,
      'post': post,
      'date': date,
    };
  }
}