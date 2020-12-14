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
	static JLabel cnpLabel = new JLabel();
	static String coupangFileName = new String();
	static String coupangFileDirectory = new String();
	static String naverFileName = new String();
	static String naverFileDirectory = new String();
	static String cnpFileName = new String();
	static String cnpFileDirectory = new String();
    static Font defaultFont = new Font("���", Font.BOLD, 20);
	
    private static final String ERR_MSG = "���� �ø��� / ���� ��� ���� �ٽ� ����";
    
	private static void clear() {
		coupangFileName = "";
		coupangFileDirectory = "";
		naverFileName = "";
		naverFileDirectory = "";
		cnpFileName = "";
		cnpFileDirectory = "";
		coupangLabel.setText("");
		naverLabel.setText("");
		cnpLabel.setText("");
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
        setTitle("�䵵�� ���� ���� ��ȯ��");
        // �� �κк��� ���ϴ� ��ư, ���̺�, �޺��ڽ� ��� ����
        this.setLayout(null);
        
        // ��ư ����
        JButton naverToCnpBtn = new JButton("���̹� -> CNPlus ��ȯ");    
        this.add(naverToCnpBtn);    
        JButton coupangToCnpBtn = new JButton("���� -> CNPlus ��ȯ");    
        this.add(coupangToCnpBtn);  
        JButton fromCnpBtn = new JButton("���� & ���̹� ����� �Է�");    
        this.add(fromCnpBtn);            
        JButton coupangFileSearchBtn = new JButton("���� ã��(����)");
        this.add(coupangFileSearchBtn);
        JButton naverFileSearchBtn = new JButton("���� ã��(���̹�)");
        this.add(naverFileSearchBtn);
        JButton cnpFileSearchBtn = new JButton("���� ã��(CNPlus)");
        this.add(cnpFileSearchBtn);
        JButton countBtn = new JButton("�׽�Ʈ");
        this.add(countBtn);
        JButton cnpToNaverBtn = new JButton("���̹� ����� �Է�");
        this.add(cnpToNaverBtn);
        JButton cnpToCoupangBtn = new JButton("���� ����� �Է�");
        this.add(cnpToCoupangBtn);        
        
        // ��Ʈ ��ȯ
        naverToCnpBtn.setFont(defaultFont);        
        fromCnpBtn.setFont(defaultFont);
        coupangToCnpBtn.setFont(defaultFont);
        cnpToNaverBtn.setFont(defaultFont);
        cnpToCoupangBtn.setFont(defaultFont);
        
        coupangFileSearchBtn.setFont(defaultFont);
        naverFileSearchBtn.setFont(defaultFont);
        cnpFileSearchBtn.setFont(defaultFont);
        
        countBtn.setFont(defaultFont);
        // ��ư ��ġ
        naverToCnpBtn.setBounds(100, 280, 250, 50);
        cnpToNaverBtn.setBounds(100, 360, 250, 50);
        coupangToCnpBtn.setBounds(370, 280, 250, 50);
        cnpToCoupangBtn.setBounds(370, 360, 250, 50);
        countBtn.setBounds(910, 280, 300, 50);
        fromCnpBtn.setBounds(640, 360, 300, 50);


        coupangFileSearchBtn.setBounds(910, 40, 300, 50);
        naverFileSearchBtn.setBounds(910, 110, 300, 50);
        cnpFileSearchBtn.setBounds(910, 180, 300, 50);

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
        cnpFileSearchBtn.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			JFrame dialogFrame = new JFrame();
    			dialogFrame.setSize(350, 250);
    			dialogFrame.setLayout(null);

    	        FileDialog dialog = new FileDialog(dialogFrame, "�ҷ�����", FileDialog.LOAD);
    	        dialog.setVisible(true);
    	        cnpFileDirectory = dialog.getDirectory();
    	        cnpFileName = dialog.getFile();
    	        
    	        if (cnpFileDirectory != null && cnpFileName != null) {
    		        cnpLabel.setText(cnpFileDirectory + cnpFileName);
    		        cnpLabel.setVisible(true);
    		        
    				// TODO Auto-generated method stub
    		        System.out.println(cnpFileDirectory + cnpFileName);
    	        }
    		}
        });
        
        coupangToCnpBtn.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			// TODO Auto-generated method stub
    			// ����
    			try {
    				Transform.coupangToCnp(coupangFileDirectory, coupangFileName);
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
        naverToCnpBtn.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			// ����
    			try {
    				Transform.naverToCnp(naverFileDirectory, naverFileName);
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
        cnpToCoupangBtn.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			// TODO Auto-generated method stub
    			try {
    				Transform.cnpToCoupang(coupangFileDirectory, coupangFileName, cnpFileDirectory, cnpFileName);
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
        cnpToNaverBtn.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			// TODO Auto-generated method stub
    			try {
    				Transform.cnpToNaver(naverFileDirectory, naverFileName, cnpFileDirectory, cnpFileName);
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
        fromCnpBtn.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			// TODO Auto-generated method stub
    			try {
    				Transform.cnpToBoth(coupangFileDirectory, coupangFileName, naverFileDirectory, naverFileName, cnpFileDirectory, cnpFileName);
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
        
        this.add(cnpLabel);
        cnpLabel.setFont(defaultFont);
        cnpLabel.setBounds(100, 180, 900, 50);
        
        // ���� ��ư ����
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ������ â ũ�� ����(����, ����)
        setSize(1300, 600);
        
        // �� �޼ҵ带 �̿��ؾ� ������ â�� ��Ÿ����.
        setVisible(true);
    }
}
