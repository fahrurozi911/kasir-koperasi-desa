package com.kasirkoperasi.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelExporter {
    public static void exportTable(JTable table, String sheetName, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(sheetName);
            TableModel model = table.getModel();

            // Header row
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < model.getColumnCount(); col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(model.getColumnName(col));
            }

            // Data rows
            for (int row = 0; row < model.getRowCount(); row++) {
                Row dataRow = sheet.createRow(row + 1);
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Cell cell = dataRow.createCell(col);
                    Object value = model.getValueAt(row, col);
                    if (value != null) {
                        cell.setCellValue(value.toString());
                    }
                }
            }

            // Auto-size columns
            for (int col = 0; col < model.getColumnCount(); col++) {
                sheet.autoSizeColumn(col);
            }

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
            JOptionPane.showMessageDialog(null, "Laporan berhasil diekspor ke " + filePath);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Gagal mengekspor: " + e.getMessage());
        }
    }
}