package com.github.project

import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.LibraryOrderEntry
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.roots.ModuleOrderEntry
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.roots.ModuleRootModificationUtil
import com.intellij.openapi.roots.OrderEntry

class IntellijProjectBlock(project: Project) : ProjectBlock {
    private val manager = ModuleManager.getInstance(project)

    override fun getModules(): List<ModuleBlock> {
        return manager.modules.map { IntellijModuleBlock(it) }
    }
}

class IntelliDependencyBlock(val orderEntry: OrderEntry) : DependencyBlock {

    override fun getName(): String {
        return orderEntry.presentableName
    }

    override fun getType(): DependencyType {
        return when (orderEntry) {
            is LibraryOrderEntry -> DependencyType.LIBRARY
            is ModuleOrderEntry -> DependencyType.MODULE
            else -> DependencyType.UNKNOWN
        }
    }
}

class IntellijIdeaMutableModuleBlock(private val modifiableModel: ModifiableRootModel) : MutableModuleBlock {
    override fun addModule(block: ModuleBlock) {
        modifiableModel.addModuleOrderEntry((block as IntellijModuleBlock).module)
    }

    override fun removeDependency(block: DependencyBlock) {
        modifiableModel.removeOrderEntry((block as IntelliDependencyBlock).orderEntry)
    }
}

class IntellijModuleBlock(val module: Module) : ModuleBlock {
    private val manager = ModuleRootManager.getInstance(module)

    override fun getName(): String {
        return module.name
    }

    override fun getType(): DependencyType {
        return DependencyType.MODULE
    }

    override fun getDependencies(): List<DependencyBlock> {
        return manager.orderEntries.map { IntelliDependencyBlock(it) }
    }

    override fun updateModel(updater: (mutable: MutableModuleBlock) -> Boolean) {
        ModuleRootModificationUtil.updateModel(module) { modModel ->
            val mutator = IntellijIdeaMutableModuleBlock(modModel)
            updater(mutator)
        }
    }
}
