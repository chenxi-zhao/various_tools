package utils.fileclient;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;

/**
 * 客户端接收文件，无此需求，故暂未使用、未优化、未测试，保留
 * @author wangghb
 *
 */
public class ReceiveFiles {
	private ReceiveFilesClientSocket cs = null;
	private String ip = "localhost";// 设置成服务器IP 
	private int port = 8821;
	private String sendMessage = "Windwos";
	
	public ReceiveFiles() {
		try {
			if (createConnection()) {
				sendMessage();
				getMessage();
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage()+ex.getStackTrace());
		}
	}

	private boolean createConnection() {
		cs = new ReceiveFilesClientSocket(ip, port);
		try {
			cs.CreateConnection();
			System.out.println("连接服务器成功!" + "\n");
			return true;
		} catch (Exception e) {
			System.out.println("连接服务器失败!" +e.getMessage()+e.getStackTrace()+ "\n");
			return false;
		}
	}

	private void sendMessage() {
		if (cs == null)
			return;
		try {
			cs.sendMessage(sendMessage);
		} catch (Exception e) {
			System.out.println("发送消息失败!" +e.getMessage()+e.getStackTrace()+ "\n");
		}
	}

	private void getMessage() {
		if (cs == null)
			return;
		DataInputStream inputStream = null;
		try {
			inputStream = cs.getMessageStream();
		} catch (Exception e) {
			System.out.println("接收消息缓存错误\n"+e.getMessage()+e.getStackTrace());
			return;
		}
		try {
			// 本地保存路径，文件名会自动从服务器端继承而来。
			String savePath = "E:\\";
			int bufferSize = 8192;
			byte[] buf = new byte[bufferSize];
			int passedlen = 0;
			long len = 0;

			savePath += inputStream.readUTF();
			DataOutputStream fileOut = new DataOutputStream(
					new BufferedOutputStream(new BufferedOutputStream(
							new FileOutputStream(savePath))));
			len = inputStream.readLong();

			System.out.println("文件的长度为:" + len + "\n");
			System.out.println("开始接收文件!" + "\n");

			while (true) {
				int read = 0;
				if (inputStream != null) {
					read = inputStream.read(buf);
				}
				passedlen += read;
				if (read == -1) {
					break;
				}
				// 下面进度条本为图形界面的prograssBar做的，这里如果是打文件，可能会重复打印出一些相同的百分比
				System.out.println("文件接收了" + (passedlen * 100 / len) + "%\n");
				fileOut.write(buf, 0, read);
			}
			System.out.println("接收完成，文件存为" + savePath + "\n");
			fileOut.close();
		} catch (Exception e) {
			System.out.println("接收消息错误" + "\n"+e.getMessage()+e.getStackTrace());
			return;
		}
	}
	
	
//	public static void main(String arg[]) {
//		new ClientTest();
//		SendFilesTest test=new SendFilesTest();
//		test.test("");
//	}
}
