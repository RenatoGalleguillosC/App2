import models.*;
import services.*;
import utils.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class App2 {
    public static void main(String[] args) {

        String archivoCSV = args[0];

        // Gestores
        GestorCultivos gestorCultivos = new GestorCultivos();
        GestorParcelas gestorParcelas = new GestorParcelas();
        GestorActividades gestorActividades = new GestorActividades();

        // Cargar CSV
        LeerCSV.cargarCultivosDesdeCSV(archivoCSV, gestorCultivos, gestorParcelas);

        // Menú
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("==== MENÚ PRINCIPAL =====");
            System.out.println("1. Lista de cultivos");
            System.out.println("2. Buscar cultivo por nombre");
            System.out.println("3. Crear nuevo cultivo");
            System.out.println("4. Registrar actividad a cultivo");
            System.out.println("5. Ver actividades de un cultivo");
            System.out.println("6. Eliminar cultivo (solo si no tiene actividades)");
            System.out.println("7. Editar cultivo (nombre, variedad, superficie)");
            System.out.println("8. Buscar cultivos por variedad");
            System.out.println("9. Ver cultivos por estado");
            System.out.println("10. Marcar actividad como completada");
            System.out.println("11. Eliminar actividad de un cultivo");
            System.out.println("12. Listar parcelas con sus cultivos");
            System.out.println("13. Crear nueva parcela");
            System.out.println("14. Editar una parcela");
            System.out.println("15. Eliminar parcela (solo si no tiene cultivos)");
            System.out.println("16. Reasignar cultivo a otra parcela");
            System.out.println("17. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1: // Cultivos lista
                    List<Cultivo> cultivos = gestorCultivos.getCultivos();
                    if (cultivos.isEmpty()) {
                        System.out.println("No hay cultivos cargados.");
                    } else {
                        for (Cultivo c : cultivos) {
                            System.out.println("- " + c);
                        }
                    }
                    break;
                case 2: // Buscar por nombre
                    System.out.print("Ingresa nombre del cultivo: ");
                    String nombre = sc.nextLine();
                    List<Cultivo> resultados = gestorCultivos.buscarPorNombre(nombre);
                    if (resultados.isEmpty()) {
                        System.out.println("No se encontró ningún cultivo con ese nombre");
                    } else {
                        resultados.forEach(System.out::println);
                    }
                    break;
                case 3: // Crea cultivo
                    System.out.print("Nombre del cultivo: ");
                    String nuevoNombre = sc.nextLine();

                    System.out.print("Variedad: ");
                    String nuevaVariedad = sc.nextLine();

                    System.out.print("Superficie (en hectáreas): ");
                    double nuevaSuperficie = sc.nextDouble();
                    sc.nextLine();

                    System.out.print("Código de parcela: ");
                    String codigoParcela = sc.nextLine();

                    System.out.print("Fecha de siembra (YYYY-MM-DD): ");
                    String fechaTexto = sc.nextLine();

                    System.out.print("Estado (ACTIVO / EN_RIESGO / COSECHADO): ");
                    String estado = sc.nextLine();

                    // Parcela
                    Parcela parcela = gestorParcelas.buscarPorCodigo(codigoParcela).orElse(null);
                    if (parcela == null) {
                        parcela = new Parcela(codigoParcela, 0.0, "Sin ubicación");
                        gestorParcelas.agregarParcela(parcela);
                    }

                    // Crear cultivo y registrar
                    try {
                        LocalDate fechaSiembra = LocalDate.parse(fechaTexto);
                        Cultivo nuevoCultivo = new Cultivo(nuevoNombre, nuevaVariedad, nuevaSuperficie, parcela,
                                fechaSiembra, estado);
                        gestorCultivos.agregarCultivo(nuevoCultivo);
                        System.out.println("DEBUG >> Total cultivos en gestor: " + gestorCultivos.getCultivos().size());
                        System.out.println("DEBUG >> Último cultivo: "
                                + gestorCultivos.getCultivos().get(gestorCultivos.getCultivos().size() - 1));

                        parcela.agregarCultivo(nuevoCultivo);

                        System.out.println("Cultivo creado correctamente.");
                    } catch (Exception e) {
                        System.out.println("Error: formato de fecha inválido.");
                    }

                    break;

                case 4:
                    System.out.print("Nombre del cultivo: ");
                    String cultivoNombre = sc.nextLine();

                    Cultivo cultivo = gestorCultivos.buscarPorNombreExacto(cultivoNombre).orElse(null);
                    if (cultivo == null) {
                        System.out.println("No se encontró un cultivo con ese nombre exacto.");
                        break;
                    }

                    System.out.print("Tipo de actividad (ej. RIEGO, COSECHA): ");
                    String tipoActividad = sc.nextLine();

                    System.out.print("Fecha de la actividad (YYYY-MM-DD): ");
                    String fechaActividadTexto = sc.nextLine();

                    try {
                        LocalDate fechaActividad = LocalDate.parse(fechaActividadTexto);
                        gestorActividades.registrarActividad(cultivo, tipoActividad, fechaActividad);
                        System.out.println("Actividad registrada correctamente.");
                    } catch (Exception e) {
                        System.out.println("Error: formato de fecha inválido.");
                    }
                    break;
                case 5:
                    System.out.print("Nombre exacto del cultivo: ");
                    String nombreCultivoAct = sc.nextLine();

                    Cultivo cultivoAct = gestorCultivos.buscarPorNombreExacto(nombreCultivoAct).orElse(null);
                    if (cultivoAct == null) {
                        System.out.println("No se encontró ese cultivo.");
                        break;
                    }

                    List<Actividad> actividades = cultivoAct.getActividades();
                    if (actividades.isEmpty()) {
                        System.out.println("Este cultivo no tiene actividades registradas.");
                    } else {
                        System.out.println("Actividades para " + cultivoAct.getNombre() + ":");
                        for (Actividad act : actividades) {
                            System.out.println("- " + act.toCSV());
                        }
                    }
                    break;
                case 6:
                    System.out.print("Nombre exacto del cultivo a eliminar: ");
                    String cultivoAEliminar = sc.nextLine();

                    Cultivo cultivoEliminar = gestorCultivos.buscarPorNombreExacto(cultivoAEliminar).orElse(null);
                    if (cultivoEliminar == null) {
                        System.out.println("No se encontró ese cultivo.");
                        break;
                    }

                    if (!cultivoEliminar.getActividades().isEmpty()) {
                        System.out.println("No se puede eliminar: el cultivo tiene actividades registradas.");
                        break;
                    }

                    gestorCultivos.eliminarCultivo(cultivoEliminar);
                    cultivoEliminar.getParcela().eliminarCultivo(cultivoEliminar);
                    System.out.println("Cultivo eliminado correctamente.");
                    break;
                case 7:
                    System.out.print("Nombre exacto del cultivo a editar: ");
                    String nombreCultivoEditar = sc.nextLine();

                    Cultivo cultivoEditar = gestorCultivos.buscarPorNombreExacto(nombreCultivoEditar).orElse(null);
                    if (cultivoEditar == null) {
                        System.out.println("No se encontró ese cultivo.");
                        break;
                    }

                    System.out.print("Nuevo nombre (ENTER para dejar igual): ");
                    String nombreEditado = sc.nextLine();
                    if (!nombreEditado.isBlank()) {
                        cultivoEditar.setNombre(nombreEditado);
                    }

                    System.out.print("Nueva variedad (ENTER para dejar igual): ");
                    String variedadEditada = sc.nextLine();
                    if (!variedadEditada.isBlank()) {
                        cultivoEditar.setVariedad(variedadEditada);
                    }

                    System.out.print("Nueva superficie (ENTER para dejar igual): ");
                    String superficieEditada = sc.nextLine();
                    if (!superficieEditada.isBlank()) {
                        try {
                            double nuevaSup = Double.parseDouble(superficieEditada);
                            cultivoEditar.setSuperficie(nuevaSup);
                        } catch (NumberFormatException e) {
                            System.out.println("Superficie inválida. No se modificó.");
                        }
                    }
                    System.out.println("Cultivo actualizado.");
                    break;
                case 8:
                    System.out.print("Ingresa la variedad a buscar: ");
                    String variedad = sc.nextLine();

                    List<Cultivo> resultadosVariedad = gestorCultivos.buscarPorVariedad(variedad);
                    if (resultadosVariedad.isEmpty()) {
                        System.out.println("No se encontraron cultivos con esa variedad.");
                    } else {
                        System.out.println("Cultivos encontrados:");
                        for (Cultivo c : resultadosVariedad) {
                            System.out.println("- " + c);
                        }
                    }
                    break;
                case 9:
                    System.out.print("Estado a filtrar (ACTIVO, EN_RIESGO, COSECHADO): ");
                    String estadoFiltro = sc.nextLine().toUpperCase();

                    List<Cultivo> porEstado = gestorCultivos.cultivosPorEstado(estadoFiltro);
                    if (porEstado.isEmpty()) {
                        System.out.println("No hay cultivos con estado: " + estadoFiltro);
                    } else {
                        System.out.println("Cultivos con estado " + estadoFiltro + ":");
                        porEstado.forEach(System.out::println);
                    }
                    break;
                case 10:
                    System.out.print("Nombre exacto del cultivo: ");
                    String cultivoNombreComp = sc.nextLine();

                    Cultivo cultivoComp = gestorCultivos.buscarPorNombreExacto(cultivoNombreComp).orElse(null);
                    if (cultivoComp == null) {
                        System.out.println("No se encontró ese cultivo.");
                        break;
                    }

                    System.out.print("Tipo de actividad a marcar (ej. RIEGO): ");
                    String tipoActComp = sc.nextLine();

                    boolean encontrada = false;
                    for (Actividad a : cultivoComp.getActividades()) {
                        if (a.getTipo().equalsIgnoreCase(tipoActComp)) {
                            a.marcarComoCompletada();
                            System.out.println("Actividad marcada como completada.");
                            encontrada = true;
                            break;
                        }
                    }

                    if (!encontrada) {
                        System.out.println("Actividad no encontrada.");
                    }
                    break;
                case 11:
                    System.out.print("Nombre exacto del cultivo: ");
                    String cultivoNombreActDel = sc.nextLine();

                    Cultivo cultivoActDel = gestorCultivos.buscarPorNombreExacto(cultivoNombreActDel).orElse(null);
                    if (cultivoActDel == null) {
                        System.out.println("No se encontró ese cultivo.");
                        break;
                    }

                    List<Actividad> actividadesDel = cultivoActDel.getActividades();
                    if (actividadesDel.isEmpty()) {
                        System.out.println("Ese cultivo no tiene actividades.");
                        break;
                    }

                    System.out.println("Actividades disponibles:");
                    for (int i = 0; i < actividadesDel.size(); i++) {
                        System.out.println((i + 1) + ". " + actividadesDel.get(i));
                    }

                    System.out.print("Ingresa el número de la actividad a eliminar: ");
                    int indexEliminar = sc.nextInt();
                    sc.nextLine();

                    if (indexEliminar < 1 || indexEliminar > actividadesDel.size()) {
                        System.out.println("Número inválido.");
                        break;
                    }

                    Actividad eliminada = actividadesDel.get(indexEliminar - 1);
                    cultivoActDel.eliminarActividad(eliminada);
                    System.out.println("Actividad eliminada: " + eliminada.getTipo());
                    break;
                case 12:
                    List<Parcela> parcelas = gestorParcelas.getParcelas();
                    if (parcelas.isEmpty()) {
                        System.out.println("No hay parcelas registradas.");
                        break;
                    }

                    for (Parcela p : parcelas) {
                        System.out.println("Parcela " + p.getCodigo() + " (" + p.getUbicacion() + ", "
                                + p.getTamano() + " [ha])"); // Medida hectareas [ha]

                        List<Cultivo> cultivosEnParcela = p.getCultivos();
                        if (cultivosEnParcela.isEmpty()) {
                            System.out.println("   - Sin cultivos.");
                        } else {
                            for (Cultivo c : cultivosEnParcela) {
                                System.out.println("   - " + c);
                            }
                        }
                    }
                    break;
                case 13:
                    System.out.print("Código de la parcela: ");
                    String codigoParcelaNueva = sc.nextLine();

                    if (gestorParcelas.buscarPorCodigo(codigoParcelaNueva).isPresent()) {
                        System.out.println("Ya existe una parcela con ese código.");
                        break;
                    }

                    System.out.print("Tamaño (en hectáreas): ");
                    double tamanoParcela = sc.nextDouble();
                    sc.nextLine();

                    System.out.print("Ubicación: ");
                    String ubicacionParcela = sc.nextLine();

                    Parcela nuevaParcela = new Parcela(codigoParcelaNueva, tamanoParcela, ubicacionParcela);
                    gestorParcelas.agregarParcela(nuevaParcela);

                    System.out.println("Parcela creada correctamente.");
                    break;
                case 14:
                    System.out.print("Código de la parcela a editar: ");
                    String codigoEditar = sc.nextLine();

                    Parcela parcelaEditar = gestorParcelas.buscarPorCodigo(codigoEditar).orElse(null);
                    if (parcelaEditar == null) {
                        System.out.println("No se encontró la parcela.");
                        break;
                    }

                    System.out.print("Nuevo tamaño (ENTER para dejar igual): ");
                    String inputTamano = sc.nextLine();
                    if (!inputTamano.isBlank()) {
                        try {
                            double nuevoTamano = Double.parseDouble(inputTamano);
                            parcelaEditar.setTamano(nuevoTamano);
                        } catch (NumberFormatException e) {
                            System.out.println("Tamaño inválido. No se modificó.");
                        }
                    }

                    System.out.print("Nueva ubicación (ENTER para dejar igual): ");
                    String nuevaUbicacion = sc.nextLine();
                    if (!nuevaUbicacion.isBlank()) {
                        parcelaEditar.setUbicacion(nuevaUbicacion);
                    }

                    System.out.println("Parcela actualizada.");
                    break;
                case 15:
                    System.out.print("Código de la parcela a eliminar: ");
                    String codigoParcelaEliminar = sc.nextLine();

                    Parcela parcelaEliminar = gestorParcelas.buscarPorCodigo(codigoParcelaEliminar).orElse(null);
                    if (parcelaEliminar == null) {
                        System.out.println("Parcela no encontrada.");
                        break;
                    }

                    if (!parcelaEliminar.getCultivos().isEmpty()) {
                        System.out.println("No se puede eliminar: la parcela tiene cultivos asignados.");
                        break;
                    }

                    gestorParcelas.eliminarParcela(parcelaEliminar);
                    System.out.println("Parcela eliminada correctamente.");
                    break;

                case 16:
                    System.out.print("Nombre exacto del cultivo a mover: ");
                    String cultivoAMover = sc.nextLine();

                    Cultivo cultivoReasignar = gestorCultivos.buscarPorNombreExacto(cultivoAMover).orElse(null);
                    if (cultivoReasignar == null) {
                        System.out.println("No se encontró ese cultivo.");
                        break;
                    }

                    System.out.print("Código de la nueva parcela: ");
                    String nuevoCodigoParcela = sc.nextLine();

                    Parcela destinoParcela = gestorParcelas.buscarPorCodigo(nuevoCodigoParcela).orElse(null);
                    if (destinoParcela == null) {
                        System.out.println("Parcela no encontrada.");
                        break;
                    }

                    // Elimina parcela anterior
                    cultivoReasignar.getParcela().eliminarCultivo(cultivoReasignar);

                    // Nueva parcela
                    cultivoReasignar.setParcela(destinoParcela);
                    destinoParcela.agregarCultivo(cultivoReasignar);

                    System.out.println("Cultivo reasignado correctamente.");
                    break;

                case 17: // Salir
                    LeerCSV.guardarCultivosEnCSV(archivoCSV, gestorCultivos.getCultivos());
                    System.out.println("Cambios guardados. ¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 17);
        sc.close();
    }
}
