package codewifi.annotation.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "redis.api")
public class ApiRedisProperties implements IRedisProperties {

	/**
	 * Redis nodes
	 */
	private List<String> nodes;

	/**
	 * Redis running mode, currently supports "elastic" && "single"
	 */
	private String mode;

	private int connectionPoolSize;

	private int responseTimeout;

	private int connectTimeout;

	private String pwd;

	public List<String> getNodes() {
		return nodes;
	}

	public void setNodes(final List<String> nodes) {
		this.nodes = nodes;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(final String mode) {
		this.mode = mode;
	}

	public int getConnectionPoolSize() {
		return connectionPoolSize;
	}

	public void setConnectionPoolSize(final int connectionPoolSize) {
		this.connectionPoolSize = connectionPoolSize;
	}

	public int getResponseTimeout() {
		return responseTimeout;
	}

	public void setResponseTimeout(final int responseTimeout) {
		this.responseTimeout = responseTimeout;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(final int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
