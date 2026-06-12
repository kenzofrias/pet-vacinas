# PET Vacinas (Java)

Aplicação para Controle de Vacinas de Animais PET baseada em Console User Interface (CUI) / Terminal User Interface (TUI). Desenvolvido como projeto final da disciplina de Programação Orientada a Objetos (POO).

---

## ⚡ Tecnologias Utilizadas

* **Java 17** - Linguagem principal do projeto.
* **Lanterna 3.1.2** - Biblioteca Java pura para construção da interface gráfica no terminal.
* **H2 Database** - Banco de dados relacional embarcado (em arquivo local) para persistência robusta dos dados.
* **JDBC** - Java Database Connectivity para comunicação com a persistência de banco de dados.
* **Maven** - Gerenciador de dependências e ferramentas de compilação.
* **JUnit 5** - Framework de testes para as regras de negócio.

---

## 🚀 Funcionalidades da Aplicação

O sistema atende de forma integral a todos os requisitos solicitados:
* **Cadastro de Animais**: Formulário interativo para salvar animais com nome, raça e tutor/dono.
* **Listagem de Animais**: Exibição em formato de tabela de todos os animais cadastrados.
* **Cadastro de Vacinas**: Formulário para registrar novas vacinas com nome e fabricante.
* **Listagem de Vacinas**: Exibição em formato de tabela de todas as vacinas disponíveis.
* **Registro de Vacinação**: Fluxo interativo associando um animal e uma vacina, definindo a data de aplicação e o veterinário/responsável.
* **Carteira de Vacinação (Histórico)**: Exibição detalhada de todas as vacinações associadas a um animal selecionado em formato tabular.
* **Persistência de Dados**: Diferencial na avaliação com banco H2 (esforço extra implementado com sucesso).

---

## 📁 Estrutura do Projeto

```text
pet-vacinas/
├── maven_bin/                    # Executável e binários locais do Maven.
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── app/
│   │   │       ├── Main.java             # Ponto de entrada da aplicação.
│   │   │       │
│   │   │       ├── database/             # Conectividade e configurações do banco.
│   │   │       │   ├── ConnectionFactory.java
│   │   │       │   └── DatabaseConfig.java
│   │   │       │
│   │   │       ├── model/                # Entidades do domínio (Animal, Vacina, Vacinacao).
│   │   │       │   ├── Animal.java
│   │   │       │   ├── Vacina.java
│   │   │       │   └── Vacinacao.java
│   │   │       │
│   │   │       ├── repository/           # Contratos de acesso aos dados (interfaces).
│   │   │       │   ├── AnimalRepository.java
│   │   │       │   ├── VacinaRepository.java
│   │   │       │   └── VacinacaoRepository.java
│   │   │       │
│   │   │       ├── persistence/          # Implementações concreta com JDBC.
│   │   │       │   ├── AnimalRepositoryJdbc.java
│   │   │       │   ├── VacinaRepositoryJdbc.java
│   │   │       │   └── VacinacaoRepositoryJdbc.java
│   │   │       │
│   │   │       ├── service/              # Regras de negócio da aplicação.
│   │   │       │   ├── AnimalService.java / AnimalServiceImpl.java
│   │   │       │   ├── VacinaService.java / VacinaServiceImpl.java
│   │   │       │   └── VacinacaoService.java / VacinacaoServiceImpl.java
│   │   │       │
│   │   │       ├── ui/                   # Interface Gráfica de Console (Lanterna).
│   │   │       │   └── Menu.java
│   │   │       │
│   │   │       └── util/                 # Classes auxiliares reutilizáveis.
│   │   │           └── UtilResource.java
│   │   │
│   │   └── resources/                    # Arquivos de configurações e scripts SQL.
│   │       ├── config.properties         # Configurações de acesso ao banco H2.
│   │       └── sql/
│   │           └── schema.sql            # Script de criação das tabelas.
│   │
│   └── test/                             # Testes Unitários de Regras de Negócio.
│       └── java/app/service/
│           ├── AnimalServiceTest.java
│           ├── VacinaServiceTest.java
│           └── VacinacaoServiceTest.java
│
├── pom.xml                               # Dependências e configuração do Maven.
└── README.md                             # Documentação do projeto.
```

