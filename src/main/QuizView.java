package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class QuizView extends JFrame{
	
	//Quiz data
	private int questionCount = 1;
	private int numberOfQuestions = 30;
	private QuizModel qm;
	private String selectedAnswer = "";
	private int correctAnswers = 0;
	private String currentQuestion = "";
	private String currentAnswer = "";
	
	//Panels
	private JPanel top = new JPanel(new GridLayout(2, 1));
	private JPanel quizPanel = new JPanel(new GridLayout(4,1));
	private JPanel bottom = new JPanel(new GridLayout(2, 1));
	
	//Labels
	private JLabel topInfo = new JLabel("Question " + questionCount + "/" + numberOfQuestions);
	private JLabel question = new JLabel("ask something");
	private JLabel bottomInfo = new JLabel("");
	
	//Buttons
	private ButtonGroup bg = new ButtonGroup();
	private JRadioButton[] rbuttons = {
		new JRadioButton("Option 1"),
		new JRadioButton("Option 2"),
		new JRadioButton("Option 3"),
		new JRadioButton("Option 4")
	};
	private JButton submit = new JButton("Submit");
	
	public QuizView() {
		
		labelSetup();
		panelSetup();
		buttonSetup();
		this.setVisible(true);
		this.setResizable(false);
		this.setSize(500, 500);
		this.setLayout(new BorderLayout());
		this.add(quizPanel, BorderLayout.CENTER);
		this.add(top, BorderLayout.NORTH);
		this.add(bottom, BorderLayout.SOUTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		qm = new QuizModel(numberOfQuestions);
		askQuestion();
	}
	
	private void labelSetup() {
		topInfo.setHorizontalAlignment(JLabel.CENTER);
		question.setHorizontalAlignment(JLabel.CENTER);
		bottomInfo.setHorizontalAlignment(JLabel.CENTER);
	}
	
	private void panelSetup() {
		//Top panel
		top.setBorder(BorderFactory.createEmptyBorder(50, 10, 50, 10));
		top.add(topInfo);
		top.add(question);
		
		//Bottom panel
		bottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		bottom.add(bottomInfo);
		bottom.add(submit);
	}
	
	private void buttonSetup() {
		for(JRadioButton rb : rbuttons) {
			quizPanel.add(rb);
			bg.add(rb);
			rb.addActionListener(rbListener);
		}
		submit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(selectedAnswer.equals("")) {
					bottomInfo.setForeground(Color.BLACK);
					bottomInfo.setText("Select an option before submitting!");
				}else if(submit.getText().equals("Next question")) {
					askQuestion();
					submit.setText("Submit");
					bottomInfo.setText("");
					bg.clearSelection();
					questionCount++;
					topInfo.setText("Question " + questionCount + "/" + numberOfQuestions);
					selectedAnswer = "";
				}else {
					if(selectedAnswer.equals(currentAnswer)) {
						bottomInfo.setForeground(Color.GREEN);
						bottomInfo.setText("Correct answer!");
						correctAnswers++;
					}else {
						bottomInfo.setForeground(Color.RED);
						bottomInfo.setText("Incorrect answer! The right choice was " + currentAnswer);
					}
					if(questionCount == numberOfQuestions) {
						submit.setVisible(false);
						question.setVisible(false);
						quizPanel.setVisible(false);
						topInfo.setText("Test complete");
						if(correctAnswers >= numberOfQuestions*0.75) {
							bottomInfo.setForeground(Color.GREEN);
						}else {
							bottomInfo.setForeground(Color.RED);
						}
						bottomInfo.setText("Your score: " + correctAnswers + "/" + numberOfQuestions);
					}
					submit.setText("Next question");
				}
			}
		});
	}
	
	//Generates new questions and updates the GUI
	private void askQuestion() {
		String[] qna = qm.getQuestionAndAnswer();
		currentQuestion = qna[0];
		question.setText(currentQuestion);
		currentAnswer = qna[1];
		
		//Add all options to an array and then shuffle
		ArrayList<String> questionOptions = new ArrayList<String>();
		questionOptions.add(currentAnswer);
		questionOptions.addAll(Arrays.asList(qm.getIncorrectOptions(currentAnswer)));
		
		Collections.shuffle(questionOptions);
		
		//Present all options in the GUI
		for(int i=0; i<questionOptions.size(); i++) {
			rbuttons[i].setText(questionOptions.get(i).toString());
		}
	}
	
	//Check which radio button was selected, add the listener to all radio buttons
	ActionListener rbListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JRadioButton rb = (JRadioButton)e.getSource();
			selectedAnswer = rb.getText();
		}
	};
}
