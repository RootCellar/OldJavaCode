import java.util.ArrayList;
import java.util.Random;
public class Question
{
    private String answer;
    private ArrayList<String> choices;
    private String question;
    public Question(String a, ArrayList<String> c, String q) {
        answer=a;
        choices=c;
        question=q;
    }
    
    public ArrayList<String> getQChoices() {
        ArrayList<String> temp = new ArrayList<String>();
        for(int i=0; i<choices.size(); i++) {
            temp.add(new String(choices.get(i)));
        }
        temp.add(answer);
        Random rand = new Random();
        ArrayList<String> temp2 = new ArrayList<String>();
        int x;
        while(temp.size()>0) {
            x=rand.nextInt(temp.size());
            temp2.add(temp.get(x));
            temp.remove(x);
        }
        return temp2;
    }
    
    public String getAnswer() {
        return answer;
    }
    
    public String getQuestion() {
        return question;
    }
    
    public boolean isRight(String c) {
        if(c.equals(answer)) {
            return true;
        }
        return false;
    }
    
    public ArrayList<String> getChoices() {
        return choices;
    }
}