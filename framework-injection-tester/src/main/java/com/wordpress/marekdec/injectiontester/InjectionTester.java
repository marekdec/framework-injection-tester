package com.wordpress.marekdec.injectiontester;

import static org.mockito.MockitoAnnotations.initMocks;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.wordpress.marekdec.injectiontester.injections.InjectionA;
import com.wordpress.marekdec.injectiontester.injections.InjectionB;
import com.wordpress.marekdec.injectiontester.injections.InjectionC;

public class InjectionTester {

	public static void main(String[] args) {
		testInjectionWith("Vanilla Java", new VanillaJavaServiceProvider());
		testInjectionWith("Spring", new SpringServiceProvider());
		testInjectionWith("Guice", new GuiceServiceProvider());
		testInjectionWith("CDI", new CDIServiceProvider());
		testInjectionWith("Mockito", new MockitoServiceProvider());
	}

	private static void testInjectionWith(String type, ServiceProvider provider) {
		Service service = provider.getService();

		System.out.println("[report] " + type);
		if (service != null) {
			System.out.println(service.collaboratorsState());
		} else {
			throw new IllegalStateException("The service " + type
					+ " failed to provide the service.");
		}
		System.out.println();
	}

	private interface ServiceProvider {

		Service getService();
	}

	private static class SpringServiceProvider implements ServiceProvider {

		public Service getService() {
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			ctx.scan("com.wordpress.marekdec.injectiontester");
			ctx.refresh();

			return ctx.getBean(Service.class);
		}
	}

	private static class GuiceServiceProvider implements ServiceProvider {

		public Service getService() {
			Injector injector = Guice.createInjector(new Module() {

				public void configure(Binder binder) {
					// EMPTY on purpose

				}
			});

			return injector.getInstance(Service.class);
		}
	}

	private static class CDIServiceProvider implements ServiceProvider {

		public Service getService() {
			WeldContainer weld = new Weld().initialize();
			return weld.instance().select(Service.class).get();
		}
	}

	private static class VanillaJavaServiceProvider implements ServiceProvider {

		public Service getService() {
			return new Service(null);
		}
	}

	private static class MockitoServiceProvider implements ServiceProvider {

		@SuppressWarnings("unused")
		@Mock
		private InjectionA injectionA;

		@SuppressWarnings("unused")
		@Mock
		private InjectionB injectionB;

		@SuppressWarnings("unused")
		@Mock
		private InjectionC injectionC;

		@InjectMocks
		private Service service;

		public Service getService() {
			initMocks(this);
			return service;
		}
	}
}
