package constactsmanager.lab04.eim.system.cs.pub.ro.contactsmanager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.net.ProtocolFamily;
import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText addressEditText;
    private EditText jobTitleEditText;
    private EditText companyEditText;
    private EditText websiteEditText;
    private EditText imEditText;

    private Button showHide;
    private Button save;
    private Button cancel;
    private LinearLayout additionalFieldsContainer;

    ButtonListener listenerB = new ButtonListener();
    private class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Button b = (Button)view;
            switch (b.getId()){
                case R.id.b_show_hide:
                    switch (additionalFieldsContainer.getVisibility()){
                        case View.VISIBLE:
                            showHide.setText(R.string.show);
                            additionalFieldsContainer.setVisibility(View.INVISIBLE);
                            break;
                        case View.INVISIBLE:
                            showHide.setText(R.string.hide);
                            additionalFieldsContainer.setVisibility(View.VISIBLE);
                            break;
                    }
                    break;
                case R.id.b_cancel:
                    setResult(Activity.RESULT_CANCELED, new Intent());
                    finish();
                    break;
                case R.id.b_save:

                    String name = nameEditText.getText().toString();
                    String phone = phoneEditText.getText().toString();
                    String email = emailEditText.getText().toString();
                    String address = addressEditText.getText().toString();
                    String job = jobTitleEditText.getText().toString();
                    String company = companyEditText.getText().toString();
                    String website = websiteEditText.getText().toString();
                    String im = imEditText.getText().toString();

                    Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                    if (name != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                    }
                    if (phone != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                    }
                    if (email != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                    }
                    if (address != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                    }
                    if (job != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, job);
                    }
                    if (company != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                    }

                    ArrayList<ContentValues> contactData = new ArrayList<>();
                    if (website != null) {
                        ContentValues websiteRow = new ContentValues();
                        websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                        websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                        contactData.add(websiteRow);
                    }
                    if (im != null) {
                        ContentValues imRow = new ContentValues();
                        imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                        imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                        contactData.add(imRow);
                    }
                    intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                    startActivityForResult(intent, Constants.CONTACTS_MANAGER_REQUEST_CODE);
                    break;
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        nameEditText = (EditText)findViewById(R.id.edit_name);
        phoneEditText = (EditText)findViewById(R.id.edit_phone);
        emailEditText = (EditText)findViewById(R.id.edit_email);
        addressEditText = (EditText)findViewById(R.id.edit_address);
        jobTitleEditText = (EditText)findViewById(R.id.edit_job);
        companyEditText = (EditText)findViewById(R.id.edit_company);
        websiteEditText = (EditText)findViewById(R.id.edit_website);
        imEditText = (EditText)findViewById(R.id.edit_im);

        additionalFieldsContainer = (LinearLayout)findViewById(R.id.container_invisible);

        showHide = (Button)findViewById(R.id.b_show_hide);
        showHide.setOnClickListener(listenerB);
        save = (Button)findViewById(R.id.b_save);
        save.setOnClickListener(listenerB);
        cancel = (Button)findViewById(R.id.b_cancel);
        cancel.setOnClickListener(listenerB);

        Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra("contactsmanager.lab04.contactsmanager.PHONE_NUMBER_KEY");
            Log.d("test", "test:........" + phone);
            if (phone != null) {
                phoneEditText.setText(phone);
            } else {
                Toast.makeText(this, getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
            }
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode) {
            case Constants.CONTACTS_MANAGER_REQUEST_CODE:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }
}
