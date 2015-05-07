/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Win 8.1
 */
public class MySynset {
    private String SynsetId;
    private String WordNetID;
    private String Title;
    private String Gloss;
    private String[] Lemma;
    private String[] RelatedLinks;

    public String getGloss() {
        return Gloss;
    }

    public void setGloss(String Gloss) {
        this.Gloss = Gloss;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String[] getRelatedLinks() {
        return RelatedLinks;
    }

    public void setRelatedLinks(String[] RelatedLinks) {
        this.RelatedLinks = RelatedLinks;
    }
    private String Source;

    public String getSynsetId() {
        return SynsetId;
    }

    public void setSynsetId(String SynsetId) {
        this.SynsetId = SynsetId;
    }

    public String getWordNetID() {
        return WordNetID;
    }

    public void setWordNetID(String WordNetID) {
        this.WordNetID = WordNetID;
    }

    public String[] getLemma() {
        return Lemma;
    }

    public void setLemma(String[] Lemma) {
        this.Lemma = Lemma;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String Source) {
        this.Source = Source;
    }
}
