package app.entities;

public class Motivation
{
    private String motivationTitle;
    private String  motivationText;
    private String  imageURL;
    private int motivationId;
    private int authorId;

    public Motivation(int motivationId, String motivationTitle, String motivationText, String imageURL, int authorId)
    {
        this.motivationId = motivationId;
        this.motivationTitle = motivationTitle;
        this.motivationText = motivationText;
        this.imageURL = imageURL;
        this.authorId = authorId;
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

    public int getMotivationId()
    {
        return motivationId;
    }

    public int getAuthorId()
    {
        return authorId;
    }
}
