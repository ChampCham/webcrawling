import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Page {
    private String url;
    private String pagePath;
    private Set<Page> pageNeigthbor;
    private HttpComponent httpComponent;

    public Page(String url){

        httpComponent = new HttpComponent(url);
        httpComponent.setValidDomainName("cs.muic.mahidol.ac.th");
        this.pagePath = httpComponent.getFilePath();
        this.url = url;
        this.pageNeigthbor = new HashSet<>();

    }


    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public  void  addPage(Page page){
        getPageNeigthbor().add(page);
    }

    public String getUrl() {
        return url;
    }

    public void setPageNeigthbor(Set<Page> pagesVisited, CloseableHttpClient httpClient) {
        if (httpComponent.isValid()){
            httpComponent.download(httpClient);
            try {
               Document document = Jsoup.connect(getUrl()).ignoreContentType(true).get();

                Elements links = document.select("a");
                Elements media = document.select("[src]");
                Elements imports = document.select("link");

                for (Element link : links) {

                    if (!link.attr("abs:href").isEmpty()){
//                         System.out.println(link.attr("abs:href"));
                        getPageNeigthbor().add(new Page(link.attr("abs:href")));
                    }

                }

                for (Element med : media) {
                    if (!med.attr("abs:src").isEmpty() ) {
//                        System.out.println(med.attr("abs:src"));
                        getPageNeigthbor().add(new Page(med.attr("abs:src")));
                    }
                }


                for (Element im : imports) {
                    if (!im.attr("abs:href").isEmpty() ) {
//                        System.out.println(im.attr("abs:href"));
                        getPageNeigthbor().add(new Page(im.attr("abs:href")));
                    }
                }

            }catch (IOException e){
                e.printStackTrace();
//                System.out.println(getUrl());
            }
        }

    }

    public Set<Page> getPageNeigthbor() {
        return pageNeigthbor;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return Objects.equals(url, page.url) &&
                Objects.equals(pagePath, page.pagePath) &&
                Objects.equals(pageNeigthbor, page.pageNeigthbor) &&
                Objects.equals(httpComponent, page.httpComponent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, pagePath, pageNeigthbor, httpComponent);
    }
}
