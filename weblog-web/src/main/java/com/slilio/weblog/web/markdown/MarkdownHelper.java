package com.slilio.weblog.web.markdown;

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
    PARSER = Parser.builder().build();
    HTML_RENDERER = HtmlRenderer.builder().build();
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
        "| First Header  | Second Header |\n"
            + "| ------------- | ------------- |\n"
            + "| Content Cell  | Content Cell  |\n"
            + "| Content Cell  | Content Cell  |";
    System.out.println(convertMarkdown2Html(markdown));
  }
}
