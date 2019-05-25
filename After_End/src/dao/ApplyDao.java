package dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import bean.Apply;


public class ApplyDao {

	//���뽨��
	public void inserClubBuild(String cname,String ctype,String reason,int uid,int tid) throws SQLException {
		String sql="insert into Apply_To_Studert (Apply_Type, Apply_From, Apply_To, Apply_Description,) "
				+ "VALUES ('��������',?,?,?);";
		String content=ctype+" "+cname+":"+reason;
		QueryRunner queryRunner = C3P0Util.getQueryRunner();
		Object[] param = {uid,tid,content};
		queryRunner.update(sql, param);
	}
	//����ٰ�
	public void insertActAdd(String content,int uid,int tid) throws SQLException {
		String sql="insert into Apply_To_Studert (Apply_Type, Apply_From, Apply_To, Apply_Description,) "
				+ "VALUES ('�����',?,?,?);";
		
		QueryRunner queryRunner = C3P0Util.getQueryRunner();
		Object[] param = {uid,tid,content};
		queryRunner.update(sql, param);
	}
	//�����������
	public void insertJoinClub(int uid,String content,int tid) throws SQLException {
		String sql="insert into Apply_To_Studert (Apply_Type, Apply_From, Apply_To, Apply_Description,) "
				+ "VALUES ('��������',?,?,?);";
		
		QueryRunner queryRunner = C3P0Util.getQueryRunner();
		Object[] param = {uid,tid,content};
		queryRunner.update(sql, param);
	}
	//�õ��糤ѧ��
	public long getszID(int cid) throws SQLException {
		QueryRunner queryRunner = C3P0Util.getQueryRunner();
		String sql="select Club_Leader from Club where Club_ID=?;";
				return queryRunner.query(sql, new ScalarHandler<>(),cid);
	}
	//�������磨����id��д��
	public void insertJoinClub(int uid,int cid,String content) throws SQLException {
		String sql="insert into Apply_To_Studert (Apply_Type, Apply_From, Apply_To, Apply_Description,) "
				+ "VALUES ('��������',?,?,?);";
		
		QueryRunner queryRunner = C3P0Util.getQueryRunner();
		Object[] param = {uid,getszID(cid),content};
		queryRunner.update(sql, param);
	}
	//����Ѷ�
	public void markread(int appid) throws SQLException {
		String sql="update Apply_To_Studert set Apply_State=1 where Apply_ID=?;";
		
		QueryRunner queryRunner = C3P0Util.getQueryRunner();
		Object[] param = {appid};
		queryRunner.update(sql, param);
	}
	//�鿴��������
	public List<Apply> getClubBuild(int tid) throws SQLException{
		String sql="select Apply_ID,Apply_From,Apply_To,Apply_State,Apply_Description,Apply_Type,Name name,Phone_Number phone\n" + 
				"from Apply_To_Studert A join User U on A.Apply_From = U.User_ID\n" + 
				"where Apply_Type='��������' and Apply_To =? and Apply_State=0;";
		QueryRunner queryRunner = C3P0Util.getQueryRunner();
		return queryRunner.query(sql, new BeanListHandler<>(Apply.class),tid);
	}
	//�鿴�����
	public List<Apply> getActadd(int tid) throws SQLException{
		String sql="select Apply_ID,Apply_From,Apply_To,Apply_State,Apply_Description,Apply_Type,Name name,Phone_Number phone\n" + 
				"from Apply_To_Studert A join User U on A.Apply_From = U.User_ID\n" + 
				"where Apply_Type='�����' and Apply_To =? and Apply_State=0;";
		QueryRunner queryRunner = C3P0Util.getQueryRunner();
		return queryRunner.query(sql, new BeanListHandler<>(Apply.class),tid);
	}
	//�鿴��������
	public List<Apply> getJoinClub(int tid) throws SQLException{
		String sql="select Apply_ID,Apply_From,Apply_To,Apply_State,Apply_Description,Apply_Type,Name name,Phone_Number phone\n" + 
				"from Apply_To_Studert A join User U on A.Apply_From = U.User_ID\n" + 
				"where Apply_Type='��������' and Apply_To =? and Apply_State=0;";
		QueryRunner queryRunner = C3P0Util.getQueryRunner();
		return queryRunner.query(sql, new BeanListHandler<>(Apply.class),tid);
	}
	//�鿴��������
	public List<Apply> getAllApply(int tid) throws SQLException{
		String sql="select Apply_ID,Apply_From,Apply_To,Apply_State,Apply_Description,Apply_Type,Name name,Phone_Number phone\n" + 
				"from Apply_To_Studert A join User U on A.Apply_From = U.User_ID\n" + 
				"where Apply_To =? and Apply_State=0;";
		QueryRunner queryRunner = C3P0Util.getQueryRunner();
		return queryRunner.query(sql, new BeanListHandler<>(Apply.class),tid);
	}
	//�鿴��������
	public long getApplyNum(int tid) throws SQLException{
		String sql="select COUNT(*) \n" + 
				"from Apply_To_Studert A join User U on A.Apply_From = U.User_ID\n" + 
				"where Apply_Type='��������' and Apply_To =? and Apply_State=0;";
		QueryRunner queryRunner = C3P0Util.getQueryRunner();
		return queryRunner.query(sql, new ScalarHandler<>(),tid);
	}
}