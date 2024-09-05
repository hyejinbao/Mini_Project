package project.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDAO {

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String username = "c##java";
	private String password = "1234";

	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private static UserDAO UserDAO = new UserDAO();

	public static UserDAO getInstance() {
		return UserDAO;
	}

	private UserDAO() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void getConnection() {
		try {
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insertUser(UserDTO userDTO) {
		int result = 0;
		getConnection();
		try {
			String sql = "INSERT INTO USER_WEB (ID, PASSWORD, EMAIL, NAME, PHONE) VALUES (?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userDTO.getId());
			pstmt.setString(2, userDTO.getPassword());
			pstmt.setString(3, userDTO.getEmail());
			pstmt.setString(4, userDTO.getName());
			pstmt.setString(5, userDTO.getPhone());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public boolean loginUser(String id, String password) {
	   boolean loginok = false;
		try {
			getConnection();

			String sql = "SELECT * FROM USER_WEB WHERE ID = ? and Password =?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);

			rs = pstmt.executeQuery();

			if (rs.next()) {
			     loginok = true;  	
			}else{
				 loginok = false;  	
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close(); 	
				if (rs!= null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return loginok;
	}

}