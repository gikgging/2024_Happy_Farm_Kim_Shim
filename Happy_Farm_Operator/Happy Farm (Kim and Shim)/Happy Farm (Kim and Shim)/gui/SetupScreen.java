package gui;

import main.GameCenter;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

//SetupScreen screen class with GUI
public class SetupScreen 
{

   //The setup screen Frame
   private JFrame setupScreenFrame;
   
   //The manager from GameCenter class
   private GameCenter manager;
   
   //The text field for user name
   private JTextField nameTextField;
   
   //The text field for farm name
   private JTextField farmNameTextField;
   
   //The warning label that shows a warning message.
   private JLabel warningLabel;

   //The constructor of the SetupScreen. With GameCenter incomingManager, the gui can be managed with GameCenter.
   public SetupScreen(GameCenter incomingManager) 
   {
      this.manager = incomingManager;
      initialize();
      setupScreenFrame.setVisible(true);
   }
   
   //The function to close the setupScreen Frame.
   public void closeWindow() 
   {
      setupScreenFrame.dispose();
   }
   
   //The function to call closeWindow to close Setup screen.
   public void finishWindow() 
   {
      manager.closeSetupScreen(this);
   }
   
   //Launch main
   public static void main(String[] args) 
   {
      EventQueue.invokeLater(new Runnable() 
      {
         public void run() 
         {
            try 
            {
               SetupScreen window = new SetupScreen();
               window.setupScreenFrame.setVisible(true);
            } 
            catch (Exception e) 
            {
               e.printStackTrace();
            }
         }
      });
   }

   //Application
   public SetupScreen() 
   {
      initialize();
   }

   private void initialize() 
   {
      setupScreenFrame = new JFrame();
      setupScreenFrame.setTitle("Happy Farm!");
      setupScreenFrame.setBounds(100, 100, 1200, 650);
      setupScreenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setupScreenFrame.getContentPane().setLayout(null);
      
      JLabel welcomeLabel = new JLabel("Welcome to Happy Farm");
      welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
      welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
      welcomeLabel.setBounds(10, 0, 1164, 44);
      setupScreenFrame.getContentPane().add(welcomeLabel);
      
      JLabel nameLabel = new JLabel("What is your name?");
      nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
      nameLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
      nameLabel.setBounds(10, 124, 586, 45);
      setupScreenFrame.getContentPane().add(nameLabel);
      
      JLabel nameWarningLabel = new JLabel("(must be between 3 and 15 characters and must not include numbers or special characters)");
      nameWarningLabel.setHorizontalAlignment(SwingConstants.RIGHT);
      nameWarningLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
      nameWarningLabel.setBounds(10, 153, 586, 27);
      setupScreenFrame.getContentPane().add(nameWarningLabel);
      
      nameTextField = new JTextField();
      nameTextField.setFont(new Font("Tahoma", Font.PLAIN, 18));
      nameTextField.setBounds(617, 137, 515, 39);
      setupScreenFrame.getContentPane().add(nameTextField);
      nameTextField.setColumns(10);
      
      JLabel farmNameLabel = new JLabel("What is your farm's name?");
      farmNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
      farmNameLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
      farmNameLabel.setBounds(10, 300, 586, 45);
      setupScreenFrame.getContentPane().add(farmNameLabel);
      
      farmNameTextField = new JTextField();
      farmNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 18));
      farmNameTextField.setColumns(10);
      farmNameTextField.setBounds(617, 316, 515, 39);
      setupScreenFrame.getContentPane().add(farmNameTextField);
      
      warningLabel = new JLabel("");
      warningLabel.setForeground(Color.RED);
      warningLabel.setHorizontalAlignment(SwingConstants.CENTER);
      warningLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
      warningLabel.setBounds(10, 530, 1164, 27);
      setupScreenFrame.getContentPane().add(warningLabel);

      JButton startGameButton = new JButton("Start Game");
      startGameButton.addActionListener(new ActionListener() 
      {
         public void actionPerformed(ActionEvent e) 
         {
            manager.setupGame(nameTextField.getText(), farmNameTextField.getText());
         }
      });
      startGameButton.setFont(new Font("Tahoma", Font.BOLD, 16));
      startGameButton.setBounds(512, 450, 160, 45);
      setupScreenFrame.getContentPane().add(startGameButton);
      
      JLabel farmNameWarningLabel = new JLabel("(must be between 3 and 15 characters and must not include numbers or special characters)");
      farmNameWarningLabel.setHorizontalAlignment(SwingConstants.RIGHT);
      farmNameWarningLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
      farmNameWarningLabel.setBounds(10, 326, 586, 27);
      setupScreenFrame.getContentPane().add(farmNameWarningLabel);
   }
   
   
   //Setup for the text of the warningLabel
   public void setWarningText(String warningType) 
   {
      if (warningType == "") 
      {
         warningLabel.setText("");
      }
      else 
      {
         warningLabel.setText("Warning: Your " + warningType + " invalid (must be between 3 and 15 characters and must not include numbers or special characters)");
      }
   }
}