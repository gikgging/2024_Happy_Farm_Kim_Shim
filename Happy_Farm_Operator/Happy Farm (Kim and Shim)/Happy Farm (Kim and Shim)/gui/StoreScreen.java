package gui;

import main.GameCenter;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import main.ProductFactory;

//StoreScreen screen class with GUI
public class StoreScreen 
{

	//The store Frame
	private JFrame storeFrame;
	
	//The manager from GameCenter class
	private GameCenter manager;
	
	//The factory from ProductFactory class
	private ProductFactory factory;
	
	//List of crops in a JList.
	private JList<Object> cropsList;
	
	//List of products in a JList.
	private JList<Object> productsList;
	
	//List of items in a JList.
	private JList<Object> itemsList;
	
	//The label that displays if a crop has been purchased.
	private JLabel cropMessageLabel;
	
	//The label that displays if a item has been purchased.
	private JLabel itemMessageLabel;
	
	//The label that displays the money available.
	private JLabel CurrentMoneyLabel;

	//he constructor of the StoreScreen. With GameCenter incomingManager, the gui can be managed with GameCenter.
	public StoreScreen(GameCenter incomingManager) 
	{
		manager = incomingManager;
		initialize();
		storeFrame.setVisible(true);
	}
	
	//The function to close the store Frame.
	public void closeWindow() 
	{
		storeFrame.dispose();
	}
	
