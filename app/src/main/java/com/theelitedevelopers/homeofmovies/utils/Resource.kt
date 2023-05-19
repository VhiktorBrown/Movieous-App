package com.theelitedevelopers.homeofmovies.utils

/**
 * @created 17/5/2023 - 6:53 PM
 * @project Movies app
 * @author Victor
 */

sealed class Resource<T>(val data : T? = null, val message : String? = null){
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message : String?, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)

}
