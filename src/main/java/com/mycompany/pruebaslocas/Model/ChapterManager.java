/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pruebaslocas.Model;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author lgser
 */
public class ChapterManager {
    
    public static Boolean alreadyExist(String path){
        File tempFile = new File(path);
        return tempFile.exists();

    }
    
    public static void Guardar(String name, List<String> chapter){
        try {
            FileUtils.writeLines(new File(name), chapter);
        } catch (IOException ex) {
            Logger.getLogger(ChapterManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
