package com.healstationlab.design.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivityUserSkinInfoBinding
import com.healstationlab.design.dto.auth
import com.healstationlab.design.dto.userInfo
import com.healstationlab.design.resource.App
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.RetrofitMansae
import com.healstationlab.design.ui.report.ReportActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EditProfileActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityUserSkinInfoBinding
    private val testList : ArrayList<String> = arrayListOf()
    var skinType = ""
    var gender = ""
    var child : Boolean? = null
    var check = false
    var nickCheck = true
    private var skinArray : Array<String> = Array(8) {""}
//    var imgUri : Uri? = null
//    var bitmap : Bitmap? = null
    var birth = ""
    private lateinit var plzFile : File
    private lateinit var photoURI : Uri
    private lateinit var currentPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserSkinInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back2.setOnClickListener {
            hideKeyboard()
        }

        /** 피부타입 클릭 리스너 **/
        binding.gun.setOnClickListener(this)
        binding.joong.setOnClickListener(this)
        binding.ji.setOnClickListener(this)
        binding.bok.setOnClickListener(this)

        /** 피부고민 클릭 리스너 **/
        binding.nothing.setOnClickListener(this)
        binding.atophy.setOnClickListener(this)
        binding.yudrm.setOnClickListener(this)
        binding.min.setOnClickListener(this)
        binding.sac.setOnClickListener(this)
        binding.black.setOnClickListener(this)
        binding.joo.setOnClickListener(this)
        binding.mo.setOnClickListener(this)

        /** 성별, 자녀여부 클릭 리스너 **/
        binding.childNoBtn.setOnClickListener(this)
        binding.childYesBtn.setOnClickListener(this)


        binding.editTextTextPersonName6.setText(App.prefs.getStringData(Constant.NICK_NAME))

        binding.imageView11.setOnClickListener {
            permission()
        }

        when(App.prefs.getBooleanData(Constant.MEITU_DATA_CHECK)){
            true -> {
                binding.textView373.isVisible = true //editing by adi
                binding.skinDateText.isVisible = true
                binding.textView383.isVisible = true
            }
            else -> {}
        }
        val idNodata = App.prefs.getIntData(Constant.UID)
        Log.d("user-id", idNodata.toString())

        binding.skinDateText.setOnClickListener {
            val intent = Intent(this, ReportActivity::class.java)
            if (idNodata == 717){
                intent.putExtra("no-data-after-survey",1)
            }
            startActivity(intent)
            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

//        val arrayYearList : ArrayList<Int> = arrayListOf()
//        for(i in 1950..2002){
//            arrayYearList.add(i)
//        }

//        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayYearList)
//        binding.yearSpinner.adapter = spinnerAdapter
//
//        binding.yearSpinner.apply {
//            adapter = spinnerAdapter
//            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                override fun onNothingSelected(p0: AdapterView<*>?) {
//
//                }
//
//                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, p3: Long) {
//
//                }
//            }
//        }

        binding.imageView76.setOnClickListener { onBackPressed() }


        binding.confirmButton.setOnClickListener {
            if(App.prefs.getStringData(Constant.NICK_NAME) != binding.editTextTextPersonName6.text.toString()){
                if(check){
                    // 비밀번호 변경 로직
                    if(binding.editTextTextPersonName3.text.toString().isEmpty() && binding.editTextTextPersonName14.text.toString().isEmpty()){
                        editUserInfo()
                    } else if(binding.editTextTextPersonName3.text.toString() == binding.editTextTextPersonName14.text.toString()){
                        newPassword()
                    } else {
                        Toast.makeText(this, "입력한 비밀번호와 다릅니다!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@EditProfileActivity, "닉네임 중복체크를 해주세요", Toast.LENGTH_SHORT).show()
                }

            } else {
                if(binding.editTextTextPersonName3.text.toString().isEmpty() && binding.editTextTextPersonName14.text.toString().isEmpty()){
                    editUserInfo()
                } else if(binding.editTextTextPersonName3.text.toString() == binding.editTextTextPersonName14.text.toString()){
                    newPassword()
                } else {
                    Toast.makeText(this, "입력한 비밀번호와 다릅니다!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.authButton3.setOnClickListener {
            if(binding.editTextTextPersonName6.text.toString().isEmpty()){
                Toast.makeText(this@EditProfileActivity, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                nicknameCheck(binding.editTextTextPersonName6.text.toString())
            }
        }

        getUser()
    }

    override fun onClick(view: View?) {
        when(view){
            binding.joong -> {
                click(binding.joong)
                skinType = "NEUTRAL"
            }
            binding.ji -> {
                click(binding.ji)
                skinType = "OILY"
            }

            binding.bok -> {
                click(binding.bok)
                skinType = "COMPOSITE"
            }
            binding.gun -> {
                click(binding.gun)
                skinType = "DRY"
            }

            binding.nothing -> {
                skinNoting(binding.atophy)
                skinNoting(binding.yudrm)
                skinNoting(binding.min)
                skinNoting(binding.sac)
                skinNoting(binding.black)
                skinNoting(binding.joo)
                skinNoting(binding.mo)
            }

            binding.atophy -> { skinClick(binding.atophy, 1) }
            binding.yudrm -> skinClick(binding.yudrm, 2)
            binding.min -> skinClick(binding.min, 3)
            binding.sac -> skinClick(binding.sac, 4)
            binding.black -> skinClick(binding.black, 5)
            binding.joo -> skinClick(binding.joo, 6)
            binding.mo -> skinClick(binding.mo, 7)

            binding.mailBtn -> {
                sexAndChild(binding.mailBtn)
                gender = "MAN"
            }
            binding.femailBtn -> {
                sexAndChild(binding.femailBtn)
                gender = "WOMAN"
            }
            binding.childYesBtn -> {
                child = true
                sexAndChild(binding.childYesBtn)
            }
            binding.childNoBtn -> {
                child = false
                sexAndChild(binding.childNoBtn)
            }
        }
    }

    /** 호출 시 전체 초기화 **/
    private fun checkd(){
        binding.gun.setBackgroundResource(R.drawable.border_gray_stroke)
        binding.joong.setBackgroundResource(R.drawable.border_gray_stroke)
        binding.ji.setBackgroundResource(R.drawable.border_gray_stroke)
        binding.bok.setBackgroundResource(R.drawable.border_gray_stroke)
        binding.gun.setTextColor(Color.parseColor("#727272"))
        binding.joong.setTextColor(Color.parseColor("#727272"))
        binding.ji.setTextColor(Color.parseColor("#727272"))
        binding.bok.setTextColor(Color.parseColor("#727272"))
    }

    fun click(button : Button) {
        checkd()
        /** 클릭한 버튼만 활성화 **/
        button.setBackgroundResource(R.drawable.border_stroke_green)
        button.setTextColor(Color.WHITE)
    }

    /** 피부고민 함수 글씨색상이 하얀 색인 거로 분기**/
    fun skinClick(button : Button, pos : Int){
        when(button.currentTextColor == -1) {
            true -> {
                when(pos){
                    1 -> {
                        button.setBackgroundResource(R.drawable.border_gray_stroke)
                        button.setTextColor(Color.parseColor("#727272"))
                        skinArray[0] = ""
                    }
                    2 -> {
                        button.setBackgroundResource(R.drawable.border_gray_stroke)
                        button.setTextColor(Color.parseColor("#727272"))
                        skinArray[1] = ""
                    }
                    3 -> {
                        button.setBackgroundResource(R.drawable.border_gray_stroke)
                        button.setTextColor(Color.parseColor("#727272"))
                        skinArray[2] = ""
                    }
                    4 -> {
                        button.setBackgroundResource(R.drawable.border_gray_stroke)
                        button.setTextColor(Color.parseColor("#727272"))
                        skinArray[3] = ""
                    }
                    5 -> {
                        button.setBackgroundResource(R.drawable.border_gray_stroke)
                        button.setTextColor(Color.parseColor("#727272"))
                        skinArray[4] = ""
                    }
                    6 -> {
                        button.setBackgroundResource(R.drawable.border_gray_stroke)
                        button.setTextColor(Color.parseColor("#727272"))
                        skinArray[5] = ""
                    }
                    7 -> {
                        button.setBackgroundResource(R.drawable.border_gray_stroke)
                        button.setTextColor(Color.parseColor("#727272"))
                        skinArray[6] = ""
                    }
                    8 -> {
                        button.setBackgroundResource(R.drawable.border_gray_stroke)
                        button.setTextColor(Color.parseColor("#727272"))
                        skinArray[7] = ""
                    }
                }
            }
            false -> {
                when(pos){
                    1 -> {
                        button.setBackgroundResource(R.drawable.border_stroke_green)
                        button.setTextColor(Color.WHITE)
                        skinArray[0] = "DARK_CIRCLE"
                    }
                    2 -> {
                        button.setBackgroundResource(R.drawable.border_stroke_green)
                        button.setTextColor(Color.WHITE)
                        skinArray[1] = "PIMPLE"
                    }
                    3 -> {
                        button.setBackgroundResource(R.drawable.border_stroke_green)
                        button.setTextColor(Color.WHITE)
                        skinArray[2] = "SENSITIVITY"
                    }
                    4 -> {
                        button.setBackgroundResource(R.drawable.border_stroke_green)
                        button.setTextColor(Color.WHITE)
                        skinArray[3] = "SHADE_SPOT"
                    }
                    5 -> {
                        button.setBackgroundResource(R.drawable.border_stroke_green)
                        button.setTextColor(Color.WHITE)
                        skinArray[4] = "BLACK_HEAD"
                    }
                    6 -> {
                        button.setBackgroundResource(R.drawable.border_stroke_green)
                        button.setTextColor(Color.WHITE)
                        skinArray[5] = "WRINKLE"
                    }
                    7 -> {
                        button.setBackgroundResource(R.drawable.border_stroke_green)
                        button.setTextColor(Color.WHITE)
                        skinArray[6] = "PORE"
                    }
                }
            }
        }
    }

    /** 해당없음 클릭 시 **/
    private fun skinNoting(button : Button){
        button.setBackgroundResource(R.drawable.border_gray_stroke)
        button.setTextColor(Color.parseColor("#727272"))
        for(i in skinArray.indices){
            skinArray[i] = ""
        }
        testList.clear()
    }

    private fun sexAndChild(btn : Button) {
        when(btn){
            binding.femailBtn -> {
                binding.femailBtn.setBackgroundResource(R.drawable.border_stroke_green)
                binding.femailBtn.setTextColor(Color.WHITE)
                binding.mailBtn.setBackgroundResource(R.drawable.border_gray_stroke)
                binding.mailBtn.setTextColor(Color.parseColor("#727272"))
            }

            binding.mailBtn -> {
                binding.femailBtn.setBackgroundResource(R.drawable.border_gray_stroke)
                binding.femailBtn.setTextColor(Color.parseColor("#727272"))
                binding.mailBtn.setBackgroundResource(R.drawable.border_stroke_green)
                binding.mailBtn.setTextColor(Color.WHITE)
            }

            binding.childYesBtn -> {
                binding.childYesBtn.setBackgroundResource(R.drawable.border_stroke_green)
                binding.childYesBtn.setTextColor(Color.WHITE)
                binding.childNoBtn.setBackgroundResource(R.drawable.border_gray_stroke)
                binding.childNoBtn.setTextColor(Color.parseColor("#727272"))
            }

            binding.childNoBtn -> {
                binding.childYesBtn.setBackgroundResource(R.drawable.border_gray_stroke)
                binding.childYesBtn.setTextColor(Color.parseColor("#727272"))
                binding.childNoBtn.setBackgroundResource(R.drawable.border_stroke_green)
                binding.childNoBtn.setTextColor(Color.WHITE)
            }
        }
    }

    private fun getUser(){
        RetrofitMansae.server.getUserInfo()
            .enqueue(object : Callback<userInfo>{
                override fun onFailure(call: Call<userInfo>, t: Throwable) {

                }

                override fun onResponse(call: Call<userInfo>, response: Response<userInfo>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            birth = response.body()!!.data.birthDate
                            binding.editTextTextPersonName6.setText(response.body()?.data?.nickname)
                            App.prefs.putStringData(Constant.NICK_NAME, response.body()?.data?.nickname)
                            binding.textView57.text = response.body()?.data?.email
                            binding.yearSpinner.setText(response.body()?.data?.birthDate)
                            if(response.body()?.data?.imageUrl != "" && response.body()?.data?.imageUrl != null){
                                Glide.with(this@EditProfileActivity).load(response.body()?.data?.imageUrl).into(binding.imageView11)
                                binding.imageView69.isVisible = false
                            }

                            when(response.body()?.data?.gender){
                                "WOMAN" -> {
                                    gender = "WOMAN"
                                    binding.femailBtn.setBackgroundResource(R.drawable.border_stroke_green)
                                    binding.femailBtn.setTextColor(Color.WHITE)
                                    binding.mailBtn.setBackgroundResource(R.drawable.border_gray_stroke)
                                    binding.mailBtn.setTextColor(Color.parseColor("#727272"))
                                }
                                "MAN" -> {
                                    gender = "MAN"
                                    binding.femailBtn.setBackgroundResource(R.drawable.border_gray_stroke)
                                    binding.femailBtn.setTextColor(Color.parseColor("#727272"))
                                    binding.mailBtn.setBackgroundResource(R.drawable.border_stroke_green)
                                    binding.mailBtn.setTextColor(Color.WHITE)
                                }
                                else -> {

                                }
                            }
                            when(response.body()!!.data.hasChildren){
                                true -> {
                                    child = true
                                    binding.childYesBtn.setBackgroundResource(R.drawable.border_stroke_green)
                                    binding.childYesBtn.setTextColor(Color.WHITE)
                                    binding.childNoBtn.setBackgroundResource(R.drawable.border_gray_stroke)
                                    binding.childNoBtn.setTextColor(Color.parseColor("#727272"))
                                }
                                false -> {
                                    child = false
                                    binding.childYesBtn.setBackgroundResource(R.drawable.border_gray_stroke)
                                    binding.childYesBtn.setTextColor(Color.parseColor("#727272"))
                                    binding.childNoBtn.setBackgroundResource(R.drawable.border_stroke_green)
                                    binding.childNoBtn.setTextColor(Color.WHITE)
                                }
                            }
                            when(response.body()?.data?.skinType){
                                "DRY" -> {
                                    click(binding.gun)
                                }

                                "NEUTRAL" -> {
                                    click(binding.joong)
                                }

                                "OILY" -> {
                                    click(binding.ji)
                                }

                                "COMPOSITE" -> {
                                    click(binding.bok)
                                }
                                else -> {

                                }
                            }
                            for (i in response.body()?.data!!.skinProblems){
                                when(i){
                                    "PIMPLE" -> {
                                        skinClick(binding.yudrm, 2)
                                    }

                                    "SENSITIVITY" -> {
                                        skinClick(binding.min, 3)
                                    }


                                    "SHADE_SPOT" -> {
                                        skinClick(binding.sac, 4)
                                    }

                                    "BLACK_HEAD" -> {
                                        skinClick(binding.black, 5)
                                    }

                                    "WRINKLE" -> {
                                        skinClick(binding.joo, 6)
                                    }

                                    "PORE" -> {
                                        skinClick(binding.mo, 7)
                                    }

                                    "DARK_CIRCLE" -> {
                                        skinClick(binding.atophy, 1)
                                    }
                                }
                            }
                        }
                    }
                }
            })
    }

    fun showDialog(){
        val alertDialog = AlertDialog.Builder(this)
                .setTitle("선택")
                .setMessage("사진을 선택해주세요.")
                .setPositiveButton("카메라"){ _: DialogInterface, _: Int ->
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                        // Ensure that there's a camera activity to handle the intent
                        takePictureIntent.resolveActivity(packageManager)?.also {
                            // Create the File where the photo should go
                            val photoFile: File? = try {
                                createImageFile()
                            } catch (ex: IOException) {
                                // Error occurred while creating the File
                                null
                            }
                            if (photoFile != null) {
                                plzFile = photoFile
                            }
                            photoFile?.also {
                                photoURI = FileProvider.getUriForFile(
                                        this,
                                        "com.healstationlab.design",
                                        it
                                )
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                                startActivityForResult(takePictureIntent, 1002)
                            }
                        }
                    }
                }
                .setNegativeButton("갤러리"){ _: DialogInterface, _: Int ->
                    val intent = Intent()
                    intent.action = Intent.ACTION_PICK
                    intent.type = "image/*"
                    startActivityForResult(intent, 1001)
                }.create()
        alertDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when (requestCode) {
                1001 -> {
                    val uri : Uri= data!!.data!!
                    binding.imageView11.setImageURI(uri)
                    binding.imageView69.isVisible = false
                    val file = File(absolutelyPath(data.data!!))
                    val requestFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
                    val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
                    editProfile(body)
                }

                1002 -> {
                    Glide.with(this).load(photoURI).into(binding.imageView11)
                    binding.imageView69.isVisible = false
                    val file = File(plzFile.toString())
                    val requestFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
                    val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
                    editProfile(body)
                }
            }
        }
    }

    private fun nicknameCheck(nickname : String){

        RetrofitMansae.server.nicknameCheck(nickname = nickname)
            .enqueue(object : Callback<auth>{
                override fun onFailure(call: Call<auth>, t: Throwable) {

                }

                override fun onResponse(call: Call<auth>, response: Response<auth>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            check = true
                            nickCheck = true
                            Toast.makeText(this@EditProfileActivity, "사용가능한 닉네임입니다.", Toast.LENGTH_SHORT).show()
                        }
                        "FAIL" -> {
                            check = false
                            nickCheck = false
                            Toast.makeText(this@EditProfileActivity, "이미 존재하는 닉네임 입니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
    }

    fun editUserInfo(){
        for(i in skinArray.indices){

            if(skinArray[i] != ""){
                testList.add(skinArray[i])
            }
        }

        RetrofitMansae.server.editUserInfo(nickname = binding.editTextTextPersonName6.text.toString(), skinProblems = testList, gender = gender, hasChildren = child!!, skinType = skinType, birthDate = birth)
            .enqueue(object : Callback<auth>{
                override fun onFailure(call: Call<auth>, t: Throwable) {

                }

                override fun onResponse(call: Call<auth>, response: Response<auth>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            App.prefs.putStringData(Constant.NICK_NAME, binding.editTextTextPersonName6.text.toString())
                            Toast.makeText(this@EditProfileActivity, "프로필이 수정되었습니다.", Toast.LENGTH_SHORT).show()
                            finish()
                            overridePendingTransition(0, R.xml.slide_right)
                        }

                    }
                }
            })
    }

    private fun editProfile(file : MultipartBody.Part){
        RetrofitMansae.server.postProfileImage(file = file)
            .enqueue(object : Callback<auth>{
                override fun onFailure(call: Call<auth>, t: Throwable) {

                }

                override fun onResponse(call: Call<auth>, response: Response<auth>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            Toast.makeText(this@EditProfileActivity, "프로필 사진을 변경했습니다.", Toast.LENGTH_SHORT).show()

                        }
                        else -> {

                        }
                    }
                }
            })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }

    private fun permission(){
        val permission = object : PermissionListener {
            override fun onPermissionGranted() {
                showDialog()
            }

            override fun onPermissionDenied(deniedPermissions: java.util.ArrayList<String>?) {
                Toast.makeText(this@EditProfileActivity, "권한을 허락해주세요!", Toast.LENGTH_SHORT).show()
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permission)
            .setRationaleMessage("앱을 사용하시려면 권한을 허용해주세요.")
            .setDeniedMessage("권한을 거부하셨습니다.앱을 사용하시려면 [앱 설정]-[권한] 항목에서 권한을 허용해주세요.")
            .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .check()
    }


    @SuppressLint("Recycle")
    fun absolutelyPath(path: Uri): String {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor? = contentResolver.query(path, proj, null, null, null)
        val index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()
        val result = c?.getString(index!!)

        return result!!
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }

    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.editTextTextPersonName6.windowToken, 0)
    }

    fun newPassword() {
        val body : HashMap<String, String> = HashMap()
        body["contact"] = App.prefs.getStringData(Constant.PHONE).toString()
        body["password"] = binding.editTextTextPersonName3.text.toString()

        RetrofitMansae.server.newPassword(body)
                .enqueue(object : Callback<auth>{
                    override fun onFailure(call: Call<auth>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<auth>, response: Response<auth>) {
                        when(response.body()?.responseCode){
                            "SUCCESS" -> {
                                editUserInfo()
                                App.prefs.putStringData(Constant.PASS_WORD, binding.editTextTextPersonName3.text.toString())
                            }
                            "FAIL" -> {

                            }
                            else -> {
                                Toast.makeText(this@EditProfileActivity, "서버에러", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
    }

//    fun getRealPathFromURI(uri: Uri?): String? {
//        var path = ""
//        if (contentResolver != null) {
//            val cursor =
//                contentResolver.query(uri!!, null, null, null, null)
//            if (cursor != null) {
//                cursor.moveToFirst()
//                val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
//                path = cursor.getString(idx)
//                cursor.close()
//            }
//        }
//        return path
//    }
}