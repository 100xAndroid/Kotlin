package cz.stokratandroid.widget1

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class WidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        val count = appWidgetIds.size
        for (i in 0 until count) {
            val widgetId = appWidgetIds[i]
            val remoteViews = RemoteViews(context.packageName, R.layout.widget)
            remoteViews.setTextViewText(R.id.textView, "Widget test")

            appWidgetManager.updateAppWidget(widgetId, remoteViews)
        }
    }
}