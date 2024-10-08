package app.entities;

public class Motivation
{
    private String motivationTitle;
    private String  motivationText;
    private String  imageURL;
    private int motivationId;

    public Motivation(int motivationId, String motivationTitle, String motivationText, String imageURL)
    {
        this.motivationId = motivationId;
        this.motivationTitle = motivationTitle;
        this.motivationText = motivationText;
        this.imageURL = imageURL;
    }
    public String getMotivationTitle()
    {
        return motivationTitle;
    }

    public String getMotivationText()
    {
        return motivationText;
    }

    public String getImageURL()
    {
        return imageURL;
    }
}
