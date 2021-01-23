package eg.edu.alexu.csd.datastructure.stack.cs03;

import java.util.EmptyStackException;

public class application implements IExpressionEvaluator {
    /**
     *contains from  methods
     *  1- infixtoPostfix
     *     which take the expression
     * @return the expression in Postfix form
     */

    public String infixToPostfix(String expression)  {
        stack vars = new stack();
        stack operators = new stack();
        String item = expression;

        return Post(0, item, vars, operators);
    }

    private String Post(int index, String item, stack vars, stack operators) {
        if (index == item.length()) {

            return getresult(vars, operators);
        } else if (item.charAt(index) == ' ') {
            return Post(index + 1, item, vars, operators);
        } else if (item.charAt(index) == '(') {
            operators.push(item.charAt(index));
        } else if (item.charAt(index) == ')') {
            while ((char) operators.peek() != '(') {
                try {
                    getprant(vars, operators);

                }catch (EmptyStackException e){
                    throw new RuntimeException("Wrong Input");
                }
            }
            operators.pop();
        } else if (item.charAt(index) == '+' || item.charAt(index) == '-') {
            if(item.charAt(index)=='-'){
                if(index==0||item.charAt(index-1)=='+'||item.charAt(index-1)=='-'||item.charAt(index-1)=='*'||item.charAt(index-1)=='/'||item.charAt(index-1)=='('){
                    String  death="-";
                    index++;
                    while (index<item.length()&&item.charAt(index)>='0'&&item.charAt(index)<='9'){
                        death+=Character.toString(item.charAt(index++));
                    }
                    index--;
                    vars.push(death);
                    return Post(index + 1, item, vars, operators);
                }
            }
            while (!operators.isEmpty() && (char) operators.peek() != '(') {
                getprant(vars, operators);
            }
            operators.push(item.charAt(index));
        } else if (item.charAt(index) == '*' || item.charAt(index) == '/') {

            while (!operators.isEmpty() && (char) operators.peek() != '(' && (char) operators.peek() != '+' && (char) operators.peek() != '-') {
                getprant(vars, operators);
            }
            operators.push(item.charAt(index));
        } else {
            String  death="";
            //index++;
            boolean f=false;
            while (index<item.length()&&item.charAt(index)>='0'&&item.charAt(index)<='9'){
                death+=Character.toString(item.charAt(index++));
                f=true;
            }
            if(!f){
                throw new RuntimeException("Wrong Input");
            }
            index--;
            vars.push(death);
        }
        return Post(index + 1, item, vars, operators);


    }

    /**
     *  2- getParent : it is called by infixToPostfix method
     *      *   make the statement from the last operators and last two statement in stack vars
     * @param vars
     * @param operators
     */
    void getprant(stack vars, stack operators)  {
        Object one;
        Object two ;
        Object op ;

        try {


            one = vars.pop();
             two = vars.pop();
            op = operators.pop();
        }catch (EmptyStackException e){
            throw new RuntimeException("Wrong Inout");
        }
        String y = "";
        try {
            y = Character.toString((char) two);
        } catch (Exception e) {
            y += two;
        }
        y+=" ";
        try {

            y += Character.toString((char) one);
        } catch (Exception e) {

            y += one;
        }
        y+=" ";
        // y+=Character.toString((char)one);
        y += Character.toString((char) op);
        // String y=(char) two+one+op;
        vars.push(y);

    }

    /**
     *  3-getResult : it is called by infixToPostfix method
     *      *   it forms the last answer from two stacks vars and operators
     * @param vars
     * @param operators
     * @return
     */
    private String getresult(stack vars, stack operators)  {
        while (!operators.isEmpty()) {
            getprant(vars, operators);
        }

        return ((String) vars.pop()).trim();
    }

    /**
     *  4- evaluate : it get the expression in postfix
     *
     * @param expression
     * postfix expression
     * @return the evaluation of the expression
     */
    public int evaluate(String expression) {
        stack death = new stack();
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '+' || (expression.charAt(i)=='-'&&(i==expression.length()-1||expression.charAt(i+1)==' '))||expression.charAt(i) == '*' || expression.charAt(i) == '/') {
                float o = 0, p = 0;
                try {
                    o = (float) death.pop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    p = (float) death.pop();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                switch (expression.charAt(i)) {
                    case '+':
                        death.push(o + p);
                        break;
                     case '-':

                         death.push(p - o);

                        break;
                    case '*':
                        death.push(p * o);
                        break;
                    case '/':
                        death.push(p / o);
                        break;


                }
                continue;
            }
            if (expression.charAt(i) == ' ') continue;
            String sacrifice = "";
            boolean flag=false;
            if(expression.charAt(i)=='-')flag=true;
            while (i < expression.length() && expression.charAt(i) != ' ' && expression.charAt(i) != '+' && (flag||expression.charAt(i) != '-') && expression.charAt(i) != '*' && expression.charAt(i) != '/') {
                flag=false;
                sacrifice += Character.toString(expression.charAt(i));
                i++;
            }
            i--;
            death.push((float) Integer.parseInt(sacrifice));

        }
        return Math.round((float)(death.pop()));
    }
}

