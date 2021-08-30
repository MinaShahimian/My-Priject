package klasse;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BuchFX {
	private Buch modellBuch;
	private SimpleIntegerProperty buchId;
	private SimpleIntegerProperty isbn;
	private SimpleStringProperty autor;
	private SimpleStringProperty titel;
	private SimpleStringProperty thema;
	private SimpleIntegerProperty jahr;
	private SimpleDoubleProperty preis;
	private SimpleBooleanProperty verkaufen;
	private SimpleStringProperty absenderAdresse;

	public BuchFX(Buch b) {
		modellBuch = b;
		buchId = new SimpleIntegerProperty(b.getBuchId());
		isbn = new SimpleIntegerProperty(b.getIsbn());
		autor = new SimpleStringProperty(b.getAutor());
		titel = new SimpleStringProperty(b.getTitel());
		thema = new SimpleStringProperty(b.getThema());
		jahr = new SimpleIntegerProperty(b.getJahr());
		preis = new SimpleDoubleProperty(b.getPreis());
		verkaufen = new SimpleBooleanProperty(b.getVerkaufen());
		absenderAdresse = new SimpleStringProperty(b.getAbsenderAdresse());

	}

	public Buch getModellBuch() {
		return modellBuch;
	}

	public int getBuchId() {
		return buchId.get();
	}

	public SimpleIntegerProperty buchIdProperty() {
		return buchId;
	}

	public int getIsbn() {
		return isbn.get();
	}

	public void setIsbn(int i) {
		isbn.set(i);
		modellBuch.setIsbn(i);
	}

	public SimpleIntegerProperty isbnProperty() {
		return isbn;
	}

	public String getAutor() {
		return autor.get();
	}

	public void setAutor(String a) {
		autor.set(a);
		modellBuch.setAutor(a);
	}

	public SimpleStringProperty autorProperty() {
		return autor;
	}

	public String getTitel() {
		return titel.get();
	}

	public void setTitel(String t) {
		titel.set(t);
		modellBuch.setTitel(t);
	}

	public SimpleStringProperty titelProperty() {
		return titel;
	}

	public String getThema() {
		return thema.get();
	}

	public void setThema(String t) {
		thema.set(t);
		modellBuch.setThema(t);
	}

	public SimpleStringProperty themaProperty() {
		return thema;
	}

	public int getJahr() {
		return jahr.get();
	}

	public void setJahr(int j) {
		jahr.set(j);
		modellBuch.setJahr(j);
	}

	public SimpleIntegerProperty jahrProperty() {
		return jahr;
	}

	public double getPreis() {
		return preis.get();
	}

	public void setPreis(double p) {
		preis.set(p);
		modellBuch.setPreis(p);
	}

	public SimpleDoubleProperty preisProperty() {
		return preis;
	}

	public boolean getVerkaufen() {
		return verkaufen.get();
	}

	public void setVerkaufen(boolean v) {
		verkaufen.set(v);
		modellBuch.setVerkaufen(v);
	}

	public SimpleBooleanProperty verkaufenProperty() {
		return verkaufen;
	}

	public String getAbsenderAdresse() {
		return absenderAdresse.get();
	}

	public void setAbsenderAdresse(String a) {
		absenderAdresse.set(a);
		modellBuch.setAbsenderAdresse(a);
	}

	public SimpleStringProperty absenderAdresseProperty() {
		return absenderAdresse;
	}

}
