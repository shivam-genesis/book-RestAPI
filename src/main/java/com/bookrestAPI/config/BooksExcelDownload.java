package com.bookrestAPI.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bookrestAPI.entity.Book;

public class BooksExcelDownload {
	public BooksExcelDownload(List<Book> books) {
		this.books = books;
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Books");
	}

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<Book> books;

	private void writeHeaderRow() {
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue("BookID");

		cell = row.createCell(1);
		cell.setCellValue("Book Title");

		cell = row.createCell(2);
		cell.setCellValue("Book Author");
	}

	private void writeDataRow() {
		int rowCount = 1;
		for (Book book : books) {
			Row row = sheet.createRow(rowCount++);

			Cell cell = row.createCell(0);
			cell.setCellValue(book.getId());

			cell = row.createCell(1);
			cell.setCellValue(book.getTitle());

			cell = row.createCell(2);
			cell.setCellValue(book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName());
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
