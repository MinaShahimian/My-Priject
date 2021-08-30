	package bibliothekProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import klasse.AnzeigeAufgeben;
import klasse.Buch;
import klasse.BuchFX;
import klasse.Datenbank;
import klasse.Kaufen;
import klasse.Leihen;
import klasse.Mitglied;
import klasse.MitgliedFX;

public class Bibliothek_Application extends Application {
	private ObservableList<BuchFX> olBucher = FXCollections.observableArrayList();

	Stage Window;
	Scene HauptScene, LoginScene, MitgliedScene, NeuMitgliedScene, BibliothekScene, Mitgliedschaft;

	private Mitglied mitglied;
	private MitgliedFX mitgliedfx;

	@Override
	public void start(Stage primaryStage) {
		Window = primaryStage;

		/**
		 * Im Hauptmenü befinden sich  3 Buttons. Jeder Button öffnet eine neue Seite. 

		 */
		Button Mitglied = new Button("Mitglied");
		Mitglied.getStyleClass().add("newstyle-button");
		Button neueMitglied = new Button("neue Mitglied");
		neueMitglied.getStyleClass().add("newstyle-button");
		Button Bibliothek = new Button("Bibliothek");
		Bibliothek.getStyleClass().add("newstyle-button");

		Mitglied.setOnAction(e -> {
			loginSceneerstellen();
			primaryStage.setScene(LoginScene);
		});
		neueMitglied.setOnAction(e -> {
			NeuMitgliedSceneerstellen();
			primaryStage.setScene(NeuMitgliedScene);
		});
		Bibliothek.setOnAction(e -> {
			BibliothekSceneerstellen();
			primaryStage.setScene(BibliothekScene);
		});

		HBox hbHaupt=new HBox(10,Mitglied,neueMitglied,Bibliothek);
		hbHaupt.setAlignment(Pos.BOTTOM_RIGHT);
		/**
		 * Fußzeile
		 */
		Label lblAdresse = new Label("Kontakt: ParagonStraße 5/2");
		
		Label lblTelefonnummer = new Label("Tel: +43 1 967567");
		
		Label lblEmail = new Label("E-Mail: Mina.shahimian@gmail.com ");
		

		VBox vbHaupt=new VBox(10,lblAdresse,lblTelefonnummer,lblEmail);

		
		BorderPane bpHauptScene=new BorderPane();

		bpHauptScene.setBottom(vbHaupt);

		StackPane spHauptScene = new StackPane(bpHauptScene,hbHaupt);
		spHauptScene.setPadding(new Insets(50));
		
		HauptScene = new Scene(spHauptScene);
		HauptScene.getStylesheets().add("HauptScene.css");
		primaryStage.setScene(HauptScene);

		Image icon = new Image(getClass().getResourceAsStream("BibliothekIcon.png"));
		primaryStage.getIcons().add(icon);
		primaryStage.setMaximized(true);
		primaryStage.setTitle("Bibliothek");
		primaryStage.show();
		Window.setWidth(primaryStage.getWidth());
		Window.setHeight(primaryStage.getHeight());
	}
/**
 * Mitglied Button führt zum Login-Bereich. Da kann man sich einloggen.
 */
	private void loginSceneerstellen() {

		Label lblBenutzername = new Label("Benutzername: ");
		
		TextField txtBenutzername = new TextField();
		txtBenutzername.setPromptText("Benutzername");

		Label lblKennwort = new Label("Kennwort: ");
	
		TextField txtKennwort = new TextField();
		txtKennwort.setPromptText("Kennwort");

		Button login = new Button("Login");
		login.getStyleClass().add("newstyle-button");

		login.setOnAction(e -> {
			MitgliedSceneerstellen();
			Window.setScene(MitgliedScene);
		});

		/**
		 * Fehlermeldung für die Textfielder.Textfielder müss ausgefüllt werden.
		 */

		login.addEventFilter(ActionEvent.ACTION, e -> {
			if (txtBenutzername.getText() == null || txtBenutzername.getText().length() == 0) {
				new Alert(AlertType.ERROR, "Ihre Benutzername eingeben").showAndWait();
				e.consume();
				return;
			}
			if (txtKennwort.getText() == null || txtKennwort.getText().length() == 0) {
				new Alert(AlertType.ERROR, "Ihre Kennwort eingeben").showAndWait();
				e.consume();
				return;
			}
			try {
				ArrayList<Mitglied> al = Datenbank.getMitglied(txtBenutzername.getText(), txtKennwort.getText());
				if (al.size() == 0) {
					new Alert(AlertType.ERROR, "Ihre benutzername oder Kennwort ist falsch").showAndWait();
					e.consume();
					return;
				}
				mitglied = al.get(0);
				mitgliedfx = new MitgliedFX(mitglied);
			} catch (SQLException e1) {
				new Alert(AlertType.ERROR, e1.toString()).showAndWait();
				e.consume();
				return;
			}

		});

		Button zurückloginScene = new Button("Zurück");
		zurückloginScene.getStyleClass().add("newstyle-button");
		zurückloginScene.setOnAction(e -> {
			Window.setScene(HauptScene);

		});

		GridPane gploginScene = new GridPane();
		gploginScene.setPadding(new Insets(10));
		gploginScene.setHgap(10);
		gploginScene.setVgap(10);
		gploginScene.add(lblBenutzername, 0, 0);
		gploginScene.add(txtBenutzername, 1, 0, 2, 1);
		gploginScene.add(lblKennwort, 0, 1);
		gploginScene.add(txtKennwort, 1, 1, 2, 1);
		gploginScene.add(login, 1, 3);
		gploginScene.add(zurückloginScene, 2, 3);
		
		BorderPane bpLogin=new BorderPane();
		bpLogin.setCenter(gploginScene);
		BorderPane.setMargin(gploginScene, new Insets(300,300,400,400));
		
		StackPane stloginScene = new StackPane(bpLogin);

		LoginScene = new Scene(stloginScene);
		LoginScene.getStylesheets().add("LoginScene.css");

	}
/**
 *  Mitglieder können nach dem Einloggen Bücher kaufen, leihen und eine Privat-Anzeige schalten.
 */
	private void MitgliedSceneerstellen() {
		Label lblName = new Label("Hallo " + mitglied.getBenutzername() + " !");

		/**
		 * Tab (BUCH-KAUFEN)  hier sind alle Bücher nach dem Thema geordnet. Man kann Bücher auswählen, im Warenkorb hinzufügen und bezahlen. 
		 */
		Tab mitgliedkaufen = new Tab("Einkaufen");
		mitgliedkaufen.setClosable(false);
		mitgliedkaufen.getStyleClass().add("newstyle-tab");

		Accordion accMitgliedKaufen = new Accordion(TitelPaneKaufenerstellen("Sprache", Optional.of(Boolean.TRUE)),
				TitelPaneKaufenerstellen("Poem", Optional.of(Boolean.TRUE)),
				TitelPaneKaufenerstellen("Music", Optional.of(Boolean.TRUE)),
				TitelPaneKaufenerstellen("Wissenschaft", Optional.of(Boolean.TRUE)),
				TitelPaneKaufenerstellen("Geschichte", Optional.of(Boolean.TRUE)),
				TitelPaneKaufenerstellen("Literatur", Optional.of(Boolean.TRUE)));
		accMitgliedKaufen.getStyleClass().add("newstyle-accordion");
		accMitgliedKaufen.setPrefSize(850, 400);

		Button zurückmitgliedkaufen = new Button("Zurück");
		zurückmitgliedkaufen.getStyleClass().add("newstyle-button");
		zurückmitgliedkaufen.setOnAction(e -> {
			Window.setScene(HauptScene);

		});

		
		Label lblKaufenbedingung1 = new Label(
				"Anmerkung1: Alle gekauften Bücher Von Online-Bibliothek werden per Post zugesendet.");
		
		Label lblKaufenbedingung2 = new Label(
				"bedingung2: Die Abwicklung des Privat-Verleih´s ist zwischen den handelnden Personen zu vereinbaren. ");
		
		VBox vbKaufenbedingung = new VBox(10, lblKaufenbedingung1, lblKaufenbedingung2);
		VBox vbmitgliedkaufen = new VBox(10, accMitgliedKaufen, zurückmitgliedkaufen, vbKaufenbedingung);
		vbmitgliedkaufen.setPadding(new Insets(5));

		mitgliedkaufen.setContent(vbmitgliedkaufen);

		/**
		 * Tab (BUCH-LEIHEN) kann man Bücher auswählen, das Ausleihdatum eintragen, im Warenkorb hinzufügen und leihen. 
		 */
		Tab mitgliedleihen = new Tab("leihen");
		mitgliedleihen.getStyleClass().add("newstyle-tab");
		mitgliedleihen.setClosable(false);

		Accordion accMitgliedLeihen = new Accordion(TitelPaneLeihenerstellen("Sprache", Optional.of(Boolean.FALSE)),
				TitelPaneLeihenerstellen("Poem", Optional.of(Boolean.FALSE)),
				TitelPaneLeihenerstellen("Music", Optional.of(Boolean.FALSE)),
				TitelPaneLeihenerstellen("Wissenschaft", Optional.of(Boolean.FALSE)),
				TitelPaneLeihenerstellen("Geschichte", Optional.of(Boolean.FALSE)),
				TitelPaneLeihenerstellen("Literatur", Optional.of(Boolean.FALSE)));
		accMitgliedLeihen.setPrefSize(850, 400);
		accMitgliedLeihen.getStyleClass().add("newstyle-accordion");

		Button zurückmitgliedleihen = new Button("Zurück");
		zurückmitgliedleihen.getStyleClass().add("newstyle-button");
		zurückmitgliedleihen.setOnAction(e -> {
			Window.setScene(HauptScene);

		});

		Label lblLeihenbedingung1 = new Label(
				"Anmerkung1: Alle geliehenen OB-Bücher, werden per Post zugesendet und müssen am Ende der Verleihzeit zurückgesendet werden.");
	
		Label lblLeihenbedingung2 = new Label(
				"Anmerkung2: Die Abwicklung des Privat-Verleih´s ist zwischen den handelnden Personen zu vereinbaren.");
		
		VBox vbLeihenbedingung = new VBox(10, lblLeihenbedingung1, lblLeihenbedingung2);

		VBox vbmitgliedleihen = new VBox(10, accMitgliedLeihen, zurückmitgliedleihen, vbLeihenbedingung);
		vbmitgliedleihen.setPadding(new Insets(5));

		mitgliedleihen.setContent(vbmitgliedleihen);

		/**
		 *Tab (PRIVAT-VERKAUF-VERLEIH) ist Anzeige-Bereich. 
		 *Hier können Mitglieder als Verkäufer eine Anzeige schalten und so eigene Bücher verkaufen oder verleihen.
		 * Wenn man da ein Buch als Anzeige aufgeben möchte, muss man alle Details eingeben. 
		 */
		Tab mitgliedanzeige = new Tab("Anzeige aufgeben");
		mitgliedanzeige.setClosable(false);
		mitgliedanzeige.getStyleClass().add("newstyle-tab");

		Label lblisbn = new Label("ISBN: ");
		TextField txtisbn = new TextField();
		Label lblautor = new Label("Autor: ");
		TextField txtautor = new TextField();
		Label lbltitel = new Label("Titel: ");
		TextField txttitel = new TextField();
		Label lblthema = new Label("Thema: ");
		ComboBox<String> cbThema = new ComboBox<>();
		cbThema.setPrefWidth(200);
		Label lbljahr = new Label("Jahrschein: ");
		TextField txtjahr = new TextField();
		Label lbladresse = new Label("Absender Adresse: ");
		TextField txtadresse = new TextField();

		ToggleGroup tganzeige = new ToggleGroup();
		RadioButton rbverkaufen = new RadioButton("verkaufen");
		rbverkaufen.setToggleGroup(tganzeige);

		RadioButton rbverleihen = new RadioButton("verleihen");
		rbverleihen.setToggleGroup(tganzeige);

		Label lblpreis = new Label("Preis: ");
		lblpreis.setDisable(true);
		TextField txtpreis = new TextField();
		txtpreis.setDisable(true);

		/**
		 * Thema auswählen:
		 */
		ArrayList<String> themen;
		try {
			themen = Datenbank.leseThemen();
		} catch (SQLException e) {

			new Alert(AlertType.ERROR, e.toString()).showAndWait();
			themen = new ArrayList<>();

		}
		cbThema.setItems(FXCollections.observableArrayList(themen));

		/**
		 * RadioButton auswählen:
		 */
		tganzeige.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				if (rbverkaufen.isSelected()) {
					lblpreis.setDisable(false);
					txtpreis.setDisable(false);

				} else {
					lblpreis.setDisable(true);
					txtpreis.setDisable(true);
				}
				if (rbverleihen.isSelected()) {
					lblpreis.setDisable(true);
					txtpreis.setDisable(true);

				}
			}

		});
		Button bmitgliedanzeige = new Button("Teilen");
		bmitgliedanzeige.getStyleClass().add("newstyle-button");

		/**
		 * Fehlermeldung für die Textfielder. Textfielder müss ausgefüllt werden.
		 */
		bmitgliedanzeige.setOnAction(e -> {
			if (txtisbn.getText() == null || txtisbn.getText().length() == 0) {
				new Alert(AlertType.ERROR, "Buch ISBN eingeben").showAndWait();
				e.consume();
				return;
			}

			if (cbThema.getSelectionModel().getSelectedIndex()<0) {
				new Alert(AlertType.ERROR, "Buch Thema auswählen").showAndWait();
				e.consume();
				return;
			}
			if (txttitel.getText() == null || txttitel.getText().length() == 0) {
				new Alert(AlertType.ERROR, "Buch Title eingeben").showAndWait();
				e.consume();
				return;
			}
			if (txtautor.getText() == null || txtautor.getText().length() == 0) {
				new Alert(AlertType.ERROR, "Buch Autor eingeben").showAndWait();
				e.consume();
				return;
			}
			if (txtjahr.getText() == null || txtjahr.getText().length() == 0) {
				new Alert(AlertType.ERROR, "Buch Jahrschein eingeben").showAndWait();
				e.consume();
				return;
			}
			if (txtadresse.getText() == null || txtadresse.getText().length() == 0) {
				new Alert(AlertType.ERROR, "Absender Adresse eingeben").showAndWait();
				e.consume();
				return;
			}
			if (!rbverkaufen.isSelected()&&!rbverleihen.isSelected()) {
				new Alert(AlertType.ERROR, "Status auswählen").showAndWait();
				e.consume();
				return;
			}
			if (rbverkaufen.isSelected()&&(txtpreis.getText() == null || txtpreis.getText().length() == 0)) {
				new Alert(AlertType.ERROR, "Buch Preis eingeben").showAndWait();
				e.consume();
				return;
			}

			new Alert(AlertType.INFORMATION, "Ihre Anzeige wurde aufgegeben.").showAndWait();

			try {
				Buch b = new Buch(0, Integer.parseInt(txtisbn.getText()), txtautor.getText(), txttitel.getText(),
						cbThema.getSelectionModel().getSelectedItem(), Integer.parseInt(txtjahr.getText()),
						rbverkaufen.isSelected() ? Integer.parseInt(txtpreis.getText()) : 0, rbverkaufen.isSelected(),
						txtadresse.getText());

				Datenbank.insertBook(b);
				Datenbank.insertAnzeige(new AnzeigeAufgeben(0, b, mitglied));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		});
		Button zurückmitgliedanzeige = new Button("Zurück");
		zurückmitgliedanzeige.getStyleClass().add("newstyle-button");
		zurückmitgliedanzeige.setOnAction(e -> {
			Window.setScene(HauptScene);

		});

		GridPane gpanzeige = new GridPane();
		gpanzeige.setHgap(10);
		gpanzeige.setVgap(10);
		gpanzeige.add(lblisbn, 0, 0);
		gpanzeige.add(txtisbn, 1, 0);
		gpanzeige.add(lblthema, 0, 1);
		gpanzeige.add(cbThema, 1, 1);
		gpanzeige.add(lbltitel, 0, 2);
		gpanzeige.add(txttitel, 1, 2);
		gpanzeige.add(lblautor, 0, 3);
		gpanzeige.add(txtautor, 1, 3);
		gpanzeige.add(lbljahr, 0, 4);
		gpanzeige.add(txtjahr, 1, 4);
		gpanzeige.add(lbladresse, 0, 5);
		gpanzeige.add(txtadresse, 1, 5);
		gpanzeige.add(rbverkaufen, 0, 6);
		gpanzeige.add(rbverleihen, 1, 6);
		gpanzeige.add(lblpreis, 0, 7);
		gpanzeige.add(txtpreis, 1, 7);

		gpanzeige.setPadding(new Insets(5));
		gpanzeige.getStyleClass().add("tab-label");

		HBox hbmitgliedanzeige = new HBox(10, gpanzeige);
		HBox hbButton = new HBox(10, bmitgliedanzeige, zurückmitgliedanzeige);
		VBox vbmitgliedanzeige = new VBox(10, hbmitgliedanzeige, hbButton);
		vbmitgliedanzeige.setPadding(new Insets(5));
		mitgliedanzeige.setContent(vbmitgliedanzeige);

		TabPane tpMitgliedScene = new TabPane(mitgliedkaufen, mitgliedleihen, mitgliedanzeige);
		VBox vbMitgliedScene = new VBox(10, lblName, tpMitgliedScene);
		StackPane stMitgliedScene = new StackPane(vbMitgliedScene);
		MitgliedScene = new Scene(stMitgliedScene);
		MitgliedScene.getStylesheets().add("MitgliedScene.css");
	}

	/**
	 * Neue Mitglied Button führt zum Mitgliedschafts-Bereich. Dort kann man sich als NEUKUNDE  anmelden.

	 */
	private void NeuMitgliedSceneerstellen() {

		Label lblNeuMitglied = new Label("Geben Sie bitte Ihre Daten ein.");
		
		Label lblname = new Label("Vorname: ");
		TextField txtname = new TextField();

		Label lblfamili = new Label("Familienname: ");
		TextField txtfamili = new TextField();
		txtfamili.setPrefColumnCount(40);

		Label lblAdrs = new Label("Adresse: ");
		TextField txtAdrs = new TextField();

		Label lblTel = new Label("Telefonnummer: ");
		TextField txtTel = new TextField();

		Button speichern = new Button("speichern");
		speichern.getStyleClass().add("newstyle-button");

		speichern.setOnAction(e -> {

			mitglied = new Mitglied(0, txtname.getText(), txtfamili.getText(), txtAdrs.getText(), txtTel.getText(),
					txtname.getText(), txtTel.getText());
			try {
				Datenbank.insertMitglied(mitglied);
				mitgliedfx = new MitgliedFX(mitglied);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			MitgliedschaftSceneerstellen();
			Window.setScene(Mitgliedschaft);
		});

		/**
		 * Fehlermeldung für die Textfielder.Textfielder müss ausgefüllt werden.
		 */

		speichern.addEventFilter(ActionEvent.ACTION, e -> {
			if (txtname.getText() == null || txtname.getText().length() == 0) {
				new Alert(AlertType.ERROR, "Ihre Vorname eingeben").showAndWait();
				e.consume();
				return;
			}
			if (txtfamili.getText() == null || txtfamili.getText().length() == 0) {
				new Alert(AlertType.ERROR, "Ihre Familienname eingeben").showAndWait();
				e.consume();
				return;
			}
			if (txtAdrs.getText() == null || txtAdrs.getText().length() == 0) {
				new Alert(AlertType.ERROR, "Ihre Adresse eingeben").showAndWait();
				e.consume();
				return;
			}
			if (txtTel.getText() == null || txtTel.getText().length() == 0) {
				new Alert(AlertType.ERROR, "Ihres Telefonnummer eingeben").showAndWait();
				e.consume();
				return;
			}

		});

		Button zurückNeuMitglied = new Button("Zurück");
		zurückNeuMitglied.setOnAction(e -> {
			Window.setScene(HauptScene);

		});
		zurückNeuMitglied.getStyleClass().add("newstyle-button");

		GridPane gpNeuMitglied = new GridPane();
		gpNeuMitglied.setVgap(10);
		gpNeuMitglied.setHgap(10);
		gpNeuMitglied.add(lblNeuMitglied, 0, 0, 2, 1);
		gpNeuMitglied.add(lblname, 0, 2);
		gpNeuMitglied.add(txtname, 1, 2, 2, 1);
		gpNeuMitglied.add(lblfamili, 0, 3);
		gpNeuMitglied.add(txtfamili, 1, 3, 2, 1);
		gpNeuMitglied.add(lblAdrs, 0, 4);
		gpNeuMitglied.add(txtAdrs, 1, 4, 2, 1);
		gpNeuMitglied.add(lblTel, 0, 5);
		gpNeuMitglied.add(txtTel, 1, 5, 2, 1);
		gpNeuMitglied.add(speichern, 1, 6);
		gpNeuMitglied.add(zurückNeuMitglied, 2, 6);
		gpNeuMitglied.setPadding(new Insets(5));

		BorderPane bpNeuMitglied=new BorderPane();
		bpNeuMitglied.setCenter(gpNeuMitglied);
		BorderPane.setMargin(gpNeuMitglied, new Insets(200,200,400,400));
		
		StackPane stNeuMitglied = new StackPane(bpNeuMitglied);
		NeuMitgliedScene = new Scene(stNeuMitglied);
		NeuMitgliedScene.getStylesheets().add("NeuMitgliedScene.css");

	}

	/**
	 * Die Mitglieder können Ihre Benutzername und Kennwort ansehen.
	 */
	private void MitgliedschaftSceneerstellen() {

		Label benutzer = new Label("Ihre Benutzername ist " + mitgliedfx.getBenutzername());
		Label kennwort = new Label("Ihre Kennwort ist  " + mitgliedfx.getKennwort());

		Button loginMitgliedschaft = new Button("Login");
		loginMitgliedschaft.getStyleClass().add("newstyle-button");
		loginMitgliedschaft.setOnAction(e -> {
			loginSceneerstellen();
			Window.setScene(LoginScene);

		});
		
		GridPane gpMitgliedSchaft = new GridPane();
		gpMitgliedSchaft.setPadding(new Insets(10));
		gpMitgliedSchaft.setHgap(10);
		gpMitgliedSchaft.setVgap(10);
		gpMitgliedSchaft.add(benutzer, 0, 0);
		gpMitgliedSchaft.add(kennwort,0, 1);
		gpMitgliedSchaft.add(loginMitgliedschaft, 0, 2);
		
		BorderPane bpMitgliedSchaft =new BorderPane();
		bpMitgliedSchaft .setCenter(gpMitgliedSchaft);
		BorderPane.setMargin(gpMitgliedSchaft, new Insets(300,300,400,400));
		
		StackPane stMitgliedschaft = new StackPane(bpMitgliedSchaft);
		Mitgliedschaft = new Scene(stMitgliedschaft);
		Mitgliedschaft.getStylesheets().add("Mitgliedschaft.css");

	}

	/**
	 * Bibliothek-Button listet alle Bücher. Für Besucher, ohne NEUKUNDENREGRISTRIERUNG, 
	 * ist die BUCH-KAUFEN, BUCH-LEIHEN und BUCH-ANZEIGEAUFGEBEN Funktion deaktiviert. 
	 */
	private void BibliothekSceneerstellen() {

		/**
		 * Kaufen Bibliothek
		 */
		Tab einkaufen = new Tab("Einkaufen");
		einkaufen.setClosable(false);
		einkaufen.getStyleClass().add("newstyle-tab");

		Accordion accBibliothekKaufen = new Accordion(
				TitelPaneBibliothekerstellen("Sprache", Optional.of(Boolean.TRUE)),
				TitelPaneBibliothekerstellen("Poem", Optional.of(Boolean.TRUE)),
				TitelPaneBibliothekerstellen("Music", Optional.of(Boolean.TRUE)),
				TitelPaneBibliothekerstellen("Wissenschaft", Optional.of(Boolean.TRUE)),
				TitelPaneBibliothekerstellen("Geschichte", Optional.of(Boolean.TRUE)),
				TitelPaneBibliothekerstellen("Literatur", Optional.of(Boolean.TRUE)));
		accBibliothekKaufen.setPrefSize(850, 400);
		accBibliothekKaufen.getStyleClass().add("newstyle-accordion");

		Button bKaufen = new Button("Kaufen");
		bKaufen.setPadding(new Insets(5));
		bKaufen.getStyleClass().add("newstyle-button");
		bKaufen.setOnAction(e -> {
			new Alert(AlertType.INFORMATION, "Diser Breich ist verfügber nur für Mitglieder.").showAndWait();

		});

		Button zurückKaufen = new Button("Zurück");
		zurückKaufen.setOnAction(e -> {
			Window.setScene(HauptScene);

		});
		zurückKaufen.setAlignment(Pos.CENTER);
		zurückKaufen.setPadding(new Insets(5));
		zurückKaufen.getStyleClass().add("newstyle-button");

		HBox hbkaufen = new HBox(10, bKaufen, zurückKaufen);
		VBox vbkaufen = new VBox(10, accBibliothekKaufen, hbkaufen);
		vbkaufen.setPadding(new Insets(5));
		einkaufen.setContent(vbkaufen);

		/**
		 * Leihen Bibliothek
		 */
		Tab leihen = new Tab("leihen");
		leihen.setClosable(false);
		leihen.getStyleClass().add("newstyle-tab");

		Accordion accBibliothekLeihen = new Accordion(
				TitelPaneBibliothekerstellen("Sprache", Optional.of(Boolean.FALSE)),
				TitelPaneBibliothekerstellen("Poem", Optional.of(Boolean.FALSE)),
				TitelPaneBibliothekerstellen("Music", Optional.of(Boolean.FALSE)),
				TitelPaneBibliothekerstellen("Wissenschaft", Optional.of(Boolean.FALSE)),
				TitelPaneBibliothekerstellen("Geschichte", Optional.of(Boolean.FALSE)),
				TitelPaneBibliothekerstellen("Literatur", Optional.of(Boolean.FALSE)));
		accBibliothekLeihen.setPrefSize(850, 400);
		accBibliothekLeihen.getStyleClass().add("newstyle-accordion");

		Button bleihen = new Button("Leihen");
		bleihen.setPadding(new Insets(5));
		bleihen.getStyleClass().add("newstyle-button");

		bleihen.setOnAction(e -> {
			new Alert(AlertType.INFORMATION, "Diser Breich ist verfügber nur für Mitglieder.").showAndWait();

		});
		Button zurückLeihen = new Button("Zurück");
		zurückLeihen.setPadding(new Insets(5));
		zurückLeihen.getStyleClass().add("newstyle-button");
		zurückLeihen.setAlignment(Pos.CENTER);

		zurückLeihen.setOnAction(e -> {
			Window.setScene(HauptScene);

		});
		HBox hbLeihen = new HBox(10, bleihen, zurückLeihen);
		VBox vbleihen = new VBox(10, accBibliothekLeihen, hbLeihen);
		vbleihen.setPadding(new Insets(5));

		leihen.setContent(vbleihen);

		/**
		 * Anzeige Bibliothek
		 */
		Tab anzeige = new Tab("Anzeige aufgeben");
		anzeige.setClosable(false);
		anzeige.getStyleClass().add("newstyle-tab");

		Label lblisbn = new Label("ISBN: ");
		TextField txtisbn = new TextField();
		Label lblautor = new Label("Autor: ");
		TextField txtautor = new TextField();
		Label lbltitel = new Label("Titel: ");
		TextField txttitel = new TextField();
		Label lblthema = new Label("Thema: ");
		ComboBox<String> cbThema = new ComboBox<>();
		cbThema.setPrefWidth(200);
		

		Label lbljahr = new Label("Jahrschein: ");
		TextField txtjahr = new TextField();
		Label lbladresse = new Label("Absender Adresse: ");
		TextField txtadresse = new TextField();

		ToggleGroup tganzeige = new ToggleGroup();
		RadioButton rbverkaufen = new RadioButton("verkaufen");
		rbverkaufen.setToggleGroup(tganzeige);
		RadioButton rbverleihen = new RadioButton("verleihen");
		rbverleihen.setToggleGroup(tganzeige);
		rbverleihen.setFont(new Font("Times new Roman",20));

		Label lblpreis = new Label("Preis: ");
		lblpreis.setDisable(true);
		TextField txtpreis = new TextField();
		txtpreis.setDisable(true);

		/**
		 * Thema auswählen:
		 */
		ArrayList<String> themen;
		try {
			themen = Datenbank.leseThemen();
		} catch (SQLException e) {

			new Alert(AlertType.ERROR, e.toString()).showAndWait();
			themen = new ArrayList<>();

		}
		cbThema.setItems(FXCollections.observableArrayList(themen));

		/**
		 * RadioButton auswählen:
		 */
		tganzeige.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				if (rbverkaufen.isSelected()) {
					lblpreis.setDisable(false);
					txtpreis.setDisable(false);

				} else {
					lblpreis.setDisable(true);
					txtpreis.setDisable(true);
				}
				if (rbverleihen.isSelected()) {
					lblpreis.setDisable(true);
					txtpreis.setDisable(true);

				}
			}

		});

		Button bBibliothekanzeige = new Button("Teilen");
		bBibliothekanzeige.setPadding(new Insets(5));
		bBibliothekanzeige.getStyleClass().add("newstyle-button");

		bBibliothekanzeige.setOnAction(e -> {
			new Alert(AlertType.INFORMATION, "Diser Breich ist verfügber nur für Mitglieder.").showAndWait();

		});
		Button zurückAnzeige = new Button("Zurück");
		zurückAnzeige.setPadding(new Insets(5));
		zurückAnzeige.getStyleClass().add("newstyle-button");

		zurückAnzeige.setOnAction(e -> {
			Window.setScene(HauptScene);

		});
		zurückAnzeige.setAlignment(Pos.CENTER);

		GridPane gpanzeigebib = new GridPane();
		gpanzeigebib.setVgap(10);
		gpanzeigebib.setHgap(10);

		gpanzeigebib.setHgap(10);
		gpanzeigebib.setVgap(10);
		gpanzeigebib.add(lblisbn, 0, 0);
		gpanzeigebib.add(txtisbn, 1, 0);
		gpanzeigebib.add(lblthema, 0, 1);
		gpanzeigebib.add(cbThema, 1, 1);
		gpanzeigebib.add(lbltitel, 0, 2);
		gpanzeigebib.add(txttitel, 1, 2);
		gpanzeigebib.add(lblautor, 0, 3);
		gpanzeigebib.add(txtautor, 1, 3);
		gpanzeigebib.add(lbljahr, 0, 4);
		gpanzeigebib.add(txtjahr, 1, 4);
		gpanzeigebib.add(lbladresse, 0, 5);
		gpanzeigebib.add(txtadresse, 1, 5);
		gpanzeigebib.add(rbverkaufen, 0, 6);
		gpanzeigebib.add(rbverleihen, 1, 6);
		gpanzeigebib.add(lblpreis, 0, 7);
		gpanzeigebib.add(txtpreis, 1, 7);

		gpanzeigebib.setPadding(new Insets(5));

		HBox hbgp = new HBox(10, gpanzeigebib);
		HBox hbanzeige = new HBox(10, bBibliothekanzeige, zurückAnzeige);

		VBox vbAnzeige = new VBox(10, hbgp, hbanzeige);

		anzeige.setContent(vbAnzeige);

		TabPane tpBibliothek = new TabPane(einkaufen, leihen, anzeige);

		VBox vbBibliothek = new VBox(10, tpBibliothek);

		StackPane stBibliothek = new StackPane(vbBibliothek);

		BibliothekScene = new Scene(stBibliothek);

		BibliothekScene.getStylesheets().add("BibliothekScene.css");

	}

	private void leseBooks(String thema, Optional<Boolean> verkaufstatus) {
		try {
			ArrayList<Buch> lc = Datenbank.getBooks(thema, verkaufstatus);
			olBucher = FXCollections.observableArrayList();
			for (Buch b : lc)
				olBucher.add(new BuchFX(b));

		} catch (SQLException e) {

			new Alert(AlertType.ERROR, e.toString()).showAndWait();
		}
	}

	/**
	 * DatePicker für die Ausleihzeit
	 *
	 */
	class DateCellFactory extends DateCell {
		DatePicker dp;
		boolean bis;

		public DateCellFactory(DatePicker dp, boolean bis) {
			this.dp = dp;
			this.bis = bis;
		}

		@Override
		public void updateItem(LocalDate item, boolean empty) {
			super.updateItem(item, empty);
			if (!empty && item != null) {
				// Disable all future date cells
				if (item.isBefore(!bis ? dp.getValue() : dp.getValue().plusDays(1)))
					this.setDisable(true);
				// Show Weekends in red color
				DayOfWeek day = DayOfWeek.from(item);
				if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY)
					this.setTextFill(Color.RED);
			} else {
				this.setText(null);
				this.setGraphic(null);
			}
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * TitledPaneKaufen_Bereich 2Button: Warenkorb && zurkassa
	 */
	private TitledPane TitelPaneKaufenerstellen(String thema, Optional<Boolean> verkaufstatus) {

		TableColumn<BuchFX, String> titelCol = new TableColumn<>("BuchTitel");
		titelCol.setCellValueFactory(new PropertyValueFactory<>("titel"));
		titelCol.setMinWidth(250);

		TableColumn<BuchFX, String> autorCol = new TableColumn<>("Autor");
		autorCol.setCellValueFactory(new PropertyValueFactory<>("autor"));
		autorCol.setMinWidth(200);

		TableColumn<BuchFX, Integer> jahrCol = new TableColumn<>("Jahrschein");
		jahrCol.setCellValueFactory(new PropertyValueFactory<>("jahr"));
		jahrCol.setMinWidth(100);

		TableColumn<BuchFX, Integer> PreisCol = new TableColumn<>("Preis");
		PreisCol.setCellValueFactory(new PropertyValueFactory<>("preis"));
		PreisCol.setMinWidth(80);
		TableColumn<BuchFX, String> adresseCol = new TableColumn<>("Absender Adresse");
		adresseCol.setCellValueFactory(new PropertyValueFactory<>("absenderAdresse"));
		adresseCol.setMinWidth(200);

		leseBooks(thema, Optional.of(Boolean.TRUE));
		TableView<BuchFX> tvKaufen = new TableView<>(olBucher);
		tvKaufen.getColumns().addAll(titelCol, autorCol, jahrCol, PreisCol, adresseCol);
		tvKaufen.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		Button bKaufenwarenkorb = new Button("In den warenkorb");
		bKaufenwarenkorb.setDisable(true);
		bKaufenwarenkorb.getStyleClass().add("newstyle-button");

		Button bKasse = new Button("Zur Kasse");
		bKasse.setDisable(true);
		bKasse.getStyleClass().add("newstyle-button");

		tvKaufen.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BuchFX>() {
			@Override
			public void changed(ObservableValue<? extends BuchFX> arg0, BuchFX arg1, BuchFX arg2) {

				if (mitglied != null)
					bKaufenwarenkorb.setDisable(false);

			}
		});

		/**
		 * Button Korb
		 */
		bKaufenwarenkorb.setOnAction(e -> {
			for (BuchFX b : tvKaufen.getSelectionModel().getSelectedItems())
				mitgliedfx.getBestellungKaufen().add(b);
			bKasse.setDisable(false);

		});

		/**
		 * Button kassa
		 */
		bKasse.setOnAction(e -> {
			new BestellungKaufenDetailDialog(mitgliedfx).showAndWait();
		});
		HBox hbKaufen = new HBox(10, bKaufenwarenkorb, bKasse);
		VBox vbKaufen = new VBox(10, tvKaufen, hbKaufen);

		return new TitledPane(thema, vbKaufen);
	}

	/**
	 * TitledPaneLeihen_Bereich 2Button: Warenkorb && zurkassa DatePicker zum Datum
	 * auswählen
	 */
	private TitledPane TitelPaneLeihenerstellen(String thema, Optional<Boolean> verkaufstatus) {

		TableColumn<BuchFX, String> titelCol = new TableColumn<>("BuchTitel");
		titelCol.setCellValueFactory(new PropertyValueFactory<>("titel"));
		titelCol.setMinWidth(250);

		TableColumn<BuchFX, String> autorCol = new TableColumn<>("Autor");
		autorCol.setCellValueFactory(new PropertyValueFactory<>("autor"));
		autorCol.setMinWidth(200);

		TableColumn<BuchFX, Integer> jahrCol = new TableColumn<>("Jahrschein");
		jahrCol.setCellValueFactory(new PropertyValueFactory<>("jahr"));
		jahrCol.setMinWidth(100);
		TableColumn<BuchFX, String> adresseCol = new TableColumn<>("Absender Adresse");
		adresseCol.setCellValueFactory(new PropertyValueFactory<>("absenderAdresse"));
		adresseCol.setMinWidth(200);

		leseBooks(thema, Optional.of(Boolean.FALSE));
		TableView<BuchFX> tvLeihen = new TableView<>(olBucher);
		tvLeihen.getColumns().addAll(titelCol, autorCol, jahrCol, adresseCol);
		tvLeihen.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		Button bLeihenwarenkorb = new Button("In den warenkorb");
		bLeihenwarenkorb.setDisable(true);
		bLeihenwarenkorb.getStyleClass().add("newstyle-button");

		Button bleihen = new Button("Leihen");
		bleihen.setDisable(true);
		bleihen.getStyleClass().add("newstyle-button");

		Label lblAb = new Label("Von");
		DatePicker dpAb = new DatePicker();
		dpAb.setValue(LocalDate.now());
		dpAb.setEditable(false);
		dpAb.setDayCellFactory(dp -> new DateCellFactory(dpAb, false));

		Label lblBis = new Label("Bis");
		DatePicker dpBis = new DatePicker();
		dpBis.setEditable(false);
		dpBis.setValue(dpAb.getValue().plusDays(1));
		dpAb.setOnAction(e -> dpBis.setValue(dpAb.getValue().plusDays(1)));
		dpBis.setDayCellFactory(dp -> new DateCellFactory(dpAb, true));
		HBox hbLeihen = new HBox(10, bLeihenwarenkorb, bleihen, lblAb, dpAb, lblBis, dpBis);
		hbLeihen.setPadding(new Insets(5));

		/**
		 * Button Warenkorb
		 */
		tvLeihen.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BuchFX>() {
			@Override
			public void changed(ObservableValue<? extends BuchFX> arg0, BuchFX arg1, BuchFX arg2) {

				if (mitglied != null)
					bLeihenwarenkorb.setDisable(false);

			}
		});
		bLeihenwarenkorb.setOnAction(e -> {

			for (BuchFX b : tvLeihen.getSelectionModel().getSelectedItems())
				mitgliedfx.getBestellungLeihen().add(b);
			bleihen.setDisable(false);

		});

		/**
		 * Button leihen
		 */
		bleihen.setOnAction(e -> {
			new BestellungLeihenDetailDialog(mitgliedfx, dpAb.getValue(), dpBis.getValue()).showAndWait();

		});

		VBox vbLeihen = new VBox(10, tvLeihen, hbLeihen);
		return new TitledPane(thema, vbLeihen);
	}

	/**
	 * TitledPaneBibliothekScene wird die alle Bücher nach Thema geordnet
	 */
	private TitledPane TitelPaneBibliothekerstellen(String thema, Optional<Boolean> verkaufstatus) {

		TableColumn<BuchFX, String> titelCol1 = new TableColumn<>("BuchTitel");
		titelCol1.setCellValueFactory(new PropertyValueFactory<>("titel"));
		titelCol1.setMinWidth(250);

		TableColumn<BuchFX, String> autorCol1 = new TableColumn<>("Autor");
		autorCol1.setCellValueFactory(new PropertyValueFactory<>("autor"));
		autorCol1.setMinWidth(200);

		TableColumn<BuchFX, Integer> jahrCol1 = new TableColumn<>("Jahrschein");
		jahrCol1.setCellValueFactory(new PropertyValueFactory<>("jahr"));
		jahrCol1.setMinWidth(100);

		TableColumn<BuchFX, Integer> PreisCol1 = new TableColumn<>("Preis");
		PreisCol1.setCellValueFactory(new PropertyValueFactory<>("preis"));
		PreisCol1.setMinWidth(80);
		leseBooks(thema, Optional.empty());
		TableView<BuchFX> tverstellen = new TableView<>(olBucher);
		tverstellen.getColumns().addAll(titelCol1, autorCol1, jahrCol1, PreisCol1);
		tverstellen.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		VBox vbsprache = new VBox(tverstellen);
		return new TitledPane(thema, vbsprache);
	}

}
