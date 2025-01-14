package com.slilio.weblog.web.markdown;

import com.slilio.weblog.web.markdown.provider.NofollowLinkAttributeProvider;
import com.slilio.weblog.web.markdown.renderer.ImageNodeRenderer;
import java.util.Arrays;
import java.util.List;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.ext.image.attributes.ImageAttributesExtension;
import org.commonmark.ext.task.list.items.TaskListItemsExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MarkdownHelper {

  /** Markdown解析器 */
  private static final Parser PARSER;

  /** HTML渲染器 */
  private static final HtmlRenderer HTML_RENDERER;

  /* 初始化 */
  static {
    // Markdown 拓展
    List<Extension> extensions =
        Arrays.asList(
            TablesExtension.create(), // 表格拓展
            HeadingAnchorExtension.create(), // 标题铆钉项
            ImageAttributesExtension.create(), // 图片宽高
            TaskListItemsExtension.create() // 任务列表
            );

    PARSER = Parser.builder().extensions(extensions).build();
    HTML_RENDERER =
        HtmlRenderer.builder()
            .extensions(extensions)
            .attributeProviderFactory(context -> new NofollowLinkAttributeProvider()) // 链接拒绝跟随爬取
            .nodeRendererFactory(context -> new ImageNodeRenderer(context)) // 图片大小以及添加说明
            .build();
  }

  /**
   * 将Markdown转换为Html
   *
   * @param markdown
   * @return
   */
  public static String convertMarkdown2Html(String markdown) {
    Node document = PARSER.parse(markdown);
    return HTML_RENDERER.render(document);
  }

  /**
   * 测试
   *
   * @param args
   */
  public static void main(String[] args) {
    String markdown =
        "![图 1-1 技术栈](https://img.quanxiaoha.com/quanxiaoha/169560181378937 \"图 1-1 技术栈\"){width=100 height=100}";
    System.out.println(MarkdownHelper.convertMarkdown2Html(markdown));
  }
}
