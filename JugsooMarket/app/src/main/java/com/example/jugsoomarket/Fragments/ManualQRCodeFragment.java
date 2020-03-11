package com.example.jugsoomarket.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.util.Pair;
import android.os.Vibrator;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.jugsoomarket.R;
import com.example.jugsoomarket.Utils.dbProcessor;
import com.example.jugsoomarket.ViewModel.ItemViewModel;
import com.example.jugsoomarket.ViewModel.item;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

public class ManualQRCodeFragment extends Fragment {
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final String QRCODE_FOUND = "QRCode Match Found in DB";
    private static final String QRCODE_INVALID = "That product ID is INVALID Please type a valid Value";
    private static final String ITEM_ADDED = "Item Added";
    private EditText editTextProductID;
    private EditText numberPickerQuantity;
    private Button btnSave;
    private Button btnCancel;
    private ItemViewModel itemViewModel;
    private SurfaceView surfaceView;
    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector;
    private Map <String, Object> objectMap;
    private Map <String, String> qrMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_manual_qrcode, container, false);
        setViews(rootView);
        initDBMap();
        initBarcode();
        return rootView;
    }

    private void setViews(View rootView) {
        editTextProductID = rootView.findViewById(R.id.item_id);
        numberPickerQuantity = rootView.findViewById(R.id.number_picker_qunatity);
        btnSave = rootView.findViewById(R.id.save);
        btnCancel = rootView.findViewById(R.id.cancel);
        surfaceView = rootView.findViewById(R.id.camerapreview);
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveItem();
                } catch (JSONException e) {
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }

    private void initDBMap() {
        dbProcessor dbProcessor = new dbProcessor();
        Pair<Map<String, Object>, Map<String,String>> dbPairMap = dbProcessor.processDB(getContext());
        objectMap = dbPairMap.first;
        qrMap = dbPairMap.second;
    }

    private void initBarcode() {
        if (getContext() == null) return;
        barcodeDetector = new BarcodeDetector.Builder(getContext())
                .setBarcodeFormats(Barcode.QR_CODE).build();

        cameraSource = new CameraSource.Builder(getContext(), barcodeDetector)
                .setRequestedPreviewSize(640, 480).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (getContext() == null) return;
                if (ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                                    new String[]{Manifest.permission.CAMERA},
                                    CAMERA_PERMISSION_CODE);
                } else {
                    try {
                        cameraSource.start(holder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }


            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();
                if (qrCodes.size() !=0) {
                    editTextProductID.post(new Runnable() {
                        @Override
                        public void run() {
                            if (qrMap.containsKey(qrCodes.valueAt(0).displayValue )) {
                                Vibrator vibrator = (Vibrator)getContext().getSystemService(Context.VIBRATOR_SERVICE);
                                vibrator.vibrate(100);
                                editTextProductID.setText(qrMap.get(qrCodes.valueAt(0).displayValue));
                                Toast.makeText(getContext(), QRCODE_FOUND, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (permissions[0].equals(Manifest.permission.CAMERA)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    cameraSource.start(surfaceView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void saveItem() throws JSONException {
        String itemID = editTextProductID.getText().toString();
        int quantity = Integer.valueOf (numberPickerQuantity.getText().toString());
        if (!objectMap.containsKey(itemID)) {
            Toast.makeText(getContext(), QRCODE_INVALID, Toast.LENGTH_LONG).show();
        } else {
            JSONObject jsonObject = (JSONObject)objectMap.get(itemID);
            int id = Integer.parseInt(itemID);
            String qrUrl = jsonObject.getString("qrUrl");
            String name = jsonObject.getString("name");
            double doublePrice = Double.parseDouble(jsonObject.getString("price").substring(1));
            int price = (int) Math.round(doublePrice);
            String thumbnail = jsonObject.getString("thumbnail");
            item item = new item (id, qrUrl, name, price, thumbnail, quantity);
            itemViewModel.delete(item); //temp fix for a crash on multiple insert TODO: Make a map of list in the cart with ID. if ID matches, dont insert.
            itemViewModel.insert(item);
            Toast.makeText(getContext(), ITEM_ADDED, Toast.LENGTH_LONG).show();
            getFragmentManager().popBackStack();
        }
    }
}
