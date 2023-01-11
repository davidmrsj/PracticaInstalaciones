package com.example.practicainstalaciones;

public class Reserva {
    String instalacion;
    String horaInicio;
    String horaFinal;
    String usuario;
    String fechaReserva;

    public Reserva(String instalacion, String horaInicio, String horaFinal, String usuario, String fechaReserva) {
        this.instalacion = instalacion;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
        this.usuario = usuario;
        this.fechaReserva = fechaReserva;
    }

    public String getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(String instalacion) {
        this.instalacion = instalacion;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    @Override
    public String toString() {
        return "Instalacion: " + instalacion + "\n fechaReserva: " + fechaReserva+" horas: "+horaInicio+"-"+horaFinal;
    }
}
