/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pruebaslocas.Controller;

import com.mycompany.pruebaslocas.Model.ChapterManager;
import java.io.File;
import java.net.URL;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author lgser
 */
public class ChapterThread extends Thread{
    private String chapterLink;
    private ChapterUtils cu;
    
    public ChapterThread(URL baseUrl,String chapterLink){
        cu = new ChapterUtils(baseUrl);
        this.chapterLink= chapterLink;
    }
    
    public void run() {
        String name = chapterLink.substring(0, chapterLink.length()-1);
        if(!ChapterManager.alreadyExist(name+".txt")){
                List<String> chapter = cu.getChapter(name);
                ChapterManager.Guardar(name+".txt", chapter);//tambien necesitamos pasarlo a un pdf bonito que si den ganas de leer xD
                ChapterManager.Guardar(name+"_traslated.txt", cu.translate("en", "es", chapter));
                System.out.println(name);
        }
    }
}
