package verwaltung;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import klasse.AnzeigeAufgeben;
import klasse.AnzeigeAufgebenFX;
import klasse.Buch;
import klasse.BuchFX;
import klasse.Datenbank;
import klasse.Kaufen;
import klasse.KaufenFX;
import klasse.Leihen;
import klasse.LeihenFX;
import klasse.Mitglied;
import klasse.MitgliedFX;

public class Verwaltung_Application extends Application {

	private ObservableList<MitgliedFX> olMitglied = FXCollections.observableArrayList();
	private ObservableList<BuchFX> olBuch = FXCollections.observableArrayList();
	private ObservableList<KaufenFX> olKaufen = FXCollections.observableArrayList();
	private ObservableList<LeihenFX> olLeihen = FXCollections.observableArrayList();
	private ObservableList<AnzeigeAufgebenFX> olAnzeige = FXCollections.observableArrayList();
	Stage Window;

	@Override
	public void start(Stage primaryStage) {
		try {
			Datenbank.createBooks();
			Datenbank.createMitglied();
			Datenbank.createKaufen();
			Datenbank.createLeihen();
			Datenbank.createAnzeige();
		} catch (SQLException e1) {

			new Alert(AlertType.ERROR, e1.toString()).showAndWait();
		}
		Window = primaryStage;

		/**
		 * Im BÜCHERLISTE_TAB können die Mitarbeiter die Bücher anlegen und bearbeiten .
		 */

		Tab buch = new Tab("Bücherliste");
		buch.setClosable(false);
		buch.getStyleClass().add("newstyle-tab");

		TableColumn<BuchFX, Integer> buchidCol = new TableColumn<>("Id");
		buchidCol.setCellValueFactory(new PropertyValueFactory<>("buchId"));
		buchidCol.setMinWidth(80);
		TableColumn<BuchFX, Integer> isbnCol = new TableColumn<>("Isbn");
		isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
		isbnCol.setMinWidth(80);
		TableColumn<BuchFX, String> autorCol = new TableColumn<>("Autor");
		autorCol.setCellValueFactory(new PropertyValueFactory<>("autor"));
		autorCol.setMinWidth(150);
		TableColumn<BuchFX, String> titelCol = new TableColumn<>("BuchTitel");
		titelCol.setCellValueFactory(new PropertyValueFactory<>("titel"));
		titelCol.setMinWidth(150);
		TableColumn<BuchFX, String> themaCol = new TableColumn<>("Thema");
		themaCol.setCellValueFactory(new PropertyValueFactory<>("thema"));
		themaCol.setMinWidth(100);
		TableColumn<BuchFX, Integer> jahrCol = new TableColumn<>("Jahrschein");
		jahrCol.setCellValueFactory(new PropertyValueFactory<>("jahr"));
		jahrCol.setMinWidth(100);

		TableColumn<BuchFX, Double> PreisCol = new TableColumn<>("Preis");
		PreisCol.setCellValueFactory(new PropertyValueFactory<>("preis"));
		PreisCol.setMinWidth(80);

		TableColumn<BuchFX, Boolean> verkuafenCol = new TableColumn<>("Verkaufen");
		verkuafenCol.setCellValueFactory(new PropertyValueFactory<>("verkaufen"));
		verkuafenCol.setMinWidth(80);
		TableColumn<BuchFX, String> absenderAdresseCol = new TableColumn<>("Absender Adresse");
		absenderAdresseCol.setCellValueFactory(new PropertyValueFactory<>("absenderAdresse"));
		absenderAdresseCol.setMinWidth(120);

		TableView<BuchFX> tvbuch = new TableView<>(olBuch);
		tvbuch.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		tvbuch.getColumns().addAll(buchidCol, isbnCol, autorCol, titelCol, themaCol, jahrCol, PreisCol, verkuafenCol,
				absenderAdresseCol);

		Button anlegenb = new Button("anlegen");
		anlegenb.getStyleClass().add("newstyle-button");

		Button Bearbeitenb = new Button("Bearbeiten");
		Bearbeitenb.getStyleClass().add("newstyle-button");

		leseBooks();
		tvbuch.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BuchFX>() {

			@Override
			public void changed(ObservableValue<? extends BuchFX> arg0, BuchFX arg1, BuchFX arg2) {

				if (arg2 != null) {
					Bearbeitenb.setDisable(false);
				} else {
					Bearbeitenb.setDisable(true);

				}
			}

		});

