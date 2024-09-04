<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.sql.*, project.bean.UserDAO" %>

<%
    request.setCharacterEncoding("UTF-8");
    
    // 하드코딩된 사용자 ID
    String userId = "pubao"; 
    String title = request.getParameter("post_title");
    String content = request.getParameter("post_content");

    // 입력값 검증
    if (title == null || content == null || title.trim().isEmpty() || content.trim().isEmpty()) {
        out.print("Invalid input");
        return;
    }

    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
        UserDAO dao = new UserDAO();
        conn = dao.getConnection();
        String sql = "INSERT INTO BOARD_WEB (USER_ID, SUBJECT, CONTENT, HIT, LOGTIME) VALUES (?, ?, ?, 0, SYSDATE)";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, userId);
        pstmt.setString(2, title);
        pstmt.setString(3, content);

        int result = pstmt.executeUpdate();

        if (result > 0) {
            out.print("success");
        } else {
            out.print("fail");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        out.print("error");
    } finally {
        // 자원 해제
        try {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
%>
