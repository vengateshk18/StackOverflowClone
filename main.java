package StackOverflow;
import java.util.*;
import java.time.LocalDateTime;



public class main {
    public static void main(String args[]){
        Search engine=new Search();
        Member user1=new Member("venky");
        Question question1=user1.createQuestion(engine, "how to write hellow world in java programming", "I am new to java, exlain me how to write hellow wordld in java", null);
        Answer ans1=user1.creatAnswer(engine, "Just in main function put this line System.out.println(\"Hellow Word\")", question1, null);
        Comment qc1=new Comment("Oh just simple thanks", user1, ans1);
        Guest g=new Guest();
        for(Question k:g.getAllQuestions(engine)){
            k.toSting();
        }
    }
}

enum Badge{
    BRONZE, SILVER, GOLD , DIAMOND, CROWN, ACE
}

enum QuestionStatus{
    OPEN, CLOSED, DELETED
}

enum AccountStatus{
    ACTIVE, ARCHIVED, BLOCKED, BANNED, UNKNOWN
}

class DataBase{
    public List<Question> questions;
    public List<Answer> answers;
    public List<Comment> comments;
    public List<Member> members;
    DataBase(){
        this.questions=new ArrayList<>();
        this.answers=new ArrayList<>();
        this.comments=new ArrayList<>();
        this.members=new ArrayList<>();
    }
    public void addQuestion(Question question){
        this.questions.add(question);
    }
    public void addAswer(Answer answer){
        this.answers.add(answer);
    }
        public void addComment(Comment comment){
        this.comments.add(comment);
    }
    public List<Question> getQuestions(){
        return this.questions;
    }

