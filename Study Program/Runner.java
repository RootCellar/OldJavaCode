import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.util.Random;
public class Runner
{
    static ArrayList<Question> qs = new ArrayList<Question>();
    public static void main(String args[]) {
        new File("Subjects").mkdir();
        //System.out.println(readQuestions("Biology"));
        String subject="";
        String input="";
        String use="";
        Scanner in = new Scanner(System.in);
        while(subject.equals("")) {
            System.out.println("What subject?");
            input=in.next();
            use=readQuestions(input);
            System.out.println(use);
            if(use.equals("Success")) {
                subject=input;
            }
        }
        boolean isGoing=true;
        int current;
        while(isGoing) {
            current=nextQuestion();
            answerQuestion(qs.get(current));
        }
    }

    public static void answerQuestion(Question which) {
        Scanner in = new Scanner(System.in);
        System.out.println(which.getQuestion());
        ArrayList<String> temp = which.getQChoices();
        for(int i=0; i<temp.size(); i++) {
            System.out.println((i+1)+". "+temp.get(i));
        }
        boolean done=false;
        String choice="";
        int x=0;
        while(!done) {
            choice=in.next();
            try{
                x=Integer.parseInt(choice);
                if(which.isRight(temp.get(x-1))) {
                    System.out.println("Correct!");
                    done=true;
                }
                else {
                    System.out.println("Incorrect!");
                    System.out.println("The answer was: "+which.getAnswer());
                    done=true;
                }
            }catch(Exception e) {
                System.out.println("Please type a number");
            }
        }
    }

    public static int nextQuestion() {
        Random rand = new Random();
        return rand.nextInt(qs.size());
    }

    public static String readQuestions(String subject) {
        try{
            File file = new File("Subjects/"+subject+".txt");
            if(file.canRead()==false) {
                return "Invalid File";
            }
            String question="";
            ArrayList<String> choices = new ArrayList<String>();
            String ans = "";
            Scanner in = new Scanner(file);
            String use="";
            while(in.hasNextLine()) {
                use=in.nextLine();
                if(use.equals("END")) {
                    qs.add(new Question(ans,choices,question));
                    System.out.println(question+" "+ans+" "+choices);
                    question=new String("");
                    ans=new String("");
                    use=new String("");
                    choices=new ArrayList<String>();
                }
                else if(question.equals("")) {
                    question=new String(use);
                }
                else if(ans.equals("")) {
                    ans=new String(use);
                }
                else {
                    choices.add(new String(use));
                }
            }
            return "Success";
        }catch(Exception e) {
            e.printStackTrace();
            return "Exception Thrown";
        }
    }
}