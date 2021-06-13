package com.github.project

import com.intellij.openapi.project.Project

interface PluginActions {
    fun triggerRefresh(project: Project)
    fun changeEnabled(enabled: Boolean, project: Project)
    fun isChangeEnabled(project: Project): Boolean
}