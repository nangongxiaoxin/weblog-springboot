package com.slilio.weblog.web.markdown.provider;

import java.util.Map;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.renderer.html.AttributeProvider;

public class NofollowLinkAttributeProvider implements AttributeProvider {
  /** 网站域名，上线替换 */
  private static final String DOMAIN = "www.baidu.com";

  /**
   * 链接网址处理
   *
   * @param node the node to set attributes for
   * @param tagName the HTML tag name that these attributes are for (e.g. {@code h1}, {@code pre},
   *     {@code code}).
   * @param attributes the attributes, with any default attributes already set in the map
   */
  @Override
  public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
    if (node instanceof Link) {
      Link linkNode = (Link) node;
      // 获取链接地址
      String href = linkNode.getDestination();
      // 如果链接不是自己的域名，则添加rel="nofollow" 属性
      if (!href.contains(DOMAIN)) {
        attributes.put("href", "nofollow");
      }
    }
  }
}