		anlegenb.setOnAction(e -> {
			BuchFX book = new BuchFX(new Buch(0, 0, "", "", "", 0, 0, true, ""));
			Optional<ButtonType> r = new BuchDetailDialog(book).showAndWait();
			if (r.isPresent() && r.get().getButtonData() == ButtonData.OK_DONE)
				leseBooks();

		});

		Bearbeitenb.setOnAction(e -> {
			Optional<ButtonType> r = new BuchDetailDialog(tvbuch.getSelectionModel().getSelectedItem()).showAndWait();
			if (r.isPresent() && r.get().getButtonData() == ButtonData.OK_DONE)
				leseBooks();

		});

		HBox hbbuch = new HBox(10, anlegenb, Bearbeitenb);
		hbbuch.setPadding(new Insets(10));
		VBox vbbuch = new VBox(10, tvbuch, hbbuch);
		buch.setContent(vbbuch);

		/**
		 * Im MITGLIEDLISTE _TAB  können die Mitarbeiter die Mitgliedliste ansehen und  bearbeiten.
		 * (Nach der Registrierung in der Mitgliedoberfläche werden die Mitglieddaten in der Datenbank gespeichert.
		 *  Mitarbeiter haben Zugriff auf Daten.)
		 */
		Tab kunde = new Tab("Mitgliedern");
		kunde.setClosable(false);
		kunde.getStyleClass().add("newstyle-tab");

