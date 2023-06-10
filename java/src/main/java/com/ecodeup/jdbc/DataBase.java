package com.ecodeup.jdbc;


import java.sql.*;
import java.util.Objects;

public class DataBase {

    int id;
    String username;
    int level;

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    private  final String SQLurl = "jdbc:mysql://localhost:3306/tower?serverTimezone=UTC";
    private final String SQLuser = "root";
    private final String SQLpassword = "CacadeVaca230403";
    public void startData(){

        username = "nombre2";

        try{

            if(!userExists()){
                createUser();
            }
            else{
                System.out.println("Si existe ");
            }

            connection.close();
            preparedStatement.close();
            resultSet.close();

        }catch (SQLException exception){

            exception.printStackTrace();
        }


    }
    //TODO El nombre del usuario se va a ingresar como un string (puede ser por parámetro o alguna otra manera)
    public boolean userExists() throws SQLException{


            connection = DriverManager.getConnection(SQLurl, SQLuser, SQLpassword);
//            statement = connection.createStatement();

            preparedStatement = connection.prepareStatement("SELECT * FROM USUARIO WHERE nombre=?"); //Busqueda de dado usuario

            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

//            ResultSet resultSet = statement.executeQuery( " SELECT * FROM usuario");


            while (resultSet.next()){

                String userSearch = resultSet.getString("nombre");

                if (Objects.equals(userSearch, username)){
                    level = resultSet.getInt(3);
                    return true;
                }


//                System.out.println(resultSet.getString("idUsuario") + " | " +
//                        resultSet.getString("nombre") + " | " +
//                        resultSet.getString(level   ));
            }

        return  false;
    }


    public void createUser() throws SQLException{

        preparedStatement = connection.prepareStatement("INSERT INTO usuario VALUES (?,?,?)");

        //TODO hacer que el id no se pueda repetir, es decir dar un id nuevo adecuado al usuario
        preparedStatement.setInt(1, 2);
        preparedStatement.setString(2, username);
        preparedStatement.setInt(3, 1);
        preparedStatement.executeUpdate();

    }
}
