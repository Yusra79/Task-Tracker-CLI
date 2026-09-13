import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Task{
    int id;
    String description, status, createdAt, updatedAt;
    public String toJson() {
        return "{"
                + "\"id\":" + id + ","
                + "\"description\":\"" + escape(description) + "\","
                + "\"status\":\"" + status + "\","
                + "\"createdAt\":\"" + createdAt + "\","
                + "\"updatedAt\":\"" + updatedAt + "\""
                + "}";
    }
    static String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    static String listToJson(List<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(tasks.get(i).toJson());
            if (i < tasks.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    static Task fromJson(String obj) {
        Map<String, String> fields = new HashMap<>();
        int i = 0;
        int n = obj.length();

        while (i < n) {
            // skip characters until we find a quote (start of a key)
            while (i < n && obj.charAt(i) != '"') i++;
            if (i >= n) break;
            i++; // skip the opening quote of the key

            // read the key until the closing quote
            int keyStart = i;
            while (obj.charAt(i) != '"') i++;
            String key = obj.substring(keyStart, i);
            i++; // skip closing quote

            // skip the colon
            while (obj.charAt(i) != ':') i++;
            i++; // skip colon

            // skip whitespace
            while (obj.charAt(i) == ' ') i++;

            String value;
            if (obj.charAt(i) == '"') {
                // string value
                i++; // skip opening quote
                int valStart = i;
                while (obj.charAt(i) != '"') i++;
                value = obj.substring(valStart, i);
                i++; // skip closing quote
            } else {
                // number value
                int valStart = i;
                while (i < n && obj.charAt(i) != ',' && obj.charAt(i) != '}') i++;
                value = obj.substring(valStart, i).trim();
            }

            fields.put(key, value);
        }

        Task t = new Task();
        t.id = Integer.parseInt(fields.get("id"));
        t.description = fields.get("description");
        t.status = fields.get("status");
        t.createdAt = fields.get("createdAt");
        t.updatedAt = fields.get("updatedAt");
        return t;
    }
    static List<Task> listFromJson(String json) {
        List<Task> tasks = new ArrayList<>();
        json = json.trim();

        // handle empty array case
        if (json.equals("[]") || json.isEmpty()) {
            return tasks;
        }

        int i = 0;
        int n = json.length();

        while (i < n) {
            // find the next '{'
            while (i < n && json.charAt(i) != '{') i++;
            if (i >= n) break;

            int objStart = i;
            int depth = 0;

            // find the matching '}' for this object
            while (i < n) {
                if (json.charAt(i) == '{') depth++;
                if (json.charAt(i) == '}') depth--;
                i++;
                if (depth == 0) break;
            }

            String objText = json.substring(objStart, i);
            tasks.add(fromJson(objText));
        }

        return tasks;
    }
    static List<Task> loadTasks() throws Exception{
        Path path= Paths.get("tasks.json");
        if(!Files.exists(path)){
            return new ArrayList<>();
        }
        String content=Files.readString(path);
        return Task.listFromJson(content);
    }
    static void saveTasks(List<Task> tasks) throws IOException {
        String json=listToJson(tasks);
        Path path=Paths.get("tasks.json");
        Files.writeString(path,json);
    }
    static void addTask(String des) throws Exception{
        List<Task> tasks=loadTasks();
        int newId=1;
        for (int i=0;i<tasks.size();i++){
            if (tasks.get(i).id>=newId){
                newId=tasks.get(i).id+1;
            }
        }
        String date= LocalDateTime.now().toString();
        Task task = new Task();
        task.id=newId;
        task.description=des;
        task.status="todo";
        task.createdAt=date;
        task.updatedAt=date;
        tasks.add(task);
        saveTasks(tasks);
        System.out.println("Task Added Successfully.");
    }
}