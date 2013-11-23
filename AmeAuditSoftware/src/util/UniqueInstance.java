package util;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;
 
/**
 * Cette classe permet d'assurer l'unicit� de l'instance de l'application. Deux applications ne peuvent pas �tre lanc�es
 * simultan�ment. Voici un exemple typique d'utilisation :
 * 
 * <pre>
 * // Port � utiliser pour communiquer avec l'instance de l'application lanc�e.
 * final int PORT = 32145;
 * // Message � envoyer � l'application lanc�e lorsqu'une autre instance essaye de d�marrer.
 * final String MESSAGE = &quot;nomDeMonApplication&quot;;
 * // Actions � effectuer lorsqu'une autre instance essaye de d�marrer.
 * final Runnable RUN_ON_RECEIVE = new Runnable() {
 *     public void run() {
 *         if(mainFrame != null) {
 *             // Si la fen�tre n'est pas visible (uniquement dans le systray par exemple), on la rend visible.
 *             if(!mainFrame.isVisible())
 *                 mainFrame.setVisible(true);
 *             // On demande � la mettre au premier plan.
 *             mainFrame.toFront();
 *         }
 *     }                   
 * });
 *                 
 * UniqueInstance uniqueInstance = new UniqueInstance(PORT, MESSAGE, RUN_ON_RECEIVE);
 * // Si aucune autre instance n'est lanc�e...
 * if(uniqueInstance.launch()) {
 *     // On d�marre l'application.
 *     new MonApplication();
 * }
 * </pre>
 * 
 * @author rom1v
 */
public class UniqueInstance {
 
    /** Port d'�coute utilis� pour l'unique instance de l'application. */
    private final int port;
 
    /** Message � envoyer � l'�ventuelle application d�j� lanc�e. */
    private final String message;
 
    /** Actions � effectuer lorsqu'une autre instance de l'application a indiqu� qu'elle avait essay� de d�marrer. */
    private final Runnable runOnReceive;
 
    /**
     * Cr�er un gestionnaire d'instance unique de l'application.
     * 
     * @param port
     *            Port d'�coute utilis� pour l'unique instance de l'application.
     * @param message
     *            Message � envoyer � l'�ventuelle application d�j� lanc�e, {@code null} si aucune action.
     * @param runOnReceive
     *            Actions � effectuer lorsqu'une autre instance de l'application a indiqu� qu'elle avait essay� de
     *            d�marrer, {@code null} pour aucune action.
     * @param runOnReceive
     *            Actions � effectuer lorsqu'une autre instance de l'application a indiqu� qu'elle
     *            avait essay� de d�marrer, {@code null} pour aucune action.
     * @throws IllegalArgumentException
     *             si le port n'est pas compris entre 1 et 65535, ou si
     *             {@code runOnReceive != null && message == null} (s'il y a des actions �
     *             effectuer, le message ne doit pas �tre {@code null}.
     */
    public UniqueInstance(int port, String message, Runnable runOnReceive) {
        if (port == 0 || (port & 0xffff0000) != 0)
            throw new IllegalArgumentException("Le port doit �tre compris entre 1 et 65535 : " + port + ".");
        if (runOnReceive != null && message == null)
            throw new IllegalArgumentException("runOnReceive != null ==> message == null.");
 
        this.port = port;
        this.message = message;
        this.runOnReceive = runOnReceive;
    }
 
    /**
     * Cr�er un gestionnaire d'instance unique de l'application. Ce constructeur d�sactive la communication entre
     * l'instance d�j� lanc�e et l'instance qui essaye de d�marrer.
     * 
     * @param port
     *            Port d'�coute utilis� pour l'unique instance de l'application.
     */
    public UniqueInstance(int port) {
        this(port, null, null);
    }
 
