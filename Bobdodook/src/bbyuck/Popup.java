package bbyuck;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import bbyuck.Display.CoupangToCnpEventHandler;
import bbyuck.event.TransformationComplete;


public class Popup extends JFrame {
    static Font defaultFont = new Font("���", Font.BOLD, 20);

	Popup(String prompt) {
		setTitle("�䵵�� ���� ���� ��ȯ��");
	       JPanel NewWindowContainer = new JPanel();
	        setContentPane(NewWindowContainer);
	        setLayout(null);
	        
	        JLabel complete = new JLabel(prompt);
	        JButton okBtn = new JButton("Ȯ��");
	        
	        complete.setBounds(190, 20, 250, 50);
	        complete.setFont(defaultFont);
	        okBtn.setBounds(245, 75, 100, 50);
	        okBtn.setFont(defaultFont);
	        
	        NewWindowContainer.add(complete);
	        okBtn.addActionListener(new ActionListener() {
	    		public void actionPerformed(ActionEvent e) {
	    			// TODO Auto-generated method stub
	    			dispose();
	    		}
	        });

	        this.add(okBtn);
	        
	        setSize(600,200);
	        setResizable(false);
	        setVisible(true);
	}
}
