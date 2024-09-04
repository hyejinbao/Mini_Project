<%@ page contentType="application/json; charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%@ page import="project.bean.UserDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.google.gson.*" %>


<%
    UserDAO dao = new UserDAO();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ArrayList<HashMap<String, Object>> postList = new ArrayList<>();

    try {
        conn = dao.getConnection();
        String sql = "SELECT SEQ, SUBJECT, HIT, TO_CHAR(LOGTIME, 'YYYY-MM-DD HH24:MI:SS') AS LOGTIME FROM BOARD_WEB ORDER BY SEQ DESC";
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            HashMap<String, Object> post = new HashMap<>();
            post.put("seq", rs.getInt("SEQ"));
            post.put("title", rs.getString("SUBJECT"));
            post.put("hit", rs.getInt("HIT"));
            post.put("date", rs.getString("LOGTIME"));
            postList.add(post);
        }

        Gson gson = new Gson();
        String json = gson.toJson(postList);
        out.print(json);
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        dao.close(rs, pstmt, conn);
    }
%>
