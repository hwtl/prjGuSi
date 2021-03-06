package com.gusi.boms.util;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import com.gusi.boms.common.Configuration;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.util
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-05-13 15:32)
 * Description: To change this template use File | Settings | File Templates.
 */
public class SSHDooiooUtils {
    private Connection conn ;
    private SCPClient scpClient;
    private static SSHDooiooUtils sshUtils;
    public synchronized static SSHDooiooUtils getInstance(){
        if(sshUtils==null){
            sshUtils = new SSHDooiooUtils();
            sshUtils.createConnection();
        }
        return sshUtils;
    }


    public SCPClient getScpClient() {
        return scpClient;
    }

    public void setScpClient(SCPClient scpClient) {
        this.scpClient = scpClient;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public  void createConnection(){
      Configuration configuration = Configuration.getInstance();
       this.setConn(new Connection(configuration.getDooiooConnection()));
        try {
            conn.connect();
            boolean isAuthenticated = this.getConn().authenticateWithPassword(configuration.getDooiooSshUser(), configuration.getDooiooSshPwd());
            if (!isAuthenticated){
                System.out.println( "连接失败");
            }
            this.setScpClient(new SCPClient(this.getConn()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SyncLocalTo130(String resourcePath,String tofile){
        try {
            this.getScpClient().put(resourcePath,tofile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
