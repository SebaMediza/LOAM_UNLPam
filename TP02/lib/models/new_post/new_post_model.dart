//import '/auth/firebase_auth/auth_util.dart';
//import '/backend/backend.dart';
//import '/backend/firebase_storage/storage.dart';
//import '/flutter_flow/flutter_flow_theme.dart';
import '/flutter_flow/flutter_flow_util.dart';
//import '/flutter_flow/flutter_flow_widgets.dart';
//import '/flutter_flow/upload_data.dart';
import 'new_post_widget.dart' show NewPostWidget;
//import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
//import 'package:flutter/services.dart';
//import 'package:google_fonts/google_fonts.dart';
//import 'package:provider/provider.dart';

class NewPostModel extends FlutterFlowModel<NewPostWidget> {
  ///  State fields for stateful widgets in this page.

  final unfocusNode = FocusNode();
  final formKey = GlobalKey<FormState>();
  // State field(s) for titulo widget.
  TextEditingController? tituloController;
  String? Function(BuildContext, String?)? tituloControllerValidator;
  // State field(s) for cuerpo widget.
  TextEditingController? cuerpoController;
  String? Function(BuildContext, String?)? cuerpoControllerValidator;
  bool isDataUploading1 = false;
  FFUploadedFile uploadedLocalFile1 =
      FFUploadedFile(bytes: Uint8List.fromList([]));

  bool isDataUploading2 = false;
  FFUploadedFile uploadedLocalFile2 =
      FFUploadedFile(bytes: Uint8List.fromList([]));
  String uploadedFileUrl2 = '';

  /// Initialization and disposal methods.

  void initState(BuildContext context) {}

  void dispose() {
    unfocusNode.dispose();
    tituloController?.dispose();
    cuerpoController?.dispose();
  }

  /// Action blocks are added here.

  /// Additional helper methods are added here.
}
