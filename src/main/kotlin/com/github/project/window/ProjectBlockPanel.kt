package com.github.project.window

import com.github.project.PluginActions
import com.intellij.icons.AllIcons.Actions
import com.intellij.openapi.project.Project
import com.intellij.ui.layout.panel
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JCheckBox
import javax.swing.JPanel
import javax.swing.JToolBar

private const val REFRESH_ACTION = "refresh-deps"
private const val CHANGE_REFRESH_SETTINGS_ACTION = "refresh-setting-change"

class ProjectBlockPanel : ActionListener {

    private val content: JPanel
    private val pluginActions: PluginActions
    private val project: Project

    private lateinit var autoSyncCheckbox: JCheckBox

    constructor(project: Project, pluginActions: PluginActions) {
        val that = this
        content = panel {
            row {
                val toolBar = JToolBar()
                toolBar.add(createButton(REFRESH_ACTION, "Refresh dependencies"))
                toolBar.isBorderPainted = false
                autoSyncCheckbox = JCheckBox("Enable sync", pluginActions.isChangeEnabled(project))
                autoSyncCheckbox.actionCommand = CHANGE_REFRESH_SETTINGS_ACTION
                autoSyncCheckbox.addActionListener(that)
                toolBar.add(autoSyncCheckbox)
                component(toolBar)
            }
        }
        this.project = project
        this.pluginActions = pluginActions
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
            REFRESH_ACTION -> pluginActions.triggerRefresh(project)
            CHANGE_REFRESH_SETTINGS_ACTION -> {
                val enabled = autoSyncCheckbox.isSelected
                pluginActions.changeEnabled(enabled, project)
            }
        }
    }
}