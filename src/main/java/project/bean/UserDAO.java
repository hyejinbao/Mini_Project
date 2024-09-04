package project.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDAO {
 private Connection conn;
 private PreparedStatement pstmt;
 private ResultSet rs;

 // 데이터베이스 연결 메서드
 public Connection getConnection() {
	 Connection conn = null;
     try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "C##JAVA", "1234");
     } catch (Exception e) {
         e.printStackTrace();
     }
     return conn;
 
 }

 //private void close(AutoCloseable... resources) {
 
 public void close(AutoCloseable... resources) {
     try {
         for (AutoCloseable resource : resources) {
             if (resource != null) {
                 resource.close();
             }
         }
     } catch (Exception e) {
         e.printStackTrace();
     }
 }

 // 회원가입 메서드
 public int insertUser(UserDTO userDTO) {
     int result = 0;
     try {
         conn = getConnection();
         conn.setAutoCommit(false); // 자동 커밋 비활성화

         String sql = "INSERT INTO USER_WEB (ID, PASSWORD, EMAIL, NAME, PHONE) VALUES (?, ?, ?, ?, ?)";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, userDTO.getId());
         pstmt.setString(2, userDTO.getPassword()); 
         pstmt.setString(3, userDTO.getEmail());
         pstmt.setString(4, userDTO.getName());
         pstmt.setString(5, userDTO.getPhone());

         result = pstmt.executeUpdate();
         conn.commit(); // 트랜잭션 커밋

     } catch (SQLException e) {
         try {
             if (conn != null) {
                 conn.rollback(); // 예외 발생 시 롤백
             }
         } catch (SQLException rollbackEx) {
             rollbackEx.printStackTrace();
         }
         e.printStackTrace();
     } finally {
         close(pstmt, conn); // 자원 해제
     }

     return result;
 }

 // 유저 검색 메서드 (예: 로그인 시 사용)
 public UserDTO getUserById(String id) {
     UserDTO user = null;
     try {
         conn = getConnection();

         String sql = "SELECT * FROM USER_WEB WHERE ID = ?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, id);

         rs = pstmt.executeQuery();

         if (rs.next()) {
             user = new UserDTO();
             user.setId(rs.getString("ID"));
             user.setPassword(rs.getString("PASSWORD"));
             user.setName(rs.getString("NAME"));
             user.setEmail(rs.getString("EMAIL"));
             user.setPhone(rs.getString("PHONE"));
        //     user.setCreatedAt(rs.getDate("CREATED_AT"));
         }

     } catch (SQLException e) {
         e.printStackTrace();
     } finally {
         close(rs, pstmt, conn); // 자원 해제
     }

     return user;
 }

 // 유저 삭제 메서드
 public int deleteUser(String id) {
     int result = 0;
     try {
         conn = getConnection();
         conn.setAutoCommit(false);

         String sql = "DELETE FROM USER_WEB WHERE ID = ?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, id);

         result = pstmt.executeUpdate();
         conn.commit();

     } catch (SQLException e) {
         try {
             if (conn != null) {
                 conn.rollback();
             }
         } catch (SQLException rollbackEx) {
             rollbackEx.printStackTrace();
         }
         e.printStackTrace();
     } finally {
         close(pstmt, conn);
     }

     return result;
 }
}

 // 자원 해제 메서드
	/*
	 * private void close() { try { if (rs != null) rs.close(); if (pstmt != null)
	 * pstmt.close(); if (conn != null) conn.close(); } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */
 
 
 


