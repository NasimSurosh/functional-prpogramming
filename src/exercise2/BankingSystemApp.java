package exercise2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Account {
  private int accountNumber;
  private String accountHolder;
  private double balance;
  private String accountType;

  public Account(int accountNumber, String accountHolder, double balance, String accountType) {
    this.accountNumber = accountNumber;
    this.accountHolder = accountHolder;
    this.balance = balance;
    this.accountType = accountType;
  }

  public int getAccountNumber() {
    return accountNumber;
  }

  public String getAccountHolder() {
    return accountHolder;
  }

  public double getBalance() {
    return balance;
  }

  public String getAccountType() {
    return accountType;
  }

  public void deposit(double amount) {
    balance += amount;
    System.out.println("Deposit successful. New balance: $" + balance);
  }

  public void withdraw(double amount) {
    if (amount <= balance) {
      balance -= amount;
      System.out.println("Withdrawal successful. New balance: $" + balance);
    } else {
      System.out.println("Insufficient funds!");
    }
  }

  public void transfer(Account destinationAccount, double amount) {
    if (amount <= balance) {
      balance -= amount;
      destinationAccount.deposit(amount);
      System.out.println("Transfer successful. New balance: $" + balance);
    } else {
      System.out.println("Insufficient funds for transfer!");
    }
  }

  @Override
  public String toString() {
    return "Account [accountNumber=" + accountNumber + ", accountHolder=" + accountHolder + ", balance=" + balance
        + ", accountType=" + accountType + "]";
  }

}

class Bank {
  private List<Account> accounts;
  private int nextAccountNumber;

  public Bank() {
    this.accounts = new ArrayList<>();
    this.nextAccountNumber = 1;
  }

  public void openAccount(String accountHolder, double initialBalance, String accountType) {
    System.out.println("write your name:");
    Account newAccount = new Account(nextAccountNumber++, accountHolder, initialBalance, accountType);
    accounts.add(newAccount);
    System.out.println("Account opened successfully. Account number: " + newAccount.getAccountNumber());
  }

  public void displayAccounts() {
    System.out.println("Accounts in the Bank:");
    accounts.forEach(System.out::println);
  }

  public List<Account> getAccounts() {
    return accounts;
  }
}

public class BankingSystemApp {
  public static void display() {
    System.out.println("\nBanking System Menu:");
    System.out.println("1. Open Account");
    System.out.println("2. Display Accounts");
    System.out.println("3. Deposit");
    System.out.println("4. Withdraw");
    System.out.println("5. Transfer");
    System.out.println("6. Exit");
  }

  public static void openAccount( Bank bank) {
    Scanner scan = new Scanner(System.in);
    System.out.println("Open acount ");
    String newAccount = scan.nextLine();
    System.out.println("Enter the balance!");
    double balance = scan.nextDouble();
    scan.nextLine();
    System.out.println("Enter type of account");
    String account = scan.nextLine().toUpperCase();
    bank.openAccount(newAccount, balance, account);
    scan.close();
  }

  private static void performTransaction(Scanner scan, Bank bank, String transactionType) {
    System.out.print("Enter account number: ");
    int accountNumber = scan.nextInt();
    System.out.print("Enter amount: $");
    double amount = scan.nextDouble();
    scan.nextLine(); // Consume newline

    Account account = findAccountByNumber(bank, accountNumber);
    if (account != null) {
      switch (transactionType) {
      case "deposit":
        account.deposit(amount);
        break;
      case "withdraw":
        account.withdraw(amount);
        break;
      }
    } else {
      System.out.println("Account not found!");
    }
  }

  private static void performTransfer(Scanner scanner, Bank bank) {
    System.out.print("Enter source account number: ");
    int sourceAccountNumber = scanner.nextInt();
    System.out.print("Enter destination account number: ");
    int destinationAccountNumber = scanner.nextInt();
    System.out.print("Enter transfer amount: $");
    double transferAmount = scanner.nextDouble();
    scanner.nextLine(); // Consume newline

    Account sourceAccount = findAccountByNumber(bank, sourceAccountNumber);
    Account destinationAccount = findAccountByNumber(bank, destinationAccountNumber);

    if (sourceAccount != null && destinationAccount != null) {
        sourceAccount.transfer(destinationAccount, transferAmount);
    } else {
        System.out.println("One or both accounts not found!");
    }
  }



  private static Account findAccountByNumber(Bank bank, int accountNumber) {
    return bank.getAccounts()
        .stream()
        .filter(acc -> acc.getAccountNumber() == accountNumber)
        .findFirst()
        .orElse(null);
  }
  

    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);
        display();
   
        while (true) {
           
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    openAccount(bank);
                    break;
                case 2:
                    bank.displayAccounts();
                    break;
                case 3:
                    performTransaction(scanner, bank, "deposit");
                    break;
                case 4:
                    performTransaction(scanner, bank, "withdraw");
                    break;
                case 5:
                    performTransfer(scanner, bank);
                    break;
                case 6:
                    System.out.println("Exiting the Banking System. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
   }
    }
