package com.example.nodemcucommunication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;




public class MainActivity extends AppCompatActivity {
    TextView tvIp,tvValueBox;;
    Button btnRice,btnWheat,btnCotton,btnP1On,btnP1Off,btnP2On,btnP2Off;
    private PrintWriter output;
    private BufferedReader input;
    boolean flagActive = false;
    Toolbar mToolbar;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    TextView email;
    String share_email, firstname, lastname;
    ImageButton mLogout;
    View hview;
    ActionBarDrawerToggle toggle;
    void widgetInitialize (){
        tvIp = (TextView)findViewById(R.id.tvIp);
        tvValueBox = (TextView) findViewById(R.id.tvValueBox);
        btnRice = (Button)findViewById(R.id.btnRice);
        btnWheat = (Button)findViewById(R.id.btnWheat);
        btnCotton = (Button)findViewById(R.id.btnCotton);
        btnP1On = (Button)findViewById(R.id.btnp1On);
        btnP1Off = (Button)findViewById(R.id.btnp1Off);
        btnP2On = (Button)findViewById(R.id.btnp2On);
        btnP2Off = (Button)findViewById(R.id.btnp2Off);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        widgetInitialize ();
        getDeviceIpAddress();
        threadActivation();
        riceButton ();
        wheatButton();
        cottonButton();
        p1OnButton();
        p1OffButton();
        p2OnButton();
        p2OffButton();
        init();
        setDrawerLayout();
        setUpToolbar();
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.like:
                        Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.fb:
                        Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.email:
                        Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                        break;
                }

                return false;
            }
        });
    }
// connect to view//
    private void init() {
        mToolbar = findViewById(R.id.toolbar);
        mDrawerLayout = findViewById(R.id.drawlayout);
        mNavigationView = findViewById(R.id.navi);
    }
    // set toolbar
    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
    }

    // set bar toggle button
    private void setDrawerLayout() {
        toggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

/****************************************/
    public void threadActivation() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Socket socket;
                //while (true) {
                try {
                    socket = new Socket("192.168.4.1", 5560);
                    output = new PrintWriter(socket.getOutputStream());
                    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    flagActive = true;
                    //output.write("Connected");
                    //output.flush();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dataReceiveAndDisplay();
                } catch (IOException e) {
                    e.printStackTrace();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            flagActive = false;
                        }
                    });

                }
            }
        }).start();
    }
    /*********************************************/
    public void dataReceiveAndDisplay() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                try {
                    final String message = input.readLine();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvValueBox.setText(message);
                           // Toast.makeText(getApplicationContext(), "RCV", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                catch (IOException e) {
                    e.printStackTrace();

                }
              }
            }
        }).start();
    }
    /****************************************/

    public void riceButton (){
        btnRice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (flagActive == true){
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                                output.write("R");
                                output.flush();

                        }
                    }).start();
               }
            }
        });
    }

    public void wheatButton (){
        btnWheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (flagActive == true){
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            output.write("W");
                            output.flush();

                        }
                    }).start();
                }

            }
        });
    }

    public void cottonButton (){
        btnCotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (flagActive == true){
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            output.write("C");
                            output.flush();

                        }
                    }).start();
                }

            }
        });
    }

    public void p1OnButton (){
        btnP1On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (flagActive == true){
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            output.write("A");
                            output.flush();

                        }
                    }).start();
                }
            }
        });
    }

    public void p1OffButton (){
        btnP1Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (flagActive == true){
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            output.write("B");
                            output.flush();

                        }
                    }).start();
                }
            }
        });
    }

    public void p2OnButton (){
        btnP2On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (flagActive == true){
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            output.write("G");
                            output.flush();

                        }
                    }).start();
                }
            }
        });
    }

    public void p2OffButton (){
        btnP2Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (flagActive == true){
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            output.write("D");
                            output.flush();

                        }
                    }).start();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Connect again
    }
    @Override
    protected void onStart() {
        super.onStart();
        // Connect here
    }
    public void getDeviceIpAddress() {
        try {
            //Loop through all the network interface devices
            for (Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
                 enumeration.hasMoreElements();) {
                NetworkInterface networkInterface = enumeration.nextElement();
                //Loop through all the ip addresses of the network interface devices
                for (Enumeration<InetAddress> enumerationIpAddr = networkInterface.getInetAddresses(); enumerationIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumerationIpAddr.nextElement();
                    //Filter out loopback address and other irrelevant ip addresses
                    if (!inetAddress.isLoopbackAddress() && inetAddress.getAddress().length == 4) {
                        //tvIp.setText(inetAddress.getHostName());
                        //Print the device ip address in to the text view
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //viewVisibitliyVisible();
                                //updateUI();
                                tvIp.setText("IP: "+inetAddress.getHostAddress());

                            }
                        });
                        // tvServerIP.setText(inetAddress.getHostAddress());
                        // VIEW_IP.setText(inetAddress.getHostAddress());
                        // VIEW_PORT.setText(Integer.toString(SERVER_PORT));
                        //Toast.makeText(getApplicationContext(), "ip", Toast.LENGTH_SHORT).show();



                    }
                }

            }
        } catch (SocketException e) {
            Log.e("ERROR:", e.toString());
            Toast.makeText(getApplicationContext(), "Error for ip", Toast.LENGTH_SHORT).show();

        }
    }
}