---

## 🛠️ Como Executar o Projeto

Certifique-se de possuir o Java JDK 17 ou superior instalado e configurado no seu ambiente.

### Executando a Aplicação
Para compilar e iniciar a interface no terminal (CUI):
```bash
./maven_bin/bin/mvn clean compile exec:java -Dexec.mainClass="app.Main"
```

### Executando a Suíte de Testes
Para rodar todos os testes de unidade JUnit 5:
```bash
./maven_bin/bin/mvn clean test
```

---

## 📺 Apresentação e Links de Entrega

* **Link do Vídeo Demonstrativo no YouTube**: `[Adicione aqui o link do vídeo de até 3 minutos]`
* **Link do Repositório do Projeto**: `https://github.com/kenzofrias/pet-vacinas`

---

## 🤝 Colaboradores

Um agradecimento a todas as pessoas que contribuíram com este projeto:

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/apedror14" title="Github de Antonio Pedro">
        <img src="https://avatars.githubusercontent.com/u/212066811?v=4" width="100px; height=100px" alt="Foto de Antonio Pedro no GitHub"/><br>
        <sub>
          <b>Antonio Pedro</b>
        </sub>
      </a>
    </td>
    <td align="center">
    <a href="https://github.com/DanVVG" title="Github de Daniel Vitor">
      <img src="https://avatars.githubusercontent.com/u/220648971?v=4" width="100px; height=100px" alt="Foto de Daniel Vitor no GitHub"/><br>
      <sub>
        <b>Daniel Vitor</b>
      </sub>
    </a>
  </td>
  <td align="center">
    <a href="https://github.com/EduardoSantrovitsch" title="Github de Eduardo Santrovitsch">
      <img src="https://avatars.githubusercontent.com/u/211915639?v=4" width="100px; height=100px" alt="Foto de Eduardo Santrovitsch no GitHub"/><br>
      <sub>
        <b>Eduardo Santrovitsch</b>
      </sub>
    </a>
  </td>
  <td align="center">
    <a href="https://github.com/Gab9082" title="Github de Gabriel Chaves">
      <img src="https://avatars.githubusercontent.com/u/230392743?v=4" width="100px; height=100px" alt="Foto de Gabriel Chaves no GitHub"/><br>
      <sub>
        <b>Gabriel Chaves</b>
      </sub>
    </a>
  </td>
  <td align="center">
      <a href="https://github.com/HenriqueCavallero" title="Github de Henrique Cavallero">
        <img src="https://avatars.githubusercontent.com/u/213304769?v=4" width="100px; height=100px" alt="Foto de Henrique Cavallero no GitHub"/><br>
        <sub>
          <b>Henrique Cavallero</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/kenzofrias" title="Github de Kenzo Friás">
        <img src="https://avatars.githubusercontent.com/u/222035713?v=4" width="100px; height=100px" alt="Foto de Kenzo Friás no GitHub"/><br>
        <sub>
          <b>Kenzo Friás</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/liviamesquitax" title="Github de Lívia Mesquita">
        <img src="https://avatars.githubusercontent.com/u/221456667?v=4" width="100px; height=100px" alt="Foto de Lívia Mesquita no GitHub"/><br>
        <sub>
          <b>Lívia Mesquita</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/murilohenderson" title="Github de Murilo Henderson">
        <img src="https://avatars.githubusercontent.com/u/200527859?v=4" width="100px; height=100px" alt="Foto de Murilo Henderson no GitHub"/><br>
        <sub>
          <b>Murilo Henderson</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/MuriloSouusa" title="Github de Murilo Sousa">
        <img src="https://avatars.githubusercontent.com/u/203001174?v=4" width="100px; height=100px" alt="Foto de Murilo Sousa no GitHub"/><br>
        <sub>
          <b>Murilo Sousa</b>
        </sub>
      </a>
    </td>
  </tr>
</table>

## 📝 Licença

Esse projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---
<div align="center">
  
  **Obrigado pela visita!**
  
</div>
