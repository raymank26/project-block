package com.github.project

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity

val dependencyUpdater = DependencyUpdater()

class ProjectBlockStartup : StartupActivity {
    override fun runActivity(project: Project) {
//        project.messageBus.connect().subscribe(ProjectTopics.MODULES, object : ModuleListener {
//            override fun moduleAdded(project: Project, module: Module) {
//                updateInBackground(project)
//            }
//
//            override fun beforeModuleRemoved(project: Project, module: Module) {
//                updateInBackground(project)
//            }
//
//            override fun moduleRemoved(project: Project, module: Module) {
//                updateInBackground(project)
//            }
//
//            override fun modulesRenamed(
//                project: Project,
//                modules: MutableList<Module>,
//                oldNameProvider: Function<Module, String>
//            ) {
//                updateInBackground(project)
//            }
//        })
//
//        project.messageBus.connect().subscribe(ProjectTopics.PROJECT_ROOTS, object : ModuleRootListener {
//            override fun beforeRootsChange(event: ModuleRootEvent) {
//                updateInBackground(project)
//            }
//
//            override fun rootsChanged(event: ModuleRootEvent) {
//                updateInBackground(project)
//            }
//        })
    }
}