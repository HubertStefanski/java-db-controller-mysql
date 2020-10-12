import java.sql.*;
import java.util.List;


public class Main {

    private static String connectionUrl = "jdbc:mysql://localhost:3306/test";


    private static String createTableStatement = "CREATE TABLE PERSON(id INTEGER not NULL,fname VARCHAR(255),lname VARCHAR(255),salary INTEGER,ssnno VARCHAR(255),gender VARCHAR(255),PRIMARY KEY ( id ))";

    private static String populateTableStatement = "INSERT INTO PERSON VALUES(1, 'Ada,', 'Kelly', 30000,'123456789','M');";

    private static String deletePersonStatement = String.format("delete from users where id = %s");

    public static void main(String[] args) {

        init();

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

    public static String init() {

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

        return String.format("initialized db with %s ", populateTableStatement);
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

    public static List<Person> readAll() {
        //invoke connection for local use
        DatabaseMetaData dbmeta;
        ResultSet rs;
        Connection con = null;
        Statement stmnt = null;
        List<Person> personList = null;

        try {
            con = invokeConnection();

            dbmeta = con.getMetaData();


            //initialize the database if empty

            stmnt = con.createStatement();

            rs = stmnt.executeQuery("SELECT * FROM  PERSON");

            while (rs.next()) {
                Person prsn = new Person();
                //Get field values for person
                prsn.setFname(rs.getString("fname"));
                prsn.setLname(rs.getString("lname"));
                prsn.setSsnno(rs.getString("ssnno"));
                prsn.setSalary(rs.getString("salary"));
                prsn.setGender(rs.getString("gender"));
                //Add person to list
                personList.add(prsn);

            }
            rs.close();


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

        return personList;

    }

    public static String update(Person person) {
        return "Person successfully updated";


    }

    public static String delete(Person person, Connection con) {
        return "";
    }
}
