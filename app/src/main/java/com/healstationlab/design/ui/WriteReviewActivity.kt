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
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.healstationlab.design.R
import com.healstationlab.design.adapter.HorizontalImgAdapter
import com.healstationlab.design.databinding.ActivityWriteReviewBinding
import com.healstationlab.design.dto.auth
import com.healstationlab.design.resource.ProgressDialog
import com.healstationlab.design.resource.RetrofitMansae
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class WriteReviewActivity : AppCompatActivity() {

    lateinit var binding : ActivityWriteReviewBinding
    val imgList : ArrayList<Any> = arrayListOf()
    var imgAdpater : HorizontalImgAdapter? = null
    private var imgUri : Uri? = null
//    var bitmap : Bitmap? = null
    val bodyList : ArrayList<MultipartBody.Part> = arrayListOf()
//    var imgCheck = true
    private lateinit var photoURI : Uri

    private lateinit var currentPhotoPath: String
    private lateinit var plzFile : File

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityWriteReviewBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            hideKeyboard()
        }

        binding.imageView116.setOnClickListener {
            permission()
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.contentsEdit2.addTextChangedListener {
            binding.textView204.text = binding.contentsEdit2.text.toString().length.toString()
        }

        imgAdpater = HorizontalImgAdapter(imgList, "upload")
        binding.imgRecyclerview.apply {
            adapter = imgAdpater
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        imgAdpater!!.setItemClickListner(object : HorizontalImgAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int) {

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onDelete(view: View, position: Int) {
                imgList.removeAt(position)
                bodyList.removeAt(position)
                imgAdpater!!.notifyDataSetChanged()
            }
        })

        binding.attacgImgButton2.setOnClickListener {
            permission()
        }

        binding.writeChatBtn2.setOnClickListener {
            Toast.makeText(this, "결과 토스트", Toast.LENGTH_SHORT).show()
            imgList.clear()
            onBackPressed()
        }

        binding.writeChatBtn2.setOnClickListener {
            binding.contentsEdit2.text.toString()
            val ratring2 = DecimalFormat("#.#").format(binding.ratingBar2.rating)
            val contents = MultipartBody.Part.createFormData("content", binding.contentsEdit2.text.toString())
            val rating = MultipartBody.Part.createFormData("rating", ratring2.toString())
            writeReview(contents, rating)
        }

        getMapping()
    }


    fun showDialog() {
        val alertDialog = AlertDialog.Builder(this)
                .setTitle("선택")
                .setMessage("사진을 선택해주세요.")
                .setPositiveButton("카메라") { _: DialogInterface, _: Int ->
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
                .setNegativeButton("갤러리") { _: DialogInterface, _: Int ->
                    val intent = Intent()
                    intent.action = Intent.ACTION_GET_CONTENT
                    intent.type = "image/*"
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    startActivityForResult(intent, 1001)
                }.create()
        alertDialog.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                /** 갤러리 **/
                1001 -> {
                    binding.imageView116.isVisible = false
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
                                    bodyList.add(body)
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
                                        bodyList.add(body)
                                    } else {
                                        val file = File(getRealPathFromURI(data.clipData!!.getItemAt(i).uri).toString())
                                        val requestFile =
                                            file.asRequestBody("image/jpg".toMediaTypeOrNull())
                                        val body = MultipartBody.Part.createFormData("files", file.name, requestFile)
                                        imgUri = data.clipData!!.getItemAt(i).uri
                                        imgList.add(imgUri.toString())
                                        imgAdpater!!.notifyDataSetChanged()
                                        bodyList.add(body)
                                    }
                                }
                            }
                        }
                        /** 한장 **/
                    } else {
                        binding.imageView116.isVisible = false
                        val file = File(getRealPathFromURI(data!!.data!!).toString())
                        val requestFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
                        val body = MultipartBody.Part.createFormData("files", file.name, requestFile)
                        imgUri = data.data
                        imgList.add(imgUri.toString())
                        imgAdpater!!.notifyDataSetChanged()
                        bodyList.add(body)
                    }
                }
                /** 카메라 **/
                1002 -> {
                    binding.imageView116.isVisible = false
                    imgList.add(photoURI)
                    imgAdpater!!.notifyDataSetChanged()
                    val file = File(plzFile.toString())
                    val requestFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
                    val body = MultipartBody.Part.createFormData("files", file.name, requestFile)
                    bodyList.add(body)
                }
            }
        }
    }

    private fun writeReview(contents : MultipartBody.Part, rating : MultipartBody.Part) {
        val dialog= ProgressDialog.progressDialog(this)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()
        RetrofitMansae.server.writeReview(content = contents, rating = rating, files = bodyList, id = intent.getIntExtra("id", 0))
                .enqueue(object : Callback<auth>{
                    override fun onFailure(call: Call<auth>, t: Throwable) {
                        dialog.dismiss()
                    }

                    override fun onResponse(call: Call<auth>, response: Response<auth>) {
                        when(response.body()?.responseCode){
                            "SUCCESS" -> {
                                Toast.makeText(this@WriteReviewActivity, "리뷰를 등록했습니다.", Toast.LENGTH_SHORT).show()
                                finish()
                            }

                            "FAIL" -> {
                                Toast.makeText(this@WriteReviewActivity,
                                    response.body()!!.message, Toast.LENGTH_SHORT).show()
                            }

                            else -> {
                                dialog.dismiss()
                                Toast.makeText(this@WriteReviewActivity, "서버에러", Toast.LENGTH_SHORT).show()
                            }
                        }
                        dialog.dismiss()
                    }
                })
    }

//    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
//        val bytes = ByteArrayOutputStream()
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//        val path = MediaStore.Images.Media.insertImage(
//                inContext.contentResolver, inImage, "IMG_" + System.currentTimeMillis(), null
//        )
//        return Uri.parse(path)
//    }

    @SuppressLint("SetTextI18n")
    private fun getMapping(){
        Glide.with(this).load(intent.getStringExtra("img")).into(binding.imageView57)
        binding.textView200.text = intent.getStringExtra("name")
        binding.textView201.text = sliceAmountNumber(intent.getStringExtra("price")!!.toLong())
        binding.textView199.text = "[${intent.getStringExtra("brand")}]"
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
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

    fun sliceAmountNumber(number : Long) : String {
        val decimalFormat = DecimalFormat("###,###")
        return decimalFormat.format(number)
    }

    private fun permission(){
        val permission = object : PermissionListener {
            override fun onPermissionGranted() {
                showDialog()
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
                Toast.makeText(this@WriteReviewActivity, "권한을 허락해주세요!", Toast.LENGTH_SHORT).show()
            }
        }

        TedPermission.with(this)
                .setPermissionListener(permission)
                .setRationaleMessage("앱을 사용하시려면 권한을 허용해주세요.")
                .setDeniedMessage("권한을 거부하셨습니다.앱을 사용하시려면 [앱 설정]-[권한] 항목에서 권한을 허용해주세요.")
                .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .check()
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.contentsEdit2.windowToken, 0)
    }
}