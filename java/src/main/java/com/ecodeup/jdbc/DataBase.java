package com.ecodeup.jdbc;

import java.sql.*;

public class DataBase {

    public void startData(){

        String url = "jdbc:mysql://localhost:3306/tower?serverTimezone=UTC";
        String username = "root";
        String password = "31415";

        try {

            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery( " SELECT  * FROM USUARIO");

            while (resultSet.next()){

                System.out.println(resultSet.getString("idUsuario") + " | " +
                        resultSet.getString("nombre") + " | " +
                        resultSet.getString("contrasenya") + " | " +
                        resultSet.getString("progreso") );
            }

            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {

            e.printStackTrace();
//            throw new RuntimeException(e);
        }
    }
}
