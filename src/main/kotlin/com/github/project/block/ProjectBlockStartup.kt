package com.github.project.block

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
        dependencyPlugin.triggerRefresh(project)
        project.messageBus.connect().subscribe(ProjectTopics.MODULES, object : ModuleListener {
            override fun moduleAdded(project: Project, module: Module) {
                dependencyPlugin.triggerRefresh(project)
            }

            override fun beforeModuleRemoved(project: Project, module: Module) {
                dependencyPlugin.triggerRefresh(project)
            }

            override fun moduleRemoved(project: Project, module: Module) {
                dependencyPlugin.triggerRefresh(project)
            }

            override fun modulesRenamed(
                project: Project,
                modules: MutableList<Module>,
                oldNameProvider: Function<Module, String>
            ) {
                dependencyPlugin.triggerRefresh(project)
            }
        })

        project.messageBus.connect().subscribe(ProjectTopics.PROJECT_ROOTS, object : ModuleRootListener {
            override fun beforeRootsChange(event: ModuleRootEvent) {
                dependencyPlugin.triggerRefresh(project)
            }

            override fun rootsChanged(event: ModuleRootEvent) {
                dependencyPlugin.triggerRefresh(project)
            }
        })
    }
}