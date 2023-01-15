package com.bookrestAPI.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bookrestAPI.entity.User;

public class UsersExcelExporter {
	public UsersExcelExporter(List<User> users) {
		this.users = users;
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Books");
	}

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<User> users;

	private void writeHeaderRow() {
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue("UserName");

		cell = row.createCell(1);
		cell.setCellValue("Password");

		cell = row.createCell(2);
		cell.setCellValue("E-Mail");

		cell = row.createCell(3);
		cell.setCellValue("Role");
	}

	private void writeDataRow() {
		int rowCount = 1;
		for (User user : users) {
			Row row = sheet.createRow(rowCount++);

			Cell cell = row.createCell(0);
			cell.setCellValue(user.getUsername());

			cell = row.createCell(1);
			cell.setCellValue(user.getPassword());

			cell = row.createCell(2);
			cell.setCellValue(user.getEmail());

			cell = row.createCell(3);
			cell.setCellValue(user.getRole());
		}
	}

	public void export(HttpServletResponse response) throws IOException {
		writeHeaderRow();
		writeDataRow();
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}
}
