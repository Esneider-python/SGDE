package com.inventario.modelo;

import java.sql.Timestamp;

public class DocenteAula {

    private int id;
    private int idUsuario;
    private int idAula;
    private String dia;
    private String horaInicio;
    private String horaFin;
    private Timestamp fechaAsignacion;

    public DocenteAula() {
    }

    public DocenteAula(int id, int idUsuario, int idAula, String dia, String horaInicio, String horaFin, Timestamp fechaAsignacion) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idAula = idAula;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.fechaAsignacion = fechaAsignacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public Timestamp getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Timestamp fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
}
