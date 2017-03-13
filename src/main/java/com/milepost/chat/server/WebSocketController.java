package com.milepost.chat.server;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/webSocket")
public class WebSocketController {

	@RequestMapping("/getOnLineCount")
	@ResponseBody
	public Map<String, String> getOnLineCount(){
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("onLineCount", WebSocketServer.getOnLineCount() + "");
		return resultMap;
	}
}
