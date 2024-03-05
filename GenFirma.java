import java.io.*; // Paquete Para Firma 
import java.security.*; // Metodos para Firmar Archivos

public class GenFirma {
    
    public static void main(String[] args){
        
        if (args.length != 1){
            System.out.println("Debes Indicar el Nombre del Documento a Firmar: java GenFirma-documento a Firmar con Extension-");
        }
        else try {
            KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("DSA", "SUN");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            
            keyGenerator.initialize(1024, random);

            KeyPair pairKey = keyGenerator.generateKeyPair();
            PrivateKey privateKey = pairKey.getPrivate();
            PublicKey publicKey = pairKey.getPublic();

            Signature signatureKey = Signature.getInstance("SHA1withDSA", "SUN"); 
            signatureKey.initSign(privateKey);

            FileInputStream fileInput = new FileInputStream(args[0]);
            BufferedInputStream bufferedInput = new BufferedInputStream(fileInput);
            byte[] byteBuffer = new byte[1024];
            int len;
            while (bufferedInput.available() != 0) {
                len = bufferedInput.read(byteBuffer);
                signatureKey.update(byteBuffer, 0, len);
                };

            bufferedInput.close();

            byte[] realSignature = signatureKey.sign();

            FileOutputStream sigFos = new FileOutputStream("firmadigital");
            sigFos.write(realSignature);

            byte[] key = publicKey.getEncoded();
            FileOutputStream keyFileOut = new FileOutputStream("clavepublica");
            keyFileOut.write(key);

            keyFileOut.close();

        } catch (Exception e){
            System.out.println("Error: " + e.toString());
        }

    }

}