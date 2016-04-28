package utils.fileclient;

/**
 * 文件服务Model
 * @author wangghb
 *
 */
public class FileServerModel {
	
	/**
	 * 主机IP/Url地址
	 */
	private String host;
	
	/**
	 * 主机端口号
	 */
	private int port;
	
	/**
	 * socket传输的用户名，保留字段
	 */
	private String username;
	
	/**
	 * socket传输的密码：保留字段
	 */
	private String password;
	
	/**
	 * 保存的相对路径
	 */
	private String saveDirPath;
	
	/**
	 * 文件名
	 */
	private String fileName;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSaveDirPath() {
		return saveDirPath;
	}
	public void setSaveDirPath(String saveDirPath) {
		this.saveDirPath = saveDirPath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
