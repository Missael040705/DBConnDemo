package org.example.dbconndemo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import org.example.dbconndemo.dashboard.InventoryDashboard;
import org.example.dbconndemo.database.CategoryDao;
import org.example.dbconndemo.database.MySQLConnection;
import org.example.dbconndemo.excel_report.*;
import org.example.dbconndemo.itext_reports.*;
import org.example.dbconndemo.models.Book;
import org.example.dbconndemo.models.Category;
import org.example.dbconndemo.models.Product;
import org.example.dbconndemo.database.ProductDao;

import java.awt.*;
import java.io.File;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Map;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    private TableView tblProducts;

    @FXML
    private ComboBox<Category> cbCategory;

    private List<Product> ProductList = new ArrayList<>();
    private List<Category> CategoryList = new ArrayList<>();


    private String selectCategory;
    List<Product> productsOB;

    ProductDao pdao = new ProductDao();

    CategoryDao pcat = new CategoryDao();

    Map<String, Integer> categoriesT = pdao.totalProductsByCategory();

    Connection conn = MySQLConnection.getConnection();


    public static final String DEST1 = "results/chapter01/hello_world.pdf";
    public static final String DEST2 = "results/chapter01/rick_astley.pdf";

    public static final String DEST3 = "results/chapter01/quick_world.pdf";
    public static final String DOG = "src/main/resources/img/dog.bmp";
    public static final String FOX = "src/main/resources/img/fox.bmp";

    public static final String DEST4 = "results/chapter01/united_states.pdf";
    public static final String DATA = "src/main/resources/data/united_states.csv";

    public static final String DEST5 = "results/chapter01/products.pdf";


    public static final String DEST6 = "results/excel/simpleBook.xlsx";
    public static final String DEST7 = "results/excel/NiceBook.xlsx";
    // public static final String DEST8 = "results/excel/NiceBook.xlsx";

    // EXCEL

    @FXML
    protected void onProductsExcelClick() throws IOException {
        // NiceExcelWriterExample excelWriter = new NiceExcelWriterExample();
        if (cbCategory.getSelectionModel().isEmpty()) {
            showMsg("Please select a category.");
            return;
        }

        selectCategory = cbCategory.getSelectionModel().getSelectedItem().toString();

        if (selectCategory.equals("All Products")) {
            productsOB = pdao.findAll();
        } else {
            productsOB = pdao.findByCategories(selectCategory);
        }

        String categoryDest = "results/excel/" + selectCategory + ".xlsx";
        File file = new File(categoryDest);
        file.getParentFile().mkdirs();
        new ProductsExcelReport().createExcel(categoryDest, selectCategory);
        openFile(categoryDest);
        System.out.println("File Created " + categoryDest);
        showMsg("File " + selectCategory + " Report Created");
    }

    @FXML
    protected void onFlexibleClick() throws IOException {


    }

    @FXML
    protected void onFormattedClick() throws IOException {
        FormattedExcelWriterExample excelWriter = new FormattedExcelWriterExample();
        List<Book> listBook = excelWriter.getListBook();
        String excelFilePath = "FormattedJavaBooks.xls";
        excelWriter.writeExcel(listBook, excelFilePath);
        showMsg(excelFilePath + " Created");
    }

    @FXML
    protected void onNiceClick() throws IOException {
        // NiceExcelWriterExample excelWriter = new NiceExcelWriterExample();
        File file = new File(DEST7);
        file.getParentFile().mkdirs();
        new NiceExcelWriterExample().createExcelReport(DEST7);
        openFile(DEST7);
        System.out.println("File Created " + DEST7);
        showMsg("File United States Report Created");

        showMsg(DEST7 + " Created");
    }

    @FXML
    protected void onSimpleClick() throws IOException {

        File file = new File(DEST6);
        file.getParentFile().mkdirs();
        new SimpleExcelWriterExample().createExcelReport(DEST6);
        openFile(DEST6);
        System.out.println("File Created " + DEST6);
        showMsg("File United States Report Created");

    }

    // PDF
    @FXML
    protected void onHelloWorldClick() throws IOException {
        File file = new File(DEST1);
        file.getParentFile().mkdirs();
        new C01E01_HelloWorld().createPdf(DEST1);
        openFile(DEST1);
        System.out.println("File Created " + DEST1);
        showMsg("File Hello World Report Created");

    }

    @FXML
    protected void onRickAstleyClick() throws IOException {
        File file = new File(DEST2);
        file.getParentFile().mkdirs();
        new C01E02_RickAstley().createPdf(DEST2);
        openFile(DEST2);
        System.out.println("File Created " + DEST2);
        showMsg("File Rick Astley Report Created");
    }

    @FXML
    protected void onQuickClick() throws IOException {
        File file = new File(DEST3);
        file.getParentFile().mkdirs();
        new C01E03_QuickBrownFox().createPdf(DEST3);
        openFile(DEST3);
        System.out.println("File Created " + DEST3);
        showMsg("File Quick Brown Fox Report Created");
    }

    @FXML
    protected void onUSAClick() throws IOException {
        File file = new File(DEST4);
        file.getParentFile().mkdirs();
        new C01E04_UnitedStates().createPdf(DEST4);
        openFile(DEST4);
        System.out.println("File Created " + DEST4);
        showMsg("File United States Report Created");
    }

    @FXML
    protected void onProductsClick() throws IOException {
        if (cbCategory.getSelectionModel().isEmpty()) {
            showMsg("Please select a category.");
            return;
        }

        selectCategory = cbCategory.getSelectionModel().getSelectedItem().toString();

        if (selectCategory.equals("All Products")) {
            productsOB = pdao.findAll();
        } else {
            productsOB = pdao.findByCategories(selectCategory);
        }

        String categoryDest = "results/chapter01/" + selectCategory + ".pdf";
        File file = new File(categoryDest);
        file.getParentFile().mkdirs();
        new ProductsReport().createPdf(categoryDest, selectCategory);
        openFile(categoryDest);
        System.out.println("File Created " + categoryDest);
        showMsg("File " + selectCategory + " Report Created");
    }

    private void showMsg(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hello World");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    protected void openFile(String filename) throws IOException {
        if (Desktop.isDesktopSupported()) {
            try {
                File file = new File(filename);
                file.getParentFile().mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void onHelloButtonClick() {
        if (cbCategory.getSelectionModel().getSelectedItem() == null) {
            showMsg("Please select a category.");
            return;
        }

        selectCategory = cbCategory.getSelectionModel().getSelectedItem().toString();

        if (selectCategory.equals("All Products")) {
            productsOB = pdao.findAll();
        } else {
            productsOB = pdao.findByCategories(selectCategory);
        }


        for (Map.Entry<String, Integer> entry : categoriesT.entrySet()) {
            System.out.println("Categoría: " + entry.getKey() + ", Total: " + entry.getValue());
        }
        //ProductList = FXCollections.observableArrayList(pdao.findAll());
        ProductList = FXCollections.observableArrayList(productsOB);
        tblProducts.setItems(FXCollections.observableList(ProductList));
    }

    @FXML
    protected void onDashClick() {

        new InventoryDashboard().showDashboard();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initForm();
    }

    private void initForm() {
        CategoryList = pcat.findAll();
        CategoryList.add(0, new Category(0, "All Products"));
        cbCategory.setItems(FXCollections.observableArrayList(CategoryList));
    }


}