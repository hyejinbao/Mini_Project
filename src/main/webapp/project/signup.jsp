<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="project.bean.UserDAO" %>
<%@ page import="project.bean.UserDTO" %>
<%
    request.setCharacterEncoding("UTF-8");

  
    String userId = request.getParameter("signup_user_id");
    String password = request.getParameter("signup_user_password");
    String email = request.getParameter("email");
    String name = request.getParameter("name");
    String phone = request.getParameter("phone");


    UserDTO userDTO = new UserDTO();
    userDTO.setId(userId);
    userDTO.setPassword(password);
    userDTO.setEmail(email);
    userDTO.setName(name);
    userDTO.setPhone(phone);

    // DAO를 통해 데이터베이스에 사용자 정보 삽입
    UserDAO userDAO = new UserDAO();
    int result = userDAO.insertUser(userDTO);

    // 결과에 따른 페이지 이동 처리
    if (result > 0) {
        response.getWriter().write("success");
    } else {
        response.getWriter().write("fail");
    }
%>