import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.HashSet;

public class Main {
   //public static final String url = "https://cs.muic.mahidol.ac.th/courses/ooc/docs/";
   public static final String url = "https://cs.muic.mahidol.ac.th/courses/ooc/docs/technotes/guides/serialization";
    public static void main(String[] args) {
        Page page = new Page(url);
        CloseableHttpClient client = HttpClientBuilder.create().build();
        page.setPageNeigthbor(new HashSet<Page>(),client);
        BFS bfs = new BFS(page, client);
        try {
            client.close();
        }catch (IOException e){
            e.printStackTrace();
        }


    }
}
