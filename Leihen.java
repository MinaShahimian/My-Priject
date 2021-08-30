package klasse;

import java.time.LocalDate;

public class Leihen {

	private int leihenId;
	private Buch buch;
	private Mitglied mitglied;
	private LocalDate fromDate;
	private LocalDate toDate;

	public Leihen(int leihenId, Buch buch, Mitglied mitglied, LocalDate fromDate, LocalDate toDate) {
		super();
		this.leihenId = leihenId;
		this.buch = buch;
		this.mitglied = mitglied;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public int getLeihenId() {
		return leihenId;
	}

	public void setLeihenId(int leihenId) {
		this.leihenId = leihenId;
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

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
}
