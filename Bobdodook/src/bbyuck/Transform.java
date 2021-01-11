package bbyuck;

import java.util.*;
import java.util.Map.Entry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import bbyuck.dto.*;
import bbyuck.event.TransformationComplete;

class Pair implements Comparable<Pair>{
	String productName;
	Integer count;
	
	public Pair(String p, Integer c) {
		productName = p;
		count = c;
	}
	
	@Override
	public int compareTo(Pair opponent) {
		return productName.compareTo(opponent.productName);
	}
}

public class Transform {
	private static HashMap<String, String> coupangDict;
	private static HashMap<String, Integer> coupangCount;
	
	private static HashMap<String, String> naverProductDict;
	private static HashMap<String, String> naverOptionDict;
	private static HashMap<String, Integer> naverCount;
	
	// ���� ���� �ٲٴ� ��������
	private static final String COUPANG_DICT_URI = "./coupangDict.xlsx";
	private static final String NAVER_DICT_URI = "./naverDict.xlsx";
	private static final String TEST_COUPANG = "C:\\Users\\k9410\\OneDrive\\���� ȭ��\\�䵵�ϼ��� ������Ʈ\\��ǰ��.xlsx";
	private static final String TEST_NAVER = "C:\\Users\\k9410\\OneDrive\\���� ȭ��\\�䵵�ϼ��� ������Ʈ\\naverDict.xlsx";
	private static final Integer COUPANG_OPTION_START_IDX = 10;
	private static final String COURIER = "CJ�������";

