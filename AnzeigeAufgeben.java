package klasse;

public class AnzeigeAufgeben {
	private int AnzeigeAufgebenId;
	private Buch buch;
	private Mitglied mitglied;


	public AnzeigeAufgeben(int anzeigeAufgebenId,Buch buch, Mitglied mitglied) {
		super();
		AnzeigeAufgebenId = anzeigeAufgebenId;
		this.buch=buch;
		this.mitglied = mitglied;
	}
	
	
	public int getAnzeigeAufgebenId() {
		return AnzeigeAufgebenId;
	}

	public void setAnzeigeAufgebenId(int anzeigeAufgebenId) {
		AnzeigeAufgebenId = anzeigeAufgebenId;
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
