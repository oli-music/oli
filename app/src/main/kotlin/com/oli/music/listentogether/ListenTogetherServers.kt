

package com.oli.music.listentogether

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class ListenTogetherServer(
    val name: String,
    val url: String,
    val location: String,
    val operator: String
)

object ListenTogetherServers {
    // Oli rebrand: no default server is configured. The upstream project's servers
    // (iad1tya's Hugging Face space, and a further third-party fallback) have been
    // removed rather than silently kept, since routing your users' listen-together
    // sessions through someone else's infrastructure without disclosure isn't okay.
    // Point SERVER_JSON_URL at your own hosted app/server.json once you deploy a
    // compatible backend (see Echo-Music-Server, GPL-3.0, as a starting point).
    private const val SERVER_JSON_URL = "https://raw.githubusercontent.com/oli-music/oli/refs/heads/main/app/server.json"

    private val _servers = MutableStateFlow<List<ListenTogetherServer>>(emptyList())

    val serversFlow: StateFlow<List<ListenTogetherServer>> = _servers

    val servers: List<ListenTogetherServer>
        get() = _servers.value

    init {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val client = okhttp3.OkHttpClient()
                val request = okhttp3.Request.Builder().url(SERVER_JSON_URL).build()
                val response = client.newCall(request).execute()
                response.body?.string()?.let { jsonString ->
                    val jsonObject = Json.parseToJsonElement(jsonString).jsonObject
                    val url = jsonObject["serverUrl"]?.jsonPrimitive?.content ?: ""
                    if (url.isNotBlank()) {
                        val name = jsonObject["name"]?.jsonPrimitive?.content ?: "Oli Server"
                        val region = jsonObject["region"]?.jsonPrimitive?.content ?: "Not configured"
                        _servers.value = listOf(
                            ListenTogetherServer(
                                name = name,
                                url = url,
                                location = region,
                                operator = "OLI"
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                // No server configured — listen-together stays disabled until one is set up.
            }
        }
    }

    val defaultServerUrl: String
        get() = servers.firstOrNull()?.url ?: ""

    fun findByUrl(url: String): ListenTogetherServer? = servers.firstOrNull { it.url == url }
}
