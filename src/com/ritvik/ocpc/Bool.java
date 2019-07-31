/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ritvik.ocpc;

/**
 *
 * @author ritvikc
 */
public class Bool {
    Boolean x;
    Bool(){
        x=false;
    }
    
    void markComplete(){
        x=true;
    }
    
    Boolean getStatus(){
        return x;
    }
}
