/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ritvik.ocpc;

import com.trilead.ssh2.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ritvikc
 */
public class Server {

    String vHostname;
    int vPort;
    String vUsername;
    String vOldPassword;
    String vNewPassword;
    
    public Server(String vServerString, String xUsername, String xOldPass, String xNewPass) {
        String [] vServer = vServerString.split(":");
        vHostname = vServer[0];
        if(vServer.length == 1){            
            vPort = 22;
        }
        else{
            vPort = Integer.parseInt(vServer[1]);
        }
        vUsername = xUsername;
        vNewPassword = xNewPass;
        vOldPassword = xOldPass;
    }

    String changePassword() {
        String vReturn =  "Error Occured";
        //System.out.println(vOldPassword+"\n"+vNewPassword);

        try {
            Connection c = new Connection(vHostname, vPort);
            c.connect();
            if(c.authenticateWithPassword(vUsername, vOldPassword)){
                Session s = c.openSession();
                s.requestDumbPTY();
                
                //command to execute
                s.execCommand("passwd");
                
                //output and input
                BufferedReader o = new BufferedReader(new InputStreamReader(s.getStdout()));
                BufferedWriter i = new BufferedWriter(new OutputStreamWriter(s.getStdin()));                
                
                Bool oo=new Bool(), ii=new Bool();
                
                //special charecters for escaping
                vNewPassword=vNewPassword.replace("~", "\\~");
                vNewPassword=vNewPassword.replace("!", "\\!");
                vNewPassword=vNewPassword.replace("@", "\\@");
                vNewPassword=vNewPassword.replace("#", "\\#");
                vNewPassword=vNewPassword.replace("$", "\\$");
                vNewPassword=vNewPassword.replace("%", "\\%");
                vNewPassword=vNewPassword.replace("^", "\\^");
                vNewPassword=vNewPassword.replace("&", "\\&");
                vNewPassword=vNewPassword.replace("*", "\\*");
                vNewPassword=vNewPassword.replace("-", "\\-");
                vNewPassword=vNewPassword.replace("+", "\\+");
                vNewPassword=vNewPassword.replace("_", "\\_");
                vNewPassword=vNewPassword.replace("=", "\\=");
                vNewPassword=vNewPassword.replace("?", "\\?");
                vNewPassword=vNewPassword.replace(":", "\\:");
                
                vOldPassword=vOldPassword.replace("~", "\\~");
                vOldPassword=vOldPassword.replace("!", "\\!");
                vOldPassword=vOldPassword.replace("@", "\\@");
                vOldPassword=vOldPassword.replace("#", "\\#");
                vOldPassword=vOldPassword.replace("$", "\\$");
                vOldPassword=vOldPassword.replace("%", "\\%");
                vOldPassword=vOldPassword.replace("^", "\\^");
                vOldPassword=vOldPassword.replace("&", "\\&");
                vOldPassword=vOldPassword.replace("*", "\\*");
                vOldPassword=vOldPassword.replace("-", "\\-");
                vOldPassword=vOldPassword.replace("+", "\\+");
                vOldPassword=vOldPassword.replace("_", "\\_");
                vOldPassword=vOldPassword.replace("=", "\\=");
                vOldPassword=vOldPassword.replace("?", "\\?");
                vOldPassword=vOldPassword.replace(":", "\\:");
                
                new Thread(()->{
                    try{
                        try {Thread.sleep(1000);} catch (InterruptedException ex) {}
                        i.write(vOldPassword+"\n");
                        i.flush();
                        try {Thread.sleep(1000);} catch (InterruptedException ex) {}
                        i.write(vNewPassword+"\n");
                        i.flush();
                        try {Thread.sleep(1000);} catch (InterruptedException ex) {}
                        i.write(vNewPassword+"\n");
                        i.flush(); 
                        try {Thread.sleep(1000);} catch (InterruptedException ex) {}
                        try{
                            i.write(vNewPassword+"\n");
                            i.flush();                        
                            try {Thread.sleep(1000);} catch (InterruptedException ex) {}
                        }
                        catch(Exception exx){}
                        ii.markComplete();
                    }
                    catch(IOException ex){
                        ex.printStackTrace();
                    }
                }).start();                
                
                String line = "";
                String line2 = "";
                while(line!=null){
                    line = o.readLine();
                    if(line!=null){
                        System.out.println(line);
                        line2=line;
                    }
                } 
                oo.markComplete();
                
                while(ii.getStatus() && oo.getStatus()){
                    //Waiting for threads to complete
                }
                                           
                o.close();
                i.close();  
                s.close();
                
                System.out.println(s.getExitSignal());
                
                try{
                    if(s.getExitStatus()==0){
                        vReturn = "Success";
                    }
                    else{
                        vReturn = line2.split("passwd: ")[1];
                    }
                }
                catch(Exception ex){
                    ex.printStackTrace();
                    vReturn = ex.getLocalizedMessage();
                }
            }
            else{
                
                vReturn = "Invalid Old Credentials";
            }
            
        } catch (IOException ex) {
            vReturn =ex.getMessage();
        }
        return vReturn;
    }
    
    
    
}
