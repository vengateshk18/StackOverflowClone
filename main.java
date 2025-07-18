package StackOverflow;
import java.util.*;
import java.time.LocalDateTime;



public class main {
    public static void main(String args[]){
        System.out.println("simple");
    }
}

enum Badge{
    BRONZE, SILVER, GOLD , DIAMOND, CROWN, ACE
}

enum Status{
    OPEN, CLOSED, DELETED
}

class Member{
    private int Id;
    private static int Idcounter=100;
    private String name;
    private Enum badge;
    private List<Question> questions;
    private int bounty;
    Member(){
        this.Id=Idcounter++;
    }
    public void addBountyToQuestio(int bounty){
        this.bounty+=bounty;
    }
    public int getId(){
        return this.Id;
    }
    
    public String getName(){
        return this.name;
    }

    public List<Question> getQuestions(){
        return this.questions;
    }

    public Question createQuestion(String title, String details,List<Tag> tags){
        return new Question();
    }

    public Answer creatAnswer(String answer, Question question, List<Tag> tags){
        return new Answer();
    }

    public Comment creaComment(String Comment, Object parent, List<Tag> tags){
        return new Comment();
    }

    public Enum getBatch(){
        return this.badge;
    }

    public Enum setBatch(){
        return this.badge=badge;
    }
}

class Question{
    private int Id;
    private static int Idcounter=100;
    private String title;
    private String details;
    private int upvotes;
    private int downvotes;
    private Enum status;
    private LocalDateTime datetime;
    private Member owner;
    private List<Tag> tags;

    Question(){

    }
    public void changeStatus(Enum status){
        this.status=status;
    }
    public String getTitle(){
        return this.title;
    }
    public String getDetails(){
        return this.details;
    }
    public int getId(){
        return this.Id;
    }
    public void editTitle(String title){
        this.title=title;
    }
    public void editDetails(String details){
        this.details=details;
    }
    public void upvote(){
        this.upvotes+=1;
    }
    public void downvote(){
        this.downvotes+=1;
    }
    public String getVotes(){
        return "Upvotes: "+this.upvotes+" Downvotes :"+this.downvotes;
    }

    public List<Tag> getTags(){
        return this.tags;
    }
    public void addTag(Tag tag){
        this.tags.add(tag);
    }



}

class Answer{
    private int Id;
    private static int Idcounter;
    private String answer;
    private LocalDateTime dateTime;
    private int upvotes;
    private int downvotes;
    private Member user;
    private Question question;
    private List<Tag> tags;
    Answer(){

    }
    public int getId(){
        return this.Id;
    }
    public String getAnswer(){
        return this.answer;
    }
    public Member getUser(){
        return this.user;
    }
    public Question gQuestion(){
        return this.question;
    }
    public void upvote(){
        this.upvotes+=1;
    }
    public void downvote(){
        this.downvotes+=1;
    }
    public void editAnswer(String answer){
        this.answer=answer;
    }
    public String getVotes(){
        return "Upvotes: "+this.upvotes+" Downvotes :"+this.downvotes;
    }

    public List<Tag> getTags(){
        return this.tags;
    }
    public void addTag(Tag tag){
        this.tags.add(tag);
    }

}

class Comment{
    private int Id;
    private static int Idcounter=100;
    private String commentDetails;
    private LocalDateTime datetime;
    private Member user;
    private int upvotes;
    private int downvotes;
    private Object parent;
    private List<Tag> tags;

    Comment(){
        this.Id=Idcounter++;
        this.upvotes=0;
        this.downvotes=0;
        this.datetime=LocalDateTime.now();
    }
    public int getId(){
        return this.Id;
    }

    public String getDetails(){
        return this.commentDetails;
    }

    public void editComment(String comment){
        this.commentDetails=comment;
    }

    public Member getUser(){
        return this.user;
    }

    public int getUpvotes(){
        return this.upvotes;
    }

    public void upvote(){
        this.upvotes+=1;
    }

    public void downvote(){
        this.downvotes+=1;
    }

    public int getDownvotes(){
        return this.downvotes;
    }

    public Object getParent(){
        return this.parent;
    }

    public List<Tag> getTags(){
        return this.tags;
    }
    public void addTag(Tag tag){
        this.tags.add(tag);
    }
}

class Moderator{
    private int Id;
    private static int Idcounter=100;
    private String name;
    private List<Question> deletedQuestions;
    Moderator(){

    }

    public void closeQuestion(Question question){
        question.changeStatus(Status.CLOSED);
    }

    public void reopenQuestion(Question question){
        question.changeStatus(Status.OPEN);
    }
    public void deletedQuestion(Question question){
        question.changeStatus(Status.DELETED);
    }
}

class Tag{
    private int Id;
    private static int Idcounter=100;
    private String details;

    Tag(){

    }
} 
class TagManager{
    
}