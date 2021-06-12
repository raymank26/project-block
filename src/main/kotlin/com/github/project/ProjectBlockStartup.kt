package com.github.project

import com.intellij.ProjectTopics
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.ModuleListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModuleRootEvent
import com.intellij.openapi.roots.ModuleRootListener
import com.intellij.openapi.startup.StartupActivity
import com.intellij.util.Function

class ProjectBlockStartup : StartupActivity {
    override fun runActivity(project: Project) {
        dependencyPlugin.startRefreshing(project)
        project.messageBus.connect().subscribe(ProjectTopics.MODULES, object : ModuleListener {
            override fun moduleAdded(project: Project, module: Module) {
                dependencyPlugin.startRefreshing(project)
            }

            override fun beforeModuleRemoved(project: Project, module: Module) {
                dependencyPlugin.startRefreshing(project)
            }

            override fun moduleRemoved(project: Project, module: Module) {
                dependencyPlugin.startRefreshing(project)
            }

            override fun modulesRenamed(
                project: Project,
                modules: MutableList<Module>,
                oldNameProvider: Function<Module, String>
            ) {
                dependencyPlugin.startRefreshing(project)
            }
        })

        project.messageBus.connect().subscribe(ProjectTopics.PROJECT_ROOTS, object : ModuleRootListener {
            override fun beforeRootsChange(event: ModuleRootEvent) {
                dependencyPlugin.startRefreshing(project)
            }

            override fun rootsChanged(event: ModuleRootEvent) {
                dependencyPlugin.startRefreshing(project)
            }
        })
    }
}