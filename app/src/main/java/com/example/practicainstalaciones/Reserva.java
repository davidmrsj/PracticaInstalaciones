package com.example.practicainstalaciones;

public class Reserva {
    int id;
    String fechaReserva;

    public Reserva(int id, String fechaReserva) {
        this.id = id;
        this.fechaReserva = fechaReserva;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    @Override
    public String toString() {
        return "id: " + id + "+fechaReserva: " + fechaReserva;
    }
}
