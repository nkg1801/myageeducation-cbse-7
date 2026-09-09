package com.myAgeEducation.cbseClass7;

//@JsonIgnoreProperties(ignoreUnknown=true)
public class Question {
    private int chapter;
    private String chapterName;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;
    private String image;
    private String supportiveText;

    public Question()
    {

    }

    public int getChapter()
    {
        return chapter;
    }

    public String getQuestion()
    {
        return question;
    }

    public String getOption1()
    {
        return option1;
    }

    public String getOption2()
    {
        return option2;
    }

   public String getOption3()
    {
        return option3;
    }

    public String getOption4()
    {
        return option4;
    }

    public String getAnswer()
    {
        return answer;
    }

    public String getImage()
    {
        return image;
    }

    public String getSupportiveText()
    {
        return supportiveText;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSupportiveText(String supportiveText) {
        this.supportiveText = supportiveText;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
}
