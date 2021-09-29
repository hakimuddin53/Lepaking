package com.example.lepaking.view.fragment;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.lepaking.BuildConfig;
import com.example.lepaking.R;
import com.jakewharton.rxbinding2.view.RxView;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by jhcheng on 9/28/2017.
 */

public class AboutDialogFragment extends Fragment {

    private static final String TAG = AboutDialogFragment.class.getSimpleName();
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.dialog_fragment_about, container, false);


        setVersionInfo();
        setContactClickable();
      //  setEmailClickable();


        return mView;
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    private void setVersionInfo() {
        TextView textViewVersionInfo = (TextView) mView.findViewById(R.id.about_version_value);
        textViewVersionInfo.setText(BuildConfig.VERSION_NAME);
    }

    private void setContactClickable() {
        TextView contactInfo = (TextView) mView.findViewById(R.id.about_contact_value);
        RxView.clicks(contactInfo).subscribe(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + contactInfo.getText().toString()));
            startActivity(callIntent);
        });
    }

//    private void setEmailClickable() {
////        SharedPreferences sharedPreferences = PreferenceHelper.getSharedPreferences(getContext(), getString(R.string.pref_name_user));
//
//        String userName = "admin";
//        long userId = 1;
//        String subject = userName + " (UserID: " + userId + ") - ";
//
//        TextView emailInfo = (TextView) mView.findViewById(R.id.about_email_value);
//        String[] emailAddress = new String[]{emailInfo.getText().toString()};
//
//        RxView.clicks(emailInfo).subscribe(v -> {
//
//            File loggingFileA = new File(String.valueOf(getContext().getDatabasePath("logging.db")));
//            File loggingFileB = new File(getContext().getFilesDir(), "logging.db");
//
//            try {
//                copy(loggingFileA, loggingFileB);
//            } catch (Exception ex){
//                Log.e(TAG, ex.getMessage());
//            }
//            final Uri logUri = FileProvider.getUriForFile(getContext(), "com.resmal.smartmerchant.fileprovider", loggingFileB);
//
//            final Intent mailIntent = ShareCompat.IntentBuilder.from(getActivity())
//                    .setType("message/rfc2822")
//                    .setEmailTo(emailAddress)
//                    .setSubject(subject)
//                    .setStream(logUri)
//                    .setChooserTitle(R.string.title_email_chooser)
//                    .createChooserIntent()
//                    .addFlags(FLAG_GRANT_READ_URI_PERMISSION);
//            if (mailIntent.resolveActivity(getContext().getPackageManager()) != null) {
//                startActivity(mailIntent);
//            }
//        });
//    }

    private void copy (File source, File destination) throws IOException {
        InputStream in = new FileInputStream(source);
        OutputStream out = new FileOutputStream(destination);
        try{
            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }finally{
            in.close();
            out.close();
        }
    }
}


