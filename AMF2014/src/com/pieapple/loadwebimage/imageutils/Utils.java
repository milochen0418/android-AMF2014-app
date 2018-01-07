package com.pieapple.loadwebimage.imageutils;

import java.io.InputStream;
import java.io.OutputStream;

public class Utils {
    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int mBufferSize=1024;
        try
        {
            byte[] bytes=new byte[mBufferSize];
            for(;;)
            {
              int count=is.read(bytes, 0, mBufferSize);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
}