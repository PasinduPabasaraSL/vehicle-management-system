package com.example.javafxapp.model;

import com.example.javafxapp.db.DBConnection;
import com.example.javafxapp.dto.VehicleDto;
import com.example.javafxapp.tm.VehicleTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleModel {
    public static boolean saveVehicle(VehicleDto vehicleDto) throws SQLException, ClassNotFoundException {
        boolean status = false;
        PreparedStatement preparedStatement;
        Connection connection = DBConnection.getDBConnection().getConnection();

        try {
            preparedStatement = connection.prepareStatement("insert into vehicle values (?,?, ?, ?, ?)");
            preparedStatement.setObject(1, vehicleDto.getId());
            preparedStatement.setObject(2, vehicleDto.getBrand());
            preparedStatement.setObject(3, vehicleDto.getModel());
            preparedStatement.setObject(4, vehicleDto.getQty());
            preparedStatement.setObject(5, vehicleDto.getPrice());

            int i = preparedStatement.executeUpdate();

            if (i > 0) {
                status = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return status;
    }

    public static boolean deleteVehicle(int id) throws SQLException, ClassNotFoundException {
        boolean status = false;
        Connection connection = DBConnection.getDBConnection().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("delete from vehicle where id = ?");
        preparedStatement.setObject(1, id);

        //execute
        int i = preparedStatement.executeUpdate();

        if (i > 0) {
            status = true;
        }

        return status;
    }

    public static VehicleDto searchVehicle(int id) throws SQLException, ClassNotFoundException {
        VehicleDto vehicleDto = null;
        Connection connection = DBConnection.getDBConnection().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("select * from vehicle where id = ?");
        preparedStatement.setObject(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();


        while (resultSet.next()) {
            vehicleDto = new VehicleDto(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getInt(4),
                    resultSet.getDouble(5));
        }

        return vehicleDto;
    }

    public static boolean updateVehicle(VehicleDto vehicleDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDBConnection().getConnection();
        boolean status = false;

        //Write sql query
        PreparedStatement preparedStatement = connection.prepareStatement("update vehicle set brand=?,model=?,qty=?,price=? where id=?");
        preparedStatement.setObject(1, vehicleDto.getBrand());
        preparedStatement.setObject(2, vehicleDto.getModel());
        preparedStatement.setObject(3, vehicleDto.getQty());
        preparedStatement.setObject(4, vehicleDto.getPrice());
        preparedStatement.setObject(5, vehicleDto.getId());

        //execute
        int i = preparedStatement.executeUpdate();

        if (i > 0) {
            status = true;
        }

        return  true;

    }

    public static ArrayList<VehicleTM> loadVehicle() throws SQLException, ClassNotFoundException {

        Connection connection = DBConnection.getDBConnection().getConnection();

        //Write sql query
        PreparedStatement preparedStatement = connection.prepareStatement("select * from vehicle");

        //execute
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<VehicleTM> tms = new ArrayList<>();

        while (resultSet.next()) {
            tms.add(new VehicleTM(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getInt(4),
                    resultSet.getDouble(5)));

        }

        return tms;

    }

}