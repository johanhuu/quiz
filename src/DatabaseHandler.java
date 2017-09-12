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
	
	public DatabaseHandler(int numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
		//Set up the db connection
		try {
	        c = DriverManager.getConnection("jdbc:sqlite:quiz.db");
	        s = c.createStatement();
	    } catch ( Exception e ) {
	    	System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	        System.exit(0);
	    }
		try {
			createDatabase();
			//fillDatabase();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void createDatabase() throws SQLException {
		String create = "CREATE TABLE IF NOT EXISTS QNA" +
    			"(ID INT PRIMARY KEY NOT NULL," +
    			"QUESTION TEXT NOT NULL," +
    			"ANSWER TEXT NOT NULL)";
		s.executeUpdate(create);
	}
	
	public void fillDatabase() throws SQLException{
		for(int i=1; i<=numberOfQuestions; i++) {
			String selectedOperation = operation[random.nextInt(operation.length)];
			int firstNumber = random.nextInt(10)+1;
			int secondNumber = random.nextInt(10)+1;
			System.out.println(selectedOperation);
			
			String question = Integer.toString(firstNumber) + selectedOperation + Integer.toString(secondNumber);
			int answer = 0;
			if(selectedOperation.equals(" + ")) {
				answer = firstNumber + secondNumber;
			}else if(selectedOperation.equals(" - ")) {
				answer = firstNumber - secondNumber;
			}else {
				answer = firstNumber * secondNumber;
			}
			System.out.println(question);
			String insert = "INSERT INTO QNA (ID, QUESTION, ANSWER) VALUES ("+i+", '"+question+"', "+answer+")";
		    s.executeUpdate(insert);
		}
	}
	
	public String[] getQuestionAndAnswer(int id) {
		System.out.println("asdf");
		String select = "SELECT * FROM QNA WHERE ID="+id;
	    String[] qna = {"question", "answer"};
		try {
			ResultSet rs = s.executeQuery(select);
		    qna[0] = rs.getString("question");
		    qna[1] = rs.getString("answer");
		}catch(Exception e){
			System.out.println(e);
		}
		System.out.println(qna[0]);
		System.out.println(qna[1]);
	    return qna;
	}
	
	public void disconnect() throws SQLException {
		s.close();
		c.close();
	}
	
	public void dropDatabase() throws SQLException {
		String query = "DROP TABLE IF EXISTS QNA";
		s.executeUpdate(query);
	}
	
	public void debug() throws SQLException {
		String select = "SELECT * FROM QNA";
	    String[] qna = {"question", "answer"};
	    ResultSet rs = s.executeQuery(select);
		try {
			while(rs.next()) {
				qna[0] = rs.getString("question");
				qna[1] = rs.getString("answer");
				System.out.println(qna[0]);
				System.out.println(qna[1]);
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	
	
}
