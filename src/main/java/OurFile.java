import java.io.File;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class OurFile extends File {

    private String hashMD;

    public OurFile(String pathname) {
        super(pathname);
        if (hashMD == null) {
            System.out.println("Hashing...");
            setHashMD();
        }
    }

    public OurFile(URI uri) {
        super(uri);
        if (hashMD == null) {
            setHashMD();
        }
    }

    /**
     * @return file MD5 hash
     */
    public String getHashMD() {
        return hashMD;
    }

    public void setHashMD() {
        this.hashMD = hashFuncMD5(this);
    }

    /**
     * @return size in MB
     */
    public double getFileSizeMB() {
        return super.length() / 1e6;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OurFile ourFile = (OurFile) o;
        return hashMD.equals(ourFile.hashMD);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hashMD);
    }

    @Override
    public String toString() {
        return this.getName() + " размер файла " + this.getFileSizeMB();
    }


    private String hashFuncMD5(OurFile file) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] fileInArray = new byte[(int) file.length()];

        Objects.requireNonNull(md).update(fileInArray);

        byte[] byteData = Objects.requireNonNull(md).digest();


        StringBuilder hexString = new StringBuilder();
        for (byte aByteData : byteData) {
            String hex = Integer.toHexString(0xff & aByteData);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
