package app.entities;

public class Motivation
{
    private String motivationTitle;
    private String  motivationText;
    private String  imageURL;

    public Motivation(String motivationTitle, String motivationText, String imageURL)
    {
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
