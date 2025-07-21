package StackOverflow;
import java.util.*;
import java.time.LocalDateTime;



public class main {
    public static void main(String args[]){
        
        DataBase base=new DataBase();
        Search engine=new Search(base);
        Member user1=new Member("venky");
        Question question1=user1.createQuestion(base, "how to write hellow world in java programming", "I am new to java, exlain me how to write hellow wordld in java", null);
        Answer ans1=user1.creatAnswer(base, "Just in main function put this line System.out.println(\"Hellow Word\")", question1, null);
        Comment qc1=new Comment("Oh just simple thanks", user1, ans1);
        Tag tag1=new Tag("Java");
        question1.addTag(tag1);
        Guest g=new Guest();
        for(Question k:g.getAllQuestions(engine)){
            System.out.println(k.toString());
        }

        System.out.println(engine.searchMostUpvotesQuestion().toString());
    }
}

enum Badge{
    BRONZE, SILVER, GOLD , DIAMOND, CROWN, ACE, CONQUEROR
}

enum QuestionStatus{
    OPEN, CLOSED, DELETED
}

enum AccountStatus{
    ACTIVE, ARCHIVED, BLOCKED, BANNED, UNKNOWN
}

class DataBase extends TagManager{
    public List<Question> questions;
    public List<Answer> answers;
    public List<Comment> comments;
    public List<Member> members;
    DataBase(){
        super();
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
    public List<Comment> getComments(){
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
                if(!dataBase.isAvailable(tag)){
                    dataBase.initializeFrequency(tag);
                }
                else{
                    dataBase.increaseFrequency(tag);
                }
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
            if(!dataBase.isAvailable(tag)){
                    dataBase.initializeFrequency(tag);
            }
            else{
                dataBase.increaseFrequency(tag);
            }
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
             if(!dataBase.isAvailable(tag)){
                    dataBase.initializeFrequency(tag);
                }
                else{
                    dataBase.increaseFrequency(tag);
                }
            }
        }
        dataBase.addComment(comObj);
        return comObj;
    }

    public Enum getBadge(){
        return this.badge;
    }

    public void setBadge(Enum badge){
        this.badge=badge;
    }

    public Enum getAccountStatus(){
        return this.status;
    }
    public void changeAccountStatus(Enum status){
        this.status=status;
    }
}

abstract class Vote{
    protected int upvotes;
    protected int downvotes;
    Vote(){
        this.upvotes=0;
        this.downvotes=0;
    }

    public int getUpvotes(){
        return this.upvotes;
    }

    public int getDownVotes(){
        return this.downvotes;
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
}

class Question extends Vote{
    private int Id;
    private static int Idcounter=100;
    private String title;
    private String details;
    private QuestionStatus status;
    private LocalDateTime datetime;
    private Member owner;
    private List<Tag> tags;

    Question(String title, String details,Member member){
        this.Id=this.Idcounter++;
        this.title=title;
        this.details=details;
        this.owner=member;
        this.status=QuestionStatus.OPEN;
        this.datetime=LocalDateTime.now();
        this.tags=new ArrayList<>();

    }
    public void changeStatus(QuestionStatus status){
        this.status=status;
    }

    public QuestionStatus getStatus(){
        return this.status;
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

    public List<Tag> getTags(){
        return this.tags;
    }
    public void addTag(Tag tag){
        this.tags.add(tag);
    }

    public Member getOwner(){
        return this.owner;
    }

    public String toString(){
        String data="Question Id "+this.Id+" Question title "+this.title+" Question details "+this.details;
        data+=" \n Tags";
        for(Tag tag: this.tags){
           data+="\n";
           data+=tag.toString();
        }
        return data;

    }

}



class Answer extends Vote{
    private int Id;
    private static int Idcounter=100;
    private String answer;
    private LocalDateTime dateTime;
    private Member user;
    private Question question;
    private List<Tag> tags;
    Answer(String answer,Member user,Question question){
        this.Id=Idcounter++;
        this.answer=answer;
        this.dateTime=LocalDateTime.now();
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
    public void editAnswer(String answer){
        this.answer=answer;
    }

    public List<Tag> getTags(){
        return this.tags;
    }
    public void addTag(Tag tag){
        this.tags.add(tag);
    }

    public String toString(){
        String data="Answer Id "+this.Id+" Answer details "+this.answer+" For the question "+this.question.toString();
        data+=" \n Tags";
        for(Tag tag: this.tags){
           data+="\n";
           data+=tag.toString();
        }
        return data;

    }

}

class Comment extends Vote{
    private int Id;
    private static int Idcounter=100;
    private String commentDetails;
    private LocalDateTime datetime;
    private Member user;
    private Object parent;
    private List<Tag> tags;

