import java.util.Stack;
import java.util.Scanner;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import javax.swing.ImageIcon;
import net.coobird.thumbnailator.Thumbnails;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.util.EmptyStackException;

public class Calc {
	private Stack<Double> stack1;
	private String currentExpression="";
	private  JFrame frame;
	private JPanel panel;
	private Display myDisp;    //If it was defined at the same place it was initialized(in the constructor) then the listeners would not be able to access it 
	public static double buttonScaleFactor = 0.1;
	
	public Calc() {
		stack1= new Stack<Double>();
		frame= new JFrame("RPN Calculator-iOS style");
		panel= new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setLayout( new GridBagLayout() );
		GridBagConstraints rules;
		
		myDisp= new Display(6);		//The column number is ignored-Don't know why, but don't care either since sizing work decently-even better that if the column number had effect
		rules = new GridBagConstraints();
		rules.gridx=0;
		rules.gridy=0;
		rules.gridwidth=4;
		rules.fill=GridBagConstraints.HORIZONTAL;
		rules.insets=new Insets(0,20,0,20);
		panel.add(myDisp,rules);
		
		rules = new GridBagConstraints();	//Settings that will be applied for all the buttons
		rules.ipadx=20;
		rules.ipady=20;
		
		Button BUTTONAC=new Button(buttonScaleFactor, "AcButtonIcon");
		BUTTONAC.addActionListener(new ButtonAcListener() );
		rules.gridx=0;
		rules.gridy=1;
		panel.add( BUTTONAC,rules );
		
		Button BUTTONDEL=new Button(buttonScaleFactor, "DelButtonIcon");
		BUTTONDEL.addActionListener(new ButtonDelListener() );
		rules.gridx=1;
		rules.gridy=1;
		panel.add( BUTTONDEL ,rules);
		
		Button BUTTONHIST=new Button(buttonScaleFactor, "HistButtonIcon");
		BUTTONHIST.addActionListener(new ButtonHistListener() );
		rules.gridx=2;
		rules.gridy=1;
		panel.add( BUTTONHIST,rules );
		
		Button BUTTONRES=new Button(buttonScaleFactor, "ResButtonIcon");
		BUTTONRES.addActionListener(new ButtonResListener() );
		rules.gridx=3;
		rules.gridy=1;
		panel.add( BUTTONRES,rules );
		
		Button BUTTON7=new Button(buttonScaleFactor, "7ButtonIcon");
		BUTTON7.addActionListener(new Button7Listener() );
		rules.gridx=0;
		rules.gridy=2;
		panel.add( BUTTON7,rules );
		
		Button BUTTON8=new Button(buttonScaleFactor, "8ButtonIcon");
		BUTTON8.addActionListener(new Button8Listener() );
		rules.gridx=1;
		rules.gridy=2;
		panel.add( BUTTON8,rules );
		
		Button BUTTON9=new Button(buttonScaleFactor, "9ButtonIcon");
		BUTTON9.addActionListener(new Button9Listener() );
		rules.gridx=2;
		rules.gridy=2;
		panel.add( BUTTON9 ,rules);
		
		Button BUTTONMULT=new Button(buttonScaleFactor, "MultButtonIcon");
		BUTTONMULT.addActionListener(new ButtonMultListener() );
		rules.gridx=3;
		rules.gridy=2;
		panel.add( BUTTONMULT,rules );
		
		Button BUTTON4=new Button(buttonScaleFactor, "4ButtonIcon");
		BUTTON4.addActionListener(new Button4Listener() );
		rules.gridx=0;
		rules.gridy=3;
		panel.add( BUTTON4,rules );
		
		Button BUTTON5=new Button(buttonScaleFactor, "5ButtonIcon");
		BUTTON5.addActionListener(new Button5Listener() );
		rules.gridx=1;
		rules.gridy=3;
		panel.add( BUTTON5 , rules);
		
		Button BUTTON6=new Button(buttonScaleFactor, "6ButtonIcon");
		BUTTON6.addActionListener(new Button6Listener() );
		rules.gridx=2;
		rules.gridy=3;
		panel.add( BUTTON6,rules );
		
		Button BUTTONDIV=new Button(buttonScaleFactor, "DivButtonIcon");
		BUTTONDIV.addActionListener(new ButtonDivListener() );
		rules.gridx=3;
		rules.gridy=3;
		panel.add( BUTTONDIV ,rules);
		
		Button BUTTON1=new Button(buttonScaleFactor, "1ButtonIcon");
		BUTTON1.addActionListener(new Button1Listener() );
		rules.gridx=0;
		rules.gridy=4;
		panel.add( BUTTON1, rules );
		
		Button BUTTON2=new Button(buttonScaleFactor, "2ButtonIcon");
		BUTTON2.addActionListener(new Button2Listener() );
		rules.gridx=1;
		rules.gridy=4;
		panel.add( BUTTON2 ,rules);
		
		Button BUTTON3=new Button(buttonScaleFactor, "3ButtonIcon");
		BUTTON3.addActionListener(new Button3Listener() );
		rules.gridx=2;
		rules.gridy=4;
		panel.add( BUTTON3,rules );
		
		Button BUTTONADD=new Button(buttonScaleFactor, "AddButtonIcon");
		BUTTONADD.addActionListener(new ButtonAddListener() );
		rules.gridx=3;
		rules.gridy=4;
		panel.add( BUTTONADD,rules );
		
		Button BUTTON0=new Button(buttonScaleFactor, "0ButtonIcon");
		BUTTON0.addActionListener(new Button0Listener() );
		rules.gridx=0;
		rules.gridy=5;
		panel.add( BUTTON0 ,rules);
		
		Button BUTTONDOT=new Button(buttonScaleFactor, "DotButtonIcon");
		BUTTONDOT.addActionListener(new ButtonDotListener() );
		rules.gridx=1;
		rules.gridy=5;
		panel.add( BUTTONDOT,rules );
		
		Button BUTTONSPACE=new Button(buttonScaleFactor, "SpaceButtonIcon");
		BUTTONSPACE.addActionListener(new ButtonSpaceListener() );
		rules.gridx=2;
		rules.gridy=5;
		panel.add( BUTTONSPACE,rules );
		
		Button BUTTONSUB=new Button(buttonScaleFactor, "SubButtonIcon");
		BUTTONSUB.addActionListener(new ButtonSubListener() );
		rules.gridx=3;
		rules.gridy=5;
		panel.add( BUTTONSUB, rules);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);
		frame.setSize(500, 800);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	private double evaluateExpression(){
		String term;
		Scanner sc = new Scanner(currentExpression);
		sc.useDelimiter(" ");
		
		while( sc.hasNext() ){
			term=sc.next();
			
			if ( term.equals("+") ){
				double topmost = stack1.pop();
				double downmost = stack1.pop();
				double result= downmost+topmost;
				stack1.push(result);
			}
			else if (  term.equals("-")  ){
				double topmost = stack1.pop();
				double downmost = stack1.pop();
				double result= downmost-topmost;
				stack1.push(result);
			}
			else if (  term.equals("*")  ){
				double topmost = stack1.pop();
				double downmost = stack1.pop();
				double result= downmost*topmost;
				stack1.push(result);
			}
			else if (  term.equals("/") ){
				double topmost = stack1.pop();
				double downmost = stack1.pop();
				double result= downmost/topmost;
				stack1.push(result);
			}
			
			else{	//Reading an operand
				stack1.push( Double.parseDouble(term) );
			}
		}
		
		return stack1.pop();
			
	}
	
