package com.qltv.QLTV.ExcelExport;

import com.qltv.QLTV.Entity.Books;
import com.qltv.QLTV.Entity.Genres;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookExcelExporter {
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    List<Books> booksList;

    public BookExcelExporter(List<Books> booksList) {
        this.booksList = booksList;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Books");
    }

    void writeHeaderRow(){
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);

        Cell cell = row.createCell(0);
        cell.setCellValue("Book ID");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("Book Title");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("Author");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("Description");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("Genres");
        cell.setCellStyle(style);

    }

    void writeDataRow(){
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        style.setFont(font);

        for(Books book : booksList){
            Row row = sheet.createRow(rowCount++);

            Cell cell = row.createCell(0);
            cell.setCellValue(book.getBookId());
            sheet.autoSizeColumn(0);

            cell = row.createCell(1);
            cell.setCellValue(book.getTitle());
            sheet.autoSizeColumn(1);

            cell = row.createCell(2);
            cell.setCellValue(book.getAuthor());
            sheet.autoSizeColumn(2);

            cell = row.createCell(3);
            cell.setCellValue(book.getDescription());
            sheet.autoSizeColumn(3);

            cell = row.createCell(4);
            String genresString = book.getGenres()
                    .stream()
                    .map(Genres::getGenreName)
                    .reduce((r1, r2) -> r1 + ", " + r2)
                    .orElse("");

            cell.setCellValue(genresString);
            sheet.autoSizeColumn(4);

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
