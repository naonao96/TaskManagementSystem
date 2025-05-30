package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.entity.TaskInfoBeans;
import model.entity.UserInfoBeans;
import util.UtilityTools;

public class TaskManagementDAO {
	
	// DB接続URL
	private String _url = "jdbc:mysql://localhost:3306/taskdb?useSSL=false&serverTimezone=UTC";
	// DB接続ユーザ名
	private String _User = "taskUser";
	// DB接続パスワード
	private String _Password = "taskPass";
	
	// ログインメールアドレスとパスワードの検索
	public UserInfoBeans revUserSertificate(String mail, String password){
		ConnectionManger connectionManger = new ConnectionManger(_url, _User, _Password);
		UserInfoBeans userInfo = new UserInfoBeans();
		
		try(Connection con = connectionManger.getConnection();)
		{
			String query = UtilityTools.readFile("/sql/selectUserInfo.sql");
			
			if (query == null) {
				System.out.println("SQLファイルの読み込みに失敗しました。");
				return null;
			}
			
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, mail);
			pstmt.setString(2, password);
			
			// 一致するユーザの取得を行います
			ResultSet ret = pstmt.executeQuery();
			
			while (ret.next()) {
				userInfo.setUserId  (ret.getString("user_id"));
				userInfo.setUserName(ret.getString("name"));
				userInfo.setMail    (ret.getString("mail_address"));
				userInfo.setPassword(ret.getString("password"));
			}
			
			con.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			System.out.println("データ取得失敗");
		}
		return userInfo;
	}
	
	// タスク一覧取得を行う
	public List<TaskInfoBeans> revTaskList(String userId) 
	{
		ConnectionManger connectionManger = new ConnectionManger(_url, _User, _Password);
		List<TaskInfoBeans> taskList = new ArrayList<TaskInfoBeans>();
		try(Connection con = connectionManger.getConnection();)
		{
			String query = UtilityTools.readFile("/sql/selectTaskInfo.sql");
			
			if (query == null) {
				System.out.println("SQLファイルの読み込みに失敗しました。");
				return null;
			}
			
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, userId);
			
			// 一致するユーザの取得を行います
			ResultSet ret = pstmt.executeQuery();
			
			while (ret.next()) {
				TaskInfoBeans taskInfo = new TaskInfoBeans();
				taskInfo.setTaskId      (ret.getString("task_id"));
				taskInfo.setTaskName    (ret.getString("task_name"));
				taskInfo.setTaskContent (ret.getString("task_contents"));
				taskInfo.setTaskDeadline(ret.getDate  ("task_deadline"));
				taskInfo.setTaskStatus  (ret.getString("task_status"));
				taskInfo.setTaskPriority(ret.getString("task_priority"));
				taskInfo.setTaskAssignee(ret.getString("task_assignee"));
				
				taskList.add(taskInfo);
			}
			
			con.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			System.out.println("データ取得失敗");
		}
		return taskList;
	}
	
	// タスク新規登録を行う
	public boolean insTaskInfo(
			String userId,
			String taskName,
			String taskContents,
			String taskDeadline,
			String taskStatus,
			String taskPriority,
			String taskAssignee) 
	{
		ConnectionManger connectionManger = new ConnectionManger(_url, _User, _Password);
		try(Connection con = connectionManger.getConnection();)
		{
			String query = UtilityTools.readFile("/sql/insertTaskInfo.sql");
			
			if (query == null) {
				System.out.println("SQLファイルの読み込みに失敗しました。");
				return false;
			}
			
			PreparedStatement pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, userId);
			pstmt.setString(2, taskName);
			pstmt.setString(3, taskContents);
			pstmt.setString(4, taskDeadline);
			pstmt.setString(5, taskStatus);
			pstmt.setString(6, taskPriority);
			pstmt.setString(7, taskAssignee);
			
			int result = pstmt.executeUpdate();
			
			if (result > 0) {
				return true;
			}
			
			con.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			System.out.println("データ取得失敗");
		}
		return false;
	}
	
	// タスク更新登録を行う
	public boolean updTaskInfo(
			String taskId,
			String userId,
			String taskName,
			String taskContents,
			String taskDeadline,
			String taskStatus,
			String taskPriority,
			String taskAssignee) 
	{
	ConnectionManger connectionManger = new ConnectionManger(_url, _User, _Password);
		try(Connection con = connectionManger.getConnection();)
		{
			String query = UtilityTools.readFile("/sql/updateTaskInfo.sql");
			
			if (query == null) {
				System.out.println("SQLファイルの読み込みに失敗しました。");
				return false;
			}
			
			PreparedStatement pstmt = con.prepareStatement(query);
			
			// パラメータの設定
			pstmt.setString(1, userId);
			pstmt.setString(2, taskName);
			pstmt.setString(3, taskContents);
			pstmt.setString(4, taskDeadline);
			pstmt.setString(5, taskStatus);
			pstmt.setString(6, taskPriority);
			pstmt.setString(7, taskAssignee);
			pstmt.setString(8, taskId);
			
			int result = pstmt.executeUpdate();
			
			if (result > 0) {
				return true;
			}
			
			con.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			System.out.println("データ更新失敗");
		}
		return false;
	}
	
	// タスク削除を行う
	public boolean delTaskInfo(String taskId) {	
		ConnectionManger connectionManger = new ConnectionManger(_url, _User, _Password);
		try(Connection con = connectionManger.getConnection();)
		{
			String query = UtilityTools.readFile("/sql/deleteTaskInfo.sql");
			
			if (query == null) {
				System.out.println("SQLファイルの読み込みに失敗しました。");
				return false;
			}
			
			PreparedStatement pstmt = con.prepareStatement(query);
			
			// パラメータの設定
			pstmt.setString(1, taskId);
			
			int result = pstmt.executeUpdate();
			
			if (result > 0) {
				return true;
			}
			
			con.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			System.out.println("データ更新失敗");
		}
		return false;
	}
	
	// ユーザ情報の登録を行う
	public boolean insUserInfo(String name, String mail, String password) {
		ConnectionManger connectionManager = new ConnectionManger(_url, _User, _Password);
		try(Connection con = connectionManager.getConnection();)
		{
			String query = UtilityTools.readFile("/sql/insertUserInfo.sql");
			
			if (query == null) {
				System.out.println("SQLファイルの読み込みに失敗しました。");
				return false;
			}
			
			PreparedStatement pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, name);
			pstmt.setString(2, mail);
			pstmt.setString(3, password);
			
			int result = pstmt.executeUpdate();
			
			if (result > 0) {
				return true;
			}
			
			con.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			System.out.println("データ登録失敗");
			return false;
		}
		return false;
		
	}
}
