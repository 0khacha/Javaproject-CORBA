import Converter.Conversion;
import Converter.ConversionHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import java.net.InetAddress;
import java.util.Properties;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Initialiser l'ORB (Object Request Broker)
        	 InetAddress clientAddress = InetAddress.getLocalHost();
             String clientIpAddress = clientAddress.getHostAddress();
             String clientHostName = clientAddress.getHostName();

            String serverHost = clientIpAddress; // Adresse IP du serveur
            int serverPort = 1050; // Port du serveur CORBA

            // Créer des propriétés pour permettre à l'ORB de se connecter au serveur
            Properties props = new Properties();
            props.put("org.omg.CORBA.ORBInitialHost", serverHost);
            props.put("org.omg.CORBA.ORBInitialPort", Integer.toString(serverPort));

            // Initialiser l'ORB avec les propriétés spécifiées
            ORB orb = ORB.init(args, props);

            // Obtenir une référence vers le service de nommage
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // Résoudre la référence d'objet pour le serveur de conversion
            String serverObjectName = "Server"; // Remplacer par le nom correct de l'objet serveur
            org.omg.CORBA.Object serverObj = ncRef.resolve_str(serverObjectName);
            Conversion converter = ConversionHelper.narrow(serverObj);

            // Obtenir l'adresse IP et le nom d'hôte du client
           

            System.out.println("Adresse IP du Client  : " + clientIpAddress);
            System.out.println("Nom d'hote du Client : " + clientHostName);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Demander à l'utilisateur de choisir la base source
                System.out.println("Choisissez la base source :\n1 - Binaire\n2 - Decimal\n3 - Octal\n4 - Hexadecimal\n:");
                short sourceBase = scanner.nextShort();

                // Demander à l'utilisateur d'entrer le nombre à convertir
                System.out.println("Entrez le nombre :");
                String number = scanner.next();

                // Demander à l'utilisateur de choisir la base cible
                System.out.println("Choisissez la base cible :\n1 - Binaire\n2 - Decimal\n3 - Octal\n4 - Hexadecimal\n:");
                short targetBase = scanner.nextShort();

                // Effectuer la conversion en utilisant le service distant CORBA
                String result = converter.convertNumber(number, sourceBase, targetBase);

                // Afficher le résultat de la conversion
                System.out.println("Resultat : " + result);

                // Demander à l'utilisateur s'il souhaite effectuer une autre conversion
                System.out.println("Voulez-vous effectuer une autre conversion ? (O/N) :");
                String choice = scanner.next();
                if (!choice.equalsIgnoreCase("O")) {
                    break; // Sortir de la boucle si la réponse n'est pas "O"
                }
            }

            System.out.println("ClientConverter termine.");

        } catch (Exception e) {
            System.err.println("Erreur : " + e);
            e.printStackTrace(System.out);
        }
    }
}
