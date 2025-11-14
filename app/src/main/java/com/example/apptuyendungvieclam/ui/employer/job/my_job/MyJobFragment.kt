package com.example.apptuyendungvieclam.ui.employer.job.my_job

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apptuyendungvieclam.R
import com.example.apptuyendungvieclam.data.model.User
import com.example.apptuyendungvieclam.data.model.job.Job
import com.example.apptuyendungvieclam.databinding.FragmentMyJobBinding
import com.example.apptuyendungvieclam.ui.base.fragment.BaseMvvmFragment
import com.example.apptuyendungvieclam.ui.base.viewmodel.BaseViewModel
import com.example.apptuyendungvieclam.ui.employer.create_job.create_description.AddJobFragment
import com.example.apptuyendungvieclam.ui.employer.job.job_information.JobInformationFragment

class MyJobFragment(private var user: User?) : BaseMvvmFragment<MyJobCallBack, MyJobViewModel>() , MyJobCallBack, JobAdapter.IJob{
    private var searchString : String = ""
    override fun setEvents() {

    }
    override fun initComponents() {
         getBindingData().myJobViewModel = mModel
         mModel.uiEventLiveData.observe(this){
             when(it){
                 BaseViewModel.FINISH_ACTIVITY -> finishActivity()
                 MyJobViewModel.GO_ADD_JOB -> goToAddJob()
                 MyJobViewModel.SET_POWER_SUCCESS -> setStatusSuccess()
                 MyJobViewModel.DELETE_SUCCESS -> deleteJobSuccess()
             }
         }
        // Điểm thực hiện thao tác READ ban đầu
        mModel.getJobDataFromDB(requireContext(),user!!,"")
        if(mModel.getListJob().size == 0){
             getBindingData().tvThongBaoNull.visibility = View.VISIBLE
        }
        initRecyclerViewoJob()
        // ... (xử lý UI, init RecyclerView)
        onSearch()
    }
    private fun initRecyclerViewoJob(){
        val jobAdapter = JobAdapter(this, requireContext(),user!!)
        getBindingData().rcvListMyJob.layoutManager = LinearLayoutManager(context)
        getBindingData().rcvListMyJob.adapter = jobAdapter
    }
    // Hàm Chuyển sang Đăng Công việc
    private fun goToAddJob(){
        // Chuyển sang Fragment thêm Job (CREATE)
        // Cơ chế: FRAGMENT TRANSACTION
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        // Thay thế Fragment
        fragmentTransaction.replace(R.id.fragmentMain, AddJobFragment(user))
        // Thêm vào Back Stack
        fragmentTransaction.addToBackStack(AddJobFragment.TAG)
        fragmentTransaction.commit()
    }
    // HÀM SEARCH
    private fun onSearch(){
        getBindingData().searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                searchString = newText
                // Điểm thực hiện thao tác READ (Tìm kiếm)
                mModel.getJobDataFromDB(requireContext(),user!!,newText)
                getBindingData().rcvListMyJob.adapter!!.notifyDataSetChanged();
                return false
            }
        })
    }

    override fun getBindingData() = mBinding as FragmentMyJobBinding

    override fun getViewModel(): Class<MyJobViewModel> {
        return MyJobViewModel::class.java
    }
    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
    }

    override fun getLayoutMain(): Int {
        return R.layout.fragment_my_job
    }

    override fun count(): Int {
        return mModel.getListJob().size
    }

    override fun getJob(position: Int): Job {
        return mModel.getListJob()[position]
    }

    override fun onClickJob(position: Int) {
        // Lấy dữ liệu Job
        val job = mModel.getListJob()[position]
        // Khởi tạo Fragment đích
        val jobInformationFragment = JobInformationFragment(user!!)
        // 1. Đóng gói dữ liệu Job vào Bundle
        val bundle = Bundle()
        bundle.putSerializable("job",job)
        jobInformationFragment.arguments = bundle
        // 2. Thực hiện Fragment Transaction
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentMain, jobInformationFragment)
        fragmentTransaction.addToBackStack(JobInformationFragment.TAG)
        fragmentTransaction.commit()
    }

    override fun onClickPower(position: Int) {
        val job = mModel.getListJob()[position]
        // Điểm thực hiện thao tác UPDATE (trạng thái)
        mModel.setStatus(job,requireContext())
    }

    override fun onClickDelete(position: Int) {
        val job = mModel.getListJob()[position]
        val alertDialog = AlertDialog.Builder(context)
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc chắn muốn xóa ?")
            .setPositiveButton(
                "Có"
            ) { _: DialogInterface?, _: Int ->
                // Điểm thực hiện thao tác DELETE
                mModel.deleteJob(job, requireContext())
            }
            .setNegativeButton(
                "Không"
            ) { _: DialogInterface?, _: Int -> }
            .create()
        alertDialog.show()
    }

    private fun setStatusSuccess(){
        // Sau khi UPDATE thành công, READ lại dữ liệu
        mModel.getJobDataFromDB(requireContext(),user!!,searchString!!)
        getBindingData().rcvListMyJob.adapter!!.notifyDataSetChanged()
    }
    private fun deleteJobSuccess(){
        Toast.makeText(context,"Xóa thành công",Toast.LENGTH_SHORT).show()
        // Sau khi DELETE thành công, READ lại dữ liệu
        mModel.getJobDataFromDB(requireContext(),user!!,"")
        getBindingData().rcvListMyJob.adapter!!.notifyDataSetChanged()
    }

    companion object{
        val TAG = MyJobFragment::class.java.name
    }
}