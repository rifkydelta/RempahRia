import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rempahpedia.ClassListResponse
import com.example.rempahpedia.Spices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListViewModel : ViewModel() {

    private val _spices = MutableLiveData<List<Spices>>()
    val spices: LiveData<List<Spices>> get() = _spices

    fun loadSpices() {
        RetrofitClient.apiService.getClassList().enqueue(object : Callback<ClassListResponse> {
            override fun onResponse(
                call: Call<ClassListResponse>,
                response: Response<ClassListResponse>
            ) {
                if (response.isSuccessful) {
                    val classListResponse = response.body()
                    val items = classListResponse?.classList?.values?.toList() ?: emptyList()
                    _spices.postValue(items)
                } else {
                    // Handle unsuccessful response
                }
            }

            override fun onFailure(call: Call<ClassListResponse>, t: Throwable) {
                // Handle error
            }
        })
    }
}
