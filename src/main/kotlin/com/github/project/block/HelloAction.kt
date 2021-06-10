package com.github.project.block

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.ui.Messages

class HelloAction : DumbAwareAction() {

    override fun actionPerformed(event: AnActionEvent) {
        Messages.showMessageDialog(event.project, "Hello from Kotlin!", "Greeting", Messages.getInformationIcon())
    }
}