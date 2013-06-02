/*
 * This file is part of LONI Pipeline Web-based Server Terminal.
 * 
 * LONI Pipeline Web-based Server Terminal is free software: 
 * you can redistribute it and/or modify it under the terms of the 
 * GNU Lesser General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * LONI Pipeline Web-based Server Terminal is distributed in the hope 
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the 
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public License
 * along with LONI Pipeline Web-based Server Terminal.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package com.google.gwt.core.client;

/**
 * Date: 1/17/13 Time: 12:37 PM Added by Kuan-Hao
 * 
 * This class is used for fixing class not found
 * com.google.gwt.core.client.GWTBridge;
 * 
 * This is only needed for GXT 2 to work. Once we remove GXT2, this can be
 * removed also.
 * 
 * http://alexluca.com/2013/01/17/gwt-25-and-extgwt-224-classnotfoundexception-
 * comgooglegwtcoreclientgwtbridge/
 */
public abstract class GWTBridge extends com.google.gwt.core.shared.GWTBridge {
}