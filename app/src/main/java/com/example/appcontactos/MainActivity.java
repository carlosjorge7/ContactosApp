package com.example.appcontactos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;

import database.ContactoDB;
import database.Contacto;

public class MainActivity extends AppCompatActivity {

    private LinearLayout llContactos;

    private static final int OPERACION_ANADIR = 0;
    private static final int OPERACION_EDITAR = 1;

    public static final String KEY_OPERACION = "key_operacion";

    public static final String KEY_ID = "key_id"; // Para cuando editemos o borremos, le paso el id

    public final static int REQUEST_CODE = 0;


    public ContactoDB contactoDB;
    public Contacto contacto;

    private String[] tipos;
    private String[] colores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llContactos = (LinearLayout) findViewById(R.id.llContactos);

        tipos = getResources().getStringArray(R.array.tipos);

        colores = getResources().getStringArray(R.array.colores);

        // instancia de la bd
        contactoDB = ContactoDB.getInstance(this);

        // carga de datos
        cargarContactos();
    }

    public void cargarContactos() {
        llContactos.removeAllViews();

        LinkedList<Contacto> contactos = contactoDB.getContactos();
        LayoutInflater layoutInflater = getLayoutInflater();

        // Declaramos los elementos del contacto.xml para inflarlo
        LinearLayout llContacto;
        TextView tvNombre;
        TextView tvTefono;
        TextView tvTipo;

        for(final Contacto contacto: contactos) {
            llContacto = (LinearLayout) layoutInflater.inflate(R.layout.contacto, null);

            tvNombre = llContacto.findViewById(R.id.tvNombre);
            tvNombre.setText(contacto.getNombre());

            tvTefono = llContacto.findViewById(R.id.tvTelefono);
            tvTefono.setText(contacto.getTelefono());

            tvTipo = llContacto.findViewById(R.id.tvTipo);
            tvTipo.setText(tipos[contacto.getTipo()]);

            llContacto.setBackgroundColor(Color.parseColor(colores[contacto.getTipo()]));


            llContacto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, EditarActivity.class);
                    intent.putExtra(KEY_OPERACION, OPERACION_EDITAR);
                    intent.putExtra(KEY_ID, contacto.get_id());
                    startActivityForResult(intent, REQUEST_CODE);
                }
            });

            llContactos.addView(llContacto);
        }

    }

    // La funcion de este es que cuando vuela, cargar todos los datos
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            cargarContactos();
        }

    }

    public void onClickAnadir(View view) {
        Intent intent = new Intent(MainActivity.this, EditarActivity.class);
        intent.putExtra(KEY_OPERACION, OPERACION_ANADIR);
        startActivityForResult(intent, REQUEST_CODE);
    }
}
