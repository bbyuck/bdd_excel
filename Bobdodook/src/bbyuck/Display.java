package bbyuck;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

import bbyuck.event.TransformationComplete;

public class Display extends JFrame{
	static JLabel coupangLabel = new JLabel();
	static JLabel naverLabel = new JLabel();
	static JLabel logenLabel = new JLabel();
	static String coupangFileName = new String();
	static String coupangFileDirectory = new String();
	static String naverFileName = new String();
	static String naverFileDirectory = new String();
	static String logenFileName = new String();
	static String logenFileDirectory = new String();
    static Font defaultFont = new Font("���", Font.BOLD, 20);
	
    private static final String ERR_MSG = "���� �ø��� / ���� ��� ���� �ٽ� ����";
    
	private static void clear() {
		coupangFileName = "";
		coupangFileDirectory = "";
		naverFileName = "";
		naverFileDirectory = "";
		logenFileName = "";
		logenFileDirectory = "";
		coupangLabel.setText("");
		naverLabel.setText("");
		logenLabel.setText("");
	}
	
    // �����ڸ� ���� GUI �ʱ� ������ ���ش�.
    Display(){
    	// ���� �����̼� �о����
    	try {
        	Transform.init();
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    		System.exit(0);
    	}
    	
        setResizable(false);
        
        // ������ ����(Title)�� ����
        setTitle("�䵵�� ���� ���� ��ȯ�� - �����ù� Ver");
        // �� �κк��� ���ϴ� ��ư, ���̺�, �޺��ڽ� ��� ����
        this.setLayout(null);
        
        // ��ư ����
        JButton naverToLogenBtn = new JButton("���̹� -> ���� ��ȯ");    
        this.add(naverToLogenBtn);    
        JButton coupangToLogenBtn = new JButton("���� -> ���� ��ȯ");    
        this.add(coupangToLogenBtn);  
        JButton fromLogenBtn = new JButton("���� & ���̹� ����� �Է�");    
        this.add(fromLogenBtn);            
        JButton coupangFileSearchBtn = new JButton("���� ã��(����)");
        this.add(coupangFileSearchBtn);
        JButton naverFileSearchBtn = new JButton("���� ã��(���̹�)");
        this.add(naverFileSearchBtn);
        JButton logenFileSearchBtn = new JButton("���� ã��(�����ù�)");
        this.add(logenFileSearchBtn);
        JButton countBtn = new JButton("�׽�Ʈ");
        this.add(countBtn);
        JButton logenToNaverBtn = new JButton("���̹� ����� �Է�");
        this.add(logenToNaverBtn);
        JButton logenToCoupangBtn = new JButton("���� ����� �Է�");
        this.add(logenToCoupangBtn);        
        
        // ��Ʈ ��ȯ
        naverToLogenBtn.setFont(defaultFont);        
        fromLogenBtn.setFont(defaultFont);
        coupangToLogenBtn.setFont(defaultFont);
        logenToNaverBtn.setFont(defaultFont);
        logenToCoupangBtn.setFont(defaultFont);
        
        coupangFileSearchBtn.setFont(defaultFont);
        naverFileSearchBtn.setFont(defaultFont);
        logenFileSearchBtn.setFont(defaultFont);
        
        countBtn.setFont(defaultFont);
        // ��ư ��ġ
        naverToLogenBtn.setBounds(100, 280, 250, 50);
        logenToNaverBtn.setBounds(100, 360, 250, 50);
        coupangToLogenBtn.setBounds(370, 280, 250, 50);
        logenToCoupangBtn.setBounds(370, 360, 250, 50);
        countBtn.setBounds(910, 280, 300, 50);
        fromLogenBtn.setBounds(640, 360, 300, 50);


        coupangFileSearchBtn.setBounds(910, 40, 300, 50);
        naverFileSearchBtn.setBounds(910, 110, 300, 50);
        logenFileSearchBtn.setBounds(910, 180, 300, 50);

        // ��ư�� �̺�Ʈ �ڵ鷯
        coupangFileSearchBtn.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			JFrame dialogFrame = new JFrame();
    			dialogFrame.setSize(350, 250);
    			dialogFrame.setLayout(null);

    	        FileDialog dialog = new FileDialog(dialogFrame, "�ҷ�����", FileDialog.LOAD);
    	        dialog.setVisible(true);
    	        coupangFileName = dialog.getFile();
    	        coupangFileDirectory = dialog.getDirectory();
    	        if (coupangFileDirectory != null && coupangFileName != null) {
    		        coupangLabel.setText(coupangFileDirectory + coupangFileName);
    		        coupangLabel.setVisible(true);
    		        
    				// TODO Auto-generated method stub
    		        System.out.println(coupangFileDirectory + coupangFileName);
    	        }
    		}
        });
        naverFileSearchBtn.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			JFrame dialogFrame = new JFrame();
    			dialogFrame.setSize(350, 250);
    			dialogFrame.setLayout(null);

    	        FileDialog dialog = new FileDialog(dialogFrame, "�ҷ�����", FileDialog.LOAD);
    	        dialog.setVisible(true);
    	        naverFileName = dialog.getFile();
    	        naverFileDirectory = dialog.getDirectory();
    	        if (naverFileDirectory != null && naverFileName != null) {
    	        	naverLabel.setText(naverFileDirectory + naverFileName);
    	        	naverLabel.setVisible(true);
    		        
    				// TODO Auto-generated method stub
    		        System.out.println(naverFileDirectory + naverFileName);
    	        }
    		}
        });
        logenFileSearchBtn.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			JFrame dialogFrame = new JFrame();
    			dialogFrame.setSize(350, 250);
    			dialogFrame.setLayout(null);

    	        FileDialog dialog = new FileDialog(dialogFrame, "�ҷ�����", FileDialog.LOAD);
    	        dialog.setVisible(true);
    	        logenFileDirectory = dialog.getDirectory();
    	        logenFileName = dialog.getFile();
    	        
    	        if (logenFileDirectory != null && logenFileName != null) {
    		        logenLabel.setText(logenFileDirectory + logenFileName);
    		        logenLabel.setVisible(true);
    		        
    				// TODO Auto-generated method stub
    		        System.out.println(logenFileDirectory + logenFileName);
    	        }
    		}
        });
        
        coupangToLogenBtn.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			// TODO Auto-generated method stub
    			// ����
    			try {
    				Transform.coupangToLogen(coupangFileDirectory, coupangFileName);
    			}
    			catch (IOException ex) {
    				ex.printStackTrace();
    				new Popup(ERR_MSG);
    			} 
    			catch (TransformationComplete c) {
    				// TODO Auto-generated catch block
    				clear();
    				new Popup(c.getMessage());
    			}
    		}
        });
        naverToLogenBtn.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			// ����
    			try {
    				Transform.naverToLogen(naverFileDirectory, naverFileName);
    			}
    			catch (IOException ex) {
    				ex.printStackTrace();
    				new Popup(ERR_MSG);
    			} 
    			catch (TransformationComplete c) {
    				// TODO Auto-generated catch block
    				clear();
    				new Popup(c.getMessage());
    			}
    		}
        });
        logenToCoupangBtn.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			// TODO Auto-generated method stub
    			try {
    				Transform.logenToCoupang(coupangFileDirectory, coupangFileName, logenFileDirectory, logenFileName);
    			}
    			catch (IOException ex) {
    				ex.printStackTrace();
    				new Popup(ERR_MSG);
    			} 
    			catch (TransformationComplete c) {
    				// TODO Auto-generated catch block
    				clear();
    				new Popup(c.getMessage());
    			}
    		}
        });
        logenToNaverBtn.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			// TODO Auto-generated method stub
    			try {
    				Transform.logenToNaver(naverFileDirectory, naverFileName, logenFileDirectory, logenFileName);
    			}
    			catch (IOException ex) {
    				ex.printStackTrace();
    				new Popup(ERR_MSG);
    			} 
    			catch (TransformationComplete c) {
    				// TODO Auto-generated catch block
    				clear();
    				new Popup(c.getMessage());
    			}
    		}
        });
        fromLogenBtn.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			// TODO Auto-generated method stub
    			try {
    				Transform.logenToBoth(coupangFileDirectory, coupangFileName, naverFileDirectory, naverFileName, logenFileDirectory, logenFileName);
    			}
    			catch (IOException ex) {
    				ex.printStackTrace();
    				new Popup(ERR_MSG);
    			} 
    			catch (TransformationComplete c) {
    				// TODO Auto-generated catch block
    				clear();
    				new Popup(c.getMessage());
    			}
    		}
        });

        JLabel testLabel = new JLabel();
        this.add(testLabel);
        testLabel.setFont(defaultFont);
        testLabel.setBounds(100, 40, 900, 50);
        
        countBtn.addActionListener(new ActionListener() {
	    		public void actionPerformed(ActionEvent e) {
	    			// TODO Auto-generated method stub
	    			testLabel.setVisible(true);
	    		}
	        });
        // ���̺�
        this.add(coupangLabel);
        coupangLabel.setFont(defaultFont);
        coupangLabel.setBounds(100, 40, 900, 50);
        
        this.add(naverLabel);
        naverLabel.setFont(defaultFont);
        naverLabel.setBounds(100, 110, 900, 50);
        
        this.add(logenLabel);
        logenLabel.setFont(defaultFont);
        logenLabel.setBounds(100, 180, 900, 50);
        
        // ���� ��ư ����
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ������ â ũ�� ����(����, ����)
        setSize(1300, 600);
        
        // �� �޼ҵ带 �̿��ؾ� ������ â�� ��Ÿ����.
        setVisible(true);
    }
}
