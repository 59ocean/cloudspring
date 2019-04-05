package com.ocean.fastdfs.conn;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fastdfs")
public class FastdfsProperties {
	private String trackerServers;
	private String charset;
	private int connectTimeout;
	private int networkTimeout;
	private boolean httpAntiStealToken = false;
	private String httpSecretKey;
	private int httpTrackerHttpPort;
	private int port;

	public String getTrackerServers() {
		return trackerServers;
	}

	public void setTrackerServers(String trackerServers) {
		this.trackerServers = trackerServers;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getNetworkTimeout() {
		return networkTimeout;
	}

	public void setNetworkTimeout(int networkTimeout) {
		this.networkTimeout = networkTimeout;
	}

	public boolean isHttpAntiStealToken() {
		return httpAntiStealToken;
	}

	public void setHttpAntiStealToken(boolean httpAntiStealToken) {
		this.httpAntiStealToken = httpAntiStealToken;
	}

	public String getHttpSecretKey() {
		return httpSecretKey;
	}

	public void setHttpSecretKey(String httpSecretKey) {
		this.httpSecretKey = httpSecretKey;
	}

	public int getHttpTrackerHttpPort() {
		return httpTrackerHttpPort;
	}

	public void setHttpTrackerHttpPort(int httpTrackerHttpPort) {
		this.httpTrackerHttpPort = httpTrackerHttpPort;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
