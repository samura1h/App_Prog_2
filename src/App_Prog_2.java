import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Клас {@code House} — модель будинку.
 * Містить поля, гетери, сетери та метод toString().
 * Використовується для зберігання інформації про окремий будинок.
 */
class House {
    // --- Приватні поля ---
    private int id;          // Унікальний ідентифікатор будинку
    private int number;      // Номер будинку
    private int area;        // Площа будинку
    private int floor;       // Поверх
    private int roomCount;   // Кількість кімнат
    private String street;   // Назва вулиці

    public House(int id, int number, int area, int floor, int roomCount, String street) {
        this.id = id;
        this.number = number;
        this.area = area;
        this.floor = floor;
        this.roomCount = roomCount;
        this.street = street;
    }

    // --- Гетери (доступ до полів) ---
    public int getId() { return id; }
    public int getNumber() { return number; }
    public int getArea() { return area; }
    public int getFloor() { return floor; }
    public int getRoomCount() { return roomCount; }
    public String getStreet() { return street; }

    // --- Сетери (зміна значень полів) ---
    public void setId(int id) { this.id = id; }
    public void setNumber(int number) { this.number = number; }
    public void setArea(int area) { this.area = area; }
    public void setFloor(int floor) { this.floor = floor; }
    public void setRoomCount(int roomCount) { this.roomCount = roomCount; }
    public void setStreet(String street) { this.street = street; }

    /**
     * Повертає текстове представлення будинку для зручного виведення.
     */
    public String toString() {
        return "ID: " + id +
                " | Номер: " + number +
                " | Площа: " + area +
                " | Поверх: " + floor +
                " | Кімнат: " + roomCount +
                " | Вулиця: " + street;
    }
}

/**
 * Основний клас програми, який керує усім процесом.
 * Містить меню, методи пошуку, введення, зчитування з файлу, збереження, видалення тощо.
 */
public class App_Prog_2 {

    // Сканер використовується для введення даних з клавіатури.
    private static Scanner sc = new Scanner(System.in);

    /**
     * Метод безпечного введення цілих чисел.
     * Якщо користувач введе текст — програма не впаде, а попросить повторити.
     */
    public static int safeIntInput(String msg) {
        while (true) {
            System.out.print(msg);
            if (sc.hasNextInt()) {       // Перевірка: чи введено саме число
                int val = sc.nextInt();   // Зчитуємо число
                sc.nextLine();            // Очищаємо буфер від залишків
                return val;               // Повертаємо коректне значення
            } else {
                System.out.println("Помилка! Введіть число.");
                sc.next(); // Пропускаємо некоректне введення
            }
        }
    }

    /**
     * Перевіряє, чи є ID унікальним серед уже існуючих будинків.
     * @param arr — масив будинків
     * @param count — кількість реально доданих елементів
     * @param id — ID для перевірки
     * @return true, якщо ID не повторюється
     */
    public static boolean isUniqueId(House[] arr, int count, int id) {
        for (int i = 0; i < count; i++) {
            if (arr[i] != null && arr[i].getId() == id)
                return false; // знайдено дубль
        }
        return true; // ID унікальний
    }

    /**
     * Введення даних про новий будинок вручну.
     * Також перевіряється унікальність ID.
     */
    public static House inputHouse(House[] arr, int count) {
        System.out.println("\n=== Введення нового будинку ===");
        int id;

        // Перевірка унікальності ID
        while (true) {
            id = safeIntInput("ID: ");
            if (isUniqueId(arr, count, id)) break;
            System.out.println("Такий ID вже існує! Введіть інший.");
        }

        int number = safeIntInput("Номер будинку: ");
        int area = safeIntInput("Площа: ");
        int floor = safeIntInput("Поверх: ");
        int roomCount = safeIntInput("Кількість кімнат: ");
        System.out.print("Вулиця: ");
        String street = sc.nextLine();
        // Створюємо новий об'єкт House з введеними даними
        return new House(id, number, area, floor, roomCount, street);
    }

    /**
     * Пошук будинків з певною кількістю кімнат.
     */
    public static void findByRoom(House[] arr, int count) {
        int rooms = safeIntInput("\nВведіть кількість кімнат: ");
        int found = 0;
        for (int i = 0; i < count; i++) {
            if (arr[i] != null && arr[i].getRoomCount() == rooms) {
                System.out.println(arr[i]);
                found++;
            }
        }
        System.out.println("\nЗнайдено квартир: "+ found);
        if (found == 0) System.out.println("Будинків з такою кількістю кімнат не знайдено.");
    }

    /**
     * Пошук будинків за кількістю кімнат та діапазоном поверхів.
     */
    public static void findByRoomAndFloor(House[] arr, int count) {
        int rooms = safeIntInput("\nКількість кімнат: ");
        int minFloor = safeIntInput("Мінімальний поверх: ");
        int maxFloor = safeIntInput("Максимальний поверх: ");
        int found = 0;

        for (int i = 0; i < count; i++) {
            if (arr[i] != null &&
                    arr[i].getRoomCount() == rooms &&
                    arr[i].getFloor() >= minFloor &&
                    arr[i].getFloor() <= maxFloor) {
                System.out.println(arr[i]);
                found++;
            }
        }
        System.out.println("\nЗнайдено квартир: "+ found);

        if (found == 0)
            System.out.println("Немає будинків, які підходять під ці умови.");
    }

    /**
     * Пошук будинків з площею більшою за задану.
     */
    public static void findByArea(House[] arr, int count) {
        int minArea = safeIntInput("\nВведіть мінімальну площу: ");
        int found = 0;
        for (int i = 0; i < count; i++) {
            if (arr[i] != null && arr[i].getArea() > minArea) {
                System.out.println(arr[i]);
                found++;
            }
        }
        System.out.println("\nЗнайдено квартир: "+ found);

        if (found == 0) System.out.println("Будинків з більшою площею не знайдено.");
    }

