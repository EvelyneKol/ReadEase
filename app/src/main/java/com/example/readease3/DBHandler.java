package com.example.readease3;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.util.Log;


public class DBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the book table
        String createTableQuery = "CREATE TABLE book (" +
                "isbn TEXT PRIMARY KEY," +
                "title TEXT NOT NULL DEFAULT 'unknown'," +
                "book_description TEXT NOT NULL," +
                "book_author TEXT NOT NULL DEFAULT 'unknown'," +
                "pages INTEGER NOT NULL DEFAULT 0," +
                "category TEXT CHECK(category IN ('Αισθηματικα', 'Επιστημονικης Φαντασιας', 'Αστυνομικα', 'Αυτοβελτιωση'))" +
                ")";

        db.execSQL(createTableQuery);

        // Create the user table
        String createUserTableQuery = "CREATE TABLE user (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "type TEXT CHECK(type IN ('USER', 'WRITER'))," +
                "mail TEXT NOT NULL," +
                "phone INTEGER NOT NULL," +
                "user_location TEXT NOT NULL," +
                "points INTEGER" +
                ")";
        db.execSQL(createUserTableQuery);
        // Create the user table
        String createAudioBookTableQuery = "CREATE TABLE AudioBook (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "isbn TEXT ," +
                "language TEXT ," +
                "title TEXT NOT NULL DEFAULT 'unknown'," +
                "pages INTEGER NOT NULL DEFAULT 0," +
                "date TEXT NOT NULL," +
                "price INTEGER NOT NULL" +
                ")";
        db.execSQL(createAudioBookTableQuery);

        // Create the wallet table
        String createWalletTableQuery = "CREATE TABLE wallet (" +
                "user_id INTEGER PRIMARY KEY," +
                "money REAL NOT NULL DEFAULT 0.0," +
                "FOREIGN KEY (user_id) REFERENCES user(user_id) ON UPDATE CASCADE ON DELETE CASCADE" +
                ")";
        db.execSQL(createWalletTableQuery);


        // Create the ebook table
        String createEbookTableQuery = "CREATE TABLE ebook (" +
                "ebook_id INTEGER PRIMARY KEY ," +
                "ebook_author TEXT NOT NULL DEFAULT 'unknown'," +
                "ebook_description TEXT NOT NULL," +
                "price INTEGER NOT NULL DEFAULT 0" +
                ")";

        db.execSQL(createEbookTableQuery);

        // Create the events table
        String createEventsTableQuery = "CREATE TABLE events (" +
                "event_id INTEGER PRIMARY KEY NOT NULL," +
                "event_title TEXT NOT NULL," +
                "event_description TEXT NOT NULL DEFAULT 'unknown'," +
                "date DATE NOT NULL," +
                "start_time TEXT NOT NULL," +
                "end_time TEXT NOT NULL," +
                "event_location TEXT NOT NULL," +
                "capacity INTEGER NOT NULL," +
                "writer_creator INTEGER," +
                "CONSTRAINT CREATOR FOREIGN KEY (writer_creator) REFERENCES user(user_id) ON UPDATE CASCADE ON DELETE CASCADE" +
                ")";
        db.execSQL(createEventsTableQuery);


        // Create the participate table
        String createParticipateTableQuery = "CREATE TABLE participate (" +
                "participants_user_id INTEGER NOT NULL," +
                "participate_event_id INTEGER NOT NULL," +
                "PRIMARY KEY (participants_user_id, participate_event_id)," +
                "CONSTRAINT PARTICIPANTS FOREIGN KEY (participants_user_id) REFERENCES user(user_id) ON UPDATE CASCADE ON DELETE CASCADE," +
                "CONSTRAINT EVENT FOREIGN KEY (participate_event_id) REFERENCES events(event_id) ON UPDATE CASCADE ON DELETE CASCADE" +
                ")";
        db.execSQL(createParticipateTableQuery);


        // Create the selling_ad table
        String createSellingAdTableQuery = "CREATE TABLE selling_ad (" +
                "selling_ad_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "selling_ad_isbn TEXT NOT NULL," +
                "selling_price REAL NOT NULL," +
                "selling_publisher INTEGER," +
                "selling_status TEXT CHECK(selling_status IN ('ΚΑΚΗ', 'ΜΕΤΡΙΑ', 'ΚΑΛΗ', 'ΠΟΛΥ ΚΑΛΗ'))," +
                "FOREIGN KEY (selling_ad_isbn) REFERENCES book(isbn) ON UPDATE CASCADE ON DELETE CASCADE," +
                "FOREIGN KEY (selling_publisher) REFERENCES user(user_id) ON UPDATE CASCADE ON DELETE CASCADE" +
                ")";
        db.execSQL(createSellingAdTableQuery);

       // Δημιουργία του πίνακα purchase
        String createPurchaseTableQuery = "CREATE TABLE purchase (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "buyer_id INTEGER NOT NULL," +
                "type_purchase TEXT NOT NULL," +
                "item_id INTEGER NOT NULL," +
                "price REAL," +
                "FOREIGN KEY (item_id) REFERENCES selling_ad(selling_ad_id) ON UPDATE CASCADE ON DELETE CASCADE" +
                ")";
        db.execSQL(createPurchaseTableQuery);
        // Create the review table
        String createReviewTableQuery = "CREATE TABLE review ("
                + "review_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "reviewer INT NOT NULL,"
                + "review TEXT,"
                + "reviewed_book VARCHAR(155) NOT NULL,"
                + "FOREIGN KEY (reviewer) REFERENCES user(user_id) ON UPDATE CASCADE ON DELETE CASCADE,"
                + "FOREIGN KEY (reviewed_book) REFERENCES book(isbn) ON UPDATE CASCADE ON DELETE CASCADE"
                + ")";
        db.execSQL(createReviewTableQuery);

        //quiz table
        String createQuizTableQuery = "CREATE TABLE quiz (" +
                "quiz_id TEXT PRIMARY KEY," +
                "title TEXT NOT NULL" +
                ");";
        db.execSQL(createQuizTableQuery);

        //questions table
        String createQuestionsTableQuery = "CREATE TABLE questions (" +
                "question_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "quiz_id INTEGER NOT NULL," +
                "question_text TEXT NOT NULL," +
                "FOREIGN KEY (quiz_id) REFERENCES quiz(quiz_id) ON DELETE CASCADE" +
                ");";
        db.execSQL(createQuestionsTableQuery);

        //options table
        String createOptionsTableQuery = "CREATE TABLE options (" +
                "option_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "question_id INTEGER NOT NULL," +
                "option_text TEXT NOT NULL," +
                "is_correct INTEGER NOT NULL, " + // 0 for false, 1 for true
                "FOREIGN KEY (question_id) REFERENCES questions(question_id) ON DELETE CASCADE" +
                ");";
        db.execSQL(createOptionsTableQuery);

        String createTablecouponQuery = "CREATE TABLE coupons (" +
                "id_coupon INTEGER PRIMARY KEY AUTOINCREMENT," +
                "type TEXT CHECK(type IN ('10% Έκπτωση σε αγορά Ebook', '50% Έκπτωση σε αγορά βιβλίου', 'Δωρεάν μεταφορικά'))," +
                "user_name TEXT NOT NULL," +
                "expired_date TEXT NOT NULL," +
                "used TEXT CHECK(used IN ('ΝΑΙ', 'ΟΧΙ'))," +
                "FOREIGN KEY (user_name) REFERENCES user(name) ON UPDATE CASCADE ON DELETE CASCADE" +
                ")";
        db.execSQL(createTablecouponQuery);

        // Create the borrow_ad table
        String createBorrowAdTableQuery = "CREATE TABLE borrow_ad (" +
                "borrow_ad_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "borrow_ad_isbn TEXT NOT NULL," +
                "start_date TEXT NOT NULL," +
                "end_date TEXT NOT NULL," +
                "borrow_publisher INTEGER," +
                "borrow_status TEXT CHECK(borrow_status IN ('ΚΑΚΗ', 'ΜΕΤΡΙΑ', 'ΚΑΛΗ', 'ΠΟΛΥ ΚΑΛΗ'))," +
                "FOREIGN KEY (borrow_ad_isbn) REFERENCES book(isbn) ON UPDATE CASCADE ON DELETE CASCADE," +
                "FOREIGN KEY (borrow_publisher) REFERENCES user(user_id) ON UPDATE CASCADE ON DELETE CASCADE" +
                ")";
        db.execSQL(createBorrowAdTableQuery);

        String createBorrowTable = "CREATE TABLE borrow (" +
                "borrow_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "borrow_ad_id INTEGER," +
                "start_date TEXT NOT NULL," +
                "end_date TEXT NOT NULL," +
                "id_borrower INTEGER," +
                "FOREIGN KEY (borrow_ad_id) REFERENCES borrow_ad(borrow_ad_id)" +
                ")";
        db.execSQL(createBorrowTable);


        // Insert initial books
        insertBook(db, "9786180149173", "Ψιλά Γράμματα", "LAUREN ASHER", "Εκείνος είναι κληρονόμος μιας εταιρείας που κατασκευάζει… παραμύθια. Εκείνη καλείται να δουλέψει για το πιο απαιτητικό αφεντικό που γνώρισε ποτέ. Αν θέλουν και το δικό τους παραμύθι να έχει ευτυχισμένο τέλος, τότε πρέπει να προσέξουν λίγο περισσότερο τα… ψιλά γράμματα! Ο Ρόουαν Κέιν και τα δύο αδέλφια του κληρονομούν από τον παππού τους μια τεράστια εταιρεία και μια αμύθητη περιουσία. Για να περάσουν όλα αυτά και επίσημα στα χέρια του, ο Ρόουαν πρέπει να αποδείξει ότι είναι ικανός να ανανεώσει την Ντρίμλαντ, το θεματικό πάρκο της εταιρείας. Στην πραγματικότητα, δε θέλει να έχει καμία σχέση μ ’ αυτά, αλλά, από σεβασμό στη μνήμη του παππού του, αποφασίζει να δουλέψει σκληρά και είναι αποφασισμένος να τα καταφέρει. Η Ζάρα είναι απλώς μία από το πλήθος των υπαλλήλων του Ρόουαν Κέιν. Όταν όμως, μεθυσμένη, στέλνει μια επικριτική πρόταση για το πάρκο, τρέμει ότι θα απολυθεί. Αντ’ αυτού, εκείνος της προσφέρει τη δουλειά των ονείρων της. Αλλά υπάρχει μια παγίδα: ο Ρόουαν είναι το πιο απαιτητικό και το πιο σκληρό αφεντικό που υπάρχει. Η καρδιά της Ζάρα δε δίνει καμία σημασία σ’ αυτό. Ήρθε η ώρα να μάθει ο δισεκατομμυριούχος ότι τα χρήματα δεν μπορούν να διορθώσουν ή να αγοράσουν τα πάντα. Και ειδικά τους ανθρώπους! Το πρώτο βιβλίο της σειράς μπεστ σέλερ, όπου πρωταγωνιστούν οι τρεις δισεκατομμυριούχοι αδελφοί της Ντρίμλαντ και οι γυναίκες που θα τους κάνουν να γονατίσουν.", 445, "Αισθηματικα");
        insertBook(db, "9786810146189", "Το τρίτο κορίτσι", "Agatha Christie", "«Eh bien, λοιπόν, είστε τρελή ή φαίνεστε τρελή ή νομίζετε πως είστε τρελή και πιθανόν να είστε τρελή».\n" +
                "Τρεις νέες γυναίκες μοιράζονται ένα διαμέρισμα στο Λονδίνο. Η πρώτη είναι μία ψυχρή αλλά αποτελεσματική ιδιαιτέρα γραμματέας, η δεύτερη είναι καλλιτέχνις και η τρίτη διακόπτει τον Πουαρό την ώρα που παίρνει το πρωινό του –μπριός και ζεστή σοκολάτα–δηλώνοντας ότι είναι δολοφόνος και εξαφανίζεται αμέσως.\n" +
                "Ο χαρισματικός ντετέκτιβ μαθαίνει σιγά σιγά τις φήμες που σχετίζονται με το μυστηριώδες τρίτο κορίτσι, με την οικογένειά της αλλά και με την εξαφάνισή του. Ωστόσο, χρειάζονται αδιάσειστα αποδεικτικά στοιχεία προτού ο Πουαρό αποφανθεί αν είναι ένοχη, αθώα ή απλώς παράφρων.", 277, "Αστυνομικα");
        insertBook(db, "9786180128055", "Έγκλημα στο Οριάν Εξπρές", "Agatha Christie", "Ο δολοφόνος βρίσκεται εδώ, μαζί μας. . .Είναι στο τρένο αυτή τη στιγμή. . .Λίγο μετά τα μεσάνυχτα, το φημισμένο Οριάν Εξπρές ακινητοποιείται από μια χιονοθύελλα στη μέση του πουθενά. Το πρωί, ο εκατομμυριούχος Σάμιουελ Έντουαρντ Ράτσετ βρίσκεται μαχαιρωμένος στο κρεβάτι του. Η πόρτα του κουπέ του είναι κλειδωμένη από μέσα.Ο δολοφόνος του είναι ένας από τους συνταξιδιώτες του. . .Ο Ηρακλής Πουαρό, παγιδευμένος κι αυτός στο τραίνο, επιχειρεί να λύσει το μυστήριο. Ανάμεσα στους επιβάτες υπάρχουν πολλοί άνθρωποι που είχαν λόγους να μισούν τον Ράτσετ. Ποιος απ' όλους είναι ο δολοφόνος; Άραγε σχεδιάζει να χτυπήσει ξανά;Ένα ταξίδι με το πιο πολυτελές τρένο του κόσμου εξελίσσεται σ' ένα αγωνιώδες μυστήριο -και σ' ένα από τα πιο δημοφιλή έργα της Άγκαθα Κρίστι, που διαβάστηκε από εκατομμύρια αναγνώστες και μεταφέρθηκε επανειλημμένα στον κινηματογράφο και στην τηλεόραση", 277, "Αστυνομικα");

        // Insert some values into the user table
        insertUser(db, "Γιώργος Παπαδόπουλος", "123", "USER", "john@example.com", 123456789, "Πατρα", 100);
        insertUser(db, "Κατερινα Παπά", "456", "WRITER", "jane@example.com", 987654321, "Πατρα", 150);
        insertUser(db, "Αλίκη Γεωργίου", "789", "USER", "alice@example.com", 555555555, "Πατρα", 200);
        // Insert some  values into the wallet table
        insertWallet(db, 1, 20.0); // John Doe with initial balance 50.0
        insertWallet(db, 2, 75.0); //  Jane Smith with initial balance 75.0
        insertWallet(db, 3, 100.0); //  Alice Johnson with initial balance 100.0
        // Insert records into the selling_ad table
        insertSellingAd(db, "9786180149173", 18, 1, "ΚΑΛΗ");
        insertSellingAd(db, "9786810146189", 12, 3, "ΠΟΛΥ ΚΑΛΗ");
        insertSellingAd(db, "9786810146189", 10, 2, "ΚΑΛΗ");
        // Insert records into the borrow_ad table
        insertBorrowAd(db, "9786180149173", "15/6/2024", "19/6/2024", 1,  "ΚΑΛΗ");
        insertBorrowAd(db, "9786810146189", "18/7/2024", "20/7/2024",3,  "ΠΟΛΥ ΚΑΛΗ");
        insertBorrowAd(db, "9786810146189", "1/8/2024", "19/8/2024",2, "ΚΑΛΗ");

        // Insert records into events table
        insertEvents(db,82224,"'Βιβλιοφάγοι'","Μια πρώτη γνωριμία με τον δημιουργό του έργου 'Βιβλιοφάγοι'","2024-08-22", "15:30","17:00 PM","Παπανδρέου 20, Πάτρα",50,1 );
        insertEvents(db,81324,"Βιβλιοδεσίες","Η Πρώτη μας συνάντηση στον Βιβλιοπωλείο 'Βιβλιοδεσίες'","2024-04-10", "10:00 PM","13:30 AM","Ανθείας 09, Πάτρα",100,1 );

        insertCoupons(db,"10% Έκπτωση σε αγορά Ebook","John Doe","2024-02-10","ΟΧΙ");
        insertCoupons(db,"50% Έκπτωση σε αγορά βιβλίου","John Doe","2024-03-10","ΟΧΙ");
        insertCoupons(db,"Δωρεάν μεταφορικά","John Doe","2024-02-10","ΟΧΙ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implement if needed
    }
    // Method to insert a purchase
    public void insertPurchase(int buyerId, String typePurchase, int itemId, float price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("buyer_id", buyerId);
        values.put("type_purchase", typePurchase);
        values.put("item_id", itemId);
        values.put("price", price);
        db.insert("purchase", null, values);
        db.close();
    }
    public void insertWallet(SQLiteDatabase db, int userId, double money) {
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("money", money);
        db.insert("wallet", null, values);
    }


    // Method to delete a selling ad
    public void deleteSellingAd(int sellingAdId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("selling_ad", "selling_ad_id = ?", new String[]{String.valueOf(sellingAdId)});
        db.close();
    }


    // Method to insert a book into the database
    private void insertBook(SQLiteDatabase db, String isbn, String title, String author, String description, int pages, String category) {
        ContentValues values = new ContentValues();
        values.put("isbn", isbn);
        values.put("title", title);
        values.put("book_description", description);
        values.put("book_author", author);
        values.put("pages", pages);
        values.put("category", category);

        db.insert("book", null, values);
    }
    public void insertBorrow(int borrowAdId, String startDate, String endDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("borrow_ad_id", borrowAdId);
        values.put("start_date", startDate);
        values.put("end_date", endDate);
        values.put("id_borrower", 1); // Θέτουμε το id_borrower στο 1

        // Εισαγωγή των τιμών στον πίνακα borrow
         db.insert("borrow", null, values);
    }
    // Method to insert a user into the user table
    private void insertUser(SQLiteDatabase db, String name, String password, String type, String mail, int phone, String userLocation, int points) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("password", password);
        values.put("type", type);
        values.put("mail", mail);
        values.put("phone", phone);
        values.put("user_location", userLocation);
        values.put("points", points);

        db.insert("user", null, values);
    }
    // Method to insert a selling ad into the selling_ad table
    public void insertSellingAd(SQLiteDatabase db, String isbn, float price, int publisher, String status) {
        ContentValues values = new ContentValues();
        values.put("selling_ad_isbn", isbn);
        values.put("selling_price", price);
        values.put("selling_publisher", publisher);
        values.put("selling_status", status);

        db.insert("selling_ad", null, values);
    }

    // Method to insert a selling ad into the selling_ad table
    private void insertBorrowAd(SQLiteDatabase db, String isbn, String start_date, String end_date, int publisher, String status) {
        ContentValues values = new ContentValues();
        values.put("borrow_ad_isbn", isbn);

        // Μετατροπή της συμβολοσειράς της ημερομηνίας εκκίνησης σε αντικείμενο ημερομηνίας
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date startDate = dateFormat.parse(start_date);
            values.put("start_date", dateFormat.format(startDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Μετατροπή της συμβολοσειράς της ημερομηνίας λήξης σε αντικείμενο ημερομηνίας
        try {
            Date endDate = dateFormat.parse(end_date);
            values.put("end_date", dateFormat.format(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        values.put("borrow_publisher", publisher);
        values.put("borrow_status", status);

        db.insert("borrow_ad", null, values);
    }

    private void insertEvents(SQLiteDatabase db, int eventId, String title, String description, String date,String start_time,String end_time, String location, int capacity, int creator) {
        ContentValues values = new ContentValues();
        values.put("event_id", eventId);
        values.put("event_title", title);
        values.put("event_description", description);
        values.put("date", date);
        values.put("start_time", start_time);
        values.put("end_time", end_time);
        values.put("event_location", location);
        values.put("capacity", capacity );
        values.put("writer_creator", creator );

        db.insert("events", null, values);
    }

    private void insertCoupons(SQLiteDatabase db, String type, String user, String expire, String used) {
        ContentValues values = new ContentValues();
        values.put("type", type);
        values.put("user_name", user);
        values.put("expired_date", expire);
        values.put("used", used);

        db.insert("coupons", null, values);
    }



    public void updateReview(int reviewId, int i, String updatedReviewText) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("review", updatedReviewText);
        db.update("review", values, "review_id = ?", new String[]{String.valueOf(reviewId)});
        db.close();
    }


    public Cursor getAllReviews() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM review", null);
    }

    public long insertReview(int reviewerId, String reviewText, String reviewedBook) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("reviewer", reviewerId);
        values.put("review", reviewText);
        values.put("reviewed_book", reviewedBook);
        long insertedId = db.insert("review", null, values);
        db.close();
        return insertedId; // Return the ID of the inserted review
    }

    // method to insert quiz no1 to the db (harry potter quiz)
    public void insertHarryPotterQuiz(SQLiteDatabase db) {
        ContentValues quizValues = new ContentValues();
        quizValues.put("title", "Harry Potter");
        long quizId = db.insert("quiz", null, quizValues);

        // Questions and Options
        insertQuestionWithOptions(db, quizId,
                "Σε ποιο σπίτι ανήκει ο Harry Potter στο Χόγκουαρτς;",
                new String[]{"Gryffindor", "Hufflepuff", "Ravenclaw", "Slytherin"},
                "Gryffindor");

        insertQuestionWithOptions(db, quizId,
                "Ποιος είναι ο καλύτερος φίλος του Harry Potter;",
                new String[]{"Draco Malfoy", "Neville Longbottom", "Ron Weasley", "Cedric Diggory"},
                "Ron Weasley");

        insertQuestionWithOptions(db, quizId,
                "Ποιο μαγικό ραβδί χρησιμοποιεί ο Harry στη μάχη του στα θαλάμια των μυστικών;",
                new String[]{"Το δικό του ραβδί", "Το ραβδί του Dumbledore", "Το ραβδί του Snape", "Το ραβδί του Voldemort"},
                "Το δικό του ραβδί");

        insertQuestionWithOptions(db, quizId,
                "Ποια ουσία χρησιμοποιεί ο Harry για να σώσει τον Δούδουρα από τις αράχνες στον Κύλικα του Φωτός;",
                new String[]{"Felix Felicis", "Polyjuice Potion", "Wolfsbane Potion", "Veritaserum"},
                "Felix Felicis");

        insertQuestionWithOptions(db, quizId,
                "Ποιος ήταν ο πραγματικός κληρονόμος του Slytherin;",
                new String[]{"Tom Riddle", "Severus Snape", "Albus Dumbledore", "Salazar Slytherin"},
                "Tom Riddle");

        insertQuestionWithOptions(db, quizId,
                "Σε ποια γλώσσα μπορεί να μιλήσει ο Harry, που συνδέεται με την καταγωγή του Voldemort;",
                new String[]{"Mermish", "Gobbledegook", "Parseltongue", "Elvish"},
                "Parseltongue");
    }

    // method to insert quiz 2 to the db (star wars quiz)
    public void insertStarWarsQuiz(SQLiteDatabase db) {
        ContentValues quizValues = new ContentValues();
        quizValues.put("title", "Star Wars");
        long quizId = db.insert("quiz", null, quizValues);

        // Insert questions and their respective options
        insertQuestionWithOptions(db, quizId,
                "Ποιος δημιούργησε τον C-3PO;",
                new String[]{"Obi-Wan Kenobi", "Anakin Skywalker", "Luke Skywalker", "Yoda"},
                "Anakin Skywalker");

        insertQuestionWithOptions(db, quizId,
                "Τι είδος πλάσμα είναι ο Chewbacca;",
                new String[]{"Ewok", "Wookiee", "Jawa", "Sith"},
                "Wookiee");

        insertQuestionWithOptions(db, quizId,
                "Ποια φράση έχει αναφερθεί σε κάθε ταινία της αρχικής τριλογίας Star Wars;",
                new String[]{"I have a bad feeling about this.", "May the Force be with you.", "It’s a trap!", "No, I am your father."},
                "I have a bad feeling about this.");

        insertQuestionWithOptions(db, quizId,
                "Ποιος είναι ο αληθινός γονέας της Princess Leia και του Luke Skywalker;",
                new String[]{"Han Solo και Princess Leia", "Anakin Skywalker και Padmé Amidala", "Obi-Wan Kenobi και Duchess Satine", "Emperor Palpatine και Anakin Skywalker"},
                "Anakin Skywalker και Padmé Amidala");

        insertQuestionWithOptions(db, quizId,
                "Ποιο ήταν το πρωτότυπο όνομα του Darth Vader πριν γίνει Sith Lord;",
                new String[]{"Anakin Skywalker", "Ben Kenobi", "Mace Windu", "Qui-Gon Jinn"},
                "Anakin Skywalker");

        insertQuestionWithOptions(db, quizId,
                "Ποια είναι η πρωτεύουσα της Νέας Δημοκρατίας στο Star Wars;",
                new String[]{"Naboo", "Coruscant", "Hoth", "Alderaan"},
                "Coruscant");
    }




    // Helper method to insert a question with its options
    private void insertQuestionWithOptions(SQLiteDatabase db, long quizId, String questionText, String[] options, String correctAnswer) {
        ContentValues questionValues = new ContentValues();
        questionValues.put("quiz_id", quizId);
        questionValues.put("question_text", questionText);
        long questionId = db.insert("questions", null, questionValues);

        for (String option : options) {
            ContentValues optionValues = new ContentValues();
            optionValues.put("question_id", questionId);
            optionValues.put("option_text", option);
            optionValues.put("is_correct", option.equals(correctAnswer) ? 1 : 0);
            db.insert("options", null, optionValues);
        }
    }

   public ArrayList<String> getQuizTitles() {
        ArrayList<String> titles = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM quiz";
        Cursor cursor = db.rawQuery(query, null); // Execute the query

        if (cursor.moveToFirst()) {
            do {
                titles.add(cursor.getString(cursor.getColumnIndexOrThrow("title")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return titles;
    }

    public List<Quiz> getQuizList() { // Removed titlePattern parameter
        List<Quiz> quizzes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT id, title FROM quiz"; // Removed WHERE clause
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null); // No parameters for rawQuery
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex("id");
                int titleIndex = cursor.getColumnIndex("title");

                if (idIndex != -1 && titleIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    String title = cursor.getString(titleIndex);
                    quizzes.add(new Quiz(title));
                }
            }
        } catch (Exception e) {
            Log.e("DBHandler", "Error while trying to get quizzes from database", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return quizzes;

    }



    public void addUserPoints(int userId, int pointsToAdd) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Update points using SQL command to avoid race condition
        String sql = "UPDATE user SET points = points + ? WHERE user_id = ?";
        db.execSQL(sql, new Object[]{pointsToAdd, userId});

        db.close();
    }

    public float getUserWalletBalance(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT money FROM wallet WHERE user_id = ?", new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            float balance = cursor.getFloat(0);
            cursor.close();
            return balance;
        }

        return 0;
    }

    public void updateUserWalletBalance(int userId, float newBalance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("money", newBalance);
        db.update("wallet", values, "user_id = ?", new String[]{String.valueOf(userId)});
    }



    public List<Book> searchBooksByTitle(String title) {
        List<Book> books = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the query to search for books by title
        String query = "SELECT * FROM book WHERE title LIKE ?";

        // Execute the query with the title parameter
        Cursor cursor = db.rawQuery(query, new String[]{"%" + title + "%"});

        // Iterate through the cursor to retrieve books
        if (cursor.moveToFirst()) {
            do {
                int isbnIndex = cursor.getColumnIndex("isbn");
                int titleIndex = cursor.getColumnIndex("title");
                int authorIndex = cursor.getColumnIndex("book_author");
                int descriptionIndex = cursor.getColumnIndex("book_description");
                int pagesIndex = cursor.getColumnIndex("pages");
                int categoryIndex = cursor.getColumnIndex("category");

                // Check if column indices are valid
                if (isbnIndex != -1 && titleIndex != -1 && authorIndex != -1 &&
                        descriptionIndex != -1 && pagesIndex != -1 && categoryIndex != -1) {
                    String isbn = cursor.getString(isbnIndex);
                    String bookTitle = cursor.getString(titleIndex);
                    String author = cursor.getString(authorIndex);
                    String description = cursor.getString(descriptionIndex);
                    int pages = cursor.getInt(pagesIndex);
                    String category = cursor.getString(categoryIndex);

                    // Create a Book object for each row and add it to the list
                    Book book = new Book(isbn, bookTitle, author, description, pages, category);
                    books.add(book);
                } else {
                    // Handle case where one or more columns are missing
                    // Log a warning or take appropriate action
                }
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        // Return the list of books
        return books;
    }

    public List<events> returnEventsInfo() {
        List<events> eventsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the query to search for events
        String query = "SELECT * FROM events";

        // Execute the query
        Cursor cursor = db.rawQuery(query, null);

        // Iterate through the cursor to retrieve events
        if (cursor.moveToFirst()) {
            do {
                int eventIdIndex = cursor.getColumnIndex("event_id");
                int titleIndex = cursor.getColumnIndex("event_title");
                int descriptionIndex = cursor.getColumnIndex("event_description");
                int dateIndex = cursor.getColumnIndex("date");
                int starttimeIndex = cursor.getColumnIndex("start_time");
                int endtimeIndex = cursor.getColumnIndex("end_time");
                int locationIndex = cursor.getColumnIndex("event_location");
                int capacityIndex = cursor.getColumnIndex("capacity");
                int creatorIndex = cursor.getColumnIndex("writer_creator");

                // Check if column indices are valid
                if (eventIdIndex != -1 && titleIndex != -1 && descriptionIndex != -1 &&
                        dateIndex != -1 && starttimeIndex != -1 && endtimeIndex != -1 && locationIndex != -1 && capacityIndex != -1 && creatorIndex != -1) {
                    int eventId = cursor.getInt(eventIdIndex);
                    String title = cursor.getString(titleIndex);
                    String description = cursor.getString(descriptionIndex);
                    String date = cursor.getString(dateIndex);
                    String startTime = cursor.getString(starttimeIndex);
                    String endTime = cursor.getString(endtimeIndex);
                    String location = cursor.getString(locationIndex);
                    int capacity = cursor.getInt(capacityIndex);
                    int creator = cursor.getInt(creatorIndex);

                    // Create an Event object for each row and add it to the list
                    events event = new events(eventId, title, description, date,startTime,endTime, location, capacity, creator);
                    eventsList.add(event);
                } else {
                    // Handle case where one or more columns are missing
                    // Log a warning or take appropriate action
                }
            } while (cursor.moveToNext());
        }
        // Close the cursor and database
        cursor.close();
        db.close();

        // Return the list of events
        return eventsList;
    }



    public List<sellingAd> getSellingAdByIsbn(String isbn) {
        List<sellingAd> sellingAds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the query to search for selling ads by ISBN and join with the book table
        String query = "SELECT sa.*, b.title, b.book_author, b.book_description, b.pages, b.category " +
                "FROM selling_ad sa " +
                "JOIN book b ON sa.selling_ad_isbn = b.isbn " +
                "WHERE sa.selling_ad_isbn = ?";

        // Execute the query with the ISBN parameter
        Cursor cursor = db.rawQuery(query, new String[]{isbn});

        // Iterate through the cursor to retrieve selling ads
        if (cursor.moveToFirst()) {
            do {
                int sellingAdIdIndex = cursor.getColumnIndex("selling_ad_id");
                int sellingPriceIndex = cursor.getColumnIndex("selling_price");
                int sellingPublisherIndex = cursor.getColumnIndex("selling_publisher");
                int sellingStatusIndex = cursor.getColumnIndex("selling_status");
                int titleIndex = cursor.getColumnIndex("title");
                int authorIndex = cursor.getColumnIndex("book_author");
                int descriptionIndex = cursor.getColumnIndex("book_description");
                int pagesIndex = cursor.getColumnIndex("pages");
                int categoryIndex = cursor.getColumnIndex("category");

                // Check if column indices are valid
                if (sellingAdIdIndex != -1 && sellingPriceIndex != -1 &&
                        sellingPublisherIndex != -1 && sellingStatusIndex != -1 &&
                        titleIndex != -1 && authorIndex != -1 &&
                        descriptionIndex != -1 && pagesIndex != -1 &&
                        categoryIndex != -1) {
                    // Retrieve data from the cursor and create a SellingAd object
                    int sellingAdId = cursor.getInt(sellingAdIdIndex);
                    float sellingPrice = cursor.getFloat(sellingPriceIndex);
                    int sellingPublisher = cursor.getInt(sellingPublisherIndex);
                    String sellingStatus = cursor.getString(sellingStatusIndex);
                    String title = cursor.getString(titleIndex);
                    String author = cursor.getString(authorIndex);
                    String description = cursor.getString(descriptionIndex);
                    int pages = cursor.getInt(pagesIndex);
                    String category = cursor.getString(categoryIndex);

                    // Create SellingAd object and add it to the list
                    sellingAd sellingAd = new sellingAd(isbn, title, author, description, pages, category, sellingAdId, sellingPrice, sellingPublisher, sellingStatus, null);
                    sellingAds.add(sellingAd);
                }
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        // Return the list of selling ads
        return sellingAds;
    }

    public List<BorrowAd> getBorrowAdByIsbn(String isbn) {
        List<BorrowAd> borrowAds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the query to search for borrow ads by ISBN and join with the user table to get the user_location
        String query = "SELECT borrow_ad.borrow_ad_id, borrow_ad.borrow_publisher, borrow_ad.borrow_status, user.user_location " +
                "FROM borrow_ad " +
                "JOIN user ON borrow_ad.borrow_publisher = user.user_id " +
                "WHERE borrow_ad.borrow_ad_isbn = ?";

        // Execute the query with the ISBN parameter
        Cursor cursor = db.rawQuery(query, new String[]{isbn});

        // Iterate through the cursor to retrieve borrow ads
        if (cursor.moveToFirst()) {
            do {
                int borrowAdIdIndex = cursor.getColumnIndex("borrow_ad_id");
                int borrowPublisherIndex = cursor.getColumnIndex("borrow_publisher");
                int borrowStatusIndex = cursor.getColumnIndex("borrow_status");
                int borrowLocationIndex = cursor.getColumnIndex("user_location");
                // Check if column indices are valid
                if (borrowAdIdIndex != -1 && borrowPublisherIndex != -1 && borrowStatusIndex != -1 && borrowLocationIndex != -1) {
                    // Retrieve data from the cursor and create a BorrowAd object
                    int borrowAdId = cursor.getInt(borrowAdIdIndex);
                    int borrowPublisher = cursor.getInt(borrowPublisherIndex);
                    String borrowStatus = cursor.getString(borrowStatusIndex);
                    String borrowLocation = cursor.getString(borrowLocationIndex);

                    // Create BorrowAd object and add it to the list
                    BorrowAd borrowAd = new BorrowAd(borrowAdId, isbn, borrowPublisher, borrowStatus, borrowLocation);
                    borrowAds.add(borrowAd);
                }
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        // Return the list of borrow ads
        return borrowAds;
    }



    // Method to retrieve user name by user ID
    public String getUserNameById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String userName = null;

        // Define the query
        String query = "SELECT name FROM user WHERE user_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        // Check if the cursor has data and the column index is valid
        if (cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex("name");
            if (nameIndex >= 0) {
                // Retrieve user name from the cursor
                userName = cursor.getString(nameIndex);
            }
        }

        // Close cursor and database
        cursor.close();
        db.close();

        return userName;
    }
    public sellingAd getAdDetailsByAdId(int adId) {
        SQLiteDatabase db = this.getReadableDatabase();
        sellingAd adDetails = null;

        // Define the query to retrieve ad details based on ad_id
        String query = "SELECT selling_ad.selling_ad_isbn, book.title, book.book_description, book.pages, selling_ad.selling_price, selling_ad.selling_publisher " +
                "FROM selling_ad " +
                "INNER JOIN book ON selling_ad.selling_ad_isbn = book.isbn " +
                "WHERE selling_ad.selling_ad_id = ?";

        // Execute the query with the ad_id parameter
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(adId)});

        // Check if the cursor has data
        if (cursor.moveToFirst()) {
            // Retrieve column indices
            int titleIndex = cursor.getColumnIndex("title");
            int descriptionIndex = cursor.getColumnIndex("book_description");
            int pagesIndex = cursor.getColumnIndex("pages");
            int priceIndex = cursor.getColumnIndex("selling_price");
            int publisherIndex = cursor.getColumnIndex("selling_publisher");

            // Check if column indices are valid
            if (titleIndex != -1 && descriptionIndex != -1 && pagesIndex != -1  && priceIndex != -1 && publisherIndex != -1) {
                // Retrieve data from the cursor
                String title = cursor.getString(titleIndex);
                String description = cursor.getString(descriptionIndex);
                int pages = cursor.getInt(pagesIndex);
                float price = cursor.getFloat(priceIndex);
                int publisherId = cursor.getInt(publisherIndex);


                // Retrieve publisher's name using user ID
                String publisherName = getUserNameById(publisherId);


                // Create a SellingAd object with the retrieved information
                adDetails = new sellingAd(null, title, null, description, pages, null, adId, price, publisherId, null, publisherName);


            }

        }

        // Close cursor and database
        cursor.close();
        db.close();

        return adDetails;
    }

    public adDetailsBorrow getAdDetailsBorrow(int adId) {
        SQLiteDatabase db = this.getReadableDatabase();
        adDetailsBorrow adDetailsBorrow = null;

        // Define the query to retrieve ad details from borrow_ad, book, and user tables
        String query = "SELECT borrow_ad.borrow_ad_id, borrow_ad.borrow_ad_isbn, borrow_ad.borrow_publisher, " +
                "borrow_ad.borrow_status, book.title, book.book_description, book.pages, " +
                "user.user_location " +
                "FROM borrow_ad " +
                "INNER JOIN book ON borrow_ad.borrow_ad_isbn = book.isbn " +
                "INNER JOIN user ON borrow_ad.borrow_publisher = user.user_id " +
                "WHERE borrow_ad.borrow_ad_id = ?";

        // Execute the query with the ad_id parameter
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(adId)});

        // Check if the cursor has data
        if (cursor.moveToFirst()) {
            // Retrieve column indices
            int borrowAdIdIndex = cursor.getColumnIndex("borrow_ad_id");
            int borrowAdIsbnIndex = cursor.getColumnIndex("borrow_ad_isbn");
            int borrowPublisherIndex = cursor.getColumnIndex("borrow_publisher");
            int borrowStatusIndex = cursor.getColumnIndex("borrow_status");
            int titleIndex = cursor.getColumnIndex("title");
            int descriptionIndex = cursor.getColumnIndex("book_description");
            int pagesIndex = cursor.getColumnIndex("pages");
            int locationIndex = cursor.getColumnIndex("user_location");

            // Check if column indices are valid
            if (borrowAdIdIndex != -1 && borrowAdIsbnIndex != -1 && borrowPublisherIndex != -1 &&
                    borrowStatusIndex != -1 && titleIndex != -1 && descriptionIndex != -1 &&
                    pagesIndex != -1 && locationIndex != -1) {

                // Retrieve data from the cursor
                int borrowAdId = cursor.getInt(borrowAdIdIndex);
                String borrowAdIsbn = cursor.getString(borrowAdIsbnIndex);
                int borrowPublisher = cursor.getInt(borrowPublisherIndex);
                String borrowStatus = cursor.getString(borrowStatusIndex);
                String title = cursor.getString(titleIndex);
                String description = cursor.getString(descriptionIndex);
                int pages = cursor.getInt(pagesIndex);
                String location = cursor.getString(locationIndex);
                // Retrieve publisher's name using user ID
                String publisherName = getUserNameById(borrowPublisher);
                // Create BorrowAd object with the retrieved information
                adDetailsBorrow= new adDetailsBorrow(borrowAdId, borrowAdIsbn, publisherName,borrowStatus, location, title, description, pages);
            }
        }

        // Close cursor and database
        cursor.close();
        db.close();

        return  adDetailsBorrow;
    }



    public List<coupons> returnCoupons() {
        List<coupons> couponsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the query to search for events
        String query = "SELECT * FROM coupons";

        // Execute the query
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int typeIndex = cursor.getColumnIndex("type");
                int usersIndex = cursor.getColumnIndex("user_name");
                int expiredIndex = cursor.getColumnIndex("expired_date");
                int usedIndex = cursor.getColumnIndex("used");
                // Check if column indices are valid
                if ( typeIndex != -1 && usersIndex != -1 && expiredIndex != -1 &&
                        usedIndex != -1) {
                    String user = cursor.getString(usersIndex);
                    String coupontype = cursor.getString(typeIndex);
                    String expiredate = cursor.getString(expiredIndex);
                    String used = cursor.getString(usedIndex);

                    // Create an Event object for each row and add it to the list
                    coupons coupon = new coupons(user, coupontype, expiredate, used);
                    couponsList.add(coupon);
                } else {
                    // Handle case where one or more columns are missing
                    // Log a warning or take appropriate action
                }
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return couponsList;
    }

    public int getUserPointsByname(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int points = 0; // Default value if user is not found or points are not set

        // Define the query
        // Define the query
        String query = "SELECT points FROM user WHERE name = ?";
        Cursor cursor = db.rawQuery(query, new String[]{userName}); // No need for String.valueOf() here

        // Check if the cursor has data and the column index is valid
        if (cursor.moveToFirst()) {
            int pointsIndex = cursor.getColumnIndex("points");
            if (pointsIndex >=0) {
                // Retrieve user points from the cursor
                points = cursor.getInt(pointsIndex);
            }
        }
        // Close cursor and database
        cursor.close();
        db.close();

        return points;
    }

    public void updateUserPoints(String userName, int newPoints) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("points", newPoints);

        // Define the update condition
        String whereClause = "name = ?";
        String[] whereArgs = {userName};

        // Execute the update operation
        int rowsAffected = db.update("user", values, whereClause, whereArgs);

        // Close the database
        db.close();
    }
    public void updatecoupons(String type, String userName, String expiredDate, String used) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("type", type);
        values.put("user_name", userName);
        values.put("expired_date", expiredDate);
        values.put("used", used);

        // Execute the insert operation
        long newRowId = db.insert("coupons", null, values);

        // Close the database
        db.close();
    }
    public String[] getStartDateAndEndDateFromBorrowId(int borrowId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] dates = new String[2]; // Αποθηκεύουμε τις ημερομηνίες εκκίνησης και λήξης

        Cursor cursor = db.rawQuery("SELECT start_date, end_date FROM borrow_ad WHERE borrow_ad_id = ?", new String[]{String.valueOf(borrowId)});
        if (cursor.moveToFirst()) {
            // Αν υπάρχει αποτέλεσμα στο Cursor, ανάκτησε τις ημερομηνίες
            int startIndex = cursor.getColumnIndex("start_date");
            int endIndex = cursor.getColumnIndex("end_date");

            if (startIndex != -1 && endIndex != -1) {
                dates[0] = cursor.getString(startIndex);
                dates[1] = cursor.getString(endIndex);
            } else {

            }
        }

        cursor.close();
        return dates;
    }
    public String getStartDateFromBorrowId(int borrowId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String startDate = null;

        Cursor cursor = db.rawQuery("SELECT start_date FROM borrow_ad WHERE borrow_ad_id = ?", new String[]{String.valueOf(borrowId)});
        if (cursor.moveToFirst()) {
            int startDateIndex = cursor.getColumnIndex("start_date");
            if (startDateIndex != -1) { // Έλεγχος εγκυρότητας του δείκτη
                startDate = cursor.getString(startDateIndex);
            } else {
                // Εδώ μπορείτε να εμφανίσετε ένα μήνυμα σφάλματος ή να διαχειριστείτε το πρόβλημα με άλλο τρόπο
            }
        }

        cursor.close();
        return startDate;
    }

    // Κώδικας για τη μέθοδο getEndDateFromBorrowId στον DBHandler
    public String getEndDateFromBorrowId(int borrowId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String endDate = null;

        Cursor cursor = db.rawQuery("SELECT end_date FROM borrow_ad WHERE borrow_ad_id = ?", new String[]{String.valueOf(borrowId)});
        if (cursor.moveToFirst()) {
            int endDateIndex = cursor.getColumnIndex("end_date");
            if (endDateIndex != -1) { // Έλεγχος εγκυρότητας του δείκτη
                endDate = cursor.getString(endDateIndex);
            } else {
                // Εδώ μπορείτε να εμφανίσετε ένα μήνυμα σφάλματος ή να διαχειριστείτε το πρόβλημα με άλλο τρόπο
            }
        }

        cursor.close();
        return endDate;
    }



}