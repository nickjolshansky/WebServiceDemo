package dao;

import entity.User;
import factories.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao{
    private Connection connection;

    public UserDaoImpl(){
        connection = ConnectionManager.getConnection();
    }

    @Override
    public void create(User user) {
        String query = "INSERT INTO users (firstName, lastName, email) VALUES (?, ?, ?)";
        System.out.println("Test");

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            int count = preparedStatement.executeUpdate();
            if(count == 1){
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                //set userId to generated key here
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        String query = "UPDATE  users SET firstName = ?, lastName = ?, email = ? WHERE userId = ?;";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getUserId());
            int count = preparedStatement.executeUpdate();
            if(count == 1){
                System.out.println("Update successful");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int idData) {
        String query = "DELETE FROM users WHERE userId = ?;";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idData);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                System.out.println("Delete successful");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public User get(int idData) {
        String query = "SELECT * FROM users WHERE userId = ?;";
        User user = null;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idData);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                user =  getUserFromResultSet(resultSet);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT * FROM users;";
        List<User> users = new ArrayList<User>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                User user =  getUserFromResultSet(resultSet);
                users.add(user);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return users;
    }

    private User getUserFromResultSet(ResultSet resultSet){
        User user = null;
        try{
            int userId = resultSet.getInt("userId");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String email = resultSet.getString("email");
            return new User(userId, firstName, lastName, email);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
