public class Person {
    private String gender;
    private String salary;
    private String ssnno;
    private String lname;
    private String fname;

    public Person(String gender, String salary, String ssnno, String lname, String fname) {
        this.gender = gender;
        this.salary = salary;
        this.ssnno = ssnno;
        this.lname = lname;
        this.fname = fname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(final String salary) {
        this.salary = salary;
    }

    public String getSsnno() {
        return ssnno;
    }

    public void setSsnno(final String ssnno) {
        this.ssnno = ssnno;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(final String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(final String fname) {
        this.fname = fname;
    }
}
