package services;

import models.Cultivo;
import models.Parcela;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GestorCultivos {
    private List<Cultivo> cultivos;

    public GestorCultivos() {
        this.cultivos = new ArrayList<>();
    }

    public void agregarCultivo(Cultivo cultivo) {
        cultivos.add(cultivo);
    }

    public List<Cultivo> getCultivos() {
        return cultivos;
    }

    public void eliminarCultivo(Cultivo cultivo) {
        if (cultivo.getActividades().isEmpty()) {
            cultivos.remove(cultivo);
        } else {
            System.out.println("No se puede eliminar: el cultivo tiene actividades asociadas.");
        }
    }

    public List<Cultivo> buscarPorNombre(String nombre) {
        return cultivos.stream()
                .filter(c -> c.getNombre().equalsIgnoreCase(nombre))
                .collect(Collectors.toList());
    }

    public List<Cultivo> buscarPorVariedad(String variedad) {
        return cultivos.stream()
                .filter(c -> c.getVariedad().equalsIgnoreCase(variedad))
                .collect(Collectors.toList());
    }

    public void editarCultivo(Cultivo cultivo, String nuevoNombre, String nuevaVariedad, double nuevaSuperficie) {
        cultivo.setNombre(nuevoNombre);
        cultivo.setFecha(cultivo.getFecha());  // mantén la fecha original
        cultivo.setEstado(cultivo.getEstado()); // mantén el estado actual
        cultivo.setNombre(nuevoNombre);
    }

    public void asignarCultivoAParcela(Cultivo cultivo, Parcela parcela) {
        cultivo.getParcela().eliminarCultivo(cultivo); // eliminar de la anterior si aplica
        cultivo.setFecha(cultivo.getFecha()); // conservar la fecha
        cultivo.setEstado(cultivo.getEstado());
        parcela.agregarCultivo(cultivo);
    }

    public List<Cultivo> cultivosPorEstado(String estado) {
        return cultivos.stream()
                .filter(c -> c.getEstado().equalsIgnoreCase(estado))
                .collect(Collectors.toList());
    }

    public Optional<Cultivo> buscarPorNombreExacto(String nombre) {
        return cultivos.stream()
                .filter(c -> c.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }
}
