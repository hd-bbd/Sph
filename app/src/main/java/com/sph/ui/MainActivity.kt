package com.sph.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.sph.R
import com.sph.base.*
import com.sph.model.BaseModel

@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity(),
    IBaseModelListener<BaseModel, ArrayList<BaseCustomViewModel>> {
    lateinit var model: MainModel
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    private lateinit var loadService: LoadService<*>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.main_recycler_view)
        progressBar = findViewById(R.id.main_progress_bar)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(SpacesItemDecoration(resources.getDimensionPixelSize(R.dimen.px_20)))
        loadService = LoadSir.getDefault().register(this) {
            model.load()
        }
        model = MainModel()
        model.register(this)
        model.getCachedDataAndLoad()
        loadService.showSuccess()
    }

    override fun onDestroy() {
        super.onDestroy()
        model.cancel()
        model.unRegister(this)
    }

    override fun onLoadFinish(
        model: MvvmBaseModel<BaseModel, ArrayList<BaseCustomViewModel>>,
        data: ArrayList<BaseCustomViewModel>,
        vararg result: PageResult
    ) {
        loadService.showSuccess()
        progressBar.visibility = View.GONE
        recyclerView.adapter = ItemListAdapter(data)
    }

    override fun onLoadFailure(
        model: MvvmBaseModel<BaseModel, ArrayList<BaseCustomViewModel>>,
        e: String,
        vararg result: PageResult
    ) {
        loadService.showCallback(ErrorNetworkCallback::class.java)
    }

}