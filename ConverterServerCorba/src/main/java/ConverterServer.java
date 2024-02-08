import Converter.Conversion;
import Converter.ConversionHelper;
import ServerImp.ConversionImpl;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import java.net.InetAddress;

public class ConverterServer {

    public static void main(String[] args) {
        try {
            // Obtenir l'adresse IP locale du serveur
            InetAddress adresseServeur = InetAddress.getLocalHost();
            String ipServeur = adresseServeur.getHostAddress();
            String nomHoteServeur = adresseServeur.getHostName();

            // Créer et initialiser l'ORB (Object Request Broker)
            String[] orbArgs = {"-ORBInitialHost", ipServeur, "-ORBInitialPort", "1050"};
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(orbArgs, null);

            System.out.println("Adresse IP du serveur : " + ipServeur);
            System.out.println("Nom d'hote du serveur : " + nomHoteServeur);

            // Créer le servant (implémentation du serveur)
            ConversionImpl conversionImpl = new ConversionImpl();

            // Obtenir le Root POA (Portable Object Adapter)
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // Obtenir la référence d'objet pour le servant
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(conversionImpl);
            Conversion href = ConversionHelper.narrow(ref);

            // Relier la référence d'objet dans le service de nommage (Naming Service)
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            org.omg.CosNaming.NamingContextExt ncRef = org.omg.CosNaming.NamingContextExtHelper.narrow(objRef);

            org.omg.CosNaming.NameComponent[] name = {new org.omg.CosNaming.NameComponent("Server", "")};
            ncRef.rebind(name, href);
            System.out.println("Le serveur convertisseur est pret et en attente...");
         // Exécuter l'ORB
            orb.run(); 
           

            conversionImpl.displayConnectedClients();  // Affiche les clients connectés initiaux
            conversionImpl.addConnectedClient(ipServeur, nomHoteServeur);

            // Créer un thread servant à afficher les informations sur les clients connectés
            Thread threadAffichageInfoClient = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(5000); // Afficher toutes les 5 secondes
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    conversionImpl.displayConnectedClients();
                }
            });

            // Définir le thread comme un daemon
            threadAffichageInfoClient.setDaemon(true);

            // Démarrer le thread daemon
            threadAffichageInfoClient.start();

            

        } catch (Exception e) {
            System.err.println("Erreur : " + e);
            e.printStackTrace(System.out);
        }
    }
}