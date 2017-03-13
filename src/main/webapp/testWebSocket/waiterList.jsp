<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="com.milepost.chat.server.WebSocketServer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <title>客服列表</title>
</head>
<body>
	<div>
		<a href="${ctx }/testWebSocket/index.jsp?from=waiter1" target="_blank">客服一</a><br>
		<a href="${ctx }/testWebSocket/index.jsp?from=waiter2" target="_blank">客服二</a><br>
		<a href="${ctx }/testWebSocket/index.jsp?from=waiter3" target="_blank">客服三</a><br>
	</div>    
</body>
</html>