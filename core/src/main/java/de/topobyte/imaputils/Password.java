// Copyright 2019 Sebastian Kuerten
//
// This file is part of imap-utils.
//
// imap-utils is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// imap-utils is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with imap-utils. If not, see <http://www.gnu.org/licenses/>.

package de.topobyte.imaputils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class Password
{

	public static class Result
	{

		private boolean valid = false;
		private char[] password = null;

		public boolean isValid()
		{
			return valid;
		}

		public char[] getPassword()
		{
			return password;
		}

	}

	public static Result get()
	{
		JPanel panel = new JPanel();

		JLabel label = new JLabel("Password:");
		JPasswordField input = new JPasswordField(10);

		panel.add(label);
		panel.add(input);

		JOptionPane optionPane = new JOptionPane(panel,
				JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION) {

			private static final long serialVersionUID = 1L;

			@Override
			public void selectInitialValue()
			{
				input.requestFocusInWindow();
			}

		};

		optionPane.createDialog(null, "Enter password...").setVisible(true);

		Result result = new Result();

		if (((int) optionPane.getValue()) == JOptionPane.YES_OPTION) {
			result.password = input.getPassword();
			result.valid = true;
		}

		return result;
	}

}
