/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pruebaslocas.Model;

import java.io.File;

/**
 *
 * @author lgser
 */
public class ChapterManager {
    
    public static Boolean alreadyExist(String path){
        File tempFile = new File(path);
        return tempFile.exists();

    }
    
}
