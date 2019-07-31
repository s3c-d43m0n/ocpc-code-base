/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ritvik.ocpc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ritvikc
 */
class Config {

    static ArrayList<String> getServerList(Launcher l) {
        ArrayList<String> list = new ArrayList<String>();
        File configFile = new File(System.getProperty("user.home"), ".ocpc.lst");
        if(configFile.exists()){
            try {
                BufferedReader r = new BufferedReader(new FileReader(configFile));
                String line = r.readLine();
                while(line!=null){
                    if(line.length()>0) list.add(line);
                    line=r.readLine();
                }
                r.close();
            } catch (FileNotFoundException ex) {
                //We already are in block of file exiast block. No need to handle                
            } catch (IOException ex) {
                ex.printStackTrace();
            }            
        }
        else{
            try {
                configFile.createNewFile();
                JOptionPane.showMessageDialog(l, "Please Add Unix/Linux Server in Table List.", "No Server Found in config", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(l, "Unable to save configuration in:\n"+System.getProperty("user.home"), "Error in Saving File", JOptionPane.ERROR_MESSAGE);
            }
        }
        return list;
    }

    static void addServerToList(String vServer) throws IOException {
        File configFile = new File(System.getProperty("user.home"), ".ocpc.lst");
        FileWriter w = new FileWriter(configFile, true);
        w.write(vServer+"\n");
        w.flush();
        w.close();
    }

    static void removeServer(String vServer,Launcher l) {
        HashSet <String> list = new HashSet<>();
        File configFile = new File(System.getProperty("user.home"), ".ocpc.lst");
        if(configFile.exists()){
            try {
                BufferedReader r = new BufferedReader(new FileReader(configFile));
                String line = r.readLine();
                while(line!=null){
                    if(line.length()>0){
                        list.add(line);
                    }
                    line=r.readLine();
                }
                r.close();
            } catch (FileNotFoundException ex) {
                //We already are in block of file exiast block. No need to handle                
            } catch (IOException ex) {
                ex.printStackTrace();
            }            
        }
        else{
            try {
                configFile.createNewFile();
                JOptionPane.showMessageDialog(l, "Please Add Unix/Linux Server in Table List.", "No Server Found in config", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(l, "Unable to save configuration in:\n"+System.getProperty("user.home"), "Error in Saving File", JOptionPane.ERROR_MESSAGE);
            }
        }  
        
        list.remove(vServer);
        try {
            FileWriter w = new FileWriter(configFile);
            for(String s : list){
                w.write(s+"\n");
            }
            w.flush();
            w.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(l, "Unable to save configuration in:\n"+System.getProperty("user.home"), "Error in Saving File", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
}
