import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuizModel {
	
	private DatabaseHandler dbh;
	private ArrayList<Integer> questionID;
	
	public QuizModel(int numberOfQuestions) {
		dbh = new DatabaseHandler(numberOfQuestions, "quiz.db");
		dbh.createDatabase();
		questionID = new ArrayList<Integer>();
		for(int i=1; i<=numberOfQuestions; i++) {
			questionID.add(i);
		}
		Collections.shuffle(questionID);
	}
		
	public String[] getQuestionAndAnswer() {
		int chosenIndex = questionID.size()-1;
		String[] qna = dbh.getQuestionAndAnswer(questionID.get(chosenIndex));
		questionID.remove(chosenIndex);
		return qna;
	}
	
	public String[] getIncorrectOptions(String answer) {
		int correctAnswer = Integer.parseInt(answer);
		String[] incorrectOptions = {"Option 1", "Option 2", "Option 3"};
		ArrayList<Integer> randomIncorrectAnswers = new ArrayList<Integer>();
		for(int i=correctAnswer-20; i<=correctAnswer+20; i++) {
			if(i != correctAnswer) {
				randomIncorrectAnswers.add(i);
			}
		}
		Random random = new Random();
		for(int i=0; i<incorrectOptions.length; i++) {
			int randomIndex = random.nextInt(randomIncorrectAnswers.size());
			incorrectOptions[i] = Integer.toString(randomIncorrectAnswers.get(randomIndex));
			randomIncorrectAnswers.remove(randomIndex);
		}
		return incorrectOptions;
	}
}