    Comment(String comment,Member member,Object parent){
        this.Id=Idcounter++;
        this.upvotes=0;
        this.downvotes=0;
        this.datetime=LocalDateTime.now();
        this.commentDetails=comment;
        this.user=member;
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

    public Object getParent(){
        return this.parent;
    }

    public int getDownVotes(){
        return this.downvotes;
    }

    public List<Tag> getTags(){
        return this.tags;
    }
    public void addTag(Tag tag){
        this.tags.add(tag);
    }

    public String toString(){
        String data="Comment Id "+this.Id+" Comment details "+this.commentDetails+" parent details"+this.parent.toString();
        data+=" \n Tags";
        for(Tag tag: this.tags){
           data+="\n";
           data+=tag.toString();
        }
        return data;
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
        this.Id=Idcounter++;
        this.details=details;
    }

    public String toString(){
        return "Tag Id: "+this.Id+" Tag details "+this.details;
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

    public boolean isAvailable(Tag tag){
        return this.frequency.containsKey(tag);
    }

    public HashMap<Tag,Integer> getAllFrequencyData(){
        return this.frequency;
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

class Search{
    private DataBase dataBase;
    Search(DataBase dataBase){
        this.dataBase=dataBase;
    }
  public List<Question> searchQuestions(String phrase){
    String searchStrings[]=phrase.split(" ");
    List<Question> answer=new ArrayList<>();
    for(Question question:this.dataBase.getQuestions()){
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
    return this.dataBase.getQuestions();
  }
  public List<Question> deepSearch(String phrase){
        String searchStrings[]=phrase.split(" ");
        List<Question> answer=new ArrayList<>();
        for(Question question:this.dataBase.getQuestions()){
            String completeDetails=question.getTitle()+question.getDetails();
            for(Answer ans:this.dataBase.getAnswers()){
                completeDetails+=ans.getAnswer();
            }
            for(Comment com:this.dataBase.getComments()){
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

   public List<Question> searchQuestionsOnTag(Tag tag){
      List<Question> answer=new ArrayList<>();
      for(Question question: this.dataBase.getQuestions()){
          if(question.getTags().contains(tag)){
            answer.add(question);
          }
      }
      return answer; 
   }

    public Tag searchtMostFrequentTag(){
      Tag ans=null;
      int max=Integer.MIN_VALUE;
      HashMap<Tag,Integer> currentTagData=this.dataBase.getAllFrequencyData();
    for (Tag tag : currentTagData.keySet()) {
        if(currentTagData.get(tag)>max){
            max=currentTagData.get(tag);
            ans=tag;
        }
    }
    return ans;

    }

    public Question searchMostUpvotesQuestion(){
        Question answerQuestion=null;
        int max=Integer.MIN_VALUE;
        for(Question question:this.dataBase.getQuestions()){
            if(question.getUpvotes()>max){
                max=question.getUpvotes();
                answerQuestion=question;
            }
        }
        return answerQuestion;
    }

    public List<Question> searchQuestionBasedOnMember(Member member){
        List<Question> answerQuestion=new ArrayList<>();

        for(Question question:this.dataBase.getQuestions()){
            if(question.getOwner()==member){
                answerQuestion.add(question);
            }
        }
        return answerQuestion;
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

class ContributionEvaluator{
    ContributionEvaluator(){}
    public void evaluateMemberPerformanceAndAssignBadge(List<Member> members,DataBase dataBase){

        // iterate members
        for(Member member: members){
            int questionContributionCount=0;
            for(Question question: dataBase.getQuestions()){
                if(question.getOwner()==member && (question.getStatus()!=QuestionStatus.DELETED || question.getStatus()!=QuestionStatus.CLOSED) && question.getDownVotes()<3){
                    questionContributionCount+=1;
                }
            }
            int answerContributionCount=0;
            for(Answer answer: dataBase.getAnswers()){
                if(answer.getUser()==member && answer.getDownVotes()<3){
                    answerContributionCount+=1;
                }
            }
            int commentContributionCount=0;
            for(Comment comment: dataBase.getComments()){
                if(comment.getUser()==member && comment.getDownVotes()<3){
                    commentContributionCount+=1;
                }
            }
            if(questionContributionCount>2 && answerContributionCount>4 && commentContributionCount>1){
                member.setBadge(Badge.SILVER);
            }
            else if(questionContributionCount>4 && answerContributionCount>10 && commentContributionCount>5){
                member.setBadge(Badge.GOLD);
            }
            else if(questionContributionCount>10 && answerContributionCount>15 && commentContributionCount>10){
                member.setBadge(Badge.DIAMOND);
            }
            else if(questionContributionCount>25 && answerContributionCount>40 && commentContributionCount>15){
                member.setBadge(Badge.CROWN);
            }
            else if(questionContributionCount>50 && answerContributionCount>100 && commentContributionCount>40){
                member.setBadge(Badge.SILVER);
            }
            else if(questionContributionCount>100 && answerContributionCount>200 && commentContributionCount>10){
                member.setBadge(Badge.SILVER);
            }
        }
    }

}