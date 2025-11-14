package com.example.apptuyendungvieclam.ui.account

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.apptuyendungvieclam.R
import com.example.apptuyendungvieclam.data.model.User
import com.example.apptuyendungvieclam.databinding.FragmentProfileBinding

import com.example.apptuyendungvieclam.ui.account.information.InformationFragment
import com.example.apptuyendungvieclam.ui.account.update_skill.UpdateSkillFragment
import com.example.apptuyendungvieclam.ui.base.fragment.BaseMvvmFragment
import com.example.apptuyendungvieclam.ui.base.viewmodel.BaseViewModel
import com.example.apptuyendungvieclam.ui.employer.company.update_company.UpdateCompanyFragment
import com.example.apptuyendungvieclam.ui.login.FirstActivity
import com.example.apptuyendungvieclam.ui.payer.PayerActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import java.io.ByteArrayOutputStream
import java.io.IOException


class ProfileFragment(var user : User?) : BaseMvvmFragment<ProfileCallBack,ProfileViewModel>(),ProfileCallBack {

    private var mUri: Uri? = null
    private var strAvatar: String? = null

    override fun setEvents() {

    }

    override fun initComponents() {
        getBindingData().profileViewModel = mModel
        getBindingData().lifecycleOwner = viewLifecycleOwner
        mModel.uiEventLiveData.observe(this) {
            when (it) {
                BaseViewModel.FINISH_ACTIVITY -> finishActivity()
                ProfileViewModel.LOG_OUT -> onClickLogOut()
                ProfileViewModel.GO_UPDATE_SKILL -> goToUpdateSkill()
                ProfileViewModel.SHOW_DIALOG -> showDiaLog()
                ProfileViewModel.ON_CLICK_AVT -> onClickRequestPermission()
                ProfileViewModel.ON_CLICK_SET_AVT -> updateAvatar(strAvatar!!)
                ProfileViewModel.ON_CLICK_COMPANY -> goToUpdateCompany()
                ProfileViewModel.ON_CLICK_INFORMATION -> goToInformation()
                ProfileViewModel.ON_CLICK_PAY -> goToPayer()
            }
        }
        setInformationUser()
        setUserAvatar()
    }

    private fun setInformationUser() {
        getBindingData().tvUserEmail.text = user!!.email
        getBindingData().tvUserName.text = user!!.name
    }



    override fun getBindingData() = mBinding as FragmentProfileBinding

    override fun getLayoutMain(): Int {
        return R.layout.fragment_profile
    }

