package ocp;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static org.junit.Assert.*;

/**
 * Created by williaz on 12/17/16.
 *  JDBC stands for Java Database Connectivity
 */
public class JdbcTest {
    private ResourceBundle db;

    @Before
    public void setUp() throws Exception {
        db = ResourceBundle.getBundle("ocp.db");

    }

    /**
     * Data is information.
     * A database is an organized collection of data, such as folder, rDB.
     * A relational database is a database that is organized into tables, which consist of rows and columns.
     *
     * 4 interfaces in JDBC:
     * Driver: Knows how to get a connection to the database
     * Connection: Knows how to communicate with the database
     * Statement: Knows how to run the SQL
     * ResultSet: Knows what was returned by a SELECT query
     */
    @Test
    public void test_Jdbc() {
        try (Connection conn = DriverManager.getConnection(db.getString("url"), db.getString("user"), db.getString("password"));
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID BETWEEN 100 AND 200")) {
            while (rs.next()) {
                System.out.println(rs.getString("FIRST_NAME") + " " + rs.getString("LAST_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * Protocol : Product/VendorName : Database Specific Connection Details
     *   Colons separate the three parts
     *   The third part typically contains the location and the name of the database.
     *   A location can be localhost or an IP address or a domain name.
     *
     * There are two main ways to get a Connection: DriverManager or DataSource.
     * Remember that the implementation class for Connection is found inside a driver JAR.
     *
     * DriverManager looks through any drivers in classpath for JARs it can find to see if they can handle the JDBC URL.
     */
    @Test
    public void test_Connection() {
        Connection conn = null;
        try {
            Class.forName(db.getString("DriverClassName")); //ensure that the specified class is loaded by the current classloader.
            conn = DriverManager.getConnection(db.getString("url"), db.getString("user"), db.getString("password"));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * createStatement():
     * The first is the ResultSet type, and the other is the ResultSet concurrency mode.
     * 1. By default, a ResultSet is in TYPE_FORWARD_ONLY mode.
     *    TYPE_SCROLL_INSENSITIVE(static data) and TYPE_SCROLL_SENSITIVE(See Latest Data from Database Table).
     *      most databases and database drivers don’t actually support the TYPE_SCROLL_SENSITIVE mode.
     *      -> If the type you request isn’t available, the driver can “helpfully” downgrade to one that is.
     * 2. By default, a ResultSet is in CONCUR_READ_ONLY mode. (can’t update the result set.)
     *    CONCUR_UPDATABLE(not universally supported).
     *
     *   boolean execute(): If sql is a SELECT, the boolean is true and we can get the ResultSet.
     *                      If it is not a SELECT, we can get the number of rows updated.
     * ResultSet executeQuery() : SELECT
     *       int executeUpdate(): INSERT, UPDATE, DELETE - nums of row affected
     *   -> if misused, runtime exception
     */

    @Test
    public void test_Statement() {
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(db.getString("url"), db.getString("user"), db.getString("password"));
            statement = conn.createStatement();
            int num = 0;
            num = statement.executeUpdate("INSERT INTO VW_EMP_MUST VALUES (401, 'Kan', 'CK', SYSDATE, 'IT_PROG')");
            assertEquals(1, num);
            num = statement.executeUpdate("UPDATE EMPLOYEES SET LAST_NAME = 'Kevin' WHERE EMPLOYEE_ID = 401");
            assertEquals(1, num);
            rs = statement.executeQuery("SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID = 401");
            rs.next();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                System.out.print(rs.getString(i) + " ");
            }
            boolean isResultSet = statement.execute("DELETE FROM EMPLOYEES WHERE EMPLOYEE_ID = 401");
            if (!isResultSet) {
                assertEquals(1, statement.getUpdateCount());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * PrepareStatement:
     * 1. Performance: For running similar queries multiple times,
     *                a Prepared- Statement figures out a plan to run the SQL well and remembers it.
     * 2. Security: SQL-injection
     * 3. Readability
     */



}
