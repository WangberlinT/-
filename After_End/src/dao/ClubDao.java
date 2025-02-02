package dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import bean.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;


//社团信息的增删改查
public class ClubDao {

        //获取总社团数量
        public long getTotalClub() {
            try {
                QueryRunner queryRunner = C3P0Util.getQueryRunner();
                String sql = "SELECT COUNT(*) from Club";
                return queryRunner.query(sql, new ScalarHandler<>());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
        }
        
        //查询所有社团
        public List<Club> queryAllClub(int currentPage, int pageSize) throws SQLException {
            int start = (currentPage - 1) * pageSize;
            QueryRunner queryRunner = C3P0Util.getQueryRunner();
            String sql = "select * from Club LIMIT ?,?";
            return queryRunner.query(sql, new BeanListHandler<>(Club.class), start, pageSize);
        }

        public void insertAct(int uid,int acid)throws SQLException{
            QueryRunner queryRunner = C3P0Util.getQueryRunner();
            String sql = "insert into Activity_User (Activity_ID, User_ID) VALUES (?,?);";
            Object[] param = {uid,acid};
            queryRunner.update(sql, param);
        }

        //注册社团
        public void addClub(Club club) throws SQLException {
            QueryRunner queryRunner = C3P0Util.getQueryRunner();
            String sql = "insert into Club(Club_ID, Club_Name, Club_Type, Club_Intro, Club_Leader,Club_State)" +
                    "VALUES (?, ?, ?, ?, ?,?);";
            Object[] param = {club.getClub_ID(), club.getClub_Name(), club.getClub_Type(), club.getClub_Intro(), club.getClub_Leader(),club.getClub_State()};
            queryRunner.update(sql, param);
        }

        public void addClub(String name,String type,String intro,int uid) throws SQLException {
            QueryRunner queryRunner = C3P0Util.getQueryRunner();
            String sql = "call addclub(?,?,?,?);";
            Object[] param = {name,type,intro,uid};
            queryRunner.update(sql, param);
        }
        
        //删除社团(将社团变为闲置状态,同时删除社团里的人）
        public void deleteClub(Club club) throws SQLException {
            QueryRunner queryRunner = C3P0Util.getQueryRunner();
            String sql = "update Club set Club_State=0 where Club_ID=?";
            String sql1 = "delete from User_Club where Club_ID=?";
            Object[] param = {club.getClub_ID()};
            queryRunner.update(sql, param);
            queryRunner.update(sql1, param);
        }

        //更新社团信息
        public void updateClub(Club club) throws SQLException {
            QueryRunner queryRunner = C3P0Util.getQueryRunner();
            String sql = "update Club set Club_Name=?, Club_Type=?, Club_Intro=?, Club_Leader=?,club_State=?  where Club_ID=?";
            Object[] param = {club.getClub_Name(), club.getClub_Type(), club.getClub_Intro(), club.getClub_Leader(),club.getClub_State(),club.getClub_ID()};
            queryRunner.update(sql, param);
        }

        //通过社团名字查询社团（精确搜索）
        public Club queryClubPrecise(String name) throws SQLException {
            QueryRunner queryRunner = C3P0Util.getQueryRunner();
            String sql = "select * from Club where Club_Name=?";
            return queryRunner.query(sql, new BeanHandler<>(Club.class), name);
        }

        public Club queryClubPrecise(int cid) throws SQLException {
            QueryRunner queryRunner = C3P0Util.getQueryRunner();
            String sql = "select * from Club where Club_ID=?";
            return queryRunner.query(sql, new BeanHandler<>(Club.class), cid);
        }
        
        //通过社团名字查询社团（模糊搜索包含该名字的）
        public List<Club> queryClubFuzzy(String name, int currentPage, int pageSize) throws SQLException {
            QueryRunner queryRunner = C3P0Util.getQueryRunner();
            String tmp = "%" + name + "%";
            int start = (currentPage - 1) * pageSize;
            String sql = "select * from Club where Club_Name like ? LIMIT ?,?;";
            return queryRunner.query(sql, new BeanListHandler<>(Club.class), tmp,start, pageSize);
        }

        public long queryClubFuzzyNum(String name) throws SQLException {
        	 String tmp = "%" + name + "%";
            QueryRunner queryRunner = C3P0Util.getQueryRunner();
            String sql = "select count(*) from Club where Club_Name like ?;";
            return queryRunner.query(sql, new ScalarHandler<>(), tmp);
        }
        
        //通过社团类别查询社团
        public List<Club> queryClubByType(String type, int currentPage, int pageSize) throws SQLException {
            int start = (currentPage - 1) * pageSize;
            QueryRunner queryRunner = C3P0Util.getQueryRunner();
            String sql = "select * from Club where Club_Type=?;";
            return queryRunner.query(sql, new BeanListHandler<>(Club.class), type, start, pageSize);
        }
        
        public long queryClubByTypeNum(String type) throws SQLException {
           
            QueryRunner queryRunner = C3P0Util.getQueryRunner();
            String sql = "select COUNT(*) from Club where Club_Type=?;";
            return queryRunner.query(sql, new ScalarHandler<>(), type);
        }


        //加人到社团(手动输入职位）
        public void joinclub(String Work_Name,Club club, User user) throws SQLException {
            QueryRunner queryRunner = C3P0Util.getQueryRunner();
            String sql = "insert into User_Club(User_ID, Club_ID, Work_Name) VALUES (?, ?, ?);";
            Object[] param ={user.getUser_ID(),club.getClub_ID(),Work_Name};
            queryRunner.update(sql, param);
        }
        public void joinclub(String Work_Name,int cid, int uid) throws SQLException {
            QueryRunner queryRunner = C3P0Util.getQueryRunner();
            String sql = "insert into User_Club(User_ID, Club_ID, Work_Name) VALUES (?, ?, ?);";
            Object[] param ={uid,cid,Work_Name};
            queryRunner.update(sql, param);
        }

        //从社团删除人
        public void exitclub(Club club, User user) throws SQLException {
            QueryRunner queryRunner = C3P0Util.getQueryRunner();
            String sql = "delete from User_Club where User_ID=? and Club_ID=?";
            Object[] param ={user.getUser_ID(),club.getClub_ID()};
            queryRunner.update(sql, param);
        }
        public void exitclub(int cid,int uid) throws SQLException {
            QueryRunner queryRunner = C3P0Util.getQueryRunner();
            String sql = "delete from User_Club where User_ID=? and Club_ID=?";
            Object[] param ={uid,cid};
            queryRunner.update(sql, param);
        }

        //找到一个人所属的社团
        public List<User_Club> queryone(User user) throws SQLException {
            QueryRunner queryRunner = C3P0Util.getQueryRunner();
            String sql = "select Club_ID,Name,Club_Name,Work_Name,User_ID from User_Club natural join Club natural join User where User_ID=?";
            return queryRunner.query(sql, new BeanListHandler<>(User_Club.class),user.getUser_ID());
        }
        public List<User_Club> queryone(int uid) throws SQLException {
            QueryRunner queryRunner = C3P0Util.getQueryRunner();
            String sql = "select Club_ID,Name,Club_Name,Work_Name,User_ID from User_Club natural join Club natural join User where User_ID=?";
            return queryRunner.query(sql, new BeanListHandler<>(User_Club.class),uid);
        }

    public List<Activity> queryActivityByClubID(int cid, int currentPage) throws SQLException {
        int start = (currentPage - 1) * 10;
        QueryRunner queryRunner = C3P0Util.getQueryRunner();
        String sql = "select * from Activity natural join Activity_Club where Club_ID=?  ORDER BY Start_Time DESC LIMIT ?,?;";
        return queryRunner.query(sql, new BeanListHandler<>(Activity.class), cid, start, 10);
    }

    public long queryActivityByClubIDNUM(int cid) throws SQLException {
        QueryRunner queryRunner = C3P0Util.getQueryRunner();
        String sql = "select COUNT(*) from Activity natural join Activity_Club where Club_ID=?;";
        return queryRunner.query(sql, new ScalarHandler<>(), cid);
    }

    public void insertActClub(String name, Timestamp st,Timestamp et,String intro,int uid,int cid,int r)throws SQLException{
        QueryRunner queryRunner = C3P0Util.getQueryRunner();//IN Nam varchar(45), IN st datetime, IN et datetime, IN intro varchar(200), IN uid int, IN cid int, IN r int
        String sql = "call addActclub(?,?,?,?,?,?,?);";
        Object[] param ={name,st,et,intro,uid,cid,r};
        queryRunner.update(sql, param);
    }

        //通过ID找社团
        public Club queryClubID(int ID) throws SQLException {
            QueryRunner queryRunner = C3P0Util.getQueryRunner();
            String sql = "select * from Club where Club_ID=?";
            return queryRunner.query(sql, new BeanHandler<>(Club.class), ID);
        }

        public long queryClubPeopleNum(int cid) throws SQLException {
        	 QueryRunner queryRunner = C3P0Util.getQueryRunner();
             String sql = "select COUNT(*) from User_Club where Club_ID=?;";
             return queryRunner.query(sql, new ScalarHandler<>(),cid);
        }
        
    
    
}