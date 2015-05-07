
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Win 8.1
 */
public class WikiPage {
    private String id;
    private String title;
    private String lemma;
    private String sense;
    private String text;
    private String[] redirection;
    private String[] links;
    private String[] categories;
    private Boolean isDisambiguation;
    private Boolean isRedirection;
    private String source;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the lemma
     */
    public String getLemma() {
        return lemma;
    }

    /**
     * @param lemma the lemma to set
     */
    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    /**
     * @return the sense
     */
    public String getSense() {
        return sense;
    }

    /**
     * @param sense the sense to set
     */
    public void setSense(String sense) {
        this.sense = sense;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
    
    public String getSource() {
        return source;
    }

    /**
     * @param text the text to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the redirection
     */
    public String[] getRedirection() {
        return redirection;
    }

    /**
     * @param redirection the redirection to set
     */
    public void setRedirection(String[] redirection) {
        this.redirection = redirection;
    }

    /**
     * @return the links
     */
    public String[] getLinks() {
        return links;
    }

    /**
     * @param links the links to set
     */
    public void setLinks(String[] links) {
        this.links = links;
    }

    /**
     * @return the categories
     */
    public String[] getCategories() {
        return categories;
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    /**
     * @return the isDisambiguation
     */
    public Boolean getIsDisambiguation() {
        return isDisambiguation;
    }

    /**
     * @param isDisambiguation the isDisambiguation to set
     */
    public void setIsDisambiguation(Boolean isDisambiguation) {
        this.isDisambiguation = isDisambiguation;
    }

    /**
     * @return the isRedirection
     */
    public Boolean getIsRedirection() {
        return isRedirection;
    }

    /**
     * @param isRedirection the isRedirection to set
     */
    public void setIsRedirection(Boolean isRedirection) {
        this.isRedirection = isRedirection;
    }
    
    
}