    /**
     * Зчитування будинків з файлу з перевіркою унікальності ID.
     * Пропускає дублікати, як у файлі, так і в масиві.
     *
     * @param arr - масив будинків
     * @param startIndex - індекс, з якого починати додавати нові будинки
     * @return кількість будинків після зчитування
     */
    public static int readFromFile(House[] arr, int startIndex) {
        System.out.print("\nВведіть шлях до файлу для зчитування: ");
        String fileName = sc.nextLine(); // Зчитуємо шлях до файлу від користувача

        int count = startIndex; // Поточна кількість будинків у масиві

        try (Scanner fileScanner = new Scanner(new File(fileName))) { // Відкриваємо файл
            while (fileScanner.hasNext()) { // Читаємо до кінця файлу
                int id = fileScanner.nextInt();       // Зчитуємо ID будинку
                int number = fileScanner.nextInt();   // Номер будинку
                int area = fileScanner.nextInt();     // Площа
                int floor = fileScanner.nextInt();    // Поверх
                int roomCount = fileScanner.nextInt();// Кількість кімнат
                String street = fileScanner.next();   // Вулиця (одне слово)

                // Перевірка на дубль ID: як серед вже існуючих будинків, так і серед тих, що зчитані зараз
                if (isUniqueId(arr, count, id)) {
                    arr[count++] = new House(id, number, area, floor, roomCount, street);
                } else {
                    // Якщо ID вже є у масиві, пропускаємо і виводимо повідомлення
                    System.out.println("Пропущено будинок з дубльованим ID: " + id);
                }
            }
            System.out.println("Дані успішно зчитані з файлу!");
        } catch (FileNotFoundException e) {
            System.out.println("Файл не знайдено: " + fileName);
        }

        return count; // Повертаємо нову кількість будинків
    }


    /**
     * Видалення будинку за ID (зі зсувом елементів масиву).
     */
    public static int deleteById(House[] arr, int count) {
        int id = safeIntInput("\nВведіть ID будинку для видалення: ");
        int index = -1;

        // Знаходимо індекс об’єкта
        for (int i = 0; i < count; i++) {// Проходимо по всіх елементах масиву
            if (arr[i] != null && arr[i].getId() == id) {
                index = i;//Якщо знайшли будинок → запам’ятовуємо його індекс у index і виходимо з циклу (break).
                break;
            }
        }

        if (index == -1) {
            System.out.println("Будинок не знайдено.");
            return count;
        }

        // Зсуваємо елементи масиву вліво
        for (int i = index; i < count - 1; i++) {
            arr[i] = arr[i + 1];
        }

        arr[count - 1] = null; // останній елемент обнуляємо
        System.out.println("Будинок з ID " + id + " видалено.");
        return count - 1;
    }

    /**
     * Збереження всіх будинків у файл.
     * Користувач сам вводить назву файлу.
     */
    public static void saveToFile(House[] arr, int count) {
        System.out.print("\nВведіть шлях до файлу для збереження: ");
        String fileName = sc.nextLine();

        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (int i = 0; i < count; i++) {
                if (arr[i] != null) {
                    House h = arr[i];
                    writer.println(h.getId() + " " +
                            h.getNumber() + " " +
                            h.getArea() + " " +
                            h.getFloor() + " " +
                            h.getRoomCount() + " " +
                            h.getStreet());
                }
            }
            System.out.println("Дані успішно збережено у файл: " + fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Помилка запису у файл: " + fileName);
        }
    }

    /**
     * Головний метод програми — точка входу.
     * Тут реалізоване меню користувача.
     */
    public static void main(String[] args) {
        final int MAX_HOUSES = 500;        // Максимальна кількість будинків
        House[] houses = new House[MAX_HOUSES]; // Масив об’єктів типу House
        int count = 0;                     // Поточна кількість заповнених елементів


        while (true) {
            // --- Меню ---
            System.out.println("\n===== МЕНЮ =====");
            System.out.println("1 - Додати будинок вручну");
            System.out.println("2 - Пошук за кількістю кімнат");
            System.out.println("3 - Пошук за кількістю кімнат і поверхами");
            System.out.println("4 - Пошук за мінімальною площею");
            System.out.println("5 - Зчитати будинки з файлу");
            System.out.println("6 - Видалити будинок за ID");
            System.out.println("7 - Зберегти та Вийти");
            System.out.println("================");

            int choice = safeIntInput("Ваш вибір: ");

            switch (choice) {
                case 1:
                    if (count >= MAX_HOUSES)
                        System.out.println("Досягнуто максимум!");
                    else
                        houses[count++] = inputHouse(houses, count);
                    break;
                case 2:
                    if (count == 0) System.out.println("Дані відсутні.");
                    else findByRoom(houses, count);
                    break;
                case 3:
                    if (count == 0) System.out.println("Дані відсутні.");
                    else findByRoomAndFloor(houses, count);
                    break;
                case 4:
                    if (count == 0) System.out.println("Дані відсутні.");
                    else findByArea(houses, count);
                    break;
                case 5:
                    count = readFromFile(houses, count);
                    break;
                case 6:
                    if (count == 0) System.out.println("Дані відсутні.");
                    else count = deleteById(houses, count);
                    break;
                case 7:
                    saveToFile(houses, count);
                    System.out.println("Дані Збережено\nЗавершення програми.");
                    return;
                default:
                    System.out.println("Невірний вибір!");
            }
        }
    }
}
