package com.qltv.QLTV.ExcelExport;

import com.qltv.QLTV.Entity.Roles;
import com.qltv.QLTV.Entity.Users;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

//@Component

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserExcelExporter {
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    List<Users> usersList;

    public UserExcelExporter(List<Users> usersList) {
        this.usersList = usersList;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Users");
    }

    void writeHeaderRow(){
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);

        Cell cell = row.createCell(0);
        cell.setCellValue("User ID");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("User Name");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("Full Name");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("Phone");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("Email");
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue("Date of birth");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("Address");
        cell.setCellStyle(style);

        cell = row.createCell(7);
        cell.setCellValue("Role");
        cell.setCellStyle(style);
    }

    void writeDataRow(){
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        style.setFont(font);

        for(Users user : usersList){
            Row row = sheet.createRow(rowCount++);

            Cell cell = row.createCell(0);
            cell.setCellValue(user.getUserId());
            cell.setCellStyle(style);
            sheet.autoSizeColumn(0);

            cell = row.createCell(1);
            cell.setCellValue(user.getUserName());
            cell.setCellStyle(style);
            sheet.autoSizeColumn(1);

            cell = row.createCell(2);
            cell.setCellValue(user.getFullName());
            cell.setCellStyle(style);
            sheet.autoSizeColumn(2);

            cell = row.createCell(3);
            cell.setCellValue(user.getPhone());
            cell.setCellStyle(style);
            sheet.autoSizeColumn(3);

            cell = row.createCell(4);
            cell.setCellValue(user.getEmail());
            cell.setCellStyle(style);
            sheet.autoSizeColumn(4);

            cell = row.createCell(5);
            cell.setCellValue(user.getDob() != null ? user.getDob().toString() : "");
            cell.setCellStyle(style);
            sheet.autoSizeColumn(5);

            cell = row.createCell(6);
            cell.setCellValue(user.getAddress());
            cell.setCellStyle(style);
            sheet.autoSizeColumn(6);

            String rolesString = user.getRoles().stream()
                    .map(Roles::getRoleName)
                    .reduce((r1, r2) -> r1 + ", " + r2)
                    .orElse("");

            row.createCell(7).setCellValue(rolesString);
            cell.setCellStyle(style);
            sheet.autoSizeColumn(7);

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
