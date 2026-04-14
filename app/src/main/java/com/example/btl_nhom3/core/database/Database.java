package com.example.btl_nhom3.core.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "foodapp.db";
    private static final int DB_VERSION = 2;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // USERS
        db.execSQL("CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "password TEXT," +
                "fullname TEXT," +
                "phone TEXT," +
                "address TEXT," +
                "role TEXT DEFAULT 'user')");

        // CATEGORIES
        db.execSQL("CREATE TABLE categories (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT," +
                "icon TEXT)");

        // FOODS
        db.execSQL("CREATE TABLE foods (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "price INTEGER," +
                "description TEXT," +
                "image TEXT," +
                "quantity INTEGER," +
                "category_id INTEGER)");

        // CART
        db.execSQL("CREATE TABLE cart (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER," +
                "food_id INTEGER," +
                "quantity INTEGER)");

        // ORDERS
        db.execSQL("CREATE TABLE orders (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "total_price INTEGER," +
                "status TEXT," +
                "created_at TEXT," +
                "address TEXT," +
                "phone TEXT)");


        // ORDER ITEMS
        db.execSQL("CREATE TABLE order_items (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "order_id INTEGER," +
                "food_id INTEGER," +
                "quantity INTEGER," +
                "price INTEGER)");

        insertSampleData(db);
    }

    private void insertSampleData(SQLiteDatabase db) {

        // users
        db.execSQL("INSERT INTO users (username,password,fullname,phone,address,role) VALUES " +
                "('admin','123456','Quan tri vien','0900000000','Viet Nam','admin')");
        db.execSQL("INSERT INTO users (username,password,fullname,phone,address,role) VALUES " +
                "('user','123456','Nguoi dung demo','0911111111','Viet Nam','user')");

        // categories
        db.execSQL("INSERT INTO categories VALUES (0,'Tất cả','all')");
        db.execSQL("INSERT INTO categories VALUES (1,'Đồ ăn','food')");
        db.execSQL("INSERT INTO categories VALUES (2,'Đồ uống','drink')");

        // foods
        db.execSQL("INSERT INTO foods (name,price,description,image,quantity,category_id) VALUES " +
                "('Burger bò',50000,'Burger bò phô mai','burger',10,1)");

        db.execSQL("INSERT INTO foods (name,price,description,image,quantity,category_id) VALUES " +
                "('Pizza hải sản',120000,'Pizza hải sản đầy topping','pizza',5,1)");

        db.execSQL("INSERT INTO foods (name,price,description,image,quantity,category_id) VALUES " +
                "('Trà sữa',30000,'Trà sữa trân châu','trasua',20,2)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE users ADD COLUMN role TEXT DEFAULT 'user'");
            db.execSQL("UPDATE users SET role = 'user' WHERE role IS NULL OR role = ''");
            db.execSQL("UPDATE users SET role = 'admin' WHERE username = 'admin'");
            return;
        }

        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS categories");
        db.execSQL("DROP TABLE IF EXISTS foods");
        db.execSQL("DROP TABLE IF EXISTS cart");
        db.execSQL("DROP TABLE IF EXISTS orders");
        db.execSQL("DROP TABLE IF EXISTS order_items");

        onCreate(db);
    }
}