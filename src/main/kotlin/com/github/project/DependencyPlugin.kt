package com.github.project

import com.github.project.block.IntellijProjectBlock
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.application.Application
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project

val dependencyPlugin = DependencyPlugin(ApplicationManager.getApplication())

private const val PROJ_BLOCK_REFRESH_PROP_KEY = "proj-block-refresh"


class DependencyPlugin(
    private val application: Application
) : PluginActions {

    private val dependencyUpdater = DependencyUpdater()

    override fun changeEnabled(enabled: Boolean, project: Project) {
        val prop = PropertiesComponent.getInstance(project)
        prop.setValue(PROJ_BLOCK_REFRESH_PROP_KEY, enabled, true)
    }

    override fun triggerRefresh(project: Project, force: Boolean) {
        if (force || isChangeEnabled(project)) {
            application.invokeLater {
                dependencyUpdater.updateDependencies(IntellijProjectBlock(project))
            }
        }
    }

    override fun isChangeEnabled(project: Project): Boolean {
        val prop = PropertiesComponent.getInstance(project)
        return !prop.isValueSet(PROJ_BLOCK_REFRESH_PROP_KEY)
    }
}
