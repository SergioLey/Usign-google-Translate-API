/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pruebaslocas;

import com.mycompany.pruebaslocas.Controller.ChapterThread;
import com.mycompany.pruebaslocas.Controller.ChapterUtils;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


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
        
        for (int i = 1; i <= noPages; i++) {
            List<String> chaptersinPage = ChapterUtils.getChaptersinPage(baseUrl,i);
            Stream<String> stream = StreamSupport.stream(chaptersinPage.spliterator(), true);
            stream.forEach(chapterLink -> {
                ChapterThread t = new ChapterThread(baseUrl, chapterLink);
                t.getChapter();
            });
        }

//        List<String> chapter = getChapter(baseUrl,"/my-vampire-system/chapter-32-a-lesson");
//        FileUtils.writeLines(new File("/my-vampire-system/chapter-32-a-lesson.txt"), chapter);

//        chapter.forEach(next -> {
//            System.out.println(next);
////            System.out.println(translate("en", "es",next));
//        });

    }
    
    

}
