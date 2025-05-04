package models;

import java.util.ArrayList;
import java.util.List;

public class Parcela {
    private String codigo;
    private double tamano;  // ← sin "ñ"
    private String ubicacion;
    private List<Cultivo> cultivos;

    public Parcela(String codigo, double tamano, String ubicacion) {
        this.codigo = codigo;
        this.tamano = tamano;
        this.ubicacion = ubicacion;
        this.cultivos = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public double getTamano() {
        return tamano;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public List<Cultivo> getCultivos() {
        return cultivos;
    }

    public void agregarCultivo(Cultivo cultivo) {
        cultivos.add(cultivo);
        cultivo.setParcela(this); // ← mantener relación consistente
    }

    public void eliminarCultivo(Cultivo cultivo) {
        cultivos.remove(cultivo);
    }
    public void setTamano(double tamano) {
        this.tamano = tamano;
    }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    @Override
    public String toString() {
        return codigo + " (" + tamano + " ha, " + ubicacion + ")";
    }
}
