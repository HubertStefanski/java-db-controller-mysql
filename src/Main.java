import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        try {
            MenuDriver.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
