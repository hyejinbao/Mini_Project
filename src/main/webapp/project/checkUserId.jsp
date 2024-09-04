<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="project.bean.UserDAO" %>
<%@ page import="project.bean.UserDTO" %>

<%
    request.setCharacterEncoding("UTF-8");
    String userId = request.getParameter("signup_user_id");

    UserDAO userDAO = new UserDAO();
    UserDTO user = userDAO.getUserById(userId);

    if (user == null) {
        // 사용 가능한 아이디
        response.getWriter().write("available");
    } else {
        // 이미 사용 중인 아이디
        response.getWriter().write("unavailable");
    }
%>