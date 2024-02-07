package same_exercise1;

import java.util.*;
import java.util.Comparator;
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
    return String.format("Task ID: %d - Description: %s - Priority: %d - Completed: %s", taskId, description,
        priority, completed);
  }

  public static boolean isCompleted(Task task) {
    return task.isCompleted();
  }
}

// TodoListManager class with additional features
class TodoListManager {
  List<Task> tasks = new ArrayList<>();

  // Unchanged methods
  public void addTask(Task task) {
    tasks.add(task);
  }

  public void displayTasks() {
    System.out.println("Task List:");
    tasks.forEach(System.out::println);
  }

  // New feature: Filter tasks based on a condition using a functional interface
  public List<Task> filterTasksByCondition(TaskCondition condition) {
    return tasks.stream().filter(task -> condition.test(task)).collect(Collectors.toList());
  }

  // New feature: Sort tasks using a comparator
  public void sortTasks(Comparator<Task> comparator) {
    tasks.sort(comparator);
  }

  // New feature: Calculate the average priority of tasks
  public double calculateAveragePriority() {
    return tasks.stream().mapToDouble(Task::getPriority).average().orElse(0.0);
  }

  // New feature: Mark a task as completed
  public void markTaskAsCompleted(int taskId) {
    tasks.stream().filter(task -> task.getTaskId() == taskId).findFirst().ifPresent(Task::markAsCompleted);
  }
}

// New functional interface for filtering tasks based on a condition
@FunctionalInterface
interface TaskCondition {
    boolean test(Task task);
    
    // Add a default method to support method reference for static isCompleted
    static boolean isCompleted(Task task) {
        return task.isCompleted();
    }
    // Add another default method to check if a task is not completed
    default boolean isNotCompleted(Task task) {
        return !test(task);
    }
}

// TodoListApp class with the main method
public class TodoListApp1 {
    public static void main(String[] args) {
        TodoListManager todoListManager = new TodoListManager();
        initializeDefaultTasks(todoListManager);
        todoListManager.addTask(new Task(4, "Complete assignment", 2));
        todoListManager.addTask(new Task(5, "Read a book", 1));
        todoListManager.addTask(new Task(6, "Exercise", 3));
        
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
       
        
        // Additional Features
        while (true) {
            System.out.println("\nAdditional Features:");
            System.out.println("1. Filter Completed Tasks");
            System.out.println("2. Sort Tasks by Priority");
            System.out.println("3. Calculate Average Priority");
            System.out.println("4. Mark Task as Completed");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1 -> filterAndDisplay(todoListManager, TaskCondition::isCompleted, "Completed Tasks");
                case 2 -> sortTasksByPriority(todoListManager);
                case 3 -> calculateAndDisplayAveragePriority(todoListManager);
                case 4 -> markTaskAsCompleted(todoListManager, scanner);
                case 5 -> {
                    System.out.println("Exiting the application. Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
    private static void sortTasksByPriority(TodoListManager todoListManager) {
        List<Task> sortedTasks = todoListManager.tasks.stream()
                .sorted(Comparator.comparingInt(Task::getPriority))
                .collect(Collectors.toList());
        System.out.println("\nTasks Sorted by Priority:");
        sortedTasks.forEach(System.out::println);
    }


  private static void initializeDefaultTasks(TodoListManager todoListManager) {
    todoListManager.addTask(new Task(1, "Complete assignment", 2));
    todoListManager.addTask(new Task(2, "Read a book", 1));
    todoListManager.addTask(new Task(3, "Exercise", 3));
  }

  // New feature: Filter tasks based on completion status and display them
  private static void filterAndDisplay(TodoListManager todoListManager, TaskCondition condition, String message) {
      List<Task> filteredTasks = todoListManager.filterTasksByCondition(condition);
      System.out.println("\n" + message + ":");
      if (filteredTasks.isEmpty()) {
          System.out.println("No tasks found.");
      } else {
          filteredTasks.forEach(System.out::println);
      }
  }

  // New feature: Sort tasks by priority using a comparator and display them
  private static void sortAndDisplay(TodoListManager todoListManager, Comparator<Task> comparator, String message) {
    todoListManager.sortTasks(comparator);
    System.out.println("\n" + message + ":");
    todoListManager.displayTasks();
  }

  // New feature: Calculate and display the average priority of tasks
  private static void calculateAndDisplayAveragePriority(TodoListManager todoListManager) {
    double averagePriority = todoListManager.calculateAveragePriority();
    System.out.printf("\nAverage Priority of Tasks: %.2f%n", averagePriority);
  }

  // New feature: Mark a task as completed
  private static void markTaskAsCompleted(TodoListManager todoListManager, Scanner scanner) {
    System.out.print("Enter task ID to mark as completed: ");
    int taskIdToComplete = scanner.nextInt();
    todoListManager.markTaskAsCompleted(taskIdToComplete);

    // Displaying tasks after marking a task as completed
    System.out.println("\nUpdated Task List after marking a task as completed:");
    todoListManager.displayTasks();
  }
}