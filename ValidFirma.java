import java.io.*;
import java.security.*;
import java.security.spec.*;

public class ValidFirma {

    public static void main(String[] args) {

        if (args.length != 3) {
            System.out.println(
                    "Debes Cargar los Archivos de: -Clabe Publica- Firma Digital - y el Documento Original con Extension");
        } else try {
            FileInputStream keyfis = new FileInputStream(args[0]);
            byte[] encKey = new byte[keyfis.available()];
            keyfis.read(encKey);

            keyfis.close();

            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);

            KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
            PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);

            /* Se cargan los bytes de la firmadigital */
			
            FileInputStream sigfis = new FileInputStream(args[1]);
            byte[] sigToVerify = new byte[sigfis.available()]; 
            sigfis.read(sigToVerify );

            sigfis.close();

            Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
            sig.initVerify(pubKey);

            FileInputStream datafis = new FileInputStream(args[2]);
            BufferedInputStream bufin = new BufferedInputStream(datafis);

            byte[] buffer = new byte[1024];
            int len;
            while (bufin.available() != 0) {
                len = bufin.read(buffer);
                sig.update(buffer, 0, len);
                };

            bufin.close();

            boolean verifies = sig.verify(sigToVerify);

            if (verifies == true) {
                System.out.println("La firma es auntentica");
            } else {
                System.out.println("La firma es ap�crifa");
            }

        } catch (Exception e) {
            System.err.println("Error tenemos la siguiente excepcion" + e.toString());
        };
    }
}
