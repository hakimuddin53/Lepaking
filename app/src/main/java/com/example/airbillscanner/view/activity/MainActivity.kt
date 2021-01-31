//package com.example.airbillscanner.view.activity
//
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.Button
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import com.example.airbillscanner.R
//import com.example.airbillscanner.common.provider.DriveServiceHelper
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.android.gms.tasks.OnFailureListener
//import com.google.android.gms.tasks.OnSuccessListener
//import com.google.api.services.drive.Drive
//import com.google.gson.Gson
//import java.io.File
//
//
//class MainActivity : AppCompatActivity() {
//    private var mGoogleSignInClient: GoogleSignInClient? = null
//    private var mDriveServiceHelper: DriveServiceHelper? = null
//    private val login: Button? = null
//    private var gDriveAction: LinearLayout? = null
//    private var searchFile: Button? = null
//    private var searchFolder: Button? = null
//    private var createTextFile: Button? = null
//    private var createFolder: Button? = null
//    private var uploadFile: Button? = null
//    private var downloadFile: Button? = null
//    private var deleteFileFolder: Button? = null
//    private var email: TextView? = null
//    private var viewFileFolder: Button? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        initView()
//        searchFile!!.setOnClickListener(View.OnClickListener {
//            if (mDriveServiceHelper == null) {
//                return@OnClickListener
//            }
//            mDriveServiceHelper.searchFile("textfilename.txt", "text/plain")
//                .addOnSuccessListener(object : OnSuccessListener<List<GoogleDriveFileHolder?>?> {
//                    override fun onSuccess(googleDriveFileHolders: List<GoogleDriveFileHolder?>?) {
//                        val gson = Gson()
//                        Log.d(TAG, "onSuccess: " + gson.toJson(googleDriveFileHolders))
//                    }
//                })
//                .addOnFailureListener(OnFailureListener { e ->
//                    Log.d(
//                        TAG,
//                        "onFailure: " + e.message
//                    )
//                })
//        })
//        searchFolder!!.setOnClickListener(View.OnClickListener {
//            if (mDriveServiceHelper == null) {
//                return@OnClickListener
//            }
//            mDriveServiceHelper.searchFolder("testDummy")
//                .addOnSuccessListener(object : OnSuccessListener<List<GoogleDriveFileHolder?>?> {
//                    override fun onSuccess(googleDriveFileHolders: List<GoogleDriveFileHolder?>?) {
//                        val gson = Gson()
//                        Log.d(TAG, "onSuccess: " + gson.toJson(googleDriveFileHolders))
//                    }
//                })
//                .addOnFailureListener(OnFailureListener { e ->
//                    Log.d(
//                        TAG,
//                        "onFailure: " + e.message
//                    )
//                })
//        })
//        createTextFile!!.setOnClickListener(View.OnClickListener {
//            if (mDriveServiceHelper == null) {
//                return@OnClickListener
//            }
//            // you can provide  folder id in case you want to save this file inside some folder.
//            // if folder id is null, it will save file to the root
//            mDriveServiceHelper.createTextFile("textfilename.txt", "some text", null)
//                .addOnSuccessListener(OnSuccessListener<Any?> { googleDriveFileHolder ->
//                    val gson = Gson()
//                    Log.d(TAG, "onSuccess: " + gson.toJson(googleDriveFileHolder))
//                })
//                .addOnFailureListener(OnFailureListener { e ->
//                    Log.d(
//                        TAG,
//                        "onFailure: " + e.message
//                    )
//                })
//        })
//        createFolder!!.setOnClickListener(View.OnClickListener {
//            if (mDriveServiceHelper == null) {
//                return@OnClickListener
//            }
//            // you can provide  folder id in case you want to save this file inside some folder.
//            // if folder id is null, it will save file to the root
//            mDriveServiceHelper.createFolder("testDummyss", null)
//                .addOnSuccessListener(OnSuccessListener<Any?> { googleDriveFileHolder ->
//                    val gson = Gson()
//                    Log.d(TAG, "onSuccess: " + gson.toJson(googleDriveFileHolder))
//                })
//                .addOnFailureListener(OnFailureListener { e ->
//                    Log.d(
//                        TAG,
//                        "onFailure: " + e.message
//                    )
//                })
//        })
//        uploadFile!!.setOnClickListener(View.OnClickListener {
//            if (mDriveServiceHelper == null) {
//                return@OnClickListener
//            }
//            mDriveServiceHelper.uploadFile(
//                File(applicationContext.filesDir, "dummy.txt"),
//                "text/plain",
//                null
//            )
//                .addOnSuccessListener(OnSuccessListener<Any?> { googleDriveFileHolder ->
//                    val gson = Gson()
//                    Log.d(TAG, "onSuccess: " + gson.toJson(googleDriveFileHolder))
//                })
//                .addOnFailureListener(OnFailureListener { e ->
//                    Log.d(
//                        TAG,
//                        "onFailure: " + e.message
//                    )
//                })
//        })
//        downloadFile!!.setOnClickListener(View.OnClickListener {
//            if (mDriveServiceHelper == null) {
//                return@OnClickListener
//            }
//            mDriveServiceHelper.downloadFile(
//                File(applicationContext.filesDir, "filename.txt"),
//                "google_drive_file_id_here"
//            )
//                .addOnSuccessListener(OnSuccessListener<Void?> { })
//                .addOnFailureListener(OnFailureListener { })
//        })
//        viewFileFolder!!.setOnClickListener { //                if (mDriveServiceHelper == null) {
//            //                    return;
//            //                }
//            //
//            //                mDriveServiceHelper.queryFiles()
//            //                        .addOnSuccessListener(new OnSuccessListener<List<GoogleDriveFileHolder>>() {
//            //                            @Override
//            //                            public void onSuccess(List<GoogleDriveFileHolder> googleDriveFileHolders) {
//            //                                Gson gson = new Gson();
//            //                                Log.d(TAG, "onSuccess: " + gson.toJson(googleDriveFileHolders));
//            //                            }
//            //                        })
//            //                        .addOnFailureListener(new OnFailureListener() {
//            //                            @Override
//            //                            public void onFailure(@NonNull Exception e) {
//            //
//            //                            }
//            //                        });
//            val openActivity = Intent(applicationContext, GDriveDebugViewActivity::class.java)
//            startActivity(openActivity)
//        }
//    }
//
//    override fun onStart() {
//        super.onStart()
//        val account = GoogleSignIn.getLastSignedInAccount(applicationContext)
//        if (account == null) {
//            signIn()
//        } else {
//            email!!.text = account.email
//            mDriveServiceHelper =
//                DriveServiceHelper(getGoogleDriveService(applicationContext, account, "appName"))
//        }
//    }
//
//    private fun signIn() {
//        mGoogleSignInClient = buildGoogleSignInClient()
//        startActivityForResult(mGoogleSignInClient!!.signInIntent, REQUEST_CODE_SIGN_IN)
//    }
//
//    private fun buildGoogleSignInClient(): GoogleSignInClient {
//        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestScopes(Drive.SCOPE_FILE)
//            .requestEmail()
//            .build()
//        return GoogleSignIn.getClient(applicationContext, signInOptions)
//    }
//
//    public override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
//        when (requestCode) {
//            REQUEST_CODE_SIGN_IN -> if (resultCode == RESULT_OK && resultData != null) {
//                handleSignInResult(resultData)
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, resultData)
//    }
//
//    fun test() {
//        println("test")
//    }
//
//    private fun handleSignInResult(result: Intent) {
//        GoogleSignIn.getSignedInAccountFromIntent(result)
//            .addOnSuccessListener { googleSignInAccount ->
//                Log.d(
//                    TAG,
//                    "Signed in as " + googleSignInAccount.email
//                )
//                email!!.text = googleSignInAccount.email
//                mDriveServiceHelper = DriveServiceHelper(
//                    getGoogleDriveService(
//                        applicationContext,
//                        googleSignInAccount,
//                        "appName"
//                    )
//                )
//                Log.d(
//                    TAG,
//                    "handleSignInResult: $mDriveServiceHelper"
//                )
//            }
//            .addOnFailureListener { e ->
//                Log.e(
//                    TAG,
//                    "Unable to sign in.",
//                    e
//                )
//            }
//    }
//
//    private fun initView() {
//        email = findViewById(R.id.email)
//        gDriveAction = findViewById(R.id.g_drive_action)
//        searchFile = findViewById(R.id.search_file)
//        searchFolder = findViewById(R.id.search_folder)
//        createTextFile = findViewById(R.id.create_text_file)
//        createFolder = findViewById(R.id.create_folder)
//        uploadFile = findViewById(R.id.upload_file)
//        downloadFile = findViewById(R.id.download_file)
//        deleteFileFolder = findViewById(R.id.delete_file_folder)
//        viewFileFolder = findViewById(R.id.view_file_folder)
//    }
//
//    companion object {
//        private const val REQUEST_CODE_SIGN_IN = 100
//        private const val TAG = "MainActivity"
//    }
//}