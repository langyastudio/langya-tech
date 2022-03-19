package com.langyastudio.io;

import java.io.File;
import java.util.*;

/**
 * @author langyastudio
 * @date 2022年03月19日
 */
public class FileMain
{
    public static void main(String[] args)
    {

    }

    //按日期排序
    public static void orderByDate(String fliePath)
    {
        File   file = new File(fliePath);
        File[] fs   = file.listFiles();
        Arrays.sort(fs, new Comparator<File>()
        {
            @Override
            public int compare(File f1, File f2)
            {
                long diff = f1.lastModified() - f2.lastModified();
                if (diff > 0)
                {
                    return 1;
                }
                else if (diff == 0)
                {
                    return 0;
                }
                else
                {
                    return -1;
                }
            }

            @Override
            public boolean equals(Object obj)
            {
                return true;
            }

        });
    }

    //按照长度排序
    public static void orderByLength(String fliePath)
    {
        List<File> files = Arrays.asList(new File(fliePath).listFiles());
        Collections.sort(files, new Comparator<File>()
        {
            @Override
            public int compare(File f1, File f2)
            {
                long diff = f1.length() - f2.length();
                if (diff > 0)
                {
                    return 1;
                }
                else if (diff == 0)
                {
                    return 0;
                }
                else
                {
                    return -1;
                }
            }

            @Override
            public boolean equals(Object obj)
            {
                return true;
            }
        });
    }

    //按照文件名称排序
    public static void orderByName(String fliePath)
    {
        List<File> files = Arrays.asList(new File(fliePath).listFiles());
        Collections.sort(files, new Comparator<File>()
        {
            @Override
            public int compare(File o1, File o2)
            {
                if (o1.isDirectory() && o2.isFile())
                {
                    return -1;
                }
                if (o1.isFile() && o2.isDirectory())
                {
                    return 1;
                }
                return o1.getName().compareTo(o2.getName());
            }
        });
    }
}
