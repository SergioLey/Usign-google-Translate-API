/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pruebaslocas.Controller;

import com.mycompany.pruebaslocas.Model.ChapterManager;
import java.net.URL;
import java.util.List;

/**
 *
 * @author lgser
 */
public class ChapterThread{
    private String chapterLink;
    private ChapterUtils cu;
    
    public ChapterThread(URL baseUrl,String chapterLink){
        cu = new ChapterUtils(baseUrl);
        this.chapterLink= chapterLink;
    }
    
    public void getChapter() {
        String name = chapterLink.substring(0, chapterLink.length()-1);
        if(!ChapterManager.alreadyExist(name+".txt")){
                List<String> chapter = cu.getChapter(name);
                ChapterManager.Guardar(name+".txt", chapter);//tambien necesitamos pasarlo a un pdf bonito que si den ganas de leer xD
                ChapterManager.Guardar(name+"_traslated.txt", cu.translate("en", "es", chapter));
                System.out.println(name);
        }
    }
}
