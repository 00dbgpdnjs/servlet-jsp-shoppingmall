package com.nhnacademy.shoppingmall.common.mvc.view;

// 역할 : Controller(Command)`가 반환하는 viewName을 `WEB-INF/views 하위에 있는 JSP 파일로 대응
public class ViewResolver {

    public static final String DEFAULT_PREFIX="/WEB-INF/views/";
    public static final String DEFAULT_POSTFIX=".jsp";
    public static final String REDIRECT_PREFIX="redirect:";
    public static final String DEFAULT_SHOP_LAYOUT="/WEB-INF/views/layout/shop.jsp";
    public static final String DEFAULT_ADMIN_LAYOUT="/WEB-INF/views/layout/admin.jsp";
    public static final String LAYOUT_CONTENT_HOLDER = "layout_content_holder";

    private final String prefix;
    private final String postfix;

    public ViewResolver(){
        this(DEFAULT_PREFIX,DEFAULT_POSTFIX);
    }
    public ViewResolver(String prefix, String postfix) {
        this.prefix = prefix;
        this.postfix = postfix;
    }

    public  String getPath(String viewName){
        //todo#6-1  prefix+viewNAme+postfix 반환 합니다.
        if(viewName.startsWith("/")) viewName = viewName.substring(1); // ?? 다른 메서드도 이 부분 고려해야할지
        return String.format("%s%s%s", prefix, viewName, postfix);
    }

    // ?? 아래 나머지 메서드는 위 getPath 와 같이 prefix, postfix 적용 언제되는 거지
    public boolean isRedirect(String viewName){
        //todo#6-2 REDIRECT_PREFIX가 포함되어 있는지 체크 합니다.
        return viewName.toLowerCase().contains(REDIRECT_PREFIX); // ?? 다른 메서드로 toLowerCase 해야할지 (/admin/ ?)
    }

    public String getRedirectUrl(String viewName){
        //todo#6-3 REDIRECT_PREFIX를 제외한 url을 반환 합니다.
        if (!isRedirect(viewName)) throw new RuntimeException("Invalid redirect url: " + viewName);
        return viewName.substring(REDIRECT_PREFIX.length());
    }

    public String getLayOut(String viewName){

        /*todo#6-4 viewName에
           /admin/경로가 포함되었다면 DEFAULT_ADMIN_LAYOUT 반환 합니다.
           /admin/경로가 포함되어 있지않다면 DEFAULT_SHOP_LAYOUT 반환 합니다.
        */

        return (viewName.contains("/admin/")) ? DEFAULT_ADMIN_LAYOUT : DEFAULT_SHOP_LAYOUT;
    }
}
