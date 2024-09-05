package project.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String username = "c##java";
	private String password = "1234";

	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private static BoardDAO BoardDAO = new BoardDAO();

	public static BoardDAO getInstance() {
		return BoardDAO;
	}

	private BoardDAO() {
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
	
	//게시글 추가 insert 
	public int writeBoard(BoardDTO boardDTO) {
		int result =0;
		getConnection();
		try {
			String sql = "insert into board_web(?,?,?,?,?,sysdate)";
		
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, boardDTO.getSeq());
		pstmt.setString(2, boardDTO.getUserId());  
		pstmt.setString(3, boardDTO.getSubject()); 
		pstmt.setString(4, boardDTO.getContent()); 
	
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

	//게시글 조회 select
	public List<BoardDTO> selectAll() {
		List<BoardDTO> all = new ArrayList<>();
		getConnection();
		try {
				String sql = "select * from board_web";
				pstmt = con.prepareStatement(sql);
			
				rs = pstmt.executeQuery();

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
			return all;
		}
	
	
	
	}



