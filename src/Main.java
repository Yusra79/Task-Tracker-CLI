import java.time.LocalDateTime;
import java.util.List;
public class Main {
    public static void main(String[] args) throws Exception {

        if (args.length==0){
            System.out.println("Usage: task-cli <command> [args]");
            return;
        }
        String command=args[0];
        //addTask
        if (command.equalsIgnoreCase("add")) {
            if (args.length < 2) {
                System.out.println("Error: Description Required.");
                return;
            }
            Task.addTask(args[1]);
        }
        //print filter
        else if (command.equalsIgnoreCase("list")){
            List<Task> tasks=Task.loadTasks();
            String filter=null;
            if (args.length>1){
                filter=args[1];
            }
            for (int i=0;i<tasks.size();i++){
                if (filter==null || filter.equalsIgnoreCase(tasks.get(i).status)){
                    System.out.println("id: "+tasks.get(i).id+"\nDescription: "+tasks.get(i).description+"\nStatus: "+tasks.get(i).status);
                }
            }
        }
        //update description
        else if (command.equalsIgnoreCase("update")) {
            if (args.length<3){
                System.out.println("Usage:task-cli <command> [args]");
                return;
            }
            //exception handling for parseInt in case user enters something other than number
            int id=0;
            try {
                id=Integer.parseInt(args[1]);
            }
            catch (NumberFormatException n){
                System.out.println("Id can only be a number.");
                return;
            }
            String des=args[2];
            boolean found=false;
            List<Task> tasks=Task.loadTasks();
            for(int i=0;i< tasks.size();i++){
                if (tasks.get(i).id==id){
                    tasks.get(i).description=des;
                    tasks.get(i).updatedAt= LocalDateTime.now().toString();
                    found=true;
                }
            }
            if(found){
                Task.saveTasks(tasks);
                System.out.println("Task Updated.");

            }
            else {
                System.out.println("Task Not Found.");
            }
        }
        //deleteTask
        else if (command.equalsIgnoreCase("delete")){
            if(args.length<2){
                System.out.println("Usage: task-cli <command> [args]");
                return;
            }
            //exception handling for parseInt in case user enters something other than number
            int id=0;
            try {
                id=Integer.parseInt(args[1]);
            }
            catch (NumberFormatException n) {
                System.out.println("Id can only be a number.");
                return;
            }
            boolean found=false;
            Task toRemove=null;
            List<Task> tasks=Task.loadTasks();
            for (int i=0;i< tasks.size();i++){
                if (tasks.get(i).id==id){
                    toRemove=tasks.get(i);
                }
            }
            if (toRemove!=null){
                tasks.remove(toRemove);
                found=true;
            }
            if (found){
                Task.saveTasks(tasks);
                System.out.println("Task Deleted.");
            }
            else {
                System.out.println("Task not found.");
            }
        }
        //mark-done
        else if (command.equalsIgnoreCase("mark-done")) {
            if (args.length<2){
                System.out.println("Usage:task-cli <command> [args]");
                return;
            }
            //exception handling for parseInt in case user enters something other than number
            int id=0;
            try {
                id=Integer.parseInt(args[1]);
            }
            catch (NumberFormatException n){
                System.out.println("Id can only be a number.");
                return;
            }

            List<Task> tasks=Task.loadTasks();
            boolean found=false;
            for(int i=0;i< tasks.size();i++){
                if (tasks.get(i).id==id){
                    tasks.get(i).status="done";
                    tasks.get(i).updatedAt= LocalDateTime.now().toString();
                    found=true;
                }
            }
            if(found){
                Task.saveTasks(tasks);
                System.out.println("Task marked-done.");

            }
            else {
                System.out.println("Task Not Found.");
            }
        }
        //mark-in-progress
        else if (command.equalsIgnoreCase("mark-in-progress")){
            if (args.length<2){
                System.out.println("Usage:task-cli <command> [args]");
                return;
            }
            //exception handling for parseInt in case user enters something other than number
            int id=0;
            try {
                id=Integer.parseInt(args[1]);
            }
            catch (NumberFormatException n){
                System.out.println("Id can only be a number.");
                return;
            }
            List<Task> tasks=Task.loadTasks();
            boolean found=false;
            for(int i=0;i< tasks.size();i++){
                if (tasks.get(i).id==id){
                    tasks.get(i).status="in-progress";
                    tasks.get(i).updatedAt= LocalDateTime.now().toString();
                    found=true;
                }
            }
            if(found){
                Task.saveTasks(tasks);
                System.out.println("Task marked-in-progress.");

            }
            else {
                System.out.println("Task Not Found.");
            }
        }
        else {
            System.out.println("Invalid Command.");
        }

    }
}