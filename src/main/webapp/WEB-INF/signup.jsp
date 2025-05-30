<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>QuickDo：Sign Up</title>
		<link rel="stylesheet" type="text/css" href="css/style.css">
		<script type="text/javascript" src="js/signupScript.js"></script>
	</head>
	<body class="sign-in-locate">
		<form id="signupForm">
		    <h2 class="login-title">QuickDo</h2>
	        <label for="name">User Name</label>
	        <br>
	        <input type="text" id="name" name="name" class="login-controller" required="required">
	        <br>
	        <label for="mail">Email</label>
	        <br>
	        <input type="email" id="mail" name="mail" class="login-controller" required="required">
	        <br>
	        <label for="password">Password</label>
	        <br>
	        <input type="password" id="password" name="password" class="login-controller" required="required">
	        <br>
	        <c:set var="errorMessage" value="${requestScope.error}"/>
	        <c:choose>
	            <c:when test="${errorMessage != null}">
	                <p class="error">${errorMessage}</p>
	            </c:when>
	        </c:choose>
	        <input type="submit" class="login-btn" value="Sign Up"><br>
	    </form>
        <a href="login">Return to the login screen</a>
	</body>
</html>