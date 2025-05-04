package models;
import java.time.LocalDate;

public class Actividad {
    private String tipo;
    private LocalDate fecha;
    private boolean completada;

    public Actividad(String tipo, LocalDate fecha) {
        this.tipo = tipo;
        this.fecha = fecha;
        this.completada = false;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public boolean estaCompletada() {
        return completada;
    }

    public void marcarComoCompletada() {
        this.completada = true;
    }

    @Override
    public String toString() {
        return tipo + ":" + fecha + (completada ? " (completada)" : "");
    }

    public String toCSV() {
        return tipo + ":" + fecha + ":" + (completada ? "Completada" : "No completada");
    }
}
