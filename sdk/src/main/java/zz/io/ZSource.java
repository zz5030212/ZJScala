package zz.io;

import java.io.*;

import zz.collection.ZArray;
import zz.collection.mutable.ZLinkedList;

public class ZSource {
    public static class io {
        public static ZArray<String> linesFromFile(File file) {
            return linesFromFile(file,"utf-8");
        }
        public static ZArray<String> linesFromFile( File file,String charSet) {
            if(null == file || !file.isFile())
                return new ZArray<String>(0);
            FileInputStream fr = null;
            InputStreamReader isr = null;
            BufferedReader br = null;
            try {
                fr = new FileInputStream(file);
                isr = new InputStreamReader(fr,charSet);
                br = new BufferedReader(isr);
                String line ;
                ZLinkedList<String> r = new ZLinkedList<String>();
                while(null !=(line=br.readLine())) {
                    r.adQ(line);
                }
                return r.toArray();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(null != br)
                        br.close();
                    if(null != isr)
                        isr.close();
                    if(null != fr)
                        fr.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return new ZArray<String>(0);
        }
        public static boolean linesToFile(ZArray<String> lines, File file) {
            return linesToFile(lines,file,"utf-8");
        }
        public static boolean linesToFile(ZArray<String> lines, File file,String charSet) {
            if(null == lines)
                return false;
            if(!file.getParentFile().exists()) {
                if(!file.getParentFile().mkdirs()) {
                    return false;
                }
            }
            try {
                PrintWriter pw = new PrintWriter(file,charSet);
                for(String line : lines) {
                    pw.println(line);
                }
                pw.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

}
