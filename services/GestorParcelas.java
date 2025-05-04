package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import models.Cultivo;
import models.Parcela;

public class GestorParcelas {
    private List<Parcela> parcelas;

    public GestorParcelas() {
        this.parcelas = new ArrayList<>();
    }

    public void agregarParcela(Parcela parcela) {
        parcelas.add(parcela);
    }

    public void eliminarParcela(Parcela parcela) {
        if (parcela.getCultivos().isEmpty()) {
            parcelas.remove(parcela);
        } else {
            System.out.println("No se puede eliminar: la parcela tiene cultivos activos.");
        }
    }

    public List<Parcela> getParcelas() {
        return parcelas;
    }

    public Optional<Parcela> buscarPorCodigo(String codigo) {
        return parcelas.stream()
                .filter(p -> p.getCodigo().equalsIgnoreCase(codigo))
                .findFirst();
    }

    public void editarParcela(Parcela parcela, double nuevoTamano, String nuevaUbicacion) {
        // Actualizamos directamente los campos de la parcela existente
        // Asumiendo que estos campos pueden ser modificables
        try {
            java.lang.reflect.Field fTamano = Parcela.class.getDeclaredField("tamano");
            java.lang.reflect.Field fUbicacion = Parcela.class.getDeclaredField("ubicacion");
            fTamano.setAccessible(true);
            fUbicacion.setAccessible(true);
            fTamano.set(parcela, nuevoTamano);
            fUbicacion.set(parcela, nuevaUbicacion);
        } catch (Exception e) {
            System.out.println("Error al editar la parcela: " + e.getMessage());
        }
    }

    public void asignarCultivo(Parcela parcela, Cultivo cultivo) {
        parcela.agregarCultivo(cultivo);
    }
}
