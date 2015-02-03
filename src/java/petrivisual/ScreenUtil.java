/**
 * BeehiveZ is a business process model and instance management system.
 * Copyright (C) 2011  
 * Institute of Information System and Engineering, School of Software, Tsinghua University,
 * Beijing, China
 *
 * Contact: jintao05@gmail.com 
 *
 * This program is a free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation with the version of 2.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package petrivisual;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

/**
 * @author JinTao
 * 
 */
public class ScreenUtil {

	public static void centerOnScreen(Window frame) {
		int w = frame.getSize().width;
		int h = frame.getSize().height;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		frame.setLocation(Math.max(0,
				(screenSize.width - frame.getSize().width) / 2), Math.max(0,
				(screenSize.height - frame.getSize().height) / 2));
		frame.validate();
	}
}
