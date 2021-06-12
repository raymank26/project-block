package com.github.project.window

import com.github.project.dependencyPlugin
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class ProjectBlockToolWindowFactory : ToolWindowFactory, DumbAware {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val panel = ProjectBlockPanel(project, dependencyPlugin)
        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(panel.getContent(), "", false)

        toolWindow.contentManager.addContent(content)
    }
}