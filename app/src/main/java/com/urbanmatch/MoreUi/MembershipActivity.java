package com.urbanmatch.MoreUi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.urbanmatch.MainActivity;
import com.urbanmatch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class MembershipActivity extends AppCompatActivity implements PaymentResultListener {

    Button subscription;
    Boolean subscriptionStatus;

    TextView subsrciptionStatusText;

    Uri uri;

    public static final String GPAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    public static String status;

    final int UPI_PAYMENT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);

        subsrciptionStatusText = findViewById(R.id.textView13);

        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("userData").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot = task.getResult();
                subscriptionStatus = snapshot.getBoolean("subscription");
                Log.i("sub",String.valueOf(subscriptionStatus));
                if(subscriptionStatus = false){
                    subsrciptionStatusText.setText("Not subscribed");
                }
                else if(subscriptionStatus = true){
                    subsrciptionStatusText.setText("Subscribed");
                }
            }
        });

        subscription = findViewById(R.id.subscription);
        subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = getUpiPaymentUri();
                payWithGpay(GPAY_PACKAGE_NAME);
            }
        });

    }

    private static Uri getUpiPaymentUri(){
        return new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa", "7045604916@upi")
                .appendQueryParameter("pn", "UrbanMatch")
                .appendQueryParameter("tn", "Premium Subscription")
                .appendQueryParameter("am","1")
                .appendQueryParameter("cu","INR")
                .build();
    }

    private void payWithGpay(String packageName){

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        Intent chooser = Intent.createChooser(intent, "pay with");
        if(null!= chooser.resolveActivity(getPackageManager())){
            startActivityForResult(chooser, UPI_PAYMENT);
        }
        else {
            Toast.makeText(MembershipActivity.this, "No UPI App found",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data!=null){
            status = data.getStringExtra("Status").toLowerCase();
        }
        if((RESULT_OK == resultCode) && status.equals("success")){
            Toast.makeText(MembershipActivity.this, "Transaction successful", Toast.LENGTH_SHORT).show();
            Log.i("payment","success");

            FirebaseFirestore db;
            db = FirebaseFirestore.getInstance();

            HashMap<String, Object> subscription = new HashMap<>();
            subscription.put("subscription",true);

            db.collection("userData").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).update(subscription).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Intent intent = new Intent(MembershipActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }
        else {
            Toast.makeText(MembershipActivity.this, "Transaction Failed", Toast.LENGTH_SHORT).show();
            Log.i("payment","failed");
        }
    }

    public static boolean isAppInstalled(Context context, String packageName){
        try {
            context.getPackageManager().getApplicationInfo(packageName,0);
            return true;
        }catch (PackageManager.NameNotFoundException e){
            return false;
        }
    }

    public void startPayment() {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();

        /**
         * Set your logo here
         */
//        checkout.setImage(R.drawable.logo1);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: ACME Corp || HasGeek etc.
             */
            options.put("name", "UrbanMatch");

            /**
             * Description can be anything
             * eg: Reference No. #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.
             *     Invoice Payment
             *     etc.
             */
            options.put("description", "Premium Subscription ");
            options.put("currency", "INR");

            /**
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            options.put("amount", String.valueOf(12900));

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        HashMap<String, Object> subscription = new HashMap<>();
        subscription.put("subscription",true);

        db.collection("userData").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).update(subscription).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(MembershipActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onPaymentError(int i, String s) {

    }
}