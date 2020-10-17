import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainMenu {
    public JPanel UIView;

    public JTextField getSsnno() {
        return ssnno;
    }

    public void setSsnno(JTextField ssnno) {
        this.ssnno = ssnno;
    }

    public JTextField getGender() {
        return gender;
    }

    public void setGender(JTextField gender) {
        this.gender = gender;
    }

    public JTextField getSalary() {
        return salary;
    }

    public void setSalary(JTextField salary) {
        this.salary = salary;
    }

    public JTextField getLname() {
        return lname;
    }

    public void setLname(JTextField lname) {
        this.lname = lname;
    }

    public JTextField getFname() {
        return fname;
    }

    public void setFname(JTextField fname) {
        this.fname = fname;
    }

    private JTextField ssnno;
    private JTextField gender;
    private JTextField salary;
    private JTextField lname;
    private JTextField fname;
    private JButton saveButton;
    private JButton updateButton;

    public JButton getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(JButton saveButton) {
        this.saveButton = saveButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public void setUpdateButton(JButton updateButton) {
        this.updateButton = updateButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(JButton deleteButton) {
        this.deleteButton = deleteButton;
    }

    private JButton deleteButton;
    public JTable personTable;


    public JTable getPersonTable() {
        return personTable;
    }

    public void setPersonTable(JTable personTable) {

        this.personTable = personTable;
    }


    public void setData(Person data) {
        gender.setText(data.getGender());
        salary.setText(String.valueOf(data.getSalary()));
        ssnno.setText(data.getSsnno());
        lname.setText(data.getLname());
        fname.setText(data.getFname());
    }

    public void getData(Person data) {
        data.setGender(gender.getText());
        data.setSalary(salary.getText());
        data.setSsnno(ssnno.getText());
        data.setLname(lname.getText());
        data.setFname(fname.getText());
    }

    public boolean isModified(Person data) {
        if (gender.getText() != null ? !gender.getText().equals(data.getGender()) : data.getGender() != null)
            return true;
        if (salary.getText() != null || String.valueOf(data.getSalary()) != null)
            return true;
        if (ssnno.getText() != null ? !ssnno.getText().equals(data.getSsnno()) : data.getSsnno() != null) return true;
        if (lname.getText() != null ? !lname.getText().equals(data.getLname()) : data.getLname() != null) return true;
        if (fname.getText() != null ? !fname.getText().equals(data.getFname()) : data.getFname() != null) return true;
        return false;
    }

    public JTable FillTable(JTable table) {
        try {

            String[] tableColumnsName = {"ID", "fname", "lname", "salary", "ssnno", "gender"};
            DefaultTableModel aModel = (DefaultTableModel) table.getModel();
//            getPersonTable().setVisible(false);
            aModel.setColumnIdentifiers(tableColumnsName);

            Connection conn = MenuDriver.invokeConnection();
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT * from PERSON");

// Loop through the ResultSet and transfer in the Model
            java.sql.ResultSetMetaData rsmd = rs.getMetaData();
            int colNo = rsmd.getColumnCount();
            while (rs.next()) {
                Object[] objects = new Object[colNo];
                for (int i = 0; i < colNo; i++) {
                    objects[i] = rs.getObject(i + 1);
                }
                aModel.addRow(objects);

            }
            rs.close();
            stat.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return table;

    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
