package com.eopeter.fluttermapboxnavigation.factory

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import com.eopeter.fluttermapboxnavigation.R
import com.eopeter.fluttermapboxnavigation.databinding.NavigationActivityBinding
import com.eopeter.fluttermapboxnavigation.models.views.EmbeddedNavigationMapView
import com.eopeter.fluttermapboxnavigation.utilities.PluginUtilities
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory


class EmbeddedNavigationViewFactory(
    private val messenger: BinaryMessenger,
    private val activity: Activity
) : PlatformViewFactory(StandardMessageCodec.INSTANCE) {

    companion object {
        private val bindings = mutableMapOf<Int, NavigationActivityBinding>()
    }

    override fun create(context: Context, viewId: Int, args: Any?): PlatformView {
        val inflater = LayoutInflater.from(context)

        val binding = bindings[viewId] ?: run {
            val newBinding = NavigationActivityBinding.inflate(inflater)
            bindings[viewId] = newBinding
            newBinding
        }

        val accessToken =
            PluginUtilities.getResourceFromContext(context, "mapbox_access_token")

        val view = EmbeddedNavigationMapView(
            context,
            activity,
            binding,
            messenger,
            viewId,
            args,
            accessToken
        )

        view.initialize()
        return view
    }

    fun clearBinding(viewId: Int) {
        bindings.remove(viewId)
    }
}
