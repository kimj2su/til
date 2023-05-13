package hello.aop;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Slf4j
public class CGLIBTest {

    static interface ProxyClass {
        void call(String name);
    }

    static class Kimjisu implements ProxyClass {
        @Override
        public void call(String name) {
            log.info(name);
        }
    }
    static class MyMethodInterceptor implements MethodInterceptor {

        private final ProxyClass target;

        public MyMethodInterceptor(ProxyClass target) {
            this.target = target;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            log.info("proxy 전");
            method.invoke(target, args);
            log.info("proxy 후");
            return null;
        }
    }

    @Test
    void CGLIBProxyTest() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ProxyClass.class);
        enhancer.setCallback(new MyMethodInterceptor(new Kimjisu()));
        ProxyClass proxy = (ProxyClass) enhancer.create();
        proxy.call("CGLIB");
    }
}