    public List<Answer> getAnswers(){
        return this.answers;
    }
    public List<Comment> gComments(){
        return this.comments;
    }

}
class Member{
    protected  int Id;
    protected static int Idcounter=100;
    protected String name;
    protected Enum badge;
    protected Enum status;
    protected List<Question> questions;
    Member(String name){
        this.Id=Idcounter++;
        this.name=name;
        this.badge=Badge.BRONZE;
        this.status=AccountStatus.ACTIVE;
        this.questions=new ArrayList<>();
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

    public Question createQuestion(DataBase dataBase,String title, String details,List<Tag> tags){
        Question quObj=new Question(title,details,this);
        if(tags!=null){
            for(Tag tag:tags){
            quObj.addTag(tag);
        }
        }
        dataBase.addQuestion(quObj);
        return quObj;
    }

    public Answer creatAnswer(DataBase dataBase,String answer, Question question, List<Tag> tags){
        Answer ansObj=new Answer(answer,this,question);
        if(tags!=null){
            for(Tag tag:tags){
            ansObj.addTag(tag);
        }
        }
        dataBase.addAswer(ansObj);
        return ansObj;
    }

    public Comment createComment(DataBase dataBase,String comment, Object parent, List<Tag> tags){
        Comment comObj=new Comment(comment,this,parent);
        if(tags!=null){
            for(Tag tag:tags){
            comObj.addTag(tag);
            }
        }
        dataBase.addComment(comObj);
        return comObj;
    }

    public Enum getBatch(){
        return this.badge;
    }

    public Enum setBatch(){
        return this.badge=badge;
    }

    public Enum getAccountStatus(){
        return this.status;
    }
    public void changeAccountStatus(Enum status){
        this.status=status;
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

    Question(String title, String details,Member member){
        this.Id=this.Idcounter++;
        this.title=title;
        this.details=details;
        this.owner=member;
        this.upvotes=0;
        this.downvotes=0;
        this.status=QuestionStatus.OPEN;
        this.datetime=LocalDateTime.now();
        this.tags=new ArrayList<>();

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

    public void toSting(){
        System.out.println("Question Id "+this.Id+" Question title "+this.title+" Question details "+this.details);
        for(Tag tag: this.tags){
            System.out.println(tag.toString());
        }
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
    Answer(String answer,Member user,Question question){
        this.Id=Idcounter++;
        this.answer=answer;
        this.dateTime=LocalDateTime.now();
        this.upvotes=0;
        this.downvotes=0;
        this.user=user;
        this.question=question;
        this.tags=new ArrayList<>();
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
    public Question getQuestion(){
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

    public void toSting(){
        System.out.println("Answer Id "+this.Id+" Answer details "+this.answer+" For the question "+this.question.toString());
        for(Tag tag: this.tags){
            System.out.println(tag.toString());
        }
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

    Comment(String comment,Member member,Object parent){
        this.Id=Idcounter++;
        this.upvotes=0;
        this.downvotes=0;
        this.datetime=LocalDateTime.now();
        this.commentDetails=comment;
        this.user=member;
        this.upvotes=0;
        this.downvotes=0;
        this.parent=parent;
        this.tags=new ArrayList<>();
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

    public void toSting(){
        System.out.println("Comment Id "+this.Id+" Comment details "+this.commentDetails+" parent details"+this.parent.toString());
        for(Tag tag: this.tags){
            System.out.println(tag.toString());
        }
    }
}

class Moderator extends Member{
    Moderator(String name){
        super(name);
    }

    public void closeQuestion(Question question){
        question.changeStatus(QuestionStatus.CLOSED);
    }

    public void reopenQuestion(Question question){
        question.changeStatus(QuestionStatus.OPEN);
    }
    public void deletedQuestion(Question question){
        question.changeStatus(QuestionStatus.DELETED);
    }
}

class Admin extends Member{
    Admin(String name){
        super(name);
    }
    public void blockMember(Member member){
        member.changeAccountStatus(AccountStatus.BLOCKED);
    }
    public void unblockMember(Member member){
        member.changeAccountStatus(AccountStatus.ACTIVE);
    }
}

class Tag{
    private int Id;
    private static int Idcounter=100;
    private String details;

    Tag(String details){
        this.details=details;
    }

    public void toSting(){
        System.err.println("Tag Id: "+this.Id+" Tag details "+this.details);
    }
} 
class TagManager{
    private HashMap<Tag,Integer> frequency;
    TagManager(){
        this.frequency=new HashMap<>();
    }

    public int getFrequency(Tag tag){
        return this.frequency.get(tag);
    }

    public void initializeFrequency(Tag tag){
        this.frequency.put(tag,0);
    }

    public void increaseFrequency(Tag tag){
        this.frequency.put(tag,this.frequency.get(tag)+1);
    }


    public void decreaseFrequency(Tag tag){
        if(this.frequency.get(tag)>0){
            this.frequency.put(tag,this.frequency.get(tag)-1);
        }
    }
}

class Search extends DataBase{
    Search(){
        super();
    }
  public List<Question> searchQuestions(String phrase){
    String searchStrings[]=phrase.split(" ");
    List<Question> answer=new ArrayList<>();
    for(Question question: questions){
        String completeDetails=question.getTitle()+question.getDetails();
        int wordMatchingCount=0;
        for(String word: searchStrings){
            if(completeDetails.indexOf(word)!=-1){
                wordMatchingCount+=1;
            }
        }
        if(searchStrings.length/wordMatchingCount>0.35){
            answer.add(question);
        }
    }
    return answer;
  }
  public List<Question> getAllQuestions(){
    return questions;
  }
  public List<Question> deepSearch(String phrase){
        String searchStrings[]=phrase.split(" ");
        List<Question> answer=new ArrayList<>();
        for(Question question: questions){
            String completeDetails=question.getTitle()+question.getDetails();
            for(Answer ans:answers){
                completeDetails+=ans.getAnswer();
            }
            for(Comment com:comments){
                completeDetails+=com.getDetails();
            }
            int wordMatchingCount=0;
            for(String word: searchStrings){
                if(completeDetails.indexOf(word)!=-1){
                    wordMatchingCount+=1;
                }
            }
            if(searchStrings.length/wordMatchingCount>0.50){
                answer.add(question);
            }
        }
        return answer;
  }

}

class Guest{
    Guest(){

    }

    public List<Question> getAllQuestions(Search search){
        return search.getAllQuestions();
    }

    public List<Question> getQuestions(Search search, String phrase){
        return search.searchQuestions(phrase);
    }
}