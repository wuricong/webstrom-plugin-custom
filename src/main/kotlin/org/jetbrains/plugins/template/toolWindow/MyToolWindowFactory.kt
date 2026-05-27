package org.jetbrains.plugins.template.toolWindow

import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.util.ui.JBUI
import com.intellij.ui.JBColor
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.content.ContentFactory
import com.intellij.util.ui.UIUtil
import org.jetbrains.plugins.template.MyBundle
import org.jetbrains.plugins.template.services.MyProjectService
import javax.swing.JButton


class MyToolWindowFactory : ToolWindowFactory {

    init {
        thisLogger().warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val myToolWindow = MyToolWindow(toolWindow)
        val content = ContentFactory.getInstance().createContent(myToolWindow.getContent(), null, false)
        toolWindow.contentManager.addContent(content)
    }

    override fun shouldBeAvailable(project: Project) = true

    class MyToolWindow(toolWindow: ToolWindow) {

        private val service = toolWindow.project.service<MyProjectService>()

        fun getContent() = JBPanel<JBPanel<*>>().apply {
            val label = JBLabel(MyBundle["randomLabel", "?"])
            add(label)
            add(JButton(MyBundle["shuffle"]).apply {
                // 使用标准的面板背景或按钮背景
                // 1. 这两行是关键：允许自定义背景色涂满
                isContentAreaFilled = false
                isOpaque = true
                // 核心代码：告诉 Swing 不要绘制边框线
                isBorderPainted = false

                // 2. 更改为你想要的颜色，比如临时换个显眼的颜色测试：红色
                background = JBColor.RED
                addActionListener {
                    label.text = MyBundle["randomLabel", service.getRandomNumber()]
                }
            })
        }
    }
}
