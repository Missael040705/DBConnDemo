module org.example.dbconndemo {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;

    opens org.example.dbconndemo.models to javafx.base;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    requires kernel;
    requires layout;
    requires io;
    requires org.slf4j;
    requires org.apache.logging.log4j;
    requires org.apache.poi.ooxml;

    exports org.example.dbconndemo;
    opens org.example.dbconndemo to java.base, javafx.fxml;
    exports org.example.dbconndemo.database;
    opens org.example.dbconndemo.database to java.base, javafx.fxml;
}