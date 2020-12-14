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
    static Font defaultFont = new Font("고딕", Font.BOLD, 20);
	
    private static final String ERR_MSG = "파일 올리기 / 엑셀 모두 끄고 다시 실행";
    
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
	
    // 생성자를 통해 GUI 초기 세팅을 해준다.
    Display(){
    	// 쿠팡 딕테이션 읽어오기
    	try {
        	Transform.init();
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    		System.exit(0);
    	}
    	
        setResizable(false);
        
        // 윈도우 제목(Title)을 생성
        setTitle("밥도둑 세상 엑셀 변환기");
        // 이 부분부터 원하는 버튼, 레이블, 콤보박스 등등 설정
        this.setLayout(null);
        
        // 버튼 생성
        JButton naverToCnpBtn = new JButton("네이버 -> CNPlus 변환");    
        this.add(naverToCnpBtn);    
        JButton coupangToCnpBtn = new JButton("쿠팡 -> CNPlus 변환");    
        this.add(coupangToCnpBtn);  
        JButton fromCnpBtn = new JButton("쿠팡 & 네이버 운송장 입력");    
        this.add(fromCnpBtn);            
        JButton coupangFileSearchBtn = new JButton("파일 찾기(쿠팡)");
        this.add(coupangFileSearchBtn);
        JButton naverFileSearchBtn = new JButton("파일 찾기(네이버)");
        this.add(naverFileSearchBtn);
        JButton cnpFileSearchBtn = new JButton("파일 찾기(CNPlus)");
        this.add(cnpFileSearchBtn);
        JButton countBtn = new JButton("테스트");
        this.add(countBtn);
        JButton cnpToNaverBtn = new JButton("네이버 운송장 입력");
        this.add(cnpToNaverBtn);
        JButton cnpToCoupangBtn = new JButton("쿠팡 운송장 입력");
        this.add(cnpToCoupangBtn);        
        
        // 폰트 변환
        naverToCnpBtn.setFont(defaultFont);        
        fromCnpBtn.setFont(defaultFont);
        coupangToCnpBtn.setFont(defaultFont);
        cnpToNaverBtn.setFont(defaultFont);
        cnpToCoupangBtn.setFont(defaultFont);
        
        coupangFileSearchBtn.setFont(defaultFont);
        naverFileSearchBtn.setFont(defaultFont);
        cnpFileSearchBtn.setFont(defaultFont);
        
        countBtn.setFont(defaultFont);
        // 버튼 위치
        naverToCnpBtn.setBounds(100, 280, 250, 50);
        cnpToNaverBtn.setBounds(100, 360, 250, 50);
        coupangToCnpBtn.setBounds(370, 280, 250, 50);
        cnpToCoupangBtn.setBounds(370, 360, 250, 50);
        countBtn.setBounds(910, 280, 300, 50);
        fromCnpBtn.setBounds(640, 360, 300, 50);


        coupangFileSearchBtn.setBounds(910, 40, 300, 50);
        naverFileSearchBtn.setBounds(910, 110, 300, 50);
        cnpFileSearchBtn.setBounds(910, 180, 300, 50);

        // 버튼별 이벤트 핸들러
        coupangFileSearchBtn.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			JFrame dialogFrame = new JFrame();
    			dialogFrame.setSize(350, 250);
    			dialogFrame.setLayout(null);

    	        FileDialog dialog = new FileDialog(dialogFrame, "불러오기", FileDialog.LOAD);
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

    	        FileDialog dialog = new FileDialog(dialogFrame, "불러오기", FileDialog.LOAD);
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

    	        FileDialog dialog = new FileDialog(dialogFrame, "불러오기", FileDialog.LOAD);
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
    			// 쿠팡
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
    			// 쿠팡
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
        // 레이블
        this.add(coupangLabel);
        coupangLabel.setFont(defaultFont);
        coupangLabel.setBounds(100, 40, 900, 50);
        
        this.add(naverLabel);
        naverLabel.setFont(defaultFont);
        naverLabel.setBounds(100, 110, 900, 50);
        
        this.add(cnpLabel);
        cnpLabel.setFont(defaultFont);
        cnpLabel.setBounds(100, 180, 900, 50);
        
        // 종료 버튼 생성
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 윈도우 창 크기 설정(가로, 세로)
        setSize(1300, 600);
        
        // 이 메소드를 이용해야 윈도우 창이 나타난다.
        setVisible(true);
    }
}
