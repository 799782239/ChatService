package com.yQ.testmysetversocket.main;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ChatManager {

	private ChatManager() {
	}

	private static final ChatManager cm = new ChatManager();

	public static ChatManager getChatManager() {
		return cm;
	}

	List<User> users = new ArrayList<User>();

	public void add(Socket socket, String name) {
		User user = new User();
		user.setName(name);
		user.setSocket(socket);
		users.add(user);
	}

	public Socket publish(String name) {
		for (int i = 0; i < users.size(); i++) {
			String send =users.get(i).getName();
			if (send.equals(name)) {
				Socket socket = users.get(i).getSocket();
				return socket;
			}
		}
		return null;
	}
}
