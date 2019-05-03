// Libs
#include <ESP8266WiFi.h>
#include <PubSubClient.h>

#define ESP12_LED 2 // GPIO2, D4
// Vars
const char* SSID = "veron"; // rede wifi
const char* PASSWORD = "32621920v"; // senha da rede wifi

const char* BROKER_MQTT = "m24.cloudmqtt.com"; // ip/host do broker
int BROKER_PORT = 13304; // porta do broker

// prototypes
void initPins();

void initSerial();
void initWiFi();
void initMQTT();

WiFiClient espClient;
PubSubClient MQTT(espClient); // instancia o mqtt

// setup
void setup() {
  pinMode(ESP12_LED, OUTPUT);     // Initialize the LED_BUILTIN pin as an output
  
  digitalWrite(ESP12_LED, HIGH);  
  
  initPins();
  initSerial();
  initWiFi();
  initMQTT();
}

void loop() {
  
  if (!MQTT.connected()) {
    Serial.println("MQTT Reconnect");
    reconnectMQTT();
  }

  
  recconectWiFi();
  MQTT.loop();
}

// implementacao dos prototypes

void initPins() {
  //pinMode(D5, OUTPUT);
  //digitalWrite(D5, 0);
}

void initSerial() {
  Serial.begin(115200);
}
void initWiFi() {
  delay(10);
  Serial.println("Conectando-se em: " + String(SSID));

  WiFi.begin(SSID, PASSWORD);
  while (WiFi.status() != WL_CONNECTED) {
    delay(100);
    Serial.print(".");
  }
  Serial.println();
  Serial.print("Conectado na Rede " + String(SSID) + " | IP => ");
  Serial.println(WiFi.localIP());
}

// Funcão para se conectar ao Broker MQTT
void initMQTT() {
  MQTT.setServer(BROKER_MQTT, BROKER_PORT);
  MQTT.setCallback(mqtt_callback);
}

//Função que recebe as mensagens publicadas
void mqtt_callback(char* topic, byte* payload, unsigned int length) {

  String message;
  for (int i = 0; i < length; i++) {
    char c = (char)payload[i];
    message += c;
  }
  Serial.println("Topico => " + String(topic) + " | Valor => " + String(message));
  if (message =="1") {
    //digitalWrite(D5, 1);
    Serial.println("led ON");
    digitalWrite(ESP12_LED, LOW);
      delay(1000);  
  } else {
    //digitalWrite(D5, 0);
    
    Serial.println("led OFF");
    digitalWrite(ESP12_LED, HIGH); 
    delay(1000);  
  }
  Serial.flush();
}

void reconnectMQTT() {
  while (!MQTT.connected()) {
    Serial.println("Tentando se conectar ao Broker MQTT: " + String(BROKER_MQTT));
    if (MQTT.connect("test2", "rsyzxphj", "-BPL9reAeaZ-")) {
      Serial.println("Conectado");
      MQTT.subscribe("led");

    } else {
      Serial.println("Falha ao Reconectar");
      Serial.println("Tentando se reconectar em 2 segundos");
      delay(2000);
    }
  }
}

void recconectWiFi() {
  while (WiFi.status() != WL_CONNECTED) {
    delay(100);
    Serial.print(".");
  }
}
