package controller;

import java.io.BufferedReader;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import model.dao.TaskManagementDAO;
import model.entity.UserInfoBeans;

@WebServlet("/sign-up")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public SignUpServlet() {
    	super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/signup.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		try {
			TaskManagementDAO dao = new TaskManagementDAO();
			BufferedReader reqReader = request.getReader();
			StringBuilder jsonBuilder = new StringBuilder();
			String line;
			
			// TODO: レスポンスでアカウント登録の失敗やアカウント登録済みのエラーメッセージを返すようにする
			if (reqReader == null) {
				response.sendRedirect("login");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			
			while ((line = reqReader.readLine()) != null) {
				jsonBuilder.append(line);
			}
	        JSONObject json = new JSONObject(jsonBuilder.toString());
	        
	        // アカウントが既に登録されているか確認
	        UserInfoBeans userInfo = dao.revUserSertificate(json.getString("mail"), json.getString("password"));
	        if (userInfo != null) {
	        	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        	return;
	        }
	        
	        // アカウントの登録を行う
			Boolean insRes = dao.insUserInfo(
					json.getString("name"), 
					json.getString("mail"),
					json.getString("password")
					);
			
			if (insRes) {
				response.setStatus(HttpServletResponse.SC_OK);
				return;
			}
			else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
	}
}
