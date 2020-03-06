package com.example.consultacep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    EditText edtCep;
    ListView lista;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtCep = findViewById(R.id.edtCep);
        lista = findViewById(R.id.idLista);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        lista.setAdapter(adapter);
        edtCep.addTextChangedListener(Mask.insert(Mask.CEP_MASK, edtCep));
    }
    public void Consultar(View view) {
        adapter.clear();
        System.out.println("fala fi");
        String cep = edtCep.getText().toString();
        try {
            String json = new HttpGet(cep).execute().get();
            String ver[] = json.split(":");
            System.out.println(ver[0] + ver[1]);
            if (json == null || ver.length == 2) {
                adapter.add("Cep inválido");
                return;
            }
            json = json.replace("\"","");
            json = json.replace("{","");
            json = json.replace("}","");
            json = json.replace("logradouro","Rua");
            json = json.replace("localidade","Cidade");
            json = json.replace("cep","CEP");
            json = json.replace("bairro","Bairro");
            json = json.replace("uf","UF");


            String arr[] = json.split(",");
            arr[0] = arr[0].toUpperCase();
            json = arr[0] + arr[1] + arr[3] + arr[4] + arr[5];

            /*Gson gson = new Gson();
            JsonArray arr = new Gson().fromJson(json, JsonArray.class);
            System.out.println(arr.get(0).getAsJsonObject().get("cep"));*/

            adapter.add(json);
        } catch(InterruptedException ie) {
            ie.printStackTrace();
        } catch (ExecutionException ee) {
            ee.printStackTrace();
        }
        catch (Exception e) {
            adapter.add("Cep Inválido");

        }

    }
}
