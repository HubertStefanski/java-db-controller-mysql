import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Random;

public class MenuDriver {

    private static int selectedID = Integer.MAX_VALUE;
    private static String connectionUrl = "jdbc:mysql://localhost:3306/test";
    private static String createTableStatement = "CREATE TABLE PERSON(id INTEGER not NULL,fname VARCHAR(255),lname VARCHAR(255),salary INTEGER,ssnno VARCHAR(255),gender VARCHAR(255),PRIMARY KEY ( id ))";
    private static String populateTableStatement = "INSERT INTO PERSON VALUES(1, 'Ada', 'Kelly', 30000,'123456789','M');";
    private static MainMenu mainMenu = new MainMenu();


    public static void run(String[] args) throws Exception {

        init();

        JFrame frame = new JFrame("MainMenu");

        mainMenu.getPersonTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (mainMenu.getPersonTable() != null && mainMenu.getPersonTable().getModel().getRowCount() > 0) {
                    JTable table = mainMenu.getPersonTable();
                    selectedID = (int) table.getValueAt(table.getSelectedRow(), 0);
                    mainMenu.getFname().setText(table.getValueAt(table.getSelectedRow(), 1).toString());
                    mainMenu.getLname().setText(table.getValueAt(table.getSelectedRow(), 2).toString());
                    mainMenu.getSalary().setText(table.getValueAt(table.getSelectedRow(), 3).toString());
                    mainMenu.getSsnno().setText(table.getValueAt(table.getSelectedRow(), 4).toString());
                    mainMenu.getGender().setText(table.getValueAt(table.getSelectedRow(), 5).toString());
                    System.out.println(table.getValueAt(table.getSelectedRow(), 5).toString());
                }
            }
        });

        mainMenu.getSaveButton().addActionListener(e -> {
            try {
                create(new Person(mainMenu.getGender().getText(), mainMenu.getSalary().getText(), mainMenu.getSsnno().getText(), mainMenu.getLname().getText(), mainMenu.getFname().getText()), invokeConnection());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        mainMenu.getUpdateButton().addActionListener(e -> {

            try {
                update(new Person(mainMenu.getGender().getText(), mainMenu.getSalary().getText(), mainMenu.getSsnno().getText(), mainMenu.getLname().getText(), mainMenu.getFname().getText()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        //your actions
        mainMenu.getDeleteButton().addActionListener(e -> {
            try {
                delete();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        frame.setContentPane(mainMenu.UIView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }


    private static void refreshTable() {
        DefaultTableModel model;
        model = (DefaultTableModel) mainMenu.getPersonTable().getModel();
        model.setRowCount(0);
        JTable locTable = mainMenu.getPersonTable();
        locTable = mainMenu.FillTable(locTable);
        mainMenu.setPersonTable(locTable);

    }

    public static java.sql.Connection invokeConnection() {

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
                se.printStackTrace();
            }
            try {
                if (con != null)
                    con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        refreshTable();

        System.out.println(String.format("initialized db with %s ", populateTableStatement));
    }


    public static void create(Person person, Connection con) throws SQLException {

        Statement stmnt;
        Random rand = new Random();

        int locID = rand.nextInt(101);
        String locFname = person.getFname();
        String locLname = person.getLname();
        String locSalary;
        String locSsnno = person.getSsnno();
        String locGender = person.getGender();

        if (!locGender.equals("M") && !locGender.equals("F")) {
            locGender = "N";
        }
        locSalary = person.getSalary();

        try {
            stmnt = con.createStatement();
            stmnt.executeUpdate(String.format("INSERT INTO PERSON VALUES(%d, '%s', '%s', '%s', '%s','%s');", locID, locFname, locLname, locSalary, locSsnno, locGender));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        refreshTable();
    }

    public static ResultSet readAll() {
        //invoke connection for local use
        ResultSet rs = null;
        Connection con;
        Statement stmnt;

        try {
            con = invokeConnection();

            //initialize the database if empty
            stmnt = con.createStatement();

            rs = stmnt.executeQuery("SELECT * FROM  PERSON");

        } catch (Exception se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return rs;

    }

    public static void update(Person updatePerson) throws SQLException {
        //invoke connection for local use
        Connection con = null;
        Statement stmnt = null;

        try {
            con = invokeConnection();

            //initialize the database if empty
            stmnt = con.createStatement();

            String personString = String.format("fname = '%s', lname = '%s', salary = '%s', ssnno = '%s', gender = '%s'", updatePerson.getFname(), updatePerson.getLname(), updatePerson.getSalary(), updatePerson.getSsnno(), updatePerson.getGender());
            stmnt.executeUpdate(String.format("UPDATE PERSON SET %s WHERE ID = %d", personString, selectedID));


        } catch (Exception se) {
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
        refreshTable();
    }

    public static void delete() throws SQLException {
        //invoke connection for local use
        Connection con = null;
        Statement stmnt = null;

        try {
            con = invokeConnection();

            //initialize the database if empty
            stmnt = con.createStatement();

            stmnt.executeUpdate(String.format("DELETE FROM PERSON WHERE ID = %d", selectedID));


        } catch (Exception se) {
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
        refreshTable();
    }

}

