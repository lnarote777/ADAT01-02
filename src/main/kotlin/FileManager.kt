package org.example

import java.io.File
import kotlin.math.round

class FileManager () {
    fun leerFichero (ficheroNotas: File): List<MutableMap<String, String?>> {
        val listaNotas = mutableListOf<MutableMap<String,String?>>()

        ficheroNotas.forEachLine {line ->
            val linea = line.split(";")
            val alumno: MutableMap<String, String?> = mutableMapOf()

            if (linea.size >= 9 ){

                alumno["Apellido"] = linea.getOrNull(0)
                alumno["Nombre"] = linea.getOrNull(1)
                alumno["Asistencia"] = linea.getOrNull(2)?.replace("%", "")
                alumno["parcial1"] = if (linea.getOrNull(3) == "") null else linea.getOrNull(3)?.replace(",", ".")
                alumno["parcial2"] = if (linea.getOrNull(4) == "") null else linea.getOrNull(4)?.replace(",", ".")
                alumno["ordinaria1"] = if (linea.getOrNull(5) == "") null else linea.getOrNull(5)?.replace(",", ".")
                alumno["ordinaria2"] =if (linea.getOrNull(6)== "") null else linea.getOrNull(6)?.replace(",", ".")
                alumno["practicas"] = if (linea.getOrNull(7) == "") null else linea.getOrNull(7)?.replace(",", ".")
                alumno["ordinariaPracticas"] = if (linea.getOrNull(8) == "") null else linea.getOrNull(8)?.replace(",", ".")
                if (alumno["Apellido"] != "Apellidos") listaNotas.add(alumno)
            }


        }

        return listaNotas.sortedBy { it["Apellido"] }

    }

    fun addNotaFinal (listaNotas: List<MutableMap<String, String?>>): List<MutableMap<String, String?>>{

        for (alumno in listaNotas){
            val notaFinal = calcularNotaFinal(alumno)
            alumno["notaFinal"] = notaFinal.toString()
        }

        return listaNotas

    }

    fun aprobSusp (listaNotas: List<MutableMap<String, String?>>): Pair<MutableList<MutableMap<String, String?>>, MutableList<MutableMap<String, String?>>> {
        val aprobados = mutableListOf<MutableMap<String, String?>>()
        val suspensos = mutableListOf<MutableMap<String, String?>>()

        for (alumno in listaNotas){
            val nota1 = if((alumno["parcial1"]?.toDouble()?: 0.0) < 5 && alumno["ordinaria1"] != null) alumno["ordinaria1"]?.toDouble()?: 0.0 else alumno["parcial1"]?.toDouble()?: 0.0
            val nota2 = if((alumno["parcial2"]?.toDouble()?: 0.0) < 5 && alumno["ordinaria2"] != null) alumno["ordinaria2"]?.toDouble()?: 0.0 else alumno["parcial2"]?.toDouble()?: 0.0

            val notaFinal = calcularNotaFinal(alumno)

            if ((alumno["Asistencia"]?.toDouble()?: 0.0) >= 75 && nota1 >= 4 && nota2 >= 4 && notaFinal >= 5 ){
                aprobados.add(alumno)
            }else{
                suspensos.add(alumno)
            }

        }

        return Pair(aprobados, suspensos)

    }

    fun calcularNotaFinal(alumno: Map<String, String?>): Int {
        val nota1 = if((alumno["parcial1"]?.toDouble()?: 0.0) < 5 && alumno["ordinaria1"] != null) alumno["ordinaria1"]?.toDouble()?: 0.0 else alumno["parcial1"]?.toDouble()?: 0.0
        val nota2 = if((alumno["parcial2"]?.toDouble()?: 0.0) < 5 && alumno["ordinaria2"] != null) alumno["ordinaria2"]?.toDouble()?: 0.0 else alumno["parcial2"]?.toDouble()?: 0.0
        val practicas = if((alumno["practicas"]?.toDouble()?: 0.0) < 5 && alumno["ordinariaPracticas"] != null) alumno["ordinariaPracticas"]?.toDouble()?: 0.0 else alumno["practicas"]?.toDouble()?: 0.0

        val notaFinal = (nota1 * 0.3) + (nota2 * 0.3) + (practicas * 0.4)

        return notaFinal.toInt()
    }
}