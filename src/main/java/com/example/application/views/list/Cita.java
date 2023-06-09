package com.example.application;

import java.time.LocalDate;

public class Cita {
    private String nombre;
    private String apellido;
    private String cedula;
    private LocalDate fecha;
    private String hora;
    private String historiaClinica;

    public Cita(String nombre, String apellido, String cedula, LocalDate fecha, String hora, String historiaClinica) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.fecha = fecha;
        this.hora = hora;
        this.historiaClinica = historiaClinica;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getHistoriaClinica() {
        return historiaClinica;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

}
