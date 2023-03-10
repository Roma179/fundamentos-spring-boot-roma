package com.fundamentosspring.springboot.fundamentos.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyBeanWithDependencyImplement implements MyBeanWithDependency{

    Log LOGGER = LogFactory.getLog(MyBeanWithDependencyImplement.class);
    MyOperation myOperation;

    public MyBeanWithDependencyImplement(MyOperation myOperation) {
        this.myOperation = myOperation;
    }

    @Override
    public void printWithDependency() {
        LOGGER.info("Hemos ingresado al mento printWithDependency");
        int numero = 1;
        LOGGER.debug("El numero enviado como parametro a la dependencia operacion es: "+ numero);
        System.out.println(myOperation.sum(numero));
        System.out.println("HOLA desde una implementacion con una dependencia");
    }
}
