package hello.aop;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {

    static interface ProxyInterface {
        void call(String name);
    }

    static class Kimjisu implements ProxyInterface {
        @Override
        public void call(String name) {
            log.info(name);
        }
    }

    static class MyInvocationHandler implements InvocationHandler {
        private final ProxyInterface target;

        public MyInvocationHandler(ProxyInterface target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            log.info("proxy 전");
            Object result = method.invoke(target, args);
            log.info("proxy 후");
            return result;
        }
    }

    @Test
    void jdkDynamicTest() {
        Kimjisu kimjisu = new Kimjisu();
        MyInvocationHandler handler = new MyInvocationHandler(kimjisu);
        ProxyInterface proxy = (ProxyInterface) Proxy.newProxyInstance(
                ProxyInterface.class.getClassLoader(),
                new Class[]{ProxyInterface.class},
                handler);

        proxy.call("JDK Dynamic Proxy");
        kimjisu.call("call");

    }
}