    /**
     * Essaye de d�marrer le gestionnaire d'instance unique. Si l'initialisation a r�ussi, c'est que l'instance est
     * unique. Sinon, c'est qu'une autre instance de l'application est d�j� lanc�e. L'appel de cette m�thode pr�vient
     * l'application d�j� lanc�e qu'une autre vient d'essayer de se connecter.
     * 
     * @return {@code true} si l'instance de l'application est unique.
     */
    public boolean launch() {
        /* Indique si l'instance du programme est unique. */
        boolean unique;
 
        try {
            /* On cr�e une socket sur le port d�fini. */
            final ServerSocket server = new ServerSocket(port);
 
            /* Si la cr�ation de la socket r�ussit, c'est que l'instance du programme est unique, aucune autre n'existe. */
            unique = true;
 
            /* S'il y a des actions � faire lorsqu'une autre instance essaye de d�marrer... */
            if(runOnReceive != null) {
 
                /* On lance un Thread d'�coute sur ce port. */
                Thread portListenerThread = new Thread("UniqueInstance-PortListenerThread") {
 
                    {
                        setDaemon(true);
                    }
 
                    @Override public void run() {
                        /* Tant que l'application est lanc�e... */
                        while(true) {
                            try {
                                /* On attend qu'une socket se connecte sur le serveur. */
                                final Socket socket = server.accept();
 
                                /* Si une socket est connect�e, on �coute le message envoy� dans un nouveau Thread. */
                                new Thread("UniqueInstance-SocketReceiver") {
 
                                    {
                                        setDaemon(true);
                                    }
 
                                    @Override public void run() {
                                        receive(socket);
                                    }
                                }.start();
                            } catch(IOException e) {
                                Logger.getLogger("UniqueInstance").warning("Attente de connexion de socket �chou�e.");
                            }
                        }
                    }
                };
 
                /* On d�marre le Thread. */
                portListenerThread.start();
            }
        } catch(IOException e) {
            /* Si la cr�ation de la socket �choue, c'est que l'instance n'est pas unique, une autre n'existe. */
            unique = false;
 
            /* Si des actions sont pr�vues par l'instance d�j� lanc�e... */
            if(runOnReceive != null) {
                /*
                 * Dans ce cas, on envoie un message � l'autre instance de l'application pour lui demander d'avoir le
                 * focus (par exemple).
                 */
                send();
            }
        }
        return unique;
    }
 
    /**
     * Envoie un message � l'instance de l'application d�j� ouverte.
     */
    private void send() {
        PrintWriter pw = null;
        try {
            /* On se connecte sur la machine locale. */
            Socket socket = new Socket("localhost", port);
 
            /* On d�finit un PrintWriter pour �crire sur la sortie de la socket. */
            pw = new PrintWriter(socket.getOutputStream());
 
            /* On �crit le message sur la socket. */
            pw.write(message);
        } catch(IOException e) {
            Logger.getLogger("UniqueInstance").warning("�criture sur flux de sortie de la socket �chou�e.");
        } finally {
            if(pw != null)
                pw.close();
        }
    }
 
    /**
     * Re�oit un message d'une socket s'�tant connect�e au serveur d'�coute. Si ce message est le message de l'instance
     * unique, l'application demande le focus.
     * 
     * @param socket
     *            Socket connect�e au serveur d'�coute.
     */
    private synchronized void receive(Socket socket) {
        Scanner sc = null;
 
        try {
            /* On n'�coute que 5 secondes, si aucun message n'est re�u, tant pis... */
            socket.setSoTimeout(5000);
 
            /* On d�finit un Scanner pour lire sur l'entr�e de la socket. */
            sc = new Scanner(socket.getInputStream());
 
            /* On ne lit qu'une ligne. */
            String s = sc.nextLine();
 
            /* Si cette ligne est le message de l'instance unique... */
            if(message.equals(s)) {
                /* On ex�cute le code demand�. */
                runOnReceive.run();
            }
        } catch(IOException e) {
            Logger.getLogger("UniqueInstance").warning("Lecture du flux d'entr�e de la socket �chou�.");
        } finally {
            if(sc != null)
                sc.close();
        }
 
    }
 
}