import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
	private int totalQuestions = 20;
	private QuizModel qm = new QuizModel();
	private String selectedAnswer = "";
	private int correctAnswers = 0;
	private String currentQuestion = "";
	private String currentAnswer = "";
	
	//Panels
	private JPanel top = new JPanel(new GridLayout(2, 1));
	private JPanel quizPanel = new JPanel(new GridLayout(4,1));
	private JPanel bottom = new JPanel(new GridLayout(2, 1));
	
	//Labels
	private JLabel topInfo = new JLabel("Question " + questionCount + "/" + totalQuestions);
	private JLabel question = new JLabel("ask something");
	private JLabel bottomInfo = new JLabel("Pass/Fail");
	
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
					bottomInfo.setText("Select an option before submitting!");
				}else if(submit.getText().equals("Next question")) {
					askQuestion();
					submit.setText("Submit");
					bottomInfo.setText("");
					bg.clearSelection();
					questionCount++;
					topInfo.setText("Question " + questionCount + "/" + totalQuestions);
					//TODO - provide feedback when all questions have been answered.
				}else {
					if(selectedAnswer.equals(currentAnswer)) {
						bottomInfo.setForeground(Color.GREEN);
						bottomInfo.setText("Correct answer!");
						correctAnswers++;
					}else {
						bottomInfo.setForeground(Color.RED);
						bottomInfo.setText("Incorrect answer! The right choice was " + currentAnswer);
					}
					submit.setText("Next question");
				}
			}
		});
	}
	
	private void askQuestion() {
		String[] qna = qm.getQuestionAndAnswer();
		currentQuestion = qna[0];
		question.setText(currentQuestion);
		currentAnswer = qna[1];
		Random random = new Random();
		
		ArrayList<Integer> questionOptions = new ArrayList<Integer>();
		questionOptions.add(Integer.parseInt(currentAnswer));
		//TODO fix
		questionOptions.add(random.nextInt(Integer.parseInt(currentAnswer)) + random.nextInt(20)-10);
		questionOptions.add(random.nextInt(Integer.parseInt(currentAnswer)) + random.nextInt(20)-10);
		questionOptions.add(random.nextInt(Integer.parseInt(currentAnswer))  + random.nextInt(20)-10);
		
		Collections.shuffle(questionOptions);
		
		for(int i=0; i<questionOptions.size(); i++) {
			rbuttons[i].setText(questionOptions.get(i).toString());
		}
	}
	
	ActionListener rbListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JRadioButton rb = (JRadioButton)e.getSource();
			selectedAnswer = rb.getText();
			//System.out.println(rb.getText());
		}
	};
	
	
	
}
