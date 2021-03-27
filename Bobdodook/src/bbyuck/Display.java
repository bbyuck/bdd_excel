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
    static Font defaultFont = new Font("고딕", Font.BOLD, 20);
	
    private static final String ERR_MSG = "파일 올리기 / 엑셀 모두 끄고 다시 실행";
    
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
        setTitle("밥도둑 세상 엑셀 변환기 - 로젠택배 Ver");
        // 이 부분부터 원하는 버튼, 레이블, 콤보박스 등등 설정
        this.setLayout(null);
        
        // 버튼 생성
        JButton naverToLogenBtn = new JButton("네이버 -> 로젠 변환");    
        this.add(naverToLogenBtn);    
        JButton coupangToLogenBtn = new JButton("쿠팡 -> 로젠 변환");    
        this.add(coupangToLogenBtn);  
        JButton fromLogenBtn = new JButton("쿠팡 & 네이버 운송장 입력");    
        this.add(fromLogenBtn);            
        JButton coupangFileSearchBtn = new JButton("파일 찾기(쿠팡)");
        this.add(coupangFileSearchBtn);
        JButton naverFileSearchBtn = new JButton("파일 찾기(네이버)");
        this.add(naverFileSearchBtn);
        JButton logenFileSearchBtn = new JButton("파일 찾기(로젠택배)");
        this.add(logenFileSearchBtn);
        JButton countBtn = new JButton("테스트");
        this.add(countBtn);
        JButton logenToNaverBtn = new JButton("네이버 운송장 입력");
        this.add(logenToNaverBtn);
        JButton logenToCoupangBtn = new JButton("쿠팡 운송장 입력");
        this.add(logenToCoupangBtn);        
        
        // 폰트 변환
        naverToLogenBtn.setFont(defaultFont);        
        fromLogenBtn.setFont(defaultFont);
        coupangToLogenBtn.setFont(defaultFont);
        logenToNaverBtn.setFont(defaultFont);
        logenToCoupangBtn.setFont(defaultFont);
        
        coupangFileSearchBtn.setFont(defaultFont);
        naverFileSearchBtn.setFont(defaultFont);
        logenFileSearchBtn.setFont(defaultFont);
        
        countBtn.setFont(defaultFont);
        // 버튼 위치
        naverToLogenBtn.setBounds(100, 280, 250, 50);
        logenToNaverBtn.setBounds(100, 360, 250, 50);
        coupangToLogenBtn.setBounds(370, 280, 250, 50);
        logenToCoupangBtn.setBounds(370, 360, 250, 50);
        countBtn.setBounds(910, 280, 300, 50);
        fromLogenBtn.setBounds(640, 360, 300, 50);


        coupangFileSearchBtn.setBounds(910, 40, 300, 50);
        naverFileSearchBtn.setBounds(910, 110, 300, 50);
        logenFileSearchBtn.setBounds(910, 180, 300, 50);

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
        logenFileSearchBtn.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			JFrame dialogFrame = new JFrame();
    			dialogFrame.setSize(350, 250);
    			dialogFrame.setLayout(null);

    	        FileDialog dialog = new FileDialog(dialogFrame, "불러오기", FileDialog.LOAD);
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
    			// 쿠팡
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
    			// 쿠팡
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
        // 레이블
        this.add(coupangLabel);
        coupangLabel.setFont(defaultFont);
        coupangLabel.setBounds(100, 40, 900, 50);
        
        this.add(naverLabel);
        naverLabel.setFont(defaultFont);
        naverLabel.setBounds(100, 110, 900, 50);
        
        this.add(logenLabel);
        logenLabel.setFont(defaultFont);
        logenLabel.setBounds(100, 180, 900, 50);
        
        // 종료 버튼 생성
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 윈도우 창 크기 설정(가로, 세로)
        setSize(1300, 600);
        
        // 이 메소드를 이용해야 윈도우 창이 나타난다.
        setVisible(true);
    }
}
