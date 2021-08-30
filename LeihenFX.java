package klasse;

import java.time.LocalDate;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class LeihenFX {

	private Leihen modellLeihen;
	private SimpleIntegerProperty leihenId;
	private SimpleStringProperty buchTitel;
	private SimpleStringProperty autor;
	private SimpleIntegerProperty jahr;
	private SimpleStringProperty absenderAdresse;
	private SimpleStringProperty mitgliedFamilienname;
	private SimpleObjectProperty<LocalDate> from;
	private SimpleObjectProperty<LocalDate> to;

	public LeihenFX(Leihen l) {
		modellLeihen = l;
		leihenId = new SimpleIntegerProperty(l.getLeihenId());
		buchTitel = new SimpleStringProperty(l.getBuch().getTitel());
		autor = new SimpleStringProperty(l.getBuch().getAutor());
		absenderAdresse = new SimpleStringProperty(l.getBuch().getAbsenderAdresse());
		jahr = new SimpleIntegerProperty(l.getBuch().getJahr());
		mitgliedFamilienname = new SimpleStringProperty(l.getMitglied().getFamilienname());
		from = new SimpleObjectProperty<>(l.getFromDate());
		to = new SimpleObjectProperty<>(l.getToDate());
	}

	public Leihen getModellLeihen() {
		return modellLeihen;
	}

	public int getLeihenId() {
		return leihenId.get();
	}
	
	public SimpleIntegerProperty leihenIdProperty() {
		return leihenId;
	}

	public int getJahr() {
		return jahr.get();
	}

	public SimpleIntegerProperty jahrProperty() {
		return jahr;
	}

	public String getBuchTitel() {
		return buchTitel.get();
	}

	public SimpleStringProperty buchTitelProperty() {
		return buchTitel;
	}

	public String getAutor() {
		return autor.get();
	}

	public SimpleStringProperty autorProperty() {
		return autor;
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

	public SimpleStringProperty mitgliedFamiliennameProperty() {
		return mitgliedFamilienname;
	}

	public LocalDate getFrom() {
		return from.get();
	}

	public SimpleObjectProperty<LocalDate> fromProperty() {
		return from;
	}

	public LocalDate getTo() {
		return to.get();
	}

	public SimpleObjectProperty<LocalDate> toProperty() {
		return to;
	}

}
