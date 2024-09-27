package org.example

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File

class FileManager () {
    fun leerFichero (ficheroNotas: File): MutableList<MutableMap<String, Any?>>{
        val listaNotas = mutableListOf<MutableMap<String, Any?>>()

        ficheroNotas.forEachLine {line ->
            val linea = line.split(";").map { it.trim() }

            if (linea.size >= 9 ){
                val alumno: MutableMap<String, Any?> = mutableMapOf(
                    "Apellido" to linea.getOrNull(0),
                    "Nombre" to linea.getOrNull(1),
                    "Asistencia" to linea[2].replace("%", "").toDoubleOrNull(),
                    "parcial1" to linea.getOrNull(3)?.replace(",", ".")?.toDoubleOrNull(),
                    "parcial2" to linea.getOrNull(4)?.replace(",", ".")?.toDoubleOrNull(),
                    "ordinario1" to linea.getOrNull(5)?.replace(",", ".")?.toDoubleOrNull(),
                    "ordinario2" to linea.getOrNull(6)?.replace(",", ".")?.toDoubleOrNull(),
                    "practicas" to linea.getOrNull(7)?.replace(",", ".")?.toDoubleOrNull(),
                    "ordinarioPracticas" to linea.getOrNull(8)?.replace(",", ".")?.toDoubleOrNull()
                )

                listaNotas.add(alumno)
            }

        }

        return listaNotas

    }

    fun addNotaFinal (listaNotas: MutableList<MutableMap<String, Any?>>): MutableList<MutableMap<String, Any?>>{

        for (alumno in listaNotas){
            val nota1 = if((alumno["parcial1"] as? Double ?: 0.0) < 5 && alumno["ordinaria1"] != null) alumno["ordinaria1"] as? Double ?: 0.0 else alumno["parcial1"] as? Double ?: 0.0
            val nota2 = if((alumno["parcial2"] as? Double ?: 0.0) < 5 && alumno["ordinaria2"] != null) alumno["ordinaria2"] as? Double ?: 0.0 else alumno["parcial2"] as? Double ?: 0.0
            val practicas = if((alumno["practicas"] as? Double ?: 0.0) < 5 && alumno["ordinariaPracticas"] != null) alumno["ordinariaPracticas"] as? Double ?: 0.0 else alumno["practicas"] as? Double ?: 0.0

            val notaFinal = (nota1 * 0.3) + (nota2 * 0.3) + (practicas * 0.4)
            alumno["notaFinal"] = notaFinal
        }

        return listaNotas

    }

    fun aprobSusp (listaNotas: MutableList<MutableMap<String, Any?>>): Pair<MutableList<MutableMap<String, Any?>>, MutableList<MutableMap<String, Any?>>> {
        val aprobados = mutableListOf<MutableMap<String, Any?>>()
        val suspensos = mutableListOf<MutableMap<String, Any?>>()

        for (alumno in listaNotas){
            val nota1 = if((alumno["parcial1"] as? Double ?: 0.0) < 5 && alumno["ordinaria1"] != null) alumno["ordinaria1"] as? Double ?: 0.0 else alumno["parcial1"] as? Double ?: 0.0
            val nota2 = if((alumno["parcial2"] as? Double ?: 0.0) < 5 && alumno["ordinaria2"] != null) alumno["ordinaria2"] as? Double ?: 0.0 else alumno["parcial2"] as? Double ?: 0.0
            val practicas = if((alumno["practicas"] as? Double ?: 0.0) < 5 && alumno["ordinariaPracticas"] != null) alumno["ordinariaPracticas"] as? Double ?: 0.0 else alumno["practicas"] as? Double ?: 0.0
            val notaFinal = (nota1 * 0.3) + (nota2 * 0.3) + (practicas * 0.4)

            if ((alumno["Asistencia"] as? Double ?: 0.0) >= 75.0 && nota1 >= 4 && nota2 >= 4 && notaFinal >= 5 ){
                aprobados.add(alumno)
            }else{
                suspensos.add(alumno)
            }

        }

        return Pair(aprobados, suspensos)

    }
}