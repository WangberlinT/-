package view;

import bean.Admin;
import bean.User;
import dao.AdminDao;
import dao.UserDao;
import util.CustomerException;

import java.util.*;

public class StartMenu extends View{
     private int inputID;
     private String inputPassword;
     private boolean isAdmin=false;
     private View view;
     private User user;
     private Admin admin;

    public static void main(String[] args) {
        StartMenu startMenu = new StartMenu();
        startMenu.displayMenu();

    }

    public void loginMenu()
    {
        try {
            System.out.println("UserID: ");
            inputID = Integer.parseInt(in.nextLine());
            System.out.println("Password: ");
            inputPassword = in.nextLine();
        }
        catch (Exception e)
        {
            System.out.println("输入无效！");
            loginMenu();
        }

        if(checkPassword())//密码不正确
        {
            System.out.println("登陆成功!");
        }
        else
            return;



        //登陆成功
        if(isAdmin)
            view = new AdminView(inputID,inputPassword);
        else
            view = new UserView(user);

        view.displayMenu();

    }

    public void display()
    {
        System.out.println("欢迎使用社团管理系统\n"
            + "-----------------\n"
            + "1.登陆\n"
            + "2.登陆(管理员)\n"
            + "3.注册\n"
            + "4.退出\n");
    }


    public void displayMenu()
    {
        final int EXIT = 4;
        int instruction;
        while(true) {
            display();
            System.out.print('>');
            try {
                instruction = Integer.parseInt(in.nextLine());

                if (instruction == EXIT)
                    break;

                switch (instruction) {
                    case 1:
                        isAdmin = false;
                        loginMenu();
                        break;
                    case 2:
                        isAdmin = true;
                        loginMenu();
                        break;
                    case 3:
                        signUp();
                        break;
                    default:
                        throw new CustomerException("Wrong input");
                }
            } catch (Exception e) {
                System.out.println("输入无效！");
                e.getMessage();
            }
        }
    }

    public void signUp()
    {
        UserDao userDao = new UserDao();
        System.out.println("输入注册学号: ");
        int UID = Integer.parseInt(in.nextLine());
        System.out.println("输入密码: ");
        String password = in.nextLine();
        System.out.println("输入姓名:");
        String Name = in.nextLine();
        System.out.println("输入性别:");
        String gender = in.nextLine();
        try {
            userDao.insertUser(new User(UID, password, Name, gender));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("注册成功!");
    }

    public boolean checkPassword()
    {
        System.out.println("登陆中...");
        if(isAdmin)
        {
            //todo Admin Password check
            return adminPasswordCheck();
        }
        else
        {
            //todo User Password check
            return userPasswordCheck();
        }
    }

    public boolean adminPasswordCheck()
    {
        //todo 管理员密码检查
        AdminDao adminDao = new AdminDao();
        admin = null;
        try {
            admin = adminDao.queryAdminByID(inputID);
            if(admin == null)
            {
                System.out.println("管理员不存在");
                return false;
            }
            else
            {
                if(admin.getPassword().equals(inputPassword))
                    return true;
                else
                {
                    System.out.println("密码错误！");
                    return false;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("数据库连接失败,检查网络连接");
        }
        return false;
    }

    public boolean userPasswordCheck()
    {
        UserDao userDao = new UserDao();
        user = null;
        try {
            user = userDao.queryUserByID(inputID);
            if(user == null)
            {
                System.out.println("用户名不存在");
                return false;
            }
            else
            {
                if(user.getPassword().equals(inputPassword))
                    return true;
                else
                {
                    System.out.println("密码错误！");
                    return false;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("数据库连接失败,检查网络连接");
        }
        return false;
    }
}
