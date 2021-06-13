package com.github.project.block.dep

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
    fun addModule(block: ModuleBlock, index: Int)
    fun removeDependency(block: DependencyBlock)
}

enum class DependencyType {
    LIBRARY,
    MODULE,
    UNKNOWN
}

