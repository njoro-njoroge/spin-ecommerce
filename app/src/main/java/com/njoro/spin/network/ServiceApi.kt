package com.njoro.spin.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.njoro.spin.employees.model.ApproveOrder
import com.njoro.spin.employees.model.ApproveResponse
import com.njoro.spin.employees.model.ClientOrderResponse
import com.njoro.spin.ui.auth.model.*
import com.njoro.spin.ui.cart.model.CartItemsModel
import com.njoro.spin.ui.cart.model.UserIdItems
import com.njoro.spin.ui.checkout.Model.CheckOut
import com.njoro.spin.ui.checkout.Model.CheckoutResponse
import com.njoro.spin.ui.checkout.Model.Counties
import com.njoro.spin.ui.orders.model.*
import com.njoro.spin.ui.productdetails.model.CartModel
import com.njoro.spin.ui.productdetails.model.CartResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


enum class OrdersApiFilter(val value: String){
    SHOW_PENDING("Pending approval"),
    SHOW_APPROVED("Approved"),
    SHOW_SHIPPING("Shipping"),
    SHOW_Delivered("Delivered"),
    SHOW_COMFRIMED_DELIVERY("Confirmed delivery")

}

private const val BASE_URL = "http://192.168.86.201/hustle_free/spin_knit/api_endpoint/"
//private const val BASE_URL="https://mars.udacity.com/"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface ServiceApi {

    @GET("products.php")
//    @GET("realestate")
    fun getProducts(): Deferred<List<Products>>

    @POST("client/register.php")
    fun register(@Body register: UserRegister): Call<RegisterResponse>

    @POST("select_user.php")
    fun login(@Body login: UserLogin): Call<LoginResponse>

    @POST("client/send_to_cart.php")
    fun addToCart(@Body addToCart: CartModel): Call<CartResponse>

    @POST("client/cart_items.php")
    fun getCartItems(@Body userIdItems: UserIdItems): Deferred<List<CartItemsModel>>

    @GET("county.php")
    fun getCounties(): Deferred<List<Counties>>

    @POST("client/checkout.php")
    fun checkout(@Body checkout: CheckOut): Call<CheckoutResponse>

    @POST("client/orders.php")
    fun orders(@Body userOrders: UserOrders): Call<OrderResponse>

    @POST("order_items.php")
    fun orderItems(@Body orderItemsById: OrderItemsById): Call<OrderItemsResponse>

    //=======ORDERS ===========
    @GET("staff/client_orders.php")
    fun getClientOrdes(@Query("filter") type: String): Call<ClientOrderResponse>

    @POST("staff/approve_order.php")
    fun approveOrders(@Body approveOrder: ApproveOrder): Call<ApproveResponse>
}

object SpinApi {
    val retrofitService: ServiceApi by lazy {
        retrofit.create(ServiceApi::class.java)
    }
}