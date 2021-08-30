package verwaltung;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import klasse.BuchFX;
import klasse.Datenbank;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;

public class BuchDetailDialog extends Dialog<ButtonType> {

	public BuchDetailDialog(BuchFX buchfx) { 
		Label lblid1 = new Label("Id");
		Label lblid2 = new Label(Integer.toString(buchfx.getBuchId()));

		Label lblisbn = new Label("Isbn");
		TextField txtisbn = new TextField();
		txtisbn.setText(Integer.toString(buchfx.getIsbn()));
		Label lblautor = new Label("Autor");
		TextField txtautor = new TextField();
		txtautor.setText(buchfx.getAutor());

		Label lbltitel = new Label("Titel");
		TextField txttitel = new TextField();
		txttitel.setText(buchfx.getTitel());
		Label lblthema = new Label("Thema");
		TextField txtthema = new TextField();
		txtthema.setText(buchfx.getThema());

		Label lbljahr = new Label("Jahr");
		TextField txtjahr = new TextField();
		txtjahr.setText(Integer.toString(buchfx.getJahr()));
		Label lblpreis = new Label("Preis");
		TextField txtpreis = new TextField();
		txtpreis.setText(Double.toString(buchfx.getPreis()));

		Label lblverkaufen = new Label("verkauf Status");
		lblverkaufen.setPrefWidth(150);
		TextField txtverkaufen = new TextField();
		txtverkaufen.setPrefColumnCount(20);
		txtverkaufen.setText(Boolean.toString(buchfx.getVerkaufen()));
		Label lbladresse = new Label("Absender Adresse");
		TextField txtadresse = new TextField();
		txtadresse.setText(buchfx.getAbsenderAdresse());
		GridPane gp = new GridPane();
		gp.setVgap(10);
		gp.setHgap(10);
		gp.add(lblid1, 0, 0);
		gp.add(lblid2, 1, 0);
		gp.add(lblisbn, 0, 1);
		gp.add(txtisbn, 1, 1);
		gp.add(lblautor, 0, 2);
		gp.add(txtautor, 1, 2);
		gp.add(lbltitel, 0, 3);
		gp.add(txttitel, 1, 3);
		gp.add(lblthema, 0, 4);
		gp.add(txtthema, 1, 4);
		gp.add(lbljahr, 0, 5);
		gp.add(txtjahr, 1, 5);
		gp.add(lblpreis, 0, 6);
		gp.add(txtpreis, 1, 6);
		gp.add(lblverkaufen, 0, 7);
		gp.add(txtverkaufen, 1, 7);
		gp.add(lbladresse, 0, 8);
		gp.add(txtadresse, 1, 8);
		gp.setPadding(new Insets(5));

		VBox vb = new VBox(gp);
		this.getDialogPane().setContent(vb);
		this.getDialogPane().getStylesheets().add("DetailDialog.css");
		
		ButtonType speichern = new ButtonType("speichern", ButtonData.OK_DONE);
		ButtonType abberechen = new ButtonType("Abberechen", ButtonData.CANCEL_CLOSE);
		this.getDialogPane().getButtonTypes().addAll(speichern, abberechen);

		Button save = (Button) this.getDialogPane().lookupButton(speichern);
		save.addEventFilter(ActionEvent.ACTION, e -> {
			if (txtisbn.getText() == null || txtisbn.getText().length() == 0) {
				new Alert(AlertType.ERROR, "Isbn eingeben").showAndWait();
				e.consume();
				return;
			}
			if (txtautor.getText() == null || txtautor.getText().length() == 0) {
				new Alert(AlertType.ERROR, "Autor eingeben").showAndWait();
				e.consume();
				return;
			}
			if (txttitel.getText() == null || txttitel.getText().length() == 0) {
				new Alert(AlertType.ERROR, "Title eingeben").showAndWait();
				e.consume();
				return;
			}
			if (txtthema.getText() == null || txtthema.getText().length() == 0) {
				new Alert(AlertType.ERROR, "thema eingeben").showAndWait();
				e.consume();
				return;
			}
			
			if (txtjahr.getText() == null || txtjahr.getText().length() == 0) {
				new Alert(AlertType.ERROR, "Jahr eingeben").showAndWait();
				e.consume();
				return;
			}
			if (txtpreis.getText() == null || txtpreis.getText().length() == 0) {
				new Alert(AlertType.ERROR, "Preis eingeben").showAndWait();
				e.consume();
				return;
			}
			if (txtadresse.getText() == null || txtadresse.getText().length() == 0) {
				new Alert(AlertType.ERROR, "Absender Adresse eingeben").showAndWait();
				e.consume();
				return;
			}
			try {
				Double.parseDouble(txtpreis.getText());
			} catch (NumberFormatException e1) {

				new Alert(AlertType.ERROR, "Preis als Gleitkommazahl eingeben").showAndWait();
				e.consume();
			}
			
		});
		this.setResultConverter(new Callback<ButtonType, ButtonType>() {

			@Override
			public ButtonType call(ButtonType arg0) {

				if (arg0 == speichern) {
					buchfx.setIsbn(Integer.parseInt(txtisbn.getText()));
					buchfx.setTitel(txttitel.getText());
					buchfx.setThema(txtthema.getText());
					buchfx.setAutor(txtautor.getText());
					buchfx.setJahr(Integer.parseInt(txtjahr.getText()));
					buchfx.setPreis(Double.parseDouble(txtpreis.getText()));
					buchfx.setVerkaufen(Boolean.parseBoolean(txtverkaufen.getText()));
					buchfx.setAbsenderAdresse(txtadresse.getText());
					try {
						if (buchfx.getBuchId() == 0)
							Datenbank.insertBook(buchfx.getModellBuch());
						else
							Datenbank.updateBook(buchfx.getModellBuch());
					} catch (SQLException e) {
						new Alert(AlertType.ERROR, e.toString()).showAndWait();
					}

				}
				return arg0;
			}

		});

	}
}
