package codewifi.annotation.config;

import java.util.List;

interface IRedisProperties {

	List<String> getNodes();

	void setNodes(final List<String> nodes);

	String getMode();

	void setMode(final String mode);

	int getConnectionPoolSize();

	void setConnectionPoolSize(final int connectionPoolSize);

	int getConnectTimeout();

	void setConnectTimeout(final int connectTimeout);

	int getResponseTimeout();

	void setResponseTimeout(final int responseTimeout);

}
