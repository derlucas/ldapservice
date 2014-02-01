package de.ctdo.ldapservice.utils;

import sun.misc.BASE64Encoder;
import java.security.MessageDigest;

public final class SSHA {
    private static final BASE64Encoder ENCODER  = new BASE64Encoder();
    private MessageDigest sha = null;
    private static final SSHA INSTANCE = new SSHA();

    public static  SSHA getInstance() {
        return INSTANCE;
    }

    private SSHA() {
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println("Construction failed: " + e);
        }
    }

    String createDigest(byte[] salt, String entity) {
        String label = "{SSHA}";

        // Update digest object with byte array of the source clear text
        // string and the salt
        sha.reset();
        sha.update(entity.getBytes());
        sha.update(salt);

        // Complete hash computation, this results in binary data
        byte[] pwhash = sha.digest();
        return label + ENCODER.encode(concatenate(pwhash, salt));
    }

    /**
     * Create Digest for each entity values passed in.  A random salt is used.
     *
     * @param entity string to be encrypted
     * @return string representing the salted hash output of the encryption
     *         operation
     */
    public String createDigest(String entity) {
        return INSTANCE.createDigest(randSalt(), entity);
    }


    /**
     * Combine two byte arrays
     *
     * @param l first byte array
     * @param r second byte array
     * @return byte[] combined byte array
     */
    private static byte[] concatenate(byte[] l, byte[] r) {
        byte[] b = new byte[l.length + r.length];
        System.arraycopy(l, 0, b, 0, l.length);
        System.arraycopy(r, 0, b, l.length, r.length);
        return b;
    }

    private byte[] randSalt() {
        int saltLen = 4; //8
        byte[] b = new byte[saltLen];
        for (int i = 0; i < saltLen; i++) {
            byte bt = (byte) (((Math.random()) * 256) - 128);
            b[i] = bt;
        }
        return b;
    }

}