<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="project.bean.UserDAO" %>
<%@ page import="project.bean.UserDTO" %>

<%
    request.setCharacterEncoding("UTF-8");
    String userId = request.getParameter("user_id");
    String password = request.getParameter("user_password");

    UserDAO userDAO = new UserDAO();
    UserDTO userDTO = userDAO.getUserById(userId);

    if (userDTO != null) {
        String storedPassword = userDTO.getPassword(); // 저장된 비밀번호

        // 비밀번호 검증 로직 (예: 비밀번호가 해시된 경우 검증)
        if (storedPassword.equals(password)) { // 단순 비교, 해시된 비밀번호를 사용할 경우 적절한 해시 비교로 수정
            response.getWriter().write("success");
        } else {
            response.getWriter().write("fail");
        }
    } else {
        response.getWriter().write("fail");
    }
%>