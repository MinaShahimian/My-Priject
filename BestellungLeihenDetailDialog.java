package bibliothekProject;

import java.sql.SQLException;
import java.time.LocalDate;

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
import klasse.Leihen;
import klasse.LeihenFX;
import klasse.MitgliedFX;

public class BestellungLeihenDetailDialog extends Dialog<ButtonType> {
	private ObservableList<LeihenFX> olLeihen = FXCollections.observableArrayList();

	public BestellungLeihenDetailDialog(MitgliedFX mitgliedfx, LocalDate Ab, LocalDate Bis) {
		this.setTitle("Details der Bestellung");

		for (BuchFX bfx : mitgliedfx.getBestellungLeihen()) {
			Leihen b = new Leihen(0, bfx.getModellBuch(), mitgliedfx.getModellMitglied(), Ab, Bis);
			olLeihen.add(new LeihenFX(b));
		}
		TableColumn<LeihenFX, String> titelCol1 = new TableColumn<>("BuchTitel");
		titelCol1.setCellValueFactory(new PropertyValueFactory<>("buchTitel"));
		titelCol1.setMinWidth(250);
		TableColumn<LeihenFX, String> autorCol1 = new TableColumn<>("Autor");
		autorCol1.setCellValueFactory(new PropertyValueFactory<>("autor"));
		autorCol1.setMinWidth(200);
		TableColumn<LeihenFX, Integer> jahrCol1 = new TableColumn<>("Jahrschein");
		jahrCol1.setCellValueFactory(new PropertyValueFactory<>("jahr"));
		jahrCol1.setMinWidth(100);
		TableColumn<LeihenFX, String> leihenFromCol = new TableColumn<>("Mietbeginn");
		leihenFromCol.setCellValueFactory(new PropertyValueFactory<>("from"));
		leihenFromCol.setMinWidth(100);
		TableColumn<LeihenFX, String> leihenToCol = new TableColumn<>("Mietende");
		leihenToCol.setCellValueFactory(new PropertyValueFactory<>("to"));
		leihenToCol.setMinWidth(100);
		TableColumn<LeihenFX, String> absenderAdresseCol1 = new TableColumn<>("Absender Adresse");
		absenderAdresseCol1.setCellValueFactory(new PropertyValueFactory<>("absenderAdresse"));
		absenderAdresseCol1.setMinWidth(200);

		TableView<LeihenFX> tvleihen = new TableView<>(olLeihen);
		tvleihen.getColumns().addAll(titelCol1, autorCol1, jahrCol1, leihenFromCol, leihenToCol,
				absenderAdresseCol1);
		tvleihen.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		Button löschen = new Button("Löschen");
		VBox vb = new VBox(10, tvleihen, löschen);
		vb.setPadding(new Insets(5));

		this.getDialogPane().setContent(vb);
		this.getDialogPane().getStylesheets().add("DetailDialog.css");
		
		tvleihen.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<LeihenFX>() {

			@Override
			public void changed(ObservableValue<? extends LeihenFX> arg0, LeihenFX arg1, LeihenFX arg2) {

				if (arg2 != null)
					löschen.setDisable(false);
				else
					löschen.setDisable(true);
			}

		});

		löschen.setOnAction(e -> {
			int i = tvleihen.getSelectionModel().getSelectedIndex();
			olLeihen.remove(i);
			mitgliedfx.getBestellungLeihen().remove(i);
		});


		ButtonType leihen = new ButtonType("leihen", ButtonData.OK_DONE);
		ButtonType beenden = new ButtonType("Beenden", ButtonData.CANCEL_CLOSE);
		this.getDialogPane().getButtonTypes().addAll(leihen, beenden);

		this.setResultConverter(new Callback<ButtonType, ButtonType>() {

			@Override
			public ButtonType call(ButtonType arg0) {

				if (arg0 == leihen) {
					try {
						for (BuchFX bfx : mitgliedfx.getBestellungLeihen()) {
							Leihen b = new Leihen(0, bfx.getModellBuch(), mitgliedfx.getModellMitglied(), Ab, Bis);
							Datenbank.insertLeihen(b);
						}
						mitgliedfx.loeschenBestellungLeihen();
						new Alert(Alert.AlertType.INFORMATION, " Das Buch Für Ihnen hat gebucht. ").showAndWait();
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