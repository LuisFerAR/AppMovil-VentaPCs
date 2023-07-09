package com.example.proyecto.clases;

public class Auto {
    private int id;
    private String Marca;
    private String Modelo;
    private String Placa;
    private double Precio;
    private String Imagen;

    public Auto() {
    }

    public Auto(int id, String marca, String modelo, String placa, double precio, String imagen) {
        this.id = id;
        Marca = marca;
        Modelo = modelo;
        Placa = placa;
        Precio = precio;
        Imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public String getPlaca() {
        return Placa;
    }

    public void setPlaca(String placa) {
        Placa = placa;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double precio) {
        Precio = precio;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }
}
