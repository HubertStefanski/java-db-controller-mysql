import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.List;
import java.util.Vector;

public class MenuDriver {


    private static String connectionUrl = "jdbc:mysql://localhost:3306/test";
    private static String createTableStatement = "CREATE TABLE PERSON(id INTEGER not NULL,fname VARCHAR(255),lname VARCHAR(255),salary INTEGER,ssnno VARCHAR(255),gender VARCHAR(255),PRIMARY KEY ( id ))";
    private static String populateTableStatement = "INSERT INTO PERSON VALUES(1, 'Ada', 'Kelly', 30000,'123456789','M');";
    private static MainMenu mainMenu = new MainMenu();
    private static JTable table = new JTable();


    public static void run(String[] args) throws Exception {

//        init();
        JFrame frame = new JFrame("MainMenu");
        JTable locTable = mainMenu.getPersonTable();
        locTable = mainMenu.FillTable(locTable);
        mainMenu.setPersonTable(locTable);
        frame.setContentPane(mainMenu.UIView);
//        mainMenu.FillTable(mainMenu.getPersonTable());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }


    private JTable refreshTable() throws SQLException {
        JTable newTable;

        ResultSet rs = readAll();
        newTable = new JTable(buildTableModel(rs));
        return newTable;

    }

    public static java.sql.Connection invokeConnection() throws SQLException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionUrl, "root", "");
            return con;

        } catch (Exception e) {
            System.out.println("caught exception " + e);
            return null;
        }


    }

    public static void init() {


        //invoke connection for local use
        DatabaseMetaData dbmeta;
        ResultSet rs;
        Connection con = null;
        Statement stmnt = null;

        try {
            con = invokeConnection();

            dbmeta = con.getMetaData();

//            System.out.println("got to meta data");

            rs = dbmeta.getTables(null, null, "PERSON", null);
            //initialize the database if empty
            stmnt = con.createStatement();
            if (!rs.next()) {

                //create the table if none exists
                stmnt.executeUpdate(createTableStatement);

                //populate the table with data
                stmnt.executeUpdate(populateTableStatement);
            }

        } catch (
                Exception se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } finally {
            //close off
            try {
                if (stmnt != null)
                    con.close();
            } catch (SQLException se) {
            }// do nothing
            try {
                if (con != null)
                    con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        System.out.println(String.format("initialized db with %s ", populateTableStatement));
    }


    public static String create(Person person, Connection con) {

        Statement stmnt = null;

        int locID = 0;
        String locFname = "";
        String locLname = "";
        int locSalary = 0;
        String locSsnno = "000000000";
        String locGender = "N";

        try {
            if (person.getFname() != null || person.getFname() != "") {
                locFname = person.getFname();

            } else {
                locFname = "null";
            }
            if (person.getLname() != null || person.getLname() != "") {
                locLname = person.getLname();

            } else {
                locLname = "null";
            }
            if (person.getSsnno() != null || person.getSsnno() != "") {
                locSsnno = person.getSsnno();
            }
            if (person.getSsnno() != "N") {
                locGender = person.getGender();
            }
            locSalary = person.getSalary();

            locID = (int) Math.random();


            stmnt = con.createStatement();

            stmnt.executeUpdate(String.format("INSERT INTO PERSON VALUES(%s, %s, %s, %s,%s, %s);", locID, locFname, locLname, locSalary, locSsnno, locGender));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return "Success";

    }

    public static ResultSet readAll() {
        //invoke connection for local use
        DatabaseMetaData dbmeta;
        ResultSet rs = null;
        Connection con = null;
        Statement stmnt = null;
        List<Person> personList = null;

        try {
            con = invokeConnection();

            //initialize the database if empty
            stmnt = con.createStatement();

            rs = stmnt.executeQuery("SELECT * FROM  PERSON");

//            while (rs.next()) {
//                Person prsn = new Person();
//                //Get field values for person
//                prsn.setFname(rs.getString("fname"));
//                prsn.setLname(rs.getString("lname"));
//                prsn.setSsnno(rs.getString("ssnno"));
//                prsn.setSalary(rs.getString("salary"));
//                prsn.setGender(rs.getString("gender"));
//                //Add person to list
//                personList.add(prsn);
//
//            }

        } catch (Exception se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return rs;

    }

    public static String update(Person updatePerson) {
        //invoke connection for local use
        ResultSet rs;
        Connection con = null;
        Statement stmnt = null;

        try {
            con = invokeConnection();

            //initialize the database if empty
            stmnt = con.createStatement();

            String personString = String.format("fname = %s, lname = %s, salary = %s, gender = %s", updatePerson.getFname(), updatePerson.getLname(), updatePerson.getSalary(), updatePerson.getGender());
            rs = stmnt.executeQuery(String.format("UPDATE %s SET %s WHERE ssnno = %s", "test", personString, updatePerson.getSsnno()));

            //rs.close();

        } catch (
                Exception se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } finally {
            //close off
            try {
                if (stmnt != null)
                    con.close();
            } catch (SQLException se) {
            }// do nothing
            try {
                if (con != null)
                    con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return "Person successfully updated";
    }

    public static String delete(Person person, Connection con) {
        return "";
    }

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }


}

