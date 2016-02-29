import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class EmailAppGUI extends JFrame implements ActionListener {
	
	JButton loginButton = new JButton();
	JTable inboxDisplay = new JTable();

	public EmailAppGUI () {
		super("Email App");
		addActionListener();
		
		this.setSize(1200, 900);
		centreWindow(this);
		this.setVisible(true);
		this.setResizable(false);
		
		
		
	}
	
	public static void centreWindow(Window frame) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
	}
	
	
	public void addActionListener () {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}
}
