import org.apache.http.impl.client.CloseableHttpClient;

import java.util.*;

public class BFS {
    private Set<Page> pagesVisited;
    private Set<Page> pagesFrontier;

    public BFS(Page firstPage, CloseableHttpClient httpClient) {
        pagesVisited = new HashSet<>(Arrays.asList(firstPage));
        pagesFrontier = new HashSet<>(Arrays.asList(firstPage));

        while (!pagesFrontier.isEmpty()){
            pagesFrontier = nbrs(pagesFrontier, pagesVisited, httpClient);


//          System.out.println("visited"+pagesVisited.size()+": frontier"+pagesFrontier.size());

            pagesFrontier.removeAll(pagesVisited);
            pagesVisited.addAll(pagesFrontier);

//
//                        System.out.println("visited"+pagesVisited.size()+": frontier"+pagesFrontier.size());
//                        for (Page p: pagesFrontier){
//                            System.out.println(p.getUrl());
//                        }
//                        System.out.println("---------------------");
        }
    }

    public Set<Page> nbrs(Set<Page> pages, Set<Page> pagesVisited, CloseableHttpClient httpClient){
        Set <Page > union = new HashSet <>();
        for (Page page: pages){
            page.setPageNeigthbor(pagesVisited, httpClient);
            union.addAll(page.getPageNeigthbor());
        }
        return union;
    }
}
