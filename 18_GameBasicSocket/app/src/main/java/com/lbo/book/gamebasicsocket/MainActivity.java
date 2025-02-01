package com.lbo.book.gamebasicsocket;

import android.content.Context;
import android.os.Handler;
import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;


public class MainActivity extends Activity {
    Context mContext;
    // 서버 IP
    String strServerIp = "";
    // 서버가보낸문자
    String strServerSendText = "";
    // 클라이언트가 받은 문자
    String strClientSendText = "";
    // 받은문자
    String strReceiveText = "";
    // 받은 총문자
    String strTotReceiveText= "";
    // 임의의 서버포트
    int strServerPort= 10876;
    // 서버소켓
    ServerSocket serverSocket = null;
    // 서버로부터 받은 연결 소켓
    Socket socket = null;
    // 클라이언트 소켓
    Socket clientSocket = null;
    // 이벤트 핸들러
    Handler mHandler = null;
    // 서버로부터응답이 왔을 경우 이벤트
    final static int RECEIVE_FROM_SERVER = 1000;
    // 클라이언트로부터 응답이 왔을 경우 이벤트
    final static int RECEIVE_FROM_CLIENT = 1001;
    // 각 컨트롤
    EditText etvServerIp;
    EditText etvServerSendText;
    EditText etvClientSendText;
    TextView txtReceiveText;
    // 서버가 응답받을 준비.
    boolean isReadySever=false;
    // 클라이언트가 응답받을 준비
    boolean isReadyClient =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        Button btnServerCreate =
                (Button)findViewById(R.id.button_create);
        Button btnConnect =
                (Button)findViewById(R.id.button_connect);
        Button btnServerSend =
                (Button)findViewById(R.id.button_send);
        Button btnClientSend =
                (Button)findViewById(R.id.button_send2);
        Button btnClose =
                (Button)findViewById(R.id.button_close);
        etvServerIp =
                (EditText)findViewById(R.id.editText_ip);
        etvServerSendText =
                (EditText)findViewById(R.id.editText_send);
        etvClientSendText =
                (EditText)findViewById(R.id.editText_send2);
        txtReceiveText =
                (TextView)findViewById(R.id.textView_receive);
        etvServerIp.setText("");
        // 서버소켓 생성
        btnServerCreate.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        createSocket();
                    }
                }
        );
        // 클라이언트소켓생성및 연결
        btnConnect.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        // 서버연결
                        connectSocket();
                    }
                }
        );
        // 서버에서보내기
        btnServerSend.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                strServerSendText =
                        etvServerSendText.getText().toString();
                byte[] temp = strServerSendText.getBytes();
                int len = temp.length;
                DecimalFormat df = new DecimalFormat("000");
                String strLen = df.format(len);

                sendFromServer(strLen + strServerSendText);
                Log.e("", "보냈음S");
            }
        });
        // 클라이언트에서 보내기
        btnClientSend.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                strClientSendText =
                        etvClientSendText.getText().toString();
                byte[] temp = strClientSendText.getBytes();
                int len = temp.length;
                DecimalFormat df = new DecimalFormat("000");
                String strLen = df.format(len);
                sendFromClient(strLen + strClientSendText);
                Log.e("", "보냈음C");
            }
        });

        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case RECEIVE_FROM_SERVER:
                        receiveFromServer();
                        break;
                    case RECEIVE_FROM_CLIENT:
                        receiveFromClient();
                        break;
                }
            }
        };

        // 서버에서 데이터응답을 기다리는 이벤트 관리 쓰레드
        Thread receiveThread = new Thread(){
            public void run(){
                while(true) {
                    try {
                        if( isReadySever == true) {
                            InputStream in = socket.getInputStream();
                            byte arrlen[] = new byte[3];
                            in.read(arrlen);
                            String strLen = new String(arrlen);
                            int len = Integer.parseInt(strLen);
                            byte arrcont[] = new byte[len];
                            in.read(arrcont);
                            String strArrCont = new String(arrcont);
                            strReceiveText = strLen + strArrCont;
                            mHandler.sendMessage(Message.obtain(
                                    mHandler, RECEIVE_FROM_SERVER));
                        }
                    }
                    catch(Exception exTemp) {
                        Log.e("", exTemp.toString());
                    }
                }
            }
        };
        receiveThread.start();
        // 클라이언트에서 데이터응답을 기다리는 이벤트 관리 쓰레드
        Thread receiveClientThread = new Thread(){
            public void run(){
                while(true) {
                    try {
                        if( isReadyClient == true) {
                            InputStream in =
                                    clientSocket.getInputStream();
                            byte arrlen[] = new byte[3];
                            in.read(arrlen);
                            String strLen = new String(arrlen);
                            int len = Integer.parseInt(strLen);
                            byte arrcont[] = new byte[len];
                            in.read(arrcont);
                            String strArrCont = new String(arrcont);
                            strReceiveText = strLen + strArrCont;
                            mHandler.sendMessage(Message.obtain(
                                    mHandler, RECEIVE_FROM_CLIENT));
                        }
                    }
                    catch(Exception exTemp) {
                        Log.e("", exTemp.toString());
                    }
                }
            }
        };
        receiveClientThread.start();
    }

    public void createSocket(){
        Toast toast = Toast.makeText(mContext,"소캣을 생성합니다.", Toast.LENGTH_SHORT);
        toast.show();
        Thread acceptThread = new Thread(){
            public void run(){
                try {
                    serverSocket = new ServerSocket( strServerPort );
                    socket = serverSocket.accept();
                    Log.e("", "Accepted");
                    isReadySever= true;
                }
                catch(Exception exSocket) {
                    Log.e("", exSocket.toString());
                }
            }
        };
        acceptThread.start();
    }
    public void connectSocket(){
        Thread thread1 = new Thread() {
            public void run() {
                try {
                    strServerIp = etvServerIp.getText().toString();
                    clientSocket = new Socket(strServerIp, strServerPort);
                    isReadyClient = true;
                }
                catch(Exception exSocket) {
                    // 소켓오류
                    Log.e("socket error",exSocket.toString());
                }
            }
        };
        thread1.start();
    }
    public void sendFromServer(String input) {
        try{
            Log.e("socket","보낸문자:" + input);
            BufferedWriter out= new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            out.write(input);
            out.flush();
            Toast toast = Toast.makeText(this,"서버에서 문자를 보냄",Toast.LENGTH_SHORT);
            toast.show();
        }
        catch(Exception exTemp) {
            Log.e("", "error" + exTemp.toString());
        }
    }
    public void sendFromClient(String input) {
        try {
            Log.e("socket","보낸문자:" + input);
            BufferedWriter out= new BufferedWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream()));
            out.write(input);
            out.flush();
            Toast toast = Toast.makeText(this,
                    "클라이언트에서 문자를 보냄",Toast.LENGTH_SHORT);
            toast.show();
        }
        catch(Exception exTemp) {
            Log.e("", "error" + exTemp.toString());
        }
    }
    public void receiveFromServer(){
        Log.e("", "receiveFrom Server");
        strTotReceiveText = strTotReceiveText +
                "\nCLINET:" + strReceiveText;
        txtReceiveText.setText(strTotReceiveText);
    }
    public void receiveFromClient(){
        Log.e("", "receiveFrom Client");
        strTotReceiveText = strTotReceiveText +
                "\nSERVER:" + strReceiveText;
        txtReceiveText.setText(strTotReceiveText);
    }
}
