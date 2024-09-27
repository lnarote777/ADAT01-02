package org.example


import java.io.File


fun main() {

    val ficheroNotas = File("src/main/resources/calificaciones.csv")

    val fileManager = FileManager()
    val notas = fileManager.leerFichero(ficheroNotas)
    //lista de diccionarios sin las notas finales
    println("------LISTADO DE CALIFICACIONES-----------------------------------------")
    notas.forEach { println(it) }
    println()

    fileManager.addNotaFinal(notas)
    //lista de diccionarios con las notas finales
    println("-------LISTADO CON NOTAS FINALES----------------------------------------")
    notas.forEach { println(it) }
    println()

    val (aprobados, suspensos) = fileManager.aprobSusp(notas)

    println("-------ALUMNOS APROBADOS----------------------------------------")
    aprobados.forEach { println(it) }
    println()
    println("-------ALUMNOS SUSPENSOS----------------------------------------")
    suspensos.forEach { println(it) }
}