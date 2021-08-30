package klasse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Optional;
import java.util.TreeSet;

public class Datenbank {
	private static final String DBLOCATION = "C:\\Java\\Bibliothek";
	private static final String CONNSTRING = "jdbc:derby:" + DBLOCATION + ";create=true";

	private static final String BUCH_TABELE = "Buch";
	private static final String BUCH_ID = "buchId";
	private static final String BUCH_ISBN = "isbn";
	private static final String BUCH_AUTOR = "autor";
	private static final String BUCH_TITLE = "titel";
	private static final String BUCH_THEMA = "thema";
	private static final String BUCH_JAHR = "jahr";
	private static final String BUCH_PREIS = "preis";
	private static final String BUCH_VERKAUFEN = "verkaufen";
	private static final String BUCH_ABSENDERADRESSE = "absenderAdresse";

	private static final String MITGLIED_TABELE = "Mitglied";
	private static final String MITGLIED_ID = "mitgliedId";
	private static final String MITGLIED_VORNAME = "vorname";
	private static final String MITGLIED_FAMILIENNAME = "familienname";
	private static final String MITGLIED_ADRESSE = "adresse";
	private static final String MITGLIED_TELEFONNUMMER = "telefonnummer";
	private static final String MITGLIED_BENUTZERNAME = "benutzername";
	private static final String MITGLIED_KENNWORT = "kennwort";

	private static final String KAUFEN_TABELE = "kaufen";
	private static final String KAUFEN_ID = "kaufenid";
	private static final String KAUFEN_BUCH_ID = "kaufenBuchId";
	private static final String KAUFEN_MITGLIED_ID = "kaufenMitgliedId";

	private static final String LEIHEN_TABELE = "leihen";
	private static final String LEIHEN_ID = "leihenId";
	private static final String LEIHEN_BUCH_ID = "leihenBuchId";
	private static final String LEIHEN_MITGLIED_ID = "leihenMitgliedId";
	private static final String LEIHEN_FROM = "leihenFrom";
	private static final String LEIHEN_TO = "leihenTo";

