package com.inventario.modelo;

import java.sql.Timestamp;
import java.time.LocalTime;

public class DocenteAula {

    private int id;
    private int idUsuario;
    private int idAula;
    private String dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Timestamp fechaAsignacion;

    public DocenteAula() {
    }

    public DocenteAula( int idUsuario, int idAula, String dia, LocalTime horaInicio, LocalTime horaFin) {
        this.idUsuario = idUsuario;
        this.idAula = idAula;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public int getId() {
        return id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdAula() {
        return idAula;
    }

    public void setIdAula(int idAula) {
        this.idAula = idAula;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public Timestamp getFechaAsignacion() {
        return fechaAsignacion;
    }

}
