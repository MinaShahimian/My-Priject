package bibliothekProject;

import java.sql.SQLException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import klasse.BuchFX;
import klasse.Datenbank;
import klasse.Kaufen;
import klasse.KaufenFX;
import klasse.MitgliedFX;


public class BestellungKaufenDetailDialog extends Dialog<ButtonType> {
	private ObservableList<KaufenFX> olKaufen = FXCollections.observableArrayList();
	private int summe;

	public BestellungKaufenDetailDialog(MitgliedFX mitgliedfx) {
		this.setTitle("Details der Bestellung");
		for (BuchFX bfx : mitgliedfx.getBestellungKaufen()) {
			Kaufen k=new Kaufen(0, bfx.getModellBuch(), mitgliedfx.getModellMitglied());
			olKaufen.add(new KaufenFX(k));
			summe += bfx.getPreis();
		}
		

		TableColumn<KaufenFX, String> titelCol1 = new TableColumn<>("BuchTitel");
		titelCol1.setCellValueFactory(new PropertyValueFactory<>("buchTitel"));
		titelCol1.setMinWidth(250);

		TableColumn<KaufenFX, String> autorCol1 = new TableColumn<>("Autor");
		autorCol1.setCellValueFactory(new PropertyValueFactory<>("autor"));
		autorCol1.setMinWidth(200);

		TableColumn<KaufenFX, Integer> jahrCol1 = new TableColumn<>("Jahrschein");
		jahrCol1.setCellValueFactory(new PropertyValueFactory<>("jahr"));
		jahrCol1.setMinWidth(100);

		TableColumn<KaufenFX, Integer> PreisCol1 = new TableColumn<>("Preis");
		PreisCol1.setCellValueFactory(new PropertyValueFactory<>("preis"));
		PreisCol1.setMinWidth(80);
		
		TableColumn<KaufenFX, String> absenderAdresseCol1 = new TableColumn<>("Absender Adresse");
		absenderAdresseCol1.setCellValueFactory(new PropertyValueFactory<>("absenderAdresse"));
		absenderAdresseCol1.setMinWidth(200);

		TableView<KaufenFX> tverstellen = new TableView<>(olKaufen);
		tverstellen.getColumns().addAll(titelCol1, autorCol1, jahrCol1, PreisCol1,absenderAdresseCol1);
		tverstellen.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		Label lbl1 = new Label("Ihre Bestellung Gesamtsumme  ");
		Label lblSum = new Label(Integer.toString(summe));
		Label lbl2 = new Label("Euro");
		Button löschen = new Button("Löschen");
		löschen.getStyleClass().add("newstyle-button");
		HBox hb = new HBox(10, lbl1, lblSum,lbl2);
		hb.setPadding(new Insets(5));
		VBox vb = new VBox(10,tverstellen, hb, löschen);
		vb.setPadding(new Insets(5));

		this.getDialogPane().setContent(vb);
		this.getDialogPane().getStylesheets().add("DetailDialog.css");

		tverstellen.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<KaufenFX>() {

			@Override
			public void changed(ObservableValue<? extends KaufenFX> arg0, KaufenFX arg1, KaufenFX arg2) {

				if (arg2 != null)
					löschen.setDisable(false);
				else
					löschen.setDisable(true);
			}

		});


		löschen.setOnAction(e -> {
			int i = tverstellen.getSelectionModel().getSelectedIndex();
			olKaufen.remove(i);
			mitgliedfx.getBestellungKaufen().remove(i);
			summe = 0;
			for (KaufenFX bfx : olKaufen)
				summe += bfx.getPreis();
			lblSum.setText(Integer.toString(summe));
		});

		ButtonType bezahlen = new ButtonType("bezahlen", ButtonData.OK_DONE);
		ButtonType beenden = new ButtonType("Beenden", ButtonData.CANCEL_CLOSE);
		this.getDialogPane().getButtonTypes().addAll(bezahlen, beenden);

		this.setResultConverter(new Callback<ButtonType, ButtonType>() {

			@Override
			public ButtonType call(ButtonType arg0) {

				if (arg0 == bezahlen) {
					try {
						for (BuchFX bfx : mitgliedfx.getBestellungKaufen()) {
							Kaufen b = new Kaufen(0, bfx.getModellBuch(),mitgliedfx.getModellMitglied());
							Datenbank.insertKaufen(b);
						}
						mitgliedfx.loeschenBestellungKaufen();
						new Alert(Alert.AlertType.INFORMATION, "Vom konto werden " + summe + " Euro abgebucht").showAndWait();
						
					} catch (SQLException e) {
						e.printStackTrace();
						new Alert(AlertType.ERROR, e.toString()).showAndWait();
						return arg0;
					}
				}
				return arg0;
			}
			
		});
	}

}
