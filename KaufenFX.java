package klasse;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class KaufenFX {

	private Kaufen modellKaufen;
	private SimpleIntegerProperty kaufenId;
	private SimpleStringProperty buchTitel;
	private SimpleStringProperty autor;
	private SimpleIntegerProperty jahr;
	private SimpleDoubleProperty preis;
	private SimpleStringProperty absenderAdresse;
	private SimpleStringProperty mitgliedFamilienname;

	public KaufenFX(Kaufen k) {
		modellKaufen = k;
		kaufenId = new SimpleIntegerProperty(k.getKaufenId());
		buchTitel = new SimpleStringProperty(k.getBuch().getTitel());
		autor = new SimpleStringProperty(k.getBuch().getAutor());
		jahr = new SimpleIntegerProperty(k.getBuch().getJahr());
		preis = new SimpleDoubleProperty(k.getBuch().getPreis());
		absenderAdresse = new SimpleStringProperty(k.getBuch().getAbsenderAdresse());
		mitgliedFamilienname = new SimpleStringProperty(k.getMitglied().getFamilienname());
	}

	public Kaufen getModellKaufen() {
		return modellKaufen;
	}

	public int getKaufenId() {
		return kaufenId.get();
	}

	public SimpleIntegerProperty kaufenIdProperty() {
		return kaufenId;
	}

	public String getBuchTitel() {
		return buchTitel.get();
	}

	public SimpleStringProperty buchTitelProperty() {
		return buchTitel;
	}

	public int getJahr() {
		return jahr.get();
	}

	public SimpleIntegerProperty jahrProperty() {
		return jahr;
	}

	public String getAutor() {
		return autor.get();
	}

	public SimpleStringProperty autorProperty() {
		return autor;
	}

	public double getPreis() {
		return preis.get();
	}

	public SimpleDoubleProperty preisProperty() {
		return preis;
	}

	public String getAbsenderAdresse() {
		return absenderAdresse.get();
	}

	public SimpleStringProperty absenderAdresseProperty() {
		return absenderAdresse;
	}

	public String getMitgliedFamilienname() {
		return mitgliedFamilienname.get();
	}

	public SimpleStringProperty mitgliedFamilinameProperty() {
		return mitgliedFamilienname;
	}
}
