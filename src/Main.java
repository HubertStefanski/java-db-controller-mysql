import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        try {
            MenuDriver.run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
