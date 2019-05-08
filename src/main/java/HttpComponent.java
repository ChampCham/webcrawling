
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.util.Objects;

public class HttpComponent {
    private UriComponents uri;
    private String filePath;
    private String validDomainName;
    private String mainPath = "/Users/Champcham/Documents/OOC/test2";

    public HttpComponent(String url) {
        setUri(url);
        setFilePath();
    }

    public UriComponents getUri() {
        return uri;
    }

    public void setUri(String url) {
        uri = UriComponentsBuilder.fromUriString(url).build();
        setFilePath();
    }


    public String getFilePath() {
        return filePath;
    }


    public void setFilePath() {
        this.filePath = this.mainPath+getUri().getPath();
    }


    public String getValidDomainName() {
        return validDomainName;
    }



    public void setValidDomainName(String validDomainName) {
        this.validDomainName = validDomainName;
    }

    public boolean isValid(){
        return isValidDomain()
                && !getUri().toString().contains("#")
                && !getUri().toString().contains("?")
                && !getUri().toString().contains("javascript:run")
                && !getUri().toString().contains(" ");
    }

    public boolean isValidDomain(){
        return getUri().getHost().equals(getValidDomainName());
    }

    public boolean isFile(){
        String[] bits = getUri().toString().split("/");
        return  bits[bits.length-1].contains(".");
    }

    public void download(CloseableHttpClient httpClient){
        if (isValid()){
            //Make directory`
            System.out.println(getFilePath());
            if (!isFile()){
                File dir = new File(getFilePath());
                dir.mkdirs();
            }else {
                File dir = new File(getFilePath());
                dir.getParentFile().mkdirs();
            }
            if (isFile()){
                //Creates a new HTTP client

                HttpGet request = new HttpGet(getUri().toUri());
                try {

                    HttpResponse response = httpClient.execute(request);
                    HttpEntity entity = response.getEntity();

                    //Create input stream
                    BufferedInputStream inputStream = new BufferedInputStream(entity.getContent());

                    //Create output stream
                    FileOutputStream outputStream = new FileOutputStream(getFilePath());


                    outputStream.close();
                    inputStream.close();

                }catch (IOException e){
                   e.printStackTrace();
                }finally {
                    request.releaseConnection();
                }
            }
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpComponent that = (HttpComponent) o;
        return Objects.equals(uri, that.uri) &&
                Objects.equals(filePath, that.filePath) &&
                Objects.equals(validDomainName, that.validDomainName) &&
                Objects.equals(mainPath, that.mainPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri, filePath, validDomainName, mainPath);
    }
}
