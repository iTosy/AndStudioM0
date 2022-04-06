package com.example.myapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    EditText e1;
    TextView e2;
    Button send;
    Button connect;
    Button terminate;
    DataOutputStream dos;
    DataInputStream dis;
    Socket s;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        send = (Button) findViewById(R.id.send);
        e1=(EditText) findViewById(R.id.editText);
        e2=(TextView) findViewById(R.id.rec);
        connect = (Button)  findViewById(R.id.connect);
        terminate = (Button)  findViewById(R.id.ter);


        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread tc = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            s =new Socket("192.168.43.173",9990);

                        }catch(UnknownHostException h){
                            h.printStackTrace();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                tc.start();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = e1.getText().toString().trim();
                Thread t = new Thread(new Runnable(){

                    @Override
                    public void run() {
                        try {

                            dos = new DataOutputStream(s.getOutputStream());
                            dos.writeUTF(message);
                            dos.flush();
                            dis = new DataInputStream(s.getInputStream());
                            String received = dis.readUTF();
                            e2.setText(received);
                            dos.close();
                            dis.close();
                        }catch(EOFException e){
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                t.start();
            }
        });

        terminate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread tt = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            dos = new DataOutputStream(s.getOutputStream());
                            dos.writeUTF("tosy");
                            dos.flush();
                            dos.close();
                            s.close();

                        }catch(UnknownHostException h){
                            h.printStackTrace();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                tt.start();
            }
        });
    }
}