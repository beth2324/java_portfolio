package lab_6;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Poems extends JFrame
{
	//instantiating code
	public static void main(String[] args) 
	{
		new Poems();
	}
	
	//poem for parsing
	private String emily_dickinson = "Ah, Moon and Star! You are very far But were no one farther than you "
			+ "Do you think I'd stop for a firmament Or a cubit or so? I could borrow a Bonnet of "
			+ "the Lark And a Chamois' Silver Boot And a stirrup of an Antelope And leap to you tonight!"
			+ " But Moon and Star Though you're very far There is one farther than you He is more than"
			+ " a firmament from Me So I cannot go!";
	private String emily = emily_dickinson.replace(" ","");
	
	//entry panel info
	private JLabel letter_label = new JLabel("Letter to Count: "); private JTextField letter = new JTextField(20); 
	private JLabel thread_label = new JLabel("Number of Threads: "); private JTextField threads = new JTextField(5);
	//buttons
	private JButton cancel = new JButton("Cancel");	private JButton start = new JButton("Start");
	//results area
	private JLabel output_name = new JLabel(""); private JTextField output = new JTextField(40);
	//formatting interactive panels of guis
	public JPanel entry()
	{
		JPanel input = new JPanel();
		input.setLayout(new GridLayout(3,2));
		input.add(letter_label);
		input.add(letter);
		input.add(thread_label);
		input.add(threads);	
		return input;
	}
	public JPanel gui()
	{
		JPanel results = new JPanel();
		setLayout(new GridLayout(1,2));
		cancel.addActionListener(new stop());
		start.addActionListener(new go());
		results.add(start);
		results.add(cancel);
		results.add(output_name);
		results.add(output);
		return results;
	}	
	
	
	//constructing components of the main gui
	public Poems()
	{
		super("Analysis of Poem Characters");
		add(gui(), BorderLayout.SOUTH);
		setLocationRelativeTo(null);
		setSize(700,100);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//coordinating actions, data, and threads after gui use
	public class go implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			terminate=false;
			JOptionPane.showMessageDialog(null,entry(),"Poem Program Parameter Entry",JOptionPane.INFORMATION_MESSAGE);
			query = letter.getText();
			number = Integer.parseInt(threads.getText());
			mother_hen = new CountDownLatch(number);
			seconds_time=0; match_counter=new AtomicInteger(0); 
			update_gui("Live Results: ",seconds_time,match_counter.get());
			
			//support for multiple threads
			poem_section_size = emily.length()/number;
			for(int x=0;x<number;x++)
			{
				beginning = x*poem_section_size;
				end = beginning +poem_section_size;
				section = emily.substring(beginning,end);
				new Thread(new calculate_slow(section)).start();
			}
			new Thread(new ending()).start();
		}
	}
	public class stop implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			timer.stop();
			terminate=true;
			output_name.setText("Final Results: ");
			if(last_time!=0)
				output.setText(match_counter.get()+" found in "+seconds_time+" seconds");
			last_time=seconds_time;
		}
	}
	
	//refreshing main gui after use
	private void update_gui(String status, int seconds, int matches)
	{
		start.setText("Restart");
		output_name.setText(status);
		if(seconds>=120)
		{
			int min = seconds/60;
			int sec = seconds-(min*60);
			output.setText(matches+" counted in "+(min)+" minutes and "+sec+" seconds");
		}
		if(seconds>60)
		{
			int min = seconds/60;
			int sec = seconds-(min*60);
			output.setText(matches+" counted in "+(min)+" minute and "+sec+" seconds");
		}
		else
			output.setText(matches+" counted in "+(seconds)+" seconds");
	}	
	
	
	//workers behind the scenes ----------------------------------------------
	
	
	//variables for global use
	private int seconds_time=0; private AtomicInteger match_counter =new AtomicInteger(0);
	private int number=0; private String query = "";  
	int poem_section_size=0; int beginning = 0; int end = 0; String section = "";
	private String [] indv_chars; int last_time=0; private CountDownLatch mother_hen; boolean terminate = false;
	
	//timer that outputs info every few seconds
	Timer timer = new Timer(1000, new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			seconds_time++;
			if(seconds_time%5==0)
			{
				update_gui("Live Results: ",seconds_time,match_counter.get());
			}
		}
	});

	//thread that does something slow
	private class calculate_slow implements Runnable
	{
		private String characters;
		public calculate_slow(String characters)
		{
			this.characters = characters;
		}	
		public void run()
		{
			try
			{
				timer.start();
				indv_chars= characters.split("");
				for(String item: indv_chars)
				{
					Thread.sleep(50);
					if(terminate)
						break;
					if(item.equals(query))
					{
						match_counter.incrementAndGet();
					}
				}
				mother_hen.countDown();
			}
			catch(Exception ex)
			{
				output.setText(ex.getMessage());
				ex.printStackTrace();
			}
		}
		
	}
	private class ending implements Runnable
	{
		@Override
		public void run()
		{
			try
			{
				mother_hen.await();
				if(terminate!=true)
				{
					timer.stop();
					output_name.setText("Final Results: ");
					if(last_time!=0)
						output.setText(match_counter.get()+" found in "+seconds_time+" seconds -> "+(last_time-seconds_time)+" seconds  faster than last time");
					last_time=seconds_time;
				}
			}
			catch (InterruptedException ex)
			{
				ex.printStackTrace();
			}
		}
	}
}
