package org.example.dbconndemo.itext_reports;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import org.example.dbconndemo.database.ProductDao;
import org.example.dbconndemo.models.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Simple table example.
 */
public class ProductsReport {


    ProductDao productDao = new ProductDao();


    //public static final String DEST = "results/chapter01/united_states.pdf";


    public void createPdf(String dest, String products) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf, PageSize.A4.rotate());
        document.setMargins(20, 20, 20, 20);

        // Estilos del PDF

        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        // Table que en el constructor indica el ancho de las columnas

        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 4, 2, 2, 2, 3, 3}))
                .useAllAvailableWidth();
        process(table, null, bold, true);


        if (products.equals("All Products")) {
            for(Product p : productDao.findAll()) {
                process(table, p, font,false);
            }
        }
        else {
            for(Product p : productDao.findByCategories(products)) {
                process(table, p, font,false);
            }
        }



        document.add(table);

        //Close document
        document.close();
    }

    public void process(Table table, Product p, PdfFont font, boolean isHeader) {
        // StringTokenizer tokenizer = new StringTokenizer(line, ";");
        // while (tokenizer.hasMoreTokens()) {
            if (isHeader) {
                table.addHeaderCell(new Cell().add(new Paragraph("ID").setFont(font)));
                table.addHeaderCell(new Cell().add(new Paragraph("NAME").setFont(font)));
                table.addHeaderCell(new Cell().add(new Paragraph("PRICE").setFont(font)));
                table.addHeaderCell(new Cell().add(new Paragraph("QUANTITY").setFont(font)));
                table.addHeaderCell(new Cell().add(new Paragraph("COLOR").setFont(font)));
                table.addHeaderCell(new Cell().add(new Paragraph("CATEGORY").setFont(font)));
                table.addHeaderCell(new Cell().add(new Paragraph("IMAGE").setFont(font)));
            } else {
                table.addCell(new Cell().add(new Paragraph(p.getId_product() + "").setFont(font)));
                table.addCell(new Cell().add(new Paragraph(p.getName() + "").setFont(font)));
                table.addCell(new Cell().add(new Paragraph(p.getPrice() + "").setFont(font)));
                table.addCell(new Cell().add(new Paragraph(p.getQuantity() + "").setFont(font)));
                table.addCell(new Cell().add(new Paragraph(p.getColor() + "").setFont(font)));
                table.addCell(new Cell().add(new Paragraph(p.getCategory_Name() + "").setFont(font)));
                Image imgProduct = new Image(ImageDataFactory.create(getClass().getResource("/img/" + p.getImage())));
                imgProduct.setHeight(80);
                imgProduct.setWidth(80);
                table.addCell(new Cell().add(new Paragraph().add(imgProduct)));

            }
        //}
    }
}