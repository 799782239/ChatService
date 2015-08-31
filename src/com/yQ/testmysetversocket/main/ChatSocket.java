package com.yQ.testmysetversocket.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import net.sf.json.JSONObject;

public class ChatSocket extends Thread {

	Socket socket;

	public ChatSocket(Socket s) {
		this.socket = s;
	}

	public void out(String out, Socket socket) {
		try {
			socket.getOutputStream().write((out + "\n").getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		out("你已经连接到本服务器了", socket);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					socket.getInputStream(), "UTF-8"));
			String line = null;
			// 在这个循环里循环接听
			while ((line = br.readLine()) != null) {
				JSONObject obj = JSONObject.fromObject(line);
				if ((obj.getString("state")).equals("add")) {
					ChatManager.getChatManager().add(socket, obj.getString("Id"));
				} else if ((obj.getString("state")).equals("chat")) {
					String content=obj.getString("content");
					System.out.println(content);
					System.out.println(obj.getString("send"));
					Socket sockets = ChatManager.getChatManager().publish(obj.getString("send"));
					if(sockets==null){
						sockets=socket;
						out("对方不在线", sockets);
					}
					else{
						out(content,sockets);
					}
					
				}
			}
			br.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
