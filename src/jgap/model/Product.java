package jgap.model;

import jgap.Evaluable;

public class Product implements Evaluable {

    private final String name;
    private final Double valor;
    private final Double volume;

    public Product(String name, Double volume, Double valor) {
        super();
        this.name = name;
        this.valor = valor;
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    @Override
    public Double getScore() {
        return valor;
    }

    @Override
    public Double getTarget() {
        return volume;
    }
}