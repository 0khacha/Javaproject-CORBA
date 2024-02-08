package ServerImp;

import Converter.ConversionPOA;
import java.util.HashMap;
import java.util.Map;

public class ConversionImpl extends ConversionPOA {

    private Map<String, String> connectedClients = new HashMap<>();

    private boolean headerPrinted = false ; // Variable to track whether the header has been printed

    public String convertNumber(String number, short BaseSource, short BaseCible) {
        try {
            long decimalNumber = convertToDecimal(number, BaseSource);
            return convertFromDecimal(decimalNumber, BaseCible);
        } catch (NumberFormatException e) {
            return "Entree invalide. Veuillez saisir un numero valide.";
        }
    }

    private long convertToDecimal(String number, short BaseSource) {
        switch (BaseSource) {
            case 1:
                return Long.parseLong(number, 2); // Binaire à Décimal
            case 2:
                return Long.parseLong(number); // Décimal
            case 3:
                return Long.parseLong(number, 8); // Octal à Décimal
            case 4:
                return Long.parseLong(number, 16); // Hexadécimal à Décimal
            default:
                throw new IllegalArgumentException("Base source invalide");
        }
    }

    private String convertFromDecimal(long decimalNumber, short BaseCible) {
        switch (BaseCible) {
            case 1:
                return Long.toBinaryString(decimalNumber); // Décimal à Binaire
            case 2:
                return Long.toString(decimalNumber); // Décimal
            case 3:
                return Long.toOctalString(decimalNumber); // Décimal à Octal
            case 4:
                return Long.toHexString(decimalNumber).toUpperCase(); // Décimal à Hexadécimal
            default:
                throw new IllegalArgumentException("Base cible invalide.");
        }
    }

    public synchronized void displayConnectedClients() {
        if (!headerPrinted) {
            System.out.println("\nClients connectes:");
            headerPrinted = true; // Set the flag to true after printing the header
        }

        for (Map.Entry<String, String> entry : connectedClients.entrySet()) {
            System.out.println("IP du client : " + entry.getKey() + ", Nom d'hote : " + entry.getValue());
        }

        // Efface la carte connectedClients après l'affichage
        connectedClients.clear();
    }

    public synchronized void addConnectedClient(String clientIp, String clientHostname) {
        connectedClients.put(clientIp, clientHostname);
    }

    public synchronized void removeConnectedClient(String clientIp) {
        connectedClients.remove(clientIp);
    }
}
