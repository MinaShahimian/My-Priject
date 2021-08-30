package klasse;


public class Kaufen {

	private int kaufenId;
	private Buch buch;
	private Mitglied mitglied;

	public Kaufen(int kaufenId, Buch buch, Mitglied mitglied) {
		super();
		this.kaufenId = kaufenId;
		this.buch = buch;
		this.mitglied = mitglied;
	}

	public int getKaufenId() {
		return kaufenId;
	}

	public void setKaufenId(int kaufenId) {
		this.kaufenId = kaufenId;
	}

	public Buch getBuch() {
		return buch;
	}

	public void setBuch(Buch buch) {
		this.buch = buch;
	}

	public Mitglied getMitglied() {
		return mitglied;
	}

	public void setMitglied(Mitglied mitglied) {
		this.mitglied = mitglied;
	}
}
