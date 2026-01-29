  package http.servlet;

  /**
   * 서블릿 인스턴스 반환
   * HelloServlet  http.example.HelloServlet
   * service.HelloServlet service.HelloServlet
   */
  public class ServletMapper {

      private static final String DEFAULT_PACKAGE = "http.servlet.example";

      public SimpleServlet getServlet(String uri) throws Exception {

          // 클래스명 생성
          String path = uri.startsWith("/") ? uri.substring(1) : uri;

          int queryIdx = path.indexOf("?");
          if (queryIdx > 0) {
              path = path.substring(0, queryIdx);
          }

          String className = path.contains(".") ? path : DEFAULT_PACKAGE + "." + path;

          // 리플렉션
          Class<?> clazz = Class.forName(className);
          SimpleServlet servlet = (SimpleServlet) clazz.getDeclaredConstructor().newInstance();

          return servlet;
      }
  }
