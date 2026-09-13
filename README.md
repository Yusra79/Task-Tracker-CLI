Project URL: https://roadmap.sh/projects/task-tracker
# Task Tracker CLI

A simple command-line app to track tasks — add, update, delete, mark progress, and list by status. Tasks are stored in a local `tasks.json` file. No external libraries; JSON handling is hand-written.

## Requirements

- JDK 17+

## Compile & Run

```bash
javac -d out src/Main.java src/Task.java
java -cp out Main <command> [arguments]
```

`tasks.json` is created automatically on first use.

## Commands

```bash
java -cp out Main add "Buy groceries"
java -cp out Main update 1 "Buy groceries and cook dinner"
java -cp out Main delete 1
java -cp out Main mark-in-progress 1
java -cp out Main mark-done 1
java -cp out Main list
java -cp out Main list done
java -cp out Main list todo
java -cp out Main list in-progress
```

## Task Fields

`id`, `description`, `status` (`todo` / `in-progress` / `done`), `createdAt`, `updatedAt`

## Error Handling

- Missing/corrupted `tasks.json` → treated as an empty list
- Missing required arguments → usage message instead of crashing
- Non-numeric IDs → rejected with a clear error
- Unknown task ID → "Task Not Found." instead of crashing
