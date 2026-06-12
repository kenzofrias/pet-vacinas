package app.model;

import java.util.Objects;

public class Vacina {
    private Long id;
    private String nome;
    private String fabricante;

    public Vacina() {
    }

    public Vacina(String nome, String fabricante) {
        this.nome = nome;
        this.fabricante = fabricante;
    }

    public Vacina(Long id, String nome, String fabricante) {
        this.id = id;
        this.nome = nome;
        this.fabricante = fabricante;
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

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacina vacina = (Vacina) o;
        return Objects.equals(id, vacina.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Vacina{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", fabricante='" + fabricante + '\'' +
                '}';
    }
}
