package exercise1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class Task {
  private int taskId;
  private String description;
  private int priority;
  private boolean completed;

  public Task(int taskId, String description, int priority) {
    this.taskId = taskId;
    this.description = description;
    this.priority = priority;
    this.completed = false;
  }

  public int getTaskId() {
    return taskId;
  }

  public String getDescription() {
    return description;
  }

  public int getPriority() {
    return priority;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void markAsCompleted() {
    this.completed = true;
  }

  @Override
  public String toString() {
    return "TaskId = " + taskId + ", description = " + description + ", priority = " + priority + ", completed="
        + completed;
  }

}

class TodoListManager {
  List<Task> tasks;

  public TodoListManager() {
    this.tasks = new ArrayList<>();
  }

  public void addTask(Task task) {
    tasks.add(task);
  }

  public void displayTasks() {
    System.out.println("Task List: ");
    tasks.forEach(System.out::println);
  }

  public List<Task> filterTask(Predicate<Task> pre) {
    return tasks.stream().filter(pre).collect(Collectors.toList());

  }

  public List<Task> findTasksByPriority(int priority) {
    return filterTask(t -> t.getPriority() == priority);

  }

  public void updateTask(Predicate<Task> pre, Consumer<Task> function) {
    tasks.stream().filter(pre).findFirst().ifPresent(function);

  }

  public void markTaskAsCompleted(int taskId) {
    updateTask(t -> t.getTaskId() == taskId, Task::markAsCompleted);
  }
}

public class TodoListApp {
  public static void main(String[] args) {
    TodoListManager todoListManager = new TodoListManager();
    todoListManager.addTask(new Task(1, "Complete assignment", 2));
    todoListManager.addTask(new Task(2, "Read a book", 1));
    todoListManager.addTask(new Task(3, "Exercise", 3));

    Scanner scanner = new Scanner(System.in);

    // Code for testing basic implementation
    System.out.println("Initial Task List:");
    todoListManager.displayTasks();

    // Adding a new task
    System.out.print("\nEnter task description: ");
    String newDescription = scanner.nextLine();
    System.out.print("Enter task priority: ");
    int newPriority = scanner.nextInt();
    Task newTask = new Task(todoListManager.tasks.size() + 1, newDescription, newPriority);
    todoListManager.addTask(newTask);

    // Displaying tasks after adding a new task
    System.out.println("\nUpdated Task List:");
    todoListManager.displayTasks();

    // Finding and displaying tasks by priority
    System.out.print("\nEnter priority to find tasks: ");
    int priorityToFind = scanner.nextInt();
    List<Task> tasksByPriority = todoListManager.findTasksByPriority(priorityToFind);
    System.out.println("Tasks with Priority " + priorityToFind + ":");
    tasksByPriority.forEach(task ->  System.out.println("Task ID: " + task.getTaskId() + " - Description: " + task.getDescription() + " - Priority: "
          + task.getPriority() + " - Completed: " + task.isCompleted()));
    

    // Marking a task as completed
    System.out.print("\nEnter task ID to mark as completed: ");
    int taskIdToComplete = scanner.nextInt();
    todoListManager.markTaskAsCompleted(taskIdToComplete);

    // Displaying tasks after marking a task as completed
    System.out.println("\nUpdated Task List after marking a task as completed:");
    todoListManager.displayTasks();

    scanner.close();
  }
}