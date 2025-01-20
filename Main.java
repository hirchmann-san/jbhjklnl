import java.util.Scanner;
import java.util.*;

public class Main {
    static class BankAccount {
        public String name; // Imię właściciela konta
        public double balance; // Saldo konta
        public String accountNumber; // Unikalny numer konta
        public ArrayList<String> transactions; // Historia transakcji

        public BankAccount(String name, double balance, String accountNumber) {
            // Konstruktor do tworzenia nowego konta
            this.name = name;
            this.balance = balance;
            this.accountNumber = accountNumber;
            this.transactions = new ArrayList<>();
        }

        public void addTransaction(String transaction) {
            // Dodaje opis tekstowy transakcji do historii
            transactions.add(transaction);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Do odczytu danych od użytkownika
        Random random = new Random(); // Do generowania losowych danych (np. numeru konta)
        Map<String, BankAccount> accounts = new HashMap<>(); // Słownik do przechowywania kont według ich numerów

        while (true) {
            Menu(); // Wyświetlenie menu głównego
            int choice = scanner.nextInt(); // Odczyt wyboru użytkownika
            scanner.nextLine(); // Czyszczenie bufora

            switch (choice) {
                case 1:
                    createAccount(scanner, random, accounts); // Tworzenie nowego konta
                    break;
                case 2:
                    plus(scanner, accounts); // Wpłata pieniędzy na konto
                    break;
                case 3:
                    minus(scanner, accounts); // Wypłata pieniędzy z konta
                    break;
                case 4:
                    transfer(scanner, accounts); // Przelew pieniędzy między kontami
                    break;
                case 5:
                    Balance(scanner, accounts); // Wyświetlenie salda konta
                    break;
                case 6:
                    TransactionHistory(scanner, accounts); // Wyświetlenie historii transakcji
                    break;
                case 7:
                    System.out.println("Wyjście z programu."); // Zakończenie programu
                    return; // Wyjście z metody main
                default:
                    System.out.println("Niepoprawna opcja. Spróbuj ponownie."); // Obsługa niepoprawnego wejścia
            }
        }
    }

    private static void Menu() {
        // Wyświetlenie menu głównego
        System.out.println("\n--- Symulator Banku ---");
        System.out.println("1. Załóż konto");
        System.out.println("2. Wpłata pieniędzy");
        System.out.println("3. Wypłata pieniędzy");
        System.out.println("4. Przelew na inne konto");
        System.out.println("5. Wyświetlenie salda");
        System.out.println("6. Historia transakcji");
        System.out.println("7. Wyjście");
        System.out.print("Wybierz opcję: ");
    }

    private static void createAccount(Scanner scanner, Random random, Map<String, BankAccount> accounts) {
        // Tworzenie nowego konta
        System.out.print("Podaj swoje imię: ");
        String name = scanner.nextLine(); // Odczyt imienia użytkownika
        String accountNumber = "SAN" + (1000 + random.nextInt(9000)); // Generowanie unikalnego numeru konta
        System.out.print("Podaj kwotę początkowego depozytu: ");
        double balance = scanner.nextDouble(); // Odczyt początkowego salda
        scanner.nextLine(); // Czyszczenie bufora

        BankAccount account = new BankAccount(name, balance, accountNumber); // Tworzenie nowego konta
        accounts.put(accountNumber, account); // Zapisanie konta w słowniku
        account.addTransaction("Konto utworzone z saldem " + balance); // Dodanie pierwszej transakcji
        System.out.println("Konto utworzone! Numer twojego konta: " + accountNumber); // Wyświetlenie informacji o koncie
    }

    private static void plus(Scanner scanner, Map<String, BankAccount> accounts) {
        // Wpłata pieniędzy na konto
        System.out.println("Podaj numer swojego konta: ");
        String accountNumber = scanner.nextLine(); // Odczyt numeru konta

        if (accounts.containsKey(accountNumber)) { // Sprawdzenie istnienia konta
            System.out.print("Podaj kwotę do wpłaty: ");
            double deposit = scanner.nextDouble(); // Odczyt kwoty depozytu
            scanner.nextLine(); // Czyszczenie bufora

            if (deposit > 0) { // Sprawdzenie dodatniej kwoty
                accounts.get(accountNumber).balance += deposit; // Zwiększenie salda
                accounts.get(accountNumber).addTransaction("Wpłacono " + deposit); // Dodanie wpisu do historii
                System.out.println("Twoje saldo = " + accounts.get(accountNumber).balance); // Wyświetlenie nowego salda
            } else {
                System.out.println("Nie można wpłacić ujemnej kwoty."); // Komunikat o błędzie
            }
        } else {
            System.out.println("Nie znaleziono konta."); // Komunikat o błędzie, jeśli konto nie istnieje
        }
    }

