package lab_5;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class AA_quiz_gui extends JFrame
{
	private JTextField my_text = new JTextField();
	private JButton start = new JButton("START QUIZ");
	private JButton cancel = new JButton("Cancel");
	private JLabel countdown = new JLabel();
	public static String[] SHORT_NAMES = { "A","R", "N", "D", "C", "Q", "E", "G",  "H", "I", "L", "K", "M", "F", "P", "S", "T", "W", "Y", "V" };
	public static String[] FULL_NAMES = {"alanine","arginine", "asparagine", "aspartic acid", "cysteine", "glutamine",  "glutamic acid",	"glycine" ,"histidine","isoleucine", "leucine",  "lysine", "methionine", "phenylalanine", "proline", "serine","threonine","tryptophan", "tyrosine", "valine"};
	private int i=0; private int wrong=0; private int right=0; private int count=0;
	Timer timer = new Timer(1000, new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
				if(count<30)
				{
					count++;
					countdown.setText("Seconds remaining: "+(30-count));
				}
				else
				{
					countdown.setText("TIMES UP");
					my_text.setText("FINAL SCORE: "+right+" right and "+wrong+" wrong");
				}
		}
	});
	

	public JPanel getBottomPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		cancel.addActionListener(new stop());
		start.addActionListener(new begin());
		panel.add(start);
		panel.add(cancel);
		return panel;
	}
	
	
	private class begin implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			right=0;
			wrong=0;
			update_my_text("");
			count=0;
			timer.start();
		}
	}
	
	
	private void update_my_text(String style)
	{
		Random random = new Random();
		i=random.nextInt(FULL_NAMES.length);
		my_text.setText(style+FULL_NAMES[i]+":	");
		validate();
	}
	
	
	private class stop implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			System.exit(0);
		}
	}
	
	
	private class quiz implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String value="";
			value = my_text.getText();
			if(value.substring(value.length()-1).toUpperCase().equals(SHORT_NAMES[i]))
			{
				right++;
				update_my_text("Right! SCORE UPDATE: "+right+" correct and "+wrong+" wrong. Now, ");
			}
			else
			{
				wrong++;
				update_my_text("Wrong. Answer was "+SHORT_NAMES[i]+". SCORE UPDATE: "+right+" correct and "+wrong+" wrong. Now, ");
			}
		}
	}

	
	public AA_quiz_gui(String title)
	{
		super(title);
		setLayout(new BorderLayout());
		add(my_text, BorderLayout.CENTER);
		add(getBottomPanel(), BorderLayout.SOUTH);
		add(countdown, BorderLayout.NORTH);
		setLocationRelativeTo(null);
		setSize(600,200);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		my_text.setText("Press START QUIZ when ready to begin.");
		my_text.addActionListener(new quiz());
	}
	

	public static void main(String[] args) 
	{
		//gui
		new AA_quiz_gui("Amino Acids Quiz");		

	}
}
