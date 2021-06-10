package com.github.project

interface ProjectBlock {
    fun getModules(): List<ModuleBlock>
}

interface DependencyBlock {
    fun getName(): String
    fun getType(): DependencyType
}

interface ModuleBlock : DependencyBlock {
    fun getDependencies(): List<DependencyBlock>
    fun updateModel(updater: (mutable: MutableModuleBlock) -> Boolean)
}

interface MutableModuleBlock {
    fun addModule(block: ModuleBlock)
    fun removeDependency(block: DependencyBlock)
}

enum class DependencyType {
    LIBRARY,
    MODULE,
    UNKNOWN
}

