package com.qa.api.mocking;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

public class WireMockSetup {
	
	private static WireMockServer server;

	public static void createWireMockServer() {
		server = new WireMockServer(8089);
		WireMock.configureFor("localhost", 8089);
		server.start();
	}

	public static void stopWireMockServer() {
		server.stop();
	}

}