	private static final String ANZEIGE_TABELE="Anzeige";
	private static final String ANZEIGE_ID="AnzeigeAufgebenId";
	private static final String ANZEIGE_BUCH_ID="AnzeigeAufgebenBuchId";
	private static final String ANZEIGE_MITGLIED_ID="AnzeigeAufgebenMitgliedId";

	
	public static void createBooks() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {

			conn = DriverManager.getConnection(CONNSTRING);
			stmt = conn.createStatement();
			rs = conn.getMetaData().getTables(null, null, BUCH_TABELE.toUpperCase(), new String[] { "TABLE" });
			if (rs.next()) {
				return;
			}
			String ct = "CREATE TABLE " + BUCH_TABELE + " (" + BUCH_ID + " INTEGER GENERATED ALWAYS AS IDENTITY,"
					+ BUCH_ISBN + " INTEGER," + BUCH_AUTOR + " VARCHAR(200)," + BUCH_TITLE + " VARCHAR(200),"
					+ BUCH_THEMA + " VARCHAR(200)," + BUCH_JAHR + " INTEGER," + BUCH_PREIS + " FLOAT," + 
					BUCH_VERKAUFEN +" Boolean,"+BUCH_ABSENDERADRESSE+ " VARCHAR(200)," + 
					"PRIMARY KEY("+ BUCH_ID + "))";

			stmt.executeUpdate(ct);
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				throw e;
			}
		}

	}

	public static void insertBook(Buch buch) throws SQLException {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;

		try {
			conn = DriverManager.getConnection(CONNSTRING);
			String insert = "INSERT INTO " + BUCH_TABELE + "(" + 
			BUCH_ISBN + "," + 
			BUCH_AUTOR + "," + 
			BUCH_TITLE + ","+ 
			BUCH_THEMA + "," + 
			BUCH_JAHR + "," + 
			BUCH_PREIS + "," +
			BUCH_VERKAUFEN + "," +
			BUCH_ABSENDERADRESSE+" )VALUES(" +
			"?, " +
			"?, " + 
			"?, " + 
			"?, " + 
			"?, "+ 
			"?, " + 
			"?, " + 
			"?)";

			pstmt = conn.prepareStatement(insert);
			pstmt.setInt(1, buch.getIsbn());
			pstmt.setString(2, buch.getAutor());
			pstmt.setString(3, buch.getTitel());
			pstmt.setString(4, buch.getThema());
			pstmt.setInt(5, buch.getJahr());
			pstmt.setDouble(6, buch.getPreis());
			pstmt.setBoolean(7, buch.getVerkaufen());
			pstmt.setString(8, buch.getAbsenderAdresse());

			pstmt.executeUpdate();
			String identity = "SELECT IDENTITY_VAL_LOCAL() FROM " + BUCH_TABELE;
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(identity);
			if (rs.next())
				buch.setBuchId(rs.getInt("1"));

		} catch (SQLException e) {
			System.out.println(e);
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				throw e;
			}

		}

	}

	public static void updateBook(Buch buch) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DriverManager.getConnection(CONNSTRING);
			String update = "UPDATE " + BUCH_TABELE + " SET " +
			BUCH_ISBN + "=?," + 
			BUCH_AUTOR + "=?," +
			BUCH_TITLE+ "=?," + 
			BUCH_THEMA + "=?," + 
			BUCH_JAHR + "=?," + 
			BUCH_PREIS + "=?,"+
			BUCH_VERKAUFEN + "=?,"+
			BUCH_ABSENDERADRESSE+ "=? WHERE " +
			BUCH_ID + "="+ buch.getBuchId();

			pstmt = conn.prepareStatement(update);
			pstmt.setInt(1, buch.getIsbn());
			pstmt.setString(2, buch.getAutor());
			pstmt.setString(3, buch.getTitel());
			pstmt.setString(4, buch.getThema());
			pstmt.setInt(5, buch.getJahr());
			pstmt.setDouble(6, buch.getPreis());
			pstmt.setBoolean(7, buch.getVerkaufen());
			pstmt.setString(8, buch.getAbsenderAdresse());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw e;
		} finally {
			try {

				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				throw e;
			}

		}

	}

	
	public static ArrayList<String> leseThemen() throws SQLException {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<String> alt = new ArrayList<>();
		TreeSet<String> themen = new TreeSet<>();

		try {
			conn = DriverManager.getConnection(CONNSTRING);
			String select = "SELECT " + BUCH_THEMA + " FROM " + BUCH_TABELE;
			stmt = conn.prepareStatement(select);
			rs = stmt.executeQuery();
			while (rs.next()) {
				themen.add(rs.getString(BUCH_THEMA));
			}

			rs.close();

		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				throw e;
			}
		}
		for (String t : themen)
			alt.add(t);
		return alt;

	}
	public static ArrayList<Buch> getBooks() throws SQLException {

		return getBooks(null,Optional.empty());
	}

	public static ArrayList<Buch> getBooks(String thema,Optional<Boolean>verkaufstatus ) throws SQLException {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Buch> alc = new ArrayList<>();

		try {
			conn = DriverManager.getConnection(CONNSTRING);
			String select = "SELECT * FROM " + BUCH_TABELE;
			if (thema != null&&verkaufstatus.isPresent())
			select += " WHERE " + BUCH_THEMA + "=? AND " + BUCH_VERKAUFEN + "=?";
			if (thema != null&&verkaufstatus.isEmpty())
				select += " WHERE " + BUCH_THEMA + "=? ";

			stmt = conn.prepareStatement(select);
			if (thema != null&&verkaufstatus.isPresent()) {
				stmt.setString(1, thema);
				stmt.setBoolean(2, verkaufstatus.get());

			}
			if (thema != null&&verkaufstatus.isEmpty()) {
				stmt.setString(1, thema);
			}

			rs = stmt.executeQuery();
			while (rs.next())
				alc.add(new Buch(rs.getInt(BUCH_ID), rs.getInt(BUCH_ISBN), rs.getString(BUCH_AUTOR),
						rs.getString(BUCH_TITLE), rs.getString(BUCH_THEMA), rs.getInt(BUCH_JAHR),
						rs.getDouble(BUCH_PREIS),rs.getBoolean(BUCH_VERKAUFEN), rs.getString(BUCH_ABSENDERADRESSE)));

			rs.close();

		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				throw e;
			}
		}

		return alc;

	}

	public static void createMitglied() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {

			conn = DriverManager.getConnection(CONNSTRING);
			stmt = conn.createStatement();
			rs = conn.getMetaData().getTables(null, null, MITGLIED_TABELE.toUpperCase(), new String[] { "TABLE" });
			if (rs.next()) {
				return;
			}
			String ct = "CREATE TABLE " + MITGLIED_TABELE + " (" + MITGLIED_ID
					+ " INTEGER GENERATED ALWAYS AS IDENTITY," + MITGLIED_VORNAME + " VARCHAR(200),"
					+ MITGLIED_FAMILIENNAME + " VARCHAR(200)," + MITGLIED_ADRESSE + " VARCHAR(200),"
					+ MITGLIED_TELEFONNUMMER + " VARCHAR(200)," + MITGLIED_BENUTZERNAME + " VARCHAR(200),"
					+ MITGLIED_KENNWORT + " VARCHAR(200)," + "PRIMARY KEY(" + MITGLIED_ID + "))";

			stmt.executeUpdate(ct);
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				throw e;
			}
		}

	}

	public static void insertMitglied(Mitglied mitglied) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;

		try {
			conn = DriverManager.getConnection(CONNSTRING);
			String insert = "INSERT INTO " + MITGLIED_TABELE + "(" + MITGLIED_VORNAME + "," + MITGLIED_FAMILIENNAME
					+ "," + MITGLIED_ADRESSE + "," + MITGLIED_TELEFONNUMMER + "," + MITGLIED_BENUTZERNAME + ","
					+ MITGLIED_KENNWORT + ") VALUES(" + "?, " + "?, " + "?, " + "?, " + "?, " + "?)";

			pstmt = conn.prepareStatement(insert);
			pstmt.setString(1, mitglied.getVorname());
			pstmt.setString(2, mitglied.getFamilienname());
			pstmt.setString(3, mitglied.getAdresse());
			pstmt.setString(4, mitglied.getTelefonnummer());
			pstmt.setString(5, mitglied.getBenutzername());
			pstmt.setString(6, mitglied.getKennwort());
			pstmt.executeUpdate();
			String identity = "SELECT IDENTITY_VAL_LOCAL() FROM " + MITGLIED_TABELE;
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(identity);
			if (rs.next())
				mitglied.setMitgliedId(rs.getInt("1"));

		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				throw e;
			}

		}

	}

	public static void updateMitglied(Mitglied mitglied) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DriverManager.getConnection(CONNSTRING);
			String update = "UPDATE " + MITGLIED_TABELE + " SET " + MITGLIED_VORNAME + "=?," + MITGLIED_FAMILIENNAME
					+ "=?," + MITGLIED_ADRESSE + "=?," + MITGLIED_TELEFONNUMMER + "=?," + MITGLIED_BENUTZERNAME + "=?,"
					+ MITGLIED_KENNWORT + "=? WHERE " + MITGLIED_ID + "=" + mitglied.getMitgliedId();

			pstmt = conn.prepareStatement(update);
			pstmt.setString(1, mitglied.getVorname());
			pstmt.setString(2, mitglied.getFamilienname());
			pstmt.setString(3, mitglied.getAdresse());
			pstmt.setString(4, mitglied.getTelefonnummer());
			pstmt.setString(5, mitglied.getBenutzername());
			pstmt.setString(6, mitglied.getKennwort());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw e;
		} finally {
			try {

				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				throw e;
			}

		}

	}

	

	public static ArrayList<Mitglied> getMitglied() throws SQLException {
		return getMitglied(null, null);
	}

	public static ArrayList<Mitglied> getMitglied(String benutzername, String kennwort) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Mitglied> alc = new ArrayList<>();

		try {
			conn = DriverManager.getConnection(CONNSTRING);
			String select = "SELECT * FROM " + MITGLIED_TABELE;
			if (benutzername != null) {
				select += " WHERE " + MITGLIED_BENUTZERNAME + "=? AND " + MITGLIED_KENNWORT + "=?";
			}
			stmt = conn.prepareStatement(select);
			if (benutzername != null) {
				stmt.setString(1, benutzername);
				stmt.setString(2, kennwort);
			}

			rs = stmt.executeQuery();
			while (rs.next())
				alc.add(new Mitglied(rs.getInt(MITGLIED_ID), rs.getString(MITGLIED_VORNAME),
						rs.getString(MITGLIED_FAMILIENNAME), rs.getString(MITGLIED_ADRESSE),
						rs.getString(MITGLIED_TELEFONNUMMER), rs.getString(MITGLIED_BENUTZERNAME),
						rs.getString(MITGLIED_KENNWORT)));

			rs.close();

		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				throw e;
			}
		}

		return alc;

	}

	public static void createKaufen() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {

			conn = DriverManager.getConnection(CONNSTRING);
			stmt = conn.createStatement();
			rs = conn.getMetaData().getTables(null, null, KAUFEN_TABELE.toUpperCase(), new String[] { "TABLE" });
			if (rs.next()) {
				return;
			}
			String ct = "CREATE TABLE " + KAUFEN_TABELE + " (" + 
			KAUFEN_ID + " INTEGER GENERATED ALWAYS AS IDENTITY, "
					+ KAUFEN_BUCH_ID + " INTEGER," + 
			KAUFEN_MITGLIED_ID + " INTEGER," + 
					"PRIMARY KEY(" + KAUFEN_ID
					+ ")," + "FOREIGN KEY(" + KAUFEN_BUCH_ID + 
					") REFERENCES " + BUCH_TABELE + "(" + BUCH_ID + "),"
					+ "FOREIGN KEY(" + KAUFEN_MITGLIED_ID +
					") REFERENCES " + MITGLIED_TABELE + "(" + MITGLIED_ID + ")"
					+ ")";

			stmt.executeUpdate(ct);
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				throw e;
			}
		}

	}

	public static void insertKaufen(Kaufen kaufen) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;

		try {
			conn = DriverManager.getConnection(CONNSTRING);
			String insert = "INSERT INTO " + KAUFEN_TABELE + " (" + KAUFEN_BUCH_ID + ", " + KAUFEN_MITGLIED_ID
					+ " )VALUES(" + "?, " + "?)";

			pstmt = conn.prepareStatement(insert);
			pstmt.setInt(1, kaufen.getBuch().getBuchId());
			pstmt.setInt(2, kaufen.getMitglied().getMitgliedId());
			pstmt.executeUpdate();
			String identity = "SELECT IDENTITY_VAL_LOCAL() FROM " + KAUFEN_TABELE;
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(identity);
			if (rs.next())
				kaufen.setKaufenId(rs.getInt("1"));

		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				throw e;
			}

		}

	}



	public static ArrayList<Kaufen> getKaufen() throws SQLException {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Kaufen> alc = new ArrayList<>();

		try {
			conn = DriverManager.getConnection(CONNSTRING);
			String select = "SELECT * FROM " + KAUFEN_TABELE + " INNER JOIN " + BUCH_TABELE + " ON " + KAUFEN_TABELE
					+ "." + KAUFEN_BUCH_ID + "=" + BUCH_TABELE + "." + BUCH_ID + " INNER JOIN " + MITGLIED_TABELE
					+ " ON " + KAUFEN_TABELE + "." + KAUFEN_MITGLIED_ID + "=" + MITGLIED_TABELE + "." + MITGLIED_ID;

			stmt = conn.prepareStatement(select);
			rs = stmt.executeQuery();
			while (rs.next())
				alc.add(new Kaufen(rs.getInt(KAUFEN_ID),
						new Buch(rs.getInt(BUCH_ID), rs.getInt(BUCH_ISBN), rs.getString(BUCH_AUTOR),
								rs.getString(BUCH_TITLE), rs.getString(BUCH_THEMA), rs.getInt(BUCH_JAHR),
								rs.getDouble(BUCH_PREIS),rs.getBoolean(BUCH_VERKAUFEN), rs.getString(BUCH_ABSENDERADRESSE)),
						new Mitglied(rs.getInt(MITGLIED_ID), rs.getString(MITGLIED_VORNAME),
								rs.getString(MITGLIED_FAMILIENNAME), rs.getString(MITGLIED_ADRESSE),
								rs.getString(MITGLIED_TELEFONNUMMER), rs.getString(MITGLIED_BENUTZERNAME),
								rs.getString(MITGLIED_KENNWORT))));
			rs.close();

		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				throw e;
			}
		}

		return alc;

	}


	public static void createLeihen() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {

			conn = DriverManager.getConnection(CONNSTRING);
			stmt = conn.createStatement();
			rs = conn.getMetaData().getTables(null, null, LEIHEN_TABELE.toUpperCase(), new String[] { "TABLE" });
			if (rs.next()) {
				return;
			}

			String ct = "CREATE TABLE " + LEIHEN_TABELE + " (" + LEIHEN_ID + " INTEGER GENERATED ALWAYS AS IDENTITY,"
					+ LEIHEN_BUCH_ID + " INTEGER," + LEIHEN_MITGLIED_ID + " INTEGER," + LEIHEN_FROM + " DATE,"
					+ LEIHEN_TO + " DATE," + "PRIMARY KEY(" + LEIHEN_ID + ")," + "FOREIGN KEY(" + LEIHEN_BUCH_ID
					+ ") REFERENCES " + BUCH_TABELE + "(" + BUCH_ID + ")," + "FOREIGN KEY(" + LEIHEN_MITGLIED_ID
					+ ") REFERENCES " + MITGLIED_TABELE + "(" + MITGLIED_ID + ")" + ")";

			stmt.executeUpdate(ct);
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				throw e;
			}
		}

	}

	public static void insertLeihen(Leihen leihen) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;

		try {
			conn = DriverManager.getConnection(CONNSTRING);
			String insert = "INSERT INTO " + LEIHEN_TABELE + " (" + LEIHEN_BUCH_ID + ", " + LEIHEN_MITGLIED_ID + ", "
					+ LEIHEN_FROM + ", " + LEIHEN_TO + ") VALUES(" + "?, " + "?, " + "?, " + "?)";

			pstmt = conn.prepareStatement(insert);
			pstmt.setInt(1, leihen.getBuch().getBuchId());
			pstmt.setInt(2, leihen.getMitglied().getMitgliedId());
			LocalDateTime ldt = LocalDateTime.of(leihen.getFromDate(), LocalTime.of(0, 0, 0));
			java.sql.Date date = new java.sql.Date(ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
			pstmt.setDate(3, date);
			ldt = LocalDateTime.of(leihen.getToDate(), LocalTime.of(0, 0, 0));
			date = new java.sql.Date(ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
			pstmt.setDate(4, date);
			pstmt.executeUpdate();
			String identity = "SELECT IDENTITY_VAL_LOCAL() FROM " + LEIHEN_TABELE;
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(identity);
			if (rs.next())
				leihen.setLeihenId(rs.getInt("1"));

		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				throw e;
			}

		}

	}

	

	public static ArrayList<Leihen> getLeihen() throws SQLException {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Leihen> alc = new ArrayList<>();

		try {
			conn = DriverManager.getConnection(CONNSTRING);
			String select = "SELECT * FROM " + LEIHEN_TABELE + " INNER JOIN " + BUCH_TABELE + " ON " + LEIHEN_TABELE
					+ "." + LEIHEN_BUCH_ID + "=" + BUCH_TABELE + "." + BUCH_ID + " INNER JOIN " + MITGLIED_TABELE
					+ " ON " + LEIHEN_TABELE + "." + LEIHEN_MITGLIED_ID + "=" + MITGLIED_TABELE + "." + MITGLIED_ID;

			stmt = conn.prepareStatement(select);

			rs = stmt.executeQuery();
			while (rs.next())
				alc.add(new Leihen(rs.getInt(LEIHEN_ID),
						new Buch(rs.getInt(BUCH_ID), rs.getInt(BUCH_ISBN), rs.getString(BUCH_AUTOR),
								rs.getString(BUCH_TITLE), rs.getString(BUCH_THEMA), rs.getInt(BUCH_JAHR),
								rs.getDouble(BUCH_PREIS),rs.getBoolean(BUCH_VERKAUFEN), rs.getString(BUCH_ABSENDERADRESSE)),
						new Mitglied(rs.getInt(MITGLIED_ID), rs.getString(MITGLIED_VORNAME),
								rs.getString(MITGLIED_FAMILIENNAME), rs.getString(MITGLIED_ADRESSE),
								rs.getString(MITGLIED_TELEFONNUMMER), rs.getString(MITGLIED_BENUTZERNAME),
								rs.getString(MITGLIED_KENNWORT)),
						rs.getDate(LEIHEN_FROM).toLocalDate(), rs.getDate(LEIHEN_TO).toLocalDate()));
			rs.close();

		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				throw e;
			}
		}

		return alc;

	}


	public static void createAnzeige() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {


			conn = DriverManager.getConnection(CONNSTRING);
			stmt = conn.createStatement();
			rs = conn.getMetaData().getTables(null, null, ANZEIGE_TABELE.toUpperCase(), new String[] { "TABLE" });
			if (rs.next()) {
				return;
			}
			String ct = "CREATE TABLE " + ANZEIGE_TABELE + " (" +
			ANZEIGE_ID + " INTEGER GENERATED ALWAYS AS IDENTITY, "
					+ ANZEIGE_BUCH_ID + " INTEGER," + 
					ANZEIGE_MITGLIED_ID + " INTEGER," +
					"PRIMARY KEY(" + ANZEIGE_ID
					+ ")," + "FOREIGN KEY(" + ANZEIGE_BUCH_ID + ") REFERENCES " + BUCH_TABELE + "(" + BUCH_ID 
					+ "),"+ "FOREIGN KEY(" + ANZEIGE_MITGLIED_ID + ") REFERENCES " + MITGLIED_TABELE + "(" + MITGLIED_ID 
					+ ")"+ ")";

			stmt.executeUpdate(ct);
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				throw e;
			}
		}

	}

	public static void insertAnzeige(AnzeigeAufgeben anzeige) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;

		try {
			conn = DriverManager.getConnection(CONNSTRING);
			String insert = "INSERT INTO " + ANZEIGE_TABELE + " (" + ANZEIGE_BUCH_ID
				+ ", " + ANZEIGE_MITGLIED_ID
					+ " )VALUES(" 
                  + "?, " 
					+ "?)";

			pstmt = conn.prepareStatement(insert);
			pstmt.setInt(1, anzeige.getBuch().getBuchId());
			pstmt.setInt(2, anzeige.getMitglied().getMitgliedId());
			pstmt.executeUpdate();
			String identity = "SELECT IDENTITY_VAL_LOCAL() FROM " + ANZEIGE_TABELE;
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(identity);
			if (rs.next())
				anzeige.setAnzeigeAufgebenId(rs.getInt("1"));

		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				throw e;
			}

		}

	}



	public static ArrayList<AnzeigeAufgeben> getAnzeigeAufgeben() throws SQLException {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<AnzeigeAufgeben> alc = new ArrayList<>();

		try {
			conn = DriverManager.getConnection(CONNSTRING);
			String select = "SELECT * FROM " + ANZEIGE_TABELE + " INNER JOIN " + BUCH_TABELE + " ON " + ANZEIGE_TABELE
					+ "." + ANZEIGE_BUCH_ID + "=" + BUCH_TABELE + "." + BUCH_ID 
					+ " INNER JOIN " + MITGLIED_TABELE+ " ON " + ANZEIGE_TABELE + "." + ANZEIGE_MITGLIED_ID + "=" + MITGLIED_TABELE + "." + MITGLIED_ID
					;

			stmt = conn.prepareStatement(select);
			rs = stmt.executeQuery();
			while (rs.next())
				alc.add(new AnzeigeAufgeben(rs.getInt(ANZEIGE_ID),
						new Buch(rs.getInt(BUCH_ID), rs.getInt(BUCH_ISBN), rs.getString(BUCH_AUTOR),
								rs.getString(BUCH_TITLE), rs.getString(BUCH_THEMA), rs.getInt(BUCH_JAHR),
								rs.getDouble(BUCH_PREIS),rs.getBoolean(BUCH_VERKAUFEN), rs.getString(BUCH_ABSENDERADRESSE))
						,
						new Mitglied(rs.getInt(MITGLIED_ID), rs.getString(MITGLIED_VORNAME),
								rs.getString(MITGLIED_FAMILIENNAME), rs.getString(MITGLIED_ADRESSE),
								rs.getString(MITGLIED_TELEFONNUMMER), rs.getString(MITGLIED_BENUTZERNAME),
								rs.getString(MITGLIED_KENNWORT))
						));
			rs.close();

		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				throw e;
			}
		}

		return alc;

	}

	public static ArrayList<AnzeigeAufgeben> lesseAnzeige() throws SQLException {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<AnzeigeAufgeben> alc = new ArrayList<>();
		
		try {
			conn = DriverManager.getConnection(CONNSTRING);
			String select = "SELECT * FROM " + ANZEIGE_TABELE + " INNER JOIN " + BUCH_TABELE + " ON " + ANZEIGE_TABELE
					+ "." + ANZEIGE_BUCH_ID + "=" + BUCH_TABELE + "." + BUCH_ID 
					+ " INNER JOIN " + MITGLIED_TABELE+ " ON " + ANZEIGE_TABELE + "." + ANZEIGE_MITGLIED_ID + "=" + MITGLIED_TABELE + "." + MITGLIED_ID
					;

			stmt = conn.prepareStatement(select);

			rs = stmt.executeQuery();
			while (rs.next())
				alc.add(new AnzeigeAufgeben(rs.getInt(ANZEIGE_ID),
						new Buch(rs.getInt(BUCH_ID), rs.getInt(BUCH_ISBN), rs.getString(BUCH_AUTOR),
								rs.getString(BUCH_TITLE), rs.getString(BUCH_THEMA), rs.getInt(BUCH_JAHR),
								rs.getDouble(BUCH_PREIS),rs.getBoolean(BUCH_VERKAUFEN), rs.getString(BUCH_ABSENDERADRESSE))
						,new Mitglied(rs.getInt(MITGLIED_ID), rs.getString(MITGLIED_VORNAME),
								rs.getString(MITGLIED_FAMILIENNAME), rs.getString(MITGLIED_ADRESSE),
								rs.getString(MITGLIED_TELEFONNUMMER), rs.getString(MITGLIED_BENUTZERNAME),
								rs.getString(MITGLIED_KENNWORT))
						));
			rs.close();

		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				throw e;
			}
		}

		return alc;

	}

}
