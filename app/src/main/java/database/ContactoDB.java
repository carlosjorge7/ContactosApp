package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.LinkedList;

public class ContactoDB extends SQLiteOpenHelper {

    private static ContactoDB contactoDB;
    private SQLiteDatabase db;

    private ContactoDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        db = getWritableDatabase();
    }

    public static ContactoDB getInstance(Context context) {
        if (contactoDB == null) {
            contactoDB = new ContactoDB(context, "contactos.db", null, 1);
        }
        return contactoDB;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table contactos(_id integer primary key autoincrement, nombre text, telefono text, tipo integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int insertarContacto(Contacto contacto) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", contacto.getNombre());
        contentValues.put("telefono", contacto.getTelefono());
        contentValues.put("tipo", contacto.getTipo());
        return  (int) db.insert("contactos", null, contentValues);
    }

    public LinkedList<Contacto> getContactos() {
        Cursor cursor = db.rawQuery("select _id, nombre, telefono, tipo from contactos order by _id", null);
        LinkedList<Contacto> llContactos = new LinkedList<>();
        if(cursor.moveToFirst()){
            do{
                Contacto contacto = new Contacto();
                contacto.set_id(cursor.getInt(0));
                contacto.setNombre(cursor.getString(1));
                contacto.setTelefono(cursor.getString(2));
                contacto.setTipo(cursor.getInt(3));
                llContactos.addLast(contacto);
            }
            while(cursor.moveToNext());
        }
        return llContactos;
    }

    public void borrarContacto(int id){
        db.execSQL("delete from contactos where _id = " + id);
    }

    public Contacto getContacto(int id) {
        Contacto contacto = new Contacto();
        Cursor cursor = db.rawQuery("select _id, nombre, telefono, tipo from contactos where _id = " + id, null);
        cursor.moveToFirst();
        contacto.set_id(cursor.getInt(0));
        contacto.setNombre(cursor.getString(1));
        contacto.setTelefono(cursor.getString(2));
        contacto.setTipo(cursor.getInt(3));
        return contacto;
    }

    public void actualizarContacto(Contacto contacto) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", contacto.getNombre());
        contentValues.put("telefono", contacto.getTelefono());
        contentValues.put("tipo", contacto.getTipo());
        String id = String.valueOf(contacto.get_id());
        db.update("contactos", contentValues, "_id = ?", new String[]{id});
    }




}
