package utils.fileclient;

import utils.ResultMessage;

/**
 * 客户端发送文件
 * @author wangghb
 *
 */
public class SendFiles {
    private SendFilesClientSocket cs = null;
    
    private boolean createConnection(String ip, int port) {
        cs = new SendFilesClientSocket(ip, port);
        try {
            cs.CreateConnection();
            System.out.println("客户端：连接服务器成功!" + "\n");
            return true;
        } catch (Exception e) {
        	System.out.println("客户端：连接服务器失败!" + "\n"+e.getMessage()+e.getStackTrace());
            return false;
        }
    }
    
    // 实现请求并接收
    public void sendFile(String filename, FileServerModel fileServer) {
        if (this.createConnection(fileServer.getHost(),fileServer.getPort())) {
        	if (cs == null)
                return;
            try {
                cs.sendFile(filename,fileServer);
            } catch (Exception e) {
            	System.out.println("客户端：发送消息失败!" + "\n");
            }finally{
            	
            }
        }
    }
    
    public ResultMessage send(String clientPath,String saveDirPath,String fileName){
    	ResultMessage message = config(saveDirPath,fileName);
    	if(message.message==null && "".equals(message.message)){
    		return message;
    	}
    	FileServerModel fileServer = (FileServerModel)message.data;
    	SendFiles ct = new SendFiles();
        ct.sendFile(clientPath,fileServer);
        return message;
    }
    
    private ResultMessage config(String saveDirPath,String fileName){
    	ResultMessage message=new ResultMessage();
    	try{
    		FileServerModel server=new FileServerModel();
    		String host="fileserver.host";
    		int post=Integer.valueOf("fileserver.port");
    		
    		server.setHost(host);
    		server.setPort(post);
    		server.setUsername("username");
    		server.setPassword("password");
    		server.setSaveDirPath(saveDirPath);
    		server.setFileName(fileName);
    		message.data = server;
    	}catch(Exception e){
    		System.out.println("客户端：文件服务器配置不正确");
    		message.message = "客户端：文件服务器配置不正确";
    	}
    	
    	return message;
    }
}
