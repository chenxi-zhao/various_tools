package utils.fileclient;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * 客户端接收文件，无此需求，故暂未使用、未优化、未测试，保留
 * @author wangghb
 *
 */
public class ReceiveFilesClientSocket {
	private String ip;
	private int port;
	private Socket socket = null;
	DataOutputStream out = null;
	DataInputStream getMessageStream = null;

	public ReceiveFilesClientSocket(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	/** */
	/**
	 * socket
	 * 
	 * @throws Exception
	 *             exception
	 */
	public void CreateConnection() throws Exception {
		try {
			socket = new Socket(ip, port);
		} catch (Exception e) {
			System.out.println(e.getMessage()+e.getStackTrace());
			if (socket != null)
				socket.close();
			throw e;
		} finally {
		}
	}

	public void sendMessage(String sendMessage) throws Exception {
		try {
			out = new DataOutputStream(socket.getOutputStream());
			if (sendMessage.equals("Windows")) {
				out.writeByte(0x1);
				out.flush();
				return;
			}
			if (sendMessage.equals("Unix")) {
				out.writeByte(0x2);
				out.flush();
				return;
			}
			if (sendMessage.equals("Linux")) {
				out.writeByte(0x3);
				out.flush();
			} else {
				out.writeUTF(sendMessage);
				out.flush();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage()+e.getStackTrace());
			if (out != null)
				out.close();
			throw e;
		} finally {
		}
	}

	public DataInputStream getMessageStream() throws Exception {
		try {
			getMessageStream = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			return getMessageStream;
		} catch (Exception e) {
			System.out.println(e.getMessage()+e.getStackTrace());
			if (getMessageStream != null)
				getMessageStream.close();
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
			if (socket != null)
				socket.close();
		} catch (Exception e) {
			System.out.println(e.getMessage()+e.getStackTrace());
		}
	}
}