package klasse;
import java.sql.SQLException;


public class Datenbank_main {

	public static void main(String[] args) {

		try {
			Datenbank.createBooks();
			
			System.out.println("Bibliothek created");
		} catch (SQLException e) {
			System.out.println(e);
			return;
		}

	
	
	}

}
