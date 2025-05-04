package utils;

import models.*;
import services.*;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class LeerCSV {

    public static void cargarCultivosDesdeCSV(String archivoCSV, GestorCultivos gestorCultivos, GestorParcelas gestorParcelas) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                String[] partes = linea.split(",", 8);
                if (partes.length < 8) continue;

                String nombre = partes[1].replaceAll("\"", "");
                String variedad = partes[2].replaceAll("\"", "");
                double superficie = Double.parseDouble(partes[3].replaceAll("\"", ""));
                String codigoParcela = partes[4].replaceAll("\"", "");
                LocalDate fechaSiembra = LocalDate.parse(partes[5].replaceAll("\"", ""));
                String estado = partes[6].replaceAll("\"", "");
                String actividadesRaw = partes[7].trim();

                // Buscar o crear parcela
                Parcela parcela = gestorParcelas.buscarPorCodigo(codigoParcela).orElse(null);
                if (parcela == null) {
                    parcela = new Parcela(codigoParcela, 0.0, "Desconocida");
                    gestorParcelas.agregarParcela(parcela);
                }

                Cultivo cultivo = new Cultivo(nombre, variedad, superficie, parcela, fechaSiembra, estado);

                // Procesar actividades
                actividadesRaw = actividadesRaw.replaceAll("[\\[\\]\"]", "");
                if (!actividadesRaw.isEmpty()) {
                    String[] actTokens = actividadesRaw.split(",");
                    for (String token : actTokens) {
                        String[] tipoFecha = token.trim().split(":");
                        if (tipoFecha.length >= 2) {
                            String tipo = tipoFecha[0].trim();
                            LocalDate fecha = LocalDate.parse(tipoFecha[1].trim());
                            Actividad act = new Actividad(tipo, fecha);
                            if (tipoFecha.length == 3 && tipoFecha[2].trim().equalsIgnoreCase("Completada")) {
                                act.marcarComoCompletada();
                            }
                            cultivo.agregarActividad(act);
                        }                                            
                    }
                }

                gestorCultivos.agregarCultivo(cultivo);
                parcela.agregarCultivo(cultivo);
            }
        } catch (Exception e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public static void guardarCultivosEnCSV(String archivoCSV, List<Cultivo> cultivos) {
        try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(archivoCSV))) {
            for (Cultivo c : cultivos) {
                writer.write(c.toCSV());
                writer.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }
}
    