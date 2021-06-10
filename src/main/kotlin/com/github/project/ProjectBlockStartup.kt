package com.github.project

import com.intellij.ProjectTopics
import com.intellij.openapi.module.Module
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.ModuleListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModuleRootEvent
import com.intellij.openapi.roots.ModuleRootListener
import com.intellij.openapi.startup.StartupActivity
import com.intellij.util.Function

class ProjectBlockStartup : StartupActivity {
    private val dependencyUpdater = DependencyUpdater()
    override fun runActivity(project: Project) {
        updateInBackground(project)
        project.messageBus.connect().subscribe(ProjectTopics.MODULES, object : ModuleListener {
            override fun moduleAdded(project: Project, module: Module) {
                updateInBackground(project)
            }

            override fun beforeModuleRemoved(project: Project, module: Module) {
                updateInBackground(project)
            }

            override fun moduleRemoved(project: Project, module: Module) {
                updateInBackground(project)
            }

            override fun modulesRenamed(
                project: Project,
                modules: MutableList<Module>,
                oldNameProvider: Function<Module, String>
            ) {
                updateInBackground(project)
            }
        })

        project.messageBus.connect().subscribe(ProjectTopics.PROJECT_ROOTS, object : ModuleRootListener {
            override fun beforeRootsChange(event: ModuleRootEvent) {
                updateInBackground(project)
            }

            override fun rootsChanged(event: ModuleRootEvent) {
                updateInBackground(project)
            }
        })
    }

    private fun updateInBackground(project: Project) {
        ProgressManager.getInstance().run(object : Task.Backgroundable(project, "Changing module dependencies") {
            override fun run(indicator: ProgressIndicator) {
                dependencyUpdater.updateDependencies(IntellijProjectBlock(project))
            }
        })
    }
}