package parteam.letspartya.model.bean;

/**
 * Created by mengce on 2016/1/7.
 */
public class PartyRecommendBean {

    private int id;
    private String url;
    private String image;
    private String title;

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){

        return this.url;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getImage(){
        return this.image;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
}
