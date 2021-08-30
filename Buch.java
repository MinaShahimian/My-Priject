package klasse;

public class Buch {
	private int buchId;
	private int isbn;
	private String autor;
	private String titel;
	private String thema;
	private int jahr;
	private double preis;
	private boolean verkaufen;
	private String absenderAdresse;

	public Buch(int buchId, int isbn, String autor, String titel, String thema, int jahr, double preis,
			boolean verkaufen,String adresse) {
		super();
		this.buchId = buchId;
		this.isbn = isbn;

		this.autor = autor;
		this.titel = titel;
		this.thema = thema;
		this.jahr = jahr;
		this.preis = preis;
		this.verkaufen = verkaufen;
		this.absenderAdresse = adresse;

	}

	public int getBuchId() {
		return buchId;
	}

	public void setBuchId(int id) {
		this.buchId = id;
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getThema() {
		return thema;
	}

	public void setThema(String thema) {
		this.thema = thema;
	}

	public int getJahr() {
		return jahr;
	}

	public void setJahr(int jahr) {
		this.jahr = jahr;
	}

	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}

	public boolean getVerkaufen() {
		return verkaufen;
	}

	public void setVerkaufen(boolean verkaufen) {
		this.verkaufen = verkaufen;
	}

	public String getAbsenderAdresse() {
		return absenderAdresse;
	}

	public void setAbsenderAdresse(String adresse) {
		this.absenderAdresse = adresse;
	}

}
