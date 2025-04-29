package com.inventario.modelo;

public class Aula {

    private int id;
    private int numeroAula;
    private Piso piso;
    private Usuario usuarioRegistra;

    public int getNumeroAula() {
        return numeroAula;
    }

    public void setNumeroAula(int numeroAula) {
        this.numeroAula = numeroAula;
    }

// Constructor actualizado
    public Aula(int id, int numeroAula, Piso piso, Usuario usuarioRegistra) {
        this.id = id;
        this.numeroAula = numeroAula;
        this.piso = piso;
        this.usuarioRegistra = usuarioRegistra;
    }

    public Aula(int numeroAula, Piso piso, Usuario usuarioRegistra) {
        this.numeroAula = numeroAula;
        this.piso = piso;
        this.usuarioRegistra = usuarioRegistra;
    }


    public Aula(Piso piso, Usuario usuarioRegistra) {
        this.piso = piso;
        this.usuarioRegistra = usuarioRegistra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Piso getPiso() {
        return piso;
    }

    public void setPiso(Piso piso) {
        this.piso = piso;
    }

    public Usuario getUsuarioRegistra() {
        return usuarioRegistra;
    }

    public void setUsuarioRegistra(Usuario usuarioRegistra) {
        this.usuarioRegistra = usuarioRegistra;
    }
}
