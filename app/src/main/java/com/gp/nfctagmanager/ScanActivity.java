package com.gp.nfctagmanager;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ScanActivity extends Activity {
    public static final String EXTRA_ID =
            "com.gp.nfctagmanager.EXTRA_ID";
    public static final String EXTRA_NAME =
            "com.gp.nfctagmanager.EXTRA_NAME";
    public static final String EXTRA_TAG =
            "com.gp.nfctagmanager.EXTRA_TAG";

    NfcAdapter nfcAdapter;

    private static final String TAG = "ScanActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        TextView tagNameView = findViewById(R.id.text_view_name);

        Intent intent = getIntent();

        tagNameView.setText(intent.getStringExtra(EXTRA_NAME));

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        // Register callback
        nfcAdapter.setNdefPushMessage(createNdef(), this);


    }

    public NdefMessage createNdef() {
        NdefRecord record = NdefRecord.createTextRecord("en_GB", EXTRA_TAG);
        return new NdefMessage(record);
    }


//        tagIDString = intent.getStringExtra(EXTRA_TAG);
//
//        NdefMessage ndefMessage = createNdef(tagIDString);
//
//        //Check if NFC is available on device
//        NfcAdapter mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
//        if (mNfcAdapter != null) {
//
//            Log.d(TAG, "PROGRAM WAS HERE");
//            //This will refer back to createNdefMessage for what it will send
//            mNfcAdapter.setNdefPushMessage(ndefMessage, this);
//
//            //This will be called if the message is sent successfully
//            //mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
//        } else {
//            Toast.makeText(this, "NFC not available on this device",
//                    Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
////    @Override
////    public NdefMessage createNdefMessage(NfcEvent event) {
////        byte[] payload = tagIDString.
////                getBytes(Charset.forName("UTF-8"));
////
////        NdefRecord record = NdefRecord.createMime("text/plain",payload);
////        return new NdefMessage(record);
////    }
//
//    public NdefMessage createNdef(String tagID) {
//        byte[] payload = tagID.
//                getBytes(Charset.forName("UTF-8"));
//
//        NdefRecord record = NdefRecord.createMime("text/plain", payload);
//        return new NdefMessage(record);
//    }
//
//    @Override
//    public void onNdefPushComplete(NfcEvent event) {
//        Toast.makeText(this, "NFC Tag Scanned",
//                Toast.LENGTH_SHORT).show();
//        //This is called when the system detects that our NdefMessage was
//        //Successfully sent
//    }
//
//    @Override
//    public NdefMessage createNdefMessage(NfcEvent event) {
//        byte[] payload = tagIDString.
//                getBytes(Charset.forName("UTF-8"));
//
//        NdefRecord record = NdefRecord.createMime("text/plain", payload);
//        return new NdefMessage(record);
//    }
}
