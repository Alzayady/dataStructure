import java.util.HashSet;
import static java.lang.Character.isDigit;
public class ExtendExpression {

    private static final int NOTFOUND =  - 1;
    private  String expression;
    private HashSet<Character> singleOperator;
    private HashSet<String> doubleOperator;
    private HashSet<Character> singleOrDoubleOperator;
    String  extendedExpression="";

    ExtendExpression(String expression){
        this.expression=" "+expression+" ";
        singleOperator=new HashSet<Character>();
        doubleOperator=new HashSet<String>();
        singleOrDoubleOperator=new HashSet<Character>();
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


    public  String convert () throws Exception {

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
        return extendedExpression;
    }

    private  int   appendOperatorTerm(int index){
        if(IsSingleOperator(expression.charAt(index))){   // + * - /  ) (
            extendedExpression+=" "+expression.charAt(index);
            return 0;
        }
        if(index+1<expression.length()&&IsDoubleOperator(expression.substring(index,index+2))){  // <= >= !=
            extendedExpression+=" "+expression.substring(index,index+2);
            return 1;
        }
        if(IsSingleOrDoubleOperator(expression.charAt(index))){  // <  >  =
            extendedExpression+= " " + expression.charAt(index);
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
        extendedExpression+=" "+expression.substring(index,index+3);
    }
    private  boolean IsOr(int index){
        if(index+3>expression.length())return false;
        return expression.substring(index-1,index+3).equalsIgnoreCase(" or ");
    }
    private void appendOperatorWithTwoDigit(int index){
        extendedExpression+=" "+expression.substring(index,index+2);
    }

    private int isIntegerAndAppendIt(int index){
        int length= 0;
        for(int i = index ; i < expression.length() ; i++ ){
            if(isDigit(expression.charAt(i)))length++;
            else  break;
        }
        if(length==0)return NOTFOUND;
        extendedExpression+=" "+expression.substring(index,index+length);
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
        extendedExpression+=" "+expression.substring(index,index+length+2);
        return length+1;
    }
    
    private void defferBetweenNegativeAndMinus() throws Exception {
        String newExtendedExpression="";
        String[] items=extendedExpression.split(" ");
        for(int i = 0 ;i < items.length;i++){

            if(Isnegative(items[i])){
                int len =0 ;
                String last = i==0 ? " " : items[i-1];
                boolean EnterLoop=false;
                 while (i < items.length&&Isnegative(items[i])){
                     len++;
                     i++;
                     EnterLoop=true;
                 }
           if(EnterLoop)  i--;

             if(len%2==0){
                 if(canAddPlus(newExtendedExpression))
                 newExtendedExpression+=" +";
                 continue;
             }

               if(i==0||!isInt(last)){ // must be negative

                   if(i==items.length-1||!isInt(items[i+1]))throw new Exception(); // next element isn't an int

                       newExtendedExpression+=" -"+items[i+1];

                       i++;

               }else  newExtendedExpression+=" "+items[i];
            } else {
                newExtendedExpression+=" "+items[i];
            }
        }

        extendedExpression=newExtendedExpression;
    }
    private boolean isInt(String testString){
        for(int i = 0 ; i <testString.length();i++){
            if(!isDigit(testString.charAt(i)))return false;
        }
        return true;
    }

    private boolean canAddPlus(String  newExtendedExpression) {
        String[] items=newExtendedExpression.split(" ");
        return (items.length!=0&&!isChar((items[items.length-1]))||items[items.length-1]!="+"); // to avoid put two +

    }
    private boolean isChar(String testChar){
        return testChar.length()==1;
    }

    private boolean Isnegative(String c){
       return c.equals("-");
    }
}
