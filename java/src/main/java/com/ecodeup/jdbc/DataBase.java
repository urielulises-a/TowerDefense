package com.ecodeup.jdbc;


import java.sql.*;
import java.util.Objects;

public class DataBase {

    static int id;
    String username;
    static int level;
    static Connection connection;
    static PreparedStatement preparedStatement;
    ResultSet resultSet;

    private  final String SQLurl = "jdbc:mysql://localhost:3306/tower?serverTimezone=UTC";
    private final String SQLuser = "root";
//    private final String SQLpassword = "CacadeVaca230403";
    private final String SQLpassword = "31415";
    public void startData(String username){

        this.username = username;

        try{

            if(!userExists()){
                createUser();
            }
            else{
                System.out.println("Si existe ");
            }

//            connection.close();
//            preparedStatement.close();
//            resultSet.close();

        }catch (SQLException exception){

            exception.printStackTrace();
        }


    }
    public boolean userExists() throws SQLException{


            connection = DriverManager.getConnection(SQLurl, SQLuser, SQLpassword);

            preparedStatement = connection.prepareStatement("SELECT * FROM USUARIO WHERE nombre=?"); //Busqueda de dado usuario

            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

//            ResultSet resultSet = statement.executeQuery( " SELECT * FROM usuario");

            while (resultSet.next()){

                String userSearch = resultSet.getString("nombre");

                if (Objects.equals(userSearch, username)){
                    id = resultSet.getInt(1);
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

        preparedStatement.setString(1, "0");
        preparedStatement.setString(2, username);
        preparedStatement.setInt(3, 0);
        preparedStatement.executeUpdate();

    }
    static public void updateUser(int newLevel) throws SQLException{

        //TODO se tiene que cerrar la conexion de la base de datos
        preparedStatement = connection.prepareStatement("UPDATE usuario SET progreso = ? WHERE idusuario = " + id);

        preparedStatement.setInt(1, newLevel);
        preparedStatement.executeUpdate();
//
//        connection.close();
//        preparedStatement.close();
//        resultSet.close();
    }
}