	class ButtonAcListener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			stack1= new Stack();
			currentExpression="";
			myDisp.setText(currentExpression);
			stack1=new Stack();
		}
	}
	class ButtonDelListener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			try {
				currentExpression= currentExpression.substring(0,  currentExpression.length()-1 );
				myDisp.setText(currentExpression);
			} catch (StringIndexOutOfBoundsException ex) {}		//This happened when you pressed delete when the expression string was empty, thus causing substring() to throw an error. Do nothing as currentExpression remains the same, as does the display output
			}
	}
	class ButtonHistListener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			
		}
	}
	class ButtonResListener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			try {
				double result=evaluateExpression();
				currentExpression=Double.toString(result);
				myDisp.setText(currentExpression);
			} catch(EmptyStackException|NumberFormatException ex) {    //The first exception happens when ,due to a user error, we try to pop the stack to perform an operation when it is empty. The second happens when the user gives an operator immediately after an operand, thus causing the parseDouble in the evaluateExpression, which runs when a term is neither an operator nor an operand as in this case, to throw the exception 
				//stack1= new Stack();--->Not needed since we got EmptyStackException(which means the stack is empty)
				currentExpression="";	//Pure genius! This way all the other button will not try to process the string "error"
				myDisp.setText("3rr0r");
			}
		}
	}
	
	class Button7Listener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			currentExpression=currentExpression+"7";
			myDisp.setText(currentExpression);
		}
	}
	class Button8Listener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			currentExpression=currentExpression+"8";
			myDisp.setText(currentExpression);
		}
	}
	class Button9Listener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			currentExpression=currentExpression+"9";
			myDisp.setText(currentExpression);
		}
	}
	class ButtonMultListener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			currentExpression=currentExpression+"*";
			myDisp.setText(currentExpression);
		}
	}
	
	class Button4Listener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			currentExpression=currentExpression+"4";
			myDisp.setText(currentExpression);
		}
	}
	class Button5Listener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			currentExpression=currentExpression+"5";
			myDisp.setText(currentExpression);
		}
	}
	class Button6Listener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			currentExpression=currentExpression+"6";
			myDisp.setText(currentExpression);
		}
	}
	class ButtonDivListener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			currentExpression=currentExpression+"/";
			myDisp.setText(currentExpression);
		}
	}
	
	class Button1Listener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			currentExpression=currentExpression+"1";
			myDisp.setText(currentExpression);
		}
	}
	class Button2Listener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			currentExpression=currentExpression+"2";
			myDisp.setText(currentExpression);
		}
	}
	class Button3Listener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			currentExpression=currentExpression+"3";
			myDisp.setText(currentExpression);
		}
	}
	class ButtonAddListener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			currentExpression=currentExpression+"+";
			myDisp.setText(currentExpression);
		}
	}
	
	class Button0Listener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			currentExpression=currentExpression+"0";
			myDisp.setText(currentExpression);
		}
	}
	class ButtonDotListener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			currentExpression=currentExpression+".";
			myDisp.setText(currentExpression);
		}
	}
	class ButtonSpaceListener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			currentExpression=currentExpression+" ";
			myDisp.setText(currentExpression);
		}
	}
	class ButtonSubListener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			currentExpression=currentExpression+"-";
			myDisp.setText(currentExpression);
		}
	}
	
}
