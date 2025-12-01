package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

public class DbUnitUtil {

    public static IDatabaseConnection getConnection() throws Exception {
        Connection conn = DriverManager.getConnection(
            "jdbc:derby:memory:clinicaDB;create=true", "app", "app"
        );
        return new DatabaseConnection(conn);
    }

    public static void insertData() throws Exception {
        InputStream is = DbUnitUtil.class.getResourceAsStream("/dataset.xml");
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(is);

        IDatabaseConnection connection = getConnection();
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
    }
}