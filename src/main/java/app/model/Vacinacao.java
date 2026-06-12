package app.model;

import java.time.LocalDate;
import java.util.Objects;

public class Vacinacao {
    private Long id;
    private Animal animal;
    private Vacina vacina;
    private LocalDate data;
    private String responsavel;

    public Vacinacao() {
    }

    public Vacinacao(Animal animal, Vacina vacina, LocalDate data, String responsavel) {
        this.animal = animal;
        this.vacina = vacina;
        this.data = data;
        this.responsavel = responsavel;
    }

    public Vacinacao(Long id, Animal animal, Vacina vacina, LocalDate data, String responsavel) {
        this.id = id;
        this.animal = animal;
        this.vacina = vacina;
        this.data = data;
        this.responsavel = responsavel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Vacina getVacina() {
        return vacina;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacinacao vacinacao = (Vacinacao) o;
        return Objects.equals(id, vacinacao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Vacinacao{" +
                "id=" + id +
                ", animal=" + (animal != null ? animal.getNome() : null) +
                ", vacina=" + (vacina != null ? vacina.getNome() : null) +
                ", data=" + data +
                ", responsavel='" + responsavel + '\'' +
                '}';
    }
}
