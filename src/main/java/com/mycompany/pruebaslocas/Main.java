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
import java.util.Comparator;
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
        List<String> chaptersinPage = ChapterUtils.getChaptersinPage(baseUrl);
        Comparator<String> nameComparator = (h1, h2) -> {
            String[] s1 = h1.split("-");
            String[] s2 = h2.split("-");
            
                Integer n1= Integer.parseInt(s1[3]);
                Integer n2= Integer.parseInt(s2[3]);
                return n1.compareTo(n2);};
        chaptersinPage.sort(nameComparator);
        Integer i = (int) chaptersinPage.size()/100;
        for (int j = 0; j <= i; j++) {//.skip(100*i).limit(100)
            int k= chaptersinPage.size()<(j+1)*100 ?  chaptersinPage.size() : (j+1)*100;
            Stream<String> stream = StreamSupport.stream(chaptersinPage.subList(j*100, k).spliterator(), true);
              stream.forEach(chapterLink -> {
              ChapterThread t = new ChapterThread(baseUrl, chapterLink);
              t.getChapter();
        });
        }
        
    }
    
    

}
