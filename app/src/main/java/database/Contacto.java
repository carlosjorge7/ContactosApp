package database;

public class Contacto {

    public static final int TIPO_SKYPE = 0;
    public static final int TIPO_EMAIL = 1;
    public static final int TIPO_TELEFONO = 2;

    private int _id;
    private String nombre;
    private String telefono;
    private int tipo;

    public Contacto(int _id, String nombre, String telefono, int tipo) {
        this._id = _id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.tipo = tipo;
    }

    public Contacto(String nombre, String telefono, int tipo) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.tipo = tipo;
    }

    public Contacto() {

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
