package services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import models.Cultivo;
import models.Actividad;

public class GestorActividades {

    public void registrarActividad(Cultivo cultivo, String tipo, LocalDate fecha) {
        Actividad nueva = new Actividad(tipo, fecha);
        cultivo.agregarActividad(nueva);
    }

    public List<Actividad> obtenerActividades(Cultivo cultivo) {
        return cultivo.getActividades();
    }

    public void eliminarActividad(Cultivo cultivo, Actividad actividad) {
        cultivo.eliminarActividad(actividad);
    }

    public Optional<Actividad> buscarActividad(Cultivo cultivo, String tipo) {
        return cultivo.getActividades().stream()
                .filter(a -> a.getTipo().equalsIgnoreCase(tipo))
                .findFirst();
    }

    public void marcarComoCompletada(Cultivo cultivo, String tipo) {
        Optional<Actividad> act = buscarActividad(cultivo, tipo);
        act.ifPresent(Actividad::marcarComoCompletada);
    }

    public void listarActividades(Cultivo cultivo) {
        System.out.println("Actividades para cultivo: " + cultivo.getNombre());
        for (Actividad a : cultivo.getActividades()) {
            System.out.println("- " + a);
        }
    }
}
