package serveur.host;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.Socket;

public class ClientProcessor implements Runnable {

    private Socket client; // The client we are treating
    private PrintWriter out; // to send message to the client
    private BufferedReader in; // to receive message from the client
    private String toSend = "";    // The response that will be send to the client
    private Gson gson;


    public ClientProcessor(Socket client) throws IOException {
        this.client = client;
        this.out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
        this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        gson = new GsonBuilder().create();
    }

    public void run() {

        while (true) {

            try {

                String received;

                if (in.ready()) {
                    received = gson.fromJson(in.readLine(), String.class);

                    if (received == null) client.close();

                    switch (received.toLowerCase()) { //Lorsque le client demande l'envoie, on lui affiche les profils

                        case "envoie":

                            System.out.println("Commande envoie detectee");
                            toSend = DAO.getProfil(); //using DAO for a better factorization

                            out.write(gson.toJson(toSend, String.class));
                            out.write("\n");
                            out.flush();
                            break;

                        case "close":
                            System.err.println("Commande close detectee");
                            gson.toJson("La connexion va s'arreter", out);
                            out.flush();
                            in.close();
                            out.close();
                            client.close();
                            break;
                    }
                    toSend = "";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
