package bbyuck;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import bbyuck.event.TransformationComplete;


public class Popup extends JFrame {
    static Font defaultFont = new Font("∞ÌµÒ", Font.BOLD, 20);

	Popup(String prompt) {
		setTitle("π‰µµµœ ººªÛ ø¢ºø ∫Ø»Ø±‚");
	       JPanel NewWindowContainer = new JPanel();
	        setContentPane(NewWindowContainer);
	        setLayout(null);
	        
	        JLabel message = new JLabel(prompt);
	        JButton okBtn = new JButton("»Æ¿Œ");
	        
	        message.setBounds(120, 20, 380, 50);
	        message.setFont(defaultFont);
	        okBtn.setBounds(245, 75, 100, 50);
	        okBtn.setFont(defaultFont);
	        
	        NewWindowContainer.add(message);
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
