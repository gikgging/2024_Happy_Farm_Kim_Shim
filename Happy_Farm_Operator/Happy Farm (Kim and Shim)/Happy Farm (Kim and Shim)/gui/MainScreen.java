package gui;

import main.GameCenter;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import main.Farmer;

//Main screen class with GUI

public class MainScreen 
{
	
	//The main screen Frame
	private JFrame mainScreenFrame;
	
	//The manager from GameCenter class
	private GameCenter manager;
	
	//The farmer from Farmer class
	//private Farmer farmer;
	
	//The constructor of the main screen. With GameCenter incomingManager, the gui can be managed with GameCenter.
	public MainScreen(GameCenter incomingManager) 
	{	
		this.manager = incomingManager;
		initialize(); //Initialize
		mainScreenFrame.setVisible(true);
	}
	
	//The function to close the main screen Frame.
	public void closeWindow() 
	{
		mainScreenFrame.dispose();
	}

	//The function to call closeWindow to close the main screen.
	public void finishWindow() 
	{
		manager.closeMainScreen(this);
	}
	
	//Launch main
	public static void main(String[] args) 
	{
		GameCenter gameCenter = new GameCenter();
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					MainScreen window = new MainScreen(gameCenter);
					window.mainScreenFrame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	//Application

	private void initialize() 
	{
		mainScreenFrame = new JFrame();
		mainScreenFrame.setTitle("Happy Farm - Main Screen");
		mainScreenFrame.setBounds(100, 100, 1200, 650);
		mainScreenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainScreenFrame.getContentPane().setLayout(null);
		
		JPanel headerPanel = new JPanel();
		headerPanel.setBounds(10, 11, 1164, 78);
		headerPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		mainScreenFrame.getContentPane().add(headerPanel);
		headerPanel.setLayout(null);
		
		JLabel farmStatusLabel = new JLabel("Farm Status");
		farmStatusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		farmStatusLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
		farmStatusLabel.setBounds(10, 11, 185, 24);
		headerPanel.add(farmStatusLabel);
		
		JLabel currentMoneyLabel = new JLabel("Current Money: $" + manager.returnMoneyString());
		currentMoneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		currentMoneyLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		currentMoneyLabel.setBounds(409, 43, 347, 24);
		headerPanel.add(currentMoneyLabel);
		
		JLabel freeCropSpaceLabel = new JLabel("Free crop space: " + manager.returnFreeCropSpace());
		freeCropSpaceLabel.setHorizontalAlignment(SwingConstants.LEFT);
		freeCropSpaceLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		freeCropSpaceLabel.setBounds(10, 43, 237, 24);
		headerPanel.add(freeCropSpaceLabel);
		
		JLabel dayLabel = new JLabel("Day: " + manager.returnDays());
		dayLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dayLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dayLabel.setBounds(969, 43, 185, 24);
		headerPanel.add(dayLabel);
		
		JLabel weatherLabel = new JLabel("Current Weather: " + manager.getWeather().getWeatherName());
	    weatherLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    weatherLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
	    weatherLabel.setBounds(409, 10, 347, 24);
	    headerPanel.add(weatherLabel);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBounds(10, 100, 288, 500);
		mainScreenFrame.getContentPane().add(buttonsPanel);
		buttonsPanel.setLayout(null);
		
		JPanel noActionsRequiredPanel = new JPanel();
		noActionsRequiredPanel.setBounds(0, 0, 288, 135);
		buttonsPanel.add(noActionsRequiredPanel);
		noActionsRequiredPanel.setLayout(null);
		
		JButton visitStoreButton = new JButton("Visit Store");
		visitStoreButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				finishWindow();
				manager.launchStoreScreen();
			}
		});
		visitStoreButton.setBounds(10, 11, 268, 50);
		noActionsRequiredPanel.add(visitStoreButton);
		visitStoreButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		visitStoreButton.setBackground(new Color(255, 250, 205));
		
		JButton sleepButton = new JButton("Sleep (Move to next day)");
		sleepButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				manager.nextDay();
				JOptionPane.showMessageDialog(mainScreenFrame, "You have slept!");
				if (manager.gameFinishing())
				{
					JOptionPane.showMessageDialog(mainScreenFrame, manager.finishGame());
					finishWindow();
				}
				else 
				{
					finishWindow();
					manager.launchMainScreen();
				}
			}
		});
		sleepButton.setBounds(10, 73, 268, 50);
		noActionsRequiredPanel.add(sleepButton);
		sleepButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		sleepButton.setBackground(new Color(240, 230, 140));
	
		
		JPanel actionsRequiredPanel = new JPanel();
		actionsRequiredPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		actionsRequiredPanel.setBounds(0, 146, 288, 354);
		buttonsPanel.add(actionsRequiredPanel);
		actionsRequiredPanel.setLayout(null);
		
		JLabel requireActionsLabel = new JLabel("Remain strength: (" + manager.getFarmer().getFarmerStrength()+")");
		requireActionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		requireActionsLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		requireActionsLabel.setForeground(new Color(255, 0, 0));
		requireActionsLabel.setBounds(10, 12, 268, 24);
		actionsRequiredPanel.add(requireActionsLabel);
		
		JButton tendToCropsButton = new JButton("Growing Crop");
		tendToCropsButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (manager.getCrops().size() > 0) 
				{
					finishWindow();
					manager.launchTendCropsScreen();
				}
				else 
				{
					JOptionPane.showMessageDialog(mainScreenFrame, "You have no crops to tend to, so no actions were used");
				}
			}
		});
		tendToCropsButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tendToCropsButton.setBounds(10, 47, 268, 50);
		actionsRequiredPanel.add(tendToCropsButton);
		tendToCropsButton.setBackground(new Color(255, 250, 205));
		

		JButton harvestCropsButton = new JButton("Harvest");
		harvestCropsButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JOptionPane.showMessageDialog(mainScreenFrame, manager.harvestCrops());
				finishWindow();
				manager.launchMainScreen();
			}
		});
		
		harvestCropsButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		harvestCropsButton.setBounds(10, 110, 268, 50);
		actionsRequiredPanel.add(harvestCropsButton);
		harvestCropsButton.setBackground(new Color(255, 250, 205));
		
		// Add Drink RedBull Button
		JButton drinkRedBullButton = new JButton("Drink RedBull");
		drinkRedBullButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		drinkRedBullButton.setBounds(10, 250, 268, 50); 
		actionsRequiredPanel.add(drinkRedBullButton);
		drinkRedBullButton.setBackground(new Color(240, 128, 128));

		// Drink RedBull
		drinkRedBullButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String message = manager.plusStrength("RedBull"); // GameCenter의 plusStrength 메소드 호출
		        JOptionPane.showMessageDialog(mainScreenFrame, message);
		    }
		});
		
		
		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		statusPanel.setBounds(308, 100, 866, 500);
		mainScreenFrame.getContentPane().add(statusPanel);
		statusPanel.setLayout(null);
		
		JLabel statusCropsLabel = new JLabel("Status of your Crops");
		statusCropsLabel.setBounds(10, 11, 846, 49);
		statusPanel.add(statusCropsLabel);
		statusCropsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		statusCropsLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 71, 846, 418);
		statusPanel.add(scrollPane);
		
		JTextArea cropsStatusTextArea = new JTextArea();
		scrollPane.setViewportView(cropsStatusTextArea);
		cropsStatusTextArea.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cropsStatusTextArea.setText(manager.returnStatusCrops());
		cropsStatusTextArea.setEditable(false);
	}



public void showGoalResults(String results) {
    JFrame resultsFrame = new JFrame("Weekly Results");
    resultsFrame.setSize(400, 300);
    resultsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    resultsFrame.setLayout(null);

    JTextArea resultsArea = new JTextArea();
    resultsArea.setText(results);
    resultsArea.setEditable(false);
    resultsArea.setBounds(10, 10, 360, 220);

    JScrollPane scrollPane = new JScrollPane(resultsArea);
    scrollPane.setBounds(10, 10, 360, 220);

    JButton okButton = new JButton("OK");
    okButton.setBounds(150, 240, 100, 30);
    okButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            resultsFrame.dispose(); //Close the window
        }
    });

    resultsFrame.add(scrollPane);
    resultsFrame.add(okButton);
    resultsFrame.setVisible(true);
}
}