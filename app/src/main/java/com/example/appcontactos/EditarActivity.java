package com.example.appcontactos;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import database.Contacto;
import database.ContactoDB;


public class EditarActivity extends AppCompatActivity {

    private EditText etNombre, etTelefono;
    private Button btEliminar, btOperacion;

    private RadioGroup rgTipos;
    private RadioButton rbSkype, rbEmail, rbTelefono;

    private ContactoDB contactoDB;

    private String[] tipos;

    private int operacion, idContacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        etNombre = (EditText) findViewById(R.id.etNombre);
        etTelefono = (EditText) findViewById(R.id.etTelefono);
        btEliminar = (Button) findViewById(R.id.btEliminar);
        btOperacion = (Button) findViewById(R.id.btOperacion);

        rgTipos = (RadioGroup)findViewById(R.id.rgTipos);
        rbSkype = (RadioButton)findViewById(R.id.rbSkype);
        rbEmail = (RadioButton)findViewById(R.id.rbEmail);
        rbTelefono = (RadioButton)findViewById(R.id.rbEmail);

        tipos = getResources().getStringArray(R.array.tipos);

        // instanciamos la db
        contactoDB = ContactoDB.getInstance(this);

        // obtenemos los intents
        Intent intent = getIntent();
        operacion = intent.getIntExtra(MainActivity.KEY_OPERACION, -1);

        Log.d("xxx", String.valueOf(operacion));

        if(operacion == 0) { // Añadir
            btEliminar.setVisibility(View.GONE);
        }
        else if(operacion == 1) { // Editar
            btOperacion.setText("Actualizar");

            // traemos los datos con el getContacto
            idContacto = intent.getIntExtra(MainActivity.KEY_ID, -1);

            Contacto contacto = contactoDB.getContacto(idContacto);

            etNombre.setText(contacto.getNombre());
            etTelefono.setText(contacto.getTelefono());

            if(contacto.getTipo() == Contacto.TIPO_SKYPE){
                rgTipos.check(R.id.rbSkype);
            }
            else if(contacto.getTipo() == Contacto.TIPO_EMAIL) {
                rgTipos.check(R.id.rbEmail);
            }
            else if(contacto.getTipo() == Contacto.TIPO_TELEFONO) {
                rgTipos.check(R.id.rbTelefono);
            }
        }
    }

    public void onClickOperacion(View view) {
        if(operacion == 0) { // Añadir

            String nombre = etNombre.getText().toString();
            String telefono = etTelefono.getText().toString();

            if(nombre.length() != 0 && telefono.length() != 0){

                Contacto contacto = new Contacto();
                contacto.setNombre(nombre);
                contacto.setTelefono(telefono);

                int iTipo = rgTipos.getCheckedRadioButtonId();
                if(iTipo == R.id.rbSkype) {
                    contacto.setTipo(Contacto.TIPO_SKYPE);
                }
                else if(iTipo == R.id.rbEmail) {
                    contacto.setTipo(Contacto.TIPO_EMAIL);
                }
                else{
                    contacto.setTipo(Contacto.TIPO_TELEFONO);
                }
                contactoDB.insertarContacto(contacto);
                Toast.makeText(this, "Contacto guardado", Toast.LENGTH_SHORT).show();

                // hay que regresar al activity anterior
                setResult(RESULT_OK);
                finish();
            }
            else{
                Toast.makeText(this, "Falta el campo nombre o telefono", Toast.LENGTH_SHORT).show();
            }
        }
        else if(operacion == 1) { // Editar
            Contacto contacto = new Contacto();
            contacto.set_id(idContacto);
            contacto.setNombre(etNombre.getText().toString());
            contacto.setTelefono(etTelefono.getText().toString());
            int iTipo = rgTipos.getCheckedRadioButtonId();
            if(iTipo == R.id.rbSkype) {
                contacto.setTipo(Contacto.TIPO_SKYPE);
            }
            else if(iTipo == R.id.rbEmail) {
                contacto.setTipo(Contacto.TIPO_EMAIL);
            }
            else{
                contacto.setTipo(Contacto.TIPO_TELEFONO);
            }
            contactoDB.actualizarContacto(contacto);
            Toast.makeText(this, "Contacto actualizado", Toast.LENGTH_SHORT).show();
            // Volvemos hacia atras
            setResult(RESULT_OK);
            finish();

        }
    }

    public void onClickEliminar(View view) {
        contactoDB.borrarContacto(idContacto);
        setResult(RESULT_OK);
        finish();
    }
}
