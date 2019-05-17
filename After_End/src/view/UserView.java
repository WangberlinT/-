package view;
import bean.*;
import util.StringAlign;

import java.util.Date;

public class UserView extends View{
    private User user;
    private int News;
    private boolean hasNewActivities;
    static final String VISIBLE = "(可见)";
    static final String INVISIBLE = "(隐藏)";


    public UserView(int ID,String password)
    {
        user = new User(ID,password);
        News = 0;
        hasNewActivities = false;
    }

    public void displayMenu()
    {
        int instruction = -1;
        final int EXIT = 6;
        while(true)
        {
            display();
            System.out.print('>');
            String temp = in.nextLine();
            if(temp.length() == 1)
            {
                if(Character.isDigit(temp.charAt(0)))
                    instruction = Character.getNumericValue(temp.charAt(0));
            }

            if(instruction == EXIT)
                break;

            switch (instruction)
            {
                case 1:
                    informationCheck();
                    break;
                case 2:
                    //todo 我的社团
                    break;
                case 3:
                    //todo 社团浏览
                    break;
                case 4:
                    //新活动已读
                    hasNewActivities = false;
                    //todo 活动墙浏览
                    break;
                case 5:
                    //新消息已读
                    News = 0;
                    //todo 消息查看
                default:
                    System.out.println("-------------\n"
                                      +"invalid input\n"
                                      +"-------------\n");
            }
        }

    }

    public void informationCheck()
    {
        int instruction = -1;
        final int EXIT = 4;
        while(true)
        {
            //display
            String name = "<1>姓名: " + user.getName();
            String gender = "<2>性别: " + user.getSex();
            String born = "<3>生日: " + user.getBorn();
            String Major = "<4>专业: " + user.getMajor();
            String Addess = "<5>地址: " + user.getAddress();
            String Phone = "<6>电话号码: " + user.getPhone_Number();



            formatter = new StringAlign(15,StringAlign.JUST_LEFT);
            System.out.print(formatter.format(name)+formatter.format(Major)+"\n"
                            +formatter.format(gender)+formatter.format(Addess)+"\n"
                            +formatter.format(born)+formatter.format(Phone)+"\n"
                            +formatter.format("1.修改信息")+formatter.format("2.隐私设置")+"\n"
                            +formatter.format("3.修改密码")+formatter.format("4.返回")+"\n");



            String temp = in.nextLine();
            if(temp.length() == 1)
            {
                if(Character.isDigit(temp.charAt(0)))
                    instruction = Character.getNumericValue(temp.charAt(0));
            }
            if(instruction == EXIT)
                break;

            switch (instruction)
            {
                case 1:
                    //todo 个人信息修改
                    modifyInfomation();
                    break;
                case 2:
                    //todo 隐私设置
                    break;
                case 3:
                    //todo 密码修改
                    break;
                default:
                    System.out.println("-------------\n"
                            +"invalid input\n"
                            +"-------------\n");
            }
        }
    }

    public void modifyInfomation()
    {

        int instruction = -1;
        final int EXIT = 0;
        while(true)
        {
            System.out.println("要修改哪一项信息?(输入0放弃修改)");
            System.out.print('>');
            String temp = in.nextLine();
            if(temp.length() == 1)
            {
                if(Character.isDigit(temp.charAt(0)))
                    instruction = Character.getNumericValue(temp.charAt(0));
            }

            if(instruction == EXIT)
                break;

            switch (instruction)
            {
                case 1:
                    System.out.println("输入新的姓名");
                    String newName = in.nextLine();
                    nameModify(newName);
                    break;
                case 2:
                    System.out.println("输入新的性别");
                    String newSex = in.nextLine();
                    sexModify(newSex);
                    break;
                case 3:
                    //todo 生日修改
                    break;
                case 4:
                    //todo 专业修改
                    break;
                case 5:
                    //todo 地址修改
                    break;
                case 6:
                    //todo 电话号码修改
                    break;
                default:
                    System.out.println("-------------\n"
                            +"invalid input\n"
                            +"-------------\n");
            }
//            user.update();//和数据库中信息保持一致
        }
    }

    private void bornModify(Date newBorn)
    {

    }

    private void majorModify(String newMajor)
    {

    }

    private void AddressModify(String newAddress)
    {

    }

    private void PhoneNumberModify(int newPhone)
    {

    }





    private void nameModify(String newName)
    {
        if(newName.length()<=20)
            user.setName(newName);
        else
            System.out.println("更改失败！输入字符串长度大于20！");
    }

    private void sexModify(String newSex)
    {
        if(newSex.equals(User.GENDER[0])||newSex.equals(User.GENDER[1]))
            user.setSex(newSex);
        else
            System.out.println("无效的性别设置");
    }

    public void display()
    {
        checkNewActivities();
        checkNews();

        System.out.printf(
                "Welcome User: %d\n"
                        +"-----------------\n"
                        +"1.我的信息\n"
                        +"2.我的社团\n"
                        +"3.社团浏览\n"
                        +"4.活动墙\n"
                        +"5.消息盒子\n"
                        +"6.退出登陆\n"
                , user.getUser_ID());
        if(News!=0)
            System.out.printf("---你有%d条未读消息---\n",News);

        if(hasNewActivities)
            System.out.println("---活动墙有新活动---");

    }

    public void checkNews()
    {
        //todo 检查数据库中有没有新的发给本对象的申请或者公告
        News = 3;//查询结果，这里仅做测试
    }

    public void checkNewActivities()
    {
        //todo 检查数据库中是否有新活动
        hasNewActivities = true;//同checkNews
    }
}
