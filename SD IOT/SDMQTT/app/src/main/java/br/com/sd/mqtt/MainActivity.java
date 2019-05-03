package br.com.sd.mqtt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MainActivity extends AppCompatActivity implements MqttCallback {

    private ImageView imageViewLed;
    boolean onOff = false;
    private String topic = "led";
    private MqttClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewLed = findViewById(R.id.imageViewLed);
        imageViewLed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (onOff){
                    onOff = false;
                    try {
                        sendMessage("0");
                        imageViewLed.setBackground(getResources().getDrawable(R.drawable.led_off));
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }else {
                    onOff = true;
                    try {
                        sendMessage("1");
                        imageViewLed.setBackground(getResources().getDrawable(R.drawable.led_on));
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
            }
        });



        new Thread(){
            public void run(){
                con();
            }
        }.start();

    }

    public void sendMessage(String payload) throws MqttException {
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(1);
        this.client.publish(this.topic, message); // Blocking publish
    }

    public void con(){
        String host = "tcp://m24.cloudmqtt.com:13304";
        String username = "rsyzxphj";
        String password = "-BPL9reAeaZ-";
        String clientId = "MQTT-Java-Example";


        MqttConnectOptions conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(true);
        conOpt.setUserName(username);
        conOpt.setPassword(password.toCharArray());

        try {
            this.client = new MqttClient(host, clientId, new MemoryPersistence());
            this.client.setCallback(this);
            this.client.connect(conOpt);
        } catch (MqttException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
