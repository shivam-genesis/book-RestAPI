package com.bookrestAPI.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bookrestAPI.entity.Author;

public class AuthorsExcelExporter {
	public AuthorsExcelExporter(List<Author> authors) {
		this.authors = authors;
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Books");
	}

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	List<Author> authors;

	private void writeHeaderRow() {
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue("ID");

		cell = row.createCell(1);
		cell.setCellValue("FirstName");

		cell = row.createCell(2);
		cell.setCellValue("LastName");

		cell = row.createCell(3);
		cell.setCellValue("E-Mail");
	}

	private void writeDataRow() {
		int rowCount = 1;
		for (Author author : authors) {
			Row row = sheet.createRow(rowCount++);

			Cell cell = row.createCell(0);
			cell.setCellValue(author.getId());

			cell = row.createCell(1);
			cell.setCellValue(author.getFirstName());

			cell = row.createCell(2);
			cell.setCellValue(author.getLastName());

			cell = row.createCell(3);
			cell.setCellValue(author.getEmail());
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
