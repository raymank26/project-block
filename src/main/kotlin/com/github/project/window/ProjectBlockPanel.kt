package com.github.project.window

import com.intellij.icons.AllIcons.Actions
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.layout.panel
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JCheckBox
import javax.swing.JPanel
import javax.swing.JSeparator
import javax.swing.JTable
import javax.swing.JToolBar

private const val REFRESH_ACTION = "refresh-deps"

class ProjectBlockPanel : ActionListener {

    private val content: JPanel
    private val projectToolbarActions: ProjectToolbarActions

    constructor(toolWindow: ToolWindow, projectToolbarActions: ProjectToolbarActions) {
        content = panel {
            row {
                val toolBar = JToolBar()
                toolBar.add(createButton(REFRESH_ACTION, "Refresh dependencies"))
                toolBar.isBorderPainted = false
                toolBar.add(JCheckBox("Enable auto-sync", true))
                component(toolBar)
            }
            row {
                component(JSeparator())
            }
            row {
                label("Excludes")
            }
            row {
                component(JTable())
            }
        }
        this.projectToolbarActions = projectToolbarActions
    }

    fun getContent(): JPanel {
        return content
    }

    private fun createButton(cmd: String, toolTip: String): JButton {
        val button = JButton(Actions.Refresh)
        button.size = Dimension(16, 16)
        button.actionCommand = cmd
        button.toolTipText = toolTip
        button.addActionListener(this)
        return button
    }

    override fun actionPerformed(e: ActionEvent) {
        when (e.actionCommand) {
            REFRESH_ACTION -> projectToolbarActions.startRefreshing()
        }
    }
}