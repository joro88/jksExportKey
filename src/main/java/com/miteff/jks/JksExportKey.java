/**
 * Copyright: GNU Lesser General Public License, version 3
 * Copyright more information: http://www.gnu.org/licenses/lgpl.html or in http://en.wikipedia.org/wiki/GNU_Lesser_General_Public_License
 * Source: http://miteff.com
 *
 * How to export the private key from keystore?
 * Does keytool not have an option to do so?
 * Mitev: Some patches applied to work 
 * Alexey Zilber: Ported to work with Base64Coder: http://www.source-code.biz/snippets/java/2.htm
 
 @see <a href = "http://miteff.com" > miteff.com </a> for more information
 
 * @author Mitev: Some patches applied to work 
 * @author Alexey Zilber: Ported to work with Base64Coder: http://www.source-code.biz/snippets/java/2.htm
 */
package com.miteff.jks;

import java.security.cert.Certificate;
import java.security.*;
import java.io.File;
import java.io.FileInputStream;
import biz.source_code.base64Coder.Base64Coder;

class JksExportKey {
    public static void main(String args[]) throws Exception{
		if (args.length < 2) {
			// Yes I know it is not good to pass password in console but 
			// it is a quick-n-dirty fix to export from a keystore to pkcs12
			System.err.println("Usage: java -jar jksExportKey-x.y.jar <keystore> <alias> <password>");
			System.exit(1);
		}
		JksExportKey myep = new JksExportKey();
		myep.doit(args[0], args[1], args[2]);
    }

    public void doit( String fileName, String aliasName, String pass ) throws Exception {
		KeyStore ks = KeyStore.getInstance("JKS");

		char[] passPhrase = pass.toCharArray();
		//BASE64Encoder myB64 = new BASE64Encoder();

		File certificateFile = new File(fileName);
		ks.load(new FileInputStream(certificateFile), passPhrase);

		KeyPair kp = getPrivateKey(ks, aliasName, passPhrase);
		
		PrivateKey privKey = kp.getPrivate();

		// patch for openssl problem, 12 lines
		char[] b64 = Base64Coder.encode(privKey.getEncoded());
		byte[] b64b = new byte[b64.length];
		for (int c = 0; c < b64.length; c++) {
			b64b[c] = (byte) b64[c];
		}

		System.out.println("-----BEGIN PRIVATE KEY-----");
		for (int c = 0; c < b64b.length; c+=64) {
	    	int l = Math.min(64, b64b.length - c);
		    System.out.write(b64b, c, l);
		    System.out.println();
		}
		System.out.println("-----END PRIVATE KEY-----");

	}

    public KeyPair getPrivateKey(KeyStore keystore, String alias, char[] password) {
        try {
            // Get private key
            Key key = keystore.getKey(alias, password);
            if (key instanceof PrivateKey) {
                // Get certificate of public key
                Certificate cert = keystore.getCertificate(alias);
    
                // Get public key
                PublicKey publicKey = cert.getPublicKey();
    
                // Return a key pair
                return new KeyPair(publicKey, (PrivateKey)key);
            }
        } catch (UnrecoverableKeyException e) {
			throw new NullPointerException( e.toString() );
        } catch (NoSuchAlgorithmException e) {
			throw new NullPointerException( e.toString() );
        } catch (KeyStoreException e) {
			throw new NullPointerException( e.toString() );
        }
        return null;
    }

}

