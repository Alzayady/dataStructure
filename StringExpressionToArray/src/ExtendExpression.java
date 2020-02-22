
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import static java.lang.Character.charCount;
import static java.lang.Character.isDigit;

public class ExtendExpression {

    private static final int NOTFOUND =  - 1;
    private  String expression;
    private HashSet<Character> singleOperator; //
    private HashSet<String> doubleOperator;
    private HashSet<Character> singleOrDoubleOperator;
    private ArrayList<Object> extendedExpression;

    ExtendExpression(String expression){
        this.expression=" "+expression+" ";
        singleOperator=new HashSet<Character>();
        doubleOperator=new HashSet<String>();
        singleOrDoubleOperator=new HashSet<Character>();
        extendedExpression=new ArrayList<Object>();
        addSingleOperators();
        addDoubleOperators();
        addSingleOrDoubleOperator();
    }
    /*
       those operators don't contain <  , > , and = because it can appears in two forms
       <  or <=
       = or !=
       > or >=
     */
    private void addSingleOperators(){
        singleOperator.add('+');
        singleOperator.add('-');
        singleOperator.add('*');
        singleOperator.add('/');
        singleOperator.add('%');
        singleOperator.add('(');
        singleOperator.add(')');

    }

    private void addDoubleOperators(){
        doubleOperator.add("<=");
        doubleOperator.add(">=");
        doubleOperator.add("!=");
    }
    private void addSingleOrDoubleOperator(){
        singleOrDoubleOperator.add('<');
        singleOrDoubleOperator.add('>');
        singleOrDoubleOperator.add('=');
    }


    public  Object [] convert () throws Exception {

        for(int i = 0 ;i < expression.length();i++){
            if(expression.charAt(i)==' ')continue;

            int shift = appendOperatorTerm(i);

            if(shift==NOTFOUND){
               if(IsAndOrNot(i)){  // and not ,, if that iterator shifted with 3 indexes

                   appendOperatorWithThreeDigit(i);

                   i+=3;
               }else if(IsOr(i)){  // or

                   appendOperatorWithTwoDigit(i);

                   i+=2;

               }else {
                   shift=isIntegerAndAppendIt(i);

                   if(shift==NOTFOUND){

                       shift=isStringAndAppendIt(i);

                        if(shift==NOTFOUND){

                            throw new Exception(" String starting in index "+ i + " is not defined");

                        }else i+=shift;

                   }else i+=shift;
               }
            }else i+=shift;
        }

        defferBetweenNegativeAndMinus();
        return extendedExpression.toArray();
    }

    private  int   appendOperatorTerm(int index){
        if(IsSingleOperator(expression.charAt(index))){   // + * - /  ) (
            extendedExpression.add(expression.charAt(index));
            return 0;
        }
        if(index+1<expression.length()&&IsDoubleOperator(expression.substring(index,index+2))){  // <= >= !=
            extendedExpression.add(expression.substring(index,index+2));
            return 1;
        }
        if(IsSingleOrDoubleOperator(expression.charAt(index))){  // <  >  =
            extendedExpression.add(expression.charAt(index));
            return 0;
        }

        return NOTFOUND;
    }
    private boolean IsSingleOperator(char testChar){
        return singleOperator.contains(testChar);
    }
    private boolean IsDoubleOperator(String testString){
        return doubleOperator.contains(testString);
    }
    private boolean IsSingleOrDoubleOperator(char textChar){
        return singleOrDoubleOperator.contains(textChar);
    }

    private boolean IsAndOrNot(int index){
        if(index+4>expression.length())return false;
        return expression.substring(index-1,index+4).equalsIgnoreCase(" and ")
                ||  expression.substring(index-1,index+4).equalsIgnoreCase(" not ");
    }

    private void appendOperatorWithThreeDigit(int index){
        extendedExpression.add(expression.substring(index,index+3));
    }
    private  boolean IsOr(int index){
        if(index+3>expression.length())return false;
        return expression.substring(index-1,index+3).equalsIgnoreCase(" or ");
    }
    private void appendOperatorWithTwoDigit(int index){
        extendedExpression.add(expression.substring(index,index+2));
    }
    private int isIntegerAndAppendIt(int index){
        int length= 0;
        for(int i = index ; i < expression.length() ; i++ ){
            if(isDigit(expression.charAt(i)))length++;
            else  break;
        }
        if(length==0)return NOTFOUND;
        extendedExpression.add(Integer.parseInt(expression.substring(index,index+length)));
        return length-1;
    }
    private int isStringAndAppendIt(int index){
        if(expression.charAt(index)!='\"')return NOTFOUND;
        int length = 0;
        boolean foundSecPrantthes=false;
        for(int i = index +1; i < expression.length() ;i++){
            if(expression.charAt(i)=='\"'){
                foundSecPrantthes=true;
                break;
            }
            length++;
        }
        if(!foundSecPrantthes)return NOTFOUND;
        extendedExpression.add(expression.substring(index,index+length+2));
        return length+1;
    }
    
    private void defferBetweenNegativeAndMinus() throws Exception {
        ArrayList<Object>newExtendedExpression=new ArrayList<Object>();
        for(int i = 0 ;i < extendedExpression.size();i++){

            if(Isnegative(extendedExpression.get(i))){
                int len =0 ;
                Object last = i==0 ? null : extendedExpression.get(i-1);
                 while (i < extendedExpression.size()&&Isnegative(extendedExpression.get(i))){
                     len++;
                     i++;
                 }
             i--;
             if(len%2==0){
                 if(canAddPlus(newExtendedExpression))
                 newExtendedExpression.add('+');
                 continue;
             }

               if(i==0||last.getClass()!=Integer.class){ // must be negative

                   if(i==extendedExpression.size()-1||extendedExpression.get(i+1).getClass()!=Integer.class)throw new Exception(); // next element isn't an int

                       newExtendedExpression.add(-1*(int)extendedExpression.get(i+1));

                       i++;

               }else  newExtendedExpression.add(extendedExpression.get(i));
            } else {
                newExtendedExpression.add(extendedExpression.get(i));
            }
        }

        extendedExpression=newExtendedExpression;
    }

    private boolean canAddPlus(ArrayList<Object> newExtendedExpression) {
        return (newExtendedExpression.size()!=0&&(newExtendedExpression.get(newExtendedExpression.size()-1).getClass()!=Character.class||(char)newExtendedExpression.get(newExtendedExpression.size()-1)!='+')); // to avoid put two +

    }

    private boolean Isnegative(Object c){
       return c.getClass()==Character.class&&(char)c=='-';
    }
}
