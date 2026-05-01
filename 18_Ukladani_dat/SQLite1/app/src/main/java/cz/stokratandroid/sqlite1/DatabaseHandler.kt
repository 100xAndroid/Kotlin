package cz.stokratandroid.sqlite1

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler
    (context: Context?) : SQLiteOpenHelper(context, "VerzeAndroidu", null, 1) {
    var db: SQLiteDatabase? = null

    // metoda volana pri vzniku databaze
    // zde se zalozi databazove tabulky
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE VerzeAndroidu (ID INTEGER PRIMARY KEY, Nazev TEXT, Verze TEXT)")
    }

    // funkce volana pri zmene verze databaze
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // smazat starou tabulku, pokud existuje
        db.execSQL("DROP TABLE IF EXISTS VerzeAndroidu")

        // znovu vytvorit tabulku
        onCreate(db)
    }

    // pripojeni k databazi
    fun pripojitDB() {
        db = writableDatabase
    }

    // nacist a vratit vsechna data z tabulky VerzeAndroidu
    fun nacistData(): Cursor {
        return db!!.rawQuery("select * from VerzeAndroidu", null)
    }

    // do tabulky VerzeAndroidu vlozit zaznam
    fun vlozitZaznam(nazev: String, verze: String) {
        db!!.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('$nazev', '$verze')")
    }

    // zmenit zaznam v tabulce VerzeAndroidu
    fun upravitZaznam(id: String, nazev: String, verze: String) {
        db!!.execSQL("UPDATE VerzeAndroidu SET Nazev='$nazev', Verze='$verze' WHERE id='$id'")
    }

    // smazat obsah tabulky VerzeAndroidu
    fun smazatData() {
        db!!.execSQL("DELETE FROM VerzeAndroidu")
    }

    // odpojeni se od databaze
    fun odpojitDB() {
        db!!.close()
    }
}