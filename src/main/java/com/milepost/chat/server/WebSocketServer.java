package com.milepost.chat.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.milepost.system.util.DateUtil;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/websocket")
public class WebSocketServer {
	private static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
	
	//静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	public static int onlineCount = 0;

	public static Map<String, WebSocketServer> fromMap = new HashMap<String, WebSocketServer>();
	public static Map<String, WebSocketServer> targetMap = new HashMap<String, WebSocketServer>();
	
	//与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	
	private String from;
	
	private String target;
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public WebSocketServer() {
		if (logger.isDebugEnabled()) {
			logger.debug("WebSocketServer constructor...");
		}
	}
	
	/**
	 * 连接建立成功调用的方法
	 * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(Session session){
		this.session = session;
		Map<String, List<String>> requestParameterMap = session.getRequestParameterMap();
		String from = requestParameterMap.get("from").get(0);
		String target = requestParameterMap.get("target").get(0);
		this.setFrom(from);
		this.setTarget(target);
		fromMap.put(from, this);
		targetMap.put(target, this);
		if (logger.isDebugEnabled()) {
			logger.debug("有新连接加入！当前在线人数为" + fromMap.size());
		}
		showFromTargetMap();
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose(){
		fromMap.remove(this.getFrom());
		targetMap.remove(this.getTarget());
		if (logger.isDebugEnabled()) {
			logger.debug("有一连接关闭！当前在线人数为" + fromMap.size());
		}
		showFromTargetMap();
	}

	/**
	 * 收到客户端消息后调用的方法
	 * @param message 客户端发送过来的消息
	 * @param session 可选的参数
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		if (logger.isDebugEnabled()) {
			logger.debug("来自客户端的消息:" + message);
		}
		try {
			String time = DateUtil.getNow("yyyy-MM-dd hh:mm:ss") + "<br>";
			String userName = this.getFrom();
			WebSocketServer webSocketServer = fromMap.get(this.getTarget());
			if(webSocketServer == null){
				//目标用户不在线
				webSocketServer = fromMap.get(this.getFrom());
				userName = this.getTarget();
				webSocketServer.sendMessage(time + userName + ":&nbsp;&nbsp;" + "您好，我不在线！");
			}else {
				webSocketServer.sendMessage(time + userName + ":&nbsp;&nbsp;" + message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		showFromTargetMap();
	}

	/**
	 * 发生错误时调用
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error){
		logger.error("发生错误");
		showFromTargetMap();
		error.printStackTrace();
	}

	/**
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException{
		this.session.getBasicRemote().sendText(message);
	}
	public void showFromTargetMap(){
		if (logger.isDebugEnabled()) {
			logger.debug("fromMap:" + fromMap.toString());
			logger.debug("targetMap:" + targetMap.toString());
		}
	}

	@Override
	public String toString() {
		return this.hashCode() + "";
	}
	
	/**
	 * 测试指定用户是否在线
	 * @param userId 用户id
	 * @return
	 */
	public static boolean isOnLine(String userId){
		return fromMap.get(userId)!=null;
	}
	
	/**
	 * 在线人数
	 * @return
	 */
	public static int getOnLineCount(){
		for(Map.Entry<String, WebSocketServer> entry : fromMap.entrySet()){
			if (logger.isDebugEnabled()) {
				logger.debug(entry.getKey() + ";" + entry.getValue());
			}
		}
		return fromMap.size();
	}
	
}
