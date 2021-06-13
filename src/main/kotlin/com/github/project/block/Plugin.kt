package com.github.project.block

import com.intellij.openapi.project.Project

interface Plugin {
    fun triggerRefresh(project: Project, force: Boolean)
    fun triggerRefresh(project: Project) {
        triggerRefresh(project, false)
    }

    fun changeEnabled(enabled: Boolean, project: Project)
    fun isChangeEnabled(project: Project): Boolean
}