package com.healstationlab.design.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.healstationlab.design.R
import com.healstationlab.design.adapter.HorizontalImgAdapter
import com.healstationlab.design.databinding.ActivityAddChatBinding
import com.healstationlab.design.dto.auth
import com.healstationlab.design.model.ImageBoard
import com.healstationlab.design.resource.ProgressDialog
import com.healstationlab.design.resource.RetrofitMansae
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddChatActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddChatBinding
    private lateinit var currentPhotoPath: String
    private lateinit var plzFile : File

    var imgAdpater : HorizontalImgAdapter? = null
    lateinit var imgModel : ArrayList<ImageBoard>
    val imgList : ArrayList<Any> = arrayListOf()
    var bodylist:ArrayList<MultipartBody.Part> = arrayListOf()
    var category = ""
    private var imgUri : Uri? = null
//    var bitmap : Bitmap? = null
    private lateinit var photoURI : Uri
    var editComment = 0
    var valueMessage = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editComment = intent.getIntExtra("edit-comment",0)
        valueMessage = intent.getStringExtra("value-message").toString()
        Log.d("edit-comment", "onCreate:  ${editComment.toString()}")
        Log.d("edit-comment", "onCreate:  ${valueMessage.toString()}")

        if (editComment == 1){
            binding.spinner7.clipChildren = false
            binding.contentsEdit.setText(valueMessage)
        }

        binding.frameLayout11.setOnClickListener {
            hideKeyboard()
        }

        imgAdpater = HorizontalImgAdapter(imgList, "upload")

        val spinnerItems = arrayListOf("자유수다","모공", "블랙헤드", "색소", "주름", "트러블", "민감", "다크서클")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems)

        binding.imageView115.setOnClickListener {
            permission()
        }

        binding.spinner7.apply {
            adapter = spinnerAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                    when(position){
                        0 -> category = "FREE"
                        1 -> category = "PORE"
                        2 -> category = "BLACK_HEAD"
                        3 -> category = "COLORED"
                        4 -> category = "WRINKLE"
                        5 -> category = "TROUBLE"
                        6 -> category = "SENSITIVITY"
                        7 -> category = "DARK_CIRCLE"
                    }
                }
            }
        }



        binding.imgRecyclerView.apply {
            adapter = imgAdpater
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        imgAdpater!!.setItemClickListner(object : HorizontalImgAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onDelete(view: View, position: Int) {
                imgList.removeAt(position)
                bodylist.removeAt(position)
                imgAdpater!!.notifyDataSetChanged()
            }
        })

        /** 이미지 첨부 버튼 **/
        binding.attacgImgButton.setOnClickListener {
            permission()
        }

        /** 내용 작성 글씨수 카운트 **/
        binding.contentsEdit.addTextChangedListener {
            binding.contentsLengthText.text = binding.contentsEdit.text.toString().length.toString()
        }


        /** 글쓰기 버튼 **/
        binding.writeChatBtn.setOnClickListener {
            val contents = binding.contentsEdit.text.toString()
            val body = MultipartBody.Part.createFormData("contents", contents)
            val category2 = MultipartBody.Part.createFormData("category", category)
            postBoard(body,bodylist, category2)
        }

        /** 뒤로가기 **/
        binding.imageView73.setOnClickListener {
            finish()
            overridePendingTransition(0, R.xml.slide_right)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when (requestCode) {
                /** 갤러리 **/
                1001 -> {
                    binding.imageView115.isVisible = false
                    /** 여러장 **/
                    if(data?.clipData!=null) {
                        for (i in 0 until data.clipData!!.itemCount) {
                            when(data.clipData!!.getItemAt(i).uri.toString().contains("google")){
                                true -> {
                                    val file = File(absolutelyPath(data.clipData!!.getItemAt(i).uri))
                                    val requestFile =
                                        file.asRequestBody("image/jpg".toMediaTypeOrNull())
                                    val body = MultipartBody.Part.createFormData("files", file.name, requestFile)
                                    imgUri = data.clipData!!.getItemAt(i).uri
                                    imgList.add(imgUri.toString())
                                    imgAdpater!!.notifyDataSetChanged()
                                    bodylist.add(body)
                                }
                                false -> {
                                    if(data.clipData!!.getItemAt(i).uri.toString().contains("external")){
                                        val file = File(absolutelyPath(data.clipData!!.getItemAt(i).uri))
                                        val requestFile =
                                            file.asRequestBody("image/jpg".toMediaTypeOrNull())
                                        val body = MultipartBody.Part.createFormData("files", file.name, requestFile)
                                        imgUri = data.clipData!!.getItemAt(i).uri
                                        imgList.add(imgUri.toString())
                                        imgAdpater!!.notifyDataSetChanged()
                                        bodylist.add(body)
                                    } else {
                                        val file = File(getRealPathFromURI(data.clipData!!.getItemAt(i).uri).toString())
                                        val requestFile =
                                            file.asRequestBody("image/jpg".toMediaTypeOrNull())
                                        val body = MultipartBody.Part.createFormData("files", file.name, requestFile)
                                        imgUri = data.clipData!!.getItemAt(i).uri
                                        imgList.add(imgUri.toString())
                                        imgAdpater!!.notifyDataSetChanged()
                                        bodylist.add(body)
                                    }
                                }
                            }
                        }
                        /** 한장 **/
                    } else {
                        if(data!!.data!!.toString().contains("external")){
                            binding.imageView115.isVisible = false
                            val file = File(absolutelyPath(data.data!!))
                            val requestFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
                            val body = MultipartBody.Part.createFormData("files", file.name, requestFile)
                            imgUri = data.data
                            imgList.add(imgUri.toString())
                            imgAdpater!!.notifyDataSetChanged()
                            bodylist.add(body)
                        } else {
                            binding.imageView115.isVisible = false
                            val file = File(getRealPathFromURI(data.data!!).toString())
                            val requestFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
                            val body = MultipartBody.Part.createFormData("files", file.name, requestFile)
                            imgUri = data.data
                            imgList.add(imgUri.toString())
                            imgAdpater!!.notifyDataSetChanged()
                            bodylist.add(body)
                        }
                    }
                }
                /** 카메라 **/
                1002 -> {
                    binding.imageView115.isVisible = false
                    imgList.add(photoURI)
                    imgAdpater!!.notifyDataSetChanged()
                    val file = File(plzFile.toString())
                    val requestFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
                    val body = MultipartBody.Part.createFormData("files", file.name, requestFile)
                    bodylist.add(body)
                }
            }
        }
    }

    private fun postBoard(contents : MultipartBody.Part, files : ArrayList<MultipartBody.Part>, category : MultipartBody.Part){
        val dialog= ProgressDialog.progressDialog(this)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()
        RetrofitMansae.server.postBoard(contents = contents,files = files, category = category)
                .enqueue(object : Callback<auth>{
                    override fun onFailure(call: Call<auth>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<auth>, response: Response<auth>) {

                        when(response.body()?.responseCode){
                            "SUCCESS" -> {
                                Toast.makeText(this@AddChatActivity, "게시물 추가 완료!", Toast.LENGTH_SHORT).show()
                                setResult(Activity.RESULT_OK)
                                finish()
                            }
                            "FAIL" -> {
                                Toast.makeText(this@AddChatActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }

                            else -> {
                                Toast.makeText(this@AddChatActivity, "서버에러", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                        }
                        dialog.dismiss()
                    }
                })
    }

//    fun postChat(title : RequestBody, contents : RequestBody){
//        Retrofit_Mansae.server.postBoards(title = title, contents = contents)
//            .enqueue(object : Callback<auth>{
//                override fun onFailure(call: Call<auth>, t: Throwable) {
//
//                }
//
//                override fun onResponse(call: Call<auth>, response: Response<auth>) {
//                    when(response.body()?.responseCode){
//                        "SUCCESS" -> {
//                            setResult(Activity.RESULT_OK)
//                            finish()
//                        }
//                    }
//                }
//            })
//    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }


    private fun permission(){
        val permission = object : PermissionListener {
            override fun onPermissionGranted() {
                showDialog()
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
                Toast.makeText(this@AddChatActivity, "권한을 허락해주세요!", Toast.LENGTH_SHORT).show()
            }
        }

        TedPermission.with(this)
                .setPermissionListener(permission)
                .setRationaleMessage("앱을 사용하시려면 권한을 허용해주세요.")
                .setDeniedMessage("권한을 거부하셨습니다.앱을 사용하시려면 [앱 설정]-[권한] 항목에서 권한을 허용해주세요.")
                .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .check()
    }

    @SuppressLint("QueryPermissionsNeeded")
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
                            // Continue only if the File was successfully created
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
                    intent.type = "image/*"
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(intent, 1001)
                }.create()
        alertDialog.show()
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

    @SuppressLint("Recycle")
    private fun getRealPathFromURI(contentUri: Uri): String? {
        if (contentUri.path!!.startsWith("/storage")) {
            return contentUri.path
        }
        val id: String = DocumentsContract.getDocumentId(contentUri).split(":")[1]
        val columns = arrayOf(MediaStore.Files.FileColumns.DATA)
        val selection = MediaStore.Files.FileColumns._ID + " = " + id
        val cursor = contentResolver.query(MediaStore.Files.getContentUri("external"), columns, selection, null, null)
        try {
            val columnIndex = cursor!!.getColumnIndex(columns[0])
            if (cursor.moveToFirst()) {
                return cursor.getString(columnIndex)
            }
        } finally {
            cursor!!.close()
        }
        return null
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.contentsEdit.windowToken, 0)
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
}