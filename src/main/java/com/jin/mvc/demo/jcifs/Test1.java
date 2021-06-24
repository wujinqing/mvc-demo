package com.jin.mvc.demo.jcifs;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author wu.jinqing
 * @date 2021年04月15日
 */
public class Test1 {

    //                                      "smb://share:admin@192.168.135.11/sharedFolder/"
    public static final String smbMachine = "smb://10.66.29.239/APP_LOG_stage/172.28.98.119/mutms.log";
    public static void main(String[] args) throws Exception {
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("","wujinqing", "wjq@1301");
        SmbFile rmifile = new SmbFile(smbMachine, auth);

        BufferedInputStream is = new BufferedInputStream(new SmbFileInputStream(rmifile));

        InputStreamReader isr = new InputStreamReader(is);

        BufferedReader br = new BufferedReader(isr);

        String buf;

        while (null != (buf = br.readLine()))
        {
            System.out.println(buf);
        }

        is.close();
        isr.close();
        br.close();

    }
}
