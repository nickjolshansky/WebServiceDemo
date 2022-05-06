package servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.UserDao;
import dao.UserDaoImpl;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpRetryException;
import java.util.List;

public class UserServlet extends HttpServlet {
    private UserDao userDao = new UserDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("Pong!");
        try{
            List<User> users = userDao.getAll();
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(users);
            resp.setStatus(200);
            resp.getWriter().print(json);
        }
        catch (IOException e){
            resp.setStatus(500);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        try{
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(req.getInputStream(), User.class);
            userDao.create(user);

            resp.setStatus(203);
            resp.getWriter().print("Success!");
        }
        catch(IOException e){
            resp.setStatus(500);
            resp.getWriter().print("Something went wrong");
            System.out.println(e.getLocalizedMessage());
        }
    }
}