	private static void test() {
		for (Entry<String, String> entry : coupangDict.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
	}
	
	public static void init() throws IOException {
		coupangDict = new HashMap<>();
		coupangCount = new HashMap<>();
		
		naverProductDict = new HashMap<>();
		naverOptionDict = new HashMap<>();
		naverCount = new HashMap<>();
		
		readCoupangProducts();
		readNaverProducts();
	}
	
	private static void readNaverProducts() throws IOException {
		FileInputStream fi = new FileInputStream(NAVER_DICT_URI);
//		FileInputStream fi = new FileInputStream(TEST_NAVER);
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(fi);
		XSSFSheet sheet = workbook.getSheetAt(0); // �ش� ���������� ��Ʈ��
		int rows = sheet.getPhysicalNumberOfRows(); // �ش� ��Ʈ�� ���� ����
		
		// �Ľ�
		for (int rowIdx = 1; rowIdx < rows; rowIdx++) {
			XSSFRow row = sheet.getRow(rowIdx); // �� ���� �о�´�

			if (row != null) {

				if (row.getCell(0) == null) {
					// �ɼ� ->			
					XSSFCell cell = row.getCell(2);
					String key = cell.getStringCellValue();
					cell = row.getCell(3);
					String value = cell.getStringCellValue();
					naverOptionDict.put(key, value);
					naverCount.put(value, 0);
				}
				else {
					// ��ǰ�� ->
					XSSFCell cell = row.getCell(0);
					String key = cell.getStringCellValue();
					cell = row.getCell(1);
					String value = cell.getStringCellValue();
					naverProductDict.put(key, value);
					naverCount.put(value, 0);
				}
			}
		}
	}
	
	private static void readCoupangProducts() throws IOException {
		FileInputStream fi = new FileInputStream(COUPANG_DICT_URI);
//		FileInputStream fi = new FileInputStream(TEST_COUPANG);
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(fi);
		XSSFSheet sheet = workbook.getSheetAt(0); // �ش� ���������� ��Ʈ��
		int rows = sheet.getPhysicalNumberOfRows(); // �ش� ��Ʈ�� ���� ����
		
		// �Ľ�
		for (int rowIdx = 1; rowIdx < rows; rowIdx++) {
			XSSFRow row = sheet.getRow(rowIdx); // �� ���� �о�´�

			if (row != null) {
				XSSFCell cell = row.getCell(6);
				String key = cell.getStringCellValue();
				cell = row.getCell(8);
				String value = cell.getStringCellValue();
				coupangDict.put(key, value);
				coupangCount.put(value, 0);
			}
		}
	}
	
	private static String processPhone(String num) {
		String answer = "";
		
		for (int i = 0; i < num.length(); i++) {
			if(num.charAt(i) >= '0' && num.charAt(i) <= '9') answer += num.charAt(i);
		}
		
		return answer;
	}
	
	
	// �߰��� ��ǰ �̸� �� ������ ī��Ʈ�ؼ� �߰����� �������Ϸ� ����ϴ� �޼ҵ� ���� ��
	public static List<CnpInputDto> readCoupang(String fileURI) throws IOException {
		List<CnpInputDto> answer = new ArrayList<>();
		List<CoupangColumnDto> coupangLs = new ArrayList<>();
		
		FileInputStream fi = new FileInputStream(fileURI);
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(fi);
		XSSFSheet sheet = workbook.getSheetAt(0); // �ش� ���������� ��Ʈ��
		int rows = sheet.getPhysicalNumberOfRows(); // �ش� ��Ʈ�� ���� ����
//		System.out.println(rows);
		
		XSSFRow row = sheet.getRow(0); // ����� �о����
		int cells = row.getPhysicalNumberOfCells();
		
		int receiverNameIdx = -1;
		int receiverPhoneIdx = -1;
		int receiverAddressIdx = -1;
		int productNameIdx = -1;
		int etcIdx = -1;
		int idIdx = -1;
		int quantityIdx = -1;
		
		for (int colIdx = 0; colIdx < cells; colIdx++) {
			XSSFCell cell = row.getCell(colIdx);
			String menu = cell.getStringCellValue();
			if (menu.equals("���ż�(����)")) quantityIdx = colIdx;
			if (menu.equals("��ȣ")) idIdx = colIdx;
			if (menu.equals("�������̸�")) receiverNameIdx = colIdx;
			if (menu.equals("��������ȭ��ȣ")) receiverPhoneIdx = colIdx;
			if (menu.equals("������ �ּ�")) receiverAddressIdx = colIdx;
			if (menu.equals("�����ǰ��(�ɼǸ�)")) productNameIdx = colIdx;
			if (menu.equals("��۸޼���")) etcIdx = colIdx;
		}

		
		// �Ľ�
		for (int rowIdx = 1; rowIdx < rows; rowIdx++) {
			row = sheet.getRow(rowIdx); // �� ���� �о�´�
			CoupangColumnDto coupangData = new CoupangColumnDto();
			CnpInputDto cnpInput = new CnpInputDto();
			
			if (row != null) {
				String productName = row.getCell(12).getStringCellValue();
				
				// ���� ����ִ� ���� �д´�.
				// ��ȣ
				Integer thisNum = Integer.parseInt(row.getCell(idIdx).getStringCellValue());
				// ���� ���
				if (thisNum.equals(coupangLs.size())) {
					// �ֹ� ���� �����ϴ� ����(�Ƹ� ���� ls���� get�ؼ� �� object �����ͼ� �ű⿡ concat �ϴ� ���)
					coupangData = coupangLs.get(thisNum - 1);
					String displayedProductName = coupangData.getDisplayedProductName();
					
					String thisRowDisplayedProductName = row.getCell(productNameIdx).getStringCellValue();
					Integer quantity = Integer.parseInt(row.getCell(quantityIdx).getStringCellValue());

					// ���û���
					if (thisRowDisplayedProductName.contains("���û���")) {
						String[] options = thisRowDisplayedProductName.split(",");
						String option = options[options.length - 1];
						String ans = option.substring(COUPANG_OPTION_START_IDX, option.length() - 2);
						if (quantity == 1) coupangData.setDisplayedProductName(displayedProductName + " // " + ans);
						else coupangData.setDisplayedProductName(displayedProductName + " // " + ans + " (" + quantity + "��)");
						coupangCount.replace(ans, coupangCount.get(ans) + quantity);		
					}
					else {
						thisRowDisplayedProductName = coupangDict.get(thisRowDisplayedProductName);
						if (quantity == 1) coupangData.setDisplayedProductName(displayedProductName + " // " + thisRowDisplayedProductName);
						else coupangData.setDisplayedProductName(displayedProductName + " // " + thisRowDisplayedProductName + " (" + quantity + "��)");
						coupangCount.replace(thisRowDisplayedProductName, coupangCount.get(thisRowDisplayedProductName) + quantity);
					}
					
					continue;
				}
				coupangData.setNum(thisNum);
				
				// ���ż�
				int quantity = Integer.parseInt(row.getCell(quantityIdx).getStringCellValue());
				coupangData.setQuantity(quantity);
				
				// �������̸�
				coupangData.setReceiverName(row.getCell(receiverNameIdx).getStringCellValue());
				
				// ��������ȭ��ȣ
				coupangData.setReceiverPhone(processPhone(row.getCell(receiverPhoneIdx).getStringCellValue()));
				
				// ������  �ּ�
				coupangData.setReceiverAddress(row.getCell(receiverAddressIdx).getStringCellValue());
				
				// �����ǰ��(�ɼǸ�)

				String val = coupangDict.get(productName);
				String ans = "�� - ";
				// ���û���
				if (productName.contains("���û���")) {
					String[] options = productName.split(",");
					String option = options[options.length - 1];
					ans += option.substring(COUPANG_OPTION_START_IDX, option.length() - 2);
					
					for (Entry<String, String> entry : coupangDict.entrySet()) {
						if (productName.equals(val)) {
							coupangCount.replace(entry.getValue(), coupangCount.get(entry.getValue()) + quantity);
							break;
						}
					}
				}
				else {
					productName = coupangDict.get(productName);
					coupangCount.replace(productName, coupangCount.get(productName) + quantity);
					ans += productName;
				}
				
				if (quantity == 1) coupangData.setDisplayedProductName(ans);
				else coupangData.setDisplayedProductName(ans + " (" + quantity + "��)");
				// ��۸޼���
				coupangData.setDeliveryMessage(row.getCell(etcIdx).getStringCellValue());
				coupangLs.add(coupangData);
			}
		}
		
		for (CoupangColumnDto coupangData : coupangLs) {
			CnpInputDto cnpInput = new CnpInputDto();
			cnpInput.setReceiverName(coupangData.getReceiverName());
			cnpInput.setReceiverPhone(coupangData.getReceiverPhone());
			cnpInput.setReceiverAddress(coupangData.getReceiverAddress());
			cnpInput.setOrderContents(coupangData.getDisplayedProductName());
			cnpInput.setRemark(coupangData.getDeliveryMessage());
			
			answer.add(cnpInput);
		}
		return answer;
	}
	public static List<CnpInputDto> readNaver(String fileURI) throws IOException {
		List<CnpInputDto> answer = new ArrayList<>();
		List<NaverColumnDto> naverLs = new ArrayList<>();
		HashMap<String, NaverColumnDto> map = new HashMap<>();
		
		FileInputStream fi = new FileInputStream(fileURI);
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(fi);
		XSSFSheet sheet = workbook.getSheetAt(0); // �ش� ���������� ��Ʈ��
		int rows = sheet.getPhysicalNumberOfRows(); // �ش� ��Ʈ�� ���� ����
//		System.out.println(rows);
		
		XSSFRow row = sheet.getRow(1); // ����� �о����
		int cells = row.getPhysicalNumberOfCells();
		
		int idIdx = -1;
		int receiverNameIdx = -1;
		int receiverPhoneIdx = -1;
		int receiverAddressIdx = -1;
		int productNameIdx = -1;
		int optionIdx = -1;
		int etcIdx = -1;
		int quantityIdx = -1;
		
		for (int colIdx = 0; colIdx < cells; colIdx++) {
			XSSFCell cell = row.getCell(colIdx);
			String menu = cell.getStringCellValue();
			if (menu.equals("����")) quantityIdx = colIdx;
			if (menu.equals("�ֹ���ȣ")) idIdx = colIdx;
			if (menu.equals("�����θ�")) receiverNameIdx = colIdx;
			if (menu.equals("�����ο���ó1")) receiverPhoneIdx = colIdx;
			if (menu.equals("�����")) receiverAddressIdx = colIdx;
			if (menu.equals("��ǰ��")) productNameIdx = colIdx;
			if (menu.equals("�ɼ�����")) optionIdx = colIdx;
			if (menu.equals("��۸޼���")) etcIdx = colIdx;
		}

		
		// �Ľ�
		for (int rowIdx = 2; rowIdx < rows; rowIdx++) {
			row = sheet.getRow(rowIdx); // �� ���� �о�´�
			NaverColumnDto naverData = new NaverColumnDto();
			CnpInputDto cnpInput = new CnpInputDto();
			
			if (row != null) {				
				// ���� ����ִ� ���� �д´�.
				// �����θ�
				String receiverName = row.getCell(receiverNameIdx).getStringCellValue();
				// ������ ����ó
				String receiverPhone = processPhone(row.getCell(receiverPhoneIdx).getStringCellValue());
				String orderNum = row.getCell(idIdx).getStringCellValue();

				NaverColumnDto chkDto = map.get(orderNum);
				
				if (chkDto != null) {
					// ���� ���
					naverData = chkDto;
					String displayedProductName = naverData.getProductName();
					String thisRowDisplayedProductName = row.getCell(productNameIdx).getStringCellValue();
					String thisRowProcessedProductName = naverProductDict.get(thisRowDisplayedProductName);
					Integer quantity = (int)row.getCell(quantityIdx).getNumericCellValue();

					if (row.getCell(optionIdx) != null) {
						// �ɼ��� �����ϴ� ���
						String thisRowOption = row.getCell(optionIdx).getStringCellValue();
						String processedOption = naverOptionDict.get(thisRowOption);
						
						if (processedOption != null) {
							thisRowDisplayedProductName = processedOption;
							naverCount.replace(processedOption, naverCount.get(processedOption) + quantity);
						}
						else {
							// �ɼ� ���� + naverDict�� ��ϵ��� ���� ���� ��å���󰡴� ��ǰ
							if (thisRowOption.contains("���û���")) thisRowDisplayedProductName = thisRowOption.substring(6, thisRowOption.length());
							else thisRowDisplayedProductName += " " + thisRowOption;
						}
					}
					else if (thisRowProcessedProductName != null) {
						// �ɼ��� �������� �����鼭 naverProductDict�� ��ϵ� ��ǰ
						thisRowDisplayedProductName = thisRowProcessedProductName;
						naverCount.replace(thisRowProcessedProductName, naverCount.get(thisRowProcessedProductName) + quantity);
					}
					
					
					if (quantity == 1) naverData.setProductName(displayedProductName + " // " + thisRowDisplayedProductName);
					else naverData.setProductName(displayedProductName + " // " + thisRowDisplayedProductName + " (" + quantity + "��)");
					
					continue;
				}

				// �������̸�
				naverData.setReceiverName(receiverName);
				
				// �����ο���ó1
				naverData.setReceiverPhone1(receiverPhone);
				
				// ���ż�
				int quantity = (int)row.getCell(quantityIdx).getNumericCellValue();
				naverData.setQuantity(quantity);
				
				// ������  �ּ�
				naverData.setReceiverAddress(row.getCell(receiverAddressIdx).getStringCellValue());
				
				// ��ǰ��
				String itemName = row.getCell(productNameIdx).getStringCellValue();
				String tag = "�� - ";

				// �ɼ��� ���ٸ�
				if (row.getCell(optionIdx) == null) {
					// dict �� �κ�
					String processedItemName = naverProductDict.get(itemName);
					
					// dict�� �����ϴ� �̸��̸�
					if (processedItemName != null) {
						naverData.setProductName(tag + processedItemName);
						naverCount.replace(processedItemName, naverCount.get(processedItemName) + quantity);
					}
					else naverData.setProductName(tag + itemName);
				}
				else {
					// �ɼ��� �ִٸ�
					String option = row.getCell(optionIdx).getStringCellValue();
					String processedOptionName = naverOptionDict.get(option);

					if (processedOptionName != null) {
						naverData.setProductName(tag + processedOptionName);
						naverCount.replace(processedOptionName, naverCount.get(processedOptionName) + quantity);
					}
					else {
						// dict�� ������ ���� ��å�� ����
						if (option.contains("���û���")) naverData.setProductName(tag + option.substring(6, option.length()));
						else naverData.setProductName(tag + itemName + " " + option);
					}
				}
				
				if (quantity > 1) naverData.setProductName(naverData.getProductName() + " (" + quantity + "��)"); 
				// ��۸޼���
				String deliveryMessage = "";
				if (row.getCell(etcIdx) != null) deliveryMessage = row.getCell(etcIdx).getStringCellValue();
				naverData.setDeliveryMessage(deliveryMessage);
				
				map.put(orderNum, naverData);		
				naverLs.add(naverData);
			}
		}
		
		for (NaverColumnDto naverData : naverLs) {
			CnpInputDto cnpInput = new CnpInputDto();
			cnpInput.setReceiverName(naverData.getReceiverName());
			cnpInput.setReceiverPhone(naverData.getReceiverPhone1());
			cnpInput.setReceiverAddress(naverData.getReceiverAddress());
			cnpInput.setOrderContents(naverData.getProductName());
			cnpInput.setRemark(naverData.getDeliveryMessage());
			
			answer.add(cnpInput);
		}
		return answer;
	}

	// coupang -> CNP
	public static void coupangToCnp(String directoryURI, String fileName) throws IOException, FileNotFoundException, TransformationComplete {
		// cnp input list
		List<CnpInputDto> cnpInputLs = readCoupang(directoryURI + fileName);
		List<Pair> coupangCountLs = coupangCountList();
		coupangCount(directoryURI, coupangCountLs);
		
		final int columnSize = 5;
		
		HSSFWorkbook xlsWb = new HSSFWorkbook();
		
		// sheet ����
		HSSFSheet sheet = xlsWb.createSheet("��۰���");
		
		// ��Ÿ��
		CellStyle menu = xlsWb.createCellStyle();
		CellStyle defaultStyle = xlsWb.createCellStyle();
		
		
		// �ٹٲ�
		menu.setWrapText(true);
		defaultStyle.setWrapText(true);
		
		// �޴�
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		
		cell.setCellValue("�������̸�");
		
		cell = row.createCell(1);
		cell.setCellValue("��������ȭ��ȣ");
		cell = row.createCell(2);
		cell.setCellValue("������ �ּ�");
		cell = row.createCell(3);
		cell.setCellValue("�����ǰ��(�ɼǸ�)");
		cell = row.createCell(4);
		cell.setCellValue("���");
		
		for (int i = 1; i <= cnpInputLs.size(); i++) {
			CnpInputDto cnpInput = cnpInputLs.get(i - 1);
			row = sheet.createRow(i);
			
			cell = row.createCell(0);
			cell.setCellValue(cnpInput.getReceiverName());
			
			cell = row.createCell(1);
			cell.setCellValue(cnpInput.getReceiverPhone());
			
			cell = row.createCell(2);
			cell.setCellValue(cnpInput.getReceiverAddress());
			
			cell = row.createCell(3);
			cell.setCellValue(cnpInput.getOrderContents());
			
			cell = row.createCell(4);
			cell.setCellValue(cnpInput.getRemark());
		}
		
		// ��� ���ϸ� �䱸���� �ʿ�
		File xlsFile = new File(directoryURI  + LocalDate.now() + " ���� - Cnp.xls");
		FileOutputStream fileOut = new FileOutputStream(xlsFile);
		xlsWb.write(fileOut);	
		fileOut.close();
		
		throw new TransformationComplete();
	}
	
	public static void coupangCount(String directoryURI, List<Pair> coupangCountLs) throws IOException{
		XSSFWorkbook xlsxWb = new XSSFWorkbook();
		
		// sheet ����
		XSSFSheet sheet = xlsxWb.createSheet(LocalDate.now() + "���� �Ǹŷ�");
		
		// ��Ÿ��
		CellStyle menu = xlsxWb.createCellStyle();
		CellStyle defaultStyle = xlsxWb.createCellStyle();
		
		// �ٹٲ�
		menu.setWrapText(true);
		defaultStyle.setWrapText(true);
		
		// �޴�
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell = row.createCell(0);
		cell.setCellValue("�Ǹ� ��ǰ �̸�");
		row.createCell(1);
		cell.setCellValue("�Ǹŷ�");

		for (int i = 1; i <= coupangCountLs.size(); i++) {
			Pair pair = coupangCountLs.get(i - 1);
			row = sheet.createRow(i);
			
			cell = row.createCell(0);
			cell.setCellValue(pair.productName);
			cell = row.createCell(1);
			cell.setCellValue(pair.count);
		}
		
		// ��� ���ϸ� �䱸���� �ʿ�
		File xlsxFile = new File(directoryURI + LocalDate.now() + " ���� �Ǹŷ�.xlsx");
		FileOutputStream fileOut = new FileOutputStream(xlsxFile);
		xlsxWb.write(fileOut);	
		fileOut.close();
	}
	
	public static List<Pair> coupangCountList() {
		List<Pair> answer = new ArrayList<>();
		int total = 0;
		for (Entry<String, Integer> entry : coupangCount.entrySet()) {
			answer.add(new Pair(entry.getKey(), entry.getValue()));
			total += entry.getValue();
		}
		Collections.sort(answer);
		
		answer.add(new Pair("�հ�", total));
		
		return answer;
	}
	
	public static void naverCount(String directoryURI, List<Pair> naverCountLs) throws IOException{
		XSSFWorkbook xlsxWb = new XSSFWorkbook();
		
		// sheet ����
		XSSFSheet sheet = xlsxWb.createSheet(LocalDate.now() + "���̹� �Ǹŷ�");
		
		// ��Ÿ��
		CellStyle menu = xlsxWb.createCellStyle();
		CellStyle defaultStyle = xlsxWb.createCellStyle();
		
		// �ٹٲ�
		menu.setWrapText(true);
		defaultStyle.setWrapText(true);
		
		// �޴�
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell = row.createCell(0);
		cell.setCellValue("�Ǹ� ��ǰ �̸�");
		row.createCell(1);
		cell.setCellValue("�Ǹŷ�");

		for (int i = 1; i <= naverCountLs.size(); i++) {
			Pair pair = naverCountLs.get(i - 1);
			row = sheet.createRow(i);
			
			cell = row.createCell(0);
			cell.setCellValue(pair.productName);
			cell = row.createCell(1);
			cell.setCellValue(pair.count);
		}
		
		// ��� ���ϸ� �䱸���� �ʿ�
		File xlsxFile = new File(directoryURI + LocalDate.now() + " ���̹� �Ǹŷ�.xlsx");
		FileOutputStream fileOut = new FileOutputStream(xlsxFile);
		xlsxWb.write(fileOut);	
		fileOut.close();
	}
	
	public static List<Pair> naverCountList() {
		List<Pair> answer = new ArrayList<>();
		int total = 0;
		for (Entry<String, Integer> entry : naverCount.entrySet()) {
			answer.add(new Pair(entry.getKey(), entry.getValue()));
			total += entry.getValue();
		}
		Collections.sort(answer);
		
		answer.add(new Pair("�հ�", total));
		
		return answer;
	}
	
	
	// naver -> CNP
	public static void naverToCnp(String directoryURI, String fileName) throws IOException, FileNotFoundException, TransformationComplete {
		// cnp input list
		List<CnpInputDto> cnpInputLs = readNaver(directoryURI + fileName);
		naverCount(directoryURI, naverCountList());
		final int columnSize = 5;
		
		HSSFWorkbook xlsWb = new HSSFWorkbook();
		
		// sheet ����
		HSSFSheet sheet = xlsWb.createSheet("��۰���");
		
		// ��Ÿ��
		CellStyle menu = xlsWb.createCellStyle();
		CellStyle defaultStyle = xlsWb.createCellStyle();
		
		// �ٹٲ�
		menu.setWrapText(true);
		defaultStyle.setWrapText(true);
		
		// �޴�
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("�������̸�");
		
		cell = row.createCell(1);
		cell.setCellValue("��������ȭ��ȣ");
		cell = row.createCell(2);
		cell.setCellValue("������ �ּ�");
		cell = row.createCell(3);
		cell.setCellValue("�����ǰ��(�ɼǸ�)");
		cell = row.createCell(4);
		cell.setCellValue("���");
		
		for (int i = 1; i <= cnpInputLs.size(); i++) {
			CnpInputDto cnpInput = cnpInputLs.get(i - 1);
			row = sheet.createRow(i);
			
			cell = row.createCell(0);
			cell.setCellValue(cnpInput.getReceiverName());
			
			cell = row.createCell(1);
			cell.setCellValue(cnpInput.getReceiverPhone());
			
			cell = row.createCell(2);
			cell.setCellValue(cnpInput.getReceiverAddress());
			
			cell = row.createCell(3);
			cell.setCellValue(cnpInput.getOrderContents());
			
			cell = row.createCell(4);
			cell.setCellValue(cnpInput.getRemark());
		}
		
		// ��� ���ϸ� �䱸���� �ʿ�
		File xlsFile = new File(directoryURI  + LocalDate.now() + " ���̹� - Cnp.xls");
		FileOutputStream fileOut = new FileOutputStream(xlsFile);
		xlsWb.write(fileOut);	
		fileOut.close();
		
		throw new TransformationComplete();
	}
	
	public static List<CoupangColumnDto> readCoupangForBillway(String fileURI) throws IOException {
		List<CoupangColumnDto> answer = new ArrayList<>();
		
		FileInputStream fi = new FileInputStream(fileURI);
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(fi);
		XSSFSheet sheet = workbook.getSheetAt(0); // �ش� ���������� ��Ʈ��
		int rows = sheet.getPhysicalNumberOfRows(); // �ش� ��Ʈ�� ���� ����
//		System.out.println(rows);
		XSSFRow row = sheet.getRow(0);
		int cells = row.getPhysicalNumberOfCells();
		
		int idxA = -1, idxB = -1, idxC = -1, idxD = -1, idxE = -1, idxF = -1, idxG = -1, idxH = -1, idxI = -1,
				idxJ = -1, idxK = -1, idxL = -1, idxM = -1,
				idxN = -1, idxO = -1, idxP = -1, idxQ = -1, 
				idxR = -1, idxS = -1, idxT = -1, idxU = -1, 
				idxV = -1, idxW = -1, idxX = -1, idxY = -1, 
				idxZ = -1, idxAA = -1, 
				idxAB = -1, idxAC = -1, idxAD = -1, idxAE = -1,
				idxAF = -1, idxAG = -1, idxAH = -1, idxAI = -1, 
				idxAJ = -1, idxAK = -1, idxAL = -1, idxAM = -1, idxAN= -1;
		
		for (int colIdx = 0; colIdx < cells; colIdx++) {
			String menu = row.getCell(colIdx).getStringCellValue();
			
			if (menu.equals("��ȣ")) idxA = colIdx;
			if (menu.equals("������۹�ȣ")) idxB = colIdx;
			if (menu.equals("�ֹ���ȣ")) idxC = colIdx;
			if (menu.equals("�ù��")) idxD = colIdx;
			if (menu.equals("������ȣ")) idxE = colIdx;
			if (menu.equals("�и���� Y/N")) idxF = colIdx;
			if (menu.equals("�и���� �������")) idxG = colIdx;
			if (menu.equals("�ֹ��� �������")) idxH = colIdx;
			if (menu.equals("�����(�߼���)")) idxI = colIdx;
			if (menu.equals("�ֹ���")) idxJ = colIdx;
			if (menu.equals("��ϻ�ǰ��")) idxK = colIdx;
			if (menu.equals("��ϿɼǸ�")) idxL = colIdx;
			if (menu.equals("�����ǰ��(�ɼǸ�)")) idxM = colIdx;
			if (menu.equals("�����ǰID")) idxN = colIdx;
			if (menu.equals("�ɼ�ID")) idxO = colIdx;
			if (menu.equals("���ʵ�ϿɼǸ�")) idxP = colIdx;
			if (menu.equals("��ü��ǰ�ڵ�")) idxQ = colIdx;
			if (menu.equals("���ڵ�")) idxR = colIdx;
			if (menu.equals("������")) idxS = colIdx;
			if (menu.equals("��ۺ񱸺�")) idxT = colIdx;
			if (menu.equals("��ۺ�")) idxU = colIdx;
			if (menu.equals("�����갣 �߰���ۺ�")) idxV = colIdx;
			if (menu.equals("���ż�(����)")) idxW = colIdx;
			if (menu.equals("�ɼ��ǸŰ�(�ǸŴܰ�)")) idxX = colIdx;
			if (menu.equals("������")) idxY = colIdx;
			if (menu.equals("��������ȭ��ȣ")) idxZ = colIdx;
			if (menu.equals("�������̸�")) idxAA = colIdx;
			if (menu.equals("��������ȭ��ȣ")) idxAB = colIdx;
			if (menu.equals("�����ȣ")) idxAC = colIdx;
			if (menu.equals("������ �ּ�")) idxAD = colIdx;
			if (menu.equals("��۸޼���")) idxAE = colIdx;
			if (menu.equals("��ǰ�� �߰��޽���")) idxAF = colIdx;
			if (menu.equals("�ֹ��� �߰��޽���")) idxAG = colIdx;
			if (menu.equals("��ۿϷ���")) idxAH = colIdx;
			if (menu.equals("����Ȯ������")) idxAI = colIdx;
			if (menu.equals("���������ȣ(PCCC)")) idxAJ = colIdx;
			if (menu.equals("����뱸������ȭ��ȣ")) idxAK = colIdx;
			if (menu.equals("��Ÿ")) idxAL = colIdx;
			if (menu.equals("������ġ")) idxAM = colIdx;

		}
		

		// �Ľ�
		for (int rowIdx = 1; rowIdx < rows; rowIdx++) {
			row = sheet.getRow(rowIdx); // �� ���� �о�´�
			CoupangColumnDto coupangData = new CoupangColumnDto();
			
			if (row != null) {
				
				int thisNum = 0;
				String shippingNum = "";
				String orderNum = "";
				String courier = "";
				String waybillNum = "";
				String separateDelivery = "";
				String separateExpectedDeliveryDate = "";
				String expectedDeliveryDate = "";
				String deliveryDate = "";
				String orderDate = "";
				String productName = "";
				String optionName = "";
				String displayedProductName = "";
				String displayedProductId = "";
				String optionId = "";
				String firstOptionName = "";
				String productCode = "";
				String barcode = "";
				Integer payment = null;
				String deliveryFeeFlag = "";
				Integer deliveryFee = null;
				Integer additionalDeliveryFee = null;
				Integer quantity = null;
				Integer unitPrice = null;
				String customerName = "";
				String customerEmail = "";
				String customerPhone = "";
				String receiverName = "";
				String receiverPhone = "";
				String postNum = "";
				String receiverAddress = "";
				String deliveryMessage = "";
				String additionalMessagePerItem = "";
				String ordererAdditionalMessage = "";
				String deliveryCompleteDate = "";
				String confirmationPurchaseDate = "";
				String pccc = "";
				String buyerPhoneNumForCustomsClearance = "";
				String etc = "";
				String paymentLocation = "";
				
				if (row.getCell(idxA) != null)	thisNum = Integer.parseInt(row.getCell(idxA).getStringCellValue());
				else throw new IOException();
				if (row.getCell(idxB) != null) shippingNum = row.getCell(idxB).getStringCellValue();
				else throw new IOException();
				if (row.getCell(idxC) != null) orderNum = row.getCell(idxC).getStringCellValue();
				else throw new IOException();
				
				if (row.getCell(idxD) != null) courier = row.getCell(idxD).getStringCellValue();
				if (row.getCell(idxE) != null) waybillNum = row.getCell(idxE).getStringCellValue();
				if (row.getCell(idxF) != null) separateDelivery = row.getCell(idxF).getStringCellValue();				
				if (row.getCell(idxG) != null) separateExpectedDeliveryDate = row.getCell(idxG).getStringCellValue();
				if (row.getCell(idxH) != null) expectedDeliveryDate = row.getCell(idxH).getStringCellValue();
				if (row.getCell(idxI) != null) deliveryDate = row.getCell(idxI).getStringCellValue();				
				if (row.getCell(idxJ) != null) orderDate = row.getCell(idxJ).getStringCellValue();
				if (row.getCell(idxK) != null) productName = row.getCell(idxK).getStringCellValue();				
				if (row.getCell(idxL) != null) optionName = row.getCell(idxL).getStringCellValue();				
				if (row.getCell(idxM) != null) displayedProductName = row.getCell(idxM).getStringCellValue();
				if (row.getCell(idxN) != null) displayedProductId = row.getCell(idxN).getStringCellValue();
				if (row.getCell(idxO) != null) optionId = row.getCell(idxO).getStringCellValue();
				if (row.getCell(idxP) != null) firstOptionName = row.getCell(idxP).getStringCellValue();
				if (row.getCell(idxQ) != null) productCode = row.getCell(idxQ).getStringCellValue();
				if (row.getCell(idxR) != null) barcode = row.getCell(idxR).getStringCellValue();
				
				if (row.getCell(idxS) != null) payment = Integer.parseInt(row.getCell(idxS).getStringCellValue());
				else throw new IOException();
				if (row.getCell(idxT) != null) deliveryFeeFlag = row.getCell(idxT).getStringCellValue();
				else throw new IOException();
				if (row.getCell(idxU) != null) deliveryFee = Integer.parseInt(row.getCell(idxU).getStringCellValue());
				else throw new IOException();
				if (row.getCell(idxV) != null) additionalDeliveryFee = Integer.parseInt(row.getCell(idxV).getStringCellValue());
				else throw new IOException();
				if (row.getCell(idxW) != null) quantity = Integer.parseInt(row.getCell(idxW).getStringCellValue());
				else throw new IOException();				
				if (row.getCell(idxX) != null) unitPrice = Integer.parseInt(row.getCell(idxX).getStringCellValue());
				else throw new IOException();
				
				if (row.getCell(idxY) != null) customerName = row.getCell(idxY).getStringCellValue();				
				if (row.getCell(idxZ) != null) customerPhone = row.getCell(idxZ).getStringCellValue();
				else throw new IOException();
				
				if (row.getCell(idxAA) != null) receiverName = row.getCell(idxAA).getStringCellValue();
				else throw new IOException();
				
				if (row.getCell(idxAB) != null) receiverPhone = row.getCell(idxAB).getStringCellValue();

				if (row.getCell(idxAC) != null) postNum = row.getCell(idxAC).getStringCellValue();
				else throw new IOException();

				if (row.getCell(idxAD) != null) receiverAddress = row.getCell(idxAD).getStringCellValue();
				if (row.getCell(idxAE) != null) deliveryMessage = row.getCell(idxAE).getStringCellValue();
				if (row.getCell(idxAF) != null) additionalMessagePerItem = row.getCell(idxAF).getStringCellValue();
				if (row.getCell(idxAG) != null) ordererAdditionalMessage = row.getCell(idxAG).getStringCellValue();
				if (row.getCell(idxAH) != null) deliveryCompleteDate = row.getCell(idxAH).getStringCellValue();
				if (row.getCell(idxAI) != null) confirmationPurchaseDate = row.getCell(idxAI).getStringCellValue();
				if (row.getCell(idxAJ) != null) pccc = row.getCell(idxAJ).getStringCellValue();
				if (row.getCell(idxAK) != null) buyerPhoneNumForCustomsClearance = row.getCell(idxAK).getStringCellValue();
				if (row.getCell(idxAL) != null) etc = row.getCell(idxAL).getStringCellValue();
				if (row.getCell(idxAM) != null) paymentLocation = row.getCell(idxAM).getStringCellValue();
				
				coupangData.setNum(thisNum);
				coupangData.setShippingNum(shippingNum);
				coupangData.setOrderNum(orderNum);
				coupangData.setCourier(courier);
				coupangData.setWaybillNum(waybillNum);
				coupangData.setSeparateDelivery(separateDelivery);
				coupangData.setSeparateExpectedDeliveryDate(separateExpectedDeliveryDate);
				coupangData.setExpectedDeliveryDate(expectedDeliveryDate);
				coupangData.setDeliveryDate(deliveryDate);
				coupangData.setOrderDate(orderDate);
				coupangData.setProductName(displayedProductName);
				coupangData.setOptionName(firstOptionName);
				if (quantity == 1) coupangData.setDisplayedProductName(displayedProductName);
				else coupangData.setDisplayedProductName(displayedProductName + " (" + quantity + "��)");
				coupangData.setDisplayedProductId(displayedProductId);
				coupangData.setOptionId(optionId);
				coupangData.setFirstOptionName(firstOptionName);
				coupangData.setProductCode(productCode);
				coupangData.setBarcode(barcode);
				coupangData.setPayment(payment);
				coupangData.setDeliveryFeeFlag(deliveryFeeFlag);
				coupangData.setDeliveryFee(deliveryFee);
				coupangData.setAdditionalDeliveryFee(additionalDeliveryFee);
				coupangData.setQuantity(quantity);
				coupangData.setUnitPrice(unitPrice);
				coupangData.setCustomerName(customerName);
				coupangData.setCustomerEmail(customerEmail);
				coupangData.setCustomerPhone(customerPhone);
				coupangData.setReceiverName(receiverName);
				coupangData.setReceiverPhone(processPhone(receiverPhone));
				coupangData.setPostNum(postNum);
				coupangData.setReceiverAddress(receiverAddress);
				coupangData.setDeliveryMessage(deliveryMessage);
				coupangData.setAdditionalMessagePerItem(additionalMessagePerItem);
				coupangData.setOrdererAdditionalMessage(ordererAdditionalMessage);
				coupangData.setDeliveryCompleteDate(deliveryCompleteDate);
				coupangData.setConfirmationPurchaseDate(confirmationPurchaseDate);
				coupangData.setPccc(pccc);
				coupangData.setBuyerPhoneNumForCustomsClearance(buyerPhoneNumForCustomsClearance);
				coupangData.setEtc(etc);
				coupangData.setPaymentLocation(paymentLocation);
								
				answer.add(coupangData);
			}
		}	
		return answer;
	}
	
	public static List<NaverColumnDto> readNaverForBillway(String fileURI) throws IOException {
		List<NaverColumnDto> answer = new ArrayList<>();
		
		FileInputStream fi = new FileInputStream(fileURI);
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(fi);
		XSSFSheet sheet = workbook.getSheetAt(0); // �ش� ���������� ��Ʈ��
		int rows = sheet.getPhysicalNumberOfRows(); // �ش� ��Ʈ�� ���� ����
//		System.out.println(rows);
		
		// �Ľ�
		for (int rowIdx = 2; rowIdx < rows; rowIdx++) {
			XSSFRow row = sheet.getRow(rowIdx); // �� ���� �о�´�
			NaverColumnDto naverData = new NaverColumnDto();
			
			if (row != null) {
				int cells = row.getPhysicalNumberOfCells();
				
				String productOrderNum = "";
				// �ֹ���ȣ
				String orderNum = "";
				// ��۹��
				String shippingMethod = "";
				// �ù��
				String courier = "";
				// �����ȣ
				String waybillNum = "";
				// �߼���
				String shippingDate = "";
				// �����θ�
				String receiverName = "";
				// ��ǰ��
				String productName = "";
				// �ɼ�����
				String optionInfo = "";
				// ����
				Integer quantity = null;
				// ��ۺ� ����
				String shippingCostForm = "";
				// �����ο���ó1
				String receiverPhone1 = "";
				// �����
				String receiverAddress = "";
				// ��۸޼���
				String deliveryMessage = "";
				// �����
				String shipFrom = "";
				// ��������
				String methodOfPayment = "";
				// ������ ���ݱ���
				String feeChargingCategory = "";
				// ������������
				String feePaymentMethod = "";
				// ����������
				Integer paymentFee = null;
				// ���⿬�� ������
				Integer salesLinkageFee = null;
				// ���꿹���ݾ�
				Integer estimatedTotalAmount = null;
				// ���԰��
				String channel = "";
				// ������ �ֹε�Ϲ�ȣ
				String buyerSSN = "";
				// �������������ȣ
				String pccc = "";
				// �ֹ��Ͻ�
				String orderDateTime = "";
				// 1�� �ֹ��Ǽ�
				Integer numOfOrdersPerYear = null;
				// ������ID
				String buyerId = "";
				// �����ڸ�
				String buyerName = "";
				// ������
				String paymentDate = "";
				// ��ǰ����
				String productType = "";
				// �ֹ����λ���
				String orderDetailStatus = "";
				// �ֹ�����
				String orderStatus = "";
				// ��ǰ��ȣ
				String itemNum = "";
				// ��ۼӼ�
				String deliveryProperty = "";
				// ��������
				String wantDeliveryDate = "";
				// (�����ο���ó1)
				String _receiverPhone1 = "";
				// (�����ο���ó2)
				String _receiverPhone2 = "";
				// (�����ȣ)
				String zipcode = "";
				// (�⺻�ּ�)
				String receiverAddress1 = "";
				// (���ּ�)
				String receiverAddress2 = "";
				// (�����ڿ���ó)
				String buyerPhone = "";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
				sdf.setTimeZone(tz);
				
				if (row.getCell(0) != null)	productOrderNum = row.getCell(0).getStringCellValue();
				if (row.getCell(1) != null) orderNum = row.getCell(1).getStringCellValue();
				if (row.getCell(2) != null) shippingMethod = row.getCell(2).getStringCellValue();				
				if (row.getCell(3) != null) courier = row.getCell(3).getStringCellValue();
				if (row.getCell(4) != null) waybillNum = row.getCell(4).getStringCellValue();
				if (row.getCell(5) != null) shippingDate = sdf.format(row.getCell(5).getDateCellValue());				
				if (row.getCell(6) != null) receiverName = row.getCell(6).getStringCellValue();
				if (row.getCell(7) != null) productName = row.getCell(7).getStringCellValue();
				if (row.getCell(8) != null) optionInfo = row.getCell(8).getStringCellValue();				
				if (row.getCell(9) != null) quantity = (int)row.getCell(9).getNumericCellValue();				
				if (row.getCell(10) != null) shippingCostForm = row.getCell(10).getStringCellValue();				
				if (row.getCell(11) != null) receiverPhone1 = row.getCell(11).getStringCellValue();				
				if (row.getCell(12) != null) receiverAddress = row.getCell(12).getStringCellValue();
				if (row.getCell(13) != null) deliveryMessage = row.getCell(13).getStringCellValue();
				if (row.getCell(14) != null) shipFrom = row.getCell(14).getStringCellValue();
				if (row.getCell(15) != null) methodOfPayment = row.getCell(15).getStringCellValue();
				if (row.getCell(16) != null) feeChargingCategory = row.getCell(16).getStringCellValue();
				if (row.getCell(17) != null) feePaymentMethod = row.getCell(17).getStringCellValue();
				if (row.getCell(18) != null) paymentFee = (int)row.getCell(18).getNumericCellValue();
				if (row.getCell(19) != null) salesLinkageFee = (int)row.getCell(19).getNumericCellValue();
				if (row.getCell(20) != null) estimatedTotalAmount = (int)row.getCell(20).getNumericCellValue();
				if (row.getCell(21) != null) channel = row.getCell(21).getStringCellValue();
				if (row.getCell(22) != null) buyerSSN = row.getCell(22).getStringCellValue();
				if (row.getCell(23) != null) pccc = row.getCell(23).getStringCellValue();				
				if (row.getCell(24) != null) orderDateTime = sdf.format(row.getCell(24).getDateCellValue());				
				if (row.getCell(25) != null) numOfOrdersPerYear = (int)row.getCell(25).getNumericCellValue();
				if (row.getCell(26) != null) buyerId = row.getCell(26).getStringCellValue();				
				if (row.getCell(27) != null) buyerName = row.getCell(27).getStringCellValue();				
				if (row.getCell(28) != null) paymentDate = sdf.format(row.getCell(28).getDateCellValue());
				if (row.getCell(29) != null) productType = row.getCell(29).getStringCellValue();
				if (row.getCell(30) != null) orderDetailStatus = row.getCell(30).getStringCellValue();
				if (row.getCell(31) != null) orderStatus = row.getCell(31).getStringCellValue();
				if (row.getCell(32) != null) itemNum = row.getCell(32).getStringCellValue();
				if (row.getCell(33) != null) deliveryProperty = row.getCell(33).getStringCellValue();
				if (row.getCell(34) != null) wantDeliveryDate = row.getCell(34).getStringCellValue();
				if (row.getCell(35) != null) _receiverPhone1 = row.getCell(35).getStringCellValue();
				if (row.getCell(36) != null) _receiverPhone2 = row.getCell(36).getStringCellValue();
				if (row.getCell(37) != null) zipcode = row.getCell(37).getStringCellValue();
				if (row.getCell(38) != null) receiverAddress1 = row.getCell(38).getStringCellValue();
				if (row.getCell(39) != null) receiverAddress2 = row.getCell(39).getStringCellValue();
				if (row.getCell(40) != null) buyerPhone = row.getCell(40).getStringCellValue();
				
				receiverPhone1 = processPhone(receiverPhone1);
				
				naverData.setProductOrderNum(productOrderNum);
				naverData.setProductName(productName);
				naverData.setOrderNum(orderNum);
				naverData.setShippingMethod(shippingMethod);
				naverData.setCourier(courier);
				naverData.setWaybillNum(waybillNum);
				naverData.setShippingDate(shippingDate);
				naverData.setReceiverName(receiverName);
				naverData.setProductName(productName);
				naverData.setOptionInfo(optionInfo);
				naverData.setQuantity(quantity);
				naverData.setShippingCostForm(shippingCostForm);
				naverData.setReceiverPhone1(receiverPhone1);
				naverData.setReceiverAddress(receiverAddress);
				naverData.setDeliveryMessage(deliveryMessage);
				naverData.setShipFrom(shipFrom);
				naverData.setMethodOfPayment(methodOfPayment);
				naverData.setFeeChargingCategory(feeChargingCategory);
				naverData.setFeePaymentMethod(feePaymentMethod);
				naverData.setPaymentFee(paymentFee);
				naverData.setSalesLinkageFee(salesLinkageFee);
				naverData.setEstimatedTotalAmount(estimatedTotalAmount);
				naverData.setChannel(channel);
				naverData.setBuyerSSN(buyerSSN);
				naverData.setPccc(pccc);
				naverData.setOrderDateTime(orderDateTime);
				naverData.setNumOfOrdersPerYear(numOfOrdersPerYear);
				naverData.setBuyerId(buyerId);
				naverData.setPaymentDate(paymentDate);
				naverData.setProductType(productType);
				naverData.setOrderDetailStatus(orderDetailStatus);
				naverData.setOrderStatus(orderStatus);
				naverData.setItemNum(itemNum);
				naverData.setDeliveryProperty(deliveryProperty);
				naverData.setWantDeliveryDate(wantDeliveryDate);
				naverData.set_receiverPhone1(_receiverPhone1);
				naverData.set_receiverPhone2(_receiverPhone2);
				naverData.setZipcode(zipcode);
				naverData.setReceiverAddress1(receiverAddress1);
				naverData.setReceiverAddress2(receiverAddress2);
				naverData.setBuyerPhone(buyerPhone);
				
				answer.add(naverData);
			}
		}	
		return answer;
	}
	
	public static HashMap<String, String> readCnp(String fileURI) throws IOException {
		HashMap<String, String> answer = new HashMap<>();
		FileInputStream fi = new FileInputStream(fileURI);
		
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook(fi);
		HSSFSheet sheet = workbook.getSheetAt(0); // �ش� ���������� ��Ʈ��
		int rows = sheet.getPhysicalNumberOfRows(); // �ش� ��Ʈ�� ���� ����
//		System.out.println(rows);
		
		// �Ľ�
		for (int rowIdx = 1; rowIdx < rows; rowIdx++) {
			HSSFRow row = sheet.getRow(rowIdx); // �� ���� �о�´�
			CnpOutputDto cnpOutput = new CnpOutputDto();
			
			
			if (row != null) {				
				// ���� ����ִ� ���� �д´�.
				String waybillNum = row.getCell(7).getStringCellValue();
				String receiverName = row.getCell(20).getStringCellValue();
				String receiverPhone = row.getCell(21).getStringCellValue();
				
				answer.put(receiverName + receiverPhone, waybillNum);
			}
		}
		return answer;
	}
	
	public static void cnpToBoth(String coupangDirectoryURI, String coupangFileName, String naverDirectoryURI, String naverFileName, String cnpDirectoryURI, String cnpFileName) throws IOException, TransformationComplete {
		List<CoupangColumnDto> coupangLs = readCoupangForBillway(coupangDirectoryURI + coupangFileName);
		List<NaverColumnDto> naverLs = readNaverForBillway(naverDirectoryURI + naverFileName);
		HashMap<String, String> customerToWaybill = readCnp(cnpDirectoryURI + cnpFileName);
		
		for (CoupangColumnDto coupangData : coupangLs) {
			String key = coupangData.getReceiverName() + coupangData.getReceiverPhone();
			coupangData.setWaybillNum(customerToWaybill.get(key));
		}
		
		for (NaverColumnDto naverData : naverLs) {
			String key = naverData.getReceiverName() + naverData.getReceiverPhone1();
			naverData.setWaybillNum(customerToWaybill.get(key));
		}
		
		// ���� ����, ���̹� ���
		outputCoupang(coupangDirectoryURI, coupangLs);
		outputNaver(naverDirectoryURI, naverLs);
		
		throw new TransformationComplete();
	}
	
	public static void cnpToCoupang(String coupangDirectoryURI, String coupangFileName, String cnpDirectoryURI, String cnpFileName) throws IOException, TransformationComplete {
		List<CoupangColumnDto> coupangLs = readCoupangForBillway(coupangDirectoryURI + coupangFileName);
		HashMap<String, String> customerToWaybill = readCnp(cnpDirectoryURI + cnpFileName);
		
		for (CoupangColumnDto coupangData : coupangLs) {
			String key = coupangData.getReceiverName() + coupangData.getReceiverPhone();
			coupangData.setWaybillNum(customerToWaybill.get(key));
		}
		outputCoupang(coupangDirectoryURI, coupangLs);
		throw new TransformationComplete();
	}

	public static void cnpToNaver(String naverDirectoryURI, String naverFileName, String cnpDirectoryURI, String cnpFileName) throws IOException, TransformationComplete {
		List<NaverColumnDto> naverLs = readNaverForBillway(naverDirectoryURI + naverFileName);
		HashMap<String, String> customerToWaybill = readCnp(cnpDirectoryURI + cnpFileName);
		
		for (NaverColumnDto naverData : naverLs) {
			String key = naverData.getReceiverName() + naverData.getReceiverPhone1();
			naverData.setWaybillNum(customerToWaybill.get(key));
		}
		outputNaver(naverDirectoryURI, naverLs);
		throw new TransformationComplete();
	}
	
	public static void outputCoupang(String directoryURI, List<CoupangColumnDto> coupangLs) throws IOException, TransformationComplete {
		XSSFWorkbook xlsxWb = new XSSFWorkbook();
		
		// sheet ����
		XSSFSheet sheet = xlsxWb.createSheet("��۰���");
		
		// ��Ÿ��
		CellStyle menu = xlsxWb.createCellStyle();
		CellStyle defaultStyle = xlsxWb.createCellStyle();
		
		// �ٹٲ�
		menu.setWrapText(true);
		defaultStyle.setWrapText(true);
		
		// �޴�
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell = row.createCell(0);
		cell.setCellValue("��ȣ");		
		cell = row.createCell(1);
		cell.setCellValue("������۹�ȣ");
		cell = row.createCell(2);
		cell.setCellValue("�ֹ���ȣ");
		cell = row.createCell(3);
		cell.setCellValue("�ù��");
		cell = row.createCell(4);
		cell.setCellValue("������ȣ");
		cell = row.createCell(5);
		cell.setCellValue("�и���� Y/N");
		cell = row.createCell(6);
		cell.setCellValue("�и���� �������");
		cell = row.createCell(7);
		cell.setCellValue("�ֹ��� �������");
		cell = row.createCell(8);
		cell.setCellValue("�����(�߼���)");
		cell = row.createCell(9);
		cell.setCellValue("�ֹ���");
		cell = row.createCell(10);
		cell.setCellValue("��ϻ�ǰ��");
		cell = row.createCell(11);
		cell.setCellValue("��ϿɼǸ�");
		cell = row.createCell(12);
		cell.setCellValue("�����ǰ��");
		cell = row.createCell(13);
		cell.setCellValue("�����ǰID");
		cell = row.createCell(14);
		cell.setCellValue("�ɼ�ID");
		cell = row.createCell(15);
		cell.setCellValue("���ʵ�ϿɼǸ�");
		cell = row.createCell(16);
		cell.setCellValue("��ü��ǰ�ڵ�");
		cell = row.createCell(17);
		cell.setCellValue("���ڵ�");
		cell = row.createCell(18);
		cell.setCellValue("������");
		cell = row.createCell(19);
		cell.setCellValue("��ۺ񱸺�");
		cell = row.createCell(20);
		cell.setCellValue("��ۺ�");
		cell = row.createCell(21);
		cell.setCellValue("�����갣 �߰���ۺ�");
		cell = row.createCell(22);
		cell.setCellValue("���ż�(����)");
		cell = row.createCell(23);
		cell.setCellValue("�ɼ��ǸŰ�(�ǸŴܰ�)");
		cell = row.createCell(24);
		cell.setCellValue("������");
		cell = row.createCell(25);
		cell.setCellValue("�������̸���");
		cell = row.createCell(26);
		cell.setCellValue("��������ȭ��ȣ");
		cell = row.createCell(27);
		cell.setCellValue("�������̸�");
		cell = row.createCell(28);
		cell.setCellValue("��������ȭ��ȣ");
		cell = row.createCell(29);
		cell.setCellValue("�����ȣ");
		cell = row.createCell(30);
		cell.setCellValue("������ �ּ�");
		cell = row.createCell(31);
		cell.setCellValue("��۸޼���");
		cell = row.createCell(32);
		cell.setCellValue("��ǰ�� �߰��޽���");
		cell = row.createCell(33);
		cell.setCellValue("�ֹ��� �߰��޽���");
		cell = row.createCell(34);
		cell.setCellValue("��ۿϷ���");
		cell = row.createCell(35);
		cell.setCellValue("����Ȯ������");
		cell = row.createCell(36);
		cell.setCellValue("���������ȣ(PCCC)");
		cell = row.createCell(37);
		cell.setCellValue("����뱸������ȭ��ȣ");
		cell = row.createCell(38);
		cell.setCellValue("��Ÿ");
		cell = row.createCell(39);
		cell.setCellValue("��ǰ�� ������ġ");
		
		for (int i = 1; i <= coupangLs.size(); i++) {
			CoupangColumnDto coupangData = coupangLs.get(i - 1);
			row = sheet.createRow(i);
			
			cell = row.createCell(0);
			cell.setCellValue(coupangData.getNum());
			cell = row.createCell(1);
			cell.setCellValue(coupangData.getShippingNum());
			cell = row.createCell(2);
			cell.setCellValue(coupangData.getOrderNum());
			cell = row.createCell(3);
			cell.setCellValue(COURIER);
			cell = row.createCell(4);
			cell.setCellValue(coupangData.getWaybillNum());
			cell = row.createCell(5);
			cell.setCellValue(coupangData.getSeparateDelivery());
			cell = row.createCell(6);
			cell.setCellValue(coupangData.getSeparateExpectedDeliveryDate());
			cell = row.createCell(7);
			cell.setCellValue(coupangData.getExpectedDeliveryDate());
			cell = row.createCell(8);
			cell.setCellValue(coupangData.getDeliveryDate());
			cell = row.createCell(9);
			cell.setCellValue(coupangData.getOrderDate());
			cell = row.createCell(10);
			cell.setCellValue(coupangData.getProductName());
			cell = row.createCell(11);
			cell.setCellValue(coupangData.getOptionName());
			cell = row.createCell(12);
			cell.setCellValue(coupangData.getDisplayedProductName());
			cell = row.createCell(13);
			cell.setCellValue(coupangData.getDisplayedProductId());
			cell = row.createCell(14);
			cell.setCellValue(coupangData.getOptionId());
			cell = row.createCell(15);
			cell.setCellValue(coupangData.getFirstOptionName());
			cell = row.createCell(16);
			cell.setCellValue(coupangData.getProductCode());
			cell = row.createCell(17);
			cell.setCellValue(coupangData.getBarcode());
			cell = row.createCell(18);
			cell.setCellValue(coupangData.getPayment());
			cell = row.createCell(19);
			cell.setCellValue(coupangData.getDeliveryFeeFlag());
			cell = row.createCell(20);
			cell.setCellValue(coupangData.getDeliveryFee());
			cell = row.createCell(21);
			cell.setCellValue(coupangData.getAdditionalDeliveryFee());
			cell = row.createCell(22);
			cell.setCellValue(coupangData.getQuantity());
			cell = row.createCell(23);
			cell.setCellValue(coupangData.getUnitPrice());
			cell = row.createCell(24);
			cell.setCellValue(coupangData.getCustomerName());
			cell = row.createCell(25);
			cell.setCellValue(coupangData.getCustomerEmail());
			cell = row.createCell(26);
			cell.setCellValue(coupangData.getCustomerPhone());
			cell = row.createCell(27);
			cell.setCellValue(coupangData.getReceiverName());
			cell = row.createCell(28);
			cell.setCellValue(coupangData.getReceiverPhone());
			cell = row.createCell(29);
			cell.setCellValue(coupangData.getPostNum());
			cell = row.createCell(30);
			cell.setCellValue(coupangData.getReceiverAddress());
			cell = row.createCell(31);
			cell.setCellValue(coupangData.getDeliveryMessage());
			cell = row.createCell(32);
			cell.setCellValue(coupangData.getAdditionalMessagePerItem());
			cell = row.createCell(33);
			cell.setCellValue(coupangData.getOrdererAdditionalMessage());
			cell = row.createCell(34);
			cell.setCellValue(coupangData.getDeliveryCompleteDate());
			cell = row.createCell(35);
			cell.setCellValue(coupangData.getConfirmationPurchaseDate());
			cell = row.createCell(36);
			cell.setCellValue(coupangData.getPccc());
			cell = row.createCell(37);
			cell.setCellValue(coupangData.getBuyerPhoneNumForCustomsClearance());
			cell = row.createCell(38);
			cell.setCellValue(coupangData.getEtc());
			cell = row.createCell(39);
			cell.setCellValue(coupangData.getPaymentLocation());
		}
		
		// ��� ���ϸ� �䱸���� �ʿ�
		File xlsxFile = new File(directoryURI + LocalDate.now() + " ���� ����� ���ε�.xlsx");
		FileOutputStream fileOut = new FileOutputStream(xlsxFile);
		xlsxWb.write(fileOut);	
		fileOut.close();
//		throw new TransformationComplete();
	}
	
	public static void outputNaver(String directoryURI, List<NaverColumnDto> naverLs) throws IOException, TransformationComplete{
		XSSFWorkbook xlsxWb = new XSSFWorkbook();
		
		// sheet ����
		XSSFSheet sheet = xlsxWb.createSheet("��۰���");
		
		// ��Ÿ��
		CellStyle menu = xlsxWb.createCellStyle();
		CellStyle defaultStyle = xlsxWb.createCellStyle();
		
		// �ٹٲ�
		menu.setWrapText(true);
		defaultStyle.setWrapText(true);
		
		// �޴�
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell = row.createCell(0);
		cell.setCellValue("��ǰ�ֹ���ȣ");		
		cell = row.createCell(1);
		cell.setCellValue("�ֹ���ȣ");
		cell = row.createCell(2);
		cell.setCellValue("��۹��");
		cell = row.createCell(3);
		cell.setCellValue("�ù��");
		cell = row.createCell(4);
		cell.setCellValue("�����ȣ");
		cell = row.createCell(5);
		cell.setCellValue("�߼���");
		cell = row.createCell(6);
		cell.setCellValue("�����θ�");
		cell = row.createCell(7);
		cell.setCellValue("��ǰ��");
		cell = row.createCell(8);
		cell.setCellValue("�ɼ�����");
		cell = row.createCell(9);
		cell.setCellValue("����");
		cell = row.createCell(10);
		cell.setCellValue("��ۺ� ����");
		cell = row.createCell(11);
		cell.setCellValue("�����ο���ó1");
		cell = row.createCell(12);
		cell.setCellValue("�����");
		cell = row.createCell(13);
		cell.setCellValue("��۸޼���");
		cell = row.createCell(14);
		cell.setCellValue("�����");
		cell = row.createCell(15);
		cell.setCellValue("��������");
		cell = row.createCell(16);
		cell.setCellValue("������ ���ݱ���");
		cell = row.createCell(17);
		cell.setCellValue("������������");
		cell = row.createCell(18);
		cell.setCellValue("����������");
		cell = row.createCell(19);
		cell.setCellValue("���⿬�� ������");
		cell = row.createCell(20);
		cell.setCellValue("���꿹���ݾ�");
		cell = row.createCell(21);
		cell.setCellValue("���԰��");
		cell = row.createCell(22);
		cell.setCellValue("������ �ֹε�Ϲ�ȣ");
		cell = row.createCell(23);
		cell.setCellValue("�������������ȣ");
		cell = row.createCell(24);
		cell.setCellValue("�ֹ��Ͻ�");
		cell = row.createCell(25);
		cell.setCellValue("1�� �ֹ��Ǽ�");
		cell = row.createCell(26);
		cell.setCellValue("������ID");
		cell = row.createCell(27);
		cell.setCellValue("�����ڸ�");
		cell = row.createCell(28);
		cell.setCellValue("������");
		cell = row.createCell(29);
		cell.setCellValue("��ǰ����");
		cell = row.createCell(30);
		cell.setCellValue("�ֹ����λ���");
		cell = row.createCell(31);
		cell.setCellValue("�ֹ�����");
		cell = row.createCell(32);
		cell.setCellValue("��ǰ��ȣ");
		cell = row.createCell(33);
		cell.setCellValue("��ۼӼ�");
		cell = row.createCell(34);
		cell.setCellValue("��������");
		cell = row.createCell(35);
		cell.setCellValue("(�����ο���ó1)");
		cell = row.createCell(36);
		cell.setCellValue("(�����ο���ó2)");
		cell = row.createCell(37);
		cell.setCellValue("(�����ȣ)");
		cell = row.createCell(38);
		cell.setCellValue("(�⺻�ּ�)");
		cell = row.createCell(39);
		cell.setCellValue("(���ּ�)");
		cell = row.createCell(40);
		cell.setCellValue("(�����ڿ���ó)");
		
		for (int i = 1; i <= naverLs.size(); i++) {
			NaverColumnDto naverData = naverLs.get(i - 1);
			row = sheet.createRow(i);
			
			cell = row.createCell(0);
			cell.setCellValue(naverData.getProductOrderNum());
			cell = row.createCell(1);
			cell.setCellValue(naverData.getOrderNum());
			cell = row.createCell(2);
			cell.setCellValue(naverData.getShippingMethod());
			cell = row.createCell(3);
			cell.setCellValue(COURIER);
			cell = row.createCell(4);
			cell.setCellValue(naverData.getWaybillNum());
			cell = row.createCell(5);
			cell.setCellValue(naverData.getShippingDate());
			cell = row.createCell(6);
			cell.setCellValue(naverData.getReceiverName());
			cell = row.createCell(7);
			cell.setCellValue(naverData.getProductName());
			cell = row.createCell(8);
			cell.setCellValue(naverData.getOptionInfo());
			cell = row.createCell(9);
			cell.setCellValue(naverData.getQuantity());
			cell = row.createCell(10);
			cell.setCellValue(naverData.getShippingCostForm());
			cell = row.createCell(11);
			cell.setCellValue(naverData.getReceiverPhone1());
			cell = row.createCell(12);
			cell.setCellValue(naverData.getReceiverAddress());
			cell = row.createCell(13);
			cell.setCellValue(naverData.getDeliveryMessage());
			cell = row.createCell(14);
			cell.setCellValue(naverData.getShipFrom());
			cell = row.createCell(15);
			cell.setCellValue(naverData.getMethodOfPayment());
			cell = row.createCell(16);
			cell.setCellValue(naverData.getFeeChargingCategory());
			cell = row.createCell(17);
			cell.setCellValue(naverData.getFeePaymentMethod());
			cell = row.createCell(18);
			if (naverData.getPaymentFee() == null) cell.setCellValue("");
			else cell.setCellValue(naverData.getPaymentFee());
			cell = row.createCell(19);
			if (naverData.getPaymentFee() == null) cell.setCellValue("");
			else cell.setCellValue(naverData.getSalesLinkageFee());
			cell = row.createCell(20);
			if (naverData.getPaymentFee() == null) cell.setCellValue("");
			else cell.setCellValue(naverData.getEstimatedTotalAmount());
			cell = row.createCell(21);
			cell.setCellValue(naverData.getChannel());
			cell = row.createCell(22);
			cell.setCellValue(naverData.getBuyerSSN());
			cell = row.createCell(23);
			cell.setCellValue(naverData.getPccc());
			cell = row.createCell(24);
			cell.setCellValue(naverData.getOrderDateTime());
			cell = row.createCell(25);
			cell.setCellValue(naverData.getNumOfOrdersPerYear());
			cell = row.createCell(26);
			cell.setCellValue(naverData.getBuyerId());
			cell = row.createCell(27);
			cell.setCellValue(naverData.getBuyerName());
			cell = row.createCell(28);
			cell.setCellValue(naverData.getPaymentDate());
			cell = row.createCell(29);
			cell.setCellValue(naverData.getProductType());
			cell = row.createCell(30);
			cell.setCellValue(naverData.getOrderDetailStatus());
			cell = row.createCell(31);
			cell.setCellValue(naverData.getOrderStatus());
			cell = row.createCell(32);
			cell.setCellValue(naverData.getItemNum());
			cell = row.createCell(33);
			cell.setCellValue(naverData.getDeliveryProperty());
			cell = row.createCell(34);
			cell.setCellValue(naverData.getWantDeliveryDate());
			cell = row.createCell(35);
			cell.setCellValue(naverData.get_receiverPhone1());
			cell = row.createCell(36);
			cell.setCellValue(naverData.get_receiverPhone2());
			cell = row.createCell(37);
			cell.setCellValue(naverData.getZipcode());
			cell = row.createCell(38);
			cell.setCellValue(naverData.getReceiverAddress1());
			cell = row.createCell(39);
			cell.setCellValue(naverData.getReceiverAddress2());
			cell = row.createCell(40);
			cell.setCellValue(naverData.getBuyerPhone());
		}
		
		// ��� ���ϸ� �䱸���� �ʿ�
		File xlsxFile = new File(directoryURI + LocalDate.now() + " ���̹� ����� ���ε�.xlsx");
		FileOutputStream fileOut = new FileOutputStream(xlsxFile);
		xlsxWb.write(fileOut);	
		fileOut.close();	
	}
	
	
	// �׽�Ʈ �޼ҵ�
	public static void coupangPrint(String fileURI) throws IOException {
		List<CoupangColumnDto> ls = readCoupangForBillway(fileURI);
		for (CoupangColumnDto data : ls) System.out.println(data.toString());
	}
	
	public static void naverPrint(String fileURI) throws IOException, TransformationComplete {
		HashMap<String, NaverColumnDto> testMap = new HashMap<>();
		List<NaverColumnDto> ls = readNaverForBillway(fileURI);
		for (NaverColumnDto data : ls) System.out.println(data.toString());
	}
	
	public static void coupangToCnpPrint(String fileURI) throws IOException {
		List<CnpInputDto> cnpInputLs = readCoupang(fileURI);
		int idx = 1;
		for (CnpInputDto cnpInput : cnpInputLs) {
			System.out.println((idx++) + " " + cnpInput.toString());
		}
	}
	
}
