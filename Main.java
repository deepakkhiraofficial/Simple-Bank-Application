import java.util.*;

class BankService extends Bank implements Transaction {

    private double balance;
    private final int PASSWORD = 9109;
    private List<String> transactions = new ArrayList<>();

    static Scanner sc = new Scanner(System.in);

    BankService(double balance) {
        this.balance = balance;
    }

    private boolean authenticate() {
        System.out.print("Enter password: ");
        return sc.nextInt() == PASSWORD;
    }

    @Override
    public void deposit() {
        if (!authenticate()) {
            System.out.println("Authentication Failed");
            return;
        }

        try {
            System.out.print("Enter deposit amount: ");
            double amt = sc.nextDouble();

            if (amt <= 0)
                throw new IllegalArgumentException("Invalid Amount");

            balance += amt;
            transactions.add("Deposited: " + amt);
            System.out.println("Balance: " + balance);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void withdraw() {
        if (!authenticate()) return;

        System.out.print("Enter withdraw amount: ");
        double amt = sc.nextDouble();

        if (amt > balance) {
            System.out.println("Insufficient Balance");
            return;
        }

        balance -= amt;
        transactions.add("Withdrawn: " + amt);
        System.out.println("Remaining Balance: " + balance);
    }

    @Override
    void checkBalance() {
        if (authenticate())
            System.out.println("Current Balance: " + balance);
    }

    @Override
    void showCustomerDetails() {
        if (authenticate()) {
            System.out.println("Name: Deepak Kushwah");
            System.out.println("Account Type: Savings");
            System.out.println("Branch: Shukalhari");
        }
    }

    void showTransactionHistory() {
        if (authenticate()) {
            for (String t : transactions)
                System.out.println(t);
        }
    }
}


interface Transaction {
    void deposit();
    void withdraw();
}
abstract class Bank {
    protected String bankName = "UCO Bank";
    protected String ifsc = "UCBA0001544";

    void bankDetails() {
        System.out.println(bankName + " | IFSC: " + ifsc);
    }

    abstract void checkBalance();
    abstract void showCustomerDetails();
}

class Main {
    public static void main(String[] args) {

        Transaction bank = new BankService(10000);
        Scanner sc = new Scanner(System.in);
        int ch;

        do {
            System.out.println("\n1 Deposit");
            System.out.println("2 Withdraw");
            System.out.println("3 Balance");
            System.out.println("4 Details");
            System.out.println("5 Transactions");
            System.out.println("0 Exit");
            System.out.print("Choice: ");

            ch = sc.nextInt();

            switch (ch) {
                case 1: bank.deposit(); break;
                case 2: bank.withdraw(); break;
                case 3: ((BankService)bank).checkBalance(); break;
                case 4: ((BankService)bank).showCustomerDetails(); break;
                case 5: ((BankService)bank).showTransactionHistory(); break;
                case 0: System.out.println("Thank You"); break;
                default: System.out.println("Invalid choice");
            }
        } while (ch != 0);
    }
}
