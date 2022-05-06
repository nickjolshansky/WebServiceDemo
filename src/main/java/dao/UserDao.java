package dao;

import entity.User;

import java.util.List;

public interface UserDao {
    public void create(User user);
    public void update(User user);
    public void delete(int idData);
    User get(int idData);
    List<User> getAll();

}
