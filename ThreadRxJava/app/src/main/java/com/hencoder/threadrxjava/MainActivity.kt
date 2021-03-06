package com.hencoder.threadrxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.hencoder.threadrxjava.model.Repo
import com.hencoder.threadrxjava.network.Api
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
  private var disposable: Disposable? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val retrofit = Retrofit.Builder()
      .baseUrl("https://api.github.com/")
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
      .build()

    val api = retrofit.create(Api::class.java)

    api.getRepos("lizhaoxiong")
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(object : SingleObserver<MutableList<Repo>> {
        override fun onSuccess(t: MutableList<Repo>?) {
          textView.text = "Result:${t!![0].name}"
        }

        override fun onSubscribe(d: Disposable?) {
          disposable = d
          textView.text = "正在请求"
        }

        override fun onError(e: Throwable) {
          textView.text = e.message ?: e.javaClass.name
        }

      })

//    Single.just(1)
//      .subscribeOn(Schedulers.io())
//      .observeOn(AndroidSchedulers.mainThread())
//
//    Observable.interval(0, 1, TimeUnit.SECONDS)
//      .observeOn(AndroidSchedulers.mainThread())
//      .subscribe(object : Observer<Long?> {
//        override fun onComplete() {
//        }
//
//        override fun onSubscribe(d: Disposable?) {
//        }
//
//        override fun onNext(t: Long?) {
//          textView.text = t.toString()
//        }
//
//        override fun onError(e: Throwable?) {
//        }
//      })
//  }

  }

  override fun onDestroy() {
    disposable?.dispose()
    super.onDestroy()
  }
}