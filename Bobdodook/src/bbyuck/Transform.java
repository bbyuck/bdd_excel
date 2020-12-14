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
	// 쿠팡 제목 바꾸는 엑셀파일
	private static final String COUPANG_DICT_URI = "./coupangDict.xlsx";
	private static final String NAVER_DICT_URI = "./naverDict.xlsc";
	private static final String TEST_COUPANG = "C:\\Users\\k9410\\OneDrive\\바탕 화면\\밥도둑세상 프로젝트\\상품명.xlsx";
	private static final Integer COUPANG_OPTION_START_IDX = 10;
	private static final String COURIER = "CJ대한통운";

	public static void init() throws IOException {
		coupangDict = new HashMap<>();
		coupangCount = new HashMap<>();
		readCoupangProducts();
		
	}
	
	private static void readCoupangProducts() throws IOException{
		FileInputStream fi = new FileInputStream(COUPANG_DICT_URI);
//		FileInputStream fi = new FileInputStream(TEST_COUPANG);
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(fi);
		XSSFSheet sheet = workbook.getSheetAt(0); // 해당 엑셀파일의 시트수
		int rows = sheet.getPhysicalNumberOfRows(); // 해당 시트의 행의 개수
		
		// 파싱
		for (int rowIdx = 1; rowIdx < rows; rowIdx++) {
			XSSFRow row = sheet.getRow(rowIdx); // 각 행을 읽어온다

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
	
	
	// 추가로 제품 이름 및 수량별 카운트해서 추가적인 엑셀파일로 출력하는 메소드 만들 것
	public static List<CnpInputDto> readCoupang(String fileURI) throws IOException {
		List<CnpInputDto> answer = new ArrayList<>();
		List<CoupangColumnDto> coupangLs = new ArrayList<>();
		
		FileInputStream fi = new FileInputStream(fileURI);
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(fi);
		XSSFSheet sheet = workbook.getSheetAt(0); // 해당 엑셀파일의 시트수
		int rows = sheet.getPhysicalNumberOfRows(); // 해당 시트의 행의 개수
//		System.out.println(rows);
		
		// 파싱
		for (int rowIdx = 1; rowIdx < rows; rowIdx++) {
			XSSFRow row = sheet.getRow(rowIdx); // 각 행을 읽어온다
			CoupangColumnDto coupangData = new CoupangColumnDto();
			CnpInputDto cnpInput = new CnpInputDto();
			
			if (row != null) {
				int cells = row.getPhysicalNumberOfCells();
				
				// 셀에 담겨있는 값을 읽는다.
				// 번호
				Integer thisNum = Integer.parseInt(row.getCell(0).getStringCellValue());
				
				// 묶음 배송
				if (thisNum.equals(coupangLs.size())) {
					// 주문 내용 변경하는 로직(아마 기존 ls에서 get해서 그 object 꺼내와서 거기에 concat 하는 방식)
					coupangData = coupangLs.get(thisNum - 1);
					String displayedProductName = coupangData.getDisplayedProductName();
					
					String thisRowDisplayedProductName = row.getCell(12).getStringCellValue();
					Integer quantity = Integer.parseInt(row.getCell(22).getStringCellValue());

					// 선택사항
					if (thisRowDisplayedProductName.contains("선택사항")) {
						String[] options = thisRowDisplayedProductName.split(",");
						String option = options[options.length - 1];
						String ans = option.substring(COUPANG_OPTION_START_IDX, option.length() - 2);
						if (quantity == 1) coupangData.setDisplayedProductName(displayedProductName + " // " + ans);
						else coupangData.setDisplayedProductName(displayedProductName + " // " + ans + " (" + quantity + "개)");
						
						for (Entry<String, String> entry : coupangDict.entrySet()) {
							if (thisRowDisplayedProductName.contains(entry.getKey())) {
								coupangCount.replace(entry.getValue(), coupangCount.get(entry.getValue()) + 1);
								break;
							}
						}
						
					}
					else {
						thisRowDisplayedProductName = coupangDict.get(thisRowDisplayedProductName);
						if (quantity == 1) coupangData.setDisplayedProductName(displayedProductName + " // " + thisRowDisplayedProductName);
						else coupangData.setDisplayedProductName(displayedProductName + " // " + thisRowDisplayedProductName + " (" + quantity + "개)");
						coupangCount.replace(thisRowDisplayedProductName, coupangCount.get(thisRowDisplayedProductName) + 1);
					}
					
					continue;
				}
				coupangData.setNum(thisNum);
				
				// 구매수
				int quantity = Integer.parseInt(row.getCell(22).getStringCellValue());
				coupangData.setQuantity(quantity);
				
				// 수취인이름
				coupangData.setReceiverName(row.getCell(27).getStringCellValue());
				
				// 수취인전화번호
				coupangData.setReceiverPhone(processPhone(row.getCell(28).getStringCellValue()));
				
				// 수취인  주소
				coupangData.setReceiverAddress(row.getCell(30).getStringCellValue());
				
				// 노출상품명(옵션명)
				String productName = row.getCell(12).getStringCellValue();
				String val = coupangDict.get(productName);
				String ans = "쿠 - ";
				// 선택사항
				if (productName.contains("선택사항")) {
					String[] options = productName.split(",");
					String option = options[options.length - 1];
					ans += option.substring(COUPANG_OPTION_START_IDX, option.length() - 2);
					
					for (Entry<String, String> entry : coupangDict.entrySet()) {
						if (productName.contains(entry.getKey())) {
							coupangCount.replace(entry.getValue(), coupangCount.get(entry.getValue()) + 1);
							break;
						}
					}
				}
				else {
					productName = coupangDict.get(productName);
					coupangCount.replace(productName, coupangCount.get(productName) + 1);
					ans += productName;
				}
				
				if (quantity == 1) coupangData.setDisplayedProductName(ans);
				else coupangData.setDisplayedProductName(ans + " (" + quantity + "개)");
				// 배송메세지
				coupangData.setDeliveryMessage(row.getCell(31).getStringCellValue());
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
		XSSFSheet sheet = workbook.getSheetAt(0); // 해당 엑셀파일의 시트수
		int rows = sheet.getPhysicalNumberOfRows(); // 해당 시트의 행의 개수
//		System.out.println(rows);
		
		// 파싱
		for (int rowIdx = 1; rowIdx < rows; rowIdx++) {
			XSSFRow row = sheet.getRow(rowIdx); // 각 행을 읽어온다
			NaverColumnDto naverData = new NaverColumnDto();
			CnpInputDto cnpInput = new CnpInputDto();
			
			if (row != null) {				
				// 셀에 담겨있는 값을 읽는다.
				// 수취인명
				String receiverName = row.getCell(6).getStringCellValue();
				// 수취인 연락처
				String receiverPhone = processPhone(row.getCell(11).getStringCellValue());
				String orderNum = row.getCell(1).getStringCellValue();

				NaverColumnDto chkDto = map.get(orderNum);
				
				if (chkDto != null) {
					// 묶음 배송
					naverData = chkDto;
					String displayedProductName = naverData.getProductName();
					String thisRowDisplayedProductName = row.getCell(7).getStringCellValue() + " " + row.getCell(8).getStringCellValue();
					Integer quantity = (int)row.getCell(9).getNumericCellValue();
					
					if (quantity == 1) naverData.setProductName(displayedProductName + " // " + thisRowDisplayedProductName);
					else naverData.setProductName(displayedProductName + " // " + thisRowDisplayedProductName + " (" + quantity + "개)");
					
					continue;
				}

				// 수취인이름
				naverData.setReceiverName(receiverName);
				
				// 수취인연락처1
				naverData.setReceiverPhone1(receiverPhone);
				
				// 구매수
				int quantity = (int)row.getCell(9).getNumericCellValue();
				naverData.setQuantity(quantity);
				
				// 수취인  주소
				naverData.setReceiverAddress(row.getCell(12).getStringCellValue());
				
				// 상품명
				if (quantity == 1) naverData.setProductName(row.getCell(7).getStringCellValue() + " " + row.getCell(8).getStringCellValue());
				else naverData.setProductName(row.getCell(7).getStringCellValue() + " " + row.getCell(8).getStringCellValue() + " (" + quantity + "개)");
				
				// 배송메세지
				String deliveryMessage = "";
				if (row.getCell(13) != null) deliveryMessage = row.getCell(13).getStringCellValue();
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
		
		// sheet 생성
		HSSFSheet sheet = xlsWb.createSheet("배송관리");
		
		// 스타일
		CellStyle menu = xlsWb.createCellStyle();
		CellStyle defaultStyle = xlsWb.createCellStyle();
		
		
		// 줄바꿈
		menu.setWrapText(true);
		defaultStyle.setWrapText(true);
		
		// 메뉴
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		
		cell.setCellValue("수취인이름");
		
		cell = row.createCell(1);
		cell.setCellValue("수취인전화번호");
		cell = row.createCell(2);
		cell.setCellValue("수취인 주소");
		cell = row.createCell(3);
		cell.setCellValue("노출상품명(옵션명)");
		cell = row.createCell(4);
		cell.setCellValue("비고");
		
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
		
		// 출력 파일명 요구사항 필요
		File xlsFile = new File(directoryURI  + LocalDate.now() + " coupangToCnpOutput.xls");
		FileOutputStream fileOut = new FileOutputStream(xlsFile);
		xlsWb.write(fileOut);	
		fileOut.close();
		
		throw new TransformationComplete();
	}
	
	public static void coupangCount(String directoryURI, List<Pair> coupangCountLs) throws IOException{
		XSSFWorkbook xlsxWb = new XSSFWorkbook();
		
		// sheet 생성
		XSSFSheet sheet = xlsxWb.createSheet(LocalDate.now() + "쿠팡 판매량");
		
		// 스타일
		CellStyle menu = xlsxWb.createCellStyle();
		CellStyle defaultStyle = xlsxWb.createCellStyle();
		
		// 줄바꿈
		menu.setWrapText(true);
		defaultStyle.setWrapText(true);
		
		// 메뉴
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell = row.createCell(0);
		cell.setCellValue("판매 물품 이름");
		row.createCell(1);
		cell.setCellValue("판매량");

		for (int i = 1; i <= coupangCountLs.size(); i++) {
			Pair pair = coupangCountLs.get(i - 1);
			row = sheet.createRow(i);
			
			cell = row.createCell(0);
			cell.setCellValue(pair.productName);
			cell = row.createCell(1);
			cell.setCellValue(pair.count);
		}
		
		// 출력 파일명 요구사항 필요
		File xlsxFile = new File(directoryURI + LocalDate.now() + " coupangCount.xlsx");
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
		
		answer.add(new Pair("합계", total));
		
		return answer;
	}
	
	// naver -> CNP
	public static void naverToCnp(String directoryURI, String fileName) throws IOException, FileNotFoundException, TransformationComplete {
		// cnp input list
		List<CnpInputDto> cnpInputLs = readNaver(directoryURI + fileName);
		final int columnSize = 5;
		
		HSSFWorkbook xlsWb = new HSSFWorkbook();
		
		// sheet 생성
		HSSFSheet sheet = xlsWb.createSheet("배송관리");
		
		// 스타일
		CellStyle menu = xlsWb.createCellStyle();
		CellStyle defaultStyle = xlsWb.createCellStyle();
		
		// 줄바꿈
		menu.setWrapText(true);
		defaultStyle.setWrapText(true);
		
		// 메뉴
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("수취인이름");
		
		cell = row.createCell(1);
		cell.setCellValue("수취인전화번호");
		cell = row.createCell(2);
		cell.setCellValue("수취인 주소");
		cell = row.createCell(3);
		cell.setCellValue("노출상품명(옵션명)");
		cell = row.createCell(4);
		cell.setCellValue("비고");
		
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
		
		// 출력 파일명 요구사항 필요
		File xlsFile = new File(directoryURI  + LocalDate.now() + " naverToCnpOutput.xls");
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
		XSSFSheet sheet = workbook.getSheetAt(0); // 해당 엑셀파일의 시트수
		int rows = sheet.getPhysicalNumberOfRows(); // 해당 시트의 행의 개수
//		System.out.println(rows);
		
		// 파싱
		for (int rowIdx = 1; rowIdx < rows; rowIdx++) {
			XSSFRow row = sheet.getRow(rowIdx); // 각 행을 읽어온다
			CoupangColumnDto coupangData = new CoupangColumnDto();
			
			if (row != null) {
				int cells = row.getPhysicalNumberOfCells();
				
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
				
				if (row.getCell(0) != null)	thisNum = Integer.parseInt(row.getCell(0).getStringCellValue());
				else throw new IOException();
				if (row.getCell(1) != null) shippingNum = row.getCell(1).getStringCellValue();
				else throw new IOException();
				if (row.getCell(2) != null) orderNum = row.getCell(2).getStringCellValue();
				else throw new IOException();
				
				if (row.getCell(3) != null) courier = row.getCell(3).getStringCellValue();
				if (row.getCell(4) != null) waybillNum = row.getCell(4).getStringCellValue();
				if (row.getCell(5) != null) separateDelivery = row.getCell(5).getStringCellValue();				
				if (row.getCell(6) != null) separateExpectedDeliveryDate = row.getCell(6).getStringCellValue();
				if (row.getCell(7) != null) expectedDeliveryDate = row.getCell(7).getStringCellValue();
				if (row.getCell(8) != null) deliveryDate = row.getCell(8).getStringCellValue();				
				if (row.getCell(9) != null) orderDate = row.getCell(9).getStringCellValue();
				if (row.getCell(10) != null) productName = row.getCell(10).getStringCellValue();				
				if (row.getCell(11) != null) optionName = row.getCell(11).getStringCellValue();				
				if (row.getCell(12) != null) displayedProductName = row.getCell(12).getStringCellValue();
				if (row.getCell(13) != null) displayedProductId = row.getCell(13).getStringCellValue();
				if (row.getCell(14) != null) optionId = row.getCell(14).getStringCellValue();
				if (row.getCell(15) != null) firstOptionName = row.getCell(15).getStringCellValue();
				if (row.getCell(16) != null) productCode = row.getCell(16).getStringCellValue();
				if (row.getCell(17) != null) barcode = row.getCell(17).getStringCellValue();
				
				if (row.getCell(18) != null) payment = Integer.parseInt(row.getCell(18).getStringCellValue());
				else throw new IOException();
				if (row.getCell(19) != null) deliveryFeeFlag = row.getCell(19).getStringCellValue();
				else throw new IOException();
				if (row.getCell(20) != null) deliveryFee = Integer.parseInt(row.getCell(20).getStringCellValue());
				else throw new IOException();
				if (row.getCell(21) != null) additionalDeliveryFee = Integer.parseInt(row.getCell(21).getStringCellValue());
				else throw new IOException();
				if (row.getCell(22) != null) quantity = Integer.parseInt(row.getCell(22).getStringCellValue());
				else throw new IOException();				
				if (row.getCell(23) != null) unitPrice = Integer.parseInt(row.getCell(23).getStringCellValue());
				else throw new IOException();
				
				if (row.getCell(24) != null) customerName = row.getCell(24).getStringCellValue();				
				if (row.getCell(25) != null) customerEmail = row.getCell(25).getStringCellValue();
				if (row.getCell(26) != null) customerPhone = row.getCell(26).getStringCellValue();
				else throw new IOException();
				
				if (row.getCell(27) != null) receiverName = row.getCell(27).getStringCellValue();
				else throw new IOException();
				
				if (row.getCell(28) != null) receiverPhone = row.getCell(28).getStringCellValue();

				if (row.getCell(29) != null) postNum = row.getCell(29).getStringCellValue();
				else throw new IOException();

				if (row.getCell(30) != null) receiverAddress = row.getCell(30).getStringCellValue();
				if (row.getCell(31) != null) deliveryMessage = row.getCell(31).getStringCellValue();
				if (row.getCell(32) != null) additionalMessagePerItem = row.getCell(32).getStringCellValue();
				if (row.getCell(33) != null) ordererAdditionalMessage = row.getCell(33).getStringCellValue();
				if (row.getCell(34) != null) deliveryCompleteDate = row.getCell(34).getStringCellValue();
				if (row.getCell(35) != null) confirmationPurchaseDate = row.getCell(35).getStringCellValue();
				if (row.getCell(36) != null) pccc = row.getCell(36).getStringCellValue();
				if (row.getCell(37) != null) buyerPhoneNumForCustomsClearance = row.getCell(37).getStringCellValue();
				if (row.getCell(38) != null) etc = row.getCell(38).getStringCellValue();
				if (row.getCell(39) != null) paymentLocation = row.getCell(39).getStringCellValue();
				
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
				else coupangData.setDisplayedProductName(displayedProductName + " (" + quantity + "개)");
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
		XSSFSheet sheet = workbook.getSheetAt(0); // 해당 엑셀파일의 시트수
		int rows = sheet.getPhysicalNumberOfRows(); // 해당 시트의 행의 개수
//		System.out.println(rows);
		
		// 파싱
		for (int rowIdx = 1; rowIdx < rows; rowIdx++) {
			XSSFRow row = sheet.getRow(rowIdx); // 각 행을 읽어온다
			NaverColumnDto naverData = new NaverColumnDto();
			
			if (row != null) {
				int cells = row.getPhysicalNumberOfCells();
				
				String productOrderNum = "";
				// 주문번호
				String orderNum = "";
				// 배송방법
				String shippingMethod = "";
				// 택배사
				String courier = "";
				// 송장번호
				String waybillNum = "";
				// 발송일
				String shippingDate = "";
				// 수취인명
				String receiverName = "";
				// 상품명
				String productName = "";
				// 옵션정보
				String optionInfo = "";
				// 수량
				Integer quantity = null;
				// 배송비 형태
				String shippingCostForm = "";
				// 수취인연락처1
				String receiverPhone1 = "";
				// 배송지
				String receiverAddress = "";
				// 배송메세지
				String deliveryMessage = "";
				// 출고지
				String shipFrom = "";
				// 결제수단
				String methodOfPayment = "";
				// 수수료 과금구분
				String feeChargingCategory = "";
				// 수수료결제방식
				String feePaymentMethod = "";
				// 결제수수료
				Integer paymentFee = null;
				// 매출연동 수수료
				Integer salesLinkageFee = null;
				// 정산예정금액
				Integer estimatedTotalAmount = null;
				// 유입경로
				String channel = "";
				// 구매자 주민등록번호
				String buyerSSN = "";
				// 개인통관고유부호
				String pccc = "";
				// 주문일시
				String orderDateTime = "";
				// 1년 주문건수
				Integer numOfOrdersPerYear = null;
				// 구매자ID
				String buyerId = "";
				// 구매자명
				String buyerName = "";
				// 결제일
				String paymentDate = "";
				// 상품종류
				String productType = "";
				// 주문세부상태
				String orderDetailStatus = "";
				// 주문상태
				String orderStatus = "";
				// 상품번호
				String itemNum = "";
				// 배송속성
				String deliveryProperty = "";
				// 배송희망일
				String wantDeliveryDate = "";
				// (수취인연락처1)
				String _receiverPhone1 = "";
				// (수취인연락처2)
				String _receiverPhone2 = "";
				// (우편번호)
				String zipcode = "";
				// (기본주소)
				String receiverAddress1 = "";
				// (상세주소)
				String receiverAddress2 = "";
				// (구매자연락처)
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
		HSSFSheet sheet = workbook.getSheetAt(0); // 해당 엑셀파일의 시트수
		int rows = sheet.getPhysicalNumberOfRows(); // 해당 시트의 행의 개수
//		System.out.println(rows);
		
		// 파싱
		for (int rowIdx = 1; rowIdx < rows; rowIdx++) {
			HSSFRow row = sheet.getRow(rowIdx); // 각 행을 읽어온다
			CnpOutputDto cnpOutput = new CnpOutputDto();
			
			
			if (row != null) {				
				// 셀에 담겨있는 값을 읽는다.
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
		
		// 각각 쿠팡, 네이버 출력
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
		
		// sheet 생성
		XSSFSheet sheet = xlsxWb.createSheet("배송관리");
		
		// 스타일
		CellStyle menu = xlsxWb.createCellStyle();
		CellStyle defaultStyle = xlsxWb.createCellStyle();
		
		// 줄바꿈
		menu.setWrapText(true);
		defaultStyle.setWrapText(true);
		
		// 메뉴
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell = row.createCell(0);
		cell.setCellValue("번호");		
		cell = row.createCell(1);
		cell.setCellValue("묶음배송번호");
		cell = row.createCell(2);
		cell.setCellValue("주문번호");
		cell = row.createCell(3);
		cell.setCellValue("택배사");
		cell = row.createCell(4);
		cell.setCellValue("운송장번호");
		cell = row.createCell(5);
		cell.setCellValue("분리배송 Y/N");
		cell = row.createCell(6);
		cell.setCellValue("분리배송 출고예정일");
		cell = row.createCell(7);
		cell.setCellValue("주문시 출고예정일");
		cell = row.createCell(8);
		cell.setCellValue("출고일(발송일)");
		cell = row.createCell(9);
		cell.setCellValue("주문일");
		cell = row.createCell(10);
		cell.setCellValue("등록상품명");
		cell = row.createCell(11);
		cell.setCellValue("등록옵션명");
		cell = row.createCell(12);
		cell.setCellValue("노출상품명");
		cell = row.createCell(13);
		cell.setCellValue("노출상품ID");
		cell = row.createCell(14);
		cell.setCellValue("옵션ID");
		cell = row.createCell(15);
		cell.setCellValue("최초등록옵션명");
		cell = row.createCell(16);
		cell.setCellValue("업체상품코드");
		cell = row.createCell(17);
		cell.setCellValue("바코드");
		cell = row.createCell(18);
		cell.setCellValue("결제액");
		cell = row.createCell(19);
		cell.setCellValue("배송비구분");
		cell = row.createCell(20);
		cell.setCellValue("배송비");
		cell = row.createCell(21);
		cell.setCellValue("도서산간 추가배송비");
		cell = row.createCell(22);
		cell.setCellValue("구매수(수량)");
		cell = row.createCell(23);
		cell.setCellValue("옵션판매가(판매단가)");
		cell = row.createCell(24);
		cell.setCellValue("구매자");
		cell = row.createCell(25);
		cell.setCellValue("구매자이메일");
		cell = row.createCell(26);
		cell.setCellValue("구매자전화번호");
		cell = row.createCell(27);
		cell.setCellValue("수취인이름");
		cell = row.createCell(28);
		cell.setCellValue("수취인전화번호");
		cell = row.createCell(29);
		cell.setCellValue("우편번호");
		cell = row.createCell(30);
		cell.setCellValue("수취인 주소");
		cell = row.createCell(31);
		cell.setCellValue("배송메세지");
		cell = row.createCell(32);
		cell.setCellValue("상품별 추가메시지");
		cell = row.createCell(33);
		cell.setCellValue("주문자 추가메시지");
		cell = row.createCell(34);
		cell.setCellValue("배송완료일");
		cell = row.createCell(35);
		cell.setCellValue("구매확정일자");
		cell = row.createCell(36);
		cell.setCellValue("개인통관번호(PCCC)");
		cell = row.createCell(37);
		cell.setCellValue("통관용구매자전화번호");
		cell = row.createCell(38);
		cell.setCellValue("기타");
		cell = row.createCell(39);
		cell.setCellValue("상품별 결제위치");
		
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
		
		// 출력 파일명 요구사항 필요
		File xlsxFile = new File(directoryURI + LocalDate.now() + " coupangOutput.xlsx");
		FileOutputStream fileOut = new FileOutputStream(xlsxFile);
		xlsxWb.write(fileOut);	
		fileOut.close();
//		throw new TransformationComplete();
	}
	
	public static void outputNaver(String directoryURI, List<NaverColumnDto> naverLs) throws IOException, TransformationComplete{
		XSSFWorkbook xlsxWb = new XSSFWorkbook();
		
		// sheet 생성
		XSSFSheet sheet = xlsxWb.createSheet("배송관리");
		
		// 스타일
		CellStyle menu = xlsxWb.createCellStyle();
		CellStyle defaultStyle = xlsxWb.createCellStyle();
		
		// 줄바꿈
		menu.setWrapText(true);
		defaultStyle.setWrapText(true);
		
		// 메뉴
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell = row.createCell(0);
		cell.setCellValue("상품주문번호");		
		cell = row.createCell(1);
		cell.setCellValue("주문번호");
		cell = row.createCell(2);
		cell.setCellValue("배송방법");
		cell = row.createCell(3);
		cell.setCellValue("택배사");
		cell = row.createCell(4);
		cell.setCellValue("송장번호");
		cell = row.createCell(5);
		cell.setCellValue("발송일");
		cell = row.createCell(6);
		cell.setCellValue("수취인명");
		cell = row.createCell(7);
		cell.setCellValue("상품명");
		cell = row.createCell(8);
		cell.setCellValue("옵션정보");
		cell = row.createCell(9);
		cell.setCellValue("수량");
		cell = row.createCell(10);
		cell.setCellValue("배송비 형태");
		cell = row.createCell(11);
		cell.setCellValue("수취인연락처1");
		cell = row.createCell(12);
		cell.setCellValue("배송지");
		cell = row.createCell(13);
		cell.setCellValue("배송메세지");
		cell = row.createCell(14);
		cell.setCellValue("출고지");
		cell = row.createCell(15);
		cell.setCellValue("결제수단");
		cell = row.createCell(16);
		cell.setCellValue("수수료 과금구분");
		cell = row.createCell(17);
		cell.setCellValue("수수료결제방식");
		cell = row.createCell(18);
		cell.setCellValue("결제수수료");
		cell = row.createCell(19);
		cell.setCellValue("매출연동 수수료");
		cell = row.createCell(20);
		cell.setCellValue("정산예정금액");
		cell = row.createCell(21);
		cell.setCellValue("유입경로");
		cell = row.createCell(22);
		cell.setCellValue("구매자 주민등록번호");
		cell = row.createCell(23);
		cell.setCellValue("개인통관고유부호");
		cell = row.createCell(24);
		cell.setCellValue("주문일시");
		cell = row.createCell(25);
		cell.setCellValue("1년 주문건수");
		cell = row.createCell(26);
		cell.setCellValue("구매자ID");
		cell = row.createCell(27);
		cell.setCellValue("구매자명");
		cell = row.createCell(28);
		cell.setCellValue("결제일");
		cell = row.createCell(29);
		cell.setCellValue("상품종류");
		cell = row.createCell(30);
		cell.setCellValue("주문세부상태");
		cell = row.createCell(31);
		cell.setCellValue("주문상태");
		cell = row.createCell(32);
		cell.setCellValue("상품번호");
		cell = row.createCell(33);
		cell.setCellValue("배송속성");
		cell = row.createCell(34);
		cell.setCellValue("배송희망일");
		cell = row.createCell(35);
		cell.setCellValue("(수취인연락처1)");
		cell = row.createCell(36);
		cell.setCellValue("(수취인연락처2)");
		cell = row.createCell(37);
		cell.setCellValue("(우편번호)");
		cell = row.createCell(38);
		cell.setCellValue("(기본주소)");
		cell = row.createCell(39);
		cell.setCellValue("(상세주소)");
		cell = row.createCell(40);
		cell.setCellValue("(구매자연락처)");
		
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
		
		// 출력 파일명 요구사항 필요
		File xlsxFile = new File(directoryURI + LocalDate.now() + " naverOutput.xlsx");
		FileOutputStream fileOut = new FileOutputStream(xlsxFile);
		xlsxWb.write(fileOut);	
		fileOut.close();	
	}
	
	
	// 테스트 메소드
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