	//The function to call closeWindow to close Store screen.
	public void finishWindow() 
	{
		manager.closeStoreScreen(this);
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
					StoreScreen window = new StoreScreen(gameCenter);
					window.storeFrame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	//Application	

	@SuppressWarnings("serial")
	private void initialize() 
	{
		storeFrame = new JFrame();
		storeFrame.setTitle("Happy Farm Store! What ever you want!");
		storeFrame.setBounds(100, 100, 1200, 650);
		storeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		storeFrame.getContentPane().setLayout(null);
		
		
		JPanel storeHeaderPanel = new JPanel();
		storeHeaderPanel.setLayout(null);
		storeHeaderPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		storeHeaderPanel.setBounds(10, 11, 1164, 78);
		storeFrame.getContentPane().add(storeHeaderPanel);
		
        CurrentMoneyLabel = new JLabel("Current Money: $<dynamic>");
		setCurrentMoneyLabel();
		CurrentMoneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		CurrentMoneyLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		CurrentMoneyLabel.setBounds(408, 43, 347, 24);
		storeHeaderPanel.add(CurrentMoneyLabel);
		
		JLabel storeLabel = new JLabel("Store"); //이런식으로 라벨, 버튼을 하나하나 설정해준 후 변수처럼 사용
		storeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		storeLabel.setFont(new Font("Tahoma", Font.BOLD, 32));
		storeLabel.setBounds(469, 11, 226, 29);
		storeHeaderPanel.add(storeLabel);
		
		JButton goBackButton = new JButton("Go back");
		goBackButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				finishWindow();
				manager.launchMainScreen();
			}
		});
		goBackButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		goBackButton.setBounds(10, 11, 300, 56);
		storeHeaderPanel.add(goBackButton);
		
		JButton viewCurrentlyOwnedButton = new JButton("View currently owned items, crops");
		viewCurrentlyOwnedButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JOptionPane.showMessageDialog(storeFrame, manager.returnItemsString(), "Items owned", JOptionPane.INFORMATION_MESSAGE);
				JOptionPane.showMessageDialog(storeFrame, manager.showCropInven(), "Crops owned", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		viewCurrentlyOwnedButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		viewCurrentlyOwnedButton.setBounds(854, 11, 300, 56);
		storeHeaderPanel.add(viewCurrentlyOwnedButton);
		
		JPanel cropsForSalePanel = new JPanel();
		cropsForSalePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		cropsForSalePanel.setBounds(10, 100, 1164, 163);
		storeFrame.getContentPane().add(cropsForSalePanel);
		cropsForSalePanel.setLayout(null);
		
		JLabel cropsForSaleLabel = new JLabel("Crops for sale");
		cropsForSaleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cropsForSaleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		cropsForSaleLabel.setBounds(10, 11, 300, 36);
		cropsForSalePanel.add(cropsForSaleLabel);
		
		
		cropMessageLabel = new JLabel("");
		cropMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cropMessageLabel.setForeground(Color.RED);
		cropMessageLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		cropMessageLabel.setBounds(10, 116, 190, 36);
		cropsForSalePanel.add(cropMessageLabel);
		
		JButton buyCropButton = new JButton("Buy");
		buyCropButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String printMessage = manager.purchaseCrop(cropsList.getSelectedIndex());
				if (printMessage.endsWith("bought!")) 
				{
					cropMessageLabel.setText(printMessage);
					itemMessageLabel.setText("");
				}
				else 
				{
					cropMessageLabel.setText("");
					JOptionPane.showMessageDialog(storeFrame, printMessage);
				}
				setCurrentMoneyLabel();
			}
		});
		
		
		buyCropButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		buyCropButton.setBounds(210, 116, 100, 36);
		cropsForSalePanel.add(buyCropButton);
		
		
		JLabel selectCropLabel = new JLabel("Select which crop you would like to purchase");
		selectCropLabel.setBounds(10, 47, 300, 22);
		cropsForSalePanel.add(selectCropLabel);
		selectCropLabel.setHorizontalAlignment(SwingConstants.CENTER);
		selectCropLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JLabel selectCropLabel2 = new JLabel("on the right, then press the buy button below");
		selectCropLabel2.setBounds(10, 68, 300, 22);
		cropsForSalePanel.add(selectCropLabel2);
		selectCropLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		selectCropLabel2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JScrollPane cropsScrollPane = new JScrollPane();
		cropsScrollPane.setBounds(320, 11, 834, 141);
		cropsForSalePanel.add(cropsScrollPane);
		
		cropsList = new JList<Object>();
		cropsScrollPane.setViewportView(cropsList);
		cropsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cropsList.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cropsList.setModel(new AbstractListModel<Object>() 
		{
			String[] values = manager.returnCropArray();
			public int getSize() 
			{
				return values.length;
			}
			public Object getElementAt(int index) 
			{
				return values[index];
			}
		});
		cropsList.setSelectedIndex(0);
		cropsList.setToolTipText("");
		
		JPanel itemsForSalePanel = new JPanel();
		itemsForSalePanel.setLayout(null);
		itemsForSalePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		itemsForSalePanel.setBounds(10, 270, 1164, 163);
		storeFrame.getContentPane().add(itemsForSalePanel);
		
		JLabel itemsForSaleLabel = new JLabel("Items for sale");
		itemsForSaleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		itemsForSaleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		itemsForSaleLabel.setBounds(10, 11, 300, 36);
		itemsForSalePanel.add(itemsForSaleLabel);
		
		itemMessageLabel = new JLabel("");
		itemMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		itemMessageLabel.setForeground(Color.RED);
		itemMessageLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		itemMessageLabel.setBounds(10, 116, 190, 36);
		itemsForSalePanel.add(itemMessageLabel);
		
		JButton buyItemButton = new JButton("Buy");
		buyItemButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String message = manager.purchaseItem(itemsList.getSelectedIndex());
				if (message.endsWith("bought!")) 
				{
					cropMessageLabel.setText("");
					itemMessageLabel.setText(message);
				}
				else 
				{
					itemMessageLabel.setText("");
					JOptionPane.showMessageDialog(storeFrame, message);
				}
				setCurrentMoneyLabel();
			}
		});
		buyItemButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		buyItemButton.setBounds(210, 116, 100, 36);
		itemsForSalePanel.add(buyItemButton);
		
		JLabel selectItemLabel = new JLabel("Select which item you would like to purchase");
		selectItemLabel.setHorizontalAlignment(SwingConstants.CENTER);
		selectItemLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		selectItemLabel.setBounds(10, 47, 300, 22);
		itemsForSalePanel.add(selectItemLabel);
		
		JLabel selectItemLabel2 = new JLabel("on the right, then press the buy button below");
		selectItemLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		selectItemLabel2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		selectItemLabel2.setBounds(10, 68, 300, 22);
		itemsForSalePanel.add(selectItemLabel2);
		
		JScrollPane itemsScrollPane = new JScrollPane();
		itemsScrollPane.setBounds(320, 11, 834, 141);
		itemsForSalePanel.add(itemsScrollPane);
		
		itemsList = new JList<Object>();
		itemsScrollPane.setViewportView(itemsList);
		itemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		itemsList.setFont(new Font("Tahoma", Font.PLAIN, 15));
		itemsList.setModel(new AbstractListModel<Object>() 
		{
			String[] values = manager.returnItemArray();
			public int getSize() 
			{
				return values.length;
			}
			public Object getElementAt(int index) 
			{
				return values[index];
			}
		});
		itemsList.setSelectedIndex(0);
		itemsList.setToolTipText("");
		
		JButton createProductButton = new JButton("Create Product");
		createProductButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		createProductButton.setBounds(650, 450, 380, 100); 
		storeFrame.getContentPane().add(createProductButton); 
		createProductButton.setBackground(new Color(173, 216, 230));
		createProductButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        openProductCreationWindow();
		    }
		});
		
		// Sell Crops 버튼 추가
		JButton sellCropsButton = new JButton("Sell Crops");
		sellCropsButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		sellCropsButton.setBounds(150, 450, 380, 100);
		storeFrame.getContentPane().add(sellCropsButton);
		sellCropsButton.setBackground(new Color(255, 192, 203));

		// Sell Crops 버튼 클릭 이벤트 처리
		sellCropsButton.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
			        String cropName = JOptionPane.showInputDialog(storeFrame, "Enter the name of the crop to sell:");
			        if (cropName != null && !cropName.trim().isEmpty()) {
			            String message = manager.sellCrops(cropName);
			            if (message.contains("not enough") || message.contains("You don't have") || message.contains("invalid")) {
			                // 잘못된 작물 이름이거나 판매 실패 메시지인 경우
			                JOptionPane.showMessageDialog(storeFrame, "Failed to sell crop: " + message, "Error", JOptionPane.ERROR_MESSAGE);
			            } else {
			                // 판매 성공 메시지
			                JOptionPane.showMessageDialog(storeFrame, message);
			                setCurrentMoneyLabel();
			            }
			        } else {
			            JOptionPane.showMessageDialog(storeFrame, "Please enter a valid crop name.", "Error", JOptionPane.ERROR_MESSAGE);
			        }
			    }
			});
		
		
		
    }

	
	
	//Creating product method
    private void openProductCreationWindow() {
        JFrame productFrame = new JFrame("Select a Product to Create");
        productFrame.setSize(500, 300);
        productFrame.setLayout(null);

        String[] products = factory.getAllProduct();  // GameCenter에서 제품 목록 가져오기
        JList<String> productList = new JList<>(products);
        productList.setBounds(50, 30, 400, 100);
        productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productFrame.add(productList);

        JButton createButton = new JButton("Create");
        createButton.setBounds(190, 180, 100, 30);
        productFrame.add(createButton);

        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedProduct = productList.getSelectedValue();
                if (selectedProduct != null) {
                    String resultMessage = manager.makeProduct(selectedProduct); //여기에 Gamecenter의 product 함수 넣기
                    JOptionPane.showMessageDialog(productFrame, resultMessage, "Product Creation", JOptionPane.INFORMATION_MESSAGE);
                    setCurrentMoneyLabel();
                    productFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(productFrame, "Please select a product!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        productFrame.setVisible(true);
    }
		
	
	//Sets the current money label.
	public void setCurrentMoneyLabel()
	{
		CurrentMoneyLabel.setText("Current Money: $" + manager.returnMoneyString());
	}
}