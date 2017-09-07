/**
 * SendMsgComponentLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ue.webservice;

public class SendMsgComponentLocator extends org.apache.axis.client.Service implements com.ue.webservice.SendMsgComponent {

    public SendMsgComponentLocator() {
    }


    public SendMsgComponentLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SendMsgComponentLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SendMsgComponentHttpPort
    private java.lang.String SendMsgComponentHttpPort_address = "http://202.111.196.16:10019/services/invokenEmsService";

    public java.lang.String getSendMsgComponentHttpPortAddress() {
        return SendMsgComponentHttpPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SendMsgComponentHttpPortWSDDServiceName = "SendMsgComponentHttpPort";

    public java.lang.String getSendMsgComponentHttpPortWSDDServiceName() {
        return SendMsgComponentHttpPortWSDDServiceName;
    }

    public void setSendMsgComponentHttpPortWSDDServiceName(java.lang.String name) {
        SendMsgComponentHttpPortWSDDServiceName = name;
    }

    public com.ue.webservice.SendMsgComponentPortType getSendMsgComponentHttpPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SendMsgComponentHttpPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSendMsgComponentHttpPort(endpoint);
    }

    public com.ue.webservice.SendMsgComponentPortType getSendMsgComponentHttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.ue.webservice.SendMsgComponentHttpBindingStub _stub = new com.ue.webservice.SendMsgComponentHttpBindingStub(portAddress, this);
            _stub.setPortName(getSendMsgComponentHttpPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSendMsgComponentHttpPortEndpointAddress(java.lang.String address) {
        SendMsgComponentHttpPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.ue.webservice.SendMsgComponentPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.ue.webservice.SendMsgComponentHttpBindingStub _stub = new com.ue.webservice.SendMsgComponentHttpBindingStub(new java.net.URL(SendMsgComponentHttpPort_address), this);
                _stub.setPortName(getSendMsgComponentHttpPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SendMsgComponentHttpPort".equals(inputPortName)) {
            return getSendMsgComponentHttpPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.task.ems.ue.com", "SendMsgComponent");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.task.ems.ue.com", "SendMsgComponentHttpPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SendMsgComponentHttpPort".equals(portName)) {
            setSendMsgComponentHttpPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
