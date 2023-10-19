// ignore_for_file: unnecessary_import

import 'dart:async';

import 'package:collection/collection.dart';

import '/backend/schema/util/firestore_util.dart';
import '/backend/schema/util/schema_util.dart';

import 'index.dart';
import '/flutter_flow/flutter_flow_util.dart';

class FwittsRecord extends FirestoreRecord {
  FwittsRecord._(
    DocumentReference reference,
    Map<String, dynamic> data,
  ) : super(reference, data) {
    _initializeFields();
  }

  // "post_photo" field.
  String? _postPhoto;
  String get postPhoto => _postPhoto ?? '';
  bool hasPostPhoto() => _postPhoto != null;

  // "post_title" field.
  String? _postTitle;
  String get postTitle => _postTitle ?? '';
  bool hasPostTitle() => _postTitle != null;

  // "post_description" field.
  String? _postDescription;
  String get postDescription => _postDescription ?? '';
  bool hasPostDescription() => _postDescription != null;

  // "time_posted" field.
  DateTime? _timePosted;
  DateTime? get timePosted => _timePosted;
  bool hasTimePosted() => _timePosted != null;

  // "num_comments" field.
  int? _numComments;
  int get numComments => _numComments ?? 0;
  bool hasNumComments() => _numComments != null;

  // "likes" field.
  int? _likes;
  int get likes => _likes ?? 0;
  bool hasLikes() => _likes != null;

  // "post_user" field.
  String? _postUser;
  String get postUser => _postUser ?? '';
  bool hasPostUser() => _postUser != null;

  // "num_votes" field.
  double? _numVotes;
  double get numVotes => _numVotes ?? 0.0;
  bool hasNumVotes() => _numVotes != null;

  void _initializeFields() {
    _postPhoto = snapshotData['post_photo'] as String?;
    _postTitle = snapshotData['post_title'] as String?;
    _postDescription = snapshotData['post_description'] as String?;
    _timePosted = snapshotData['time_posted'] as DateTime?;
    _numComments = castToType<int>(snapshotData['num_comments']);
    _likes = castToType<int>(snapshotData['likes']);
    _postUser = snapshotData['post_user'] as String?;
    _numVotes = castToType<double>(snapshotData['num_votes']);
  }

  static CollectionReference get collection =>
      FirebaseFirestore.instance.collection('fwitts');

  static Stream<FwittsRecord> getDocument(DocumentReference ref) =>
      ref.snapshots().map((s) => FwittsRecord.fromSnapshot(s));

  static Future<FwittsRecord> getDocumentOnce(DocumentReference ref) =>
      ref.get().then((s) => FwittsRecord.fromSnapshot(s));

  static FwittsRecord fromSnapshot(DocumentSnapshot snapshot) => FwittsRecord._(
        snapshot.reference,
        mapFromFirestore(snapshot.data() as Map<String, dynamic>),
      );

  static FwittsRecord getDocumentFromData(
    Map<String, dynamic> data,
    DocumentReference reference,
  ) =>
      FwittsRecord._(reference, mapFromFirestore(data));

  @override
  String toString() =>
      'FwittsRecord(reference: ${reference.path}, data: $snapshotData)';

  @override
  int get hashCode => reference.path.hashCode;

  @override
  bool operator ==(other) =>
      other is FwittsRecord &&
      reference.path.hashCode == other.reference.path.hashCode;
}

Map<String, dynamic> createFwittsRecordData({
  String? postPhoto,
  String? postTitle,
  String? postDescription,
  DateTime? timePosted,
  int? numComments,
  int? likes,
  String? postUser,
  double? numVotes,
}) {
  final firestoreData = mapToFirestore(
    <String, dynamic>{
      'post_photo': postPhoto,
      'post_title': postTitle,
      'post_description': postDescription,
      'time_posted': timePosted,
      'num_comments': numComments,
      'likes': likes,
      'post_user': postUser,
      'num_votes': numVotes,
    }.withoutNulls,
  );

  return firestoreData;
}

class FwittsRecordDocumentEquality implements Equality<FwittsRecord> {
  const FwittsRecordDocumentEquality();

  @override
  bool equals(FwittsRecord? e1, FwittsRecord? e2) {
    return e1?.postPhoto == e2?.postPhoto &&
        e1?.postTitle == e2?.postTitle &&
        e1?.postDescription == e2?.postDescription &&
        e1?.timePosted == e2?.timePosted &&
        e1?.numComments == e2?.numComments &&
        e1?.likes == e2?.likes &&
        e1?.postUser == e2?.postUser &&
        e1?.numVotes == e2?.numVotes;
  }

  @override
  int hash(FwittsRecord? e) => const ListEquality().hash([
        e?.postPhoto,
        e?.postTitle,
        e?.postDescription,
        e?.timePosted,
        e?.numComments,
        e?.likes,
        e?.postUser,
        e?.numVotes
      ]);

  @override
  bool isValidKey(Object? o) => o is FwittsRecord;
}
