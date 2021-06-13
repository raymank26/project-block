package com.github.project.block

import com.github.project.block.dep.DependencyBlock
import com.github.project.block.dep.DependencyType
import com.github.project.block.dep.ModuleBlock
import com.github.project.block.dep.ProjectBlock

class DependencyUpdater {

    fun updateDependencies(projectBlock: ProjectBlock) {
        val moduleNameToModule = projectBlock.getModules().map { it.getName() to it }.toMap()
        for (module in projectBlock.getModules()) {
            module.updateModel { mutableModel ->
                var changed = false
                for (dependency in module.getDependencies()) {
                    if (dependency.getType() != DependencyType.LIBRARY) {
                        continue
                    }
                    val replacementModule: ModuleBlock? = match(dependency, moduleNameToModule)
                    if (replacementModule != null) {
                        mutableModel.removeDependency(dependency)
                        mutableModel.addModule(replacementModule)
                        changed = true
                    }
                }
                changed
            }
        }
    }

    private fun match(dependencyBlock: DependencyBlock, moduleNameToModule: Map<String, ModuleBlock>): ModuleBlock? {
        var name = dependencyBlock.getName()
        if (name.startsWith("Gradle") || name.startsWith("Maven")) {
            name = name.split(":")[2]
        }
        return moduleNameToModule[name]
    }
}