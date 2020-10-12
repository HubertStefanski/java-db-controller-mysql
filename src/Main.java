public class Main {

    private static Person person;



    public static void main (String[] args){

    }


    public static String create (Person person)  {
        return "Success";

    }
    public static Person read(){
        return person;

    }
    public static String update(Person person){
        return "Person successfully updated";


    }
    public static String delete(Person person){
        return "Person successfully deleted";


    }
}
