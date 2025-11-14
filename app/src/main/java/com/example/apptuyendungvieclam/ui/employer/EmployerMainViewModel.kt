package com.example.apptuyendungvieclam.ui.employer

import com.example.apptuyendungvieclam.data.local.AppDatabase
import com.example.apptuyendungvieclam.data.remote.InteractCommon
import com.example.apptuyendungvieclam.ui.base.viewmodel.BaseViewModel
import java.util.concurrent.Executor
import javax.inject.Inject

class EmployerMainViewModel @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor
) : BaseViewModel<EmployerMainCallBack>(appDatabase, interactCommon, scheduler) {

}