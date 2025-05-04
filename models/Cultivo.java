package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Cultivo extends ElementoAgricola {
    private String variedad;
    private double superficie;
    private Parcela parcela;
    private List<Actividad> actividades;

    public Cultivo(String nombre, String variedad, double superficie, Parcela parcela, LocalDate fechaSiembra, String estado) {
        super(nombre, fechaSiembra, estado);
        this.variedad = variedad;
        this.superficie = superficie;
        this.parcela = parcela;
        this.actividades = new ArrayList<>();
    }

    public String getVariedad() {
        return variedad;
    }

    public double getSuperficie() {
        return superficie;
    }

    public Parcela getParcela() {
        return parcela;
    }

    public void setParcela(Parcela parcela) {
        this.parcela = parcela;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void agregarActividad(Actividad act) {
        actividades.add(act);
    }

    public void eliminarActividad(Actividad act) {
        actividades.remove(act);
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setVariedad(String variedad) {
        this.variedad = variedad;
    }
    
    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }
    

    @Override
    public String toCSV() {
        String actividadesStr = actividades.stream()
            .map(Actividad::toCSV)
            .collect(Collectors.joining("\",\""));
        return String.format(Locale.US,
            "Cultivo,\"%s\",\"%s\",%.2f,\"%s\",\"%s\",\"%s\",[\"%s\"]",
            nombre, variedad, superficie, parcela.getCodigo(), fecha, estado, actividadesStr);
    }

    @Override
    public String toString() {
        return nombre + " (" + variedad + ", " + estado + ")";
    }
}
