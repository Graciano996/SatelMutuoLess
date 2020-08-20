package com.example.satelprojetos.model;

public class Mapa {
    private String latitude;
    private String longitude;
    private String codigo;
    private String cadastrado;
    private String existe;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCadastrado() {
        return cadastrado;
    }

    public void setCadastrado(String cadastrado) {
        this.cadastrado = cadastrado;
    }

    public String getExiste() {
        return existe;
    }

    public void setExiste(String existe) {
        this.existe = existe;
    }
}
