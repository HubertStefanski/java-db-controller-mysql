import javax.swing.*;

public class MainMenu {
    private JPanel panel1;
    private JTextField ssnno;
    private JTextField gender;
    private JTextField salary;
    private JTextField lname;
    private JTextField fname;
    private JTable table1;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;

    public void setData(Person data) {
        gender.setText(data.getGender());
        salary.setText(data.getSalary());
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
        if (salary.getText() != null ? !salary.getText().equals(data.getSalary()) : data.getSalary() != null)
            return true;
        if (ssnno.getText() != null ? !ssnno.getText().equals(data.getSsnno()) : data.getSsnno() != null) return true;
        if (lname.getText() != null ? !lname.getText().equals(data.getLname()) : data.getLname() != null) return true;
        if (fname.getText() != null ? !fname.getText().equals(data.getFname()) : data.getFname() != null) return true;
        return false;
    }
}
