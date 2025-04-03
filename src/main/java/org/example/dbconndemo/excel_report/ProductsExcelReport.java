package org.example.dbconndemo.excel_report;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.*;
import org.example.dbconndemo.database.ProductDao;
import org.example.dbconndemo.models.Product;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class ProductsExcelReport {

    ProductDao productDao = new ProductDao();

    Map<String, Integer> categoriesT = productDao.totalProductsByCategory();


    public void createExcel(String filename, String category_name) {
        // HSSFWorkbook -> XLS
        // XSSFWorkbook -> XLSX
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("Products");

        XSSFRow row = sheet.createRow(0);

        CellStyle cellTitleStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        cellTitleStyle.setFont(font);
        cellTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        XSSFCell cellHeader1 = row.createCell(0);
        cellHeader1.setCellValue("ID");
        cellHeader1.setCellStyle(cellTitleStyle);
        XSSFCell cellHeader2 = row.createCell(1);
        cellHeader2.setCellValue("NAME");
        cellHeader2.setCellStyle(cellTitleStyle);
        XSSFCell cellHeader3 = row.createCell(2);
        cellHeader3.setCellValue("DESCRIPTION");
        cellHeader3.setCellStyle(cellTitleStyle);
        XSSFCell cellHeader4 = row.createCell(3);
        cellHeader4.setCellValue("PRICE");
        cellHeader4.setCellStyle(cellTitleStyle);
        XSSFCell cellHeader5 = row.createCell(4);
        cellHeader5.setCellValue("QUANTITY");
        cellHeader5.setCellStyle(cellTitleStyle);
        XSSFCell cellHeader6 = row.createCell(5);
        cellHeader6.setCellValue("COLOR");
        cellHeader6.setCellStyle(cellTitleStyle);
        XSSFCell cellHeader7 = row.createCell(6);
        cellHeader7.setCellValue("CATEGORY");
        cellHeader7.setCellStyle(cellTitleStyle);
        XSSFCell cellHeader8 = row.createCell(7);
        cellHeader8.setCellValue("IMAGE");
        cellHeader8.setCellStyle(cellTitleStyle);

        int row_number = 1;
        CellStyle cellStyleDateFormat = workbook.createCellStyle();
        CreationHelper helper = workbook.getCreationHelper();
        cellStyleDateFormat.setDataFormat(helper.createDataFormat().getFormat("dd/mm/yyyy"));
        cellStyleDateFormat.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleDateFormat.setAlignment(HorizontalAlignment.CENTER);
        CellStyle cellContentStyle = sheet.getWorkbook().createCellStyle();
        cellContentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellContentStyle.setAlignment(HorizontalAlignment.CENTER);


        for (Product product : (category_name.equals("All Products")) ? productDao.findAll() : productDao.findByCategories(category_name)) {
            XSSFRow newRow = sheet.createRow(row_number++);

            XSSFCell cell1 = newRow.createCell(0);
            cell1.setCellValue(product.getId_product());
            cell1.setCellStyle(cellContentStyle);
            XSSFCell cell2 = newRow.createCell(1);
            cell2.setCellValue(product.getName());
            cell2.setCellStyle(cellContentStyle);
            XSSFCell cell3 = newRow.createCell(2);
            cell3.setCellValue(product.getDescription().substring(0, 10) + "...");
            cell3.setCellStyle(cellContentStyle);
            XSSFCell cell4 = newRow.createCell(3);
            cell4.setCellValue(product.getPrice());
            cell4.setCellStyle(cellContentStyle);
            XSSFCell cell5 = newRow.createCell(4);
            cell5.setCellValue(product.getQuantity());
            cell5.setCellStyle(cellContentStyle);
            XSSFCell cell6 = newRow.createCell(5);
            cell6.setCellValue(product.getColor());
            cell6.setCellStyle(cellStyleDateFormat);
            XSSFCell cell7 = newRow.createCell(6);
            cell7.setCellValue(product.getCategory_Name());
            cell7.setCellStyle(cellContentStyle);
            InputStream inputStream1 = getClass().getResourceAsStream("/img/" + product.getImage());

            try {
                byte[] inputImageBytes1 = IOUtils.toByteArray(inputStream1);
                int inputImagePictureID1 = workbook.addPicture(inputImageBytes1, workbook.PICTURE_TYPE_JPEG);
                XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
                XSSFClientAnchor imageAnchor = new XSSFClientAnchor();
                imageAnchor.setCol1(7); // Sets the column (0 based) of the first cell.
                imageAnchor.setCol2(8); // Sets the column (0 based) of the Second cell.

                imageAnchor.setRow1(row_number - 1); // Sets the row (0 based) of the first cell.
                imageAnchor.setRow2(row_number); // Sets the row (0 based) of the Second cell.
                XSSFPicture myPicture = drawing.createPicture(imageAnchor, inputImagePictureID1);
                //myPicture.resize();
                //myPicture.getImageDimension().setSize(100, 100);
                //newRow.setHeight((short)-1);
                newRow.setHeightInPoints(60);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);

        createSheetReportTotal(workbook);

        try (FileOutputStream outputStream = new FileOutputStream(filename)) {
            workbook.write(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void createSheetReportTotal(XSSFWorkbook workbook) {
        // HSSFWorkbook -> XLS
        // XSSFWorkbook -> XLSX

        XSSFSheet sheetTotal = workbook.createSheet("Total");

        XSSFRow row = sheetTotal.createRow(0);

        CellStyle cellTitleStyle = sheetTotal.getWorkbook().createCellStyle();
        Font font = sheetTotal.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        cellTitleStyle.setFont(font);
        cellTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        XSSFCell cellHeader1 = row.createCell(0);
        cellHeader1.setCellValue("CATEGORY");
        cellHeader1.setCellStyle(cellTitleStyle);
        XSSFCell cellHeader2 = row.createCell(1);
        cellHeader2.setCellValue("TOTAL PRODUCTS");
        cellHeader2.setCellStyle(cellTitleStyle);

        int row_numberC = 1;

        CellStyle cellStyleDateFormat = workbook.createCellStyle();
        CreationHelper helper = workbook.getCreationHelper();
        cellStyleDateFormat.setDataFormat(helper.createDataFormat().getFormat("dd/mm/yyyy"));
        cellStyleDateFormat.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleDateFormat.setAlignment(HorizontalAlignment.CENTER);
        CellStyle cellContentStyle = sheetTotal.getWorkbook().createCellStyle();
        cellContentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellContentStyle.setAlignment(HorizontalAlignment.CENTER);

        for (Map.Entry<String, Integer> entry : categoriesT.entrySet()) {
            XSSFRow newRowC = sheetTotal.createRow(row_numberC++);
//            System.out.println("Categoría: " + entry.getKey() + ", Total: " + entry.getValue());

            XSSFCell cell1 = newRowC.createCell(0);
            cell1.setCellValue(entry.getKey());
            cell1.setCellStyle(cellContentStyle);

            XSSFCell cell2 = newRowC.createCell(1);
            cell2.setCellValue(entry.getValue());
            cell2.setCellStyle(cellContentStyle);

        }

        sheetTotal.autoSizeColumn(0);
        sheetTotal.autoSizeColumn(1);

//        try (FileOutputStream outputStream = new FileOutputStream(filename)) {
//            workbook.write(outputStream);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
