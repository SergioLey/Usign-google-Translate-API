/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pruebaslocas;

import com.mycompany.pruebaslocas.Controller.ChapterUtils;
import com.mycompany.pruebaslocas.Model.ChapterManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;


/**
 *
 * @author lgser
 */

// /api/novels/${bookSlug}/chapters?page=${parseInt(page)}
// https://readnovelfull.org/api/novels/my-vampire-system/chapters?page=2
//"https://readnovelfull.org/my-vampire-system"
public class Main {
    
    public static void main(String[] args) throws MalformedURLException, IOException  {
        URL baseUrl = new URL("https://readnovelfull.org");
        Integer noPages = ChapterUtils.getNoPages(baseUrl);
        ChapterUtils cu = new ChapterUtils(baseUrl);
        for (int i = 1; i <= noPages; i++) {
            List<String> chaptersinPage = cu.getChaptersinPage(i);
            for (String chapterLink : chaptersinPage) {//esta es la parte que tenemos que hacer hilos
                String name = chapterLink.substring(0, chapterLink.length()-1);
                if(!ChapterManager.alreadyExist(name+".txt")){
                    List<String> chapter = cu.getChapter(name);
                    FileUtils.writeLines(new File(name+".txt"), chapter);//tambien necesitamos pasarlo a un pdf bonito que si den ganas de leer xD
                    FileUtils.writeLines(new File(name+"_traslated.txt"), cu.translate("en", "es", chapter));
                    System.out.println(name);
                }
                
            }
        }

//        List<String> chapter = getChapter(baseUrl,"/my-vampire-system/chapter-32-a-lesson");
//        FileUtils.writeLines(new File("/my-vampire-system/chapter-32-a-lesson.txt"), chapter);

//        chapter.forEach(next -> {
//            System.out.println(next);
////            System.out.println(translate("en", "es",next));
//        });

    }
    
    

}
