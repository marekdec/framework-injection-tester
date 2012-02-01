package com.wordpress.marekdec.injectiontester;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.wordpress.marekdec.injectiontester.injections.InjectionA;
import com.wordpress.marekdec.injectiontester.injections.InjectionB;
import com.wordpress.marekdec.injectiontester.injections.InjectionC;

@Component
public class Service {

	private final InjectionA injectedByConstructor;

	@Inject
	private InjectionB injectedByField;

	private InjectionC injectedBySetter;

	@Inject
	public Service(InjectionA injectedByConstructor) {
		super();
		this.injectedByConstructor = injectedByConstructor;
	}

	public InjectionB getInjectedByField() {
		return injectedByField;
	}

	public InjectionC getInjectedBySetter() {
		return injectedBySetter;
	}

	@Inject
	public void setInjectedBySetter(InjectionC injectedBySetter) {
		this.injectedBySetter = injectedBySetter;
	}

	public InjectionA getInjectedByConstructor() {
		return injectedByConstructor;
	}

	@Override
	public String toString() {
		return "Service [injectedByConstructor=" + injectedByConstructor
				+ ", injectedByField=" + injectedByField
				+ ", injectedBySetter=" + injectedBySetter + "]";
	}

	public String collaboratorsState() {
		return "[report] Collaborator injected by constructor "
				+ isNull(injectedByConstructor)
				+ "\n[report] Collaborator injected by field "
				+ isNull(injectedByField)
				+ "\n[report] Collaborator injected by setter "
				+ isNull(injectedBySetter);
	}

	private String isNull(Object collaborator) {
		return collaborator == null ? "IS NULL" : "is not null";
	}

}
