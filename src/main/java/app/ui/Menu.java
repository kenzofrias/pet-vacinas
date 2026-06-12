package app.ui;

import app.model.Animal;
import app.model.Vacina;
import app.model.Vacinacao;
import app.service.*;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.ListSelectDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import app.util.UtilResource;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private final AnimalService animalService;
    private final VacinaService vacinaService;
    private final VacinacaoService vacinacaoService;

    public Menu() {
        this.animalService = new AnimalServiceImpl();
        this.vacinaService = new VacinaServiceImpl();
        this.vacinacaoService = new VacinacaoServiceImpl();
    }

    public void start() {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Screen screen = null;
        try {
            screen = terminalFactory.createScreen();
            screen.startScreen();

            WindowBasedTextGUI gui = new MultiWindowTextGUI(screen);

            BasicWindow mainWindow = new BasicWindow("Controle de Vacinas PET");
            mainWindow.setHints(List.of(Window.Hint.CENTERED));

            Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));
            panel.addComponent(new Label("=== Menu Principal ==="));
            panel.addComponent(new EmptySpace(new TerminalSize(1, 1)));

            panel.addComponent(new Button("1. Cadastrar Animal", () -> cadastrarAnimal(gui)));
            panel.addComponent(new Button("2. Listar Animais", () -> listarAnimais(gui)));
            panel.addComponent(new Button("3. Cadastrar Vacina", () -> cadastrarVacina(gui)));
            panel.addComponent(new Button("4. Listar Vacinas", () -> listarVacinas(gui)));
            panel.addComponent(new Button("5. Registrar Vacinação", () -> registrarVacinacao(gui)));
            panel.addComponent(new Button("6. Histórico de Vacinações (Carteira)", () -> historicoVacinacoes(gui)));
            panel.addComponent(new EmptySpace(new TerminalSize(1, 1)));
            panel.addComponent(new Button("7. Sair", mainWindow::close));

            mainWindow.setComponent(panel);
            gui.addWindowAndWait(mainWindow);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (screen != null) {
                try {
                    screen.stopScreen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void cadastrarAnimal(WindowBasedTextGUI gui) {
        BasicWindow window = new BasicWindow("Cadastrar Animal");
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel mainPanel = new Panel(new GridLayout(2));

        mainPanel.addComponent(new Label("Nome:"));
        TextBox txtNome = new TextBox(new TerminalSize(30, 1));
        mainPanel.addComponent(txtNome);

        mainPanel.addComponent(new Label("Raça:"));
        TextBox txtRaca = new TextBox(new TerminalSize(30, 1));
        mainPanel.addComponent(txtRaca);

        mainPanel.addComponent(new Label("Dono/Tutor:"));
        TextBox txtDono = new TextBox(new TerminalSize(30, 1));
        mainPanel.addComponent(txtDono);

        mainPanel.addComponent(new EmptySpace());

        Panel btnPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        btnPanel.addComponent(new Button("Salvar", () -> {
            String nome = txtNome.getText().trim();
            String raca = txtRaca.getText().trim();
            String dono = txtDono.getText().trim();
            try {
                animalService.cadastrarAnimal(new Animal(nome, raca, dono));
                UtilResource.showInfo(gui, "Sucesso", "Animal cadastrado com sucesso!");
                window.close();
            } catch (Exception e) {
                UtilResource.showError(gui, "Erro", "Erro ao cadastrar: " + e.getMessage());
            }
        }));
        btnPanel.addComponent(new Button("Cancelar", window::close));
        mainPanel.addComponent(btnPanel);

        window.setComponent(mainPanel);
        gui.addWindow(window);
    }

    private void listarAnimais(WindowBasedTextGUI gui) {
        BasicWindow window = new BasicWindow("Lista de Animais");
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));

        List<Animal> animais = animalService.listarAnimais();
        if (animais.isEmpty()) {
            panel.addComponent(new Label("Nenhum animal cadastrado."));
        } else {
            Table<String> table = new Table<>("ID", "Nome", "Raça", "Dono/Tutor");
            for (Animal a : animais) {
                table.getTableModel().addRow(String.valueOf(a.getId()), a.getNome(), a.getRaca(), a.getDono());
            }
            panel.addComponent(table);
        }

        panel.addComponent(new EmptySpace(new TerminalSize(1, 1)));
        panel.addComponent(new Button("Voltar", window::close));

        window.setComponent(panel);
        gui.addWindow(window);
    }

    private void cadastrarVacina(WindowBasedTextGUI gui) {
        BasicWindow window = new BasicWindow("Cadastrar Vacina");
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel mainPanel = new Panel(new GridLayout(2));

        mainPanel.addComponent(new Label("Nome da Vacina:"));
        TextBox txtNome = new TextBox(new TerminalSize(30, 1));
        mainPanel.addComponent(txtNome);

        mainPanel.addComponent(new Label("Fabricante:"));
        TextBox txtFabricante = new TextBox(new TerminalSize(30, 1));
        mainPanel.addComponent(txtFabricante);

        mainPanel.addComponent(new EmptySpace());

        Panel btnPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        btnPanel.addComponent(new Button("Salvar", () -> {
            String nome = txtNome.getText().trim();
            String fabricante = txtFabricante.getText().trim();
            try {
                vacinaService.cadastrarVacina(new Vacina(nome, fabricante));
                UtilResource.showInfo(gui, "Sucesso", "Vacina cadastrada com sucesso!");
                window.close();
            } catch (Exception e) {
                UtilResource.showError(gui, "Erro", "Erro ao cadastrar: " + e.getMessage());
            }
        }));
        btnPanel.addComponent(new Button("Cancelar", window::close));
        mainPanel.addComponent(btnPanel);

        window.setComponent(mainPanel);
        gui.addWindow(window);
    }

    private void listarVacinas(WindowBasedTextGUI gui) {
        BasicWindow window = new BasicWindow("Lista de Vacinas");
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));

        List<Vacina> vacinas = vacinaService.listarVacinas();
        if (vacinas.isEmpty()) {
            panel.addComponent(new Label("Nenhuma vacina cadastrada."));
        } else {
            Table<String> table = new Table<>("ID", "Nome da Vacina", "Fabricante");
            for (Vacina v : vacinas) {
                table.getTableModel().addRow(String.valueOf(v.getId()), v.getNome(), v.getFabricante());
            }
            panel.addComponent(table);
        }

        panel.addComponent(new EmptySpace(new TerminalSize(1, 1)));
        panel.addComponent(new Button("Voltar", window::close));

        window.setComponent(panel);
        gui.addWindow(window);
    }

    private void registrarVacinacao(WindowBasedTextGUI gui) {
        List<Animal> animais = animalService.listarAnimais();
        if (animais.isEmpty()) {
            UtilResource.showError(gui, "Aviso", "Cadastre um animal antes de registrar uma vacinação.");
            return;
        }

        List<Vacina> vacinas = vacinaService.listarVacinas();
        if (vacinas.isEmpty()) {
            UtilResource.showError(gui, "Aviso", "Cadastre uma vacina antes de registrar uma vacinação.");
            return;
        }

        List<OptionWrapper<Animal>> animalOptions = new ArrayList<>();
        for (Animal a : animais) {
            animalOptions.add(new OptionWrapper<>(a, "ID: " + a.getId() + " - " + a.getNome() + " (" + a.getRaca() + ")"));
        }
        ListSelectDialogBuilder<OptionWrapper<Animal>> animalBuilder = new ListSelectDialogBuilder<OptionWrapper<Animal>>()
                .setTitle("Selecionar Animal")
                .setDescription("Selecione o animal que recebeu a vacina:");
        for (OptionWrapper<Animal> opt : animalOptions) {
            animalBuilder.addListItem(opt);
        }
        OptionWrapper<Animal> selectedAnimalWrapper = animalBuilder.build().showDialog(gui);

        if (selectedAnimalWrapper == null) return;
        Animal animal = selectedAnimalWrapper.getValue();

        List<OptionWrapper<Vacina>> vacinaOptions = new ArrayList<>();
        for (Vacina v : vacinas) {
            vacinaOptions.add(new OptionWrapper<>(v, "ID: " + v.getId() + " - " + v.getNome() + " (" + v.getFabricante() + ")"));
        }
        ListSelectDialogBuilder<OptionWrapper<Vacina>> vacinaBuilder = new ListSelectDialogBuilder<OptionWrapper<Vacina>>()
                .setTitle("Selecionar Vacina")
                .setDescription("Selecione a vacina aplicada:");
        for (OptionWrapper<Vacina> opt : vacinaOptions) {
            vacinaBuilder.addListItem(opt);
        }
        OptionWrapper<Vacina> selectedVacinaWrapper = vacinaBuilder.build().showDialog(gui);

        if (selectedVacinaWrapper == null) return;
        Vacina vacina = selectedVacinaWrapper.getValue();

        BasicWindow window = new BasicWindow("Registrar Vacinação");
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel formPanel = new Panel(new GridLayout(2));

        formPanel.addComponent(new Label("Animal:"));
        formPanel.addComponent(new Label(animal.getNome() + " (ID: " + animal.getId() + ")"));

        formPanel.addComponent(new Label("Vacina:"));
        formPanel.addComponent(new Label(vacina.getNome() + " (ID: " + vacina.getId() + ")"));

        formPanel.addComponent(new Label("Data (AAAA-MM-DD):"));
        TextBox txtData = new TextBox(new TerminalSize(15, 1));
        txtData.setText(LocalDate.now().toString());
        formPanel.addComponent(txtData);

        formPanel.addComponent(new Label("Responsável:"));
        TextBox txtResponsavel = new TextBox(new TerminalSize(30, 1));
        formPanel.addComponent(txtResponsavel);

        formPanel.addComponent(new EmptySpace());

        Panel btnPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        btnPanel.addComponent(new Button("Salvar", () -> {
            String dataStr = txtData.getText().trim();
            String responsavel = txtResponsavel.getText().trim();
            try {
                LocalDate data = LocalDate.parse(dataStr);
                Vacinacao vacinacao = new Vacinacao(animal, vacina, data, responsavel);
                vacinacaoService.registrarVacinacao(vacinacao);
                UtilResource.showInfo(gui, "Sucesso", "Vacinação registrada com sucesso!");
                window.close();
            } catch (Exception e) {
                UtilResource.showError(gui, "Erro", "Erro ao registrar: " + e.getMessage() + "\nUse o formato AAAA-MM-DD para data.");
            }
        }));
        btnPanel.addComponent(new Button("Cancelar", window::close));
        formPanel.addComponent(btnPanel);

        window.setComponent(formPanel);
        gui.addWindow(window);
    }

    private void historicoVacinacoes(WindowBasedTextGUI gui) {
        List<Animal> animais = animalService.listarAnimais();
        if (animais.isEmpty()) {
            UtilResource.showError(gui, "Aviso", "Nenhum animal cadastrado.");
            return;
        }

        List<OptionWrapper<Animal>> animalOptions = new ArrayList<>();
        for (Animal a : animais) {
            animalOptions.add(new OptionWrapper<>(a, "ID: " + a.getId() + " - " + a.getNome() + " (Tutor: " + a.getDono() + ")"));
        }
        ListSelectDialogBuilder<OptionWrapper<Animal>> animalBuilder = new ListSelectDialogBuilder<OptionWrapper<Animal>>()
                .setTitle("Carteira de Vacinação")
                .setDescription("Selecione o animal para ver o histórico:");
        for (OptionWrapper<Animal> opt : animalOptions) {
            animalBuilder.addListItem(opt);
        }
        OptionWrapper<Animal> selectedAnimalWrapper = animalBuilder.build().showDialog(gui);

        if (selectedAnimalWrapper == null) return;
        Animal animal = selectedAnimalWrapper.getValue();

        List<Vacinacao> historico = vacinacaoService.obterHistoricoAnimal(animal.getId());

        BasicWindow window = new BasicWindow("Carteira de Vacinação: " + animal.getNome());
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(new Label("Histórico do PET: " + animal.getNome() + " (" + animal.getRaca() + ")"));
        panel.addComponent(new Label("Dono/Tutor: " + animal.getDono()));
        panel.addComponent(new EmptySpace(new TerminalSize(1, 1)));

        if (historico.isEmpty()) {
            panel.addComponent(new Label("Nenhuma vacina aplicada até o momento."));
        } else {
            Table<String> table = new Table<>("Vacina", "Fabricante", "Data Aplicada", "Responsável");
            for (Vacinacao v : historico) {
                table.getTableModel().addRow(
                        v.getVacina().getNome(),
                        v.getVacina().getFabricante(),
                        v.getData().toString(),
                        v.getResponsavel()
                );
            }
            panel.addComponent(table);
        }

        panel.addComponent(new EmptySpace(new TerminalSize(1, 1)));
        panel.addComponent(new Button("Voltar", window::close));

        window.setComponent(panel);
        gui.addWindow(window);
    }

    private static class OptionWrapper<T> {
        private final T value;
        private final String label;

        public OptionWrapper(T value, String label) {
            this.value = value;
            this.label = label;
        }

        public T getValue() {
            return value;
        }

        @Override
        public String toString() {
            return label;
        }
    }
}
