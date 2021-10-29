/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pruebaslocas.Controller;

import com.mycompany.pruebaslocas.Main;
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
import org.apache.commons.io.IOUtils;

/**
 *
 * @author lgser
 */
public class ChapterUtils {
    final static private String NOVELA = "my-vampire-system"; 
    final static private String TRANSALTE_URL = "https://script.google.com/macros/s/AKfycbwHSM-dVGMeu6VileKHamXLxf_U_qeyMNqov4thMMGzucDczqDw/exec"; 

    private URL baseUrl;

    public ChapterUtils(URL baseUrl) {
        if(baseUrl==null){
            throw new Error("No url");
        }
        this.baseUrl=baseUrl;
    }
    
    public static Integer getNoPages(URL baseUrl) throws MalformedURLException, IOException{
        URL url = new URL(baseUrl,NOVELA);
            URLConnection con = url.openConnection();
            String body;
            InputStream in = con.getInputStream();
            String encoding = con.getContentEncoding();  
            encoding = encoding == null ? "UTF-8" : encoding;
            body = IOUtils.toString(in, encoding);
            in.close();

            for(String s : body.lines().toList()){
                if(s.contains("Go to page")){
                    String aux = s.substring(s.indexOf("1"));
                    aux= aux.substring(4,aux.indexOf(')'));
                    return Integer.parseInt(aux.trim());
                }
            }
            return null;
    }
    
    public static List<String> getChaptersinPage(URL baseUrl,Integer page) throws MalformedURLException, IOException{
        URL url = new URL(baseUrl,"/api/novels/"+NOVELA+"/chapters?page="+page);
            URLConnection con = url.openConnection();
            String body;
            InputStream in = con.getInputStream();
            String encoding = con.getContentEncoding();  
            encoding = encoding == null ? "UTF-8" : encoding;
            body = IOUtils.toString(in, encoding);
            in.close();

            List<String> chapters;
            List<String> chaptersLink = new ArrayList<>();
                
            chapters = new LinkedList<String>(Arrays.asList(body.replace("</li>", "").split("<li>")));
            if(!chapters.isEmpty())
                chapters.remove(0);
                    
            chapters.forEach(next -> {chaptersLink.add((next.split("\\s+")[1]).split("\"")[1]); });
            return chaptersLink;
    }
    
//      "https://readnovelfull.org
//      /my-vampire-system/chapter-32-a-lesson"
    public List<String> getChapter(String strURL){
        List<String> chapter = new ArrayList<>();
        try {
            URL url = new URL(baseUrl,strURL);
            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();
            String encoding = con.getContentEncoding();  
            encoding = encoding == null ? "UTF-8" : encoding;
            String body = IOUtils.toString(in, encoding);
            in.close();
            List<String> lines;
            StringBuilder novel = new StringBuilder();
            body.lines().filter(next -> (next.contains("<script src=\"https://translate.google.com/translate_a/element.js?cb=googleTranslateElementInit\" defer></script>"))).forEachOrdered(next -> {
                novel.append(next);
            });
            lines = Arrays.asList(novel.substring(novel.indexOf("<p>")).replace("</p>", "")
                    .replaceAll("<div class=\"ads-banner\"><input type=\"hidden\" name=\"IL_IN_ARTICLE\"></div>", "")
                    .replace("<br><div>", "").split("<p>"));
            lines.forEach(next -> {
                chapter.add(next+System.lineSeparator());
            });
        } catch (MalformedURLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return chapter;
    }
    
    /**
     * 
     * @param langFrom language in the text
     * @param langTo language to translate
     * @param text text to translate
     * @return
     * @throws IOException
     * 
     * Usage translate("en", "es", text)
     */
    private String translate(String langFrom, String langTo, String text) throws IOException {
        
        String urlStr = TRANSALTE_URL +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        InputStream in = con.getInputStream();
        String encoding = con.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        return IOUtils.toString(in, encoding);
    }
    
    public List<String> translate(String langFrom, String langTo, List<String> chapter)  {
         List<String> result = new ArrayList<>();
         for (String string : chapter) {
             if(!string.isEmpty()){
                 try {
                     String  translated =translate(langFrom, langTo, string);
                     //ok thats all.... but what if something just going wrong?
                     Boolean badTranslation =  translated.contains("img alt=\"Google Apps Script\"");//google just send a error
                     int i = 0;
                     while(badTranslation){
                         //just try n times more
                         translated =translate(langFrom, langTo, string);
                         badTranslation =  translated.contains("img alt=\"Google Apps Script\"")||i>3;
                         i++;
                     }
                     result.add(translated.replace("&quot;", "\""));
                 } catch (IOException ex) {
                     Logger.getLogger(ChapterUtils.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
             
         }
         return result;
     }
}
