package klasse;

import java.time.LocalDate;

public class Mitglied {
	private int mitgliedId;
	private String vorname;
	private String familienname;
	private String adresse;
	private String telefonnummer;
	private String benutzername;
	private String kennwort;

	public Mitglied(int mitgliedId, String vorname, String familienname, String adresse, String telefonnummer,
			String benutzername, String kennwort) {
		super();
		this.mitgliedId = mitgliedId;
		this.vorname = vorname;
		this.familienname = familienname;
		this.adresse = adresse;
		this.telefonnummer = telefonnummer;
		this.benutzername = benutzername;
		this.kennwort = kennwort;
	}

	public int getMitgliedId() {
		return mitgliedId;
	}

	public void setMitgliedId(int id) {
		this.mitgliedId = id;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getFamilienname() {
		return familienname;
	}

	public void setFamilienname(String familienname) {
		this.familienname = familienname;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getTelefonnummer() {
		return telefonnummer;
	}

	public void setTelefonnummer(String telefonnummer) {
		this.telefonnummer = telefonnummer;
	}

	public String getBenutzername() {
		return benutzername;
	}

	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}

	public String getKennwort() {
		return kennwort;
	}

	public void setKennwort(String kennwort) {
		this.kennwort = kennwort;
	}

}
