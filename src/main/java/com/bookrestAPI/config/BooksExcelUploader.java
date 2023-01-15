package com.bookrestAPI.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.bookrestAPI.entity.Author;
import com.bookrestAPI.entity.Book;

public class BooksExcelUploader {

	// check that file is of excel type or not
	public static boolean checkExcelFormat(MultipartFile file) {

		String contentType = file.getContentType();

		if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			return true;
		} else {
			return false;
		}

	}

	// convert excel to list of products

	public static List<Book> convertExcelToListOfBooks(InputStream is) {
		List<Book> listBook = new ArrayList<>();
		try {
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			XSSFSheet sheet = workbook.getSheet("Books");
			int rowNumber = 0;
			Iterator<Row> iterator = sheet.iterator();
			
			while (iterator.hasNext()) {
				
				Row row = iterator.next();

				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cells = row.iterator();

				int cid = 0;

				Book b = new Book();
				Author a = new Author();
				while (cells.hasNext()) {
					Cell cell = cells.next();
					switch (cid) {
					case 0:
						b.setId((int) cell.getNumericCellValue());
						System.out.println(b.getId());
						break;
					case 1:
						b.setTitle(cell.getStringCellValue());
						break;
					case 2:
						a.setId((int) cell.getNumericCellValue());
						break;
					case 3:
						a.setFirstName(cell.getStringCellValue());
						break;
					case 4:
						a.setLastName(cell.getStringCellValue());
						break;
					case 5:
						a.setEmail(cell.getStringCellValue());
						break;
					default:
						break;
					}
					cid++;
				}
				b.setAuthor(a);
				listBook.add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listBook;
	}
}
