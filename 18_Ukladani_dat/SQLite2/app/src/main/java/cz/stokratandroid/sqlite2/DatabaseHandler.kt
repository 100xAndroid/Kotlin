package cz.stokratandroid.sqlite2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHandler
    (context: Context?) : SQLiteOpenHelper(context, "VerzeAndroidu", null, 1) {

    var db: SQLiteDatabase? = null

    // funkce volana pri vzniku databaze
    // zde se zalozi databazove tabulky
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE VerzeAndroidu (_id INTEGER PRIMARY KEY, Nazev TEXT, Verze TEXT)")

        // vlozime do tabulky iniciacni data
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('bez kódového označení', '1.0')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('bez kódového označení', '1.1')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Cupcake', '1.5')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Donut', '1.6')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Eclair', '2.0')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Eclair', '2.1')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Froyo', '2.2')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Gingerbread', '2.3')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Honeycomb', '3.0')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Honeycomb', '3.1')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Honeycomb', '3.2')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Ice Cream Sandwich', '4.0')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Jelly Bean', '4.1')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Jelly Bean', '4.2')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Jelly Bean', '4.3')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('KitKat', '4.4')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Lollipop', '5.0')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Lollipop', '5.1')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Marshmallow', '6.0')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Nougat', '7.0')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Nougat', '7.1')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Oreo', '8.0')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Oreo', '8.1')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Pie', '9.0')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Android 10', '10')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Android 11', '11')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Android 12', '12')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Android 13', '13')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Android 14', '14')")
        db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('Android 15', '15')")
    }

    // metoda volana pri zmene verze databaze smerem nahoru
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // smazat starou tabulku, pokud existuje
        db.execSQL("DROP TABLE IF EXISTS VerzeAndroidu")

        // znovu vytvorit tabulku
        onCreate(db)
    }

    // metoda volana pri zmene verze databaze smerem dolu
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // smazat starou tabulku, pokud existuje
        db.execSQL("DROP TABLE IF EXISTS VerzeAndroidu")

        // znovu vytvorit tabulku
        onCreate(db)
    }

    // pripojeni k databazi
    fun pripojitDB() {
        db = writableDatabase
    }

    // nacte a vrati vsechna data z tabulky VerzeAndroidu
    fun nacistData(): Cursor {
        return db!!.rawQuery("select _id, Verze, Nazev from VerzeAndroidu", null)
    }

    // do tabulky VerzeAndroidu vlozi zaznam
    fun vlozitZaznam(nazev: String?, verze: String?) {
        // insert SQL dotazem
        // db.execSQL("INSERT INTO VerzeAndroidu ('Nazev', 'Verze') VALUES ('" + nazev + "', '" + verze + "')");
        val vkladaneHodnoty = ContentValues()
        vkladaneHodnoty.put("Nazev", nazev)
        vkladaneHodnoty.put("Verze", verze)
        db!!.insert("VerzeAndroidu", null, vkladaneHodnoty)
    }

    // zmeni zaznam v tabulce VerzeAndroidu
    fun upravitZaznam(id: String, nazev: String?, verze: String?) {
        // update SQL dotazem
        // db.execSQL("UPDATE VerzeAndroidu SET Nazev='" + nazev + "', Verze='" + verze + "' WHERE _id='" + id + "'");
        val vkladaneHodnoty = ContentValues()
        vkladaneHodnoty.put("Nazev", nazev)
        vkladaneHodnoty.put("Verze", verze)
        db!!.update("VerzeAndroidu", vkladaneHodnoty, "_id=$id", null)
    }

    // smaze zaznam z tabulky VerzeAndroidu
    fun smazatZaznam(id: String) {
        // delete SQL dotazem
        // db.execSQL("DELETE FROM VerzeAndroidu WHERE _id='" + id + "'");
        db!!.delete("VerzeAndroidu", "_id=$id", null)
    }

    // smaze obsah tabulky VerzeAndroidu
    fun smazatData() {
        // delete SQL dotazem
        // db.execSQL("DELETE FROM VerzeAndroidu");
        db!!.delete("VerzeAndroidu", null, null)
    }

    // odpojeni se od databaze
    fun odpojitDB() {
        db!!.close()
    }
}