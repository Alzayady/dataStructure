import java.util.HashMap;
import java.util.Stack;

public class InfixToPostfix {
    private Stack<String>operators;
    private Stack<String>items;
    private String infix ;
    private HashMap<String,Integer>priority;
    public InfixToPostfix(String infix){
        this.infix=infix;
        operators=new Stack<String>();
        items=new Stack<String>();
        priority=new HashMap<String, Integer>();
        setupPriority();
    }
    private void setupPriority(){
        priority.put("(",0);
        priority.put(")",0);
        priority.put("or",1);
        priority.put("and",2);
        priority.put("<",3);
        priority.put(">",3);
        priority.put("=",3);
        priority.put("<=",3);
        priority.put(">=",3);
        priority.put("not",4);
        priority.put("+",5);
        priority.put("-",5);
        priority.put("*",6);
        priority.put("/",6);
        priority.put("%",6);
        priority.put("^",7);
    }
    public String Convert() throws Exception {
       String[] extendedInfix=new ExtendExpression(infix).convert().trim().split(" ");
       for(String item : extendedInfix){

            if(!priority.containsKey(item)) {
                items.push(item);
                continue;
            }
            if(item.equals("(")){
                operators.push(item);
                continue;
            }

            if(item.equals(")")){
                while (!operators.peek().equals("(")){
                 removeLastInOperatorStack();
                }
                operators.pop();
                continue;
            }

            while (operators.size()>0&&priority.get(operators.peek())>priority.get(item)){
                removeLastInOperatorStack();
            }
            operators.push(item);

       }
       while (operators.size()>0){
           removeLastInOperatorStack();
       }
        return items.peek();
    }

    private void removeLastInOperatorStack(){
        String first=items.pop();
        String second=items.pop();
        String operator=operators.pop();
        items.push(second.trim()+" "+first.trim()+" "+operator.trim()+" ");
    }
}
