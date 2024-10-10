package app.entities;

public class Motivation
{
    private final String motivationTitle;
    private final String  motivationText;
    private final String  imageURL;
    private final int motivationId;
    private final int authorId;

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
