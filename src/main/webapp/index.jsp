<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.slf4j.*" %>
<% 
	Logger logger = LoggerFactory.getLogger(this.getClass());
	logger.debug("jsp页面输出的日志");    
%>   
<html>
<body> 
<h2>Hello World!</h2>
<a href="employee/testLogger">Test Logger</a>
</body> 
</html>       
     