package com.github.project

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
        for (name in listOf(dependencyBlock.getName(), dependencyBlock.getName().replace(".", "-"))) {
            val module = moduleNameToModule[name]
            if (module != null) {
                return module
            }
        }
        return null
    }
}