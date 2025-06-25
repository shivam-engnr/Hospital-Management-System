package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class doctors {
        private Connection connection;

        public doctors(Connection connection){
            this.connection = connection;

        }


        public void viewDoctor(){
            String query = "SELECT *FROM doctors;";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                System.out.println("doctors : ");
                System.out.println("+-----------+---------------+------------------+");
                System.out.println("|doctor_id  | Name          | Specialization   |");
                System.out.println("+-----------+---------------+------------------+");
                while (resultSet.next()){
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String specialization = resultSet.getString("specialization");
                    System.out.printf("| %-9s | %-13s | %-16s |\n",id,name,specialization);
                    System.out.println("+-----------+---------------+------------------+");
                }
            }catch (SQLException e){
                e.getStackTrace();
            }
        }

        public boolean getDoctorById(int id){
            String query = "SELECT * FROM doctors WHERE id = ? ";
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


