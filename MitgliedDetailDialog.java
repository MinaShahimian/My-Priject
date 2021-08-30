package verwaltung;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import klasse.Datenbank;
import klasse.Mitglied;
import klasse.MitgliedFX;

public class MitgliedDetailDialog extends  Dialog<ButtonType> {
	

	public MitgliedDetailDialog(MitgliedFX mitgliedfx) {
		
		Label lblid1 = new Label("Id");
		Label lblid2 = new Label(Integer.toString(mitgliedfx.getMitgliedid()));
	
		Label lblvorname = new Label("Vorname ");
		TextField txtvorname = new TextField();
		txtvorname.setText(mitgliedfx.getVorname());

		Label lblfamiliname = new Label("Familienname");
		TextField txtfamiliname = new TextField();
		txtfamiliname.setText(mitgliedfx.getFamilienname());

		Label lbladresse = new Label("Adresse");
		TextField txtadresse = new TextField();
		txtadresse.setText(mitgliedfx.getAdresse());

		Label lbltellefonnummer = new Label("Telefonnummer");
		TextField txttellefonnummer = new TextField();
		txttellefonnummer.setText(mitgliedfx.getTelefonnummer());
		
		Label lblBenutzername = new Label("Benutzername");
		TextField txtBenutzername = new TextField();
		txtBenutzername.setText(mitgliedfx.getBenutzername());
		
		Label lblKennwort = new Label("Kennwort");
		TextField txtKennwort = new TextField();
		txtKennwort.setText(mitgliedfx.getKennwort());

		
		GridPane gpmitglied=new GridPane();
		gpmitglied.setVgap(10);
		gpmitglied.setHgap(10);
		
		gpmitglied.setPadding(new Insets(5));
		gpmitglied.add(lblid1, 0, 0);
		gpmitglied.add(lblid2, 1, 0);
		gpmitglied.add(lblvorname, 0, 1);
		gpmitglied.add(txtvorname, 1, 1);
		gpmitglied.add(lblfamiliname, 0, 2);
		gpmitglied.add(txtfamiliname, 1, 2);
		gpmitglied.add(lbladresse, 0, 3);
		gpmitglied.add(txtadresse, 1, 3);
		gpmitglied.add(lbltellefonnummer, 0, 4);
		gpmitglied.add(txttellefonnummer, 1, 4);
		gpmitglied.add(lblBenutzername, 0, 5);
		gpmitglied.add(txtBenutzername, 1, 5);
		gpmitglied.add(lblKennwort, 0, 6);
		gpmitglied.add(txtKennwort, 1, 6);
		
		VBox vb = new VBox(gpmitglied);
		this.getDialogPane().setContent(vb);
		this.getDialogPane().getStylesheets().add("DetailDialog.css");
		
		ButtonType speichern = new ButtonType("speichern", ButtonData.OK_DONE);
		ButtonType abberechen = new ButtonType("Abberechen", ButtonData.CANCEL_CLOSE);
		this.getDialogPane().getButtonTypes().addAll(speichern, abberechen);


		Button save = (Button) this.getDialogPane().lookupButton(speichern);
		save.addEventFilter(ActionEvent.ACTION, e -> {
			if (txtvorname.getText() == null || txtvorname.getText().length() == 0) {
				new Alert(AlertType.ERROR, "vorname eingeben").showAndWait();
				e.consume();
				return;
			}
			if (txtfamiliname.getText() == null || txtfamiliname.getText().length() == 0) {
				new Alert(AlertType.ERROR, "familiname eingeben").showAndWait();
				e.consume();
				return;
			}
			if (txtadresse.getText() == null || txtadresse.getText().length() == 0) {
				new Alert(AlertType.ERROR, "Adresse eingeben").showAndWait();
				e.consume();
				return;
			}
			if (txttellefonnummer.getText() == null || txttellefonnummer.getText().length() == 0) {
				new Alert(AlertType.ERROR, "Telefonnummer eingeben").showAndWait();
				e.consume();
				return;
			}
			
		});
		this.setResultConverter(new Callback<ButtonType, ButtonType>() {

			@Override
			public ButtonType call(ButtonType arg0) {

				if (arg0 == speichern) {
					
					mitgliedfx.setVorname(txtvorname.getText());
					mitgliedfx.setFamilienname(txtfamiliname.getText());
					mitgliedfx.setAdresse(txtadresse.getText());
					mitgliedfx.setTelefonnummer(txttellefonnummer.getText());
					mitgliedfx.setBenutzername(txtBenutzername.getText());
					mitgliedfx.setKennwort(txtKennwort.getText());
					

					try {
						if (mitgliedfx.getMitgliedid() == 0)
							Datenbank.insertMitglied(mitgliedfx.getModellMitglied());
						else
							Datenbank.updateMitglied(mitgliedfx.getModellMitglied());
					} catch (SQLException e) {
						new Alert(AlertType.ERROR, e.toString()).showAndWait();
					}

				}
				return arg0;
			}

		});

	}

	}


