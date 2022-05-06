package factories;

import dao.UserDao;
import dao.UserDaoImpl;

public class DaoFactory {
    private static UserDao userDao;

    private DaoFactory(){}

    public static UserDao getUserDao(){
        if(userDao == null){
            userDao = new UserDaoImpl();
        }

        return userDao;
    }
}