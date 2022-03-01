package com.example.sneakercity.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sneakercity.Helpes.UtilsHelper;
import com.example.sneakercity.Models.Invoice;
import com.example.sneakercity.Models.User;
import com.example.sneakercity.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class InvoiceActivity extends AppCompatActivity {

    private Invoice mInvoice;
    private EditText name, country, community, city, postalCode, phone, address, address2;
    private MaterialButton save;
    private Toolbar toolbar;
    private boolean isNew = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        setToolbar();
        onInit();

        final String idAddress = getIntent().getStringExtra("key");
        if (idAddress != null){
            isNew = false;
            UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("invoiceAddress").child(idAddress).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User post = snapshot.getValue(User.class);
                    name.setText(post.getName());
                    country.setText(post.getCountry());
                    community.setText(post.getCommunity());
                    city.setText(post.getCity());
                    postalCode.setText(post.getPostalCode());
                    phone.setText(post.getPhone());
                    address.setText(post.getAddress());
                    address2.setText(post.getAddress2());
                    mInvoice.setIdInvoice(snapshot.getKey());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveInformation();
            }
        });
    }

    private void saveInformation(){
        name.setError((null));
        country.setError(null);
        community.setError(null);
        city.setError(null);
        postalCode.setError(null);
        phone.setError(null);
        address.setError(null);
        address2.setError(null);

        if (name.getText().toString().equals("")){
            name.setError(getString(R.string.enter_name_message));


        }else if (country.getText().toString().equals("")){
            country.setError(getString(R.string.enter_country_message));

        }else if (community.getText().toString().equals("")){
            community.setError(getString(R.string.enter_community_message));

        }else if (city.getText().toString().equals("")){
            city.setError(getString(R.string.enter_city_message));

        }else if (postalCode.getText().toString().equals("")){
            postalCode.setError(getString(R.string.enter_zip_code_message));

        }else if (phone.getText().toString().equals("")){
            phone.setError(getString(R.string.enter_phone_message));

        }else if (address.getText().toString().equals("")){
            address.setError(getString(R.string.enter_address_message));

        }else {
            mInvoice.setName(name.getText().toString());
            mInvoice.setCountry(country.getText().toString());
            mInvoice.setCommunity(community.getText().toString());
            mInvoice.setCity(city.getText().toString());
            mInvoice.setPostalCode(postalCode.getText().toString());
            mInvoice.setPhone(phone.getText().toString());
            mInvoice.setAddress(address.getText().toString());
            mInvoice.setAddress2(address2.getText().toString());
            //Guardar informacion
            mInvoice.saveBillingAddressInformation(isNew);
            Toast toast = Toast.makeText(InvoiceActivity.this, getResources().getString(R.string.information_saved_correctly_message), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0,0);
            toast.show();

        }
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.user_address_message);
        setSupportActionBar(toolbar);
    }

    private void onInit() {
        name = findViewById(R.id.etName);
        country = findViewById(R.id.etCountry);
        community = findViewById(R.id.etCommunity);
        city = findViewById(R.id.etCity);
        postalCode = findViewById(R.id.etPostalCode);
        phone = findViewById(R.id.etPhone);
        address = findViewById(R.id.etAddress);
        address2 = findViewById(R.id.etAddress2);
        save = findViewById(R.id.btnSave);
        mInvoice = new Invoice();

    }
}