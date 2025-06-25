package HospitalManagementSystem;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class patient {
    private Connection connection;
    private Scanner scanner;

    public patient(Connection connection,Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;

    }

    // AddPatient method

    public void addPatient(){
        System.out.print("Enter patient name : ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("Enter patient age : ");
        int age = scanner.nextInt();
        System.out.print("Enter patient gender : ");
        String gender = scanner.next();

        try {
            String query = "INSERT INTO patients(name,age,gender) VALUES(? ,? ,? )";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows>0){
                System.out.println("Patient data inserted succesfully .");
            }else {
                System.out.println("failed to addd patient .");
            }

        }catch (SQLException e){
            e.getStackTrace();
        }

    }

    public void viewPatient(){
        String query = "SELECT * FROM patients";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("patients : ");
            System.out.println("+-----------+---------------+--------+----------+");
            System.out.println("|patient_id | Name          | Age    | Gender   |");
            System.out.println("+-----------+---------------+--------+----------+");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("|%-11s|%-15s|%-8s|%-10s|\n",id,name,age,gender);
                System.out.println("+-----------+---------------+--------+----------+");
            }
        }catch (SQLException e){
            e.getStackTrace();
        }
    }

    public boolean getPatientById(int id){
        String query = "SELECT * FROM patients WHERE id = ? ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }else {
                return false;
            }
        }catch (SQLException e){
            e.getStackTrace();
        }
        return false;
    }
}
