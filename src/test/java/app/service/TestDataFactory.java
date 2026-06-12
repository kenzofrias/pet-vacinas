package app.service;

import app.model.Animal;
import app.model.Vacina;
import app.model.Vacinacao;

import java.time.LocalDate;

public class TestDataFactory {

    public static Animal createAnimal(Long id, String nome, String raca, String dono) {
        Animal animal = new Animal(nome, raca, dono);
        animal.setId(id);
        return animal;
    }

    public static Vacina createVacina(Long id, String nome, String fabricante) {
        Vacina vacina = new Vacina(nome, fabricante);
        vacina.setId(id);
        return vacina;
    }

    public static Vacinacao createVacinacao(Long id, Animal animal, Vacina vacina, LocalDate data, String responsavel) {
        Vacinacao vacinacao = new Vacinacao(animal, vacina, data, responsavel);
        vacinacao.setId(id);
        return vacinacao;
    }
}