    override fun getViewModel(): Class<ProfileViewModel> {
        return ProfileViewModel::class.java
    }

    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
    }

    private fun goToUpdateSkill(){
        if(user!!.permission == 0){
            // Cơ chế: FRAGMENT TRANSACTION (Chuyển trang)
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            // Trang đích: Cập nhật Kỹ năng
            fragmentTransaction.replace(R.id.fragmentMain, UpdateSkillFragment(user))
            fragmentTransaction.addToBackStack(UpdateSkillFragment.TAG)
            fragmentTransaction.commit()
        }else if(user!!.permission == 1){
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentMain2, UpdateSkillFragment(user))
            fragmentTransaction.addToBackStack(UpdateSkillFragment.TAG)
            fragmentTransaction.commit()
        }
    }

    private fun goToUpdateCompany(){
        if(user!!.permission == 0){   // CHỈ DÀNH CHO NHÀ TUYỂN DỤNG
            // Cơ chế: FRAGMENT TRANSACTION (Chuyển trang)
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentMain, UpdateCompanyFragment(user!!))
            fragmentTransaction.addToBackStack(UpdateCompanyFragment.TAG)
            fragmentTransaction.commit()
        }else if(user!!.permission == 1){
            Toast.makeText(context,"Bạn không sử dụng được tính năng này", Toast.LENGTH_SHORT).show()
        }  // ... else if(user!!.permission == 1) hiển thị Toast
    }

    private fun goToInformation(){
        if(user!!.permission == 0) {
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentMain, InformationFragment(user!!))
            fragmentTransaction.addToBackStack(InformationFragment.TAG)
            fragmentTransaction.commit()
        }else if(user!!.permission == 1){
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentMain2, InformationFragment(user!!))
            fragmentTransaction.addToBackStack(InformationFragment.TAG)
            fragmentTransaction.commit()
        }
    }

    private fun goToPayer(){
        // Cơ chế: INTENT (Chuyển Activity)
        val intent = Intent(context,PayerActivity::class.java)
        // Chi tiết: Truyền ID người dùng
        intent.putExtra("userId",user!!.idAccount)
        startActivity(intent)
    }

    private fun onClickLogOut(){
        val alertDialog = AlertDialog.Builder(context)
            .setTitle("Xác nhận đăng xuất")
            .setMessage("Bạn có chắc chắn muốn đăng xuất ?")
            .setPositiveButton(
                "Có"
            ) { dialog: DialogInterface?, which: Int ->
                mModel.onLogOut()
                // Cơ chế: INTENT (Chuyển Activity)
                val intent = Intent(context, FirstActivity::class.java)
                startActivity(intent)
            }
            .setNegativeButton(
                "Không"
            ) { dialog: DialogInterface?, which: Int -> }
            .create()
        alertDialog.show()
    }
    private fun showDiaLog(){
//        val rateUsDialog = RateUsDialog(requireContext())
//        rateUsDialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.transparent)))
//        rateUsDialog.setCancelable(false)
//        rateUsDialog.show()
        val view = View.inflate(context,R.layout.rate_us_dialog_layout,null)
        val builder = AlertDialog.Builder(context)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)
        dialog.setCancelable(false)
        val btnLater = view.findViewById<Button>(R.id.btnLater)
        val btnRateNow = view.findViewById<Button>(R.id.btnRateNow)
        btnLater.setOnClickListener {
            dialog.dismiss()
        }
        btnRateNow.setOnClickListener {
            Toast.makeText(context,"Thành công",Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
    }

    private fun setUserAvatar() {
        val user: FirebaseUser = FirebaseAuth.getInstance().currentUser ?: return
        Glide.with(this).load(user.photoUrl).error(R.drawable.avatardefult1)
            .into(getBindingData().imAvatar)
    }

    private fun onClickRequestPermission() {
        getBindingData().btnSetAvt.visibility = View.VISIBLE
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery()
            return
        }
        if (activity?.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery()
        } else {
            val permisstions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            this.requestPermissions(permisstions, MY_REQUEST_CODE)
        }
    }

    private fun setBitmapImageView(bitmapImageView: Bitmap?) {
        Glide.with(getBindingData().imAvatar).load(bitmapImageView).into(getBindingData().imAvatar)
    }

    private fun setmUri(mUri: Uri?) {
        this.mUri = mUri
    }

    private fun updateAvatar(strAvatar : String) {
        mModel.saveAvatarToDB(user!!,strAvatar,requireContext())
        val user: FirebaseUser = FirebaseAuth.getInstance().currentUser ?: return
        val profileUpdates: UserProfileChangeRequest = UserProfileChangeRequest.Builder()
            .setPhotoUri(mUri)
            .build()
        user.updateProfile(profileUpdates)
            .addOnCompleteListener { task: Task<Void?> ->
                getBindingData().btnSetAvt.visibility = View.INVISIBLE
                if (task.isSuccessful) {
                    Toast.makeText(activity, "Sucess", Toast.LENGTH_SHORT).show()
                    setUserAvatar()
                }
            }
    }


    private val intentActivityResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode === Activity.RESULT_OK) {
                val intent: Intent = result.data ?: return@registerForActivityResult
                val uri: Uri = intent.data!!
                setmUri(uri)
                try {
                    val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, uri)
                    setBitmapImageView(bitmap)

                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream)
                    val bytes = stream.toByteArray()
                    strAvatar = Base64.encodeToString(bytes, Base64.DEFAULT)

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                Toast.makeText(context, "Please give access", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intentActivityResultLauncher.launch(Intent.createChooser(intent, "Pick image"))
    }

    companion object {
        const val MY_REQUEST_CODE = 10
    }
}