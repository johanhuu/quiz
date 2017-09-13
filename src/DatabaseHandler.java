import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class DatabaseHandler {
	
	private Connection c = null;
	private Statement s = null;
	private Random random = new Random();
	private String[] operation = {" + ", " - ", " * "};
	int numberOfQuestions;
	
	public DatabaseHandler(int numberOfQuestions, String dbName) {
		this.numberOfQuestions = numberOfQuestions;
		//Set up the db connection
		try {
	        c = DriverManager.getConnection("jdbc:sqlite:"+dbName);
	        s = c.createStatement();
	    } catch ( Exception e ) {
	    	System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	        System.exit(0);
	    }
	}
	
	public boolean createDatabase() {
		String create = "CREATE TABLE IF NOT EXISTS QNA" +
    			"(ID INT PRIMARY KEY NOT NULL," +
    			"QUESTION TEXT NOT NULL," +
    			"ANSWER TEXT NOT NULL)";
		try {
			s.executeUpdate(create);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean fillDatabase() {
		for(int i=1; i<=numberOfQuestions; i++) {
			String selectedOperation = operation[random.nextInt(operation.length)];
			int firstNumber = random.nextInt(10)+1;
			int secondNumber = random.nextInt(10)+1;
			
			String question = Integer.toString(firstNumber) + selectedOperation + Integer.toString(secondNumber);
			int answer = 0;
			if(selectedOperation.equals(" + ")) {
				answer = firstNumber + secondNumber;
			}else if(selectedOperation.equals(" - ")) {
				answer = firstNumber - secondNumber;
			}else {
				answer = firstNumber * secondNumber;
			}
			String insert = "INSERT INTO QNA (ID, QUESTION, ANSWER) VALUES ("+i+", '"+question+"', "+answer+")";
		    try {
				s.executeUpdate(insert);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	public String[] getQuestionAndAnswer(int id) {
		String select = "SELECT * FROM QNA WHERE ID="+id;
	    String[] qna = {"question", "answer"};
		try {
			ResultSet rs = s.executeQuery(select);
		    qna[0] = rs.getString("question");
		    qna[1] = rs.getString("answer");
		}catch(Exception e){
			System.out.println(e);
		}
	    return qna;
	}
	
	public boolean dropDatabase() {
		String query = "DROP TABLE QNA";
		try {
			s.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean disconnect() {
		try {
			s.close();
			c.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public void debug() {
		String select = "SELECT * FROM QNA";
	    String[] qna = {"question", "answer"};
		try {
			ResultSet rs = s.executeQuery(select);
			while(rs.next()) {
				qna[0] = rs.getString("question");
				qna[1] = rs.getString("answer");
				System.out.println(qna[0]);
				System.out.println(qna[1]);
				System.out.println();
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	
	
}
