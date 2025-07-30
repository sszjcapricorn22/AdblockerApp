package sszj.s.adblockerapp.models

import com.google.gson.annotations.SerializedName

data class WebsiteWrapper( @SerializedName("Websites")
                           val websites: List<WebItem>
)