		TableColumn<MitgliedFX, Integer> idCol = new TableColumn<>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("mitgliedid"));
		idCol.setMinWidth(100);
		TableColumn<MitgliedFX, String> vornameCol = new TableColumn<>("Vorname");
		vornameCol.setCellValueFactory(new PropertyValueFactory<>("vorname"));
		vornameCol.setMinWidth(100);
		TableColumn<MitgliedFX, String> FamiliennameCol = new TableColumn<>("Familienname");
		FamiliennameCol.setCellValueFactory(new PropertyValueFactory<>("familienname"));
		FamiliennameCol.setMinWidth(100);
		TableColumn<MitgliedFX, String> adresseCol = new TableColumn<>("Adresse");
		adresseCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
		adresseCol.setMinWidth(200);
		TableColumn<MitgliedFX, Integer> telefonCol = new TableColumn<>("Telefonnummer");
		telefonCol.setCellValueFactory(new PropertyValueFactory<>("telefonnummer"));
		telefonCol.setMinWidth(100);
		TableColumn<MitgliedFX, String> benutzernameCol = new TableColumn<>("Benutzername");
		benutzernameCol.setCellValueFactory(new PropertyValueFactory<>("benutzername"));
		benutzernameCol.setMinWidth(100);
		TableColumn<MitgliedFX, String> kennwortCol = new TableColumn<>("Kennwort");
		kennwortCol.setCellValueFactory(new PropertyValueFactory<>("kennwort"));
		kennwortCol.setMinWidth(100);

		TableView<MitgliedFX> tvkunde = new TableView<>(olMitglied);
		tvkunde.getColumns().addAll(idCol, vornameCol, FamiliennameCol, adresseCol, telefonCol, benutzernameCol,
				kennwortCol);
		tvkunde.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		Button Bearbeitenk = new Button("Bearbeiten");
		Bearbeitenk.getStyleClass().add("newstyle-button");
		

		leseMitglied();
		tvkunde.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MitgliedFX>() {

			@Override
			public void changed(ObservableValue<? extends MitgliedFX> arg0, MitgliedFX arg1, MitgliedFX arg2) {

				if (arg2 != null) {
					Bearbeitenk.setDisable(false);
				} else {
					Bearbeitenk.setDisable(true);

				}
			}

		});

		Bearbeitenk.setOnAction(e -> {
			Optional<ButtonType> r = new MitgliedDetailDialog(tvkunde.getSelectionModel().getSelectedItem())
					.showAndWait();
			if (r.isPresent() && r.get().getButtonData() == ButtonData.OK_DONE)
				leseMitglied();

		});

		HBox hbkunde = new HBox(10, Bearbeitenk);
		hbkunde.setPadding(new Insets(10));
		VBox vbkunde = new VBox(10, tvkunde, hbkunde);
		kunde.setContent(vbkunde);

		/**
		 *Im KAUFLISTE_TAB können die Mitarbeiter die BestellungsKaufliste ansehen. 
		 *  ( Nach erfolgter Buchung der Bücher in der Mitgliedoberfläche werden die Daten in der Datenbank gespeichert. )
		 */
		Tab kaufen = new Tab("Kaufliste");
		kaufen.setClosable(false);
		kaufen.getStyleClass().add("newstyle-tab");

		TableColumn<KaufenFX, Integer> kaufenidCol = new TableColumn<>("ID");
		kaufenidCol.setCellValueFactory(new PropertyValueFactory<>("kaufenId"));
		kaufenidCol.setMinWidth(100);
		TableColumn<KaufenFX, String> kaufenBuchTitleCol = new TableColumn<>("BuchTitle");
		kaufenBuchTitleCol.setCellValueFactory(new PropertyValueFactory<>("buchTitel"));
		kaufenBuchTitleCol.setMinWidth(200);
		TableColumn<KaufenFX, String> kaufenMitgliedFamilinameCol = new TableColumn<>("MitgliedFamilienname");
		kaufenMitgliedFamilinameCol.setCellValueFactory(new PropertyValueFactory<>("mitgliedFamilienname"));
		kaufenMitgliedFamilinameCol.setMinWidth(200);
		TableColumn<KaufenFX, String> kaufenAbsenderAdresseCol = new TableColumn<>("Absender Adresse");
		kaufenAbsenderAdresseCol.setCellValueFactory(new PropertyValueFactory<>("absenderAdresse"));
		kaufenAbsenderAdresseCol.setMinWidth(150);
		leseKaufen();
		TableView<KaufenFX> tvKaufen = new TableView<>(olKaufen);
		tvKaufen.getColumns().addAll(kaufenidCol, kaufenBuchTitleCol, kaufenMitgliedFamilinameCol,
				kaufenAbsenderAdresseCol);
		tvKaufen.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		kaufen.setContent(tvKaufen);

		/**
		 *Im LEIHLISTE_TAB können die Mitarbeiter die BestellungsLeihliste ansehen.   
		 *( Nach erfolgte Leihen der Bücher in der Mitgliedoberfläche werden die Daten in der Datenbank gespeichert. )
		 */
		Tab leihen = new Tab("Leihliste");
		leihen.setClosable(false);
		leihen.getStyleClass().add("newstyle-tab");

		TableColumn<LeihenFX, Integer> leihenidCol = new TableColumn<>("ID");
		leihenidCol.setCellValueFactory(new PropertyValueFactory<>("leihenId"));
		leihenidCol.setMinWidth(100);
		TableColumn<LeihenFX, String> leihenBuchTitleCol = new TableColumn<>("BuchTitle");
		leihenBuchTitleCol.setCellValueFactory(new PropertyValueFactory<>("buchTitel"));
		leihenBuchTitleCol.setMinWidth(200);
		TableColumn<LeihenFX, String> leihenMitgliedFamilinameCol = new TableColumn<>("MitgliedFamilienname");
		leihenMitgliedFamilinameCol.setCellValueFactory(new PropertyValueFactory<>("mitgliedFamilienname"));
		leihenMitgliedFamilinameCol.setMinWidth(200);
		TableColumn<LeihenFX, String> leihenFromCol = new TableColumn<>("Mietbeginn");
		leihenFromCol.setCellValueFactory(new PropertyValueFactory<>("from"));
		leihenFromCol.setMinWidth(100);
		TableColumn<LeihenFX, String> leihenToCol = new TableColumn<>("Mietende");
		leihenToCol.setCellValueFactory(new PropertyValueFactory<>("to"));
		leihenToCol.setMinWidth(100);
		TableColumn<LeihenFX, String> leihenAbsenderAdresseCol = new TableColumn<>("Absender Adresse");
		leihenAbsenderAdresseCol.setCellValueFactory(new PropertyValueFactory<>("absenderAdresse"));
		leihenAbsenderAdresseCol.setMinWidth(150);
		leseLeihen();
		TableView<LeihenFX> tvLeihen = new TableView<>(olLeihen);
		tvLeihen.getColumns().addAll(leihenidCol, leihenBuchTitleCol, leihenMitgliedFamilinameCol, leihenFromCol,
				leihenToCol, leihenAbsenderAdresseCol);
		tvLeihen.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		leihen.setContent(tvLeihen);

		/**
		 * Im ANZEIGEAUFGEBEN_TAB können die Mitarbeiter  PRIVAT-VERKAUF-VERLEIH Anzeigen ansehen.
		 *  ( Nach erfolgter Anzeige in der Mitgliedoberfläche werden die Daten in der Datenbank gespeichert. )
		 */
		Tab Anzeige = new Tab("AnzeigeAufgeben ");
		Anzeige.setClosable(false);
		Anzeige.getStyleClass().add("newstyle-tab");
		TableColumn<AnzeigeAufgebenFX, Integer> AnzeigeidCol = new TableColumn<>("Id");
		AnzeigeidCol.setCellValueFactory(new PropertyValueFactory<>("AnzeigeAufgebenId"));
		AnzeigeidCol.setMinWidth(80);
		TableColumn<AnzeigeAufgebenFX, String> AnzeigeFamiliennameCol = new TableColumn<>("Familienname");
		AnzeigeFamiliennameCol.setCellValueFactory(new PropertyValueFactory<>("AnzeigeAufgebenMitgliedFamilienname"));
		AnzeigeFamiliennameCol.setMinWidth(150);
		TableColumn<AnzeigeAufgebenFX, Integer> AnzeigeisbnCol = new TableColumn<>("Isbn");
		AnzeigeisbnCol.setCellValueFactory(new PropertyValueFactory<>("AnzeigeAufgebenBuchIsbn"));
		AnzeigeisbnCol.setMinWidth(80);
		TableColumn<AnzeigeAufgebenFX, String> AnzeigeautorCol = new TableColumn<>("Autor");
		AnzeigeautorCol.setCellValueFactory(new PropertyValueFactory<>("AnzeigeAufgebenBuchAutor"));
		AnzeigeautorCol.setMinWidth(150);
		TableColumn<AnzeigeAufgebenFX, String> AnzeigetitelCol = new TableColumn<>("BuchTitel");
		AnzeigetitelCol.setCellValueFactory(new PropertyValueFactory<>("AnzeigeAufgebenBuchTitle"));
		AnzeigetitelCol.setMinWidth(150);
		TableColumn<AnzeigeAufgebenFX, String> AnzeigethemaCol = new TableColumn<>("Thema");
		AnzeigethemaCol.setCellValueFactory(new PropertyValueFactory<>("AnzeigeAufgebenBuchThema"));
		AnzeigethemaCol.setMinWidth(100);
		TableColumn<AnzeigeAufgebenFX, Integer> AnzeigejahrCol = new TableColumn<>("Jahrschein");
		AnzeigejahrCol.setCellValueFactory(new PropertyValueFactory<>("AnzeigeAufgebenBuchJahr"));
		AnzeigejahrCol.setMinWidth(100);
		TableColumn<AnzeigeAufgebenFX, Double> AnzeigePreisCol = new TableColumn<>("Preis");
		AnzeigePreisCol.setCellValueFactory(new PropertyValueFactory<>("AnzeigeAufgebenBuchPries"));
		AnzeigePreisCol.setMinWidth(80);

		TableColumn<AnzeigeAufgebenFX, Boolean> AnzeigeVerkaufenCol = new TableColumn<>("Status");
		AnzeigeVerkaufenCol.setCellValueFactory(new PropertyValueFactory<>("AnzeigeAufgebenBuchVerkaufen"));
		AnzeigeVerkaufenCol.setMinWidth(80);

		TableColumn<AnzeigeAufgebenFX, String> AnzeigeAdresseCol = new TableColumn<>("Absender Adresse");
		AnzeigeAdresseCol.setCellValueFactory(new PropertyValueFactory<>("AnzeigeAufgebenBuchAbsenderAdresse"));
		AnzeigeAdresseCol.setMinWidth(150);

		leseAnzeige();
		TableView<AnzeigeAufgebenFX> tvAnzeige = new TableView<>(olAnzeige);
		tvAnzeige.getColumns().addAll(AnzeigeidCol, AnzeigeFamiliennameCol, AnzeigeisbnCol, AnzeigeautorCol,
				AnzeigetitelCol, AnzeigethemaCol, AnzeigejahrCol, AnzeigePreisCol, AnzeigeVerkaufenCol,
				AnzeigeAdresseCol);
		tvAnzeige.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		Anzeige.setContent(tvAnzeige);

		TabPane tp = new TabPane(buch, kunde, kaufen, leihen, Anzeige);
		VBox vb = new VBox(10, tp);

		Scene scene = new Scene(vb);
		scene.getStylesheets().add("VerwaltungApplication.css");
		Image icon = new Image(getClass().getResourceAsStream("BibliothekIcon.png"));
		primaryStage.getIcons().add(icon);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Verwaltung");
		primaryStage.show();
		primaryStage.setMaximized(true);
		Window.setWidth(primaryStage.getWidth());
		Window.setHeight(primaryStage.getHeight());

	}

	private void leseBooks() {
		try {
			ArrayList<Buch> lc = Datenbank.getBooks();
			olBuch.clear();
			for (Buch b : lc)
				olBuch.add(new BuchFX(b));

		} catch (SQLException e) {

			new Alert(AlertType.ERROR, e.toString()).showAndWait();
		}
	}

	private void leseMitglied() {
		try {
			ArrayList<Mitglied> lc = Datenbank.getMitglied();
			olMitglied.clear();
			for (Mitglied m : lc)
				olMitglied.add(new MitgliedFX(m));

		} catch (SQLException e) {

			new Alert(AlertType.ERROR, e.toString()).showAndWait();
		}
	}

	private void leseKaufen() {
		try {
			ArrayList<Kaufen> lc = Datenbank.getKaufen();
			olKaufen.clear();
			for (Kaufen k : lc)
				olKaufen.add(new KaufenFX(k));

		} catch (SQLException e) {

			new Alert(AlertType.ERROR, e.toString()).showAndWait();
		}
	}

	private void leseLeihen() {
		try {
			ArrayList<Leihen> lc = Datenbank.getLeihen();
			olLeihen.clear();
			for (Leihen l : lc)
				olLeihen.add(new LeihenFX(l));

		} catch (SQLException e) {

			new Alert(AlertType.ERROR, e.toString()).showAndWait();
		}
	}

	private void leseAnzeige() {
		try {
			ArrayList<AnzeigeAufgeben> lc = Datenbank.getAnzeigeAufgeben();
			olAnzeige.clear();
			for (AnzeigeAufgeben l : lc)
				olAnzeige.add(new AnzeigeAufgebenFX(l));

		} catch (SQLException e) {

			new Alert(AlertType.ERROR, e.toString()).showAndWait();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
