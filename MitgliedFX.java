package klasse;

//import java.time.LocalDate;
import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MitgliedFX {
	private Mitglied modellMitglied;
	private SimpleIntegerProperty mitgliedid;
	private SimpleStringProperty vorname;
	private SimpleStringProperty familienname;
	private SimpleStringProperty adresse;
	private SimpleStringProperty telefonnummer;
	private SimpleStringProperty benutzername;
	private SimpleStringProperty kennwort;
	private ArrayList<BuchFX> bestellungKaufen = new ArrayList<>();
	private ArrayList<BuchFX> bestellungLeihen = new ArrayList<>();

	public MitgliedFX(Mitglied m) {
		modellMitglied = m;
		mitgliedid = new SimpleIntegerProperty(m.getMitgliedId());
		vorname = new SimpleStringProperty(m.getVorname());
		familienname = new SimpleStringProperty(m.getFamilienname());
		adresse = new SimpleStringProperty(m.getAdresse());
		telefonnummer = new SimpleStringProperty(m.getTelefonnummer());
		benutzername = new SimpleStringProperty(m.getBenutzername());
		kennwort = new SimpleStringProperty(m.getKennwort());

	}


	public Mitglied getModellMitglied() {
		return modellMitglied;
	}

	public ArrayList<BuchFX> getBestellungKaufen() {
		return bestellungKaufen;
	}
	public void loeschenBestellungKaufen() {
		bestellungKaufen.clear();
	}
	public void loeschenBestellungLeihen() {
		bestellungLeihen.clear();
	}

	public ArrayList<BuchFX> getBestellungLeihen() {
		return bestellungLeihen;
	}
	public int getMitgliedid() {
		return mitgliedid.get();
	}

	public SimpleIntegerProperty mitgliedidProperty() {
		return mitgliedid;
	}

	public void setMitgliedid(int i) {
		mitgliedid.set(i);
		modellMitglied.setMitgliedId(i);
	}

	public String getVorname() {
		return vorname.get();
	}

	public SimpleStringProperty vornameProperty() {
		return vorname;
	}

	public void setVorname(String v) {
		vorname.set(v);
		modellMitglied.setVorname(v);
	}

	public String getFamilienname() {
		return familienname.get();
	}

	public SimpleStringProperty familiennameProperty() {
		return familienname;
	}

	public void setFamilienname(String f) {
		familienname.set(f);
		modellMitglied.setFamilienname(f);
	}

	public String getAdresse() {
		return adresse.get();
	}

	public SimpleStringProperty adresseProperty() {
		return adresse;
	}

	public void setAdresse(String a) {
		adresse.set(a);
		modellMitglied.setAdresse(a);
	}

	public String getTelefonnummer() {
		return telefonnummer.get();
	}

	public SimpleStringProperty telefonnummerProperty() {
		return telefonnummer;
	}

	public void setTelefonnummer(String t) {
		telefonnummer.set(t);
		modellMitglied.setTelefonnummer(t);
	}

	public String getBenutzername() {
		return benutzername.get();
	}

	public SimpleStringProperty benutzernameProperty() {
		return benutzername;
	}

	public void setBenutzername(String b) {
		benutzername.set(b);
		modellMitglied.setBenutzername(b);
	}

	public String getKennwort() {
		return kennwort.get();
	}

	public SimpleStringProperty kennwortProperty() {
		return kennwort;
	}

	public void setKennwort(String k) {
		kennwort.set(k);
		modellMitglied.setKennwort(k);
	}

}
