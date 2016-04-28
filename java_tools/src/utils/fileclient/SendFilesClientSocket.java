package utils.fileclient;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 客户端发起连接
 * 
 * @author wangghb
 * 
 */
public class SendFilesClientSocket {
	
	private String ip;
	private int port;
	private Socket socket = null;
	@SuppressWarnings("unused")
	private final int TIMEOUT = 5*1000;
	private final String SPILT = "&";
	
	DataOutputStream out = null;
	DataInputStream getMessageStream = null;
	
	public SendFilesClientSocket(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	/**
	 * 发送文件
	 * 
	 * @param filename
	 * @throws IOException
	 */
	public void sendFile(String filename,FileServerModel fileServer) throws IOException {
		out = new DataOutputStream(socket.getOutputStream());// 获取输出流
		// 向输出流中写入文件
		DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)));
		
		//这里把用户名和密码放中间了，格式：saveDirPath/username/password/filename
		StringBuffer sb=new StringBuffer(fileServer.getSaveDirPath());
		sb.append(SPILT);
		sb.append(fileServer.getUsername());
		sb.append(SPILT);
		sb.append(fileServer.getPassword());
		sb.append(SPILT);
		sb.append(fileServer.getFileName());
		
		out.writeUTF(sb.toString());
		out.flush();
		int bufferSize = 8192;
		byte[] buf = new byte[bufferSize];
		while (true) {
			int read = 0;
			if (fis != null) {
				read = fis.read(buf);
			}
			if (read == -1) {
				break;
			}
			out.write(buf, 0, read);
		}
		out.flush();
		// 关闭资源
		out.close();
		fis.close();
		shutDownConnection();
	}
	
	/**
	 * 创建socket连接
	 */
	public void CreateConnection() throws Exception {
		try {
			socket = new Socket(ip, port);
			socket.setSoTimeout(5000);
		} catch (Exception e) {
			System.out.println(e.getMessage()+e.getStackTrace());
			if (socket != null)
				socket.close();
			throw e;
		} finally {
		}
	}

	public void shutDownConnection() {
		try {
			if (out != null)
				out.close();
			if (getMessageStream != null)
				getMessageStream.close();
			if (socket != null){
				socket.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage()+e.getStackTrace());
		}
	}
}
