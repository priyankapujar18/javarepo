import java.util.ArrayList;
import java.util.List;

abstract class Account {
    protected String accountNumber;
    protected String owner;
    protected double balance;

    public Account(String accountNumber, String owner, double balance) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public abstract boolean withdraw(double amount);

    public abstract void displayAccountDetails();
}

class SavingsAccount extends Account {
    private static final double MIN_BALANCE = 500.0;
    private static final double DAILY_LIMIT = 2000.0;

    public SavingsAccount(String accountNumber, String owner, double balance) {
        super(accountNumber, owner, balance);
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > DAILY_LIMIT) {
            System.out.println("Withdrawal exceeds daily limit of $" + DAILY_LIMIT);
            return false;
        }
        if (balance - amount < MIN_BALANCE) {
            System.out.println("Insufficient funds. Minimum balance of $" + MIN_BALANCE + " must be maintained.");
            return false;
        }
        balance -= amount;
        System.out.println("Withdrawn: $" + amount);
        return true;
    }

    @Override
    public void displayAccountDetails() {
        System.out.println("Savings Account [" + accountNumber + "] - Owner: " + owner + ", Balance: $" + balance);
    }
}

class CurrentAccount extends Account {
    private static final double OVERDRAFT_LIMIT = 1000.0;

    public CurrentAccount(String accountNumber, String owner, double balance) {
        super(accountNumber, owner, balance);
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance - amount < -OVERDRAFT_LIMIT) {
            System.out.println("Overdraft limit exceeded.");
            return false;
        }
        balance -= amount;
        System.out.println("Withdrawn: $" + amount);
        return true;
    }

    @Override
    public void displayAccountDetails() {
        System.out.println("Current Account [" + accountNumber + "] - Owner: " + owner + ", Balance: $" + balance);
    }
}

class Bank {
    private List<Account> accounts;

    public Bank() {
        accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
        System.out.println("Account added: " + account.getAccountNumber());
    }

    public Account findAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        Account fromAccount = findAccount(fromAccountNumber);
        Account toAccount = findAccount(toAccountNumber);

        if (fromAccount == null || toAccount == null) {
            System.out.println("Account not found.");
            return;
        }

        if (fromAccount.withdraw(amount)) {
            toAccount.deposit(amount);
            System.out.println("Transfer of $" + amount + " from " + fromAccountNumber + " to " + toAccountNumber + " successful.");
        }
    }

    public void displayAllAccounts() {
        for (Account account : accounts) {
            account.displayAccountDetails();
        }
    }
}

public class BankingSystem {
    public static void main(String[] args) {
        Bank bank = new Bank();

        SavingsAccount savings = new SavingsAccount("S123", "John Doe", 1000.0);
        CurrentAccount current = new CurrentAccount("C123", "Jane Smith", 500.0);

        bank.addAccount(savings);
        bank.addAccount(current);

        savings.deposit(500);
        savings.withdraw(600);
        current.withdraw(700);

        bank.transfer("S123", "C123", 200);

        bank.displayAllAccounts();
    }
}
