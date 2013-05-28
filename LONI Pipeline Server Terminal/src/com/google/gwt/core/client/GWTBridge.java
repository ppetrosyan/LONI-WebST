package com.google.gwt.core.client;
import com.google.gwt.core.shared.*;
/**
* Date: 1/17/13
* Time: 12:37 PM
* Added by Kuan-Hao
* 
* This class is used for fixing class not found com.google.gwt.core.client.GWTBridge;
*
* This is only needed for GXT 2 to work. Once we remove GXT2, this can be removed also.
* 
* http://alexluca.com/2013/01/17/gwt-25-and-extgwt-224-classnotfoundexception-comgooglegwtcoreclientgwtbridge/
*/
public abstract class GWTBridge extends com.google.gwt.core.shared.GWTBridge {
}