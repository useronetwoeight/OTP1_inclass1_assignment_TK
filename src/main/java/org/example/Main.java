package org.example;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.example.config.I18n;

import java.sql.SQLException;
import java.util.List;

public class Main extends Application {

    private TemperatureUnitDAO unitDao = new TemperatureUnitDAO();
    private TempRecordDAO recordDao = new TempRecordDAO();
    private TemperatureConverter converter = new TemperatureConverter();

    private ObservableList<TempRecord> rows = FXCollections.observableArrayList();
    private Label status = new Label("");

    // everything that shows text, so the language can be switched at runtime
    private Stage stage;
    private Label languageLabel = new Label();
    private Label temperatureLabel = new Label();
    private Label toLabel = new Label();
    private Label distanceLabel = new Label();
    private Label timeLabel = new Label();
    private Button saveButton = new Button();
    private Button deleteButton = new Button();
    private TableView<TempRecord> table = new TableView<>(rows);
    private TableColumn<TempRecord, String> idColumn = new TableColumn<>();
    private TableColumn<TempRecord, String> fromColumn = new TableColumn<>();
    private TableColumn<TempRecord, String> toColumn = new TableColumn<>();
    private TableColumn<TempRecord, String> distanceColumn = new TableColumn<>();
    private TableColumn<TempRecord, String> timeColumn = new TableColumn<>();
    private TableColumn<TempRecord, String> speedColumn = new TableColumn<>();
    private ComboBox<TemperatureUnit> fromBox = new ComboBox<>();
    private ComboBox<TemperatureUnit> toBox = new ComboBox<>();

    public static double parse(String text) {
        return Double.parseDouble(text.trim().replace(',', '.'));
    }

    public static String fmt(double number) {
        return String.format(I18n.getFormatLocale(), "%.2f", number);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ChoiceDialog<I18n.Language> dialog = new ChoiceDialog<>(I18n.Language.ENG, I18n.Language.values());
        dialog.setTitle("Language / Kieli / Språk / 言語");
        dialog.setHeaderText("Select your language");
        dialog.setContentText("Language:");
        dialog.showAndWait().ifPresent(I18n::setLanguage);

        this.stage = stage;
        DBConnection.init();
        List<TemperatureUnit> units = unitDao.findAll();

        TextField valueField = new TextField();
        TextField distanceField = new TextField();
        TextField timeField = new TextField();

        ComboBox<I18n.Language> languageBox = new ComboBox<>();
        languageBox.getItems().addAll(I18n.Language.values());
        languageBox.getSelectionModel().select(I18n.getLanguage());

        fromBox.getItems().addAll(units);
        fromBox.getSelectionModel().select(0);

        toBox.getItems().addAll(units);
        toBox.getSelectionModel().select(1);

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.addRow(0, languageLabel, languageBox);
        form.addRow(1, temperatureLabel, valueField, fromBox, toLabel, toBox);
        form.addRow(2, distanceLabel, distanceField);
        form.addRow(3, timeLabel, timeField);
        form.addRow(4, saveButton, deleteButton);

        // table with the saved records
        idColumn.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));
        fromColumn.setCellValueFactory(data -> new SimpleStringProperty(
                fmt(data.getValue().getInputValue()) + " " + data.getValue().getFromSymbol()));
        toColumn.setCellValueFactory(data -> new SimpleStringProperty(
                fmt(data.getValue().getResultValue()) + " " + data.getValue().getToSymbol()));
        distanceColumn.setCellValueFactory(data -> new SimpleStringProperty(fmt(data.getValue().getDistanceKm())));
        timeColumn.setCellValueFactory(data -> new SimpleStringProperty(fmt(data.getValue().getTimeHours())));
        speedColumn.setCellValueFactory(data -> new SimpleStringProperty(fmt(data.getValue().getSpeedKmh())));

        table.getColumns().add(idColumn);
        table.getColumns().add(fromColumn);
        table.getColumns().add(toColumn);
        table.getColumns().add(distanceColumn);
        table.getColumns().add(timeColumn);
        table.getColumns().add(speedColumn);

        languageBox.setOnAction(e -> {
            I18n.setLanguage(languageBox.getValue());
            status.setText("");
            applyTexts();
        });

        saveButton.setOnAction(e -> {
            try {
                double value = parse(valueField.getText());
                double distance = parse(distanceField.getText());
                double time = parse(timeField.getText());
                TemperatureUnit from = fromBox.getValue();
                TemperatureUnit to = toBox.getValue();

                double result = converter.convert(value, from.getSymbol(), to.getSymbol());

                TempRecord record = new TempRecord(from.getId(), to.getId(), value, result, distance, time);
                recordDao.insert(record);
                refreshTable();

                status.setText(I18n.t("status.saved", fmt(value), from.getSymbol(), fmt(result), to.getSymbol()));
            } catch (NumberFormatException ex) {
                status.setText(I18n.t("status.invalidNumber"));
            } catch (Exception ex) {
                status.setText(I18n.t("status.error", ex.getMessage()));
            }
        });

        deleteButton.setOnAction(e -> {
            TempRecord selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                status.setText(I18n.t("status.selectRow"));
                return;
            }
            try {
                recordDao.delete(selected.getId());
                refreshTable();
                status.setText(I18n.t("status.deleted", String.valueOf(selected.getId())));
            } catch (SQLException ex) {
                status.setText(I18n.t("status.error", ex.getMessage()));
            }
        });

        refreshTable();
        applyTexts();

        VBox root = new VBox(10, form, table, status);
        root.setPadding(new Insets(10));

        stage.setScene(new Scene(root, 700, 500));
        stage.show();
    }

    /** Sets every visible text from the current language. */
    private void applyTexts() {
        stage.setTitle(I18n.t("app.title"));
        languageLabel.setText(I18n.t("label.language"));
        temperatureLabel.setText(I18n.t("label.temperature"));
        toLabel.setText(I18n.t("label.to"));
        distanceLabel.setText(I18n.t("label.distance"));
        timeLabel.setText(I18n.t("label.time"));
        saveButton.setText(I18n.t("button.save"));
        deleteButton.setText(I18n.t("button.delete"));

        idColumn.setText(I18n.t("column.id"));
        fromColumn.setText(I18n.t("column.from"));
        toColumn.setText(I18n.t("column.to"));
        distanceColumn.setText(I18n.t("column.distance"));
        timeColumn.setText(I18n.t("column.time"));
        speedColumn.setText(I18n.t("column.speed"));

        refreshUnitBox(fromBox);
        refreshUnitBox(toBox);
        table.refresh(); // numbers are re-formatted for the new language
    }

    private void refreshUnitBox(ComboBox<TemperatureUnit> box) {
        int selected = box.getSelectionModel().getSelectedIndex();
        box.setConverter(new StringConverter<>() {
            @Override
            public String toString(TemperatureUnit unit) {
                if (unit == null) {
                    return "";
                }
                String key = "unit." + unit.getSymbol();
                return I18n.has(key) ? I18n.t(key) : unit.getSymbol();
            }

            @Override
            public TemperatureUnit fromString(String text) {
                return null;
            }
        });
        box.getSelectionModel().clearSelection();
        box.getSelectionModel().select(selected);
    }

    private void refreshTable() throws SQLException {
        rows.setAll(recordDao.findAll());
    }

    public static void main(String[] args) {
        launch(args);
    }
}