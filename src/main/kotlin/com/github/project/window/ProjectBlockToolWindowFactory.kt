package com.github.project.window

import com.github.project.IntellijProjectBlock
import com.github.project.dependencyUpdater
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class ProjectBlockToolWindowFactory : ToolWindowFactory, DumbAware {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val panel = ProjectBlockPanel(toolWindow, object : ProjectToolbarActions {
            override fun startRefreshing() {
                updateInBackground(project)
            }
        })
        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(panel.getContent(), "", false)

        toolWindow.contentManager.addContent(content)
    }

    private fun updateInBackground(project: Project) {
        ProgressManager.getInstance().run(object : Task.Backgroundable(project, "Changing module dependencies") {
            override fun run(indicator: ProgressIndicator) {
                dependencyUpdater.updateDependencies(IntellijProjectBlock(project))
            }
        })
    }

}