package klasse;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AnzeigeAufgebenFX {
	private AnzeigeAufgeben modelAnzeigeaufgeben;
	private SimpleIntegerProperty AnzeigeAufgebenId;
	private SimpleIntegerProperty AnzeigeAufgebenBuchIsbn;
	private SimpleStringProperty AnzeigeAufgebenBuchThema;
	private SimpleStringProperty AnzeigeAufgebenBuchTitle;
	private SimpleStringProperty AnzeigeAufgebenBuchAutor;
	private SimpleIntegerProperty AnzeigeAufgebenBuchJahr;
	private SimpleDoubleProperty AnzeigeAufgebenBuchPries;
	private SimpleBooleanProperty AnzeigeAufgebenBuchVerkaufen;
	private SimpleStringProperty AnzeigeAufgebenBuchAbsenderAdresse;
	private SimpleStringProperty AnzeigeAufgebenMitgliedFamilienname;

	public AnzeigeAufgebenFX(AnzeigeAufgeben a) {
		modelAnzeigeaufgeben = a;
		AnzeigeAufgebenId = new SimpleIntegerProperty(a.getAnzeigeAufgebenId());
		AnzeigeAufgebenBuchIsbn = new SimpleIntegerProperty(a.getBuch().getIsbn());
		AnzeigeAufgebenBuchThema = new SimpleStringProperty(a.getBuch().getThema());
		AnzeigeAufgebenBuchTitle = new SimpleStringProperty(a.getBuch().getTitel());
		AnzeigeAufgebenBuchAutor = new SimpleStringProperty(a.getBuch().getAutor());
		AnzeigeAufgebenBuchJahr = new SimpleIntegerProperty(a.getBuch().getJahr());
		AnzeigeAufgebenBuchPries = new SimpleDoubleProperty(a.getBuch().getPreis());
		AnzeigeAufgebenBuchVerkaufen = new SimpleBooleanProperty(a.getBuch().getVerkaufen());
		AnzeigeAufgebenBuchAbsenderAdresse = new SimpleStringProperty(a.getBuch().getAbsenderAdresse());
		AnzeigeAufgebenMitgliedFamilienname = new SimpleStringProperty(a.getMitglied().getFamilienname());

	}

	public AnzeigeAufgeben getModelAnzeigeaufgeben() {
		return modelAnzeigeaufgeben;
	}

	public int getAnzeigeAufgebenId() {
		return AnzeigeAufgebenId.get();
	}

	public SimpleIntegerProperty AnzeigeAufgebenIdProperty() {
		return AnzeigeAufgebenId;
	}

	public int getAnzeigeAufgebenBuchIsbn() {
		return AnzeigeAufgebenBuchIsbn.get();
	}

	public SimpleIntegerProperty AnzeigeAufgebenBuchIsbnProperty() {
		return AnzeigeAufgebenBuchIsbn;
	}

	public String getAnzeigeAufgebenBuchThema() {
		return AnzeigeAufgebenBuchThema.get();
	}

	public SimpleStringProperty AnzeigeAufgebenBuchThemaProperty() {
		return AnzeigeAufgebenBuchThema;
	}

	public String getAnzeigeAufgebenBuchTitle() {
		return AnzeigeAufgebenBuchTitle.get();
	}

	public SimpleStringProperty AnzeigeAufgebenBuchTitleProperty() {
		return AnzeigeAufgebenBuchTitle;
	}

	public String getAnzeigeAufgebenBuchAutor() {
		return AnzeigeAufgebenBuchAutor.get();
	}

	public SimpleStringProperty AnzeigeAufgebenBuchAutorProperty() {
		return AnzeigeAufgebenBuchAutor;
	}

	public int getAnzeigeAufgebenBuchJahr() {
		return AnzeigeAufgebenBuchJahr.get();
	}

	public SimpleIntegerProperty AnzeigeAufgebenBuchJahrProperty() {
		return AnzeigeAufgebenBuchJahr;
	}

	public double getAnzeigeAufgebenBuchPries() {
		return AnzeigeAufgebenBuchPries.get();
	}

	public SimpleDoubleProperty AnzeigeAufgebenBuchPriesProperty() {
		return AnzeigeAufgebenBuchPries;
	}

	public boolean getAnzeigeAufgebenBuchVerkaufen() {
		return AnzeigeAufgebenBuchVerkaufen.get();
	}

	public SimpleBooleanProperty AnzeigeAufgebenBuchVerkaufenProperty() {
		return AnzeigeAufgebenBuchVerkaufen;
	}

	public String getAnzeigeAufgebenBuchAbsenderAdresse() {
		return AnzeigeAufgebenBuchAbsenderAdresse.get();
	}

	public SimpleStringProperty AnzeigeAufgebenBuchAbsenderAdresseProperty() {
		return AnzeigeAufgebenBuchAbsenderAdresse;
	}

	public String getAnzeigeAufgebenMitgliedFamilienname() {
		return AnzeigeAufgebenMitgliedFamilienname.get();
	}

	public SimpleStringProperty AnzeigeAufgebenMitgliedFamiliennameProperty() {
		return AnzeigeAufgebenMitgliedFamilienname;
	}

}
