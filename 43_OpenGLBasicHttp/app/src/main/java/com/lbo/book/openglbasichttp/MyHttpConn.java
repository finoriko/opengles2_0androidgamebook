package com.lbo.book.openglbasichttp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class MyHttpConn extends Thread {
    String strHttpReturn;
    String strCheck = "NONE";
    private String strConnectionUrl;
    public MyHttpConn(String strUrl){
        this.strConnectionUrl = strUrl;
    }
    public void run(){

        HttpURLConnection connection = null;
        try {
            // 요청 URL (예를 들어 제 홈페이지로)
            URL url = new URL(strConnectionUrl);
            connection = (HttpURLConnection)url.openConnection();
            // 요청 방식 (GET or POST)
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            // 요청 응답 타임아웃 설정
            connection.setConnectTimeout(3000);
            // 읽기 타임아웃 설정
            connection.setReadTimeout(3000);
            connection.connect();
            // 컨텐츠의 캐릭터셋이 euc-kr 이라면 (connection.getInputStream(), "euc-kr")
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            StringBuffer buffer = new StringBuffer();
            int read = 0;
            char[] cbuff = new char[1024];
            while ((read = reader.read(cbuff)) > 0) {
                buffer.append(cbuff, 0, read);
            }
            reader.close();
            strHttpReturn = buffer.toString();
            strCheck = "OK";
            Log.e("", "----------------1...............");
        } catch (Exception e) {
            strCheck = "ERROR";
            Log.e("", "----------------...............");
            Log.e("", e.toString());
            e.printStackTrace();
            //Toast.makeText(mMainContext, "서버에 접속할 수 없습니다.",5000);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        //Log.e("", strHttpReturn);
    }
    public String getValue(){
        int count = 0;
        while(true){
            if(strCheck.equals("NONE")){
                if(count > 5){
                    strHttpReturn = "TIMEOUT";
                    break;
                }
                try{
                    Thread.sleep(1000);
                    count++;
                }catch(Exception ex){}
            }
            else if(strCheck.equals("OK")){
                break;
            }
            else if(strCheck.equals("ERROR")){
                strHttpReturn = "ERROR";
                break;
            }
        }
        return strHttpReturn;
    }


}