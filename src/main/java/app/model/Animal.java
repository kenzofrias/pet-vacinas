package app.model;

import java.util.Objects;

public class Animal {
    private Long id;
    private String nome;
    private String raca;
    private String dono;

    public Animal() {
    }

    public Animal(String nome, String raca, String dono) {
        this.nome = nome;
        this.raca = raca;
        this.dono = dono;
    }

    public Animal(Long id, String nome, String raca, String dono) {
        this.id = id;
        this.nome = nome;
        this.raca = raca;
        this.dono = dono;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getDono() {
        return dono;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(id, animal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", raca='" + raca + '\'' +
                ", dono='" + dono + '\'' +
                '}';
    }
}
