package HospitalManagementSystem;

import java.sql.*;
import java.util.Locale;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "Rehekuri@1205";

    public static void main(String[] args) {
        try {
            Class.forName("com.sql.cj.jdbc.Driver");

        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);

        try {
            Connection connection = DriverManager.getConnection(url,username,password);
            patient Patient = new patient(connection,scanner);
            doctors Doctor = new doctors(connection);

            while (true){
                System.out.println("Hospital Management System : ");
                System.out.println("1.Add Patient ");
                System.out.println("2.View Patient ");
                System.out.println("3.View Doctor ");
                System.out.println("4.Book Appointment");
                System.out.println("5.Exit ");
                System.out.println("Enter Your Choice : ");
                int Choice = scanner.nextInt();

                switch (Choice){
                    case 1:
                        Patient.addPatient();
                        break;
                    case 2:
                        Patient.viewPatient();
                        break;
                    case 3:
                        Doctor.viewDoctor();
                        break;
                    case 4:
                        bookAppointment(Patient,Doctor,connection,scanner);
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Enter valid Choice : ");
                        break;
                }

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public static void bookAppointment( patient Patient,doctors Doctor ,Connection connection , Scanner scanner){
        System.out.println("Enter Patient Id :");
        int patient_id = scanner.nextInt();
        System.out.println("Enter Doctor Id : ");
        int doctor_id = scanner.nextInt();
        System.out.println("Enter appointment Date (YYYY-MM-DD) : ");
        String appointmentDate = scanner.next();

        if (Patient.getPatientById(patient_id) && Doctor.getDoctorById(doctor_id)){
            if (checkDoctorAvalability(doctor_id, appointmentDate,connection)){
               String appointmentQuery = "INSERT INTO appointment(patient_id,doctor_id,appointment_date)VALUES(?,?,?)";
               try{
                   PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                   preparedStatement.setInt(1,patient_id);
                   preparedStatement.setInt(2,doctor_id);
                   preparedStatement.setString(3,appointmentDate);

                   int rowsAfffected = preparedStatement.executeUpdate();
                   if (rowsAfffected>0){
                       System.out.println("Appointment booked .");
                   }else {
                       System.out.println("Failed to book appointment .");
                   }

               }catch (SQLException e){
                   e.printStackTrace();
               }

            }else {
                System.out.println("Doctor not available on this date");
            }
        }else{
            System.out.println("Either Patient or Doctors Does'nt exist .");
        }
    }

    public static boolean checkDoctorAvalability(int doctor_id,String appointment_date,Connection connection){
        String Query = "SELECT COUNT(*) FROM appointment WHERE doctor_id=? AND appointment_date = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setInt(1,doctor_id);
            preparedStatement.setString(2,appointment_date);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                int count =resultSet.getInt(1);
                if (count==0){
                    return true;
                }else {
                    return false;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
