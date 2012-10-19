package net.caffeinelab;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import com.ning.http.client.providers.netty.FeedableBodyGenerator;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args) throws Exception {
        nonBlocking();
        // blocking();
    }

    private static void nonBlocking() throws Exception {
        System.out.println( "Let's upload some non blocking shit.");
        AsyncHttpClient client = new AsyncHttpClient();

        File file = new File("/Users/elo/Dropbox/IMG_20120709_144609.jpg");
        FeedableBodyGenerator bodyGenerator = new FeedableBodyGenerator();

        bodyGenerator.feed(fileToByteBuffer(file), true);
        BoundRequestBuilder q = client.preparePost("http://localhost:9000/upload").setBody(bodyGenerator);
        ListenableFuture<Response> f = q.execute();

        Response resp = f.get();
        System.out.println("Done in the upload. " + resp + "\n" + resp.getStatusCode() + "\n" + resp.getResponseBody());
        client.close();
    }

    private static void blocking() throws Exception {
        System.out.println( "Let's upload some shit.");
        AsyncHttpClient client = new AsyncHttpClient();

        File file = new File("/Users/elo/Dropbox/IMG_20120709_144609.jpg");
        ListenableFuture<Response> f = client.preparePost("http://localhost:9000/upload").setBody(file).execute();
        Response resp = f.get();
        System.out.println("Done in the upload. " + resp.getStatusCode() + "\n" + resp.getResponseBody());
        client.close();
    }

    private static byte[] fileToByteArray(File inFile) throws Exception {
        byte[] result = new byte[(int)inFile.length()];
        InputStream is = new FileInputStream(inFile);
        int b;
        int i=0;
        while ((b=is.read())!=-1) {
            result[i] = ((byte)b);
            i++;
        }
        is.close();
        return result;
    }

    private static ByteBuffer fileToByteBuffer(File inFile) throws Exception {
        return ByteBuffer.wrap(fileToByteArray(inFile));
    }

    
}
