<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>QuickDo</title>
		<link rel="stylesheet" type="text/css" href="css/style.css">
	</head>
	<body class="sign-in-locate">
		<form action="login" method="Post">
			<h2 class="login-title">QuickDo</h2>
			<label for="mail">Email</input>
			<br>
			<input type="text" id="mail" class="login-controller" name="mail" required="required">
			<br>
			<label for="password">Password</input>
			<br>
			<input type="text" id="password" class="login-controller" name="password" required="required">
			<br>
			<c:if test="${not empty requestScope.error}">
                <c:set var="errorMessageRequest" value="${requestScope.error}"/>
            </c:if>
            <c:if test="${not empty sessionScope.error}">
				<c:set var="errorMessageSession" value="${sessionScope.error}"/>
			</c:if>
			<c:choose>
				<c:when test="${errorMessageRequest != null}">
					<p class="error">${errorMessageRequest}</p>
					<c:remove var="error" scope="request"/>
				</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${errorMessageSession != null}">
					<p class="error">${errorMessageSession}</p>
					<%session.invalidate();%>
				</c:when>
			</c:choose>
			<input type="submit" class="login-btn" value="Sign In">
			<br>
			Don't have an account? <a href="sign-up">Sign Up</a>
		</form>
	</body>
</html>