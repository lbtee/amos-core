package org.amos.starter.xss.cleaner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

/**
 * @author hccake
 */
public class DefaultXssCleaner implements XssCleaner {

	private final Whitelist whitelist;

	/**
	 * 用于在 src 属性使用相对路径时，强制转换为绝对路径。 为空时不处理，值应为绝对路径的前缀（包含协议部分）
	 */
	private final String baseUri;

	/**
	 * 无参构造，默认使用 {@link DefaultXssCleaner#buildWhitelist} 方法构建一个安全列表
	 */
	public DefaultXssCleaner() {
		this.whitelist = buildWhitelist();
		this.baseUri = "";
	}

	public DefaultXssCleaner(Whitelist safelist) {
		this.whitelist = safelist;
		this.baseUri = "";
	}

	public DefaultXssCleaner(String baseUri) {
		this.whitelist = buildWhitelist();
		this.baseUri = baseUri;
	}

	public DefaultXssCleaner(Whitelist whitelist, String baseUri) {
		this.whitelist = whitelist;
		this.baseUri = baseUri;
	}

	/**
	 * <p>
	 * 自定义xss 白名单
	 * </p>
	 *
	 * <ul>
	 * 基于 Safelist#relaxed() 的基础上:
	 * <li>扩展支持了 style 和 class 属性</li>
	 * <li>a 标签额外支持了 target 属性</li>
	 * <li>img 标签额外支持了 data 协议，便于支持 base64</li>
	 * </ul>
	 * @return Safelist
	 */
	protected Whitelist buildWhitelist() {
		// 使用 jsoup 提供的默认的
		Whitelist whitelist = Whitelist.relaxed();
		// 富文本编辑时一些样式是使用 style 来进行实现的
		// 比如红色字体 style="color:red;", 所以需要给所有标签添加 style 属性
		// 注意：style 属性会有注入风险 <img STYLE="background-image:url(javascript:alert('XSS'))">
		whitelist.addAttributes(":all", "style", "class");
		// 保留 a 标签的 target 属性
		whitelist.addAttributes("a", "target");
		// 支持img 为base64
		whitelist.addProtocols("img", "src", "data");

		// 保留相对路径, 保留相对路径时，必须提供对应的 baseUri 属性，否则依然会被删除
		// WHITELIST.preserveRelativeLinks(false);

		// 移除 a 标签和 img 标签的一些协议限制，这会导致 xss 防注入失效，如 <img src=javascript:alert("xss")>
		// 虽然可以重写 WhiteList#isSafeAttribute 来处理，但是有隐患，所以暂时不支持相对路径
		// WHITELIST.removeProtocols("a", "href", "ftp", "http", "https", "mailto");
		// WHITELIST.removeProtocols("img", "src", "http", "https");

		return whitelist;
	}

	@Override
	public String clean(String html) {
		return Jsoup.clean(html, baseUri, whitelist, new Document.OutputSettings().prettyPrint(false));
	}

}
