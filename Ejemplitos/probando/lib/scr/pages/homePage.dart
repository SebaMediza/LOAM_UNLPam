// ignore_for_file: file_names

import 'package:flutter/material.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});
  // 1. Dentro del build se diseña nuestra aplicación. En este caso solo creará un Scaffold
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // 2. El scaffold tiene propiedades que ayudan a diseñar nuestra aplicación
      // Sus parametros del scaffold son los que nos permiten diseñar nuestra aplicación.
      // Queremos que nuestra aplicación tenga AppBar y además Flutter ya nos brinda un widget AppBar
      appBar: AppBar(
        title: const Text('Titulo'),
        centerTitle: true,
      ),
      // 3. La propiedad body es el cuerpo del Scaffold
      body: const Center(
        child: Text('Hola World'),
      ),
    );
  }
}
