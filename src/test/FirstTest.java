package test;

import main.DatabaseHandler;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FirstTest {
	
	private int numberOfQuestions = 40;
	private DatabaseHandler dbh = new DatabaseHandler(numberOfQuestions, "testDB.db");
	
	@Test(description="Create a table in the database, if necessary")
	public void createTable() {
		Assert.assertTrue(dbh.createDatabase());
	}
	
	@Test(dependsOnMethods = {"createTable"}, description="Add some data to the database")
	public void fillDatabase() {
		Assert.assertTrue(dbh.fillDatabase());
		System.out.println(dbh.getRowCount());
	}
	
	@Test(dependsOnMethods = {"fillDatabase"}, description="Make sure each question is associated with the correct answer")
	public void checkDatabaseContent() {
		for(int i=1; i<=numberOfQuestions; i++) {
			String[] qna = dbh.getQuestionAndAnswer(i);
			Assert.assertEquals(evaluate(qna[0]), Integer.parseInt(qna[1]));
		}
	}
	
	@Test(dependsOnMethods = {"checkDatabaseContent"})
	public void cleanUp() {
		
		//Assert.assertTrue(dbh.dropDatabase());
		//Assert.assertTrue(dbh.disconnect());
	}
	
	@Test(priority = -1, description="Make sure the helper method is working as intended")
	public void testEvaluate() {
		Assert.assertEquals(evaluate("2 + 7"), 9);
		Assert.assertEquals(evaluate("2 - 7"), -5);
		Assert.assertEquals(evaluate("2 * 7"), 14);
		Assert.assertEquals(evaluate("8 + 9"), 17);
		Assert.assertEquals(evaluate("8 - 9"), -1);
		Assert.assertEquals(evaluate("8 * 9"), 72);
	}
	
	private int evaluate(String s) {
		String[] parts = s.split(" ");
		if(parts[1].equals("+")) {
			return Integer.parseInt(parts[0]) + Integer.parseInt(parts[2]);
		}else if(parts[1].equals("-")) {
			return Integer.parseInt(parts[0]) - Integer.parseInt(parts[2]);
		}else {
			return Integer.parseInt(parts[0]) * Integer.parseInt(parts[2]);
		}
	}
	
}
