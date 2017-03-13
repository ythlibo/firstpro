<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="com.milepost.chat.server.WebSocketServer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<%
	String from = request.getParameter("from");
	String target = request.getParameter("target");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Java后端WebSocket的Tomcat实现</title>
</head>
<body>
    Welcome<br/>
    在线用户数：<span id="onLineCount"></span><br>
    我是：<%=from %><br>
    目标：<%=target %>(<%= WebSocketServer.isOnLine(target) %>)<br>
    <input id="text" type="text"/><br>
    <button onclick="send()">发送消息</button>
    <hr/>
    <button onclick="closeWebSocket()">关闭WebSocket连接</button>
    <hr/>
    <div id="message"></div>
</body>
<script type="text/javascript" src="${ctx }/testWebSocket/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
var websocket = null;
//判断当前浏览器是否支持WebSocket
if ('WebSocket' in window) {
    websocket = new WebSocket("ws://localhost:8080/firstpro/websocket?from=<%=from%>&target=<%=target%>");
}else {
    alert('当前浏览器 Not support websocket')
}

//连接发生错误的回调方法
websocket.onerror = function () {
    setMessageInnerHTML("WebSocket连接发生错误");
};

//连接成功建立的回调方法
websocket.onopen = function () {
    setMessageInnerHTML("WebSocket连接成功");
    $(function(){
    	$.getJSON("${ctx }/webSocket/getOnLineCount", function(data){
    		$('#onLineCount').text(data.onLineCount);
    	});
    })
}

//接收到消息的回调方法
websocket.onmessage = function (event) {
    setMessageInnerHTML(event.data);
}

//连接关闭的回调方法
websocket.onclose = function () {
    setMessageInnerHTML("WebSocket连接关闭");
}

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function () {
    closeWebSocket();
}

//将消息显示在网页上
function setMessageInnerHTML(innerHTML) {
    document.getElementById('message').innerHTML += innerHTML + '<br/>';
}

//关闭WebSocket连接
function closeWebSocket() {
    websocket.close();
}

//发送消息
function send() {
    var message = document.getElementById('text').value;
    websocket.send(message);
}
    
</script>
</html>