    private static void minus(Scanner scanner, Map<String, BankAccount> accounts) {
        // Wypłata pieniędzy z konta
        System.out.println("Podaj numer swojego konta: ");
        String accountNumber = scanner.nextLine(); // Odczyt numeru konta

        if (accounts.containsKey(accountNumber)) { // Sprawdzenie istnienia konta
            System.out.print("Podaj kwotę do wypłaty: ");
            double amount = scanner.nextDouble(); // Odczyt kwoty do wypłaty
            scanner.nextLine(); // Czyszczenie bufora

            if (amount > 0 && accounts.get(accountNumber).balance >= amount) { // Sprawdzenie warunków wypłaty
                accounts.get(accountNumber).balance -= amount; // Zmniejszenie salda
                accounts.get(accountNumber).addTransaction("Wypłacono " + amount); // Dodanie wpisu do historii
                System.out.println("Twoje saldo = " + accounts.get(accountNumber).balance); // Wyświetlenie nowego salda
            } else {
                System.out.println("Niewystarczające środki lub podano nieprawidłową kwotę."); // Komunikat o błędzie
            }
        } else {
            System.out.println("Nie znaleziono konta."); // Komunikat o błędzie, jeśli konto nie istnieje
        }
    }

    private static void transfer(Scanner scanner, Map<String, BankAccount> accounts) {
        // Przelew pieniędzy między kontami
        System.out.println("Podaj numer swojego konta: ");
        String senderAccountNumber = scanner.nextLine(); // Odczyt numeru konta nadawcy

        if (accounts.containsKey(senderAccountNumber)) { // Sprawdzenie istnienia konta nadawcy
            System.out.println("Podaj numer konta odbiorcy: ");
            String recipientAccountNumber = scanner.nextLine(); // Odczyt numeru konta odbiorcy

            if (accounts.containsKey(recipientAccountNumber)) { // Sprawdzenie istnienia konta odbiorcy
                System.out.print("Podaj kwotę do przelewu: ");
                double transferAmount = scanner.nextDouble(); // Odczyt kwoty do przelewu
                scanner.nextLine(); // Czyszczenie bufora

                if (transferAmount > 0 && accounts.get(senderAccountNumber).balance >= transferAmount) {
                    // Sprawdzenie warunków przelewu
                    accounts.get(senderAccountNumber).balance -= transferAmount; // Zmniejszenie salda nadawcy
                    accounts.get(recipientAccountNumber).balance += transferAmount; // Zwiększenie salda odbiorcy
                    accounts.get(senderAccountNumber).addTransaction(
                            "Przelano " + transferAmount + " na konto " + recipientAccountNumber); // Dodanie wpisu do historii nadawcy
                    accounts.get(recipientAccountNumber).addTransaction(
                            "Otrzymano " + transferAmount + " z konta " + senderAccountNumber); // Dodanie wpisu do historii odbiorcy
                    System.out.println("Przelew wykonany."); // Powiadomienie o udanym przelewie
                } else {
                    System.out.println("Niewystarczające środki lub podano nieprawidłową kwotę."); // Komunikat o błędzie
                }
            } else {
                System.out.println("Nie znaleziono konta odbiorcy."); // Komunikat o błędzie, jeśli konto odbiorcy nie istnieje
            }
        } else {
            System.out.println("Nie znaleziono konta nadawcy."); // Komunikat o błędzie, jeśli konto nadawcy nie istnieje
        }
    }

    private static void Balance(Scanner scanner, Map<String, BankAccount> accounts) {
        // Wyświetlenie salda
        System.out.println("Podaj numer swojego konta: ");
        String accountNumber = scanner.nextLine(); // Odczyt numeru konta

        if (accounts.containsKey(accountNumber)) { // Sprawdzenie istnienia konta
            System.out.println("Twoje saldo: " + accounts.get(accountNumber).balance); // Wyświetlenie salda
        } else {
            System.out.println("Nie znaleziono konta."); // Komunikat o błędzie, jeśli konto nie istnieje
        }
    }

    private static void TransactionHistory(Scanner scanner, Map<String, BankAccount> accounts) {
        // Wyświetlenie historii transakcji
        System.out.println("Podaj numer swojego konta: ");
        String accountNumber = scanner.nextLine(); // Odczyt numeru konta

        if (accounts.containsKey(accountNumber)) { // Sprawdzenie istnienia konta
            System.out.println("Historia transakcji: ");
            for (String transaction : accounts.get(accountNumber).transactions) { // Przejście po liście transakcji
                System.out.println(transaction); // Wyświetlenie każdej transakcji
            }
        } else {
            System.out.println("Nie znaleziono konta."); // Komunikat o błędzie, jeśli konto nie istnieje
        }
    }
}

